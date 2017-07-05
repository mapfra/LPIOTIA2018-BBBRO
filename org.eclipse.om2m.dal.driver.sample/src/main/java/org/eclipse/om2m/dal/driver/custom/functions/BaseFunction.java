/*******************************************************************************
 * Copyright (c) 2017 Huawei Technologies Co., Ltd (http://www.huawei.com)
 * Huawei Base, Bantian,Longgang District ,Shenzhen ,Guangdong ,China
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Seven Ganlu : Developer
 *
 * New contributors :
 *******************************************************************************/

package org.eclipse.om2m.dal.driver.custom.functions;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.dal.Function;
import org.osgi.service.dal.OperationMetadata;
import org.osgi.service.dal.PropertyMetadata;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.dal.driver.sample.Activator;

/**
 * Base function class, provide common properties and operations.
 */
public abstract class BaseFunction {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(BaseFunction.class);

	/** Function properties and metadatas */
	protected Dictionary<String, Object> props = null;
	protected Dictionary<String, PropertyMetadata> propMetadatas = null;
	protected Dictionary<String, OperationMetadata> oprMetadatas = null;

	protected String funcionId = "";
	protected String uid = "";
	protected String deviceUid = "";
	
	/** Function service referenc and registration*/
	protected ServiceReference<?> funcRef = null;
	protected ServiceRegistration<?> funcReg = null;

	
	/**
	 * Constructor
	 */
	public BaseFunction(String deviceUid, String serviceType, String funcionId) {

		// Construct the function UID
		this.funcionId = funcionId;
		this.deviceUid = deviceUid;
		this.uid = deviceUid + ":" + funcionId;

		// Set the basic function service properties
		props = new Hashtable<String, Object>();
		props.put(Function.SERVICE_UID, uid);
		props.put(Function.SERVICE_TYPE, serviceType);
		props.put(Function.SERVICE_DEVICE_UID, deviceUid);

		propMetadatas = new Hashtable<String, PropertyMetadata>();
		oprMetadatas = new Hashtable<String, OperationMetadata>();

		// Setup the function properties for registering function service
		setupFuncProperties();
		setupPropertyMetadata();
		setupOperationMetadata();

		// Register function service
		LOGGER.info("Register function (" + uid + ") ...");
		funcReg = Activator.getContext().registerService(
				Function.class.getName(), this, props);
		funcRef = funcReg.getReference();
		LOGGER.info("Function (" + uid + ") is registered.");
	}

	/**
	 * Unregister the function service.
	 * 
	 * @param
	 * @return
	 */
	public void remove() {
		funcReg.unregister();
	}

	/**
	 * Return the function service property by specifying the name
	 * 
	 * @param
	 * @return Object
	 */
	public Object getServiceProperty(String propKey) {

		if (funcRef != null) {
			return funcRef.getProperty(propKey);
		}

		return props.get(propKey);
	}

	/**
	 * Return the property meta data by specifying the property name
	 * 
	 * @param
	 * @return PropertyMetadata
	 */
	public PropertyMetadata getPropertyMetadata(String propertyName) {
		return propMetadatas.get(propertyName);
	}

	/**
	 * Return the operation meta data by specifying the operation name
	 * 
	 * @param
	 * @return OperationMetadata
	 */
	public OperationMetadata getOperationMetadata(String operationName) {
		return oprMetadatas.get(operationName);
	}

	/**
	 * Return all function property keys
	 * 
	 * @param
	 * @return String[]
	 */
	public String[] getServicePropertyKeys() {

		if (funcRef != null) {
			return funcRef.getPropertyKeys();
		}

		String[] keys = new String[props.size()];

		int count = 0;
		
		for (Enumeration<String> key = props.keys(); key.hasMoreElements();) {
			keys[count++] = key.nextElement();
		}

		return keys;
	}

	/**
	 * abstract functions, should be implemented by subclass
	 * 
	 * @param
	 * @return
	 */
	protected abstract void setupFuncProperties();

	protected abstract void setupPropertyMetadata();

	protected abstract void setupOperationMetadata();
}
