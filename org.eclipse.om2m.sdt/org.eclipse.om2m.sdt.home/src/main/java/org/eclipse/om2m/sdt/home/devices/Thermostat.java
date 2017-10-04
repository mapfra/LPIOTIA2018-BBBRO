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
import org.eclipse.om2m.sdt.home.modules.RunMode;
import org.eclipse.om2m.sdt.home.modules.Temperature;
import org.eclipse.om2m.sdt.home.modules.Timer;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class Thermostat extends GenericDevice {
	
	private RunMode runMode;
	private Temperature temperature;
	private Timer timer;
	
	public Thermostat(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceThermostat, domain);
	}
	
	public void addModule(Module module) {
		if (module instanceof RunMode)
			addModule((RunMode)module);
		else if (module instanceof Temperature)
			addModule((Temperature)module);
		else if (module instanceof Timer)
			addModule((Timer)module);
		else 
			super.addModule(module);
	}

	public void addModule(RunMode runMode) {
		this.runMode = runMode;
		super.addModule(runMode);
	}

	public void addModule(Temperature temperature) {
		this.temperature = temperature;
		super.addModule(temperature);
	}

	public void addModule(Timer timer) {
		this.timer = timer;
		super.addModule(timer);
	}

	public RunMode getRunMode() {
		return runMode;
	}

	public Temperature getTemperature() {
		return temperature;
	}

	public Timer getTimer() {
		return timer;
	}

}
