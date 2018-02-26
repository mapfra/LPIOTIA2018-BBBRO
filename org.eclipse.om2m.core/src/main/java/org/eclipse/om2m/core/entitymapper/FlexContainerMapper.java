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
import java.util.Map;

import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResultContent;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.CustomAttributeEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.Subscription;
import org.eclipse.om2m.commons.resource.flexcontainerspec.FlexContainerFactory;
import org.eclipse.om2m.core.flexcontainer.FlexContainerSelector;
import org.eclipse.om2m.flexcontainer.service.FlexContainerService;

public class FlexContainerMapper extends EntityMapper<FlexContainerEntity, AbstractFlexContainer>{

	@Override
	protected AbstractFlexContainer createResource() {
		return new FlexContainer();
	}
	
	@Override
	protected AbstractFlexContainer createResource(FlexContainerEntity entity) {
		return FlexContainerFactory.getSpecializationFlexContainer(entity.getShortName());
	}

	@Override
	protected void mapAttributes(FlexContainerEntity entity, AbstractFlexContainer resource, int level, int offset) {
		if (level < 0) {
			return;
		}
		
		// announceableResource attributes
		EntityMapperFactory.getAnnounceableSubordonateEntity_AnnounceableResourceMapper().mapAttributes(entity, resource, level, offset);
		
		// flexContainer attributes
		resource.setCreator(entity.getCreator());
		resource.setOntologyRef(entity.getOntologyRef());
		resource.setStateTag(entity.getStateTag());
		resource.setContainerDefinition(entity.getContainerDefinition());
		resource.setNodeLink(entity.getNodeLink());
		
		resource.setLongName(entity.getLongName());
		resource.setShortName(entity.getShortName());
		
		FlexContainerService fcs = FlexContainerSelector
				.getFlexContainerService(entity.getResourceID());
		
		if (fcs == null) {
			for (CustomAttributeEntity cae : entity.getCustomAttributes()) {
				CustomAttribute ca = new CustomAttribute();
				ca.setCustomAttributeName(cae.getCustomAttributeName());
				ca.setCustomAttributeValue(cae.getCustomAttributeValue());
				resource.getCustomAttributes().add(ca);
			}
		} else {
			List<String> customAttributeNames = new ArrayList<String>();
			for (CustomAttributeEntity cae : entity.getCustomAttributes()) {
				customAttributeNames.add(cae.getCustomAttributeName());
			}
			for (Map.Entry<String, String> entry : fcs.getCustomAttributeValues(customAttributeNames).entrySet()) {
				CustomAttribute ca = new CustomAttribute();
				ca.setCustomAttributeName(entry.getKey());
				ca.setCustomAttributeValue(entry.getValue());
				resource.getCustomAttributes().add(ca);
			}
		}
	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(FlexContainerEntity entity, int level, int offset) {
		List<ChildResourceRef> childRefs = new ArrayList<>();
		if (level == 0) {
			return childRefs;
		}
		
		// add child ref FlexContainer
		for (FlexContainerEntity fcnt : entity.getChildFlexContainers()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(fcnt.getName());
			child.setType(ResourceType.FLEXCONTAINER);
			child.setValue(fcnt.getResourceID());
			child.setSpid(fcnt.getContainerDefinition());
			childRefs.add(child);
			childRefs.addAll(new FlexContainerMapper().getChildResourceRef(fcnt, level - 1, offset - 1));
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
		
		// add child ref with containers
		for (ContainerEntity childCont : entity.getChildContainers()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(childCont.getName());
			child.setType(ResourceType.CONTAINER);
			child.setValue(childCont.getResourceID());
			childRefs.add(child);
			childRefs.addAll(new ContainerMapper().getChildResourceRef(childCont, level - 1, offset - 1));
		}
		
		return childRefs;
	}

	@Override
	protected void mapChildResourceRef(FlexContainerEntity entity,
			AbstractFlexContainer resource, int level, int offset) {
		resource.getChildResource().addAll(getChildResourceRef(entity, level, offset));
	}

	@Override
	protected void mapChildResources(FlexContainerEntity entity, AbstractFlexContainer resource, int level, int offset) {
		if (level == 0) {
			return;
		}
		// add child ref flexContainer
		for (FlexContainerEntity cin : entity.getChildFlexContainers()) {
			AbstractFlexContainer flexContainerRes = new FlexContainerMapper().mapEntityToResource(cin, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getFlexContainerOrContainerOrSubscription().add(flexContainerRes);
		}

		// add child ref subscription
		for (SubscriptionEntity sub : entity.getSubscriptions()){
			Subscription subRes = new SubscriptionMapper().mapEntityToResource(sub, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getFlexContainerOrContainerOrSubscription().add(subRes);
		}
		
		// add child ref with containers
		for (ContainerEntity childCont : entity.getChildContainers()) {
			Container cnt = new ContainerMapper().mapEntityToResource(childCont, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getFlexContainerOrContainerOrSubscription().add(cnt);
		}
		
		resource.finalizeSerialization();
	}

}
