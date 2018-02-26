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
package org.eclipse.om2m.core.entitymapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.constants.MgmtDefinitionTypes;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResultContent;
import org.eclipse.om2m.commons.entities.AreaNwkDeviceInfoAnncEntity;
import org.eclipse.om2m.commons.entities.AreaNwkInfoAnncEntity;
import org.eclipse.om2m.commons.entities.DeviceInfoAnncEntity;
import org.eclipse.om2m.commons.entities.MgmtObjAnncEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.AnnouncedMgmtResource;
import org.eclipse.om2m.commons.resource.AreaNwkDeviceInfoAnnc;
import org.eclipse.om2m.commons.resource.AreaNwkInfoAnnc;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.DeviceInfoAnnc;
import org.eclipse.om2m.commons.resource.MgmtObjAnncWithChildren;
import org.eclipse.om2m.commons.resource.Subscription;

public class MgmtObjAnncMapper extends EntityMapper<MgmtObjAnncEntity, AnnouncedMgmtResource> {

//	private static Log LOGGER = LogFactory.getLog(MgmtObjAnncMapper.class);

	@Override
	protected void mapAttributes(MgmtObjAnncEntity entity, AnnouncedMgmtResource resource, int level, int offset) {
		if (level < 0) {
			return;
		}
		
		// Announceable resource attributes
		EntityMapperFactory.getAnnouncedResourceMapper().mapAttributes(entity,
				resource, level, offset);

		resource.setCreationTime(entity.getCreationTime());
		resource.setDescription(entity.getDescription());
		resource.setExpirationTime(entity.getExpirationTime());
		resource.setLastModifiedTime(entity.getLastModifiedTime());
		resource.setName(entity.getName());
		resource.setParentID(entity.getParentID());
		resource.setResourceID(entity.getResourceID());
		resource.setResourceType(entity.getResourceType());
		
		BigInteger mgd = entity.getMgmtDefinition();
		if (mgd.equals(MgmtDefinitionTypes.AREA_NWK_INFO)) {
			mapSpecificAttributes((AreaNwkInfoAnncEntity)entity, (AreaNwkInfoAnnc)resource);
		} else if (mgd.equals(MgmtDefinitionTypes.AREA_NWK_DEVICE_INFO)) {
			mapSpecificAttributes((AreaNwkDeviceInfoAnncEntity)entity, (AreaNwkDeviceInfoAnnc)resource);
		} else if (mgd.equals(MgmtDefinitionTypes.DEVICE_INFO)) {
			mapSpecificAttributes((DeviceInfoAnncEntity)entity, (DeviceInfoAnnc)resource);
		}
	}
	
	private void mapSpecificAttributes(AreaNwkInfoAnncEntity entity, AreaNwkInfoAnnc resource) {
		resource.setAreaNwkType(entity.getAreaNwkType());
		if (!entity.getListOfDevices().isEmpty()) {
			resource.getListOfDevices().addAll(entity.getListOfDevices());
		}
	}
	
	private void mapSpecificAttributes(AreaNwkDeviceInfoAnncEntity entity, AreaNwkDeviceInfoAnnc resource) {
		resource.setAreaNwkId(entity.getAreaNwkId());
		resource.setDevID(entity.getDevID());
		resource.setSleepDuration(entity.getSleepDuration());
		resource.setSleepInterval(entity.getSleepInterval());
		resource.setStatus(entity.getStatus());
	}
	
	private void mapSpecificAttributes(DeviceInfoAnncEntity entity, DeviceInfoAnnc resource) {
		resource.setDeviceLabel(entity.getDeviceLabel());
		resource.setModel(entity.getModel());
		resource.setManufacturer(entity.getManufacturer());
		resource.setDeviceType(entity.getDeviceType());
		
		resource.setDeviceName(entity.getDeviceName());
		resource.setFwVersion(entity.getFwVersion());
		resource.setSwVersion(entity.getSwVersion());
		resource.setHwVersion(entity.getHwVersion());
		resource.setOsVersion(entity.getOsVersion());
		resource.setManufacturerDetailsLink(entity.getManufacturerDetailsLink());
		resource.setManufacturingDate(entity.getManufacturingDate());
		resource.setSubModel(entity.getSubModel());
		resource.setCountry(entity.getCountry());
		resource.setLocation(entity.getLocation());
		resource.setSystemTime(entity.getSystemTime());
		resource.setSupportURL(entity.getSupportURL());
		resource.setPresentationURL(entity.getPresentationURL());
		resource.setProtocol(entity.getProtocol());
	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(MgmtObjAnncEntity entity, int level, int offset) {
		List<ChildResourceRef> childRefs = new ArrayList<>();
		if (level == 0) {
			return childRefs;
		}
		// add child ref subscription
		for (SubscriptionEntity sub : entity.getSubscriptions()){
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(sub.getName());
			child.setType(ResourceType.SUBSCRIPTION);
			child.setValue(sub.getResourceID());
			childRefs.add(child);
			childRefs.addAll(new SubscriptionMapper().getChildResourceRef(sub, level - 1, offset - 1));
		}
		return childRefs;
	}

	@Override
	protected void mapChildResourceRef(MgmtObjAnncEntity entity, AnnouncedMgmtResource resource, int level, int offset) {
		((MgmtObjAnncWithChildren)resource).getChildResource().addAll(getChildResourceRef(entity, level, offset));
	}

	@Override
	protected void mapChildResources(MgmtObjAnncEntity entity, AnnouncedMgmtResource resource, int level, int offset) {
		// add child ref subscription
		for (SubscriptionEntity sub : entity.getSubscriptions()){
			Subscription subRes = new SubscriptionMapper().mapEntityToResource(sub, ResultContent.ATTRIBUTES, level - 1, offset - 1);
			((MgmtObjAnncWithChildren)resource).getSubscriptions().add(subRes);
		}
	}

	@Override
	protected AnnouncedMgmtResource createResource(MgmtObjAnncEntity entity) {
		BigInteger mgd = entity.getMgmtDefinition();
		if (mgd.equals(MgmtDefinitionTypes.AREA_NWK_INFO)) {
			return new AreaNwkInfoAnnc();
		} 
		if (mgd.equals(MgmtDefinitionTypes.AREA_NWK_DEVICE_INFO)) {
			return new AreaNwkDeviceInfoAnnc();
		} 
		if (mgd.equals(MgmtDefinitionTypes.DEVICE_INFO)) {
			return new DeviceInfoAnnc();
		}
		return null;
	}

	protected AnnouncedMgmtResource createResource() {
		return null;
	}

}
