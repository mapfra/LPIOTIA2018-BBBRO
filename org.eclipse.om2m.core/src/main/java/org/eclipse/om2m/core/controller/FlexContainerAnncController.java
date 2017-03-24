package org.eclipse.om2m.core.controller;

import java.util.List;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceStatus;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.constants.ResultContent;
import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.exceptions.ConflictException;
import org.eclipse.om2m.commons.exceptions.NotImplementedException;
import org.eclipse.om2m.commons.exceptions.NotPermittedAttrException;
import org.eclipse.om2m.commons.exceptions.Om2mException;
import org.eclipse.om2m.commons.exceptions.ResourceNotFoundException;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.FlexContainerAnnc;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.utils.Util.DateUtil;
import org.eclipse.om2m.core.announcer.Announcer;
import org.eclipse.om2m.core.datamapper.DataMapperSelector;
import org.eclipse.om2m.core.entitymapper.EntityMapperFactory;
import org.eclipse.om2m.core.flexcontainer.FlexContainerSelector;
import org.eclipse.om2m.core.flexcontainer.FlexContainerXMLValidator;
import org.eclipse.om2m.core.notifier.Notifier;
import org.eclipse.om2m.core.redirector.Redirector;
import org.eclipse.om2m.core.router.Patterns;
import org.eclipse.om2m.core.urimapper.UriMapper;
import org.eclipse.om2m.core.util.ControllerUtil;
import org.eclipse.om2m.flexcontainer.service.FlexContainerService;
import org.eclipse.om2m.persistence.service.DAO;

public class FlexContainerAnncController extends Controller {

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
		ResponsePrimitive response = new ResponsePrimitive(request);

		// get the dao of the parent
		DAO<ResourceEntity> dao = (DAO<ResourceEntity>) Patterns.getDAO(request.getTargetId(), dbs);
		if (dao == null) {
			throw new ResourceNotFoundException("Cannot find parent resource");
		}

		// get the parent entity
		ResourceEntity parentEntity = (ResourceEntity) dao.find(transaction, request.getTargetId());
		// check the parent existence
		if (parentEntity == null) {
			throw new ResourceNotFoundException("Cannot find parent resource");
		}
		
		// lock parent entity
		transaction.lock(parentEntity);

		// get lists to change in the method corresponding to specific object
		List<AccessControlPolicyEntity> acpsToCheck = null;
		List<FlexContainerAnncEntity> childFlexContainerAnncs = null;
		List<SubscriptionEntity> subscriptions = null;
		List<DynamicAuthorizationConsultationEntity> dacsToCheck = null;

		// different cases
		// case parent is AEAnnc
		if (parentEntity.getResourceType().intValue() == (ResourceType.AE_ANNC)) {
			AeAnncEntity aeAnnc = (AeAnncEntity) parentEntity;
			acpsToCheck = aeAnnc.getAccessControlPolicies();
			childFlexContainerAnncs = aeAnnc.getFlexContainerAnncs();
			subscriptions = aeAnnc.getSubscriptions();
			dacsToCheck = aeAnnc.getDynamicAuthorizationConsultations();
		}
		// case parent is a FlexContainerAnnc
		if (parentEntity.getResourceType().intValue() == (ResourceType.FLEXCONTAINER_ANNC)) {
			FlexContainerAnncEntity parentFlexContainer = (FlexContainerAnncEntity) parentEntity;
			acpsToCheck = parentFlexContainer.getAccessControlPolicies();
			childFlexContainerAnncs = parentFlexContainer.getChildFlexContainerAnncs();
			subscriptions = parentFlexContainer.getSubscriptions();
			dacsToCheck = parentFlexContainer.getDynamicAuthorizationConsultations();
		}

		// case parent is a RemoteCSE
		// TODO

		// check access control policy of the originator
		checkACP(acpsToCheck, request.getFrom(), Operation.CREATE);

		// check if content is present
		if (request.getContent() == null) {
			throw new BadRequestException("A content is requiered for FlexContainer creation");
		}
		// get the object from the representation
		FlexContainerAnnc flexContainerAnnc = null;
		try {
			if (request.getRequestContentType().equals(MimeMediaType.OBJ)) {
				flexContainerAnnc = (FlexContainerAnnc) request.getContent();
			} else {
				flexContainerAnnc = (FlexContainerAnnc) DataMapperSelector.getDataMapperList()
						.get(request.getRequestContentType()).stringToObj((String) request.getContent());
			}

		} catch (ClassCastException e) {
			e.printStackTrace();
			LOGGER.debug("ClassCastException: Incorrect resource type in object conversion.", e);
			throw new BadRequestException("Incorrect resource representation in content", e);
		}

		if (flexContainerAnnc == null) {
			throw new BadRequestException("Error in provided content");
		}

