package org.eclipse.om2m.persistence.mongodb.dao;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class DescContainerByParentDAO implements DAO<ContainerEntity> {

	private static final String DESC = "DESCRIPTOR";
	
	@Override
	public void create(DBTransaction dbTransaction, ContainerEntity resource) throws RuntimeException {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public ContainerEntity find(DBTransaction dbTransaction, Object id)  {
		Document doc = DBServiceImpl.getInstance().getResourceCollection().find(and(eq("pi", id), eq("ty", 3), eq("rn", DESC))).first();
		
		ContainerEntity toBeReturned = null;
		if (doc != null) {
			toBeReturned = DBServiceImpl.getInstance().getGson().fromJson(doc.toJson(), ContainerEntity.class);
		}
		
		return toBeReturned;
	}

	@Override
	public void update(DBTransaction dbTransaction, ContainerEntity resource) throws RuntimeException {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void delete(DBTransaction dbTransaction, ContainerEntity resource) throws RuntimeException {
		throw new RuntimeException("Not implemented");
	}


}
