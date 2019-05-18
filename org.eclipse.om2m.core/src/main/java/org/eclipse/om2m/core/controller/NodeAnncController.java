/*******************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.core.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceStatus;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.NodeAnncEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.exceptions.ConflictException;
import org.eclipse.om2m.commons.exceptions.ResourceNotFoundException;
import org.eclipse.om2m.commons.resource.NodeAnnc;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.utils.Util.DateUtil;
import org.eclipse.om2m.core.datamapper.DataMapperSelector;
import org.eclipse.om2m.core.entitymapper.EntityMapperFactory;
import org.eclipse.om2m.core.notifier.Notifier;
import org.eclipse.om2m.core.router.Patterns;
import org.eclipse.om2m.core.urimapper.UriMapper;
import org.eclipse.om2m.core.util.ControllerUtil;
import org.eclipse.om2m.core.util.ControllerUtil.UpdateUtil;
import org.eclipse.om2m.persistence.service.DAO;


/**
 * Controller for Node resource
 *
 */
public class NodeAnncController extends Controller {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(NodeAnncController.class);

	@Override
	public ResponsePrimitive doCreate(RequestPrimitive request) {
		/*
		 * NODE CREATION PROCEDURE
		 * @resourceName			NP
		 * resourceType				NP
		 * resourceID				NP
		 * parentID					NP
		 * accessControlPolicyIDs	O
		 * creationTime				NP
		 * expirationTime			O
		 * lastModifiedTime			NP
		 * labels					O
		 * 
		 * nodeID					M
		 * hostedCSELink			O
		 * 
		 */

		ResponsePrimitive response = new ResponsePrimitive(request);
		Patterns patterns = new Patterns();

		// retrieve the parent
		DAO<ResourceEntity> dao = (DAO<ResourceEntity>) patterns.getDAO(request.getTo(), dbs);
		if (dao == null) {
			throw new ResourceNotFoundException("Cannot find parent resource");
		}

		// Get the parent entity
		ResourceEntity parentEntity = (ResourceEntity) dao.find(transaction, request.getTo());
		// Check the parent existence
		if (parentEntity == null) {
			throw new ResourceNotFoundException("Cannot find parent resource");
		}

		List<NodeAnncEntity> childNodes = null;
		List<AccessControlPolicyEntity> acpsToCheck = null;
		List<SubscriptionEntity> subscriptions = null;
		// case parent is csb
		if (parentEntity.getResourceType().intValue() == ResourceType.CSE_BASE) {
			CSEBaseEntity csb = (CSEBaseEntity) parentEntity;
			childNodes = csb.getChildAnncNodes();
			acpsToCheck = csb.getAccessControlPolicies();
			subscriptions = csb.getSubscriptions();
		}
		// case parent is csr
		if (parentEntity.getResourceType().intValue() == ResourceType.REMOTE_CSE) {
			RemoteCSEEntity remoteCse = (RemoteCSEEntity) parentEntity;
			childNodes = remoteCse.getChildAnncNodes();
			acpsToCheck = remoteCse.getAccessControlPolicies();
			subscriptions = remoteCse.getSubscriptions();
		}

		// check access control policy of the originator
		checkACP(acpsToCheck, request.getFrom(), Operation.CREATE);

		// check if content is present
		if (request.getContent() == null) {
			throw new BadRequestException("A content is required for Container creation");
		}

		// get the object from the representation
		NodeAnnc node = null;
		try {
			if (request.getRequestContentType().equals(MimeMediaType.OBJ)) {
				node = (NodeAnnc) request.getContent();
			} else {
				node = (NodeAnnc)DataMapperSelector.getDataMapperList()
						.get(request.getRequestContentType()).stringToObj((String)request.getContent());				
			}

		} catch (ClassCastException e) {
			throw new BadRequestException("Incorrect resource representation in content", e);
		}
		if (node == null) {
			throw new BadRequestException("Error in provided content");
		}

		NodeAnncEntity nodeEntity = new NodeAnncEntity();
		// check attributes
		ControllerUtil.CreateUtil.fillEntityFromGenericResource(node, nodeEntity);

		if (node.getLink() == null) {
			throw new BadRequestException("Link is Mandatory");
		} else {
			nodeEntity.setLink(node.getLink());
		}

		/*
		 * nodeID					M
		 * hostedCSELink			O
		 */
		if (node.getNodeID() == null) {
			throw new BadRequestException("Node ID is Mandatory");
		} else {
			nodeEntity.setNodeID(node.getNodeID());
		}

		if (node.getHostedCSELink() != null) {
			nodeEntity.setHostedCSELink(node.getHostedCSELink());
		}
		if (node.getHostedServiceLinks() != null) {
			nodeEntity.setHostedServiceLinks(node.getHostedServiceLinks());
		}

		String generatedId = generateId();
		// Set other parameters
		nodeEntity.setResourceID("/" + Constants.CSE_ID + "/" +
				ShortName.NODE_ANNC + Constants.PREFIX_SEPERATOR + generatedId);

		nodeEntity.setCreationTime(DateUtil.now());
		nodeEntity.setLastModifiedTime(DateUtil.now());
		nodeEntity.setParentID(parentEntity.getResourceID());
		nodeEntity.setResourceType(ResourceType.NODE_ANNC);
		if (parentEntity.getResourceType().intValue() == ResourceType.CSE_BASE) {
			nodeEntity.setParentCsb((CSEBaseEntity) parentEntity);
		} else if (parentEntity.getResourceType().intValue() == ResourceType.REMOTE_CSE) {
			nodeEntity.setParentCsr((RemoteCSEEntity) parentEntity);
		}

		if (dbs.getDAOFactory().getNodeAnncDAO().find(transaction, nodeEntity.getResourceID()) != null) {
			throw new ConflictException("Already registered");
		}

		if (node.getName() != null) {
			if (!patterns.checkResourceName(node.getName())) {
				throw new BadRequestException("Name provided is incorrect. Must be:" + patterns.ID_STRING);
			}
			nodeEntity.setName(node.getName());
		} else {
			nodeEntity.setName(ShortName.NODE_ANNC + "_" + generatedId);
		}
		nodeEntity.setHierarchicalURI(parentEntity.getHierarchicalURI() + "/" + nodeEntity.getName());

		// set acps
		if (!node.getAccessControlPolicyIDs().isEmpty()) {
			nodeEntity.setAccessControlPolicies(ControllerUtil.buildAcpEntityList(node.getAccessControlPolicyIDs(), transaction));
		} else {
			nodeEntity.getAccessControlPolicies().addAll(acpsToCheck);
		}
		
		// dynamicAuthorizationConsultationIDs O
		if (!node.getDynamicAuthorizationConsultationIDs().isEmpty()) {
			nodeEntity.setDynamicAuthorizationConsultations(
					ControllerUtil.buildDacEntityList(node.getDynamicAuthorizationConsultationIDs(), transaction));
		} 

		// storing the hierarchical uri
		if (!UriMapper.addNewUri(nodeEntity.getHierarchicalURI(), nodeEntity.getResourceID(), ResourceType.NODE_ANNC)) {
			throw new ConflictException("Name already present in the parent collection.");
		}

		// persisting data
		dbs.getDAOFactory().getNodeAnncDAO().create(transaction, nodeEntity);

		// get the managed object
		NodeAnncEntity nodeDB = dbs.getDAOFactory().getNodeAnncDAO().find(transaction, nodeEntity.getResourceID());
		// Add the AE to the parentEntity list
		childNodes.add(nodeDB);
		dao.update(transaction, parentEntity);
		transaction.commit();

		Notifier.notify(subscriptions, nodeDB, ResourceStatus.CHILD_CREATED);
		response.setResponseStatusCode(ResponseStatusCode.CREATED);
		setLocationAndCreationContent(request, response, nodeDB);
		return response;
	}

