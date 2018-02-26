/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
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
import org.eclipse.om2m.commons.constants.ResourceStatus;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.exceptions.ConflictException;
import org.eclipse.om2m.commons.exceptions.NotPermittedAttrException;
import org.eclipse.om2m.commons.exceptions.Om2mException;
import org.eclipse.om2m.commons.exceptions.ResourceNotFoundException;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.flexcontainerspec.FlexContainerFactory;
import org.eclipse.om2m.commons.utils.Util.DateUtil;
import org.eclipse.om2m.core.announcer.Announcer;
import org.eclipse.om2m.core.datamapper.DataMapperSelector;
import org.eclipse.om2m.core.entitymapper.EntityMapperFactory;
import org.eclipse.om2m.core.flexcontainer.FlexContainerSelector;
import org.eclipse.om2m.core.flexcontainer.FlexContainerXMLValidator;
import org.eclipse.om2m.core.notifier.Notifier;
import org.eclipse.om2m.core.router.Patterns;
import org.eclipse.om2m.core.urimapper.UriMapper;
import org.eclipse.om2m.core.util.ControllerUtil;
import org.eclipse.om2m.flexcontainer.service.FlexContainerService;
import org.eclipse.om2m.persistence.service.DAO;

/**
 * Controller for the Container Resource
 *
 */
public class FlexContainerController extends Controller {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(FlexContainerController.class);

