package org.eclipse.om2m.persistence.mongodb.dao;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class RemoteCSEByIdDAO implements DAO<RemoteCSEEntity> {

	@Override
	public void create(DBTransaction dbTransaction, RemoteCSEEntity resource) {
	}

	@Override
	public RemoteCSEEntity find(DBTransaction dbTransaction, Object id) {
		// find
		Document doc = DBServiceImpl.getInstance().getResourceCollection().find(eq("RemoteCseId", id)).first();

		// convert
		RemoteCSEEntity toBeReturned = null;
		if (doc != null) {
			toBeReturned = DBServiceImpl.getInstance().getGson().fromJson(doc.toJson(), RemoteCSEEntity.class);
		}

		return toBeReturned;
	}

	@Override
	public void update(DBTransaction dbTransaction, RemoteCSEEntity resource) {
	}

	@Override
	public void delete(DBTransaction dbTransaction, RemoteCSEEntity resource) {
	}

}
