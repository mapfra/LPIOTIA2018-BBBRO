package org.eclipse.om2m.persistence.eclipselink.internal.dao;

import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class NodeEntityDAO extends AbstractDAO<NodeEntity>{

	@Override
	public NodeEntity find(DBTransaction dbTransaction, Object id) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		return transaction.getEm().find(NodeEntity.class, id);
	}

	@Override
	public void delete(DBTransaction dbTransaction, NodeEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		transaction.getEm().remove(resource);
		transaction.getEm().getEntityManagerFactory().getCache().evict(CSEBaseEntity.class);
		transaction.getEm().getEntityManagerFactory().getCache().evict(RemoteCSEEntity.class);
	}

}
