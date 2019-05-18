package org.eclipse.om2m.persistence.eclipselink.internal.dao;

import java.util.List;

import javax.persistence.Query;

import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class AeByAppIdDAO extends AbstractDAO<AeEntity> {
	

	@Override
	public AeEntity find(DBTransaction dbTransaction, Object id) {
		AeEntity result = null;
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		String req = "SELECT r FROM " + DBEntities.AE_ENTITY
				+ " r WHERE r.appID = '"
				+ id + "'";
		Query q = transaction.getEm().createQuery(req);
		List<?> resultList = q.getResultList();
		if (resultList.size() == 1) {
			result = (AeEntity)resultList.get(0);
		}
		return result;
	}
	
	

	@Override
	public void delete(DBTransaction dbTransaction, AeEntity resource) {
		// TODO Auto-generated method stub
		
	}

}
