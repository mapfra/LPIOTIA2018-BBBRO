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


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.eclipse.om2m.commons.constants.DiscoveryResultType;
import org.eclipse.om2m.commons.constants.FilterUsage;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.ContentInstanceEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.GroupEntity;
import org.eclipse.om2m.commons.entities.LabelEntity;
import org.eclipse.om2m.commons.entities.MgmtObjAnncEntity;
import org.eclipse.om2m.commons.entities.MgmtObjEntity;
import org.eclipse.om2m.commons.entities.NodeAnncEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.entities.UriMapperEntity;
import org.eclipse.om2m.commons.exceptions.AccessDeniedException;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.exceptions.NotImplementedException;
import org.eclipse.om2m.commons.exceptions.OperationNotAllowed;
import org.eclipse.om2m.commons.exceptions.ResourceNotFoundException;
import org.eclipse.om2m.commons.resource.FilterCriteria;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.URIList;
import org.eclipse.om2m.core.router.Patterns;
import org.eclipse.om2m.persistence.service.DAO;

/**
 * Controller for Discovery operation
 *
 */
public class DiscoveryController extends Controller {

	@Override
	public ResponsePrimitive doCreate(RequestPrimitive request) {
		throw new OperationNotAllowed("Delete of Discovery is not allowed");
	}

	@Override
	public ResponsePrimitive doRetrieve(RequestPrimitive request) {
		// Create the response
		ResponsePrimitive response = new ResponsePrimitive(request);
		Patterns patterns = new Patterns();

		// Get the DAO of the parent
		DAO<?> dao = (DAO<?>) patterns.getDAO(request.getTo(), dbs);
		if (dao == null){
			throw new ResourceNotFoundException("Root resource not found for discovery");
		}

		ResourceEntity resourceEntity = (ResourceEntity) dao.find(transaction, request.getTo());

//		List<AccessControlPolicyEntity> acpsToCheck = getAcpsFromEntity(resourceEntity);
//
//		// Check access control policy of the originator
//		if (resourceEntity.getResourceType().intValue() == (ResourceType.ACCESS_CONTROL_POLICY)){
//			checkSelfACP(acpsToCheck.get(0), request.getFrom(), Operation.DISCOVERY);
//		} else {
//			checkACP(acpsToCheck, request.getFrom(), Operation.DISCOVERY);			
//		}
		response = new ResponsePrimitive(request);

		// Get the filter criteria object from request primitive
		FilterCriteria filter = request.getFilterCriteria();

		if (filter.getFilterUsage().equals(FilterUsage.EVENT_NOTIFICATION_CRITERIA)){
			throw new NotImplementedException("Event notification criteria is not implemented");
		}

		if (filter.getFilterUsage().intValue() > FilterUsage.EVENT_NOTIFICATION_CRITERIA.intValue()){
			throw new BadRequestException("Incorrect FilterUsage value (fu)");
		}

		// Check the discovery result type
		if (request.getDiscoveryResultType() == null){
			request.setDiscoveryResultType(DiscoveryResultType.HIERARCHICAL);
		}		
		if (request.getDiscoveryResultType().equals(DiscoveryResultType.CSEID_AND_RESOURCEID)){
			throw new NotImplementedException();
		} else if (request.getDiscoveryResultType().intValue() > DiscoveryResultType.CSEID_AND_RESOURCEID.intValue()){
			throw new BadRequestException("Incorrect discovery result type provided");
		}

		List<UriMapperEntity> childUris = new ArrayList<>();
		if (!filter.getLabels().isEmpty()) {
			HashSet<UriMapperEntity> allFoundUriEntities = new HashSet<>();
			int limit = (filter.getLimit() != null ? filter.getLimit().intValue() : -1);
			for (int indexLabel = 0; indexLabel < filter.getLabels().size(); indexLabel++) {
				String label = filter.getLabels().get(indexLabel);
				LabelEntity labelEntity = dbs.getDAOFactory().getLabelDAO().find(transaction, label);

				if (labelEntity != null) {
					List<ResourceEntity> allFoundResources = stackLabelResources(labelEntity, filter);
					for (ResourceEntity resEntity : allFoundResources) {
						UriMapperEntity uriEntity = new UriMapperEntity();
						uriEntity.setHierarchicalUri(resEntity.getHierarchicalURI());
						uriEntity.setNonHierarchicalUri(resEntity.getResourceID());
						uriEntity.setResourceType(resEntity.getResourceType());
						allFoundUriEntities.add(uriEntity);
						// stop in case limit filter is reached
						if (limit != -1 && allFoundUriEntities.size() == limit) {
							break;
						}
					}
				}
			}
			childUris.addAll(allFoundUriEntities);
		} else {
			// Get the list of UriMapperEntity from database with some filters
			childUris = dbs.getDBUtilManager().getComplexFindUtil().
					getChildUrisDis(request.getTo(), filter);
		}
		
		URIList uriList = new URIList();
		// need to call getListofUri in order to create at least an empty list
		uriList.getListOfUri();
		for(UriMapperEntity uriEntity : childUris){
			if(filter.getLimit() != null && uriList.getListOfUri().size() == filter.getLimit().intValue()){
				break;
			}
			
			// check acp
			DAO<?> currentDao = (DAO<?>) patterns.getDAO(uriEntity.getNonHierarchicalUri(), dbs);
			ResourceEntity currentResourceEntity = (ResourceEntity) currentDao.find(transaction, uriEntity.getNonHierarchicalUri());
			if (currentResourceEntity != null) {
				List<AccessControlPolicyEntity> acps = getAcpsFromEntity(currentResourceEntity);
				try {
					checkPermissions(request, currentResourceEntity, acps);
//					checkACP(acps, request.getFrom(), Operation.DISCOVERY);
					if(request.getDiscoveryResultType().equals(DiscoveryResultType.HIERARCHICAL)){
						if(!uriList.getListOfUri().contains(uriEntity.getHierarchicalUri())){
							uriList.getListOfUri().add(uriEntity.getHierarchicalUri());
						}
					} else {
						if(!uriList.getListOfUri().contains(uriEntity.getNonHierarchicalUri())){
							uriList.getListOfUri().add(uriEntity.getNonHierarchicalUri());
						}
					}
				} catch (AccessDeniedException e) {
					// nothing to do
				}
			}
		}

		response.setContent(uriList);
		response.setResponseStatusCode(ResponseStatusCode.OK);
		return response;
	}

