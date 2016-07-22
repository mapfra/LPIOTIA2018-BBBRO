/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.devices;

import org.onem2m.home.modules.PushButton;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;

public class SwitchButton extends GenericDevice {
	
	private PushButton buttonSwitch;

	
	public SwitchButton(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceSwitchButton, domain);
	}

	public void addModule(PushButton buttonSwitch) {
		this.buttonSwitch = buttonSwitch;
		super.addModule(buttonSwitch);
	}

	public PushButton getButtonSwitch() {
		return buttonSwitch;
	}

}
