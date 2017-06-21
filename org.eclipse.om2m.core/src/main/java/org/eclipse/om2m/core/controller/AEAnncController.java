package org.eclipse.om2m.core.controller;

import java.math.BigInteger;
import java.util.List;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceStatus;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.constants.ResultContent;
import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.entities.AccessControlOriginatorEntity;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AccessControlRuleEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.AnnounceableSubordinateEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.exceptions.ConflictException;
import org.eclipse.om2m.commons.exceptions.NotImplementedException;
import org.eclipse.om2m.commons.exceptions.NotPermittedAttrException;
import org.eclipse.om2m.commons.exceptions.ResourceNotFoundException;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AEAnnc;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.utils.Util.DateUtil;
import org.eclipse.om2m.core.announcer.Announcer;
import org.eclipse.om2m.core.datamapper.DataMapperSelector;
import org.eclipse.om2m.core.entitymapper.EntityMapperFactory;
import org.eclipse.om2m.core.notifier.Notifier;
import org.eclipse.om2m.core.redirector.Redirector;
import org.eclipse.om2m.core.router.Patterns;
import org.eclipse.om2m.core.urimapper.UriMapper;
import org.eclipse.om2m.core.util.ControllerUtil;
import org.eclipse.om2m.core.util.ControllerUtil.UpdateUtil;
import org.eclipse.om2m.persistence.service.DAO;

public class AEAnncController extends Controller {

