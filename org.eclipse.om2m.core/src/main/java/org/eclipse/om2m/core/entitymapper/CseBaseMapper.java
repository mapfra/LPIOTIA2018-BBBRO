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
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.GroupEntity;
import org.eclipse.om2m.commons.entities.LabelEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.RequestEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.CSEBase;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.Group;
import org.eclipse.om2m.commons.resource.Node;
import org.eclipse.om2m.commons.resource.RemoteCSE;
import org.eclipse.om2m.commons.resource.Request;
import org.eclipse.om2m.commons.resource.Subscription;

public class CseBaseMapper extends EntityMapper<CSEBaseEntity, CSEBase> {

	@Override
	protected CSEBase createResource() {
		return new CSEBase();
	}

	@Override
	protected void mapAttributes(CSEBaseEntity cseBaseEntity, CSEBase cseBaseResource, int level, int offset) {
		if (level < 0) {
			return;
		}
		
		cseBaseResource.setNodeLink(cseBaseEntity.getNodeLink());
		cseBaseResource.setCSEID(cseBaseEntity.getCseid());
		cseBaseResource.setCseType(cseBaseEntity.getCseType());

		// setting supported resources
		for (BigInteger ty : cseBaseEntity.getSupportedResourceType()) {
			cseBaseResource.getSupportedResourceType().add(ty);
		}

		// setting access control policy ids
		for (AccessControlPolicyEntity acp : cseBaseEntity.getAccessControlPolicies()) {
			cseBaseResource.getAccessControlPolicyIDs().add(acp.getResourceID());
		}

		// setting dynamic authorization consultation ids
		List<String> dacis = cseBaseResource.getDynamicAuthorizationConsultationIDs();
		for(DynamicAuthorizationConsultationEntity dace : cseBaseEntity.getDynamicAuthorizationConsultations()) {
			dacis.add(dace.getResourceID());
		}
		
		if (!cseBaseEntity.getLabelsEntities().isEmpty()) {
			for (LabelEntity l : cseBaseEntity.getLabelsEntities()) {
				cseBaseResource.getLabels().add(l.getLabel());
			}
		}
		if (!cseBaseEntity.getPointOfAccess().isEmpty()) {
			cseBaseResource.getPointOfAccess().addAll(cseBaseEntity.getPointOfAccess());
		}
	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(CSEBaseEntity cseBaseEntity, int level, int offset) {
		List<ChildResourceRef> childRefs = new ArrayList<>();
		if (level == 0) {
			return childRefs;
		}
		
		for (AccessControlPolicyEntity acp : cseBaseEntity.getChildAccessControlPolicies()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(acp.getName());
			child.setType(ResourceType.ACCESS_CONTROL_POLICY);
			child.setValue(acp.getResourceID());
			childRefs.add(child);
			
			childRefs.addAll(new AcpMapper().getChildResourceRef(acp, level - 1, offset - 1));
		}
		// adding aes refs
		for (AeEntity ae : cseBaseEntity.getAes()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(ae.getName());
			child.setType(ResourceType.AE);
			child.setValue(ae.getResourceID());
			childRefs.add(child);
			
			childRefs.addAll(new AeMapper().getChildResourceRef(ae, level - 1, offset - 1));
		}
		// adding container refs
		for (ContainerEntity cnt : cseBaseEntity.getChildContainers()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(cnt.getName());
			child.setType(ResourceType.CONTAINER);
			child.setValue(cnt.getResourceID());
			childRefs.add(child);
			
			childRefs.addAll(new ContainerMapper().getChildResourceRef(cnt, level - 1, offset - 1));
		}
		// adding flexcontainer refs
		for (FlexContainerEntity fcnt : cseBaseEntity.getChildFlexContainers()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(fcnt.getName());
			child.setType(ResourceType.FLEXCONTAINER);
			child.setValue(fcnt.getResourceID());
			childRefs.add(child);
			
			childRefs.addAll(new FlexContainerMapper().getChildResourceRef(fcnt, level - 1, offset - 1));
		}

		// adding remoteCSE refs
		for (RemoteCSEEntity csr : cseBaseEntity.getRemoteCses()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(csr.getName());
			child.setType(ResourceType.REMOTE_CSE);
			child.setValue(csr.getResourceID());
			childRefs.add(child);
			
			childRefs.addAll(new RemoteCSEMapper().getChildResourceRef(csr, level - 1, offset - 1));
		}
		// adding group refs
		for (GroupEntity group : cseBaseEntity.getGroups()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(group.getName());
			child.setType(ResourceType.GROUP);
			child.setValue(group.getResourceID());
			childRefs.add(child);
			
			childRefs.addAll(new GroupMapper().getChildResourceRef(group, level - 1, offset - 1));
		}
		// adding subscription refs
		for (SubscriptionEntity sub : cseBaseEntity.getSubscriptions()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(sub.getName());
			child.setType(ResourceType.SUBSCRIPTION);
			child.setValue(sub.getResourceID());
			childRefs.add(child);
			
			childRefs.addAll(new SubscriptionMapper().getChildResourceRef(sub, level - 1, offset - 1));
		}
		// adding request refs
		for (RequestEntity req : cseBaseEntity.getChildReq()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(req.getName());
			child.setType(ResourceType.REQUEST);
			child.setValue(req.getResourceID());
			childRefs.add(child);
			
			childRefs.addAll(new RequestMapper().getChildResourceRef(req, level - 1, offset - 1));
		}
		// adding node refs
		for (NodeEntity nod : cseBaseEntity.getChildNodes()) {
			ChildResourceRef ch = new ChildResourceRef();
			ch.setResourceName(nod.getName());
			ch.setType(ResourceType.NODE);
			ch.setValue(nod.getResourceID());
			childRefs.add(ch);
			
			childRefs.addAll(new NodeMapper().getChildResourceRef(nod, level - 1, offset - 1));
		}

		// adding DynamicAuthorizationConsultation refs
		for (DynamicAuthorizationConsultationEntity dace : cseBaseEntity.getChildDynamicAuthorizationConsultation()) {
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
	protected void mapChildResourceRef(CSEBaseEntity cseBaseEntity, CSEBase cseBaseResource, int level, int offset) {
		// setting child resources refs
		cseBaseResource.getChildResource().addAll(getChildResourceRef(cseBaseEntity, level, offset));
	}

	@Override
	protected void mapChildResources(CSEBaseEntity entity, CSEBase resource, int level, int offset) {
		if (level == 0) {
			return;
		}
		
		for (AccessControlPolicyEntity acp : entity.getChildAccessControlPolicies()) {
			AccessControlPolicy acpRes = new AcpMapper().mapEntityToResource(acp, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getRemoteCSEOrNodeOrAE().add(acpRes);
		}
		// adding aes refs
		for (AeEntity ae : entity.getAes()) {
			AE aeRes = new AeMapper().mapEntityToResource(ae, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getRemoteCSEOrNodeOrAE().add(aeRes);
		}
		// adding container refs
		for (ContainerEntity cnt : entity.getChildContainers()) {
			Container cntRes = new ContainerMapper().mapEntityToResource(cnt, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getRemoteCSEOrNodeOrAE().add(cntRes);
		}
		// adding flexcontainer refs
		for (FlexContainerEntity fcnt : entity.getChildFlexContainers()) {
			AbstractFlexContainer fcntRes = new FlexContainerMapper().mapEntityToResource(fcnt, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getRemoteCSEOrNodeOrAE().add(fcntRes);
		}
		// adding remoteCSE refs
		for (RemoteCSEEntity csr : entity.getRemoteCses()) {
			RemoteCSE csrRes = new RemoteCSEMapper().mapEntityToResource(csr, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getRemoteCSEOrNodeOrAE().add(csrRes);
		}
		// adding group refs
		for (GroupEntity group : entity.getGroups()) {
			Group grp = new GroupMapper().mapEntityToResource(group, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getRemoteCSEOrNodeOrAE().add(grp);
		}
		// adding subscription refs
		for (SubscriptionEntity sub : entity.getSubscriptions()) {
			Subscription subRes = new SubscriptionMapper().mapEntityToResource(sub, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getRemoteCSEOrNodeOrAE().add(subRes);
		}
		// adding node refs
		for (NodeEntity node : entity.getChildNodes()) {
			Node nodeRes = new NodeMapper().mapEntityToResource(node, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getRemoteCSEOrNodeOrAE().add(nodeRes);
		}
		// adding request refs
		for (RequestEntity req : entity.getChildReq()) {
			Request reqResource = new RequestMapper().mapEntityToResource(req, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getRemoteCSEOrNodeOrAE().add(reqResource);
		}

		// adding DynamicAuthorizationConsultation resource
		for (DynamicAuthorizationConsultationEntity daceEntity : entity.getChildDynamicAuthorizationConsultation()) {
			DynamicAuthorizationConsultation dace = new DynamicAuthorizationConsultationMapper()
					.mapEntityToResource(daceEntity, ResultContent.ATTRIBUTES_AND_CHILD_RES, level - 1, offset - 1);
			resource.getRemoteCSEOrNodeOrAE().add(dace);
		}
	}

}
