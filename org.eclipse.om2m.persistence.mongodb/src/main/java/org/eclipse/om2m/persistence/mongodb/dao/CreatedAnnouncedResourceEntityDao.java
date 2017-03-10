package org.eclipse.om2m.persistence.mongodb.dao;

import static com.mongodb.client.model.Filters.*;
import org.bson.Document;
import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.entities.CreatedAnnouncedResourceEntity;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.mongodb.DBTransactionImpl;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class CreatedAnnouncedResourceEntityDao implements DAO<CreatedAnnouncedResourceEntity> {

	@Override
	public void create(DBTransaction dbTransaction, CreatedAnnouncedResourceEntity resource) {
		Document document = new Document();
		document.put(DBEntities.ANNOUNCE_CSE_ID, resource.getAnnounceCseId());
		document.put(DBEntities.LOCAL_RESOURCE_ID, resource.getLocalAnnounceableId());
		document.put(DBEntities.REMOTE_RESOURCE_ID, resource.getRemoteAnnouncedId());
		document.put("id", resource.getId());

		DBServiceImpl.getInstance().getAnnounceCollection().insertOne(document);

	}

	@Override
	public CreatedAnnouncedResourceEntity find(DBTransaction dbTransaction, Object id) {
		Document doc = DBServiceImpl.getInstance().getAnnounceCollection().find(eq("id", id)).first();

		if (doc != null) {
			CreatedAnnouncedResourceEntity entity = new CreatedAnnouncedResourceEntity();
			entity.setAnnounceCseId(doc.getString(DBEntities.ANNOUNCE_CSE_ID));
			entity.setId(doc.getLong("id"));
			entity.setLocalAnnounceableId(doc.getString(DBEntities.LOCAL_RESOURCE_ID));
			entity.setRemoteAnnouncedId(doc.getString(DBEntities.REMOTE_RESOURCE_ID));

			return entity;

		}

		return null;
	}

	@Override
	public void update(DBTransaction dbTransaction, CreatedAnnouncedResourceEntity resource) {
	}

	@Override
	public void delete(DBTransaction dbTransaction, CreatedAnnouncedResourceEntity resource) {
		DBServiceImpl.getInstance().getAnnounceCollection()
				.findOneAndDelete(and(eq(DBEntities.LOCAL_RESOURCE_ID, resource.getLocalAnnounceableId()),
						eq(DBEntities.REMOTE_RESOURCE_ID, resource.getRemoteAnnouncedId())));
	}

}
