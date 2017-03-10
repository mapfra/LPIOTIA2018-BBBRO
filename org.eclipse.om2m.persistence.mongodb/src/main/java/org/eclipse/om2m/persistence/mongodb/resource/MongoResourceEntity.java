package org.eclipse.om2m.persistence.mongodb.resource;

import org.eclipse.om2m.commons.entities.ResourceEntity;

public abstract class MongoResourceEntity extends ResourceEntity {

	/**
	 * load in database child
	 */
	public abstract void loadChilds();
}
