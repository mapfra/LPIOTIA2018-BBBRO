package org.eclipse.om2m.persistence.service.util;

import org.eclipse.om2m.commons.entities.CreatedAnnouncedResourceEntity;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBTransaction;

public interface AnnouncedResourceUtil extends DAO<CreatedAnnouncedResourceEntity> {
	
	public CreatedAnnouncedResourceEntity find(DBTransaction dbTransaction, String localAnnounceableId, String announceCseId);

}
