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
package org.eclipse.om2m.core.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AnnounceableSubordinateEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.exceptions.NotPermittedAttrException;
import org.eclipse.om2m.commons.exceptions.Om2mException;
import org.eclipse.om2m.commons.exceptions.ResourceNotFoundException;
import org.eclipse.om2m.commons.resource.AnnounceableResource;
import org.eclipse.om2m.commons.resource.Resource;
import org.eclipse.om2m.commons.utils.Util.DateUtil;
import org.eclipse.om2m.core.persistence.PersistenceService;
import org.eclipse.om2m.core.urimapper.UriMapper;
import org.eclipse.om2m.persistence.service.DBService;
import org.eclipse.om2m.persistence.service.DBTransaction;
/**
 * Util class that handle computation done in controllers
 *
 */
public class ControllerUtil {
	
	private ControllerUtil(){}

	/**
	 * Build the list of ACP Entity from the db.
	 * @param acpUriList
	 * @param transaction
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public static List<AccessControlPolicyEntity> buildAcpEntityList(
			List<String> acpUriList, DBTransaction transaction)
					throws ResourceNotFoundException {

		// Get the database service
	
		DBService dbs = PersistenceService.getInstance().getDbService();
		// Create the response  list
		List<AccessControlPolicyEntity> response = new ArrayList<>();	
		String acpHierarchical = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + Constants.ADMIN_PROFILE_ID;
		String acpAdminResourceId = UriMapper.getNonHierarchicalUri(acpHierarchical);
		AccessControlPolicyEntity acpAdminDB = dbs.getDAOFactory().getAccessControlPolicyDAO().find(transaction,acpAdminResourceId );
		response.add(acpAdminDB);
		
		
		
		// For each id provided, check the existence of the resource
		if(acpUriList!=null){
			for (String acpId : acpUriList) {
					AccessControlPolicyEntity acpEntity = dbs.getDAOFactory()
							.getAccessControlPolicyDAO().find(transaction, acpId);
					if (acpEntity == null) {
						throw new Om2mException("AccessControlPolicy Id ["
								+ acpId + "] is not found", ResponseStatusCode.BAD_REQUEST);
					}
					if(!response.contains(acpEntity)){
						response.add(acpEntity);
					}		
			}
		}
		return response;
	}
	
	public static List<DynamicAuthorizationConsultationEntity> buildDacEntityList(
			List<String> dacIds, DBTransaction transaction) 
					throws ResourceNotFoundException{
		// Get the database service
		DBService dbs = PersistenceService.getInstance().getDbService();
		
		// create response list
		List<DynamicAuthorizationConsultationEntity> daces = new ArrayList<>();
		for(String dacId : dacIds) {
			String dbId = UriMapper.getNonHierarchicalUri(dacId);
			if (dbId == null) {
				throw new ResourceNotFoundException("DynamicAuthorizationConsultation Id [" 
						+ dacId + "] is not found");
			}
			DynamicAuthorizationConsultationEntity dace = dbs.getDAOFactory().
					getDynamicAuthorizationDAO().find(transaction, dbId);
			if (dace == null) {
				throw new ResourceNotFoundException("DynamicAuthorizationConsultation Id [" 
						+ dacId + "] is not found_");
			}
			daces.add(dace);
		}
		
		return daces;
	}

	/**
	 * Util methods in create case
	 */
	public static class CreateUtil{
		
		private CreateUtil(){}
		
		public static void fillEntityFromGenericResource(Resource resource, ResourceEntity entity) 
				throws NotPermittedAttrException{
			if(resource.getResourceType() != null){
				throw new NotPermittedAttrException("ResourceType is Not Permitted");
			}
			if(resource.getResourceID() != null){
				throw new NotPermittedAttrException("ResourceID is Not Permitted");
			}
			if(resource.getParentID() != null){
				throw new NotPermittedAttrException("ParentID is Not Permitted");
			}
			if(resource.getCreationTime() != null){
				throw new NotPermittedAttrException("Creation time is Not Permitted");
			}
			entity.setCreationTime(DateUtil.now());
			if(resource.getLastModifiedTime() != null){
				throw new NotPermittedAttrException("LastTimeModified is Not Permitted");
			}
			entity.setLastModifiedTime(DateUtil.now());
			if(!resource.getLabels().isEmpty()){
				entity.setLabelsEntitiesFromSring(resource.getLabels());
			}
			
		}

		public static void fillEntityFromAnnounceableResource(AnnounceableResource resource, AnnounceableSubordinateEntity entity) 
				throws NotPermittedAttrException{
			fillEntityFromGenericResource(resource, entity);
			if(!resource.getAnnouncedAttribute().isEmpty()){
				entity.getAnnouncedAttribute().addAll(resource.getAnnouncedAttribute());
			}
			if(!resource.getAnnounceTo().isEmpty()){
				entity.getAnnounceTo().addAll(resource.getAnnounceTo());
			}
			if(resource.getExpirationTime() != null){
				entity.setExpirationTime(resource.getExpirationTime());
			} else {
				entity.setExpirationTime(DateUtil.getDefaultExpirationTime());
			}
		}
	}
	
	public static class UpdateUtil{
		
		private UpdateUtil(){}
		
		public static void checkNotPermittedParameters(Resource resource){
			if(resource.getName() != null){
				throw new BadRequestException("ResourceName is NP");
			}
			if(resource.getResourceType() != null){
				throw new BadRequestException("ResourceType is NP");
			}
			if(resource.getResourceID() != null){
				throw new BadRequestException("ResourceID is NP");
			}
			if(resource.getParentID() != null){
				throw new BadRequestException("ParentID is NP");
			}
			if(resource.getCreationTime() != null){
				throw new BadRequestException("CreationTime is NP");
			}
			if(resource.getLastModifiedTime() != null){
				throw new BadRequestException("LastModifiedTime is NP");
			}
		}
		
	}


}
