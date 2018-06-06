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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@SuppressWarnings("unchecked")
public class ResourceSerializerDeserializer<T extends ResourceEntity> 	
	implements Constants, JsonSerializer<T>, JsonDeserializer<T> {

	private static final Log LOGGER = LogFactory.getLog(ResourceSerializerDeserializer.class);
	
	private static final String GET = "get";
	private static final String IS = "is";
	private static final String SET = "set";

	@Override
	public JsonElement serialize(T entity, Type xxx, JsonSerializationContext ctxt) {
		LOGGER.debug(" * IN * serialize " + entity);
		JsonObject json = new JsonObject();

		for (Method method : entity.getClass().getMethods()) {
			if (!(method.getName().startsWith(GET) || method.getName().startsWith(IS))
					|| method.getName().equals("getClass")) {
				continue;
			}

			// at this point, the selected method is a getter
			String propertyName = method.getName().startsWith(GET) 
					? method.getName().substring(3)
					: method.getName().substring(2); // method name starts with "is"
			
			// check if the return type is primitive
			boolean isPrimitive = isSuperPrimitive(method.getReturnType());
			LOGGER.debug("serialize (method=" + method.getName() + ") - returnType=" 
					+ method.getReturnType() + " - isPrimitive=" + isPrimitive);

			if (isPrimitive) {
				try {
					Object result = method.invoke(entity, new Object[0]);
					LOGGER.debug("serialize (method=" + method.getName() + ") - invoke result=" + result);
					json.add(propertyName, ctxt.serialize(result));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					LOGGER.warn("", e);
				}
			} else if (ResourceEntity.class.isAssignableFrom(method.getReturnType())) {
				LOGGER.debug("Directly assignable to ResourceEntity " + method.getName());
			} else if (List.class.equals(method.getReturnType())) {
				// list
				try {
					List<?> result = (List<?>) method.invoke(entity, new Object[0]);
					JsonArray jsonArray = new JsonArray();
					LOGGER.debug("List serialize (method=" + method.getName() 
					+ ") - invoke result=" + result);

					Type type = method.getGenericReturnType();
					if (! ParameterizedType.class.isAssignableFrom(type.getClass())) {
						continue;
					}
					ParameterizedType pt = (ParameterizedType) type;
					if (pt.getActualTypeArguments().length == 0) {
						continue;
					}
					if (ResourceEntity.class.isAssignableFrom((Class<?>) pt.getActualTypeArguments()[0])) {
						LOGGER.debug("ComponentType =" + pt.getActualTypeArguments()[0]);
						// continue;
						if (result != null) {
							for (Object obj : result) {
								if (obj != null) {
									ResourceEntity resourceEntity = (ResourceEntity) obj;
									JsonObject jsonObject = new JsonObject();
									jsonObject.add(RES_ID, new JsonPrimitive(resourceEntity.getResourceID()));
									jsonObject.add(RES_TYPE, new JsonPrimitive(resourceEntity.getResourceType()));
									if (obj instanceof MgmtObjEntity) {
										jsonObject.add(MGMT_DEF,
												new JsonPrimitive(((MgmtObjEntity)obj).getMgmtDefinition()));
									} else if (obj instanceof MgmtObjAnncEntity) {
										jsonObject.add(MGMT_DEF,
												new JsonPrimitive(((MgmtObjAnncEntity)obj).getMgmtDefinition()));
									}
									jsonArray.add(jsonObject);
								}
							}
						}
						propertyName = "#" + propertyName;
					} else {
						jsonArray = (JsonArray) ctxt.serialize(result);
					}
					json.add(propertyName, jsonArray);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					LOGGER.warn("", e);
				}
			}
		}
		LOGGER.debug(" * OUT * serialize " + json);
		return json;
	}

	private static boolean isSuperPrimitive(Class<?> clazz) {
		return clazz.isPrimitive() || clazz.getName().startsWith("java.lang.") 
				|| clazz.getName().startsWith("java.math.");
	}

	@Override
	public T deserialize(JsonElement obj, Type xxx, JsonDeserializationContext ctxt) 
			throws JsonParseException {
		LOGGER.debug(" * IN * deserialize " + obj);
		// we are sure obj is a Json object
		JsonObject jsonObject = (JsonObject) obj;
		// retrieve resource type
		T entity = create(jsonObject);
		if (entity == null) {
			return null;
		}
		entity.setResourceType(jsonObject.get(RES_TYPE).getAsBigInteger());
		
		for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			String propertyName = entry.getKey();
			JsonElement value = entry.getValue();

			if (propertyName.equals(RES_TYPE)) {
				continue;
			}

			if (value.isJsonPrimitive()) {
				JsonPrimitive jsonPrimitive = (JsonPrimitive) value;
				Object param = null;

				Method setterMethod = getSetter(propertyName, entity.getClass());
				// identify setter

				if (setterMethod == null) {
					LOGGER.debug("Setter not found for " + propertyName);
				} else {
					if (jsonPrimitive.isBoolean()) {
						param = jsonPrimitive.getAsBoolean();
					} else if (jsonPrimitive.isString()) {
						param = jsonPrimitive.getAsString();
					} else if (jsonPrimitive.isNumber()) {
						// valueObject = jsonPrimitive.getAsNumber();
						param = ctxt.deserialize(jsonPrimitive, 
								setterMethod.getGenericParameterTypes()[0]);
					}

					try {
						setterMethod.invoke(entity, new Object[] { param });
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						LOGGER.info("Exception setter=" + setterMethod.getName() + " - "
								+ setterMethod.getParameterTypes()[0] + "- valueObject.getClass:"
								+ param.getClass(), e);
					}
				}
			} else if (value.isJsonArray()) {
				// list case
				JsonArray jsonArray = (JsonArray) value;
				List<?> params = new ArrayList<>();

				String initialPropertyName = propertyName;
				if (initialPropertyName.startsWith("#")) {
					initialPropertyName = initialPropertyName.substring(1);
				}
				Method setterMethod = getSetter(initialPropertyName, entity.getClass());
				if (setterMethod == null) {
					LOGGER.debug("Setter not found for " + propertyName);
				} else {
					if (jsonArray.size() > 0) {
						if (propertyName.startsWith("#")) {
							// need load them through
							MongoChildLoader<T> m = new MongoChildLoader<>();
							params = (List<?>) m.loadChildren(entity, jsonArray);
						} else {
							// inner object
							params = (List<?>) ctxt.deserialize(jsonArray, 
									setterMethod.getGenericParameterTypes()[0]);
							LOGGER.debug("jsonArray=" + jsonArray.toString() + " -> subObject=" + params);
						} 
					}
					try {
						setterMethod.invoke(entity, new Object[] { params });
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						LOGGER.warn("", e);
					}
				}
			}
		}
		LOGGER.debug(" * OUT * deserialize " + entity);
		return entity;
	}

	private static Method getSetter(String propertyName, Class<?> clazz) {
		String setterName = SET + propertyName;
		for (Method method : clazz.getMethods()) {
			if (setterName.equals(method.getName())) {
				return method;
			}
		}
		return null;
	}

	private final T create(JsonObject jsonObject) {
		BigInteger type = jsonObject.get(RES_TYPE).getAsBigInteger();
		switch (type.intValue()) {
		case ResourceType.AE:
			return (T) new AeEntity();
		case ResourceType.CSE_BASE:
			return (T) new CSEBaseEntity();
		case ResourceType.ACCESS_CONTROL_POLICY:
			return (T) new AccessControlPolicyEntity();
		case ResourceType.FLEXCONTAINER:
			return (T) new FlexContainerEntity();
		case ResourceType.CONTAINER:
			return (T) new ContainerEntity();
		case ResourceType.CONTENT_INSTANCE:
			return (T) new ContentInstanceEntity();
		case ResourceType.REMOTE_CSE:
			return (T) new RemoteCSEEntity();
		case ResourceType.AE_ANNC:
			return (T) new AeAnncEntity();
		case ResourceType.FLEXCONTAINER_ANNC:
			return (T) new FlexContainerAnncEntity();
		case ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION:
			return (T) new DynamicAuthorizationConsultationEntity();
		case ResourceType.NODE:
			return (T) new NodeEntity();
		case ResourceType.NODE_ANNC:
			return (T) new NodeAnncEntity();
		case ResourceType.MGMT_OBJ:
			return (T) MgmtObjEntity.create(jsonObject.get(MGMT_DEF).getAsBigInteger());
		case ResourceType.MGMT_OBJ_ANNC:
			return (T) MgmtObjAnncEntity.create(jsonObject.get(MGMT_DEF).getAsBigInteger());
		}
		LOGGER.warn("Cannot create entity for " + type);
		return null;
	}

}
