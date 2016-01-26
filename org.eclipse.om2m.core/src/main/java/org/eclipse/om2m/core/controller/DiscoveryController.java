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
import java.util.List;

import org.eclipse.om2m.commons.constants.DiscoveryResultType;
import org.eclipse.om2m.commons.constants.FilterUsage;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.ContentInstanceEntity;
import org.eclipse.om2m.commons.entities.GroupEntity;
import org.eclipse.om2m.commons.entities.LabelEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.entities.UriMapperEntity;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.exceptions.NotImplementedException;
import org.eclipse.om2m.commons.exceptions.ResourceNotFoundException;
import org.eclipse.om2m.commons.resource.DiscoveryResult;
import org.eclipse.om2m.commons.resource.DiscoveryResult.ResourceRef;
import org.eclipse.om2m.commons.resource.FilterCriteria;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.persistence.PersistenceService;
import org.eclipse.om2m.core.router.Patterns;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBService;
import org.eclipse.om2m.persistence.service.DBTransaction;

/**
 * Controller for Discovery operation
 *
 */
public class DiscoveryController extends Controller {

	@Override
	public ResponsePrimitive doCreate(RequestPrimitive request) {
		ResponsePrimitive response = new ResponsePrimitive(request);
		response.setResponseStatusCode(ResponseStatusCode.OPERATION_NOT_ALLOWED);
		return response;
	}

	@Override
	public ResponsePrimitive doRetrieve(RequestPrimitive request) {
		// Create the response
		ResponsePrimitive response = new ResponsePrimitive(request);

		//Get the database service
		DBService dbs = PersistenceService.getInstance().getDbService();
		DBTransaction transaction = dbs.getDbTransaction();
		transaction.open();

		// Get the DAO of the parent
		DAO<?> dao = (DAO<?>) Patterns.getDAO(request.getTargetId(), dbs);
		if (dao == null){
			throw new ResourceNotFoundException("Root resource not found for discovery");
		}

		ResourceEntity resourceEntity = (ResourceEntity) dao.find(transaction, request.getTargetId());

		List<AccessControlPolicyEntity> acpsToCheck = getAcpsFromEntity(resourceEntity);

		// Check access control policy of the originator
		if (resourceEntity.getResourceType().intValue() == (ResourceType.ACCESS_CONTROL_POLICY)){
			checkSelfACP(acpsToCheck.get(0), request.getFrom(), Operation.DISCOVERY);
		} else {
			checkACP(acpsToCheck, request.getFrom(), Operation.DISCOVERY);			
		}
		response = new ResponsePrimitive(request);

		// Get the filter criteria object from request primitive
		FilterCriteria filter = request.getFilterCriteria();

		if(filter.getFilterUsage().equals(FilterUsage.EVENT_NOTIFICATION_CRITERIA)){
			throw new NotImplementedException("Event notification criteria is not implemented");
		}

		if (filter.getFilterUsage().intValue() > FilterUsage.EVENT_NOTIFICATION_CRITERIA.intValue()){
			throw new BadRequestException("Incorrect FilterUsage value (fu)");
		}

		// Check the discovery result type
		if(request.getDiscoveryResultType() == null){
			request.setDiscoveryResultType(DiscoveryResultType.HIERARCHICAL);
		}		
		if (request.getDiscoveryResultType().equals(DiscoveryResultType.CSEID_AND_RESOURCEID)){
			throw new NotImplementedException();
		} else if (request.getDiscoveryResultType().intValue() > DiscoveryResultType.CSEID_AND_RESOURCEID.intValue()){
			throw new BadRequestException("Incorrect discovery result type provided");
		}

		List<UriMapperEntity> childUris = new ArrayList<>();
		if(!filter.getLabels().isEmpty()){
			for(String label : filter.getLabels()){
				LabelEntity labelEntity = dbs.getDAOFactory().getLabelDAO().find(transaction, label);
				if(labelEntity != null){
					LOGGER.info("Label found: " + label);
					List<ResourceEntity> allFoundResources = stackLabelResources(labelEntity, filter);
					LOGGER.info("after stack, number of resources: " + allFoundResources.size());

					int size = allFoundResources.size();
					if(filter.getLimit() != null && filter.getLimit().intValue() < size){
						size = filter.getLimit().intValue();
					}
					for(int i = 0 ; i < size ; i++){
						ResourceEntity resEntity = allFoundResources.get(i);
						if (resEntity.getHierarchicalURI().matches(resourceEntity.getHierarchicalURI() + "/?.*")) {
							UriMapperEntity uriEntity = new UriMapperEntity();
							uriEntity.setHierarchicalUri(resEntity.getHierarchicalURI());
							uriEntity.setNonHierarchicalUri(resEntity.getResourceID());
							uriEntity.setResourceType(resEntity.getResourceType());
							childUris.add(uriEntity);
						}
					}
				}
			}
		} else {
			// Get the list of UriMapperEntity from database with some filters
			childUris = dbs.getDBUtilManager().getComplexFindUtil().
					getChildUrisDis(request.getTargetId(), filter);
		}

		// Create the result object that will be serialized
		DiscoveryResult result = new DiscoveryResult();
		for (UriMapperEntity uriEntity : childUris){
			ResourceRef ref = new ResourceRef();
			ref.setResourceType(uriEntity.getResourceType());
			if(request.getDiscoveryResultType().equals(DiscoveryResultType.HIERARCHICAL)){
				ref.setUri(uriEntity.getHierarchicalUri());
			} else {
				ref.setUri(uriEntity.getNonHierarchicalUri());
			}
			result.getReferences().add(ref);
		}

		response.setContent(result);
		response.setResponseStatusCode(ResponseStatusCode.OK);
		return response;
	}

	@Override
	public ResponsePrimitive doUpdate(RequestPrimitive request) {
		ResponsePrimitive response = new ResponsePrimitive(request);
		response.setResponseStatusCode(ResponseStatusCode.OPERATION_NOT_ALLOWED);
		return response;
	}

	@Override
	public ResponsePrimitive doDelete(RequestPrimitive request) {
		ResponsePrimitive response = new ResponsePrimitive(request);
		response.setResponseStatusCode(ResponseStatusCode.OPERATION_NOT_ALLOWED);
		return response;
	}

	private List<AccessControlPolicyEntity> getAcpsFromEntity(
			ResourceEntity resourceEntity) {
		int ty = resourceEntity.getResourceType().intValue();
		switch(ty){		
		case ResourceType.ACCESS_CONTROL_POLICY:
			return Arrays.asList((AccessControlPolicyEntity) resourceEntity);
		case ResourceType.AE:
			return ((AeEntity) resourceEntity).getAccessControlPolicies();
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
			return ((SubscriptionEntity) resourceEntity).getAcpList();
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
			default:
				break;
			}
		} else {
			result.addAll(labelEntity.getLinkedAe());			
			result.addAll(labelEntity.getLinkedCin());			
			result.addAll(labelEntity.getLinkedCnt());
			result.addAll(labelEntity.getLinkedGroup());
			result.addAll(labelEntity.getLinkedCsr());
			result.addAll(labelEntity.getLinkedCsb());
		}
		return result;
	}

}
