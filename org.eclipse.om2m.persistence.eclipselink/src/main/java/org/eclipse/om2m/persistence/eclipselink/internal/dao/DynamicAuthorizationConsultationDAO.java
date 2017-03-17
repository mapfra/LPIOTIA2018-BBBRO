package org.eclipse.om2m.persistence.eclipselink.internal.dao;

import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class DynamicAuthorizationConsultationDAO extends AbstractDAO<DynamicAuthorizationConsultationEntity> {

	@Override
	public DynamicAuthorizationConsultationEntity find(DBTransaction dbTransaction, Object id) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		return transaction.getEm().find(DynamicAuthorizationConsultationEntity.class, id);
	}

	@Override
	public void delete(DBTransaction dbTransaction, DynamicAuthorizationConsultationEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		if (resource.getParentCseBase() != null) {
			resource.getParentCseBase().getChildDynamicAuthorizationConsultation().remove(resource);
		}
		if (resource.getParentAe() != null) {
			resource.getParentAe().getDynamicAuthorizationConsultations().remove(resource);
		}
		if (resource.getParentRemoteCse() != null) {
			resource.getParentRemoteCse().getChildDynamicAuthorizationConsultation().remove(resource);
		}
		transaction.getEm().remove(resource);
	}

}