	/**
	 * Create the resource in the system according to the representation
	 */
	@Override
	public ResponsePrimitive doCreate(RequestPrimitive request) {
		/*
		 * Container creation procedure
		 * 
		 * @resourceName NP resourceType NP resourceID NP parentID NP
		 * accessControlPolicyIDs O creationTime NP expirationTime O
		 * lastModifiedTime NP labels O announceTo O announcedAttribute O
		 * 
		 * creator O maxNrOfInstances O maxByteSize O maxInstanceAge O
		 * currentNrOfInstances NP currentByteSize NP locationID O ontologyRef O
		 * 
		 */
		
		String contentFormat = System.getProperty("org.eclipse.om2m.registration.contentFormat", MimeMediaType.XML);
		Patterns patterns = new Patterns();

		ResponsePrimitive response = new ResponsePrimitive(request);

		// get the dao of the parent
		DAO<ResourceEntity> dao = (DAO<ResourceEntity>) patterns.getDAO(request.getTo(), dbs);
		if (dao == null) {
			throw new ResourceNotFoundException("Cannot find parent resource");
		}

		// get the parent entity
		ResourceEntity parentEntity = (ResourceEntity) dao.find(transaction, request.getTo());
		// check the parent existence
		if (parentEntity == null) {
			throw new ResourceNotFoundException("Cannot find parent resource");
		}

		// lock parent
		transaction.lock(parentEntity);

		// get lists to change in the method corresponding to specific object
		List<AccessControlPolicyEntity> acpsToCheck = null;
//		List<DynamicAuthorizationConsultationEntity> dacsToCheck = null;
		List<FlexContainerEntity> childFlexContainers = null;
		List<SubscriptionEntity> subscriptions = null;

		// different cases
		// case parent is CSEBase
		if (parentEntity.getResourceType().intValue() == (ResourceType.CSE_BASE)) {
			CSEBaseEntity cseB = (CSEBaseEntity) parentEntity;
			acpsToCheck = cseB.getAccessControlPolicies();
			childFlexContainers = cseB.getChildFlexContainers();
			subscriptions = cseB.getSubscriptions();
//			dacsToCheck = cseB.getDynamicAuthorizationConsultations();
		}
		// case parent is AE
		if (parentEntity.getResourceType().intValue() == (ResourceType.AE)) {
			AeEntity ae = (AeEntity) parentEntity;
			acpsToCheck = ae.getAccessControlPolicies();
			childFlexContainers = ae.getChildFlexContainers();
			subscriptions = ae.getSubscriptions();
//			dacsToCheck = ae.getDynamicAuthorizationConsultations();
		}
		// case parent is a FlexContainer
		if (parentEntity.getResourceType().intValue() == (ResourceType.FLEXCONTAINER)) {
			FlexContainerEntity parentFlexContainer = (FlexContainerEntity) parentEntity;
			acpsToCheck = parentFlexContainer.getAccessControlPolicies();
			childFlexContainers = parentFlexContainer.getChildFlexContainers();
			subscriptions = parentFlexContainer.getSubscriptions();
//			dacsToCheck = parentFlexContainer.getDynamicAuthorizationConsultations();
		}
		// case parent is a Container
		if (parentEntity.getResourceType().intValue() == (ResourceType.CONTAINER)) {
			ContainerEntity parentContainer = (ContainerEntity) parentEntity;
			acpsToCheck = parentContainer.getAccessControlPolicies();
			childFlexContainers = parentContainer.getChildFlexContainers();
			subscriptions = parentContainer.getSubscriptions();
//			dacsToCheck = parentContainer.getDynamicAuthorizationConsultations();
		}

		// case parent is a RemoteCSE
		if (parentEntity.getResourceType().intValue() == ResourceType.REMOTE_CSE) {
			RemoteCSEEntity csr = (RemoteCSEEntity) parentEntity;
			acpsToCheck = csr.getAccessControlPolicies();
			childFlexContainers = csr.getChildFcnt();
			subscriptions = csr.getSubscriptions();
//			dacsToCheck = csr.getDynamicAuthorizationConsultations();
		}

		// check access control policy of the originator
//		checkACP(acpsToCheck, request.getFrom(), Operation.CREATE);
		checkPermissions(request, parentEntity, acpsToCheck);
		
		// check if content is present
		if (request.getContent() == null) {
			throw new BadRequestException("A content is requiered for FlexContainer creation");
		}
		// get the object from the representation
		AbstractFlexContainer flexContainer = null;
		try {

			String payload = null;
			if (request.getRequestContentType().equals(MimeMediaType.OBJ)) {
				flexContainer = (AbstractFlexContainer) request.getContent();

				// need to create the payload in order to validate it
				payload = DataMapperSelector.getDataMapperList().get(contentFormat).objToString(flexContainer);

			} else {
				flexContainer = (AbstractFlexContainer) DataMapperSelector.getDataMapperList()
						.get(request.getRequestContentType()).stringToObj((String) request.getContent());

				if (request.getRequestContentType().equals(contentFormat)) {
					payload = (String) request.getContent();
				} else {
					// need to create the payload in order to validate it
					payload = DataMapperSelector.getDataMapperList().get(contentFormat).objToString(flexContainer);
				}
			}

			// validate XML payload
			if (contentFormat.equals(MimeMediaType.XML)) {
				FlexContainerXMLValidator.validateXMLPayload(payload, flexContainer.getContainerDefinition());
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
			LOGGER.debug("ClassCastException: Incorrect resource type in object conversion.", e);
			throw new BadRequestException("Incorrect resource representation in content", e);
		}

		if (flexContainer == null) {
			throw new BadRequestException("Error in provided content");
		}

		// creating the corresponding entity
		FlexContainerEntity flexContainerEntity = new FlexContainerEntity();
		// check attributes
		// @resourceName NP
		// Resource Type NP
		// resourceID NP
		// parentID NP
		// lastModifiedTime NP
		// creationTime NP
		// expiration time O
		// labels O
		// announceTo O
		// announcedAttribute O

		ControllerUtil.CreateUtil.fillEntityFromAnnounceableResource(flexContainer, flexContainerEntity);
		
		flexContainerEntity.setLongName(flexContainer.getLongName());
		flexContainerEntity.setShortName(flexContainer.getShortName());

		// creator O
		if (flexContainer.getCreator() != null) {
			flexContainerEntity.setCreator(flexContainer.getCreator());
		}

		// containerDefinition != null
		if ((flexContainer.getContainerDefinition() == null) || (flexContainer.getContainerDefinition().isEmpty())) {
			// the containerDefinition MUST be provided
			throw new NotPermittedAttrException("containerDefinition attribute must be provided.");
		} else {
			flexContainerEntity.setContainerDefinition(flexContainer.getContainerDefinition());
		}

		// containerDefinition exists...
		// TODO

		String generatedId = generateId("", "");
		// set name if present and without any conflict
		if (flexContainer.getName() != null) {
			if (!patterns.checkResourceName(flexContainer.getName())) {
				throw new BadRequestException("Name provided is incorrect. Must be:" + patterns.ID_STRING);
			}
			flexContainerEntity.setName(flexContainer.getName());
		} else {
			flexContainerEntity.setName(ShortName.FCNT + "_" + generatedId);
		}
		flexContainerEntity.setResourceID(
				"/" + Constants.CSE_ID + "/" + ShortName.FCNT + Constants.PREFIX_SEPERATOR + generatedId);
		flexContainerEntity.setHierarchicalURI(parentEntity.getHierarchicalURI() + "/" + flexContainerEntity.getName());
		flexContainerEntity.setParentID(parentEntity.getResourceID());
		flexContainerEntity.setResourceType(ResourceType.FLEXCONTAINER);
		switch (parentEntity.getResourceType().intValue()) {
		case ResourceType.AE:
			flexContainerEntity.setParentAE((AeEntity) parentEntity);
			break;
		case ResourceType.FLEXCONTAINER:
			flexContainerEntity.setParentFlexContainer((FlexContainerEntity) parentEntity);
			break;
		}

		// accessControlPolicyIDs O
		if (!flexContainer.getAccessControlPolicyIDs().isEmpty()) {
			flexContainerEntity.setAccessControlPolicies(
					ControllerUtil.buildAcpEntityList(flexContainer.getAccessControlPolicyIDs(), transaction));
		} else {
			flexContainerEntity.getAccessControlPolicies().addAll(acpsToCheck);
		}

		// dynamicAuthorizationConsultationIDs O
		if (!flexContainer.getDynamicAuthorizationConsultationIDs().isEmpty()) {
			flexContainerEntity.setDynamicAuthorizationConsultations(
					ControllerUtil.buildDacEntityList(flexContainer.getDynamicAuthorizationConsultationIDs(), transaction));
		}

		if (!UriMapper.addNewUri(flexContainerEntity.getHierarchicalURI(), flexContainerEntity.getResourceID(),
				ResourceType.FLEXCONTAINER)) {
			throw new ConflictException("Name already present in the parent collection.");
		}

		// ontologyRef O
		if (flexContainer.getOntologyRef() != null) {
			flexContainerEntity.setOntologyRef(flexContainer.getOntologyRef());
		}

		// nodeLink O
		if (flexContainer.getNodeLink() != null) {
			flexContainerEntity.setNodeLink(flexContainer.getNodeLink());
		}

		// custom attributes
		for (CustomAttribute ca : flexContainer.getCustomAttributes()) {
			flexContainerEntity.createOrUpdateCustomAttribute(ca.getCustomAttributeName(),
					ca.getCustomAttributeValue());
		}

		// create the FlexContainer in the DB
		dbs.getDAOFactory().getFlexContainerDAO().create(transaction, flexContainerEntity);
		// retrieve the managed object from DB
		FlexContainerEntity flexContainerFromDB = dbs.getDAOFactory().getFlexContainerDAO().find(transaction,
				flexContainerEntity.getResourceID());

		// add the container to the parentEntity child list
		childFlexContainers.add(flexContainerFromDB);
		dao.update(transaction, parentEntity);
		
		// update link with flexContainerEntity - DacEntity
		for(DynamicAuthorizationConsultationEntity dace : flexContainerFromDB.getDynamicAuthorizationConsultations()) {
			DynamicAuthorizationConsultationEntity daceFromDB = dbs.getDAOFactory().getDynamicAuthorizationDAO().find(transaction, dace.getResourceID());
			daceFromDB.getLinkedFlexContainerEntites().add(flexContainerFromDB);
			dbs.getDAOFactory().getDynamicAuthorizationDAO().update(transaction, daceFromDB);
		}

		// commit the transaction & release lock
		transaction.commit();

		if (! flexContainer.getAnnounceTo().isEmpty()) {
			flexContainer.setName(flexContainerFromDB.getName());
			flexContainer.setResourceID(flexContainerFromDB.getResourceID());
			flexContainer.setResourceType(ResourceType.FLEXCONTAINER);
			flexContainer.setParentID(flexContainerFromDB.getParentID());
			String hierachicalURI = flexContainerFromDB.getHierarchicalURI();
			String remoteLocation = hierachicalURI
					.substring(("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/").length());
			Announcer.announce(flexContainer, request.getFrom(), remoteLocation);
		}

		Notifier.notify(subscriptions, flexContainerFromDB, ResourceStatus.CHILD_CREATED);

		// create the response
		response.setResponseStatusCode(ResponseStatusCode.CREATED);
		// set the location of the resource
		setLocationAndCreationContent(request, response, flexContainerFromDB);
		return response;
	}

	/**
	 * Return the container resource with the normalized representation
	 * 
	 * @param request
	 *            primitive routed
	 * @return response primitive
	 */
	@Override
	public ResponsePrimitive doRetrieve(RequestPrimitive request) {
		// Creating the response primitive
		ResponsePrimitive response = new ResponsePrimitive(request);

		// Check existence of the resource
		FlexContainerEntity flexContainerEntity = dbs.getDAOFactory().getFlexContainerDAO().find(transaction,
				request.getTo());
		if (flexContainerEntity == null) {
			throw new ResourceNotFoundException("Resource not found");
		}

		// if resource exists, check authorization
		// retrieve
		List<AccessControlPolicyEntity> acpList = flexContainerEntity.getAccessControlPolicies();
//		checkACP(acpList, request.getFrom(), request.getOperation());
		checkPermissions(request, flexContainerEntity, flexContainerEntity.getAccessControlPolicies());

		// Mapping the entity with the exchange resource
		AbstractFlexContainer flexContainerResource = EntityMapperFactory.getFlexContainerMapper()
				.mapEntityToResource(flexContainerEntity, request);


		response.setContent(flexContainerResource);

		response.setResponseStatusCode(ResponseStatusCode.OK);
		// return the response
		return response;
	}

	/**
	 * Implement the full update method for container entity
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public ResponsePrimitive doUpdate(RequestPrimitive request) {
		return processInternalNotifyOrUpdate(request, false);
	}

	@Override
	public ResponsePrimitive doInternalNotify(RequestPrimitive request) {
		return processInternalNotifyOrUpdate(request, true);
	}

	/**
	 * @param request
	 * @return
	 */
	private ResponsePrimitive processInternalNotifyOrUpdate(RequestPrimitive request, boolean isInternalNotify) {
		/*
		 * Container update procedure
		 * 
		 * @resourceName NP resourceType NP resourceID NP parentID NP
		 * accessControlPolicyIDs O creationTime NP expirationTime O
		 * lastModifiedTime NP labels O announceTo O announcedAttribute O
		 * 
		 * creator NP maxNrOfInstances O maxByteSize O maxInstanceAge O
		 * currentNrOfInstances NP currentByteSize NP locationID O ontologyRef O
		 * 
		 */
		// create the response base
		String contentFormat = System.getProperty("org.eclipse.om2m.registration.contentFormat", MimeMediaType.XML);

		ResponsePrimitive response = new ResponsePrimitive(request);

		// retrieve the resource from database
		FlexContainerEntity flexContainerEntity = dbs.getDAOFactory().getFlexContainerDAO().find(transaction,
				request.getTo());

		// lock current object
		transaction.lock(flexContainerEntity);

		if (flexContainerEntity == null) {
			throw new ResourceNotFoundException("Resource not found");
		}
		// check ACP
//		checkACP(flexContainerEntity.getAccessControlPolicies(), request.getFrom(), Operation.UPDATE);
		if (!isInternalNotify) {
			checkPermissions(request, flexContainerEntity, flexContainerEntity.getAccessControlPolicies());
		}

		AbstractFlexContainer modifiedFlexCtr = FlexContainerFactory.getSpecializationFlexContainer(flexContainerEntity.getShortName());
		// check if content is present
		if (request.getContent() == null) {
			// content might be null for FlexContainer representing a SDT action
		} else {
			// create the java object from the resource representation
			// get the object from the representation
			AbstractFlexContainer flexContainer = null;
			try {
				String payload = null;
				if (request.getRequestContentType().equals(MimeMediaType.OBJ)) {
					flexContainer = (AbstractFlexContainer) request.getContent();
					// need to create the payload in order to validate it
					payload = DataMapperSelector.getDataMapperList().get(contentFormat)
							.objToString(flexContainer);
				} else {
					flexContainer = (AbstractFlexContainer) DataMapperSelector.getDataMapperList()
							.get(request.getRequestContentType()).stringToObj((String) request.getContent());
					if (request.getRequestContentType().equals(contentFormat)) {
						payload = (String) request.getContent();
					} else {
						// need to create the XML payload in order to validate it
						payload = DataMapperSelector.getDataMapperList().get(contentFormat)
								.objToString(flexContainer);
					}
				}

				// validate XML payload
				if (contentFormat.equals(MimeMediaType.XML)) {
					FlexContainerXMLValidator.validateXMLPayload(payload, flexContainer.getContainerDefinition());
				}
			} catch (ClassCastException e) {
				throw new BadRequestException("Incorrect resource representation in content", e);
			}
			if (flexContainer == null) {
				throw new BadRequestException("Error in provided content");
			}

			// check attributes, NP attributes are ignored
			// @resourceName NP
			// resourceType NP
			// resourceID NP
			// parentID NP
			// creationTime NP
			// creator NP
			// lastModifiedTime NP
			// currentNrOfInstances NP
			// currentByteSize NP

			// labels O
			// accessControlPolicyIDs O
			if (!flexContainer.getAccessControlPolicyIDs().isEmpty()) {
				flexContainerEntity.getAccessControlPolicies().clear();
				flexContainerEntity.setAccessControlPolicies(
						ControllerUtil.buildAcpEntityList(flexContainer.getAccessControlPolicyIDs(), transaction));
				modifiedFlexCtr.getAccessControlPolicyIDs().addAll(flexContainer.getAccessControlPolicyIDs());
			}
			
			// dynamicAuthorizationConsultationIDs O
			if (!flexContainer.getDynamicAuthorizationConsultationIDs().isEmpty()) {
				flexContainerEntity.setDynamicAuthorizationConsultations(
						ControllerUtil.buildDacEntityList(flexContainer.getDynamicAuthorizationConsultationIDs(), transaction));
				
				// update link with flexContainerEntity - DacEntity
				for(DynamicAuthorizationConsultationEntity dace : flexContainerEntity.getDynamicAuthorizationConsultations()) {
					DynamicAuthorizationConsultationEntity daceFromDB = dbs.getDAOFactory().getDynamicAuthorizationDAO().find(transaction, dace.getResourceID());
					daceFromDB.getLinkedFlexContainerEntites().add(flexContainerEntity);
					dbs.getDAOFactory().getDynamicAuthorizationDAO().update(transaction, daceFromDB);
				}
			}
			
			// labels O
			if (!flexContainer.getLabels().isEmpty()) {
				flexContainerEntity.setLabelsEntitiesFromSring(flexContainer.getLabels());
				modifiedFlexCtr.getLabels().addAll(flexContainer.getLabels());
			}
			// expirationTime O
			if (flexContainer.getExpirationTime() != null) {
				flexContainerEntity.setExpirationTime(flexContainer.getExpirationTime());
				modifiedFlexCtr.setExpirationTime(flexContainer.getExpirationTime());
			}
			// announceTo O
			if (!flexContainer.getAnnounceTo().isEmpty()) {
				// TODO Announcement in AE update
				flexContainerEntity.getAnnounceTo().clear();
				flexContainerEntity.getAnnounceTo().addAll(flexContainer.getAnnounceTo());
				modifiedFlexCtr.getAnnounceTo().addAll(flexContainer.getAnnounceTo());
			}
			// announcedAttribute O
			if (!flexContainer.getAnnouncedAttribute().isEmpty()) {
				flexContainerEntity.getAnnouncedAttribute().clear();
				flexContainerEntity.getAnnouncedAttribute().addAll(flexContainer.getAnnouncedAttribute());
				modifiedFlexCtr.getAnnouncedAttribute().addAll(flexContainer.getAnnouncedAttribute());
			}
			// ontologyRef O
			if (flexContainer.getOntologyRef() != null) {
				flexContainerEntity.setOntologyRef(flexContainer.getOntologyRef());
				modifiedFlexCtr.setOntologyRef(flexContainer.getOntologyRef());
			}
			// nodeLink O
			if (flexContainer.getNodeLink() != null) {
				flexContainerEntity.setNodeLink(flexContainer.getNodeLink());
				modifiedFlexCtr.setNodeLink(flexContainer.getNodeLink());
			}
			// containerDef
			if ((flexContainer.getContainerDefinition() != null)
					&& (!flexContainerEntity.getContainerDefinition().equals(flexContainer.getContainerDefinition()))) {
				throw new BadRequestException("unable to change the containerDefinition value");
			}

			// here add customAttribute that might be updated
			if (!flexContainer.getCustomAttributes().isEmpty()) {
				for (CustomAttribute ca : flexContainer.getCustomAttributes()) {
					flexContainerEntity.createOrUpdateCustomAttribute(ca.getCustomAttributeName(),
							ca.getCustomAttributeValue());
				}
				modifiedFlexCtr.setCustomAttributes(flexContainer.getCustomAttributes());
			}
		}

		flexContainerEntity.setLastModifiedTime(DateUtil.now());
		modifiedFlexCtr.setLastModifiedTime(flexContainerEntity.getLastModifiedTime());

		// in case of update operation
		if (!isInternalNotify) {
			// check if a FlexContainerService exist
			FlexContainerService fcs = FlexContainerSelector.getFlexContainerService(
					/* request.getTo() */ /* UriUtil.toCseRelativeUri( */flexContainerEntity.getResourceID()/* ) */);
			if (fcs != null) {
				try {
					fcs.setCustomAttributeValues(modifiedFlexCtr.getCustomAttributes(), request);
					// at this modifiedAttributes.getCustomAttributes() list
					// contains the new values of CustomAttribute
				} catch (Om2mException e) {
					throw e;
				}
			}
		}

		// at this point, we are sure there was no error when setting custom
		// attribute parameter

		response.setContent(modifiedFlexCtr);
		// update the resource in the database
		dbs.getDAOFactory().getFlexContainerDAO().update(transaction, flexContainerEntity);

		// commit and release lock
		transaction.commit();

		Notifier.notify(flexContainerEntity.getSubscriptions(), flexContainerEntity, modifiedFlexCtr,
				ResourceStatus.UPDATED);

		// set response status code
		response.setResponseStatusCode(ResponseStatusCode.UPDATED);
		return response;
	}

	/**
	 * Delete the container if access control policies are correct
	 */
	@Override
	public ResponsePrimitive doDelete(RequestPrimitive request) {
		// Generic delete procedure
		ResponsePrimitive response = new ResponsePrimitive(request);

		// retrieve the corresponding resource from database
		FlexContainerEntity flexContainerEntity = dbs.getDAOFactory().getFlexContainerDAO().find(transaction,
				request.getTo());
		if (flexContainerEntity == null) {
			LOGGER.info("Delete flexCnt: not found");
			throw new ResourceNotFoundException("Resource not found");
		}

		// lock entity
		transaction.lock(flexContainerEntity);
		
		// check access control policies
//		checkACP(flexContainerEntity.getAccessControlPolicies(), request.getFrom(), Operation.DELETE);
		checkPermissions(request, flexContainerEntity, flexContainerEntity.getAccessControlPolicies());

		UriMapper.deleteUri(flexContainerEntity.getHierarchicalURI());
		Notifier.notifyDeletion(flexContainerEntity.getSubscriptions(), flexContainerEntity);

		// delete the resource in the database
		dbs.getDAOFactory().getFlexContainerDAO().delete(transaction, flexContainerEntity);
		// commit the transaction & release lock
		transaction.commit();
		
		// deannounce
		if (! flexContainerEntity.getAnnounceTo().isEmpty()) {
			Announcer.deAnnounce(flexContainerEntity, Constants.ADMIN_REQUESTING_ENTITY);
		}

		// return the response
		response.setResponseStatusCode(ResponseStatusCode.DELETED);
		return response;
	}

}
