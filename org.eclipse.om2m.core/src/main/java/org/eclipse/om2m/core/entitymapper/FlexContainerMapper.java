/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.core.entitymapper;

import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResultContent;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.CustomAttributeEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.Subscription;
import org.eclipse.om2m.commons.resource.flexcontainerspec.FlexContainerFactory;

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
	protected void mapAttributes(FlexContainerEntity entity, AbstractFlexContainer resource) {
		
		// announceableResource attributes
		EntityMapperFactory.getAnnounceableSubordonateEntity_AnnounceableResourceMapper().mapAttributes(entity, resource);
		
		// flexContainer attributes
		resource.setCreator(entity.getCreator());
		resource.setOntologyRef(entity.getOntologyRef());
		resource.setStateTag(entity.getStateTag());
		resource.setContainerDefinition(entity.getContainerDefinition());
		
		resource.setLongName(entity.getLongName());
		resource.setShortName(entity.getShortName());
		
		for(CustomAttributeEntity cae : entity.getCustomAttributes()) {
			CustomAttribute ca = new CustomAttribute();
			ca.setCustomAttributeName(cae.getCustomAttributeName());
			ca.setCustomAttributeType(cae.getCustomAttributeType());
			ca.setCustomAttributeValue(cae.getCustomAttributeValue());
			resource.getCustomAttributes().add(ca);
		}
	}

	@Override
	protected void mapChildResourceRef(FlexContainerEntity entity,
			AbstractFlexContainer resource) {

		// add child ref FlexContainer
		for (FlexContainerEntity fcnt : entity.getChildFlexContainers()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(fcnt.getName());
			child.setType(ResourceType.FLEXCONTAINER);
			child.setValue(fcnt.getResourceID());
			child.setSpid(fcnt.getContainerDefinition());
			resource.getChildResource().add(child);	
		}

		// add child ref subscription
		for (SubscriptionEntity sub : entity.getSubscriptions()){
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(sub.getName());
			child.setType(ResourceType.SUBSCRIPTION);
			child.setValue(sub.getResourceID());
			resource.getChildResource().add(child);
		}
		
		
		// add child ref with containers
		for (ContainerEntity childCont : entity.getChildContainers()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(childCont.getName());
			child.setType(ResourceType.CONTAINER);
			child.setValue(childCont.getResourceID());
			resource.getChildResource().add(child);
		}
	}

	@Override
	protected void mapChildResources(FlexContainerEntity entity, AbstractFlexContainer resource) {
		// add child ref flexContainer
		for (FlexContainerEntity cin : entity.getChildFlexContainers()) {
			AbstractFlexContainer flexContainerRes = new FlexContainerMapper().mapEntityToResource(cin, ResultContent.ATTRIBUTES);
			resource.getFlexContainerOrContainerOrSubscription().add(flexContainerRes);
		}

		// add child ref subscription
		for (SubscriptionEntity sub : entity.getSubscriptions()){
			Subscription subRes = new SubscriptionMapper().mapEntityToResource(sub, ResultContent.ATTRIBUTES);
			resource.getFlexContainerOrContainerOrSubscription().add(subRes);
		}
		
		
		// add child ref with containers
		for (ContainerEntity childCont : entity.getChildContainers()) {
			Container cnt = new ContainerMapper().mapEntityToResource(childCont, ResultContent.ATTRIBUTES);
			resource.getFlexContainerOrContainerOrSubscription().add(cnt);
		}
	}

	
}
