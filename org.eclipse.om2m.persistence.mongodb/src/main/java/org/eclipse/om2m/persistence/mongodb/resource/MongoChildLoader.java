/*******************************************************************************
 * Copyright (c) 2014 - 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    BAREAU Cyrille <cyrille.bareau@orange.com>, 
 *    BONNARDEL Gregory <gbonnardel.ext@orange.com>, 
 *******************************************************************************/
package org.eclipse.om2m.persistence.mongodb.resource;

import static com.mongodb.client.model.Filters.eq;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.ContentInstanceEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.MgmtObjAnncEntity;
import org.eclipse.om2m.commons.entities.MgmtObjEntity;
import org.eclipse.om2m.commons.entities.NodeAnncEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.persistence.mongodb.Constants;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MongoChildLoader<T extends ResourceEntity> implements Constants {

	private static final Log LOGGER = LogFactory.getLog(MongoChildLoader.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<? extends ResourceEntity> loadChildren(T t, JsonArray json) {
		LOGGER.debug(" * IN loadChildren * " + t + " with " + json);
		List entities = new ArrayList<>();
		for (Iterator<JsonElement> iterator = json.iterator(); iterator.hasNext();) {
			JsonObject jsonObject = (JsonObject) iterator.next();

			Class<?> clazz = getEntityClass(jsonObject);
			if (clazz == null) {
				continue;
			}

			String resId = jsonObject.get(RES_ID).getAsString();
			Document doc = DBServiceImpl.getInstance().getResourceCollection()
					.find(eq(RES_ID, resId)).first();

			if (doc != null) {
				for (Iterator<Entry<String, Object>> it = doc.entrySet().iterator(); it.hasNext();) {
					Entry<String, Object> entry = it.next();
					if (entry.getKey().startsWith("#")) {
						it.remove();
					}
				}
				entities.add(DBServiceImpl.getInstance().getGson().fromJson(doc.toJson(), clazz));
			}
		}
		LOGGER.debug(" * OUT loadChildren * " + t + " with " + entities);
		return entities;
	}

	private Class<?> getEntityClass(JsonObject jsonObject) {
		BigInteger type = jsonObject.get(RES_TYPE).getAsBigInteger();
		switch (type.intValue()) {
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
		case ResourceType.CONTENT_INSTANCE:
			return ContentInstanceEntity.class;
		case ResourceType.REMOTE_CSE:
			return RemoteCSEEntity.class;
		case ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION:
			return DynamicAuthorizationConsultationEntity.class;
		case ResourceType.NODE:
			return NodeEntity.class;
		case ResourceType.NODE_ANNC:
			return NodeAnncEntity.class;
		case ResourceType.MGMT_OBJ:
			return MgmtObjEntity.create(jsonObject.get(MGMT_DEF).getAsBigInteger()).getClass();
		case ResourceType.MGMT_OBJ_ANNC:
			return MgmtObjAnncEntity.create(jsonObject.get(MGMT_DEF).getAsBigInteger()).getClass();
		}
		LOGGER.warn("Cannot create entity for " + type);
		return null;
	}

}
