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

import org.eclipse.om2m.commons.entities.AreaNwkDeviceInfoEntity;
import org.eclipse.om2m.commons.resource.AreaNwkDeviceInfo;
import org.eclipse.om2m.commons.resource.ChildResourceRef;

/**
 * Mapper for AreaNwkDeviceInfo
 *
 */
public class AreaNwkDeviceInfoMapper extends EntityMapper<AreaNwkDeviceInfoEntity, AreaNwkDeviceInfo> {

	@Override
	protected void mapAttributes(AreaNwkDeviceInfoEntity entity,
			AreaNwkDeviceInfo resource, int level, int offset) {
		if (level < 0) {
			return;
		}
		
		// Announceable resource attributes
		EntityMapperFactory.getAnnounceableSubordonateEntity_AnnounceableResourceMapper().mapAttributes(entity, resource, level, offset);
		
		// AreaNwkDeviceInfo attributes
		resource.setAreaNwkId(entity.getAreaNwkId());
		resource.setDescription(entity.getDescription());
		resource.setDevID(entity.getDevID());
		resource.setMgmtDefinition(entity.getMgmtDefinition());
		resource.setName(entity.getName());
		resource.setSleepDuration(entity.getSleepDuration());
		resource.setSleepInterval(entity.getSleepInterval());
		resource.setStatus(entity.getStatus());
	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(AreaNwkDeviceInfoEntity entity, int level, int offset) {
		return new ArrayList<>();
	}

	@Override
	protected void mapChildResourceRef(AreaNwkDeviceInfoEntity entity,
			AreaNwkDeviceInfo resource, int level, int offset) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void mapChildResources(AreaNwkDeviceInfoEntity entity,
			AreaNwkDeviceInfo resource, int level, int offset) {
		// TODO Auto-generated method stub

	}

	@Override
	protected AreaNwkDeviceInfo createResource() {
		return new AreaNwkDeviceInfo();
	}



}