		// creating the corresponding entity
		FlexContainerAnncEntity flexContainerAnncEntity = new FlexContainerAnncEntity();
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

		ControllerUtil.CreateUtil.fillEntityFromGenericResource(flexContainerAnnc, flexContainerAnncEntity);

		if (flexContainerAnnc.getLink() == null) {
			throw new BadRequestException("Link is Mandatory");
		} else {
			flexContainerAnncEntity.setLink(flexContainerAnnc.getLink());
		}

		if (flexContainerAnnc.getExpirationTime() != null) {
			flexContainerAnncEntity.setExpirationTime(flexContainerAnncEntity.getExpirationTime());
		}

		// creator O
		if (flexContainerAnnc.getCreator() != null) {
			flexContainerAnncEntity.setCreator(flexContainerAnnc.getCreator());
		}

		// containerDefinition != null
		if ((flexContainerAnnc.getContainerDefinition() == null) || (flexContainerAnnc.getContainerDefinition().isEmpty())) {
			// the containerDefinition MUST be provided
			throw new NotPermittedAttrException("containerDefinition attribute must be provided.");
		} else {
			flexContainerAnncEntity.setContainerDefinition(flexContainerAnnc.getContainerDefinition());
		}

		// containerDefinition exists...
		// TODO

		String generatedId = generateId("", "");
		// set name if present and without any conflict
		if (request.getName() != null) {
			if (!Patterns.checkResourceName(request.getName())) {
				throw new BadRequestException("Name provided is incorrect. Must be:" + Patterns.ID_STRING);
			}
			flexContainerAnncEntity.setName(request.getName());
		} else {
			flexContainerAnncEntity.setName(ShortName.FCNTA + "_" + generatedId);
		}
		flexContainerAnncEntity.setResourceID(
				"/" + Constants.CSE_ID + "/" + ShortName.FCNTA + Constants.PREFIX_SEPERATOR + generatedId);
		flexContainerAnncEntity
				.setHierarchicalURI(parentEntity.getHierarchicalURI() + "/" + flexContainerAnncEntity.getName());
		flexContainerAnncEntity.setParentID(parentEntity.getResourceID());
		flexContainerAnncEntity.setResourceType(ResourceType.FLEXCONTAINER_ANNC);
		switch(parentEntity.getResourceType().intValue()) {
		case ResourceType.AE_ANNC:
			flexContainerAnncEntity.setParentAeAnnc((AeAnncEntity) parentEntity);
			break;
		case ResourceType.FLEXCONTAINER_ANNC:
			flexContainerAnncEntity.setParentFlexContainerAnnc((FlexContainerAnncEntity) parentEntity);
			break;
		}

		// accessControlPolicyIDs O
		if (!flexContainerAnnc.getAccessControlPolicyIDs().isEmpty()) {
			flexContainerAnncEntity.setAccessControlPolicies(
					ControllerUtil.buildAcpEntityList(flexContainerAnnc.getAccessControlPolicyIDs(), transaction));
		} else {
			flexContainerAnncEntity.getAccessControlPolicies().addAll(acpsToCheck);
		}
		
		// dynamicAuthorizationConsultationIDs O
		if (!flexContainerAnnc.getDynamicAuthorizationConsultationIDs().isEmpty()) {
			flexContainerAnncEntity.setDynamicAuthorizationConsultations(
					ControllerUtil.buildDacEntityList(flexContainerAnnc.getDynamicAuthorizationConsultationIDs(), transaction));
		} else {
			flexContainerAnncEntity.setDynamicAuthorizationConsultations(dacsToCheck);
		}

		if (!UriMapper.addNewUri(flexContainerAnncEntity.getHierarchicalURI(), flexContainerAnncEntity.getResourceID(),
				ResourceType.FLEXCONTAINER)) {
			throw new ConflictException("Name already present in the parent collection.");
		}

		// ontologyRef O
		if (flexContainerAnnc.getOntologyRef() != null) {
			flexContainerAnncEntity.setOntologyRef(flexContainerAnnc.getOntologyRef());
		}

		// custom attributes
		// LOGGER.debug("nb of customAttributes: "
		// + (flexContainer.getCustomAttributes() != null ?
		// flexContainer.getCustomAttributes().size() : "0"));
		// for (CustomAttribute ca : flexContainer.getCustomAttributes()) {
		//
		// flexContainerEntity.createOrUpdateCustomAttribute(ca.getCustomAttributeName(),
		// ca.getCustomAttributeType(),
		// ca.getCustomAttributeValue());
		// }

		// create the FlexContainer in the DB
		dbs.getDAOFactory().getFlexContainerAnncDAO().create(transaction, flexContainerAnncEntity);
		// retrieve the managed object from DB
		FlexContainerAnncEntity flexContainerFromDB = dbs.getDAOFactory().getFlexContainerAnncDAO().find(transaction,
				flexContainerAnncEntity.getResourceID());

