/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.persistence.mongodb.dao;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.eclipse.om2m.commons.entities.AccessControlOriginatorEntity;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class AccessControlOriginatorEntityDAO implements DAO<AccessControlOriginatorEntity> {

	@Override
	public void create(DBTransaction dbTransaction, AccessControlOriginatorEntity resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AccessControlOriginatorEntity find(DBTransaction dbTransaction, Object id) {
		Document doc = DBServiceImpl.getInstance().getResourceCollection().find(eq("originatorID", id)).first();
		
		AccessControlOriginatorEntity accessControlOriginator = null;
		if (doc != null) {
			accessControlOriginator = new AccessControlOriginatorEntity();
			accessControlOriginator.setOriginatorID((String) id);
		}
		
		return accessControlOriginator;
	}

	@Override
	public void update(DBTransaction dbTransaction, AccessControlOriginatorEntity resource) {
	}

	@Override
	public void delete(DBTransaction dbTransaction, AccessControlOriginatorEntity resource) {
	}

}
