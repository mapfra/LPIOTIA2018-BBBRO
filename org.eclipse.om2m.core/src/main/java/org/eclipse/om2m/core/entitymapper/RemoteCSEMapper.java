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
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.GroupEntity;
import org.eclipse.om2m.commons.entities.NodeAnncEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.commons.entities.PollingChannelEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.ScheduleEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AEAnnc;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.Group;
import org.eclipse.om2m.commons.resource.Node;
import org.eclipse.om2m.commons.resource.NodeAnnc;
import org.eclipse.om2m.commons.resource.PollingChannel;
import org.eclipse.om2m.commons.resource.RemoteCSE;
import org.eclipse.om2m.commons.resource.Subscription;

public class RemoteCSEMapper extends EntityMapper<RemoteCSEEntity, RemoteCSE> {

	@Override
	protected RemoteCSE createResource() {
		return new RemoteCSE();
	}

	@Override
	protected void mapAttributes(RemoteCSEEntity csrEntity, RemoteCSE csr, int level, int offset) {
		
		if (level < 0) {
			return;
		}
		
		// announceableResource attributes
		EntityMapperFactory.getAnnounceableSubordonateEntity_AnnounceableResourceMapper().mapAttributes(csrEntity, csr, level, offset);
			
		// remoteCse attributes
		csr.setCSEBase(csrEntity.getRemoteCseUri());
		csr.setCSEID(csrEntity.getRemoteCseId());
		csr.setCseType(csrEntity.getCseType());
		csr.setExpirationTime(csrEntity.getExpirationTime());
		csr.setM2MExtID(csrEntity.getM2mExtId());
		csr.setNodeLink(csrEntity.getNodeLink());
		csr.setRequestReachability(csrEntity.isRequestReachability());
		csr.setTriggerRecipientID(csrEntity.getTriggerRecipientID());
		if (!csrEntity.getPointOfAccess().isEmpty()) {
			csr.getPointOfAccess().addAll(csrEntity.getPointOfAccess());
		}

	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(RemoteCSEEntity csrEntity, int level, int offset) {
		List<ChildResourceRef> childRefs = new ArrayList<>();
		if (level == 0) {
			return childRefs;
		}
		
		// adding subscription refs
		for (SubscriptionEntity sub : csrEntity.getSubscriptions()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(sub.getName());
			child.setType(ResourceType.SUBSCRIPTION);
			child.setValue(sub.getResourceID());
			childRefs.add(child);
			childRefs.addAll(new SubscriptionMapper().getChildResourceRef(sub, level - 1, offset - 1));
		}
		// adding ae ref
		for (AeEntity ae : csrEntity.getChildAes()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(ae.getName());
			child.setType(ResourceType.AE);
			child.setValue(ae.getResourceID());
			childRefs.add(child);
			childRefs.addAll(new AeMapper().getChildResourceRef(ae, level - 1, offset - 1));
		}
		// adding aeA ref
		for (AeAnncEntity aeAnnc : csrEntity.getChildAeAnncs()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(aeAnnc.getName());
			child.setType(ResourceType.AE_ANNC);
			child.setValue(aeAnnc.getResourceID());
			childRefs.add(child);
			childRefs.addAll(new AeAnncMapper().getChildResourceRef(aeAnnc, level - 1, offset - 1));
		}
		// adding acp ref
		for (AccessControlPolicyEntity acp : csrEntity.getChildAcps()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(acp.getName());
			child.setType(ResourceType.ACCESS_CONTROL_POLICY);
			child.setValue(acp.getResourceID());
			childRefs.add(child);
			childRefs.addAll(new AcpMapper().getChildResourceRef(acp, level - 1, offset - 1));
		}
		// adding cnt ref
		for (ContainerEntity container : csrEntity.getChildCnt()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(container.getName());
			child.setType(ResourceType.CONTAINER);
			child.setValue(container.getResourceID());
			childRefs.add(child);
			childRefs.addAll(new ContainerMapper().getChildResourceRef(container, level - 1, offset - 1));
		}
		// adding fcnt ref
		for (FlexContainerEntity flexContainer : csrEntity.getChildFcnt()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(flexContainer.getName());
			child.setType(ResourceType.FLEXCONTAINER);
			child.setValue(flexContainer.getResourceID());
			child.setSpid(flexContainer.getContainerDefinition());
			childRefs.add(child);
			childRefs.addAll(new FlexContainerMapper().getChildResourceRef(flexContainer, level - 1, offset - 1));
		}
		// adding group ref
		for (GroupEntity group : csrEntity.getChildGrps()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(group.getName());
			child.setType(ResourceType.GROUP);
			child.setValue(group.getResourceID());
			childRefs.add(child);
			childRefs.addAll(new GroupMapper().getChildResourceRef(group, level - 1, offset - 1));
		}
		// adding polling channel child
		for (PollingChannelEntity pollEntity : csrEntity.getPollingChannels()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(pollEntity.getName());
			child.setValue(pollEntity.getResourceID());
			child.setType(ResourceType.POLLING_CHANNEL);
			childRefs.add(child);
			childRefs.addAll(new PollingChannelMapper().getChildResourceRef(pollEntity, level - 1, offset - 1));
		}
		// adding schedule child
		ScheduleEntity sch = csrEntity.getLinkedSchedule();
		if (sch != null) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(sch.getName());
			child.setValue(sch.getResourceID());
			child.setType(ResourceType.SCHEDULE);
			childRefs.add(child);
		}
		// adding node refs
		for (NodeEntity nod : csrEntity.getChildNodes()) {
			ChildResourceRef ch = new ChildResourceRef();
			ch.setResourceName(nod.getName());
			ch.setType(ResourceType.NODE);
			ch.setValue(nod.getResourceID());
			childRefs.add(ch);
			childRefs.addAll(new NodeMapper().getChildResourceRef(nod, level - 1, offset - 1));
		}
		// adding nodeAnnc refs
		for (NodeAnncEntity nod : csrEntity.getChildAnncNodes()) {
			ChildResourceRef ch = new ChildResourceRef();
			ch.setResourceName(nod.getName());
			ch.setType(ResourceType.NODE_ANNC);
			ch.setValue(nod.getResourceID());
			childRefs.add(ch);
			childRefs.addAll(new NodeAnncMapper().getChildResourceRef(nod, level - 1, offset - 1));
		}

		// adding DynamicAuthorizationConsultation refs
		for (DynamicAuthorizationConsultationEntity dace : csrEntity.getChildDynamicAuthorizationConsultation()) {
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
	protected void mapChildResourceRef(RemoteCSEEntity csrEntity, RemoteCSE csr, int level, int offset) {
		csr.getChildResource().addAll(getChildResourceRef(csrEntity, level, offset));
	}

	@Override
	protected void mapChildResources(RemoteCSEEntity csrEntity, RemoteCSE csr, int level, int offset) {
		if (level == 0) {
			return;
		}
		
		// adding subscription refs
		for (SubscriptionEntity sub : csrEntity.getSubscriptions()) {
			Subscription chSub = new SubscriptionMapper().mapEntityToResource(sub, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			csr.getAEOrContainerOrGroup().add(chSub);
		}
		// adding ae ref
		for (AeEntity ae : csrEntity.getChildAes()) {
			AE chAe = new AeMapper().mapEntityToResource(ae, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			csr.getAEOrContainerOrGroup().add(chAe);
		}
		// adding aeAnnc ref
		for (AeAnncEntity aeAnnc : csrEntity.getChildAeAnncs()) {
			AEAnnc chAeAnnc = new AeAnncMapper().mapEntityToResource(aeAnnc, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			csr.getAEOrContainerOrGroup().add(chAeAnnc);
		}

		// adding acp ref
		for (AccessControlPolicyEntity acp : csrEntity.getChildAcps()) {
			AccessControlPolicy chAcp = new AcpMapper().mapEntityToResource(acp, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			csr.getAEOrContainerOrGroup().add(chAcp);
		}
		// adding cnt ref
		for (ContainerEntity container : csrEntity.getChildCnt()) {
			Container chCnt = new ContainerMapper().mapEntityToResource(container, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			csr.getAEOrContainerOrGroup().add(chCnt);
		}
		// adding fcnt ref
		for (FlexContainerEntity flexContainer : csrEntity.getChildFcnt()) {
			AbstractFlexContainer chFcnt = new FlexContainerMapper().mapEntityToResource(flexContainer,
					ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			csr.getAEOrContainerOrGroup().add(chFcnt);
		}
		// adding group ref
		for (GroupEntity grp : csrEntity.getChildGrps()) {
			Group chGrp = new GroupMapper().mapEntityToResource(grp, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			csr.getAEOrContainerOrGroup().add(chGrp);
		}
		// adding polling channel child
		for (PollingChannelEntity pollEntity : csrEntity.getPollingChannels()) {
			PollingChannel chPch = new PollingChannelMapper().mapEntityToResource(pollEntity, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			csr.getAEOrContainerOrGroup().add(chPch);
		}
		// adding node refs
		for (NodeEntity nod : csrEntity.getChildNodes()) {
			Node node = new NodeMapper().mapEntityToResource(nod, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			csr.getAEOrContainerOrGroup().add(node);
		}
		for (NodeAnncEntity nod : csrEntity.getChildAnncNodes()) {
			NodeAnnc node = new NodeAnncMapper().mapEntityToResource(nod, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			csr.getAEOrContainerOrGroup().add(node);
		}
		// adding schedule child
		ScheduleEntity sch = csrEntity.getLinkedSchedule();
		if (sch != null) {
			// TODO add schedule child schedule
		}

		// adding DynamicAuthorizationConsultation resource
		for (DynamicAuthorizationConsultationEntity daceEntity : csrEntity.getChildDynamicAuthorizationConsultation()) {
			DynamicAuthorizationConsultation dace = new DynamicAuthorizationConsultationMapper()
					.mapEntityToResource(daceEntity, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			csr.getAEOrContainerOrGroup().add(dace);
		}
	}

}
