/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceStatus;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.exceptions.ConflictException;
import org.eclipse.om2m.commons.exceptions.ResourceNotFoundException;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.utils.Util.DateUtil;
import org.eclipse.om2m.core.datamapper.DataMapperSelector;
import org.eclipse.om2m.core.entitymapper.EntityMapperFactory;
import org.eclipse.om2m.core.notifier.Notifier;
import org.eclipse.om2m.core.router.Patterns;
import org.eclipse.om2m.core.urimapper.UriMapper;
import org.eclipse.om2m.core.util.ControllerUtil;
import org.eclipse.om2m.persistence.service.DAO;

public class DynamicAuthorizationConsultationController extends Controller {

	@Override
	public ResponsePrimitive doCreate(RequestPrimitive request) {
		ResponsePrimitive response = new ResponsePrimitive(request);
		Patterns patterns = new Patterns();

		// Get the DAO of the parent
		DAO<ResourceEntity> dao = (DAO<ResourceEntity>) patterns.getDAO(request.getTargetId(), dbs);
		if (dao == null) {
			throw new ResourceNotFoundException("Cannot find parent resource");
		}

		// Get the parent entity
		ResourceEntity parentEntity = (ResourceEntity) dao.find(transaction, request.getTargetId());
		// Check the parent existence
		if (parentEntity == null) {
			throw new ResourceNotFoundException("Cannot find parent resource");
		}

		// lock parent database entity
		transaction.lock(parentEntity);

		// Get lists to change in the method corresponding to specific object
		List<AccessControlPolicyEntity> acpsToCheck = null;
		List<DynamicAuthorizationConsultationEntity> childDynamicAuthorizationConsultations = null;
		List<SubscriptionEntity> subs = null;
		List<DynamicAuthorizationConsultationEntity> dacsToCheck = null;

		// retrieve fields values depending on the type of the parent
		switch (parentEntity.getResourceType().intValue()) {
		case ResourceType.CSE_BASE:
			CSEBaseEntity cseBaseEntity = (CSEBaseEntity) parentEntity;
			acpsToCheck = cseBaseEntity.getAccessControlPolicies();
			childDynamicAuthorizationConsultations = cseBaseEntity.getChildDynamicAuthorizationConsultation();
			subs = cseBaseEntity.getSubscriptions();
			dacsToCheck = cseBaseEntity.getDynamicAuthorizationConsultations();
			break;
		case ResourceType.REMOTE_CSE:
			RemoteCSEEntity remoteCseEntity = (RemoteCSEEntity) parentEntity;
			acpsToCheck = remoteCseEntity.getAccessControlPolicies();
			childDynamicAuthorizationConsultations = remoteCseEntity.getChildDynamicAuthorizationConsultation();
			subs = remoteCseEntity.getSubscriptions();
			dacsToCheck = remoteCseEntity.getDynamicAuthorizationConsultations();
			break;
		case ResourceType.AE:
			AeEntity aeEntity = (AeEntity) parentEntity;
			acpsToCheck = aeEntity.getAccessControlPolicies();
			childDynamicAuthorizationConsultations = aeEntity.getChildDynamicAuthorizationConsultations();
			subs = aeEntity.getSubscriptions();
			dacsToCheck = aeEntity.getDynamicAuthorizationConsultations();
			break;
		}

		// Check access control policy of the originator
		checkACP(acpsToCheck, request.getFrom(), Operation.CREATE);

		// Check if content is present
		if (request.getContent() == null) {
			throw new BadRequestException("A content is requiered for AE creation");
		}

		// get java representation of the DynamicAuthorizationConsultation
		// object
		DynamicAuthorizationConsultation dac = null;
		try {
			if (request.getRequestContentType().equals(MimeMediaType.OBJ)) {
				dac = (DynamicAuthorizationConsultation) request.getContent();
			} else {
				dac = (DynamicAuthorizationConsultation) DataMapperSelector.getDataMapperList()
						.get(request.getRequestContentType()).stringToObj((String) request.getContent());
			}
		} catch (ClassCastException e) {
			throw new BadRequestException("Incorrect resource representation in content", e);
		}

		// if representation == null ==> throw BadRequest
		if (dac == null) {
			throw new BadRequestException("Error in provided content");
		}

		// Check attributes
		// @resourceName NP
		// resourceType NP
		// resourceID NP
		// parentID NP
		// creationTime NP
		// lastModifiedTime NP
		// expirationTime O
		// accessControlPolicyIds O
		// labels O
		// dynamicAuthorizationConsultationIDs O

		// create entity
		DynamicAuthorizationConsultationEntity dacEntity = new DynamicAuthorizationConsultationEntity();

		// fill up GenericResource parameters
		ControllerUtil.CreateUtil.fillEntityFromGenericResource(dac, dacEntity);

		// expirationTime
		dacEntity.setExpirationTime(dac.getExpirationTime());

		// dynamicAuthorizationConsultationIDs O
		if (!dac.getDynamicAuthorizationConsultationIDs().isEmpty()) {
			dacEntity.setDynamicAuthorizationConsultations(
					ControllerUtil.buildDacEntityList(dac.getDynamicAuthorizationConsultationIDs(), transaction));
		} 

		// dynamicAuthorizationEnabled M
		if (dac.getDynamicAuthorizationEnabled() == null) {
			throw new BadRequestException("dynamicAuthorizationEnabled is Mandatory");
		}
		dacEntity.setDynamicAuthorizationEnabled(dac.getDynamicAuthorizationEnabled());

		// dynamicAuthorizationPoA O
		dacEntity.setDynamicAuthorizationPoA(dac.getDynamicAuthorisationPoA());

		// dynamicAuthorizationLifetime O
		dacEntity.setDynamicAuthorizationLifetime(dac.getDynamicAuthorizationLifetime());

		// name
		String generatedId = generateId("", "");
		// set name if present and without any conflict
		if (dac.getName() != null) {
			if (!patterns.checkResourceName(dac.getName())) {
				throw new BadRequestException("Name provided is incorrect. Must be:" + patterns.ID_STRING);
			}
			dacEntity.setName(dac.getName());
		} else {
			dacEntity.setName(ShortName.DAC + "_" + generatedId);
		}

		// resourceId
		dacEntity
				.setResourceID("/" + Constants.CSE_ID + "/" + ShortName.DAC + Constants.PREFIX_SEPERATOR + generatedId);

		// hierarchical uri
		dacEntity.setHierarchicalURI(parentEntity.getHierarchicalURI() + "/" + dacEntity.getName());

		// resource type
		dacEntity.setResourceType(ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION);

		// parent
		switch (parentEntity.getResourceType().intValue()) {
		case ResourceType.CSE_BASE:
			dacEntity.setParentCseBase((CSEBaseEntity) parentEntity);
			break;
		case ResourceType.REMOTE_CSE:
			dacEntity.setParentRemoteCse((RemoteCSEEntity) parentEntity);
			break;
		case ResourceType.AE:
			dacEntity.setParentAe((AeEntity) parentEntity);
			break;
		default:
			// should not occurs
			throw new BadRequestException("invalid parent type");
		}

		// parentID
		dacEntity.setParentID(parentEntity.getResourceID());

		// accessControlPolicyIDs O
		if (!dac.getAccessControlPolicyIDs().isEmpty()) {
			dacEntity.setAccessControlPolicies(
					ControllerUtil.buildAcpEntityList(dac.getAccessControlPolicyIDs(), transaction));
		} else {
			dacEntity.setAccessControlPolicies(acpsToCheck);
		}

		// check uri does not exist
		if (!UriMapper.addNewUri(dacEntity.getHierarchicalURI(), dacEntity.getResourceID(),
				ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION)) {
			throw new ConflictException("Name already present in the parent collection.");
		}

		// create DynamicAuthorizationConsultation entity in database
		dbs.getDAOFactory().getDynamicAuthorizationDAO().create(transaction, dacEntity);

		// retrieve from database
		DynamicAuthorizationConsultationEntity dacFromDB = dbs.getDAOFactory().getDynamicAuthorizationDAO()
				.find(transaction, dacEntity.getResourceID());
		
		// update parent child dynamicAuthorizationList entity
		childDynamicAuthorizationConsultations.add(dacFromDB);

		// update parent
		dao.update(transaction, parentEntity);

		// update link with dynamicAuthorizationConsultation - DacEntity
		for(DynamicAuthorizationConsultationEntity dace : dacFromDB.getDynamicAuthorizationConsultations()) {
			DynamicAuthorizationConsultationEntity daceFromDB = dbs.getDAOFactory().getDynamicAuthorizationDAO().find(transaction, dace.getResourceID());
			daceFromDB.getLinkedDynamicAuthorizationConsultationEntity().add(dacFromDB);
			dbs.getDAOFactory().getDynamicAuthorizationDAO().update(transaction, daceFromDB);
		}
		
		// notify all subscribers if any
		Notifier.notify(subs, dacFromDB, ResourceStatus.CHILD_CREATED);

		// set response
		response.setResponseStatusCode(ResponseStatusCode.CREATED);

		// set location and content
		setLocationAndCreationContent(request, response, dacFromDB);

		return response;
	}

