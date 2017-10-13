/*******************************************************************************
 * Copyright (c) 2017 Deutsche Telekom.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.devices;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.home.modules.NumberValue;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class DeviceNumberDevice extends GenericActuator {
	
	private NumberValue numberValue;

	public DeviceNumberDevice(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceNumberDevice, domain);
	}
	
	public void addModule(Module module) {
		if (module instanceof org.eclipse.om2m.sdt.home.modules.NumberValue)
			addModule((NumberValue)module);
		else
			super.addModule(module);
	}

	public void addModule(NumberValue numberValue) {
		this.numberValue = numberValue;
		super.addModule(numberValue);
	}

	public NumberValue getNumberValue() {
		return numberValue;
	}

}
