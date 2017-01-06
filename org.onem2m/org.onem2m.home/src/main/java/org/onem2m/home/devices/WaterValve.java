/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.devices;

import org.onem2m.home.modules.Level;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;

public class WaterValve extends GenericActuator {
	
	private Level waterLevel;

	public WaterValve(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceWaterValve, domain);
		setDeviceSubModelName("WATER");
	}
	
	public void addModule(Module module) {
		if (module instanceof Level)
			addModule((Level)module);
		else
			super.addModule(module);
	}

	public void addModule(Level waterLevel) {
		this.waterLevel = waterLevel;
		super.addModule(waterLevel);
	}

	public Level getWaterLevel() {
		return waterLevel;
	}

}
