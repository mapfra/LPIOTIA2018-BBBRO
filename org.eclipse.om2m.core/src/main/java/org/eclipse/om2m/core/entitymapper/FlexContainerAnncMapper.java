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
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.FlexContainerAnnc;
import org.eclipse.om2m.commons.resource.Subscription;

public class FlexContainerAnncMapper extends EntityMapper<FlexContainerAnncEntity, FlexContainerAnnc>{

	@Override
	protected FlexContainerAnnc createResource() {
		return new FlexContainerAnnc();
	}

	@Override
	protected void mapAttributes(FlexContainerAnncEntity entity, FlexContainerAnnc resource) {
		
		resource.setCreator(entity.getCreator());
		resource.setOntologyRef(entity.getOntologyRef());
		resource.setStateTag(entity.getStateTag());
		resource.setExpirationTime(entity.getExpirationTime());
		resource.setContainerDefinition(entity.getContainerDefinition());
		resource.setLink(entity.getLink());
		resource.setExpirationTime(entity.getExpirationTime());
		// setting acpIds
		for (AccessControlPolicyEntity acp : entity.getAccessControlPolicies()) {
			resource.getAccessControlPolicyIDs().add(acp.getResourceID());
		}
		
	}

	@Override
	protected void mapChildResourceRef(FlexContainerAnncEntity entity,
			FlexContainerAnnc resource) {

		// add child ref FlexContainer
		for (FlexContainerAnncEntity fcntAnnc : entity.getChildFlexContainerAnncs()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(fcntAnnc.getName());
			child.setType(ResourceType.FLEXCONTAINER_ANNC);
			child.setValue(fcntAnnc.getResourceID());
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
	}

	@Override
	protected void mapChildResources(FlexContainerAnncEntity entity, FlexContainerAnnc resource) {
		// add child ref flexContainer
		for (FlexContainerAnncEntity flexContainerAnncEntity : entity.getChildFlexContainerAnncs()) {
			FlexContainerAnnc flexContainerAnncRes = new FlexContainerAnncMapper().mapEntityToResource(flexContainerAnncEntity, ResultContent.ATTRIBUTES);
			resource.getFlexContainerOrContainerOrSubscription().add(flexContainerAnncRes);
		}

		// add child ref subscription
		for (SubscriptionEntity sub : entity.getSubscriptions()){
			Subscription subRes = new SubscriptionMapper().mapEntityToResource(sub, ResultContent.ATTRIBUTES);
			resource.getFlexContainerOrContainerOrSubscription().add(subRes);
		}
		
		
		// add child ref with containers
	}

	
}
