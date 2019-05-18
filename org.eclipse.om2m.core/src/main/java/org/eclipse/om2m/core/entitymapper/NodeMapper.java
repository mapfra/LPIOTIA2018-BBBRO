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
import org.eclipse.om2m.commons.entities.MgmtObjEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.Node;
import org.eclipse.om2m.commons.resource.Subscription;

/**
 * Mapper for Node resource - entity
 *
 */
public class NodeMapper extends EntityMapper<NodeEntity, Node> {

	private MgmtObjMapper mgmtObjMapper;
	
	public NodeMapper() {
		super();
		mgmtObjMapper = new MgmtObjMapper();
	}

	@Override
	protected void mapAttributes(NodeEntity entity, Node resource, int level, int offset) {
		if (level < 0) {
			return;
		}
		
		// announceableResource attributes
		EntityMapperFactory.getAnnounceableSubordonateEntity_AnnounceableResourceMapper()
			.mapAttributes(entity, resource, level, offset);
		
		// node attribute
		resource.setNodeID(entity.getNodeID());
		resource.setHostedCSELink(entity.getHostedCSELink());
		resource.setHostedServiceLinks(entity.getHostedServiceLinks());
	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(NodeEntity entity, 
			int level, int offset) {
		List<ChildResourceRef> childRefs = new ArrayList<>();
		if (level == 0) {
			return childRefs;
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
		
		// add mgmt obj entities
		for (MgmtObjEntity mgmtObj : entity.getMgmtObjEntities()) {
			childRefs.add(createChildResourceRef(mgmtObj));
			childRefs.addAll(mgmtObjMapper.getChildResourceRef(mgmtObj, level - 1, offset - 1));
		}
		
		return childRefs;
	}

	@Override
	protected void mapChildResourceRef(NodeEntity entity, Node resource, int level, int offset) {
		resource.getChildResource().addAll(getChildResourceRef(entity, level, offset));
	}

	@Override
	protected void mapChildResources(NodeEntity entity, Node resource, int level, int offset) {
		// add child ref subscription
		for (SubscriptionEntity sub : entity.getSubscriptions()){
			Subscription subRes = new SubscriptionMapper().mapEntityToResource(sub, ResultContent.ATTRIBUTES, level - 1, offset - 1);
			resource.getSubscriptions().add(subRes);
		}
		// add mgmt obj entities
		for (MgmtObjEntity mgmtObj : entity.getMgmtObjEntities()) {
			resource.getMgmtObjs().add(mgmtObjMapper.mapEntityToResource(mgmtObj, 
					ResultContent.ATTRIBUTES, level - 1, offset - 1));
		}
	}

	@Override
	protected Node createResource() {
		return new Node();
	}

	private final ChildResourceRef createChildResourceRef(MgmtObjEntity entity) {
		ChildResourceRef chref = new ChildResourceRef();
		chref.setResourceName(entity.getName());
		chref.setType(ResourceType.MGMT_OBJ);
		chref.setValue(entity.getResourceID());
		return chref;
	}
	
}
