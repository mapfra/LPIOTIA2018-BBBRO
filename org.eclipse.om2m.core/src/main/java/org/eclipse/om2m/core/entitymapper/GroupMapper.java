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

import org.eclipse.om2m.commons.constants.ResultContent;
import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.entities.GroupEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.Group;
import org.eclipse.om2m.commons.resource.Subscription;

public class GroupMapper extends EntityMapper<GroupEntity, Group>{

	@Override
	protected Group createResource() {
		return new Group();
	}

	@Override
	protected void mapAttributes(GroupEntity entity, Group resource, int level, int offset) {
		if (level < 0) {
			return;
		}
		
		// announceable resource attributes
		EntityMapperFactory.getAnnounceableSubordonateEntity_AnnounceableResourceMapper().mapAttributes(entity, resource, level, offset);
		
		// group attributes
		resource.setConsistencyStrategy(entity.getConsistencyStrategy());
		resource.setCreator(entity.getCreator());
		resource.setCurrentNrOfMembers(BigInteger.valueOf(entity.getMemberIDs().size()));
		resource.setFanOutPoint(entity.getHierarchicalURI() + "/" + ShortName.FANOUTPOINT);
		resource.setGroupName(resource.getGroupName());
		resource.setMaxNrOfMembers(entity.getMaxNrOfMembers());
		resource.setMemberType(entity.getMemberType());
		resource.setMemberTypeValidated(entity.isMemberTypeValidated());
		if(!entity.getMemberIDs().isEmpty()){
			resource.getMemberIDs().addAll(entity.getMemberIDs());
		}
		if(!entity.getMemberAcpIds().isEmpty()){
			resource.getMembersAccessControlPolicyIDs().addAll(entity.getMemberAcpIds());
		}
	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(GroupEntity entity, int level, int offset) {
		List<ChildResourceRef> childRefs = new ArrayList<>();
		
		if (level == 0) {
			return childRefs;
		}
		
		// ChildResourceRef Subscription
		for(SubscriptionEntity sub : entity.getSubscriptions()){
			ChildResourceRef ref = new ChildResourceRef();
			ref.setResourceName(sub.getName());
			ref.setType(sub.getResourceType());
			ref.setValue(sub.getResourceID());
			childRefs.add(ref);
			childRefs.addAll(new SubscriptionMapper().getChildResourceRef(sub, level - 1, offset - 1));
		}
		
		return childRefs;
	}

	@Override
	protected void mapChildResourceRef(GroupEntity entity, Group resource, int level, int offset) {
		resource.getChildResource().addAll(getChildResourceRef(entity, level, offset));
	}

	@Override
	protected void mapChildResources(GroupEntity entity, Group resource, int level, int offset) {
		if (level == 0) {
			return;
		}
		for(SubscriptionEntity sub : entity.getSubscriptions()){
			Subscription subRes = new SubscriptionMapper().mapEntityToResource(sub, ResultContent.ATTRIBUTES, level - 1, offset - 1);
			resource.getSubscription().add(subRes);
		}
	}
	
	

}
