/*******************************************************************************
 * Copyright (c) 2014, 2017 Orange.
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
import org.eclipse.om2m.commons.entities.MgmtObjAnncEntity;
import org.eclipse.om2m.commons.entities.NodeAnncEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.NodeAnnc;
import org.eclipse.om2m.commons.resource.Subscription;

/**
 * Mapper for Node resource - entity
 *
 */
public class NodeAnncMapper extends EntityMapper<NodeAnncEntity, NodeAnnc> {

	private MgmtObjAnncMapper mgmtObjMapper;
	
	public NodeAnncMapper() {
		super();
		mgmtObjMapper = new MgmtObjAnncMapper();
	}

	@Override
	protected void mapAttributes(NodeAnncEntity entity, NodeAnnc resource, int level, int offset) {
		if (level < 0) {
			return;
		}
		
		// announceableResource attributes
		EntityMapperFactory.getAnnouncedResourceMapper().mapAttributes(entity, resource, level, offset);
		
		// node attribute
		resource.setNodeID(entity.getNodeID());
		resource.setHostedCSELink(entity.getHostedCSELink());
		resource.setHostedServiceLinks(entity.getHostedServiceLinks());
	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(NodeAnncEntity entity, 
			int level, int offset) {
		List<ChildResourceRef> childRefs = new ArrayList<>();
		if (level == 0) {
			return childRefs;
		}

		// add child ref subscription
		for (SubscriptionEntity sub : entity.getChildSubscriptions()){
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(sub.getName());
			child.setType(ResourceType.SUBSCRIPTION);
			child.setValue(sub.getResourceID());
			childRefs.add(child);
			childRefs.addAll(new SubscriptionMapper().getChildResourceRef(sub, level - 1, offset - 1));
		}
		
		// add mgmt obj entities
		for (MgmtObjAnncEntity mgmtObj : entity.getMgmtObjEntities()) {
			childRefs.add(createChildResourceRef(mgmtObj));
			childRefs.addAll(mgmtObjMapper.getChildResourceRef(mgmtObj, level - 1, offset - 1));
		}
		
		return childRefs;
	}

	@Override
	protected void mapChildResourceRef(NodeAnncEntity entity, NodeAnnc resource, int level, int offset) {
		resource.getChildResource().addAll(getChildResourceRef(entity, level, offset));
	}

	@Override
	protected void mapChildResources(NodeAnncEntity entity, NodeAnnc resource, int level, int offset) {
		// add child ref subscription
		for (SubscriptionEntity sub : entity.getChildSubscriptions()){
			Subscription subRes = new SubscriptionMapper().mapEntityToResource(sub, ResultContent.ATTRIBUTES, level - 1, offset - 1);
//			resource.getMgmtObjs().add(subRes);
		}
		// add mgmt obj entities
		for (MgmtObjAnncEntity mgmtObj : entity.getMgmtObjEntities()) {
			resource.getMgmtObjs().add(mgmtObjMapper.mapEntityToResource(mgmtObj, 
					ResultContent.ATTRIBUTES, level - 1, offset - 1));
		}
	}

	@Override
	protected NodeAnnc createResource() {
		return new NodeAnnc();
	}

	private final ChildResourceRef createChildResourceRef(MgmtObjAnncEntity entity) {
		ChildResourceRef chref = new ChildResourceRef();
		chref.setResourceName(entity.getName());
		chref.setType(ResourceType.MGMT_OBJ_ANNC);
		chref.setValue(entity.getResourceID());
		return chref;
	}
	
}
