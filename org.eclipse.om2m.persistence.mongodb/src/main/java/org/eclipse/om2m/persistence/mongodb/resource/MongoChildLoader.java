package org.eclipse.om2m.persistence.mongodb.resource;

import static com.mongodb.client.model.Filters.eq;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bson.Document;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MongoChildLoader<T extends ResourceEntity> {

	public List<? extends ResourceEntity> loadChilds(T t, JsonArray json) {

		List toBeReturned = new ArrayList<>();
		Class clazz = null;

		for (Iterator<JsonElement> iterator = json.iterator(); iterator.hasNext();) {
			JsonObject jsonObject = (JsonObject) iterator.next();

			String resourceId = jsonObject.get("ResourceID").getAsString();
			BigInteger resourceType = jsonObject.get("ResourceType").getAsBigInteger();

			Class objectClass = getEntityClass(resourceType.intValue());
			if (objectClass == null) {
				continue;
			}

			Document doc = DBServiceImpl.getInstance().getResourceCollection().find(eq("ResourceID", resourceId)).first();

			if (doc != null) {
				for (Iterator<Entry<String, Object>> it = doc.entrySet().iterator(); it.hasNext();) {
					Entry<String, Object> entry = it.next();
					if (entry.getKey().startsWith("#")) {
						it.remove();
					}
				}

				Object object = DBServiceImpl.getInstance().getGson().fromJson(doc.toJson(), objectClass);
				toBeReturned.add(object);

			}
		}

		return toBeReturned;
	}

	private Class getEntityClass(int resourceType) {
		switch (resourceType) {
		case ResourceType.AE:
			return AeEntity.class;
		case ResourceType.AE_ANNC:
			return AeAnncEntity.class;
		case ResourceType.ACCESS_CONTROL_POLICY:
			return AccessControlPolicyEntity.class;
		case ResourceType.CSE_BASE:
			return CSEBaseEntity.class;
		case ResourceType.FLEXCONTAINER:
			return FlexContainerEntity.class;
		case ResourceType.FLEXCONTAINER_ANNC:
			return FlexContainerAnncEntity.class;
		case ResourceType.CONTAINER:
			return ContainerEntity.class;
		case ResourceType.REMOTE_CSE:
			return RemoteCSEEntity.class;
		case ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION:
			return DynamicAuthorizationConsultationEntity.class;
		default:
			return null;
		}
	}

}
