/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.Action;
import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.args.Command;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.ActionException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.actions.Toggle;
import org.eclipse.om2m.sdt.home.types.ActionType;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class BinarySwitch extends Module {
	
	private BooleanDataPoint powerState;
	
	private Action toggle;
	
	public BinarySwitch(final String name, final Domain domain, 
			BooleanDataPoint powerState) {
		this(name, domain, powerState, ModuleType.binarySwitch);
	}
	
	protected BinarySwitch(final String name, final Domain domain,
			BooleanDataPoint powerState, ModuleType type) {
		super(name, domain, type);

		if ((powerState == null) ||
				! powerState.getShortDefinitionType().equals(DatapointType.powerState.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong powerState datapoint: " + powerState);
		}
		this.powerState = powerState;
		this.powerState.setDoc("The current status of the BinarySwitch. \"True\" indicates turned-on, and \"False\" indicates turned-off.");
		addDataPoint(this.powerState);
	}
	
	public BinarySwitch(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (BooleanDataPoint) dps.get(DatapointType.powerState.getShortName()));
	}
	
	public void addAction(Action action) {
		if (! action.getShortDefinitionName().equals(ActionType.toggle.getShortName()))
			throw new IllegalArgumentException("Wrong toggle action: " + action);
		this.toggle = action;
		this.toggle.setDoc("Toggle the switch");
		super.addAction(toggle);
	}

	public boolean getPowerState() throws DataPointException, AccessException {
		return powerState.getValue();
	}

	public void setPowerState(boolean v) throws DataPointException, AccessException {
		powerState.setValue(v);
	}

	public void setToggle(Toggle toggle) {
		addAction(toggle);
	}
	
	public void toggle() throws ActionException, AccessException {
		if (toggle == null)
			throw new ActionException("Not implemented");
		if (toggle instanceof Toggle)
			((Toggle) toggle).toggle();
		else
			((Command)toggle).invoke(null);
	}

}
