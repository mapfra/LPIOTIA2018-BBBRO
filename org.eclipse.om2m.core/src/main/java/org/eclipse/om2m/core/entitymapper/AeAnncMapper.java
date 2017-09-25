/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.core.entitymapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResultContent;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.AEAnnc;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.Subscription;

public class AeAnncMapper extends EntityMapper<AeAnncEntity, AEAnnc> {

	@Override
	protected void mapAttributes(AeAnncEntity entity, AEAnnc resource, int level, int offset) {
		if (level < 0) {
			return;
		}
		
		// announcedResource attributes
		EntityMapperFactory.getAnnouncedResourceMapper().mapAttributes(entity, resource, level, offset);
		
		resource.setAEID(entity.getAeid());
		resource.setAppID(entity.getAppID());
		resource.setExpirationTime(entity.getExpirationTime());
		resource.setAppName(entity.getAppName());
		resource.setNodeLink(entity.getNodeLink());
		resource.setLink(entity.getLink());
		resource.setOntologyRef(entity.getOntologyRef());
		resource.getPointOfAccess().addAll(entity.getPointOfAccess());

	}

	@Override
	protected void mapChildResourceRef(AeAnncEntity entity, AEAnnc resource, int level, int offset) {
		resource.getChildResource().addAll(getChildResourceRef(entity, level, offset));
	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(AeAnncEntity entity, int level, int offset) {
		
		
		List<ChildResourceRef> childRefs = new ArrayList<>();
		
		if (level == 0) {
			return childRefs;
		}
		
		for (FlexContainerAnncEntity flexContainerEntity : entity.getFlexContainerAnncs()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(flexContainerEntity.getName());
			child.setType(flexContainerEntity.getResourceType());
			child.setValue(flexContainerEntity.getResourceID());
			child.setSpid(flexContainerEntity.getContainerDefinition());
			childRefs.add(child);
			
			childRefs.addAll(new FlexContainerAnncMapper().getChildResourceRef(flexContainerEntity, level - 1, offset - 1));
		}

		// ChildResourceRef Subscription
		for (SubscriptionEntity sub : entity.getSubscriptions()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(sub.getName());
			child.setType(BigInteger.valueOf(ResourceType.SUBSCRIPTION));
			child.setValue(sub.getResourceID());
			childRefs.add(child);
			
			childRefs.addAll(new SubscriptionMapper().getChildResourceRef(sub, level - 1, offset - 1));
		}
		
		return childRefs;
	}

	@Override
	protected void mapChildResources(AeAnncEntity entity, AEAnnc resource, int level, int offset) {

		if (level == 0) {
			return;
		}
		
		// ChildResourceRef FlexContainerAnnc
		for (FlexContainerAnncEntity flexContainerEntity : entity.getFlexContainerAnncs()) {
			AbstractFlexContainerAnnc fcnt = new FlexContainerAnncMapper().mapEntityToResource(flexContainerEntity,
					ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getContainerOrContainerAnncOrGroup().add(fcnt);
		}
		// ChildResourceRef Subscription
		for (SubscriptionEntity sub : entity.getSubscriptions()) {
			Subscription subRes = new SubscriptionMapper().mapEntityToResource(sub, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getContainerOrContainerAnncOrGroup().add(subRes);
		}
	}

	@Override
	protected AEAnnc createResource() {
		return new AEAnnc();
	}

}
