/*******************************************************************************
 * Copyright (c) 2017 Sensinov (www.sensinov.com)
 * 41 Rue de la découverte 31676 Labège - France 
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Mahdi Ben Alaya - benalaya@sensinov.com
 *
 * New contributors :
 *******************************************************************************/
package org.eclipse.om2m.persistence.eclipselink.internal.dao;

import java.util.List;

import javax.persistence.Query;

import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.entities.ContentInstanceEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

/**
 * DAO for the content instance entity
 *
 */
public class OldestDAO extends AbstractDAO<ContentInstanceEntity> {

	@Override
	public void create(DBTransaction dbTransaction,
			ContentInstanceEntity resource) {
		// NOT AVAILABLE
	}
	
	@Override
	public ContentInstanceEntity find(DBTransaction dbTransaction, Object id) {
		ContentInstanceEntity result = null;

		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		String req = "SELECT r FROM " + DBEntities.CONTENTINSTANCE_ENTITY
				+ " r WHERE r.parentID = '"+ id + "' ORDER BY r.creationTime";
		
		Query q = transaction.getEm().createQuery(req);
		List<ContentInstanceEntity> resultList = q.setMaxResults(1).getResultList();
		if (resultList.size() == 1) {
			result = resultList.get(0);
		}
		return result;
	}

	@Override
	public void delete(DBTransaction dbTransaction,	ContentInstanceEntity resource) {
	}
}