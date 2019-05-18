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

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.osgi.service.dal.DeviceException;
import org.osgi.service.dal.FunctionData;
import org.osgi.service.dal.FunctionEvent;
import org.osgi.service.dal.PropertyMetadata;
import org.osgi.service.dal.functions.MultiLevelControl;
import org.osgi.service.dal.functions.data.LevelData;


import org.eclipse.om2m.dal.driver.custom.functions.BaseFunction;
import org.eclipse.om2m.dal.driver.custom.functions.CustomTypes;
import org.eclipse.om2m.dal.driver.sample.Activator;

/**
 * Speed control function
 */
public class Speed extends BaseFunction implements MultiLevelControl {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(Speed.class);
	public static BigDecimal LEVEL_0 = new BigDecimal(0);
	public static BigDecimal LEVEL_1 = new BigDecimal(1);
	public static BigDecimal LEVEL_2 = new BigDecimal(2);

	private SpeedValueMetadata speedValueMetadata;
	private BigDecimal data = LEVEL_0;

	/**
	 * Constructor
	 * 
	 * @param devUid
	 *            - device UID that this function belong to funcId - function ID
	 * @return
	 */
	public Speed(String devUid, String funcId) {
		super(devUid, CustomTypes.FAN_SPEED, funcId);
	}

	/**
	 * Return the current speed level
	 * 
	 * @param
	 * @return LevelData
	 */
	@Override
	public LevelData getData() throws DeviceException {
		return new LevelData(System.currentTimeMillis(),
				speedValueMetadata.getMetadata(null), data, "");
	}

	/**
	 * Set the speed level
	 * 
	 * @param
	 * @return LevelData
	 */
	@Override
	public void setData(BigDecimal level, String unit) throws DeviceException {

		// Valid check
		if (level.compareTo(LEVEL_0) != 0 && level.compareTo(LEVEL_1) != 0
				&& level.compareTo(LEVEL_2) != 0) {
			throw new DeviceException(String.format(
					"Input level is out of scope (%s, %s, %s)!",
					LEVEL_0.toString(), LEVEL_1.toString(), LEVEL_2.toString()));
		}

		this.data = level;
		Activator.getEventAdmin().postEvent(
				new FunctionEvent(FunctionEvent.TOPIC_PROPERTY_CHANGED,
						this.uid, PROPERTY_DATA, new LevelData(System
								.currentTimeMillis(), speedValueMetadata
								.getMetadata(null), data, "")));
		LOGGER.info("Function event (" + this.uid + "-" + this.data
				+ ") is sent.");
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
		props.put(SERVICE_DESCRIPTION, "Speed control function");
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
		speedValueMetadata = new SpeedValueMetadata();
		propMetadatas.put(PROPERTY_DATA, speedValueMetadata);
	}

	/**
	 * Setup the meta data of function operations
	 * 
	 * @param
	 * @return
	 */
	@Override
	protected void setupOperationMetadata() {

	}

	/**
	 * Property meta data implementation
	 * 
	 */
	private class SpeedValueMetadata implements PropertyMetadata {

		/** Meta data */
		private Map<String, Object> metadatas = null;

		/**
		 * Constructor
		 * 
		 */
		public SpeedValueMetadata() {
			metadatas = new Hashtable<String, Object>();
			metadatas.put(ACCESS,
					new Integer(ACCESS_READABLE | ACCESS_WRITABLE));
			metadatas.put(DESCRIPTION, "The current speed level.");
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
			return new FunctionData[] {
					new LevelData(0, metadatas, LEVEL_0, ""),
					new LevelData(0, metadatas, LEVEL_1, ""),
					new LevelData(0, metadatas, LEVEL_2, "") };
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
