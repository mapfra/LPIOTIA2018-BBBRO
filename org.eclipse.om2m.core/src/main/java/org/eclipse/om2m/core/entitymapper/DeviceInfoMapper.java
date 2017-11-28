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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.entities.DeviceInfoEntity;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.DeviceInfo;

/**
 * Mapper for DeviceInfo
 *
 */
public class DeviceInfoMapper extends EntityMapper<DeviceInfoEntity, DeviceInfo> {

	@Override
	protected void mapAttributes(DeviceInfoEntity entity,
			DeviceInfo resource, int level, int offset) {
		if (level < 0) {
			return;
		}
		
		// Announceable resource attributes
		EntityMapperFactory.getAnnounceableSubordonateEntity_AnnounceableResourceMapper().mapAttributes(entity, resource, level, offset);
		
		// DeviceInfo attributes
		resource.setDescription(entity.getDescription());
		resource.setName(entity.getName());
		
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
	protected List<ChildResourceRef> getChildResourceRef(DeviceInfoEntity entity, int level, int offset) {
		return new ArrayList<>();
	}

	@Override
	protected void mapChildResourceRef(DeviceInfoEntity entity,
			DeviceInfo resource, int level, int offset) {
	}

	@Override
	protected void mapChildResources(DeviceInfoEntity entity,
			DeviceInfo resource, int level, int offset) {
	}

	@Override
	protected DeviceInfo createResource() {
		return new DeviceInfo();
	}

}
