package org.eclipse.om2m.persistence.eclipselink.internal.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.entities.CreatedAnnouncedResourceEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;
import org.eclipse.om2m.persistence.service.util.AnnouncedResourceUtil;

public class CreatedAnnouncedResourceDAO extends AbstractDAO<CreatedAnnouncedResourceEntity> implements AnnouncedResourceUtil {

	@Override
	public CreatedAnnouncedResourceEntity find(DBTransaction dbTransaction, Object id) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		return transaction.getEm().find(CreatedAnnouncedResourceEntity.class, id);
	}

	@Override
	public void delete(DBTransaction dbTransaction, CreatedAnnouncedResourceEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		transaction.getEm().remove(resource);
	}

	public CreatedAnnouncedResourceEntity find(DBTransaction dbTransaction, String localAnnounceableId, String announceCseId) {
		CreatedAnnouncedResourceEntity are = null;

		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;


		Query q = transaction.getEm().createQuery(
				"SELECT ar FROM " + DBEntities.ANNOUNCED_RESOURCE_ENTITY + " ar WHERE ar.localAnnounceableId=\""
						+ localAnnounceableId + "\" AND ar.announceCseId=\"" + announceCseId + "\"");
		try {
			are = (CreatedAnnouncedResourceEntity) q.getSingleResult();
		} catch (NoResultException e) {
			are = null;
		}

		return are;
	}

}
