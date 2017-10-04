/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.devices;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.home.modules.AbstractAlarmSensor;
import org.eclipse.om2m.sdt.home.modules.ContactSensor;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.modules.MotionSensor;
import org.eclipse.om2m.sdt.home.modules.SmokeSensor;
import org.eclipse.om2m.sdt.home.modules.WaterSensor;
import org.eclipse.om2m.sdt.home.types.DeviceType;

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
	
	public void addModule(Module module) {
		if (module instanceof FaultDetection)
			addModule((FaultDetection)module);
		else if (module instanceof AbstractAlarmSensor)
			addModule((AbstractAlarmSensor)module);
		else
			super.addModule(module);
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
		else if (sensor instanceof ContactSensor)
			setDeviceType(DeviceType.deviceContactDetector);
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
