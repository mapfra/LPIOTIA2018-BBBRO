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

import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResultContent;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.Subscription;
import org.eclipse.om2m.commons.utils.AcpUtils;

public class AcpMapper extends EntityMapper<AccessControlPolicyEntity, AccessControlPolicy> {
	
	// TODO Notifcation ACP doCreate
	// TODO Announce ACP doCreate

	@Override
	protected void mapAttributes(AccessControlPolicyEntity entity,
			AccessControlPolicy resource, int level, int offset) {
		if (level < 0) {
			return;
		}
		
		// announceableSubordinateResource attribute
		EntityMapperFactory.getAnnounceableSubordinateMapper().mapAttributes(entity, resource, level, offset);
		
		resource.setPrivileges(AcpUtils.getSetOfArcsFromACRE(entity
				.getPrivileges()));
		resource.setSelfPrivileges(AcpUtils.getSetOfArcsFromACRE(entity
				.getSelfPrivileges()));
	}

	@Override
	protected List<ChildResourceRef> getChildResourceRef(AccessControlPolicyEntity entity, int level, int offset) {
		
		List<ChildResourceRef> childRefs = new ArrayList<>();
		
		if (level == 0) {
			return childRefs;
		}
		
		for(SubscriptionEntity sub : entity.getChildSubscriptions()){
			ChildResourceRef child = new ChildResourceRef();
			child.setValue(sub.getResourceID());
			child.setType(ResourceType.SUBSCRIPTION);
			child.setResourceName(sub.getName());
			childRefs.add(child);

			childRefs.addAll(new SubscriptionMapper().getChildResourceRef(sub, level-1, offset-1));
		}
		
		return childRefs;
	}
	
	@Override
	protected void mapChildResourceRef(AccessControlPolicyEntity entity,
			AccessControlPolicy resource, int level, int offset) {
		resource.getChildResource().addAll(getChildResourceRef(entity, level, offset));
	}

	@Override
	protected void mapChildResources(AccessControlPolicyEntity entity,
			AccessControlPolicy resource, int level, int offset) {
		
		if (level == 0) {
			// reach limit
			return;
		}
		
		// add sub child resource
		for(SubscriptionEntity sub : entity.getChildSubscriptions()){
			Subscription subRes = new SubscriptionMapper().mapEntityToResource(sub, ResultContent.ATTRIBUTES, level-1, offset-1);
			resource.getSubscription().add(subRes);
		}
	}

	@Override
	protected AccessControlPolicy createResource() {
		return new AccessControlPolicy();
	}

	
	
}
