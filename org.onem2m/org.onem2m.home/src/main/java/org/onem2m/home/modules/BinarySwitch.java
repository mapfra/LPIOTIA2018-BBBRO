/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.modules;

import org.onem2m.home.actions.Toggle;
import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.ActionException;
import org.onem2m.sdt.impl.DataPointException;

public class BinarySwitch extends Module {
	
	private BooleanDataPoint powerState;
	
	private Toggle toggle;
	
	public BinarySwitch(final String name, final Domain domain, 
			BooleanDataPoint powerState) {
		super(name, domain, ModuleType.binarySwitch.getDefinition());

		this.powerState = powerState;
		this.powerState.setDoc("The current status of the BinarySwitch. \"True\" indicates turned-on, and \"False\" indicates turned-off.");
		addDataPoint(this.powerState);
	}

	public boolean getPowerState() throws DataPointException, AccessException {
		return powerState.getValue();
	}

	public void setPowerState(boolean v) throws DataPointException, AccessException {
		powerState.setValue(v);
	}

	public Toggle getToggle() {
		return toggle;
	}

	public void setToggle(Toggle toggle) {
		this.toggle = toggle;
		this.toggle.setDoc("Toggle the switch");
		addAction(toggle);
	}
	
	public void toggle() throws ActionException, AccessException {
		if (toggle == null)
			throw new ActionException("Not implemented");
		toggle.toggle();
	}

}