	@Override
	public ResponsePrimitive doRetrieve(RequestPrimitive request) {
		// create the response primitive
		ResponsePrimitive response = new ResponsePrimitive(request);

		// get the entity
		NodeAnncEntity nodeEntity = dbs.getDAOFactory().getNodeAnncDAO().find(transaction, 
				request.getTo());
		if (nodeEntity == null) {
			throw new ResourceNotFoundException();
		}

		// check authorization
		List<AccessControlPolicyEntity> acps = nodeEntity.getAccessControlPolicies();
		checkACP(acps, request.getFrom(), request.getOperation());
		
		response = new ResponsePrimitive(request);
		// map the entity with the representation resource
		NodeAnnc node = EntityMapperFactory.getNodeAnncMapper().mapEntityToResource(nodeEntity, request);
		response.setContent(node);
		// set status code
		response.setResponseStatusCode(ResponseStatusCode.OK);
		// return the response
		return response;
	}

	@Override
	public ResponsePrimitive doUpdate(RequestPrimitive request) {
		// create the response base
		ResponsePrimitive response = new ResponsePrimitive(request);

		// retrieve the resource from database
		NodeAnncEntity nodeEntity = dbs.getDAOFactory().getNodeAnncDAO().find(transaction, request.getTo());
		if (nodeEntity == null) {
			throw new ResourceNotFoundException();
		}
		// check ACP
		checkACP(nodeEntity.getAccessControlPolicies(), request.getFrom(), Operation.UPDATE);
		

		// check if content is present
		if (request.getContent() == null) {
			throw new BadRequestException("A content is required for node update");
		}

		// create the java object from the resource representation
		// get the object from the representation
		NodeAnnc node = null;
		try{
			if (request.getRequestContentType().equals(MimeMediaType.OBJ)) {
				node = (NodeAnnc) request.getContent();
			} else {
				node = (NodeAnnc)DataMapperSelector.getDataMapperList()
						.get(request.getRequestContentType()).stringToObj((String)request.getContent());				
			}

		} catch (ClassCastException e) {
			throw new BadRequestException("Incorrect resource representation in content", e);
		}
		if (node == null) {
			throw new BadRequestException("Error in provided content");
		}

		// check attributes, NP attributes are ignored
		// @resourceName				NP
		// resourceType					NP
		// resourceID					NP
		// parentID						NP
		// creationTime					NP
		// creator						NP
		// lastModifiedTime				NP
		UpdateUtil.checkNotPermittedParameters(node);
		// hostedCseLink				NP
		if (node.getHostedCSELink() != null) {
			throw new BadRequestException("HostedCSELink is NP");
		}

		NodeAnnc modifiedAttributes = new NodeAnnc();
		// labels						O
		if (!node.getLabels().isEmpty()) {
			nodeEntity.setLabelsEntitiesFromSring(node.getLabels());
			modifiedAttributes.getLabels().addAll(node.getLabels());
		}
		// accessControlPolicyIDs		O
		if (!node.getAccessControlPolicyIDs().isEmpty()) {
			for(AccessControlPolicyEntity acpe : nodeEntity.getAccessControlPolicies()) {
				checkSelfACP(acpe, request.getFrom(), Operation.UPDATE);
			}
			nodeEntity.getAccessControlPolicies().clear();
			nodeEntity.setAccessControlPolicies(ControllerUtil.buildAcpEntityList(node.getAccessControlPolicyIDs(), transaction));
			modifiedAttributes.getAccessControlPolicyIDs().addAll(node.getAccessControlPolicyIDs());
		}
		// expirationTime			O
		if (node.getExpirationTime() != null) {
			nodeEntity.setExpirationTime(node.getExpirationTime());
			modifiedAttributes.setExpirationTime(node.getExpirationTime());
		}

		// nodeID					O
		if (node.getNodeID() != null) {
			nodeEntity.setNodeID(node.getNodeID());
			modifiedAttributes.setNodeID(node.getNodeID());
		}
		
		nodeEntity.setLastModifiedTime(DateUtil.now());
		modifiedAttributes.setLastModifiedTime(nodeEntity.getLastModifiedTime());
		response.setContent(modifiedAttributes);
		
		// uptade the persisted resource
		dbs.getDAOFactory().getNodeAnncDAO().update(transaction, nodeEntity);
		// commit & close the db transaction
		transaction.commit();
		Notifier.notify(nodeEntity.getChildSubscriptions(), nodeEntity, ResourceStatus.UPDATED);

		// set response status code
		response.setResponseStatusCode(ResponseStatusCode.UPDATED);
		return response;
	}

	@Override
	public ResponsePrimitive doDelete(RequestPrimitive request) {
		// Generic delete procedure
		ResponsePrimitive response = new ResponsePrimitive(request);

		// retrieve the entity
		NodeAnncEntity nodeEntity = dbs.getDAOFactory().getNodeAnncDAO().find(transaction, request.getTo());
		if (nodeEntity == null) {
			throw new ResourceNotFoundException();
		}

		// check access control policies
		checkACP(nodeEntity.getAccessControlPolicies(), request.getFrom(), Operation.DELETE);
		
		UriMapper.deleteUri(nodeEntity.getHierarchicalURI());
		Notifier.notifyDeletion(nodeEntity.getChildSubscriptions(), nodeEntity);

		// delete the resource in the database
		dbs.getDAOFactory().getNodeAnncDAO().delete(transaction, nodeEntity);
		// commit the transaction
		transaction.commit();
		// return the response
		response.setResponseStatusCode(ResponseStatusCode.DELETED);
		return response;
	}

}
