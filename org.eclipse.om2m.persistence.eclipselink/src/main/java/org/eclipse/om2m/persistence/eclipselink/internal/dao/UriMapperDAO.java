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

import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.entities.UriMapperEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class UriMapperDAO extends AbstractDAO<UriMapperEntity>{

	@Override
	public UriMapperEntity find(DBTransaction dbTransaction, Object id) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		return transaction.getEm().find(UriMapperEntity.class, id);
	}

	@Override
	public void delete(DBTransaction dbTransaction, UriMapperEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		String req = "DELETE FROM "+DBEntities.URI_MAPPER_ENTITY+" U WHERE U.hierarchicalUri LIKE '"+resource.getHierarchicalUri()+"%'";
		transaction.getEm().createQuery(req).executeUpdate();
	}
	
}
