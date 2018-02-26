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
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.GroupEntity;
import org.eclipse.om2m.commons.entities.PollingChannelEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.Group;
import org.eclipse.om2m.commons.resource.PollingChannel;
import org.eclipse.om2m.commons.resource.Subscription;

public class AeMapper extends EntityMapper<AeEntity, AE> {

	@Override
	protected AE createResource() {
		return new AE();
	}

	@Override
	protected void mapAttributes(AeEntity entity, AE resource, int level, int offset) {
		
		if (level < 0) {
			return;
		}
		
		// AnnounceableResource attributes
		EntityMapperFactory.getAnnounceableSubordonateEntity_AnnounceableResourceMapper().mapAttributes(entity, resource, level, offset);
		
		// Ae attributes
		resource.setAEID(entity.getAeid());
		resource.setAppID(entity.getAppID());
		resource.setAppName(entity.getAppName());
		resource.setNodeLink(entity.getNodeLink());
		resource.setOntologyRef(entity.getOntologyRef());
		if (!entity.getPointOfAccess().isEmpty()) {
			resource.getPointOfAccess().addAll(entity.getPointOfAccess());
		}
		resource.setRequestReachability(entity.isRequestReachability());
	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(AeEntity entity, int level, int offset) {
		List<ChildResourceRef> childRefs = new ArrayList<>();
		
		if (level == 0) {
			return childRefs;
		}
		
		// ChildResourceRef ACP
		for (AccessControlPolicyEntity acpEntity : entity.getChildAccessControlPolicies()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(acpEntity.getName());
			child.setType(BigInteger.valueOf(ResourceType.ACCESS_CONTROL_POLICY));
			child.setValue(acpEntity.getResourceID());
			childRefs.add(child);
			
			childRefs.addAll(new AcpMapper().getChildResourceRef(acpEntity, level - 1, offset - 1));
		}
		// ChildResourceRef Container
		for (ContainerEntity containerEntity : entity.getChildContainers()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(containerEntity.getName());
			child.setType(BigInteger.valueOf(ResourceType.CONTAINER));
			child.setValue(containerEntity.getResourceID());
			childRefs.add(child);
			
			childRefs.addAll(new ContainerMapper().getChildResourceRef(containerEntity, level - 1, offset - 1));
		}
		// ChildResourceRef FlexContainer
		for (FlexContainerEntity flexContainerEntity : entity.getChildFlexContainers()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(flexContainerEntity.getName());
			child.setType(flexContainerEntity.getResourceType());
			child.setValue(flexContainerEntity.getResourceID());
			child.setSpid(flexContainerEntity.getContainerDefinition());
			childRefs.add(child);
			
			childRefs.addAll(new FlexContainerMapper().getChildResourceRef(flexContainerEntity, level - 1, offset - 1));
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
		// ChildResourceRef Group
		for (GroupEntity group : entity.getChildGroups()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(group.getName());
			child.setType(BigInteger.valueOf(ResourceType.GROUP));
			child.setValue(group.getResourceID());
			childRefs.add(child);
			
			childRefs.addAll(new GroupMapper().getChildResourceRef(group, level - 1, offset - 1));
		}
		// ChildResourceRef PollingChannel
		for (PollingChannelEntity pollEntity : entity.getPollingChannels()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(pollEntity.getName());
			child.setValue(pollEntity.getResourceID());
			child.setType(ResourceType.POLLING_CHANNEL);
			childRefs.add(child);
			
			childRefs.addAll(new PollingChannelMapper().getChildResourceRef(pollEntity, level - 1, offset - 1));
		}

		// adding DynamicAuthorizationConsultation refs
		for (DynamicAuthorizationConsultationEntity dace : entity.getChildDynamicAuthorizationConsultations()) {
			ChildResourceRef ch = new ChildResourceRef();
			ch.setResourceName(dace.getName());
			ch.setType(ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION);
			ch.setValue(dace.getResourceID());
			childRefs.add(ch);
			
			childRefs.addAll(new DynamicAuthorizationConsultationMapper().getChildResourceRef(dace, level - 1, offset - 1));
		}
		
		return childRefs;
	}

	@Override
	protected void mapChildResourceRef(AeEntity entity, AE resource, int level, int offset) {
		resource.getChildResource().addAll(getChildResourceRef(entity, level, offset));
		
	}

	@Override
	protected void mapChildResources(AeEntity entity, AE resource, int level, int offset) {
		if (level == 0) {
			return;
		}
		
		// ChildResourceRef ACP
		for (AccessControlPolicyEntity acpEntity : entity.getChildAccessControlPolicies()) {
			AccessControlPolicy acpRes = new AcpMapper().mapEntityToResource(acpEntity, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getContainerOrGroupOrAccessControlPolicy().add(acpRes);
		}
		// ChildResourceRef Container
		for (ContainerEntity containerEntity : entity.getChildContainers()) {
			Container cnt = new ContainerMapper().mapEntityToResource(containerEntity, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getContainerOrGroupOrAccessControlPolicy().add(cnt);
		}
		// ChildResourceRef FlexContainer
		for (FlexContainerEntity flexContainerEntity : entity.getChildFlexContainers()) {
			AbstractFlexContainer fcnt = new FlexContainerMapper().mapEntityToResource(flexContainerEntity,
					ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getContainerOrGroupOrAccessControlPolicy().add(fcnt);
		}
		// ChildResourceRef Subscription
		for (SubscriptionEntity sub : entity.getSubscriptions()) {
			Subscription subRes = new SubscriptionMapper().mapEntityToResource(sub, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getContainerOrGroupOrAccessControlPolicy().add(subRes);
		}
		// ChildResourceRef Group
		for (GroupEntity group : entity.getChildGroups()) {
			Group grp = new GroupMapper().mapEntityToResource(group, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getContainerOrGroupOrAccessControlPolicy().add(grp);
		}
		// ChildResourceRef PollingChannel
		for (PollingChannelEntity pollEntity : entity.getPollingChannels()) {
			PollingChannel poll = new PollingChannelMapper().mapEntityToResource(pollEntity, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getContainerOrGroupOrAccessControlPolicy().add(poll);
		}

		// adding DynamicAuthorizationConsultation resource
		for (DynamicAuthorizationConsultationEntity daceEntity : entity.getChildDynamicAuthorizationConsultations()) {
			DynamicAuthorizationConsultation dace = new DynamicAuthorizationConsultationMapper()
					.mapEntityToResource(daceEntity, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getContainerOrGroupOrAccessControlPolicy().add(dace);
		}
	}

}
