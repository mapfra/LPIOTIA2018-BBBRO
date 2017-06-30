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
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.SmokeDetector;
import org.eclipse.om2m.sdt.home.driver.Utils;
import org.eclipse.om2m.sdt.home.enocean.Activator.EnOceanSDTDevice;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.modules.SmokeSensor;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.enocean.EnOceanDevice;
import org.osgi.service.enocean.EnOceanMessage;

@SuppressWarnings("rawtypes")
public class EOSmokeDetector extends SmokeDetector implements EnOceanSDTDevice {
	
	static private final String[] NU1_MODES = new String[] {
		"Button A1: 'Switch light on' or 'Dim light down' or 'Move blind closed'",
		"Button A0: 'Switch light off' or 'Dim light up' or 'Move blind open'",
		"Button B1: 'Switch light on' or 'Dim light down' or 'Move blind closed'",
		"Button B0: 'Switch light off' or 'Dim light up' or 'Move blind open'",
	};
	static private final String[] NU0_MODES = new String[] {
		"no button",
		"invalid",
		"invalid",
		"3 or 4 buttons",
	};

	private FaultDetection faultDetection;
	private SmokeSensor smokeSensor;
	
	private boolean val;
	private BooleanDataPoint status;
	private int detectedTime;
	
	private final EnOceanDevice eoDevice;
	private Domain domain;
	private List<ServiceRegistration> registrations;
	private BundleContext context;

	public EOSmokeDetector(EnOceanDevice device, Domain domain,
			BundleContext context) {
		super(Integer.toHexString(device.getChipId()), "0x" + Integer.toHexString(device.getChipId()), domain);
		
		this.eoDevice = device;
		this.domain = domain;
		this.context = context;
		
		try {
			addFaultDetection();
		} catch (Exception e) {
			Activator.logger.warning("Error addFaultDetection", e);
		}
		try {
			addSmokeSensor();
		} catch (Exception e) {
			Activator.logger.warning("Error addSmokeSensor", e);
		}
	}

	@Override
	public void handleEvent(EnOceanMessage msg) {
		byte[] payload = msg.getPayloadBytes();
		if (payload == null) {
			Activator.logger.info("invalid null payload", getClass());
			return;
		}
		String s = "";
		for (int i = 0; i < payload.length; i++) s += payload[i] + ":";
		Activator.logger.info("payload: " + s, getClass());
		byte data = payload[0];
		byte status = (byte) msg.getStatus();
		Activator.logger.info("status: " + status, getClass());
		if (status == 48) { // T21 = 1, NU = 1
			try {
				Activator.logger.info("Rocker 1st action: " + NU1_MODES[(data & 0xe0) >> 5]);
				Activator.logger.info("Rocker 2nd action: " + NU1_MODES[(data & 0x1d) >> 4]);
			} catch (Exception ignored) {
			}
			Activator.logger.info(((data & 0x01) == 0) ? "No 2nd action" : "2nd action valid");
			val = ((data & 0x10) == 0x10); // pressed / released
			Activator.logger.info("" + data + " -> " + (val ? "pressed" : "released"));
		} else if (status == 32) { // T21 = 1, NU = 0
			try {
				Activator.logger.info("Number of buttons pressed simultaneously: " 
						+ NU0_MODES[(data & 0xe0) >> 5]);
			} catch (Exception ignored) {
			}
			val = ((data & 0x10) == 0x10); // pressed / released
			Activator.logger.info("" + data + " -> " + (val ? "pressed" : "released"));
			if (val)
				detectedTime = (int)(System.currentTimeMillis() / 1000);
		} else {
			Activator.logger.warning("Unknown status");
		}
		try {
			Event evt = new Event("Alarm");
			evt.setValue(val);
			evt.addDataPoint(this.status);
			smokeSensor.addEvent(evt);
		} catch (Exception e) {
			Activator.logger.warning("Error notifying listeners", e);
		}
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
	
	private void addSmokeSensor() {
		status = new BooleanDataPoint(DatapointType.alarm) {
			@Override
			public Boolean doGetValue() throws DataPointException {
				Activator.logger.info("alarm: " + val);
				return val;
			}
		};
		smokeSensor = new SmokeSensor("SmokeSensor_" + eoDevice.getChipId(), domain, status);
		addModule(smokeSensor);
		
		smokeSensor.setDetectedTime(new IntegerDataPoint(DatapointType.detectedTime) {
			@Override
			protected Integer doGetValue() throws DataPointException {
				return detectedTime;
			}
			@Override
			protected void doSetValue(Integer v) throws DataPointException {
				detectedTime = v;
			}
		});
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
	}

	@Override
	public Integer getChipId() {
		return eoDevice.getChipId();
	}

}
