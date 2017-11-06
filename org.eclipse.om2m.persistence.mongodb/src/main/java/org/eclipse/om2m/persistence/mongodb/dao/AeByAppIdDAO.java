package org.eclipse.om2m.persistence.mongodb.dao;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class AeByAppIdDAO implements DAO<AeEntity> {

	@Override
	public void create(DBTransaction dbTransaction, AeEntity resource) throws RuntimeException {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public AeEntity find(DBTransaction dbTransaction, Object id)  {
		Document doc = DBServiceImpl.getInstance().getResourceCollection().find(and(eq("api", id), eq("ty", 2))).first();
		
		AeEntity toBeReturned = null;
		if (doc != null) {
			toBeReturned = DBServiceImpl.getInstance().getGson().fromJson(doc.toJson(), AeEntity.class);
		}
		
		return toBeReturned;
	}

	@Override
	public void update(DBTransaction dbTransaction, AeEntity resource) throws RuntimeException {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void delete(DBTransaction dbTransaction, AeEntity resource) throws RuntimeException {
		throw new RuntimeException("Not implemented");
	}


}
