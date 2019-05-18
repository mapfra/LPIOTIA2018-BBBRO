/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.core.entitymapper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AnnouncedResourceEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.resource.AnnouncedResource;
import org.eclipse.om2m.commons.resource.ChildResourceRef;

/**
 * @author MPCY8647
 *
 */
public class AnnouncedResourceMapper extends EntityMapper<AnnouncedResourceEntity, AnnouncedResource> {

	@Override
	protected void mapAttributes(AnnouncedResourceEntity entity, AnnouncedResource resource, int level, int offset) {
		if (level < 0) {
			return;
		}
		
		// expiration time
		resource.setExpirationTime(entity.getExpirationTime());
		
		// link
		resource.setLink(entity.getLink());
		
		// access control policy ids 
		for (AccessControlPolicyEntity acpe : entity.getAccessControlPolicies()) {
			resource.getAccessControlPolicyIDs().add(acpe.getResourceID());
		}
		
		// dynamic authorization consultation ids
		List<String> dacis = resource.getDynamicAuthorizationConsultationIDs();
		for (DynamicAuthorizationConsultationEntity dace : entity.getDynamicAuthorizationConsultations()) {
			dacis.add(dace.getResourceID());
		}
		
	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(AnnouncedResourceEntity entity, int level, int offset) {
		return new ArrayList<>();
	}

	@Override
	protected void mapChildResourceRef(AnnouncedResourceEntity entity, AnnouncedResource resource, int level, int offset) {
	}

	@Override
	protected void mapChildResources(AnnouncedResourceEntity entity, AnnouncedResource resource, int level, int offset) {
	}

	@Override
	protected AnnouncedResource createResource() {
		return null;
	}

}
