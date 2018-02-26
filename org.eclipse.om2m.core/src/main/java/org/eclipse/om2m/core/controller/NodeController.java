/*******************************************************************************
 * Copyright (c) 2013-2016 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Thierry Monteil : Project manager, technical co-manager
 *     Mahdi Ben Alaya : Technical co-manager
 *     Samir Medjiah : Technical co-manager
 *     Khalil Drira : Strategy expert
 *     Guillaume Garzone : Developer
 *     François Aïssaoui : Developer
 *
 * New contributors :
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
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.exceptions.ConflictException;
import org.eclipse.om2m.commons.exceptions.ResourceNotFoundException;
import org.eclipse.om2m.commons.resource.Node;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.utils.Util.DateUtil;
import org.eclipse.om2m.core.announcer.Announcer;
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
public class NodeController extends Controller {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(NodeController.class);

	@SuppressWarnings("unchecked")
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
		DAO<ResourceEntity> parentDao = (DAO<ResourceEntity>) patterns.getDAO(request.getTo(), dbs);
		if (parentDao == null){
			throw new ResourceNotFoundException("Cannot find parent resource");
		}

		// Get the parent entity
		ResourceEntity parentEntity = (ResourceEntity) parentDao.find(transaction, request.getTo());
		// Check the parent existence
		if (parentEntity == null){
			throw new ResourceNotFoundException("Cannot find parent resource");
		}

		// lock parent
		transaction.lock(parentEntity);

		List<NodeEntity> childNodes = null;
		List<AccessControlPolicyEntity> acpsToCheck = null;
		List<SubscriptionEntity> subscriptions = null;
		// case parent is csb
		if(parentEntity.getResourceType().intValue() == ResourceType.CSE_BASE){
			CSEBaseEntity csb = (CSEBaseEntity) parentEntity;
			childNodes = csb.getChildNodes();
			acpsToCheck = csb.getAccessControlPolicies();
			subscriptions = csb.getSubscriptions();
		}
		// case parent is csr
		else if(parentEntity.getResourceType().intValue() == ResourceType.REMOTE_CSE){
			RemoteCSEEntity remoteCse = (RemoteCSEEntity) parentEntity;
			childNodes = remoteCse.getChildNodes();
			acpsToCheck = remoteCse.getAccessControlPolicies();
			subscriptions = remoteCse.getSubscriptions();
		}

		// check access control policy of the originator
//		checkACP(acpsToCheck, request.getFrom(), Operation.CREATE);
		checkPermissions(request, parentEntity, acpsToCheck);

		response = new ResponsePrimitive(request);
		// check if content is present
		if (request.getContent() == null){
			throw new BadRequestException("A content is required for Container creation");
		}

		// get the object from the representation
		Node node = null;
		try{
			if (request.getRequestContentType().equals(MimeMediaType.OBJ)){
				node = (Node) request.getContent();
			} else {
				node = (Node)DataMapperSelector.getDataMapperList()
						.get(request.getRequestContentType()).stringToObj((String)request.getContent());				
			}

		} catch (ClassCastException e){
			throw new BadRequestException("Incorrect resource representation in content", e);
		}
		if (node == null){
			throw new BadRequestException("Error in provided content");
		}

		NodeEntity nodeEntity = new NodeEntity();
		// check attributes
		ControllerUtil.CreateUtil.fillEntityFromAnnounceableResource(node, nodeEntity);

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
		nodeEntity.setResourceID("/" + Constants.CSE_ID + "/" + ShortName.NODE + Constants.PREFIX_SEPERATOR + generatedId);;
		nodeEntity.setCreationTime(DateUtil.now());
		nodeEntity.setLastModifiedTime(DateUtil.now());
		nodeEntity.setParentID(parentEntity.getResourceID());
		nodeEntity.setResourceType(ResourceType.NODE);
		if (parentEntity.getResourceType().intValue() == ResourceType.CSE_BASE) {
			nodeEntity.setParentCsb((CSEBaseEntity) parentEntity);
		} else if (parentEntity.getResourceType().intValue() == ResourceType.REMOTE_CSE) {
			nodeEntity.setParentCsr((RemoteCSEEntity) parentEntity);
		}

		if (node.getName() != null){
			if (!patterns.checkResourceName(node.getName())){
				throw new BadRequestException("Name provided is incorrect. Must be:" + patterns.ID_STRING);
			}
			nodeEntity.setName(node.getName());
		} else {
			nodeEntity.setName(ShortName.NODE + "_" + generatedId);
		}
		nodeEntity.setHierarchicalURI(parentEntity.getHierarchicalURI()+ "/" + nodeEntity.getName());

		// set acps
		if (!node.getAccessControlPolicyIDs().isEmpty()) {
			nodeEntity.setAccessControlPolicies(ControllerUtil.buildAcpEntityList(node.getAccessControlPolicyIDs(), transaction));
		} else {
			nodeEntity.getAccessControlPolicies().addAll(acpsToCheck);
		}

		// storing the hierarchical uri
		if (!UriMapper.addNewUri(nodeEntity.getHierarchicalURI(), nodeEntity.getResourceID(), ResourceType.NODE)){
			throw new ConflictException("Name already present in the parent collection.");
		}
		// persisting data
		DAO<NodeEntity> nodeDao = dbs.getDAOFactory().getNodeDAO();
		nodeDao.create(transaction, nodeEntity);

		// get the manage object
		NodeEntity nodeDB = nodeDao.find(transaction, nodeEntity.getResourceID());
		childNodes.add(nodeDB);
		parentDao.update(transaction, parentEntity);
		transaction.commit();

		if (! node.getAnnounceTo().isEmpty()) {
			node.setName(nodeDB.getName());
			node.setResourceID(nodeDB.getResourceID());
			node.setResourceType(ResourceType.NODE);
			node.setParentID(nodeDB.getParentID());
			Announcer.announce(node, request.getFrom(), "");
		}

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
		NodeEntity nodeEntity = dbs.getDAOFactory().getNodeDAO().find(transaction, request.getTo());
		if (nodeEntity == null) {
			throw new ResourceNotFoundException();
		}

		// check authorization
		List<AccessControlPolicyEntity> acps = nodeEntity.getAccessControlPolicies();
		checkACP(acps, request.getFrom(), request.getOperation());
		
		response = new ResponsePrimitive(request);
		// map the entity with the representation resource
		Node node = EntityMapperFactory.getNodeMapper().mapEntityToResource(nodeEntity, request);
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
		DAO<NodeEntity> nodeDao = dbs.getDAOFactory().getNodeDAO();
		NodeEntity nodeEntity = nodeDao.find(transaction, request.getTo());
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
		Node node = null;
		try{
			if (request.getRequestContentType().equals(MimeMediaType.OBJ)){
				node = (Node) request.getContent();
			} else {
				node = (Node)DataMapperSelector.getDataMapperList()
						.get(request.getRequestContentType()).stringToObj((String)request.getContent());				
			}

		} catch (ClassCastException e){
			throw new BadRequestException("Incorrect resource representation in content", e);
		}
		if (node == null){
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
		if(node.getHostedCSELink() != null){
			throw new BadRequestException("HostedCSELink is NP");
		}

		Node modifiedAttributes = new Node();
		// labels						O
		if(!node.getLabels().isEmpty()){
			nodeEntity.setLabelsEntitiesFromSring(node.getLabels());
			modifiedAttributes.getLabels().addAll(node.getLabels());
		}
		// accessControlPolicyIDs		O
		if(!node.getAccessControlPolicyIDs().isEmpty()){
			for(AccessControlPolicyEntity acpe : nodeEntity.getAccessControlPolicies()){
				checkSelfACP(acpe, request.getFrom(), Operation.UPDATE);
			}
			nodeEntity.getAccessControlPolicies().clear();
			nodeEntity.setAccessControlPolicies(ControllerUtil.buildAcpEntityList(node.getAccessControlPolicyIDs(), transaction));
			modifiedAttributes.getAccessControlPolicyIDs().addAll(node.getAccessControlPolicyIDs());
		}
		// expirationTime			O
		if (node.getExpirationTime() != null){
			nodeEntity.setExpirationTime(node.getExpirationTime());
			modifiedAttributes.setExpirationTime(node.getExpirationTime());
		}
		// announceTo				O
		if(!node.getAnnounceTo().isEmpty()){
			// TODO Announcement in AE update
			nodeEntity.getAnnounceTo().clear();
			nodeEntity.getAnnounceTo().addAll(node.getAnnounceTo());
			modifiedAttributes.getAnnounceTo().addAll(node.getAnnounceTo());
		}
		// announcedAttribute		O
		if(!node.getAnnouncedAttribute().isEmpty()){
			nodeEntity.getAnnouncedAttribute().clear();
			nodeEntity.getAnnouncedAttribute().addAll(node.getAnnouncedAttribute());
			modifiedAttributes.getAnnouncedAttribute().addAll(node.getAnnouncedAttribute());
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
		nodeDao.update(transaction, nodeEntity);
		// commit & close the db transaction
		transaction.commit();
		Notifier.notify(nodeEntity.getSubscriptions(), nodeEntity, ResourceStatus.UPDATED);

		// set response status code
		response.setResponseStatusCode(ResponseStatusCode.UPDATED);
		return response;
	}

	@Override
	public ResponsePrimitive doDelete(RequestPrimitive request) {
		// Generic delete procedure
		ResponsePrimitive response = new ResponsePrimitive(request);
		// retrieve the entity
		DAO<NodeEntity> nodeDao = dbs.getDAOFactory().getNodeDAO();
		NodeEntity nodeEntity = nodeDao.find(transaction, request.getTo());
		if (nodeEntity == null) {
			LOGGER.info("Delete node: not found");
			throw new ResourceNotFoundException();
		}

		// lock entity
		transaction.lock(nodeEntity);

		// check access control policies
//		checkACP(nodeEntity.getAccessControlPolicies(), request.getFrom(), Operation.DELETE);
		checkPermissions(request, nodeEntity, nodeEntity.getAccessControlPolicies());
		
		UriMapper.deleteUri(nodeEntity.getHierarchicalURI());
		Notifier.notifyDeletion(nodeEntity.getSubscriptions(), nodeEntity);

		// delete the resource in the database
		nodeDao.delete(transaction, nodeEntity);
		// commit the transaction
		transaction.commit();

		// deannounce
		if (! nodeEntity.getAnnounceTo().isEmpty()) {
			Announcer.deAnnounce(nodeEntity, Constants.ADMIN_REQUESTING_ENTITY);
		}

		// return the response
		response.setResponseStatusCode(ResponseStatusCode.DELETED);
		return response;
	}

}
