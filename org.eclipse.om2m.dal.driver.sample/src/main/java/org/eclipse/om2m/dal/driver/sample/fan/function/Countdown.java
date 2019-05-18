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
import org.eclipse.om2m.dal.driver.custom.functions.OperationMetadataImpl;
import org.eclipse.om2m.dal.driver.sample.Activator;


/**
 * Count down clock function
 */
public class Countdown extends BaseFunction implements MultiLevelControl {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(Countdown.class);

	/** Constant definition */
	private static final int ONE_SENCOND = 100;
	private static final int ONE_MINUTE = 60;
	public static final BigDecimal MIN_VALUE = new BigDecimal(0);
	public static final BigDecimal MAX_VALUE = new BigDecimal(5);
	private static final BigDecimal STEP = new BigDecimal(1);

	/** Function operations */
	private static final String OPERATION_INCREASE = "increase";
	private static final String OPERATION_REDUCE = "reduce";

	/** Meta data of function property */
	private CountdownMetadata countdownMetadata;
	/** The count down clock data */
	private BigDecimal data = MIN_VALUE;
	/** Count down clock data in seconds */
	private int countdownSec = 0;

	/**
	 * Constructor
	 * 
	 * @param  devUid - device UID that this function belong to
	 *         funcId - function ID
	 * @return 
	 */
	public Countdown(String devUid, String funcId) {

		super(devUid, CustomTypes.FAN_COUNTDOWN, funcId);

		/** Automatically count down thread */
		new Thread() {
			public void run() {

				while (true) {
					try {
						Thread.sleep(ONE_SENCOND);
					} catch (Exception e) {
						LOGGER.error(e);
					}

					// Generate function event every minute when the count down clock is running
					if (data.compareTo(MIN_VALUE) > 0) {
						if ((--countdownSec) % ONE_MINUTE == 0) {
							data = data.subtract(STEP);

							Activator
									.getEventAdmin()
									.postEvent(
											new FunctionEvent(
													FunctionEvent.TOPIC_PROPERTY_CHANGED,
													uid,
													PROPERTY_DATA,
													new LevelData(
															System.currentTimeMillis(),
															countdownMetadata
																	.getMetadata(null),
															data, "")));
							LOGGER.info("Function event(" + uid + "-"
									+ data + ") is sent.");
						}
					}
				}

			}
		}.start();
	}

	/**
	 * return the current data of count down clock
	 * 
	 * @param  
	 * @return LevelData
	 */
	@Override
	public LevelData getData() throws DeviceException {

		return new LevelData(System.currentTimeMillis(),
				countdownMetadata.getMetadata(null), data, "");
	}

	/**
	 * Set the data of count down clock
	 * 
	 * @param  
	 * @return LevelData
	 */
	@Override
	public void setData(BigDecimal level, String unit) throws DeviceException {
		
		if (level.compareTo(MIN_VALUE) < 0 || level.compareTo(MAX_VALUE) > 0) {
			throw new DeviceException(String.format(
					"Input level is out of scope [%s, %s]!",
					MIN_VALUE.toString(), MAX_VALUE.toString()));
		}

		this.data = level;
		this.countdownSec = this.data.intValue() * ONE_MINUTE;
		
		Activator.getEventAdmin().postEvent(
				new FunctionEvent(FunctionEvent.TOPIC_PROPERTY_CHANGED,
						this.uid, PROPERTY_DATA, new LevelData(System
								.currentTimeMillis(), countdownMetadata
								.getMetadata(null), data, "")));
		LOGGER.info("Function event (" + this.uid + "-" + this.data
				+ ") is sent.");
	}

	/**
	 * setup the function properties
	 * 
	 * @param  
	 * @return
	 */
	@Override
	protected void setupFuncProperties() {
		props.put(SERVICE_VERSION, "1.0");
		props.put(SERVICE_DESCRIPTION, "AudioVolume function");
		props.put(SERVICE_OPERATION_NAMES, new String[] { OPERATION_INCREASE,
				OPERATION_REDUCE });
		props.put(SERVICE_PROPERTY_NAMES, new String[] { PROPERTY_DATA });
	}

	/**
	 * Function operation, increase the value of count down clock
	 * 
	 * @param  
	 * @return
	 */
	public void increase() throws DeviceException {
		setData(data.add(STEP), "");
	}

	/**
	 * Function operation, reduce the value of count down clock
	 * 
	 * @param  
	 * @return
	 */
	public void reduce() throws DeviceException {
		setData(data.subtract(STEP), "");
	}

	/**
	 * Setup the meta data of function property
	 * 
	 * @param  
	 * @return
	 */
	@Override
	protected void setupPropertyMetadata() {
		countdownMetadata = new CountdownMetadata();
		propMetadatas.put(PROPERTY_DATA, countdownMetadata);
	}

	/**
	 * Setup the meta data of function operation
	 * 
	 * @param  
	 * @return
	 */
	@Override
	protected void setupOperationMetadata() {
		oprMetadatas.put(OPERATION_INCREASE, new OperationMetadataImpl(
				"Increase the value of count down clock", null, null));
		oprMetadatas.put(OPERATION_REDUCE, new OperationMetadataImpl(
				"Reduce the value of count down clock", null, null));
	}

	
	/**
	 * Property meta data implementation
	 */
	private class CountdownMetadata implements PropertyMetadata {

		private Map<String, Object> metadatas = null;

		/**
		 * Constructor
		 */
		public CountdownMetadata() {
			metadatas = new Hashtable<String, Object>();
			metadatas.put(ACCESS, new Integer(ACCESS_READABLE | ACCESS_WRITABLE
					| ACCESS_EVENTABLE));
			metadatas.put(DESCRIPTION,
					"The current value of count down clock.");
			metadatas.put(UNITS, new String[] {"Minute"});
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Map getMetadata(String unit) {
			return metadatas;
		}

		@Override
		public FunctionData getStep(String unit) {
			return new LevelData(0, metadatas, STEP, "");
		}

		@Override
		public FunctionData[] getEnumValues(String unit) {
			return null;
		}

		@Override
		public FunctionData getMinValue(String unit) {
			return new LevelData(0, metadatas, MIN_VALUE, "");
		}

		@Override
		public FunctionData getMaxValue(String unit) {
			return new LevelData(0, metadatas, MAX_VALUE, "");
		}

	}

}