	@Override
	public ResponsePrimitive doRetrieve(RequestPrimitive request) {
		// create the response primitive
		ResponsePrimitive response = new ResponsePrimitive(request);

		// check resource existency
		DynamicAuthorizationConsultationEntity dacEntity = dbs.getDAOFactory().getDynamicAuthorizationDAO()
				.find(transaction, request.getTargetId());
		if (dacEntity == null) {
			throw new ResourceNotFoundException("Resource not found");
		}

		// check authorization
		List<AccessControlPolicyEntity> acpsToCheck = dacEntity.getAccessControlPolicies();
		checkACP(acpsToCheck, request.getFrom(), request.getOperation());

		// build response content
		DynamicAuthorizationConsultation dac = EntityMapperFactory.getDynamicAuthorizationConsultationMapper()
				.mapEntityToResource(dacEntity, request);

		// set content
		response.setContent(dac);

		// status code
		response.setResponseStatusCode(ResponseStatusCode.OK);

		return response;
	}

	@Override
	public ResponsePrimitive doUpdate(RequestPrimitive request) {
		// create the response primitive
		ResponsePrimitive response = new ResponsePrimitive(request);

		// check resource existency
		DynamicAuthorizationConsultationEntity dacEntity = dbs.getDAOFactory().getDynamicAuthorizationDAO()
				.find(transaction, request.getTargetId());
		if (dacEntity == null) {
			throw new ResourceNotFoundException("Resource not found");
		}

		// check authorization
		List<AccessControlPolicyEntity> acpsToCheck = dacEntity.getAccessControlPolicies();
		checkACP(acpsToCheck, request.getFrom(), request.getOperation());

		// lock current entity
		transaction.lock(dacEntity);

		// retrieve DynamicAuthorizationConsultation from request
		DynamicAuthorizationConsultation dac = null;
		try {
			if (request.getRequestContentType().equals(MimeMediaType.OBJ)) {
				dac = (DynamicAuthorizationConsultation) request.getContent();
			} else {
				dac = (DynamicAuthorizationConsultation) DataMapperSelector.getDataMapperList()
						.get(request.getRequestContentType()).stringToObj((String) request.getContent());
			}
		} catch (ClassCastException e) {
			throw new BadRequestException("Incorrect resource representation in content", e);
		}

		// if representation == null ==> throw BadRequest
		if (dac == null) {
			throw new BadRequestException("Error in provided content");
		}

		// modified Dac resource
		DynamicAuthorizationConsultation modifiedDAC = new DynamicAuthorizationConsultation();

		// Check attributes
		// @resourceName NP
		// resourceType NP
		// resourceID NP
		// parentID NP
		// creationTime NP
		// lastModifiedTime NP
		// expirationTime O
		// accessControlPolicyIds O
		// labels O
		// dynamicAuthorizationConsultationIDs O
		// accessControlPolicyIDs O

		// expirationTime O
		if (dac.getExpirationTime() != null) {
			dacEntity.setExpirationTime(dac.getExpirationTime());
			modifiedDAC.setExpirationTime(dac.getExpirationTime());
		}

		// accessControlPolicyIDs O
		if (!dac.getAccessControlPolicyIDs().isEmpty()) {
			dacEntity.getAccessControlPolicies().clear();
			dacEntity.setAccessControlPolicies(
					ControllerUtil.buildAcpEntityList(dac.getAccessControlPolicyIDs(), transaction));
			modifiedDAC.getAccessControlPolicyIDs().addAll(dac.getAccessControlPolicyIDs());
		}

		// labels O
		if (!dac.getLabels().isEmpty()) {
			dacEntity.getLabelsEntities().clear();
			dacEntity.setLabelsEntitiesFromSring(dac.getLabels());
			modifiedDAC.getLabels().addAll(dac.getLabels());
		}

		// dynamicAuthorizationConsultationIDs O
		if (!dac.getDynamicAuthorizationConsultationIDs().isEmpty()) {
			dacEntity.setDynamicAuthorizationConsultations(
					ControllerUtil.buildDacEntityList(dac.getDynamicAuthorizationConsultationIDs(), transaction));
			
			// update link with dynamicAuthorizationConsultation - DacEntity
			for(DynamicAuthorizationConsultationEntity dace : dacEntity.getDynamicAuthorizationConsultations()) {
				DynamicAuthorizationConsultationEntity daceFromDB = dbs.getDAOFactory().getDynamicAuthorizationDAO().find(transaction, dace.getResourceID());
				daceFromDB.getLinkedDynamicAuthorizationConsultationEntity().add(dacEntity);
				dbs.getDAOFactory().getDynamicAuthorizationDAO().update(transaction, daceFromDB);
			}
		}

		// dynamicAuthorizationEnabled O
		if (dac.getDynamicAuthorizationEnabled() != null) {
			dacEntity.setDynamicAuthorizationEnabled(dac.getDynamicAuthorizationEnabled());
			modifiedDAC.setDynamicAuthorizationEnabled(dac.getDynamicAuthorizationEnabled());
		}

		// dynamicAuthorizationPoA O
		if (!dac.getDynamicAuthorisationPoA().isEmpty()) {
			dacEntity.setDynamicAuthorizationPoA(dac.getDynamicAuthorisationPoA());
			modifiedDAC.setDynamicAuthorisationPoA(dac.getDynamicAuthorisationPoA());
		}

		// dynamicAuthorizationLifetime O
		if (dac.getDynamicAuthorizationLifetime() != null) {
			dacEntity.setDynamicAuthorizationLifetime(dac.getDynamicAuthorizationLifetime());
			modifiedDAC.setDynamicAuthorizationLifetime(dac.getDynamicAuthorizationLifetime());
		}

		// last modified time
		dacEntity.setLastModifiedTime(DateUtil.now());
		modifiedDAC.setLastModifiedTime(dacEntity.getLastModifiedTime());

		// update in database
		dbs.getDAOFactory().getDynamicAuthorizationDAO().update(transaction, dacEntity);
		
		// commit & release lock
		transaction.commit();

		// set content
		response.setContent(modifiedDAC);

		// set response status code
		response.setResponseStatusCode(ResponseStatusCode.UPDATED);

		return response;
	}

	@Override
	public ResponsePrimitive doDelete(RequestPrimitive request) {
		// create the response primitive
		ResponsePrimitive response = new ResponsePrimitive(request);

		// check resource existency
		DynamicAuthorizationConsultationEntity dacEntity = dbs.getDAOFactory().getDynamicAuthorizationDAO()
				.find(transaction, request.getTargetId());
		if (dacEntity == null) {
			throw new ResourceNotFoundException("Resource not found");
		}
		
		// lock entity
		transaction.lock(dacEntity);

		// check authorization
		List<AccessControlPolicyEntity> acpsToCheck = dacEntity.getAccessControlPolicies();
		checkACP(acpsToCheck, request.getFrom(), request.getOperation());

		// delete uri
		UriMapper.deleteUri(dacEntity.getHierarchicalURI());

		// delete in database
		dbs.getDAOFactory().getDynamicAuthorizationDAO().delete(transaction, dacEntity);

		// commit transaction & release lock
		transaction.commit();

		// set response status DELETED
		response.setResponseStatusCode(ResponseStatusCode.DELETED);

		return response;
	}

}
