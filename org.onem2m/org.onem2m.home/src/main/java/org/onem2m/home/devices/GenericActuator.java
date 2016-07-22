/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.devices;

import org.onem2m.home.modules.BinarySwitch;
import org.onem2m.home.modules.FaultDetection;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;

public class GenericActuator extends GenericDevice {
	
	protected FaultDetection faultDetection;
	protected BinarySwitch binarySwitch;

	public GenericActuator(final String id, final String serial, 
			final DeviceType type, final Domain domain) {
		super(id, serial, type, domain);
	}
	
	public void addModule(BinarySwitch binarySwitch) {
		this.binarySwitch = binarySwitch;
		super.addModule(binarySwitch);
	}

	public void addModule(FaultDetection faultDetection) {
		this.faultDetection = faultDetection;
		super.addModule(faultDetection);
	}

	public BinarySwitch getBinarySwitch() {
		return binarySwitch;
	}

	public FaultDetection getFaultDetection() {
		return faultDetection;
	}

}
