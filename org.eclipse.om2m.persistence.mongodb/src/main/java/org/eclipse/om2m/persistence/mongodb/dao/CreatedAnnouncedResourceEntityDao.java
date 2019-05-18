/*******************************************************************************
 * Copyright (c) 2014 - 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    BAREAU Cyrille <cyrille.bareau@orange.com>, 
 *    BONNARDEL Gregory <gbonnardel.ext@orange.com>, 
 *******************************************************************************/
package org.eclipse.om2m.persistence.mongodb.dao;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.entities.CreatedAnnouncedResourceEntity;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class CreatedAnnouncedResourceEntityDao implements DAO<CreatedAnnouncedResourceEntity> {
	
	static private final String ID = "id";

	@Override
	public void create(DBTransaction dbTransaction, CreatedAnnouncedResourceEntity resource) {
		Document document = new Document();
		document.put(DBEntities.ANNOUNCE_CSE_ID, resource.getAnnounceCseId());
		document.put(DBEntities.LOCAL_RESOURCE_ID, resource.getLocalAnnounceableId());
		document.put(DBEntities.REMOTE_RESOURCE_ID, resource.getRemoteAnnouncedId());
		document.put(ID, resource.getId());

		DBServiceImpl.getInstance().getAnnounceCollection().insertOne(document);
	}

	@Override
	public CreatedAnnouncedResourceEntity find(DBTransaction dbTransaction, Object id) {
		Document doc = DBServiceImpl.getInstance().getAnnounceCollection().find(eq(ID, id)).first();

		if (doc != null) {
			CreatedAnnouncedResourceEntity entity = new CreatedAnnouncedResourceEntity();
			entity.setAnnounceCseId(doc.getString(DBEntities.ANNOUNCE_CSE_ID));
			entity.setId(doc.getLong(ID));
			entity.setLocalAnnounceableId(doc.getString(DBEntities.LOCAL_RESOURCE_ID));
			entity.setRemoteAnnouncedId(doc.getString(DBEntities.REMOTE_RESOURCE_ID));
			return entity;
		}
		return null;
	}

	@Override
	public void update(DBTransaction dbTransaction, CreatedAnnouncedResourceEntity resource) {
	}

	@Override
	public void delete(DBTransaction dbTransaction, CreatedAnnouncedResourceEntity resource) {
		DBServiceImpl.getInstance().getAnnounceCollection()
				.findOneAndDelete(and(eq(DBEntities.LOCAL_RESOURCE_ID, resource.getLocalAnnounceableId()),
						eq(DBEntities.REMOTE_RESOURCE_ID, resource.getRemoteAnnouncedId())));
	}

}
