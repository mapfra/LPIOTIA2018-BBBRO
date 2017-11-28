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
import org.eclipse.om2m.commons.entities.DeviceInfoEntity;
import org.eclipse.om2m.commons.entities.MgmtObjEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.commons.resource.AreaNwkDeviceInfo;
import org.eclipse.om2m.commons.resource.AreaNwkInfo;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.DeviceInfo;
import org.eclipse.om2m.commons.resource.Node;

/**
 * Mapper for Node resource - entity
 *
 */
public class NodeMapper extends EntityMapper<NodeEntity, Node> {

	private AreaNwkInfoMapper areaNwkInfoMapper;
	private AreaNwkDeviceInfoMapper areaNwkDeviceInfoMapper;
	private DeviceInfoMapper deviceInfoMapper;
	
	public NodeMapper() {
		super();
		areaNwkDeviceInfoMapper = new AreaNwkDeviceInfoMapper();
		areaNwkInfoMapper = new AreaNwkInfoMapper();
		deviceInfoMapper = new DeviceInfoMapper();
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
		resource.setHostedAppLinks(entity.getHostedAppLink());
	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(NodeEntity entity, 
			int level, int offset) {
		List<ChildResourceRef> childRefs = new ArrayList<>();
		if (level == 0) {
			return childRefs;
		}
		
		// add child area nwk info entities
		for (AreaNwkInfoEntity aniEntity : entity.getChildAreaNwkInfoEntities()) {
			childRefs.add(createChildResourceRef(aniEntity));
			childRefs.addAll(areaNwkInfoMapper.getChildResourceRef(aniEntity, level - 1, offset - 1));
		}
		// add child area nwk device info entities
		for (AreaNwkDeviceInfoEntity andiEntity : entity.getChildAreaNwkDeviceInfoEntities()) {
			childRefs.add(createChildResourceRef(andiEntity));
			childRefs.addAll(areaNwkDeviceInfoMapper.getChildResourceRef(andiEntity, level - 1, offset - 1));
		}
		// add child device info entities
		for (DeviceInfoEntity dviEntity : entity.getChildDeviceInfoEntities()) {
			childRefs.add(createChildResourceRef(dviEntity));
			childRefs.addAll(deviceInfoMapper.getChildResourceRef(dviEntity, level - 1, offset - 1));
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
			AreaNwkInfo aniRes = areaNwkInfoMapper.mapEntityToResource(aniEntity, 
					ResultContent.ATTRIBUTES, level - 1, offset - 1);
			resource.getMgmtObjs().add(aniRes);
		}
		// add child area nwk device info entities
		for (AreaNwkDeviceInfoEntity andiEntity : entity.getChildAreaNwkDeviceInfoEntities()) {
			AreaNwkDeviceInfo andiRes = areaNwkDeviceInfoMapper.mapEntityToResource(andiEntity, 
					ResultContent.ATTRIBUTES, level - 1, offset - 1);
			resource.getMgmtObjs().add(andiRes);
		}
		// add child device info entities
		for (DeviceInfoEntity dviEntity : entity.getChildDeviceInfoEntities()) {
			DeviceInfo dviRes = deviceInfoMapper.mapEntityToResource(dviEntity, 
					ResultContent.ATTRIBUTES, level - 1, offset - 1);
			resource.getMgmtObjs().add(dviRes);
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
