package org.eclipse.om2m.persistence.eclipselink.internal.dao;

import java.util.List;

import javax.persistence.Query;

import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class DescContainerByParentDAO extends AbstractDAO<ContainerEntity> {

	public static final String DESC = "DESCRIPTOR";
	
	@Override
	public ContainerEntity find(DBTransaction dbTransaction, Object id) {
		ContainerEntity result = null;
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		String req = "SELECT r FROM " + DBEntities.CONTAINER_ENTITY
				+ " r WHERE r.name = '"
				+ DESC + "' and r.parentID = '" + id + "'";
		Query q = transaction.getEm().createQuery(req);
		List<?> resultList = q.getResultList();
		if (resultList.size() == 1) {
			result = (ContainerEntity)resultList.get(0);
		}
		return result;
	}

	@Override
	public void delete(DBTransaction dbTransaction, ContainerEntity resource) {
		// TODO Auto-generated method stub
		
	}
	

}

