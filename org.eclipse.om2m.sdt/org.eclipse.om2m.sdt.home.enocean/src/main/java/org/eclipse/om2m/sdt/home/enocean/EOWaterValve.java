/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.enocean;

import java.util.List;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Event;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.EnumDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.WaterValve;
import org.eclipse.om2m.sdt.home.driver.Utils;
import org.eclipse.om2m.sdt.home.enocean.Activator.EnOceanSDTDevice;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.LiquidLevel;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.enocean.EnOceanDevice;
import org.osgi.service.enocean.EnOceanHandler;
import org.osgi.service.enocean.EnOceanMessage;
import org.osgi.service.enocean.EnOceanRPC;

@SuppressWarnings("rawtypes")
public class EOWaterValve extends WaterValve implements EnOceanSDTDevice {

	protected EnOceanDevice eoDevice;
	
	protected Domain domain;
	private List<ServiceRegistration> registrations;
	private BundleContext context;

	private FaultDetection faultDetection;
	private org.eclipse.om2m.sdt.home.modules.LiquidLevel waterLevel;
	
	private LiquidLevel liquidLevelDP;
	private Integer liquidLevel = LiquidLevel.maximum;

	public EOWaterValve(EnOceanDevice device, Domain domain,
			String serial, BundleContext context) {
		super(Integer.toString(device.getChipId()), serial, domain);
		try {
			setDeviceManufacturer("0x" + Integer.toHexString(eoDevice.getManufacturer()));
		} catch (Exception ignored) {
		}
		this.domain = domain;
		this.eoDevice = device;
		this.context = context;

		try {
			addWaterLevel();
		} catch (Exception e) {
			Activator.logger.warning("Error addOpenLevel", e);
		}
		try {
			addFaultDetection();
		} catch (Exception e) {
			Activator.logger.warning("Error addFaultDetection", e);
		}
	}

	@Override
	public Integer getChipId() {
		return eoDevice.getChipId();
	}

	@Override
	public void register() {
		registrations = Utils.register(this, context);
	}
	
	@Override
	public void unregister() {
		for (ServiceRegistration reg : registrations) {
			reg.unregister();
		}
		registrations.clear();
	}

	@Override
	public void handleEvent(EnOceanMessage data) {
		byte[] payload = data.getPayloadBytes();
		if (payload == null) {
			Activator.logger.info("invalid null payload", getClass());
			return;
		}
		String s = "";
		for (int i = 0; i < payload.length; i++) s += payload[i] + ":";
		Activator.logger.info("payload: " + s, getClass());
		byte feedback = payload[0];
		String msg;
		if (feedback == 1) {
			msg = "Closed";
			liquidLevel = LiquidLevel.zero;
		} else if (feedback == 2) {
			msg = "Opened";
			liquidLevel = LiquidLevel.maximum;
		} else {
			msg = "Not defined";
			liquidLevel = null;
		}
		if (liquidLevel != null) {
			Event evt = new Event("Status: " + msg);
			evt.setValue(liquidLevel);
			evt.addDataPoint(liquidLevelDP);
			waterLevel.addEvent(evt);
		}
	}

	private void addWaterLevel() {
		liquidLevelDP = new LiquidLevel(new EnumDataPoint<Integer>(null) {
			@Override
			public void doSetValue(Integer value) throws DataPointException {
				try {
					turn(value);
				} catch (DataPointException e) {
					throw e;
				} catch (Exception e) {
					throw new DataPointException("", e);
				}
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				if (liquidLevel == null)
					throw new DataPointException("Unknown");
				return liquidLevel;
			}
		});
		waterLevel = new org.eclipse.om2m.sdt.home.modules.LiquidLevel("Level_" + eoDevice.getChipId(), domain, liquidLevelDP);
		
		Activator.logger.info("add LiquidLevel module: " + waterLevel);
		addModule(waterLevel);
	}

	private void addFaultDetection() {
		faultDetection = new FaultDetection("FaultDetection_" + eoDevice.getChipId(), domain, 
				new BooleanDataPoint(DatapointType.status) {
			@Override
			public Boolean doGetValue() throws DataPointException {
				return false;
			}
		});
		addModule(faultDetection);
	}

	private void turn(int value) throws DataPointException {
		final String command;
		switch (value) {
		case LiquidLevel.zero:
			command = "HARDCODED_TURN_OFF";
			break;
		case LiquidLevel.maximum:
			command = "HARDCODED_APPAIR_TURN_ON";
			break;
		default:
			throw new DataPointException("Invalid value " + value);
		}
		EnOceanRPC appairRPC = new EnOceanRPC() {
			private int senderId = 0x00000000;
			public void setSenderId(int chipId) {
				this.senderId = chipId;
			}
			public int getSenderId() {
				return senderId;
			}
			public byte[] getPayload() {
				return null;
			}
			public int getManufacturerId() {
				return -1;
			}
			public int getFunctionId() {
				return -1;
			}
			public String getName() {
				return command;
			}
		};

		EnOceanHandler handlerTurnRPC = new EnOceanHandler() {
			public void notifyResponse(EnOceanRPC enOceanRPC, byte[] payload) {
				Activator.logger.info("enOceanRPC: " + enOceanRPC + ", payload: " + payload,
						EOWaterValve.class);
			}
		};

		Activator.logger.info("Water pump available, " 
				+ ((value == LiquidLevel.zero) ? "close it!" : "Open it!"),
			EOWaterValve.class);
		eoDevice.invoke(appairRPC, handlerTurnRPC);
		Activator.logger.info("OK!", EOWaterValve.class);
	}

}
