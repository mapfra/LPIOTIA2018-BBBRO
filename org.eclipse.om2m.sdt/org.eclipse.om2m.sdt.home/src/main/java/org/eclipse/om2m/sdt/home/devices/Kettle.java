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
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.modules.KeepWarm;
import org.eclipse.om2m.sdt.home.modules.LiquidLevel;
import org.eclipse.om2m.sdt.home.modules.RunState;
import org.eclipse.om2m.sdt.home.modules.Temperature;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class Kettle extends GenericDevice {

	private FaultDetection faultDetection;
	private RunState runState;
	private LiquidLevel waterLevel;
	private BinarySwitch binarySwitch;
	private Temperature temperature;
	private Boiler boiler;
	private KeepWarm keepWarm;

	public Kettle(final String id, final String serial, final Domain domain){
		super(id, serial, DeviceType.deviceKettle, domain);
	}

	public void addModule(Module module){
		if (module instanceof FaultDetection)
			addModule((FaultDetection)module);
		else if (module instanceof RunState)
			addModule((RunState)module);
		else if (module instanceof LiquidLevel)
			addModule((LiquidLevel)module);
		else if (module instanceof BinarySwitch)
			addModule((BinarySwitch)module);
		else if (module instanceof KeepWarm)
			addModule((KeepWarm)module);
		else if (module instanceof Temperature)
			addModule((Temperature)module);
		else if (module instanceof Boiler)
			addModule((Boiler)module);
		else 
			super.addModule(module);
	}

	//******************ADD MODULES******************

	public void addModule(FaultDetection mod) {
		this.faultDetection = mod;
		super.addModule(faultDetection);
	}

	public void addModule(RunState mod) {
		this.runState = mod;
		super.addModule(runState);
	}

	public void addModule(Boiler mod) {
		this.boiler = mod;
		super.addModule(boiler);
	}
	 
	public void addModule(LiquidLevel mod){
		this.waterLevel = mod;
		super.addModule(waterLevel);
	}

	public void addModule(BinarySwitch mod) {
		this.binarySwitch = mod;
		super.addModule(binarySwitch);
	}

	public void addModule(Temperature mod) {
		this.temperature = mod;
		super.addModule(temperature);
	}

	public void addModule(KeepWarm mod) {
		this.keepWarm = mod;
		super.addModule(keepWarm);
	}

	//******************GETTERS******************

	public FaultDetection getFaultDetection() {
		return faultDetection;
	}

	public Temperature getTemperature() {
		return temperature;
	}


	public KeepWarm getKeepWarm() {
		return keepWarm;
	}

	public RunState getRunState() {
		return runState;
	}

	public LiquidLevel getWaterLevel() {
		return waterLevel;
	}

	public BinarySwitch getBinarySwitch() {
		return binarySwitch;
	}

	  public Boiler getBoiler() {
		return boiler;
	}

}
