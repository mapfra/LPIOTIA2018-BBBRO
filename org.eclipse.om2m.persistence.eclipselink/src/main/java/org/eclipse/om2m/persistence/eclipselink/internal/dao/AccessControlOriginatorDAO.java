package org.eclipse.om2m.persistence.eclipselink.internal.dao;

import org.eclipse.om2m.commons.entities.AccessControlOriginatorEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

/**
 * DAO for the accessControlOriginator entity
 *
 */
public class AccessControlOriginatorDAO extends AbstractDAO<AccessControlOriginatorEntity> {

	@Override
	public AccessControlOriginatorEntity find(DBTransaction dbTransaction,
			Object id) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		return transaction.getEm().find(AccessControlOriginatorEntity.class, id);
	}

	@Override
	public void delete(DBTransaction dbTransaction,
			AccessControlOriginatorEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		transaction.getEm().remove(resource);
	}

	
	
}
