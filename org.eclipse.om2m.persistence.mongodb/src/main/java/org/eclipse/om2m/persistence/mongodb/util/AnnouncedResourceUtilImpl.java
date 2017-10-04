/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.persistence.mongodb.util;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.entities.CreatedAnnouncedResourceEntity;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.mongodb.DBTransactionImpl;
import org.eclipse.om2m.persistence.mongodb.dao.CreatedAnnouncedResourceEntityDao;
import org.eclipse.om2m.persistence.service.DBTransaction;
import org.eclipse.om2m.persistence.service.util.AnnouncedResourceUtil;

public class AnnouncedResourceUtilImpl implements AnnouncedResourceUtil {

	private CreatedAnnouncedResourceEntityDao dao = new CreatedAnnouncedResourceEntityDao();

	@Override
	public void create(DBTransaction dbTransaction, CreatedAnnouncedResourceEntity resource) {
		dao.create(dbTransaction, resource);
	}

	@Override
	public CreatedAnnouncedResourceEntity find(DBTransaction dbTransaction, Object id) {
		return dao.find(dbTransaction, id);
	}

	@Override
	public void update(DBTransaction dbTransaction, CreatedAnnouncedResourceEntity resource) {
		dao.update(dbTransaction, resource);
	}

	@Override
	public void delete(DBTransaction dbTransaction, CreatedAnnouncedResourceEntity resource) {
		dao.delete(dbTransaction, resource);
	}

	@Override
	public CreatedAnnouncedResourceEntity find(DBTransaction dbTransaction, String localAnnounceableId,
			String announceCseId) {
		// convert transaction
		DBTransactionImpl dbTransactionImpl = (DBTransactionImpl) dbTransaction;

		Document doc = DBServiceImpl.getInstance().getAnnounceCollection().find(and(eq(DBEntities.LOCAL_RESOURCE_ID, localAnnounceableId),
				eq(DBEntities.ANNOUNCE_CSE_ID, announceCseId))).first();
		
		CreatedAnnouncedResourceEntity entity = null;
		if (doc != null) {
			entity = new CreatedAnnouncedResourceEntity();
			entity.setAnnounceCseId(doc.getString(DBEntities.ANNOUNCE_CSE_ID));
			entity.setId(doc.getLong("id"));
			entity.setLocalAnnounceableId(doc.getString(DBEntities.LOCAL_RESOURCE_ID));
			entity.setRemoteAnnouncedId(doc.getString(DBEntities.REMOTE_RESOURCE_ID));
		}

		return entity;
	}

}
