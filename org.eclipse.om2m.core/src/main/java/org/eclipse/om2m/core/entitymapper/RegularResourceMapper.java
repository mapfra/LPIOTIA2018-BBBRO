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
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.RegularResourceEntity;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.RegularResource;


public class RegularResourceMapper extends EntityMapper<RegularResourceEntity, RegularResource> {

	@Override
	protected void mapAttributes(RegularResourceEntity entity, RegularResource resource, int level, int offset) {
		
		if (level < 0) {
			return;
		}
		
		// expiration time
		resource.setExpirationTime(entity.getExpirationTime());

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
	protected List<ChildResourceRef> getChildResourceRef(RegularResourceEntity entity, int level, int offset) {
		return new ArrayList<>();
	}

	@Override
	protected void mapChildResourceRef(RegularResourceEntity entity, RegularResource resource, int level, int offset) {

	}

	@Override
	protected void mapChildResources(RegularResourceEntity entity, RegularResource resource, int level, int offset) {

	}

	@Override
	protected RegularResource createResource() {
		return null;
	}

}
