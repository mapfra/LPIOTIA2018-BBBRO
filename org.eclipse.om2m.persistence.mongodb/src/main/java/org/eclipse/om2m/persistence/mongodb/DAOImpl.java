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
package org.eclipse.om2m.persistence.mongodb;

import static com.mongodb.client.model.Filters.eq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBTransaction;

import com.mongodb.client.MongoCursor;

@SuppressWarnings("unused")
public abstract class DAOImpl<T extends ResourceEntity> implements DAO<T>, Constants {

	private static final Log LOGGER = LogFactory.getLog(DAOImpl.class);
	
	private final Class<?> clazz;
	
	public DAOImpl(Class<?> pClazz) {
		this.clazz = pClazz;
	}
	
	@Override
	public void create(DBTransaction dbTransaction, T resource) {
		try {
			// convert transaction
			DBTransactionImpl dbTransactionImpl = (DBTransactionImpl) dbTransaction;

			String json = DBServiceImpl.getInstance().getGson().toJson(resource);
			// convert resource as a Document
			Document newOject = Document.parse(json);
			// insert document
			DBServiceImpl.getInstance().getResourceCollection().insertOne(newOject);
		} catch (Exception e) {
			LOGGER.info("Error create " + resource, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T find(DBTransaction dbTransaction, Object id) {
		// convert transaction
		DBTransactionImpl dbTransactionImpl = (DBTransactionImpl) dbTransaction;

		// find
		Document doc = DBServiceImpl.getInstance().getResourceCollection().find(eq(RES_ID, id)).first();
		// convert
		return (doc == null) ? null
				: (T) DBServiceImpl.getInstance().getGson().fromJson(doc.toJson(), clazz);
	}

	@Override
	public void update(DBTransaction dbTransaction, T resource) {
		try {
			// convert transaction
			DBTransactionImpl dbTransactionImpl = (DBTransactionImpl) dbTransaction;

			// find it first
			Document doc = DBServiceImpl.getInstance().getResourceCollection()
					.find(eq(RES_ID, resource.getResourceID())).first();

			// update
			if (doc != null) {
				String json = DBServiceImpl.getInstance().getGson().toJson(resource);
				Document newDoc = Document.parse(json);
				DBServiceImpl.getInstance().getResourceCollection()
					.replaceOne(eq(RES_ID, resource.getResourceID()), newDoc);
			}
		} catch (Exception e) {
			LOGGER.info("Error update " + resource, e);
		}
	}

	@Override
	public void delete(DBTransaction dbTransaction, T resource) {
		// delete also all this child !
		delete(resource.getResourceID());
	}

	@SuppressWarnings("rawtypes")
	private void delete(Object id) {
		// delete the resource
		DBServiceImpl.getInstance().getResourceCollection().deleteMany(eq(RES_ID, id));
		// delete also all this children
		for (MongoCursor cursor = DBServiceImpl.getInstance().getResourceCollection()
				.find(eq(PARENT_ID, id)).iterator(); cursor.hasNext();) {
			Object idChild = ((Document) cursor.next()).get(RES_ID);
			delete(idChild);
		}
	}

}
