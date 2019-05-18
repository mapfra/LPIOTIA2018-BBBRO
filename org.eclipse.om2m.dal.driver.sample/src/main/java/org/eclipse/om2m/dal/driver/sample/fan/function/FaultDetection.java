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

import org.osgi.service.dal.FunctionData;
import org.osgi.service.dal.FunctionEvent;
import org.osgi.service.dal.PropertyMetadata;
import org.osgi.service.dal.functions.Alarm;
import org.osgi.service.dal.functions.data.AlarmData;

import org.eclipse.om2m.dal.driver.custom.functions.BaseFunction;
import org.eclipse.om2m.dal.driver.custom.functions.CustomTypes;
import org.eclipse.om2m.dal.driver.sample.Activator;

/**
 * Fault detection function
 */
public class FaultDetection extends BaseFunction implements Alarm {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(FaultDetection.class);

	/** Property meta data */
	private AlarmMetadata metaData;
	/** Alarm data */
	private AlarmData alarm;

	/**
	 * Constructor
	 * 
	 * @param  devUid - device UID that this function belong to
	 *         funcId - function ID
	 * @return 
	 */
	public FaultDetection(String devUid, String funcId) {
		super(devUid, CustomTypes.FAULT_DETECTION, funcId);
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
		props.put(SERVICE_DESCRIPTION, "FaultDetection function");
		props.put(SERVICE_PROPERTY_NAMES, new String[] { PROPERTY_ALARM });
	}

	/**
	 * Setup the meta data of function property
	 * 
	 * @param  
	 * @return 
	 */
	@Override
	protected void setupPropertyMetadata() {
		metaData = new AlarmMetadata();
		propMetadatas.put(PROPERTY_ALARM, metaData);
	}

	/**
	 * Setup the meta data of function operation
	 * 
	 * @param  
	 * @return 
	 */
	@Override
	protected void setupOperationMetadata() {

	}

	/**
	 * Return the current alarm data
	 * 
	 * @param  
	 * @return  AlarmData
	 */
	public AlarmData getAlarm() {
		return alarm;
	}

	/**
	 * Set a new alarm, generate a alarm event
	 * 
	 * @param  alarm
	 * @return
	 */
	public void setAlarm(AlarmData alarm) {
		this.alarm = alarm;
		Activator.getEventAdmin().postEvent(
				new FunctionEvent(FunctionEvent.TOPIC_PROPERTY_CHANGED,
						this.uid, PROPERTY_ALARM, alarm));
		LOGGER.info("Function event (" + this.uid + "-" + this.alarm
				+ ") is sent.");
	}

	/**
	 * Property meta data implementation
	 */
	private class AlarmMetadata implements PropertyMetadata {

		/** Meta data */
		private Map<String, Object> metadatas = null;

		/**
		 * Constructor
		 */
		public AlarmMetadata() {
			metadatas = new Hashtable<String, Object>();
			metadatas.put(ACCESS, new Integer(ACCESS_READABLE | ACCESS_WRITABLE
					| ACCESS_EVENTABLE));
			metadatas.put(DESCRIPTION, "The alarm data of fault detection.");
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
			return null;
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
