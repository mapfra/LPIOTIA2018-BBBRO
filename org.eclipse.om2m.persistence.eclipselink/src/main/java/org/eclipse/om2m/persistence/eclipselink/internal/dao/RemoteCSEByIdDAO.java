/*******************************************************************************
 * Copyright (c) 2013-2015 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Thierry Monteil (Project co-founder) - Management and initial specification,
 *         conception and documentation.
 *     Mahdi Ben Alaya (Project co-founder) - Management and initial specification,
 *         conception, implementation, test and documentation.
 *     Khalil Drira - Management and initial specification.
 *     Guillaume Garzone - Initial specification, conception, implementation, test
 *         and documentation.
 *     François Aïssaoui - Initial specification, conception, implementation, test
 *         and documentation.
 *******************************************************************************/
package org.eclipse.om2m.persistence.eclipselink.internal.dao;

import java.util.List;

import javax.persistence.Query;

import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class RemoteCSEByIdDAO extends AbstractDAO<RemoteCSEEntity> {
	
	@Override
	public void create(DBTransaction dbTransaction, RemoteCSEEntity resource) {
		// NOT AVAILABLE
	}

	@Override
	public RemoteCSEEntity find(DBTransaction dbTransaction, Object id) {
		RemoteCSEEntity result = null;
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		String req = "SELECT r FROM " + DBEntities.REMOTECSE_ENTITY
				+ " r WHERE r.remoteCseId = '"
				+ id + "'";
		Query q = transaction.getEm().createQuery(req);
		List<RemoteCSEEntity> resultList = q.getResultList();
		if (resultList.size() == 1) {
			result = resultList.get(0);
		}
		return result;
	}
	
	@Override
	public void update(DBTransaction dbTransaction, RemoteCSEEntity resource) {
		// NOT AVAILABLE
	}

	@Override
	public void delete(DBTransaction dbTransaction, RemoteCSEEntity resource) {
		// NOT AVAILABLE
	}

}
