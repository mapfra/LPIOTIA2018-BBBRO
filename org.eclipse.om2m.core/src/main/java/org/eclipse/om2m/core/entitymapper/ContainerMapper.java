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

import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResultContent;
import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.ContentInstanceEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.Subscription;

public class ContainerMapper extends EntityMapper<ContainerEntity, Container>{

	@Override
	protected Container createResource() {
		return new Container();
	}

	@Override
	protected void mapAttributes(ContainerEntity entity, Container resource, int level, int offset) {
		if (level < 0) {
			return;
		}
		
		// announceable resource mapper
		EntityMapperFactory.getAnnounceableSubordonateEntity_AnnounceableResourceMapper().mapAttributes(entity, resource, level, offset);
		
		// Container attributes
		resource.setCreator(entity.getCreator());
		resource.setCurrentByteSize(BigInteger.valueOf(entity.getCurrentByteSize()));
		resource.setCurrentNrOfInstances(entity.getCurrentNrOfInstances());
		resource.setLocationID(entity.getLocationID());
		resource.setMaxByteSize(entity.getMaxByteSize());
		resource.setMaxInstanceAge(entity.getMaxInstanceAge());
		resource.setMaxNrOfInstances(entity.getMaxNrOfInstances());
		resource.setOntologyRef(entity.getOntologyRef());
		resource.setStateTag(entity.getStateTag());
		resource.setOldest(entity.getHierarchicalURI() + "/" + ShortName.OLDEST);
		resource.setLatest(entity.getHierarchicalURI() + "/" + ShortName.LATEST);
	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(ContainerEntity entity, int level, int offset) {
		List<ChildResourceRef> childRefs = new ArrayList<>();
		
		if (level == 0) {
			return childRefs;
		}
		
		// add child ref contentInstance
		for (ContentInstanceEntity cin : entity.getChildContentInstances()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(cin.getName());
			child.setType(ResourceType.CONTENT_INSTANCE);
			child.setValue(cin.getResourceID());
			childRefs.add(child);
			
			childRefs.addAll(new ContentInstanceMapper().getChildResourceRef(cin, level - 1, offset - 1));
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
		
		// add child ref FlexContainers
		for(FlexContainerEntity childFlexCont : entity.getChildFlexContainers()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(childFlexCont.getName());
			child.setType(ResourceType.FLEXCONTAINER);
			child.setValue(childFlexCont.getResourceID());
			childRefs.add(child);
			
			childRefs.addAll(new FlexContainerMapper().getChildResourceRef(childFlexCont, level - 1, offset - 1));
		}
		
		return childRefs;
	}

	@Override
	protected void mapChildResourceRef(ContainerEntity entity,
			Container resource, int level, int offset) {
		resource.getChildResource().addAll(getChildResourceRef(entity, level, offset));
	}

	@Override
	protected void mapChildResources(ContainerEntity entity, Container resource, int level, int offset) {
		// add child ref contentInstance
		for (ContentInstanceEntity cin : entity.getChildContentInstances()) {
			ContentInstance cinRes = new ContentInstanceMapper().mapEntityToResource(cin, ResultContent.ATTRIBUTES, level - 1, offset - 1);
			resource.getContentInstanceOrContainerOrSubscription().add(cinRes);
		}

		// add child ref subscription
		for (SubscriptionEntity sub : entity.getSubscriptions()){
			Subscription subRes = new SubscriptionMapper().mapEntityToResource(sub, ResultContent.ATTRIBUTES, level - 1, offset - 1);
			resource.getContentInstanceOrContainerOrSubscription().add(subRes);
		}
		
		
		// add child ref with containers
		for (ContainerEntity childCont : entity.getChildContainers()) {
			Container cnt = new ContainerMapper().mapEntityToResource(childCont, ResultContent.ATTRIBUTES, level - 1, offset - 1);
			resource.getContentInstanceOrContainerOrSubscription().add(cnt);
		}
		
		// add child ref flexContainers
		for(FlexContainerEntity childFlexCont : entity.getChildFlexContainers()) {
			AbstractFlexContainer fcnt = new FlexContainerMapper().mapEntityToResource(childFlexCont, ResultContent.ATTRIBUTES, level - 1, offset - 1);
			resource.getContentInstanceOrContainerOrSubscription().add(fcnt);
		}
	}

	
}
