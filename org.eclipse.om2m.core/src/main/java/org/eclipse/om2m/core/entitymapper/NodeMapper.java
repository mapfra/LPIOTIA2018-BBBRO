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
import org.eclipse.om2m.commons.entities.AreaNwkDeviceInfoEntity;
import org.eclipse.om2m.commons.entities.AreaNwkInfoEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.commons.resource.AreaNwkDeviceInfo;
import org.eclipse.om2m.commons.resource.AreaNwkInfo;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.Node;

/**
 * Mapper for Node resource - entity
 *
 */
public class NodeMapper extends EntityMapper<NodeEntity, Node> {

	@Override
	protected void mapAttributes(NodeEntity entity, Node resource, int level, int offset) {
		
		if (level < 0) {
			return;
		}
		
		// announceableResource attributes
		EntityMapperFactory.getAnnounceableSubordonateEntity_AnnounceableResourceMapper().mapAttributes(entity, resource, level, offset);
		
		// node attribute
		resource.setNodeID(entity.getNodeID());
		resource.setHostedCSELink(entity.getHostedCSELink());
	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(NodeEntity entity, int level, int offset) {
		List<ChildResourceRef> childRefs = new ArrayList<>();
		
		if (level == 0) {
			return childRefs;
		}
		
		// add child area nwk info entities
		for (AreaNwkInfoEntity aniEntity : entity.getChildAreaNwkInfoEntities()) {
			ChildResourceRef chref = new ChildResourceRef();
			chref.setResourceName(aniEntity.getName());
			chref.setType(ResourceType.MGMT_OBJ);
			chref.setValue(aniEntity.getResourceID());
			childRefs.add(chref);
			childRefs.addAll(new AreaNwkInfoMapper().getChildResourceRef(aniEntity, level - 1, offset - 1));
		}
		// add child area nwk device info entities
		for (AreaNwkDeviceInfoEntity andiEntity : entity.getChildAreaNwkDeviceInfoEntities()) {
			ChildResourceRef chref = new ChildResourceRef();
			chref.setResourceName(andiEntity.getName());
			chref.setType(ResourceType.MGMT_OBJ);
			chref.setValue(andiEntity.getResourceID());
			childRefs.add(chref);
			childRefs.addAll(new AreaNwkDeviceInfoMapper().getChildResourceRef(andiEntity, level - 1, offset - 1));
		}
		
		return childRefs;
	}

	@Override
	protected void mapChildResourceRef(NodeEntity entity, Node resource, int level, int offset) {
		resource.getChildResource().addAll(getChildResourceRef(entity, level, offset));
	}

	@Override
	protected void mapChildResources(NodeEntity entity, Node resource, int level, int offset) {
		// add child area nwk info entities
		for (AreaNwkInfoEntity aniEntity : entity.getChildAreaNwkInfoEntities()) {
			AreaNwkInfo aniRes = new AreaNwkInfoMapper().mapEntityToResource(aniEntity, ResultContent.ATTRIBUTES, level - 1, offset - 1);
			resource.getMemoryOrBatteryOrAreaNwkInfo().add(aniRes);
		}
		// add child area nwk device info entities
		for (AreaNwkDeviceInfoEntity andiEntity : entity.getChildAreaNwkDeviceInfoEntities()) {
			AreaNwkDeviceInfo andiRes = new AreaNwkDeviceInfoMapper().mapEntityToResource(andiEntity, ResultContent.ATTRIBUTES, level - 1, offset - 1);
			resource.getMemoryOrBatteryOrAreaNwkInfo().add(andiRes);
		}
	}

	@Override
	protected Node createResource() {
		return new Node();
	}

}
