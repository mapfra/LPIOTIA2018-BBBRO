/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.devices;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.home.modules.MotionSensor;
import org.eclipse.om2m.sdt.home.modules.SmokeSensor;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class MotionDetector extends GenericSensor {
	
	public MotionDetector(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceMotionDetector, domain);
		setDeviceSubModelName("MOTION");
	}

	public MotionSensor getMotionSensor() {
		return (MotionSensor) sensor;
	}

}
