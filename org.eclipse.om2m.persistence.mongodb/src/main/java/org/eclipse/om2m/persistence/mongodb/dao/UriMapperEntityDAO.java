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
import org.eclipse.om2m.commons.entities.UriMapperEntity;
import org.eclipse.om2m.persistence.mongodb.Constants;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.mongodb.DBTransactionImpl;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class UriMapperEntityDAO implements DAO<UriMapperEntity>, Constants {
	
//	private static final Gson gson = new GsonBuilder().create();

	@Override
	public void create(DBTransaction dbTransaction, UriMapperEntity resource) {
		// nothing to do
	}

	@Override
	public UriMapperEntity find(DBTransaction dbTransaction, Object id) {
		// convert transaction
		DBTransactionImpl dbTransactionImpl = (DBTransactionImpl) dbTransaction;

		// find
		Document doc = DBServiceImpl.getInstance().getResourceCollection()
				.find(eq(HIERARCHICAL_URI, id)).first();
		if (doc == null)
			return null;
		// convert
		UriMapperEntity toBeReturned = new UriMapperEntity();
		toBeReturned.setHierarchicalUri(doc.getString(HIERARCHICAL_URI));
		toBeReturned.setNonHierarchicalUri(doc.getString(RES_ID));
		toBeReturned.setResourceType(doc.getInteger(RES_TYPE));
		return toBeReturned;
	}

	@Override
	public void update(DBTransaction dbTransaction, UriMapperEntity resource) {
	}

	@Override
	public void delete(DBTransaction dbTransaction, UriMapperEntity resource) {
		// convert transaction
		DBTransactionImpl dbTransactionImpl = (DBTransactionImpl) dbTransaction;

//		dbTransactionImpl.getCollection().findOneAndDelete(eq(fieldName, value))
	}

}
