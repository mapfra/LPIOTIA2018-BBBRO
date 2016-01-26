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

import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.commons.resource.Node;

/**
 * Mapper for Node resource - entity
 *
 */
public class NodeMapper extends EntityMapper<NodeEntity, Node> {

	@Override
	protected void mapAttributes(NodeEntity entity, Node resource) {
		resource.setNodeID(entity.getNodeID());
		resource.setHostedCSELink(entity.getHostedCSELink());
	}

	@Override
	protected void mapChildResourceRef(NodeEntity entity, Node resource) {
		// TODO Auto-generated method stub	
	}

	@Override
	protected void mapChildResources(NodeEntity entity, Node resource) {
		// TODO Auto-generated method stub
	}

	@Override
	protected Node createResource() {
		return new Node();
	}

}
