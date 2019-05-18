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

import org.eclipse.om2m.commons.entities.ScheduleEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.Subscription;

public class SubscriptionMapper extends EntityMapper<SubscriptionEntity, Subscription> {

	@Override
	protected Subscription createResource() {
		return new Subscription();
	}

	@Override
	protected void mapAttributes(SubscriptionEntity subscriptionEntity, Subscription subscription, int level, int offset) {
		if (level < 0) {
			return;
		}
		
		// regular resources
		EntityMapperFactory.getRegularResourceMapper().mapAttributes(subscriptionEntity, subscription, level, offset);

		// subscription.setBatchNotify(value); // TODO
		subscription.setCreator(subscriptionEntity.getCreator());
		// subscription.setEventNotificationCriteria(value); // TODO
		subscription.setExpirationCounter(subscriptionEntity.getExpirationCounter());
		subscription.setExpirationTime(subscriptionEntity.getExpirationTime());
		subscription.setGroupID(subscriptionEntity.getGroupID());
		subscription.setLatestNotify(subscriptionEntity.getLatestNotify());
		subscription.setNotificationContentType(subscriptionEntity.getNotificationContentType());
		subscription.setNotificationEventCat(subscriptionEntity.getNotificationEventCat());
		subscription.setNotificationForwardingURI(subscriptionEntity.getNotificationForwardingURI());
		subscription.setNotificationStoragePriority(subscriptionEntity.getNotificationStoragePriority());
		subscription.setPendingNotification(subscriptionEntity.getPendingNotification());
		subscription.setPreSubscriptionNotify(subscriptionEntity.getPreSubscriptionNotify());
		// subscription.setRateLimit(value); // TODO
		// subscription.setSchedule(value); // TODO
		subscription.setSubscriberURI(subscriptionEntity.getSubscriberURI());

		subscription.getNotificationURI().addAll(subscriptionEntity.getNotificationURI());

	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(SubscriptionEntity entity, int level, int offset) {
		List<ChildResourceRef> childRefs = new ArrayList<>();
		if (level == 0) {
			return childRefs;
		}
		
		ScheduleEntity schE = entity.getChildSchedule();
		if (schE != null) {
			ChildResourceRef ch = new ChildResourceRef();
			ch.setResourceName(schE.getName());
			ch.setType(schE.getResourceType());
			ch.setValue(schE.getResourceID());
			childRefs.add(ch);
		}
		
		return childRefs;
	}

	@Override
	protected void mapChildResourceRef(SubscriptionEntity entity, Subscription resource, int level, int offset) {
		List<ChildResourceRef> childRefs = getChildResourceRef(entity, level, offset);
		if (!childRefs.isEmpty()) {
			resource.setChildResource(childRefs.get(0));
		}
	}

	@Override
	protected void mapChildResources(SubscriptionEntity entity, Subscription resource, int level, int offset) {
		ScheduleEntity schE = entity.getChildSchedule();
		if (schE != null) {
			// TODO add schedule child resource
		}
	}

}
