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
import org.eclipse.om2m.sdt.home.modules.Temperature;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class TemperatureDetector extends GenericSensor {
	
	private Temperature temperature;
	
	public TemperatureDetector(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceTemperatureDetector, domain);
		setDeviceSubModelName("TEMPERATURE");
	}
	
	public void addModule(Module module) {
		if (module instanceof Temperature)
			addModule((Temperature)module);
		else 
			super.addModule(module);
	}

	public void addModule(Temperature temperature) {
		this.temperature = temperature;
		super.addModule(temperature);
	}

	public Temperature getTemperature() {
		return temperature;
	}

}
