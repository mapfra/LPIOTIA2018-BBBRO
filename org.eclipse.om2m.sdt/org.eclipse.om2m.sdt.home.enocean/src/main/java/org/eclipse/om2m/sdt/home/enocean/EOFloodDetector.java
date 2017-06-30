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
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.FloodDetector;
import org.eclipse.om2m.sdt.home.driver.Utils;
import org.eclipse.om2m.sdt.home.enocean.Activator.EnOceanSDTDevice;
import org.eclipse.om2m.sdt.home.modules.AbstractAlarmSensor;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.modules.WaterSensor;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.enocean.EnOceanDevice;
import org.osgi.service.enocean.EnOceanMessage;

@SuppressWarnings("rawtypes")
public class EOFloodDetector extends FloodDetector implements EnOceanSDTDevice {

	private final EnOceanDevice eoDevice;
	private Domain domain;
	
	private List<ServiceRegistration> registrations;
	private BundleContext context;

	private FaultDetection faultDetection;
	private AbstractAlarmSensor sensor;

	private boolean floodDetected = false;
	private BooleanDataPoint alarm;

	public EOFloodDetector(EnOceanDevice device, Domain domain, BundleContext context) {
		super(Integer.toHexString(device.getChipId()), "0x" + Integer.toHexString(device.getChipId()), domain);

		this.eoDevice = device;
		this.domain = domain;
		this.context = context;

		try {
			addWaterSensor();
		} catch (Exception e) {
			Activator.logger.warning("Error addBinarySwitch", e);
		}
		try {
			addFaultDetection();
		} catch (Exception e) {
			Activator.logger.warning("Error addFaultDetection", e);
		}
	}

	@Override
	public void handleEvent(EnOceanMessage data) {
		byte[] payload = data.getBytes();
		if (payload == null) {
			Activator.logger.info("invalid null payload", getClass());
			return;
		}
		String s = "";
		for (int i = 0; i < payload.length; i++)
			s += payload[i] + ":";
		Activator.logger.info("payload: " + s, getClass());
		if (payload.length > 6) {
			if (payload[6] == 0x30) {
				// flood detected
				floodDetected = true;
				Event evt = new Event("flood detected");
				evt.addDataPoint(alarm);
				evt.setValue(true);
				sensor.addEvent(evt);
			} else if (payload[6] == 0x20) {
				// back to normal situation
				floodDetected = false;
				Event evt = new Event("normal situation");
				evt.setValue(false);
				evt.addDataPoint(alarm);
				sensor.addEvent(evt);
			} else {
				Activator.logger.info("unknown payload[6]: " + payload[6], getClass());
			}
		} else {
			Activator.logger.info("invalid payload: " + payload, getClass());
		}
	}

	private void addWaterSensor() {
		alarm = new BooleanDataPoint(DatapointType.alarm) {
			@Override
			public Boolean doGetValue() throws DataPointException {
				return floodDetected;
			}
		};
		if (eoDevice.getFunc() == 0x05) {
			sensor = new WaterSensor("WaterSensor_" + eoDevice.getChipId(), domain, alarm);
			addModule((WaterSensor)sensor);
		} else {
			sensor = new AbstractAlarmSensor("AbstractAlarmSensor_" + eoDevice.getChipId(), domain, alarm);
			addModule(sensor);
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