	@Override
	public ResponsePrimitive doCreate(RequestPrimitive request) {
		/*
		 * AE Creation procedure Req
		 * 
		 * @resourceName NP resourceType NP resourceID NP parentID NP
		 * accessControlPolicyIDs O creationTime NP expirationTime O
		 * lastModifiedTime NP labels O announceTo O announcedAttribute O
		 * appName O app-ID M ae-ID NP pointOfAccess O ontologyRef O nodeLink NP
		 */

		ResponsePrimitive response = new ResponsePrimitive(request);

		// Get the DAO of the parent
		DAO<ResourceEntity> dao = (DAO<ResourceEntity>) Patterns.getDAO(request.getTargetId(), dbs);
		if (dao == null) {
			throw new ResourceNotFoundException("Cannot find parent resource");
		}

		// Get the parent entity
		ResourceEntity parentEntity = (ResourceEntity) dao.find(transaction, request.getTargetId());
		// Check the parent existence
		if (parentEntity == null) {
			throw new ResourceNotFoundException("Cannot find parent resource");
		}
		
		// lock parent
		transaction.lock(parentEntity);

		// Get lists to change in the method corresponding to specific object
		List<AccessControlPolicyEntity> acpsToCheck = null;
		List<AeAnncEntity> childAnncs = null;
		List<SubscriptionEntity> subs = null;

		// Distinguish parents
		
		// Case of remoteCSE
		if (parentEntity.getResourceType().intValue() == (ResourceType.REMOTE_CSE)) {
			RemoteCSEEntity csr = (RemoteCSEEntity) parentEntity;
			acpsToCheck = csr.getAccessControlPolicies();
			childAnncs = csr.getChildAeAnncs();
			subs = csr.getSubscriptions();
		}
		// case of FlexContainer
		if (parentEntity.getResourceType().intValue() == (ResourceType.FLEXCONTAINER_ANNC)) {
//			FlexContainerEntity fce = (FlexContainerEntity) parentEntity;
//			acpsToCheck = fce.getAccessControlPolicies();
//			childAnncs = null;
//			subs = fce.getSubscriptions();
		}
		
		// case of AEAnnc
		if (parentEntity.getResourceType().intValue() == (ResourceType.AE_ANNC)) {
			AeAnncEntity aeAnncEntity = (AeAnncEntity) parentEntity;
			acpsToCheck = aeAnncEntity.getAccessControlPolicies();
			childAnncs = null;
			subs = aeAnncEntity.getSubscriptions();
		}
		
		// other cases ?
		
		// Check access control policy of the originator
//		checkACP(acpsToCheck, request.getFrom(), Operation.CREATE);
		checkPermissions(request, parentEntity, acpsToCheck);


		// Check if content is present
		if (request.getContent() == null) {
			throw new BadRequestException("A content is requiered for AEAnnc creation");
		}

		// Get the java object from the representation
		AEAnnc aeAnnc = null;
		try {
			if (request.getRequestContentType().equals(MimeMediaType.OBJ)) {
				aeAnnc = (AEAnnc) request.getContent();
			} else {
				aeAnnc = (AEAnnc) DataMapperSelector.getDataMapperList().get(request.getRequestContentType())
						.stringToObj((String) request.getContent());
			}
		} catch (ClassCastException e) {
			throw new BadRequestException("Incorrect resource representation in content", e);
		}
		if (aeAnnc == null) {
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
		// labels O
		// announceTo O
		// announcedAttribute O
		// Creating the corresponding entity
		AeAnncEntity aeAnncEntity = new AeAnncEntity();
		ControllerUtil.CreateUtil.fillEntityFromGenericResource(aeAnnc, aeAnncEntity);

		// ae-ID NP
		if (aeAnnc.getAEID() != null) {
			throw new NotPermittedAttrException("ae-id is Not Permitted");
		}
		// nodeLink NP
		if (aeAnnc.getNodeLink() != null) {
			aeAnncEntity.setNodeLink(aeAnnc.getNodeLink());
		}
		
		if (aeAnnc.getLink() == null) {
			throw new BadRequestException("Link is Mandatory");
		} else {
			aeAnncEntity.setLink(aeAnnc.getLink());
		}

		// app-ID M
		if (aeAnnc.getAppID() == null) {
			throw new BadRequestException("App ID is Mandatory");
		} else {
			aeAnncEntity.setAppID(aeAnnc.getAppID());
		}
//
//		// requestReachability M
//		if (ae.getRequestReachability() == null) {
//			throw new BadRequestException("Request Reachability is Mandatory");
//		} else {
//			aeEntity.setRequestReachability(ae.getRequestReachability());
//		}

		String generatedId = generateId();
		aeAnncEntity.setAeid(ShortName.AE_ANNC + Constants.PREFIX_SEPERATOR + generatedId);
		// Set other parameters
		aeAnncEntity.setResourceID("/" + Constants.CSE_ID + "/" + aeAnncEntity.getAeid());
		aeAnncEntity.setParentCsr((RemoteCSEEntity) parentEntity);
		
		if (dbs.getDAOFactory().getAeAnncDAO().find(transaction, aeAnncEntity.getResourceID()) != null) {
			throw new ConflictException("Already registered");
		}

		// accessControlPolicyIDs O
		if (!aeAnnc.getAccessControlPolicyIDs().isEmpty()) {
			aeAnncEntity.setAccessControlPolicies(
					ControllerUtil.buildAcpEntityList(aeAnnc.getAccessControlPolicyIDs(), transaction));
		} else {
			aeAnncEntity.getAccessControlPolicies().addAll(acpsToCheck);
		}
		
		// dynamicAuthorizationConsultationIDs O
		if (!aeAnnc.getDynamicAuthorizationConsultationIDs().isEmpty()) {
			aeAnncEntity.setDynamicAuthorizationConsultations(
					ControllerUtil.buildDacEntityList(aeAnnc.getDynamicAuthorizationConsultationIDs(), transaction));
		} 

		// FIXME [0001] Creation of AE with an acpi provided
		// } else {
		// Create the acp corresponding to the AE_ID
//		AccessControlPolicyEntity acpEntity = new AccessControlPolicyEntity();
//		acpEntity.setCreationTime(DateUtil.now());
//		acpEntity.setLastModifiedTime(DateUtil.now());
//		acpEntity.setParentID("/" + Constants.CSE_ID);
//		acpEntity.setResourceID(
//				"/" + Constants.CSE_ID + "/" + ShortName.ACP + Constants.PREFIX_SEPERATOR + generateId());
//		acpEntity.setName(ShortName.ACP + ShortName.AE + Constants.PREFIX_SEPERATOR + generatedId);
//		AccessControlRuleEntity ruleEntity = new AccessControlRuleEntity();
//		AccessControlOriginatorEntity originatorEntity = new AccessControlOriginatorEntity(
//				Constants.ADMIN_REQUESTING_ENTITY);
//		ruleEntity.getAccessControlOriginators().add(originatorEntity);
//		ruleEntity.setCreate(true);
//		ruleEntity.setRetrieve(true);
//		ruleEntity.setUpdate(true);
//		ruleEntity.setDelete(true);
//		ruleEntity.setNotify(true);
//		ruleEntity.setDiscovery(true);
//		acpEntity.getSelfPrivileges().add(ruleEntity);
//		// Privileges
//		ruleEntity = new AccessControlRuleEntity();
//		ruleEntity.setCreate(true);
//		ruleEntity.setRetrieve(true);
//		ruleEntity.setUpdate(true);
//		ruleEntity.setDelete(true);
//		ruleEntity.setNotify(true);
//		ruleEntity.setDiscovery(true);
//		ruleEntity.getAccessControlOriginators().add(new AccessControlOriginatorEntity(aeAnncEntity.getAeid()));
//		ruleEntity.getAccessControlOriginators()
//				.add(new AccessControlOriginatorEntity(Constants.ADMIN_REQUESTING_ENTITY));
//		acpEntity.getPrivileges().add(ruleEntity);
//		acpEntity.setHierarchicalURI("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + acpEntity.getName());
//		// Add the acp in the UriMapper table
//		UriMapper.addNewUri(acpEntity.getHierarchicalURI(), acpEntity.getResourceID(),
//				ResourceType.ACCESS_CONTROL_POLICY);
//		dbs.getDAOFactory().getAccessControlPolicyDAO().create(transaction, acpEntity);
		// Retrieve the acp in the database to make the link with the CSEBase
		// resource
//		AccessControlPolicyEntity acpDB = dbs.getDAOFactory().getAccessControlPolicyDAO().find(transaction,
//				acpEntity.getResourceID());
		
		
		
//		CSEBaseEntity cseBase = dbs.getDAOFactory().getCSEBaseDAO().find(transaction, "/" + Constants.CSE_ID);
//		cseBase.getChildAccessControlPolicies().add(acpDB);
//		dbs.getDAOFactory().getCSEBaseDAO().update(transaction, cseBase);
//		// adding new acp to the acp list
//		aeAnncEntity.getAccessControlPolicies().add(acpDB);
//		// direct link to the generated acp
////		aeAnncEntity.setGeneratedAcp(acpDB);
//		// }

		// appName O
		if (aeAnnc.getAppName() != null) {
			aeAnncEntity.setAppName(aeAnnc.getAppName());
		}
//		// pointOfAccess O
//		if (!ae.getPointOfAccess().isEmpty()) {
//			aeEntity.getPointOfAccess().addAll(ae.getPointOfAccess());
//		}
//		// ontologyRef O
//		if (ae.getOntologyRef() != null) {
//			aeEntity.setOntologyRef(ae.getOntologyRef());
//		}
		
		

		aeAnncEntity.setParentID(parentEntity.getResourceID());
		aeAnncEntity.setResourceType(BigInteger.valueOf(ResourceType.AE_ANNC));
		if (aeAnnc.getName() != null) {
			if (!Patterns.checkResourceName(aeAnnc.getName())) {
				throw new BadRequestException("Name provided is incorrect. Must be:" + Patterns.ID_STRING);
			}
			aeAnncEntity.setName(aeAnnc.getName());
		} else {
			aeAnncEntity.setName(ShortName.AE_ANNC + "_" + generatedId);
		}
		aeAnncEntity.setHierarchicalURI(parentEntity.getHierarchicalURI() + "/" + aeAnncEntity.getName());
		if (!UriMapper.addNewUri(aeAnncEntity.getHierarchicalURI(), aeAnncEntity.getResourceID(), ResourceType.AE_ANNC)) {
			throw new ConflictException("Name already present in the parent collection.");
		}

		
		// Create AE in database
		dbs.getDAOFactory().getAeAnncDAO().create(transaction, aeAnncEntity);

		// Get the managed object from db
		AeAnncEntity aeAnncDB = dbs.getDAOFactory().getAeAnncDAO().find(transaction, aeAnncEntity.getResourceID());

		// Add the AE to the parentEntity list
		childAnncs.add(aeAnncDB);
		dao.update(transaction, parentEntity);
		
		// update link with aeAnncEntity - DacEntity
		for(DynamicAuthorizationConsultationEntity dace : aeAnncDB.getDynamicAuthorizationConsultations()) {
			DynamicAuthorizationConsultationEntity daceFromDB = dbs.getDAOFactory().getDynamicAuthorizationDAO().find(transaction, dace.getResourceID());
			daceFromDB.getLinkedAeAnncEntities().add(aeAnncDB);
			dbs.getDAOFactory().getDynamicAuthorizationDAO().update(transaction, daceFromDB);
		}

		// Commit the DB transaction & release lock
		transaction.commit();

		// Create the response
		response.setResponseStatusCode(ResponseStatusCode.CREATED);
		// Set the location of the resource
		setLocationAndCreationContent(request, response, aeAnncDB);

		Notifier.notify(subs, aeAnncDB, ResourceStatus.CHILD_CREATED);

		return response;
	}

	@Override
	public ResponsePrimitive doRetrieve(RequestPrimitive request) {
		/*
		 * Generic retrieve procedure
		 */
		ResponsePrimitive response = new ResponsePrimitive(request);

		// Check existence of the resource
		AeAnncEntity aeAnncEntity = dbs.getDAOFactory()
				.getAeAnncDAO().find(transaction, request.getTargetId());
		if (aeAnncEntity == null){
			throw new ResourceNotFoundException("Resource not found");
		}

//		checkACP(aeAnncEntity.getAccessControlPolicies(), request.getFrom(), 
//				Operation.RETRIEVE);
		checkPermissions(request, aeAnncEntity, aeAnncEntity.getAccessControlPolicies());

		if (ResultContent.ORIGINAL_RES.equals(request.getResultContent())) {
			RequestPrimitive originalResourceRequest = new RequestPrimitive();
			originalResourceRequest.setOperation(Operation.RETRIEVE);
			originalResourceRequest.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
			originalResourceRequest.setTargetId(aeAnncEntity.getLink());
			originalResourceRequest.setReturnContentType(MimeMediaType.OBJ);
			return Redirector.retarget(originalResourceRequest );
		} else {
			// Create the object used to create the representation of the resource
			AEAnnc aeAnnc = EntityMapperFactory.getAEAnncMapper().mapEntityToResource(aeAnncEntity, request);
			response.setContent(aeAnnc);
		}

		

		response.setResponseStatusCode(ResponseStatusCode.OK);
		return response;
	}

	@Override
	public ResponsePrimitive doUpdate(RequestPrimitive request) {
		/*
		 * 							Req
		 * @resourceName			NP
		 * resourceType				NP
		 * resourceID				NP
		 * parentID					NP
		 * accessControlPolicyIDs	O
		 * creationTime				NP
		 * expirationTime			O
		 * lastModifiedTime			NP
		 * labels					NP
		 * announceTo				O
		 * announcedAttribute		O
		 * appName					O
		 * app-ID					NP
		 * ae-ID					NP
		 * pointOfAccess			O
		 * ontologyRef				O
		 * nodeLink					NP
		 * requestReachability		O
		 */
		ResponsePrimitive response = new ResponsePrimitive(request);

		// Retrieve the resource from database
		AeAnncEntity aeAnncEntity = dbs.getDAOFactory()
				.getAeAnncDAO().find(transaction, request.getTargetId());
		if (aeAnncEntity == null){
			throw new ResourceNotFoundException("Resource not found");
		}
		
		// lock current entity
		transaction.lock(aeAnncEntity);

//		checkACP(aeAnncEntity.getAccessControlPolicies(), request.getFrom(), 
//				Operation.UPDATE);
		checkPermissions(request, aeAnncEntity, aeAnncEntity.getAccessControlPolicies());


		// Check if content is present
		if (request.getContent() == null){
			throw new BadRequestException("A content is requiered for AE update");
		}

		// Create the java object from the resource representation
		AEAnnc aeAnnc = null;
		try{
			if (request.getRequestContentType().equals(MimeMediaType.OBJ)){
				aeAnnc = (AEAnnc) request.getContent();
			} else {
				aeAnnc = (AEAnnc)DataMapperSelector.getDataMapperList()
						.get(request.getRequestContentType()).stringToObj((String)request.getContent());
			}
		} catch (ClassCastException e){
			throw new BadRequestException("Incorrect resource representation in content", e);
		}
		if (aeAnnc == null){
			throw new BadRequestException("Error in provided content");
		}

		// Check attributes

		// NP Attributes are ignored
		// @resourceName			NP
		// resourceType				NP
		// resourceID				NP
		// parentID					NP
		// creationTime				NP
		// lastModifiedTime			NP
		UpdateUtil.checkNotPermittedParameters(aeAnnc);
		// app-ID					NP
		if(aeAnnc.getAppID() != null){
			throw new BadRequestException("AppID is NP");
		}
		// ae-ID					NP
		if(aeAnnc.getAEID() != null){
			throw new BadRequestException("AE ID is NP");
		}
		// nodeLink					NP
		if(aeAnnc.getNodeLink() != null){
			throw new BadRequestException("NodeLink is NP");
		}

		AE modifiedAttributes = new AE();
		// labels					O
		if(!aeAnnc.getLabels().isEmpty()){
			aeAnncEntity.setLabelsEntitiesFromSring(aeAnnc.getLabels());
			modifiedAttributes.getLabels().addAll(aeAnnc.getLabels());
		}
		// accessControlPolicyIDs	O
		if(!aeAnnc.getAccessControlPolicyIDs().isEmpty()){
			for(AccessControlPolicyEntity acpe : aeAnncEntity.getAccessControlPolicies()){
				checkSelfACP(acpe, request.getFrom(), Operation.UPDATE);
			}
			aeAnncEntity.getAccessControlPolicies().clear();
			aeAnncEntity.setAccessControlPolicies(ControllerUtil.buildAcpEntityList(aeAnnc.getAccessControlPolicyIDs(), transaction));
			modifiedAttributes.getAccessControlPolicyIDs().addAll(aeAnnc.getAccessControlPolicyIDs());
		}
		// dynamicAuthorizationConsultationIDs O
		if (!aeAnnc.getDynamicAuthorizationConsultationIDs().isEmpty()) {
			aeAnncEntity.setDynamicAuthorizationConsultations(
					ControllerUtil.buildDacEntityList(aeAnnc.getDynamicAuthorizationConsultationIDs(), transaction));
			
			// update link with aeAnncEntity - DacEntity
			for(DynamicAuthorizationConsultationEntity dace : aeAnncEntity.getDynamicAuthorizationConsultations()) {
				DynamicAuthorizationConsultationEntity daceFromDB = dbs.getDAOFactory().getDynamicAuthorizationDAO().find(transaction, dace.getResourceID());
				daceFromDB.getLinkedAeAnncEntities().add(aeAnncEntity);
				dbs.getDAOFactory().getDynamicAuthorizationDAO().update(transaction, daceFromDB);
			}
		}
		// expirationTime			O
		if (aeAnnc.getExpirationTime() != null){
			aeAnncEntity.setExpirationTime(aeAnnc.getExpirationTime());
			modifiedAttributes.setExpirationTime(aeAnnc.getExpirationTime());
		}
		// appName					O
		if(aeAnnc.getAppName() != null){
			aeAnncEntity.setAppName(aeAnnc.getAppName());
			modifiedAttributes.setAppName(aeAnnc.getAppName());
		}
		// pointOfAccess			O
		if(!aeAnnc.getPointOfAccess().isEmpty()){
			aeAnncEntity.getPointOfAccess().clear();
			aeAnncEntity.getPointOfAccess().addAll(aeAnnc.getPointOfAccess());
			modifiedAttributes.getPointOfAccess().addAll(aeAnnc.getPointOfAccess());
		}
		// ontologyRef				O
		if (aeAnnc.getOntologyRef() != null){
			aeAnncEntity.setOntologyRef(aeAnnc.getOntologyRef());
			modifiedAttributes.setOntologyRef(aeAnnc.getOntologyRef());
		}

		// Last Time Modified update
		aeAnncEntity.setLastModifiedTime(DateUtil.now());
		modifiedAttributes.setLastModifiedTime(aeAnncEntity.getLastModifiedTime());
		response.setContent(modifiedAttributes);
		// Update the resource in database
		dbs.getDAOFactory().getAeAnncDAO().update(transaction, aeAnncEntity);
		
		// commit transaction & release lock
		transaction.commit();

		Notifier.notify(aeAnncEntity.getSubscriptions(), aeAnncEntity, ResourceStatus.UPDATED);

		response.setResponseStatusCode(ResponseStatusCode.UPDATED);
		return response;
	}

	@Override
	public ResponsePrimitive doDelete(RequestPrimitive request) {
		/*
		 * Generic delete procedure
		 */
		ResponsePrimitive response = new ResponsePrimitive(request);

		// Retrieve the resource from database
		AeAnncEntity aeAnncEntity = dbs.getDAOFactory()
				.getAeAnncDAO().find(transaction, request.getTargetId());
		if (aeAnncEntity == null){
			throw new ResourceNotFoundException("Resource not found");
		}
		
		// lock entity
		transaction.lock(aeAnncEntity);

//		checkACP(aeAnncEntity.getAccessControlPolicies(), request.getFrom(), 
//				Operation.DELETE);
		checkPermissions(request, aeAnncEntity, aeAnncEntity.getAccessControlPolicies());

		UriMapper.deleteUri(aeAnncEntity.getHierarchicalURI());
		
		Notifier.notifyDeletion(aeAnncEntity.getSubscriptions(), aeAnncEntity);

		// Delete the resource
		dbs.getDAOFactory().getAeAnncDAO().delete(transaction, aeAnncEntity);

		// Commit the transaction & unlock
		transaction.commit();

		// Return rsc
		response.setResponseStatusCode(ResponseStatusCode.DELETED);
		return response;
	}

}