		// add the container to the parentEntity child list
		childFlexContainerAnncs.add(flexContainerFromDB);
		dao.update(transaction, parentEntity);
		
		// update link with flexContainerAnncEntity - DacEntity
		for(DynamicAuthorizationConsultationEntity dace : flexContainerFromDB.getDynamicAuthorizationConsultations()) {
			DynamicAuthorizationConsultationEntity daceFromDB = dbs.getDAOFactory().getDynamicAuthorizationDAO().find(transaction, dace.getResourceID());
			daceFromDB.getLinkedFlexContainerAnncEntities().add(flexContainerFromDB);
			dbs.getDAOFactory().getDynamicAuthorizationDAO().update(transaction, daceFromDB);
		}
		
		// commit the transaction & unlock parent entity
		transaction.commit();

		Notifier.notify(subscriptions, flexContainerFromDB, ResourceStatus.CHILD_CREATED);

		// create the response
		response.setResponseStatusCode(ResponseStatusCode.CREATED);
		// set the location of the resource
		setLocationAndCreationContent(request, response, flexContainerFromDB);
		return response;
	}

	@Override
	public ResponsePrimitive doRetrieve(RequestPrimitive request) {
		// Creating the response primitive
		ResponsePrimitive response = new ResponsePrimitive(request);

		// Check existence of the resource
		FlexContainerAnncEntity flexContainerAnncEntity = dbs.getDAOFactory().getFlexContainerAnncDAO()
				.find(transaction, request.getTargetId());
		if (flexContainerAnncEntity == null) {
			throw new ResourceNotFoundException("Resource not found");
		}

		// if resource exists, check authorization
		// retrieve
		List<AccessControlPolicyEntity> acpList = flexContainerAnncEntity.getAccessControlPolicies();
		checkACP(acpList, request.getFrom(), request.getOperation());

		if (ResultContent.ORIGINAL_RES.equals(request.getResultContent())) {
			RequestPrimitive originalResourceRequest = new RequestPrimitive();
			originalResourceRequest.setOperation(Operation.RETRIEVE);
			originalResourceRequest.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
			originalResourceRequest.setTargetId(flexContainerAnncEntity.getLink());
			originalResourceRequest.setReturnContentType(MimeMediaType.OBJ);
			return Redirector.retarget(originalResourceRequest);
		} else {
			// Mapping the entity with the exchange resource
			FlexContainerAnnc flexContainerAnncResource = EntityMapperFactory.getFlexContainerAnncMapper()
					.mapEntityToResource(flexContainerAnncEntity, request);
			response.setContent(flexContainerAnncResource);
		}

		response.setResponseStatusCode(ResponseStatusCode.OK);
		// return the response
		return response;
	}

	@Override
	public ResponsePrimitive doUpdate(RequestPrimitive request) {

		// Check existence of the resource
		FlexContainerAnncEntity flexContainerAnncEntity = dbs.getDAOFactory().getFlexContainerAnncDAO()
				.find(transaction, request.getTargetId());
		if (flexContainerAnncEntity == null) {
			throw new ResourceNotFoundException("Resource not found");
		}
		
		// lock current entity
		transaction.lock(flexContainerAnncEntity);

		// if resource exists, check authorization
		// retrieve
		List<AccessControlPolicyEntity> acpList = flexContainerAnncEntity.getAccessControlPolicies();
		checkACP(acpList, request.getFrom(), request.getOperation());

		if (ResultContent.ORIGINAL_RES.equals(request.getResultContent())) {
			// commit transaction ==> release lock
			transaction.commit();
			
			RequestPrimitive originalResourceRequest = new RequestPrimitive();
			originalResourceRequest.setOperation(Operation.UPDATE);
			originalResourceRequest.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
			originalResourceRequest.setTargetId(flexContainerAnncEntity.getLink());
			originalResourceRequest.setRequestContentType(request.getRequestContentType());
			originalResourceRequest.setReturnContentType(MimeMediaType.OBJ);
			originalResourceRequest.setContent(request.getContent());
			return Redirector.retarget(originalResourceRequest);
		} else {
			// create the response base
			ResponsePrimitive response = new ResponsePrimitive(request);

			FlexContainerAnnc modifiedAttributes = new FlexContainerAnnc();
			// check if content is present
			if (request.getContent() != null) {
				// create the java object from the resource representation
				// get the object from the representation
				FlexContainerAnnc flexContainerAnnc = null;
				try {
					if (request.getRequestContentType().equals(MimeMediaType.OBJ)) {
						flexContainerAnnc = (FlexContainerAnnc) request.getContent();
					} else {
						flexContainerAnnc = (FlexContainerAnnc) DataMapperSelector.getDataMapperList()
								.get(request.getRequestContentType()).stringToObj((String) request.getContent());
					}


				} catch (ClassCastException e) {
					throw new BadRequestException("Incorrect resource representation in content", e);
				}
				if (flexContainerAnnc == null) {
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
				if (!flexContainerAnnc.getAccessControlPolicyIDs().isEmpty()) {
					flexContainerAnncEntity.getAccessControlPolicies().clear();
					flexContainerAnncEntity.setAccessControlPolicies(
							ControllerUtil.buildAcpEntityList(flexContainerAnnc.getAccessControlPolicyIDs(), transaction));
					modifiedAttributes.getAccessControlPolicyIDs().addAll(flexContainerAnnc.getAccessControlPolicyIDs());
				}
				// dynamicAuthorizationConsultationIDs O
				if (!flexContainerAnnc.getDynamicAuthorizationConsultationIDs().isEmpty()) {
					flexContainerAnncEntity.setDynamicAuthorizationConsultations(
							ControllerUtil.buildDacEntityList(flexContainerAnnc.getDynamicAuthorizationConsultationIDs(), transaction));
					
					// update link with flexContainerAnncEntity - DacEntity
					for(DynamicAuthorizationConsultationEntity dace : flexContainerAnncEntity.getDynamicAuthorizationConsultations()) {
						DynamicAuthorizationConsultationEntity daceFromDB = dbs.getDAOFactory().getDynamicAuthorizationDAO().find(transaction, dace.getResourceID());
						daceFromDB.getLinkedFlexContainerAnncEntities().add(flexContainerAnncEntity);
						dbs.getDAOFactory().getDynamicAuthorizationDAO().update(transaction, daceFromDB);
					}
				}
				// labels O
				if (!flexContainerAnnc.getLabels().isEmpty()) {
					flexContainerAnncEntity.setLabelsEntitiesFromSring(flexContainerAnnc.getLabels());
					modifiedAttributes.getLabels().addAll(flexContainerAnnc.getLabels());
				}
				// expirationTime O
				if (flexContainerAnnc.getExpirationTime() != null) {
					flexContainerAnncEntity.setExpirationTime(flexContainerAnnc.getExpirationTime());
					modifiedAttributes.setExpirationTime(flexContainerAnnc.getExpirationTime());
				}
				
				// ontologyRef O
				if (flexContainerAnnc.getOntologyRef() != null) {
					flexContainerAnncEntity.setOntologyRef(flexContainerAnnc.getOntologyRef());
					modifiedAttributes.setOntologyRef(flexContainerAnnc.getOntologyRef());
				}

				// containerDef
				if ((flexContainerAnnc.getContainerDefinition() != null)
						&& (!flexContainerAnncEntity.getContainerDefinition().equals(flexContainerAnnc.getContainerDefinition()))) {
					throw new BadRequestException("unable to change the containerDefinition value");
				}


			} else {
				// content might be null for FlexContainer representing a SDT action
			}

			flexContainerAnncEntity.setLastModifiedTime(DateUtil.now());
			modifiedAttributes.setLastModifiedTime(flexContainerAnncEntity.getLastModifiedTime());


			// at this point, we are sure there was no error when setting custom
			// attribute parameter

			response.setContent(modifiedAttributes);
			// update the resource in the database
			dbs.getDAOFactory().getFlexContainerAnncDAO().update(transaction, flexContainerAnncEntity);
			
			// commit transaction & unlock current entity
			transaction.commit();

			Notifier.notify(flexContainerAnncEntity.getSubscriptions(), flexContainerAnncEntity, modifiedAttributes,
					ResourceStatus.UPDATED);

			// set response status code
			response.setResponseStatusCode(ResponseStatusCode.UPDATED);
			return response;			
			
			
		}

	}

	@Override
	public ResponsePrimitive doDelete(RequestPrimitive request) {
		/*
		 * Generic delete procedure
		 */
		ResponsePrimitive response = new ResponsePrimitive(request);

		// Retrieve the resource from database
		FlexContainerAnncEntity flexContainerAnncEntity = dbs.getDAOFactory().getFlexContainerAnncDAO()
				.find(transaction, request.getTargetId());
		if (flexContainerAnncEntity == null) {
			throw new ResourceNotFoundException("Resource not found");
		}

		checkACP(flexContainerAnncEntity.getAccessControlPolicies(), request.getFrom(), Operation.DELETE);

		UriMapper.deleteUri(flexContainerAnncEntity.getHierarchicalURI());

		Notifier.notifyDeletion(flexContainerAnncEntity.getSubscriptions(), flexContainerAnncEntity);

		// Delete the resource
		dbs.getDAOFactory().getFlexContainerAnncDAO().delete(transaction, flexContainerAnncEntity);

		// Commit the transaction
		transaction.commit();

		// Return rsc
		response.setResponseStatusCode(ResponseStatusCode.DELETED);
		return response;
	}

}