	@Override
	public ResponsePrimitive doUpdate(RequestPrimitive request) {
		throw new OperationNotAllowed("Update of Discovery is not allowed");
	}

	@Override
	public ResponsePrimitive doDelete(RequestPrimitive request) {
		throw new OperationNotAllowed("Delete of Discovery is not allowed");
	}

	private List<AccessControlPolicyEntity> getAcpsFromEntity(
			ResourceEntity resourceEntity) {
		int ty = resourceEntity.getResourceType().intValue();
		switch(ty){		
		case ResourceType.ACCESS_CONTROL_POLICY:
			return Arrays.asList((AccessControlPolicyEntity) resourceEntity);
		case ResourceType.AE:
			return ((AeEntity) resourceEntity).getAccessControlPolicies();
		case ResourceType.AE_ANNC:
			return ((AeAnncEntity) resourceEntity).getAccessControlPolicies();
		case ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION:
			return ((DynamicAuthorizationConsultationEntity) resourceEntity).getAccessControlPolicies();
		case ResourceType.CONTAINER:
			return ((ContainerEntity) resourceEntity).getAccessControlPolicies();		
		case ResourceType.CONTENT_INSTANCE:
			return ((ContentInstanceEntity) resourceEntity).getAcpListFromParent();		
		case ResourceType.GROUP:
			return ((GroupEntity) resourceEntity).getAccessControlPolicies();	
		case ResourceType.REMOTE_CSE:
			return ((RemoteCSEEntity) resourceEntity).getAccessControlPolicies();		
		case ResourceType.CSE_BASE:
			return ((CSEBaseEntity) resourceEntity).getAccessControlPolicies();
		case ResourceType.SUBSCRIPTION:
			return ((SubscriptionEntity) resourceEntity).getAccessControlPolicies();
		case ResourceType.FLEXCONTAINER:
			return ((FlexContainerEntity) resourceEntity).getAccessControlPolicies();
		case ResourceType.FLEXCONTAINER_ANNC:
			return ((FlexContainerAnncEntity) resourceEntity).getAccessControlPolicies();
		case ResourceType.NODE:
			return ((NodeEntity) resourceEntity).getAccessControlPolicies();
		case ResourceType.NODE_ANNC:
			return ((NodeAnncEntity) resourceEntity).getAccessControlPolicies();
		case ResourceType.MGMT_OBJ:
			return ((MgmtObjEntity)resourceEntity).getAccessControlPolicies();
		case ResourceType.MGMT_OBJ_ANNC:
			return ((MgmtObjAnncEntity)resourceEntity).getAccessControlPolicies();
		default:
			// TODO On implementing resource, add the reference here
			return null;
		}
	}

