/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.om2m.commons.entities.ContainerAnncEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ResourceSerializerDeserializer<T extends ResourceEntity> implements JsonSerializer<T>, JsonDeserializer<T> {

	private static final Log LOGGER = LogFactory.getLog(ResourceSerializerDeserializer.class);

	@Override
	public JsonElement serialize(T arg0, Type arg1, JsonSerializationContext arg2) {

		JsonObject cseBaseEntityObject = new JsonObject();

		for (Method method : arg0.getClass().getMethods()) {
			LOGGER.debug("serialize (method=" + method.getName() + ")");
			if (!((method.getName().startsWith("get")) || (method.getName().startsWith("is")))
					|| (method.getName().equals("getClass"))) {
				continue;
			}

			LOGGER.debug("serialize (method=" + method.getName() + ") - is a getter");
			// at this point, the selected method is a getter
			String propertyName = null;
			if (method.getName().startsWith("get")) {
				propertyName = method.getName().substring(3);
			} else {
				// method name starts with "is"
				propertyName = method.getName().substring(2);
			}
			

			// check if the return type is primitive
			boolean isPrimitive = isSuperPrimitive(method.getReturnType());
			LOGGER.debug("serialize (method=" + method.getName() + ") - returnType=" + method.getReturnType());
			LOGGER.debug("serialize (method=" + method.getName() + ") - isPrimitive=" + isPrimitive);

			if (isPrimitive) {
				try {
					Object result = method.invoke(arg0, new Object[0]);
					LOGGER.debug("serialize (method=" + method.getName() + ") - invoke result=" + result);
					cseBaseEntityObject.add(propertyName, arg2.serialize(result));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				if (List.class.equals(method.getReturnType())) {
					// list
					LOGGER.debug("!!!!!!! list");
					try {
						List result = (List) method.invoke(arg0, new Object[0]);
						JsonArray jsonArray = new JsonArray();
						LOGGER.debug("!!!! List serialize (method=" + method.getName() + ") - invoke result=" + result);

						Type type = method.getGenericReturnType();
						if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
							ParameterizedType pt = (ParameterizedType) type;

							if (pt.getActualTypeArguments().length == 0) {
								continue;
							}
							if (ResourceEntity.class.isAssignableFrom((Class<?>) pt.getActualTypeArguments()[0])) {
								LOGGER.debug("____________________________________________________ComponentType ="
										+ pt.getActualTypeArguments()[0]);
								// continue;
								if (result != null) {
									for (Object o : result) {
										if (o != null) {
											ResourceEntity resourceEntity = (ResourceEntity) o;
											String resourceId = resourceEntity.getResourceID();
											JsonObject jsonObject = new JsonObject();
											jsonObject.add("ResourceID", new JsonPrimitive(resourceId));
											jsonObject.add("ResourceType",
													new JsonPrimitive(resourceEntity.getResourceType()));
											jsonArray.add(jsonObject);
										}
									}
								}
								propertyName = "#" + propertyName;
							} else {
								jsonArray = (JsonArray) arg2.serialize(result);
							}
							cseBaseEntityObject.add(propertyName, jsonArray);
						}

					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (ResourceEntity.class.isAssignableFrom(method.getReturnType())) {
					LOGGER.debug("!!!! assignable to ResourceEntity");
				}

			}

		}

		return cseBaseEntityObject;
	}

	private static boolean isSuperPrimitive(Class clazz) {
		return clazz.isPrimitive() || clazz.getName().startsWith("java.lang.") || clazz.getName().startsWith("java.math.");
	}

	@Override
	public T deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {

		T t = null;

		// we are sure arg0 is a Json object
		JsonObject jsonObject = (JsonObject) arg0;

		// retrieve resource type
		BigInteger resourceType = jsonObject.get("ResourceType").getAsBigInteger();
		t = create(resourceType);

		if (t == null) {
			return t;
		}
		t.setResourceType(resourceType);

		for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			String propertyName = entry.getKey();
			JsonElement value = entry.getValue();

			if (propertyName.equals("ResourceType")) {
				continue;
			}

			if (value.isJsonPrimitive()) {
				JsonPrimitive jsonPrimitive = (JsonPrimitive) value;
				Object valueObject = null;

				Method setterMethod = getSetter(propertyName, (Class) arg1);
				// identifie setter

				if (setterMethod != null) {

					if (jsonPrimitive.isBoolean()) {
						valueObject = jsonPrimitive.getAsBoolean();
					} else if (jsonPrimitive.isString()) {
						valueObject = jsonPrimitive.getAsString();
					} else if (jsonPrimitive.isNumber()) {
						// valueObject = jsonPrimitive.getAsNumber();
						valueObject = arg2.deserialize(jsonPrimitive, setterMethod.getGenericParameterTypes()[0]);
					}

					try {
						setterMethod.invoke(t, new Object[] { valueObject });
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						LOGGER.debug("Exception setter=" + setterMethod.getName() + " - "
								+ setterMethod.getParameterTypes()[0] + "- valueObject.getClass:"
								+ valueObject.getClass());
						e.printStackTrace();
					}
				}

			} else if (value.isJsonArray()) {
				// list case
				JsonArray jsonArray = (JsonArray) value;
				List list = new ArrayList();

				String initialPropertyName = propertyName;
				if (initialPropertyName.startsWith("#")) {
					initialPropertyName = initialPropertyName.substring(1);
				}
				Method setterMethod = getSetter(initialPropertyName, (Class) arg1);

				if (setterMethod != null) {

					if (propertyName.startsWith("#")) {

						// need load them through
						MongoChildLoader<T> m = new MongoChildLoader<>();
						list = (List) m.loadChilds(t, jsonArray);

					} else {
						// inner object
						LOGGER.debug("jsonArray=" + jsonArray.toString());
						Object subObject = arg2.deserialize(jsonArray, setterMethod.getGenericParameterTypes()[0]);
						LOGGER.debug("##############################subObject=" + subObject);
						list = (List) subObject;

					}

					try {
						setterMethod.invoke(t, new Object[] { list });
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}

		return t;

	}

	private static Method getSetter(String propertyName, Class clazz) {
		String setterName = "set" + propertyName;
		for (Method method : clazz.getMethods()) {
			if (setterName.equals(method.getName())) {
				return method;
			}
		}
		return null;
	}

	private final T create(BigInteger type) {
		T t = null;

		switch (type.intValue()) {
		case ResourceType.AE:
			t = (T) new AeEntity();
			break;
		case ResourceType.CSE_BASE:
			t = (T) new CSEBaseEntity();
			break;
		case ResourceType.ACCESS_CONTROL_POLICY:
			t = (T) new AccessControlPolicyEntity();
			break;
		case ResourceType.FLEXCONTAINER:
			t = (T) new FlexContainerEntity();
			break;
		case ResourceType.CONTAINER:
			t = (T) new ContainerAnncEntity();
			break;
		case ResourceType.REMOTE_CSE:
			t = (T) new RemoteCSEEntity();
			break;
		case ResourceType.AE_ANNC:
			t = (T) new AeAnncEntity();
			break;
		case ResourceType.FLEXCONTAINER_ANNC:
			t = (T) new FlexContainerAnncEntity();
			break;
		case ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION:
			t = (T) new DynamicAuthorizationConsultationEntity();
			break;

		}

		return t;
	}

}
