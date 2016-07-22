/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.devices;

import org.onem2m.home.modules.AbstractAlarmSensor;
import org.onem2m.home.modules.FaultDetection;
import org.onem2m.home.modules.MotionSensor;
import org.onem2m.home.modules.SmokeSensor;
import org.onem2m.home.modules.WaterSensor;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;

public class GenericSensor extends GenericDevice {
	
	protected AbstractAlarmSensor sensor;
	
	protected FaultDetection faultDetection;

	public GenericSensor(final String id, final String serial, final Domain domain) {
		super(id, serial, domain);
	}

	public GenericSensor(final String id, final String serial, 
			final DeviceType type, final Domain domain) {
		super(id, serial, type, domain);
	}

	public void addModule(AbstractAlarmSensor sensor) {
		this.sensor = sensor;
		super.addModule(sensor);
		if (sensor instanceof SmokeSensor)
			setDeviceType(DeviceType.deviceSmokeDetector);
		else if (sensor instanceof MotionSensor)
			setDeviceType(DeviceType.deviceMotionDetector);
		else if (sensor instanceof WaterSensor)
			setDeviceType(DeviceType.deviceFloodDetector);
	}

	public void addModule(FaultDetection faultDetection) {
		this.faultDetection = faultDetection;
		super.addModule(faultDetection);
	}

	public AbstractAlarmSensor getSensor() {
		return sensor;
	}

	public FaultDetection getFaultDetection() {
		return faultDetection;
	}

}
