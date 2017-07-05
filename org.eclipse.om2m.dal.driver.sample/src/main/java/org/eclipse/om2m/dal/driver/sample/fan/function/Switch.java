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

package org.eclipse.om2m.dal.driver.sample.fan.function;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.osgi.service.dal.DeviceException;
import org.osgi.service.dal.FunctionData;
import org.osgi.service.dal.FunctionEvent;
import org.osgi.service.dal.PropertyMetadata;
import org.osgi.service.dal.functions.BooleanControl;
import org.osgi.service.dal.functions.Types;
import org.osgi.service.dal.functions.data.BooleanData;

import org.eclipse.om2m.dal.driver.custom.functions.BaseFunction;
import org.eclipse.om2m.dal.driver.custom.functions.OperationMetadataImpl;
import org.eclipse.om2m.dal.driver.sample.Activator;

/**
 * Power switch function
 */
public class Switch extends BaseFunction implements BooleanControl {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(Switch.class);

	private boolean data = false;
	private PowerStateMetadata metaData;

	/**
	 * Constructor
	 * 
	 * @param devUid
	 *            - device UID that this function belong to funcId - function ID
	 * @return
	 */
	public Switch(String devUid, String funcId) {
		super(devUid, Types.POWER, funcId);
	}

	/**
	 * Setup the function properties
	 * 
	 * @param
	 * @return
	 */
	@Override
	protected void setupFuncProperties() {
		props.put(SERVICE_VERSION, "1.0");
		props.put(SERVICE_DESCRIPTION, "Power switch function");
		props.put(SERVICE_OPERATION_NAMES, new String[] { OPERATION_INVERSE,
				OPERATION_SET_TRUE, OPERATION_SET_FALSE });
		props.put(SERVICE_PROPERTY_NAMES, new String[] { PROPERTY_DATA });

	}

	/**
	 * Setup the meta data of function properties
	 * 
	 * @param
	 * @return
	 */
	@Override
	protected void setupPropertyMetadata() {
		metaData = new PowerStateMetadata();
		propMetadatas.put(PROPERTY_DATA, metaData);
	}

	/**
	 * Setup the meta data of function operations
	 * 
	 * @param
	 * @return
	 */
	@Override
	protected void setupOperationMetadata() {
		oprMetadatas.put(OPERATION_INVERSE, new OperationMetadataImpl(
				"Inverse the power state", null, null));
		oprMetadatas.put(OPERATION_SET_TRUE, new OperationMetadataImpl(
				"Set the power state to ON", null, null));
		oprMetadatas.put(OPERATION_SET_FALSE, new OperationMetadataImpl(
				"Set the power state to OFF", null, null));
	}

	/**
	 * Return the current switch data
	 * 
	 * @param
	 * @return BooleanData
	 */
	@Override
	public BooleanData getData() throws DeviceException {
		return new BooleanData(System.currentTimeMillis(),
				metaData.getMetadata(null), data);
	}

	/**
	 * Set the power switch data
	 * 
	 * @param data
	 *            - power state
	 * @return
	 */
	@Override
	public void setData(boolean data) throws DeviceException {
		this.data = data;

		Activator.getEventAdmin().postEvent(
				new FunctionEvent(FunctionEvent.TOPIC_PROPERTY_CHANGED,
						this.uid, PROPERTY_DATA, new BooleanData(System
								.currentTimeMillis(), metaData
								.getMetadata(null), data)));
		LOGGER.info("Function event (" + this.uid + "-" + this.data
				+ ") is sent.");
	}

	/**
	 * Inverse the power switch data
	 * 
	 * @param
	 * @return
	 */
	@Override
	public void inverse() throws DeviceException {
		setData(!this.data);
	}

	/**
	 * Turn on the power
	 * 
	 * @param
	 * @return
	 */
	@Override
	public void setTrue() throws DeviceException {
		setData(true);
	}

	/**
	 * Turn off the power
	 * 
	 * @param
	 * @return
	 */
	@Override
	public void setFalse() throws DeviceException {
		setData(false);
	}

	/**
	 * Property meta data implementation
	 */
	private class PowerStateMetadata implements PropertyMetadata {

		/** Meta data */
		private Map<String, Object> metadatas = null;

		/**
		 * Constructor
		 */
		public PowerStateMetadata() {
			metadatas = new Hashtable<String, Object>();
			metadatas.put(ACCESS, new Integer(ACCESS_READABLE | ACCESS_WRITABLE
					| ACCESS_EVENTABLE));
			metadatas
					.put(DESCRIPTION,
							"The current status of the power switch.");
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Map getMetadata(String unit) {
			return metadatas;
		}

		@Override
		public FunctionData getStep(String unit) {
			return null;
		}

		@Override
		public FunctionData[] getEnumValues(String unit) {
			FunctionData[] funcDatas = new FunctionData[2];
			funcDatas[0] = new BooleanData(0, metadatas, true);
			funcDatas[1] = new BooleanData(0, metadatas, false);
			return funcDatas;
		}

		@Override
		public FunctionData getMinValue(String unit) {
			return null;
		}

		@Override
		public FunctionData getMaxValue(String unit) {
			return null;
		}
	}

}