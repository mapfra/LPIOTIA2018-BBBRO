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

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.persistence.mongodb.Constants;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class RemoteCSEByIdDAO implements DAO<RemoteCSEEntity>, Constants {

	@Override
	public void create(DBTransaction dbTransaction, RemoteCSEEntity resource) {
	}

	@Override
	public RemoteCSEEntity find(DBTransaction dbTransaction, Object id) {
		// find
		Document doc = DBServiceImpl.getInstance().getResourceCollection()
				.find(eq(REMOTE_CSE_ID, id)).first();
		// convert
		return (doc == null) ? null
				: DBServiceImpl.getInstance().getGson().fromJson(doc.toJson(), RemoteCSEEntity.class);
	}

	@Override
	public void update(DBTransaction dbTransaction, RemoteCSEEntity resource) {
	}

	@Override
	public void delete(DBTransaction dbTransaction, RemoteCSEEntity resource) {
	}

}
