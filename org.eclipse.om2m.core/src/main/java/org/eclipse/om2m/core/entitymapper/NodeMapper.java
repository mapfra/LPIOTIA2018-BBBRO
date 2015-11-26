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
