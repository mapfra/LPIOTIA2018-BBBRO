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

import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResultContent;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.Subscription;
import org.eclipse.om2m.commons.resource.flexcontainerspec.FlexContainerFactory;

public class FlexContainerAnncMapper extends EntityMapper<FlexContainerAnncEntity, AbstractFlexContainerAnnc>{

	@Override
	protected AbstractFlexContainerAnnc createResource() {
		return new AbstractFlexContainerAnnc();
	}
	
	@Override
	protected AbstractFlexContainerAnnc createResource(FlexContainerAnncEntity flexContainerAnncEntity) {
		return FlexContainerFactory.getSpecializationFlexContainerAnnc(flexContainerAnncEntity.getShortName());
	}

	@Override
	protected void mapAttributes(FlexContainerAnncEntity entity, AbstractFlexContainerAnnc resource, int level, int offset) {
		if (level < 0) {
			return;
		}
		// announcedResource attribute
		EntityMapperFactory.getAnnouncedResourceMapper().mapAttributes(entity, resource, level, offset);
		
		// flexContainerAnnc attribute
		resource.setCreator(entity.getCreator());
		resource.setOntologyRef(entity.getOntologyRef());
		resource.setStateTag(entity.getStateTag());
		resource.setContainerDefinition(entity.getContainerDefinition());
		resource.setNodeLink(entity.getNodeLink());
	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(FlexContainerAnncEntity entity, int level, int offset) {
		List<ChildResourceRef> childRefs = new ArrayList<>();
		if (level == 0) {
			return childRefs;
		}
		
		// add child ref FlexContainer
		for (FlexContainerAnncEntity fcntAnnc : entity.getChildFlexContainerAnncs()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(fcntAnnc.getName());
			child.setType(ResourceType.FLEXCONTAINER_ANNC);
			child.setValue(fcntAnnc.getResourceID());
			child.setSpid(fcntAnnc.getContainerDefinition());
			childRefs.add(child);
			
			childRefs.addAll(new FlexContainerAnncMapper().getChildResourceRef(fcntAnnc, level - 1, offset - 1));
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
	protected void mapChildResourceRef(FlexContainerAnncEntity entity,
			AbstractFlexContainerAnnc resource, int level, int offset) {
		resource.getChildResource().addAll(getChildResourceRef(entity, level, offset));
	}

	@Override
	protected void mapChildResources(FlexContainerAnncEntity entity, AbstractFlexContainerAnnc resource, int level, int offset) {
		if (level == 0) {
			return;
		}
		
		// add child ref flexContainer
		for (FlexContainerAnncEntity flexContainerAnncEntity : entity.getChildFlexContainerAnncs()) {
			AbstractFlexContainerAnnc flexContainerAnncRes = new FlexContainerAnncMapper().mapEntityToResource(flexContainerAnncEntity, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getFlexContainerOrContainerOrSubscription().add(flexContainerAnncRes);
		}

		// add child ref subscription
		for (SubscriptionEntity sub : entity.getSubscriptions()){
			Subscription subRes = new SubscriptionMapper().mapEntityToResource(sub, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getFlexContainerOrContainerOrSubscription().add(subRes);
		}
		
		// add child ref with containers
		
		resource.finalizeSerialization();
	}
	
}