	private List<ResourceEntity> stackLabelResources(LabelEntity labelEntity, FilterCriteria filter) {
		List<ResourceEntity> result = new ArrayList<>();
		BigInteger rty = filter.getResourceType();
		if (rty != null){
			switch (rty.intValue()) {
			case ResourceType.AE:
				result.addAll(labelEntity.getLinkedAe());
				break;
			case ResourceType.AE_ANNC:
				result.addAll(labelEntity.getLinkedAeA());
				break;
			case(ResourceType.CONTENT_INSTANCE):
				result.addAll(labelEntity.getLinkedCin());	
				break;
			case(ResourceType.CONTAINER):
				result.addAll(labelEntity.getLinkedCnt());	
				break;
			case(ResourceType.GROUP):
				result.addAll(labelEntity.getLinkedGroup());
				break;
			case(ResourceType.REMOTE_CSE):
				result.addAll(labelEntity.getLinkedCsr());
				break;
			case(ResourceType.CSE_BASE):
				result.addAll(labelEntity.getLinkedCsb());
				break;
			case (ResourceType.FLEXCONTAINER):
				result.addAll(labelEntity.getLinkedFcnt());
				break;
			case (ResourceType.FLEXCONTAINER_ANNC):
				result.addAll(labelEntity.getLinkedFcntA());
				break;
			case (ResourceType.ACCESS_CONTROL_POLICY):
				result.addAll(labelEntity.getLinkedACP());
				break;
			case(ResourceType.NODE): 
				result.addAll(labelEntity.getLinkedNodes());
				break;
			case(ResourceType.NODE_ANNC): 
				result.addAll(labelEntity.getLinkedNodesA());
				break;
			case(ResourceType.MGMT_OBJ): 
				result.addAll(labelEntity.getLinkedAni());
				result.addAll(labelEntity.getLinkedAndi());
				result.addAll(labelEntity.getLinkedDvi());
				break;
			case(ResourceType.MGMT_OBJ_ANNC): 
				result.addAll(labelEntity.getLinkedAniA());
				result.addAll(labelEntity.getLinkedAndiA());
				result.addAll(labelEntity.getLinkedDviA());
				break;
			case(ResourceType.SUBSCRIPTION):
				result.addAll(labelEntity.getLinkedSub());
				break;
			default:
				break;
			}
		} else {
			result.addAll(labelEntity.getLinkedAe());	
			result.addAll(labelEntity.getLinkedAeA());	
			result.addAll(labelEntity.getLinkedCin());			
			result.addAll(labelEntity.getLinkedCnt());
			result.addAll(labelEntity.getLinkedGroup());
			result.addAll(labelEntity.getLinkedCsr());
			result.addAll(labelEntity.getLinkedCsb());
			result.addAll(labelEntity.getLinkedFcnt());
			result.addAll(labelEntity.getLinkedFcntA());
			result.addAll(labelEntity.getLinkedACP());
			result.addAll(labelEntity.getLinkedNodes());
			result.addAll(labelEntity.getLinkedNodesA());
			result.addAll(labelEntity.getLinkedAni());
			result.addAll(labelEntity.getLinkedAniA());
			result.addAll(labelEntity.getLinkedAndi());
			result.addAll(labelEntity.getLinkedAndiA());
			result.addAll(labelEntity.getLinkedDvi());
			result.addAll(labelEntity.getLinkedDviA());
			result.addAll(labelEntity.getLinkedSub());
		}
		return result;
	}

}
