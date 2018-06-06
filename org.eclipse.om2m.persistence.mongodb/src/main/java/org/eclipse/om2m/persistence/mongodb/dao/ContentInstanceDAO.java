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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.ContentInstanceEntity;
import org.eclipse.om2m.persistence.mongodb.DAOImpl;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.mongodb.DBTransactionImpl;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class ContentInstanceDAO extends DAOImpl<ContentInstanceEntity> implements DAO<ContentInstanceEntity> {

	private static final Log LOGGER = LogFactory.getLog(ContentInstanceDAO.class);
	
	private boolean oldest;
	private DAO<ContainerEntity> ctrDAO;

	public ContentInstanceDAO(boolean oldest) {
		super(ContentInstanceEntity.class);
		this.oldest = oldest;
		ctrDAO = DBServiceImpl.getInstance().getDAOFactory().getContainerDAO();
	}

	@Override
	public void create(DBTransaction dbTransaction, ContentInstanceEntity cin) {
		try {
			// convert transaction
			DBTransactionImpl dbTransactionImpl = (DBTransactionImpl) dbTransaction;
			
			String json = DBServiceImpl.getInstance().getGson().toJson(cin);
			
			// convert resource as a Document
			Document newOject = Document.parse(json);

			// insert document
			DBServiceImpl.getInstance().getResourceCollection().insertOne(newOject);
			
			ContainerEntity parent = cin.getParentContainer();
			parent.getChildContentInstances().add(cin);
			ctrDAO.update(dbTransactionImpl, parent);
		} catch (Exception e) {
			LOGGER.info("Error create " + cin, e);
		}
	}

	@Override
	public ContentInstanceEntity find(DBTransaction dbTransaction, Object id) {
		return oldest ? findOldest(dbTransaction, id) : findById(dbTransaction, id);
	}
	
	private ContentInstanceEntity findById(DBTransaction dbTransaction, Object id) {
		// convert transaction
		DBTransactionImpl dbTransactionImpl = (DBTransactionImpl) dbTransaction;

		// find
		Document doc = DBServiceImpl.getInstance().getResourceCollection().find(eq(RES_ID, id)).first();
		// convert
		if (doc == null) 
			return null;
		ContentInstanceEntity cin = (ContentInstanceEntity) DBServiceImpl.getInstance().getGson()
				.fromJson(doc.toJson(), ContentInstanceEntity.class);
		cin.setParentContainer(ctrDAO.find(dbTransactionImpl, cin.getParentID()));
		return cin;
	}

	private ContentInstanceEntity findOldest(DBTransaction dbTransaction, Object id) {
		ContainerEntity parent = ctrDAO.find(dbTransaction, id);
		LOGGER.info(parent.getChildContentInstances());
		String old = "3333";
		ContentInstanceEntity ret = null;
		for (ContentInstanceEntity cin : parent.getChildContentInstances()) {
			if (old.compareTo(cin.getCreationTime()) > 0) {
				old = cin.getCreationTime();
				ret = cin;
			}
		}
		return findById(dbTransaction, ret.getResourceID());
	}

	@Override
	public void update(DBTransaction dbTransaction, ContentInstanceEntity resource) {
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
	public void delete(DBTransaction dbTransaction, ContentInstanceEntity resource) {
		super.delete(dbTransaction, resource);
		ContainerEntity parent = resource.getParentContainer();
		parent.getChildContentInstances().remove(resource);
		ctrDAO.update(dbTransaction, parent);
	}

}
