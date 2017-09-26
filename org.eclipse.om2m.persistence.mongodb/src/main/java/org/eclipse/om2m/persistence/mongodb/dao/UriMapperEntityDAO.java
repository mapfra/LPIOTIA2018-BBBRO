/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.persistence.mongodb.dao;

import static com.mongodb.client.model.Filters.eq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.eclipse.om2m.commons.entities.UriMapperEntity;
import org.eclipse.om2m.persistence.mongodb.DAOImpl;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.mongodb.DBTransactionImpl;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBTransaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UriMapperEntityDAO implements DAO<UriMapperEntity> {
	
	private static final Log LOGGER = LogFactory.getLog(DAOImpl.class);
	private static final Gson gson = new GsonBuilder().create();

	@Override
	public void create(DBTransaction dbTransaction, UriMapperEntity resource) {
		// convert transaction
//		DBTransactionImpl dbTransactionImpl = (DBTransactionImpl) dbTransaction;
//		
//		String json = gson.toJson(resource);
//		LOGGER.info("create json=" + json);
//		
//		Document document = Document.parse(json);
//
//
//		// insert document
//		DBServiceImpl.getInstance().getCollection().insertOne(document);

		
		// nothing to do
	}

	@Override
	public UriMapperEntity find(DBTransaction dbTransaction, Object id) {
		// convert transaction
		DBTransactionImpl dbTransactionImpl = (DBTransactionImpl) dbTransaction;

		// find
		Document doc = DBServiceImpl.getInstance().getResourceCollection().find(eq("HierarchicalURI", id)).first();

		// convert
		UriMapperEntity toBeReturned = null;
		if (doc != null) {
			toBeReturned = new UriMapperEntity();
			toBeReturned.setHierarchicalUri(doc.getString("HierarchicalURI"));
			toBeReturned.setNonHierarchicalUri(doc.getString("ResourceID"));
			toBeReturned.setResourceType(doc.getInteger("ResourceType"));
		}

		return toBeReturned;
	}

	@Override
	public void update(DBTransaction dbTransaction, UriMapperEntity resource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(DBTransaction dbTransaction, UriMapperEntity resource) {
		// convert transaction
		DBTransactionImpl dbTransactionImpl = (DBTransactionImpl) dbTransaction;

		
//		dbTransactionImpl.getCollection().findOneAndDelete(eq(fieldName, value))

	}

}
