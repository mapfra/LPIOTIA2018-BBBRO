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
import org.eclipse.om2m.sdt.home.modules.PushButton;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class SwitchButton extends GenericDevice {
	
	private PushButton buttonSwitch;

	
	public SwitchButton(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceSwitchButton, domain);
	}
	
	public void addModule(Module module) {
		if (module instanceof PushButton)
			addModule((PushButton)module);
		else 
			super.addModule(module);
	}

	public void addModule(PushButton buttonSwitch) {
		this.buttonSwitch = buttonSwitch;
		super.addModule(buttonSwitch);
	}

	public PushButton getButtonSwitch() {
		return buttonSwitch;
	}

}
