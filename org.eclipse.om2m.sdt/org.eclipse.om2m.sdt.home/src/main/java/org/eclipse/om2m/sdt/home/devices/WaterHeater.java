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
import org.eclipse.om2m.sdt.home.modules.BinarySwitch;
import org.eclipse.om2m.sdt.home.modules.Boiler;
import org.eclipse.om2m.sdt.home.modules.Clock;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.modules.HotWaterSupply;
import org.eclipse.om2m.sdt.home.modules.RunMode;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class WaterHeater extends GenericDevice {

	private FaultDetection faultDetection;
	
	private BinarySwitch binarySwitch;
	
	private RunMode runMode;
	
	private Clock clock;
	
	private Boiler boiler;
	
	private HotWaterSupply hotWaterSupply;

	
	public WaterHeater(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceWaterHeater, domain);
	}
	
	public void addModule(Module module) {
		if (module instanceof BinarySwitch)
			addModule((BinarySwitch)module);
		else if (module instanceof FaultDetection)
			addModule((FaultDetection)module);
		else if (module instanceof RunMode)
			addModule((RunMode)module);
		else if (module instanceof Clock)
			addModule((Clock)module);
		else if (module instanceof Boiler)
			addModule((Boiler)module);
		else if (module instanceof HotWaterSupply)
			addModule((HotWaterSupply)module);
		else 
			super.addModule(module);
	}

	public void addModule(FaultDetection faultDetection) {
		this.faultDetection = faultDetection;
		super.addModule(faultDetection);
	}

	public void addModule(BinarySwitch binarySwitch) {
		this.binarySwitch = binarySwitch;
		super.addModule(binarySwitch);
	}

	public void addModule(RunMode runMode) {
		this.runMode = runMode;
		super.addModule(runMode);
	}

	public void addModule(Clock clock) {
		this.clock = clock;
		super.addModule(clock);
	}

	public void addModule(Boiler boiler) {
		this.boiler = boiler;
		super.addModule(boiler);
	}

	public void addModule(HotWaterSupply hotWaterSupply) {
		this.hotWaterSupply = hotWaterSupply;
		super.addModule(hotWaterSupply);
	}

	public FaultDetection getFaultDetection() {
		return faultDetection;
	}

	public BinarySwitch getBinarySwitch() {
		return binarySwitch;
	}

	public RunMode getRunMode() {
		return runMode;
	}

	public Clock getClock() {
		return clock;
	}

	public Boiler getBoiler() {
		return boiler;
	}

	public HotWaterSupply getHotWaterSupply() {
		return hotWaterSupply;
	}

}
