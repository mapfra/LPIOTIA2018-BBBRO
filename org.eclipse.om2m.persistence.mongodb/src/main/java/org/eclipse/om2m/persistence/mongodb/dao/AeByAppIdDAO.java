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
import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class AeByAppIdDAO implements DAO<AeEntity> {

	@Override
	public void create(DBTransaction dbTransaction, AeEntity resource) throws RuntimeException {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public AeEntity find(DBTransaction dbTransaction, Object id)  {
		Document doc = DBServiceImpl.getInstance().getResourceCollection()
				.find(and(eq(ShortName.APP_ID, id), eq(ShortName.RESOURCE_TYPE, 2))).first();
		return (doc == null) ? null
				: DBServiceImpl.getInstance().getGson().fromJson(doc.toJson(), AeEntity.class);
	}

	@Override
	public void update(DBTransaction dbTransaction, AeEntity resource) throws RuntimeException {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void delete(DBTransaction dbTransaction, AeEntity resource) throws RuntimeException {
		throw new RuntimeException("Not implemented");
	}

}
