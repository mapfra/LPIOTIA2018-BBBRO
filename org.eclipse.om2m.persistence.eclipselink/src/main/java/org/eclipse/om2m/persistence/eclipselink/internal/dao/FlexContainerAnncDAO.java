package org.eclipse.om2m.persistence.eclipselink.internal.dao;

import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class FlexContainerAnncDAO extends AbstractDAO<FlexContainerAnncEntity> {

	@Override
	public FlexContainerAnncEntity find(DBTransaction dbTransaction, Object id) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		return transaction.getEm().find(FlexContainerAnncEntity.class, id);
	}

	@Override
	public void delete(DBTransaction dbTransaction, FlexContainerAnncEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		transaction.getEm().remove(resource);
		transaction.getEm().getEntityManagerFactory().getCache().evict(AeAnncEntity.class);	
		transaction.getEm().getEntityManagerFactory().getCache().evict(FlexContainerAnncEntity.class);	
		
	}

}
