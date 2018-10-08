/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.devices;

import java.util.Collection;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.home.modules.BinarySwitch;
import org.eclipse.om2m.sdt.home.modules.Brewing;
import org.eclipse.om2m.sdt.home.modules.Clock;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.modules.Foaming;
import org.eclipse.om2m.sdt.home.modules.Grinder;
import org.eclipse.om2m.sdt.home.modules.KeepWarm;
import org.eclipse.om2m.sdt.home.modules.LiquidLevel;
import org.eclipse.om2m.sdt.home.modules.RunState;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class CoffeeMachine extends GenericDevice {
	
	private FaultDetection faultDetection;
	private RunState runState;
	private Clock clock;
	private Brewing brewing;
	private LiquidLevel waterStatus;
	private LiquidLevel milkStatus;
	private LiquidLevel beansStatus;
	private Grinder grinder;
	private Foaming foamedMilk;
	private LiquidLevel milkQuantity;
	private KeepWarm keepWarm;
	private BinarySwitch brewingSwitch;
	
	public CoffeeMachine(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceCoffeeMachine, domain);
	}
	
	public void addModule(Module module) {
		if (module instanceof FaultDetection)
			addModule((FaultDetection)module);
		else if (module instanceof RunState)
			addModule((RunState)module);
		else if (module instanceof Clock)
			addModule((Clock)module);
		else if (module instanceof Brewing)
			addModule((Brewing)module);
		else if (module instanceof LiquidLevel){
			Collection<String> col = module.getDataPointNames();
			if(col.contains("waterStatus")){
				addModuleWaterStatus((LiquidLevel)module);
			}
			if(col.contains("milkStatus")){
				addModuleMilkStatus((LiquidLevel)module);
			}
			if(col.contains("beansStatus")){
				addModuleBeansStatus((LiquidLevel)module);
			}
			if(col.contains("milkQuantity")){
				addModuleMilkQuantity((LiquidLevel)module);
			}
		}
		else if (module instanceof Grinder)
			addModule((Grinder)module);
		else if (module instanceof Foaming)
			addModule((Foaming)module);
		else if(module instanceof KeepWarm)
			addModule((KeepWarm)module);
		else if(module instanceof BinarySwitch)
			addModule((BinarySwitch)module);
		else
			super.addModule(module);
	}
	
	public void addModule(FaultDetection mod) {
		this.faultDetection = mod;
		super.addModule(faultDetection);
	}
	
	public void addModule(BinarySwitch mod){
		this.brewingSwitch = mod;
		super.addModule(brewingSwitch);
	}
	
	public void addModule(KeepWarm mod){
		this.keepWarm = mod;
		super.addModule(keepWarm);
	}

	
	public void addModule(RunState mod) {
		this.runState = mod;
		super.addModule(runState);
	}
	
	public void addModule(Clock mod) {
		this.clock = mod;
		super.addModule(clock);
	}
	
	public void addModule(Brewing mod) {
		this.brewing = mod;
		super.addModule(brewing);
	}
	
	public void addModule(Grinder mod) {
		this.grinder = mod;
		super.addModule(grinder);
	}
	
	public void addModule(Foaming mod) {
		this.foamedMilk = mod;
		super.addModule(foamedMilk);
	}
	
	
	public void addModuleWaterStatus(LiquidLevel mod) {
		this.waterStatus = mod;
		super.addModule(waterStatus);
	}
	
	public void addModuleMilkStatus(LiquidLevel mod) {
		this.milkStatus = mod;
		super.addModule(milkStatus);
	}
	
	public void addModuleBeansStatus(LiquidLevel mod) {
		this.beansStatus = mod;
		super.addModule(beansStatus);
	}
	
	
	public void addModuleMilkQuantity(LiquidLevel mod) {
		this.milkQuantity = mod;
		super.addModule(milkQuantity);
	}
	
	public FaultDetection getFaultDetection() {
		return faultDetection;
	}

	public RunState getRunState() {
		return runState;
	}

	public Clock getClock() {
		return clock;
	}

	public Brewing getBrewing() {
		return brewing;
	}

	public LiquidLevel getWaterStatus() {
		return waterStatus;
	}

	public LiquidLevel getMilkStatus() {
		return milkStatus;
	}

	public LiquidLevel getBeansStatus() {
		return beansStatus;
	}

	public Grinder getGrinder() {
		return grinder;
	}

	public Foaming getFoamedMilk() {
		return foamedMilk;
	}

	public LiquidLevel getMilkQuantity() {
		return milkQuantity;
	}

	public KeepWarm getKeepWarm() {
		return keepWarm;
	}

	public void setKeepWarm(KeepWarm keepWarm) {
		this.keepWarm = keepWarm;
	}

	public BinarySwitch getBrewingSwitch() {
		return brewingSwitch;
	}

	public void setBrewingSwitch(BinarySwitch brewingSwitch) {
		this.brewingSwitch = brewingSwitch;
	}

	
	
	
}
