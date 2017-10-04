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
import org.eclipse.om2m.sdt.Property;
import org.eclipse.om2m.sdt.home.modules.BinarySwitch;
import org.eclipse.om2m.sdt.home.modules.Clock;
import org.eclipse.om2m.sdt.home.modules.EnergyConsumption;
import org.eclipse.om2m.sdt.home.modules.EnergyGeneration;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.modules.RunMode;
import org.eclipse.om2m.sdt.home.types.DeviceType;
import org.eclipse.om2m.sdt.home.types.PropertyType;
import org.eclipse.om2m.sdt.types.SimpleType;

public class SmartElectricMeter extends GenericDevice {
	
	private BinarySwitch binarySwitch;
	private EnergyConsumption energyConsumption;
	private FaultDetection faultDetection;
	private RunMode runMode;
	private Clock clock;
	private EnergyGeneration generationMeter;
	
	private Property measuringScope;
	
	public SmartElectricMeter(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceSmartElectricMeter, domain);
		
		measuringScope = new Property(PropertyType.measuringScope);
		measuringScope.setType(SimpleType.String);
		measuringScope.setOptional(true);
		measuringScope.setDoc("Measuring scope of the meter (ex. Whole house, room, or device)");
		addProperty(measuringScope);
	}
	
	public void addModule(Module module) {
		if (module instanceof FaultDetection)
			addModule((FaultDetection)module);
		else if (module instanceof EnergyConsumption)
			addModule((EnergyConsumption)module);
		else if (module instanceof BinarySwitch)
			addModule((BinarySwitch)module);
		else if (module instanceof RunMode)
			addModule((RunMode)module);
		else if (module instanceof Clock)
			addModule((Clock)module);
		else if (module instanceof EnergyGeneration)
			addModule((EnergyGeneration)module);
		else 
			super.addModule(module);
	}

	public void addModule(BinarySwitch binarySwitch) {
		this.binarySwitch = binarySwitch;
		super.addModule(binarySwitch);
	}

	public void addModule(EnergyConsumption energyConsumption) {
		this.energyConsumption = energyConsumption;
		super.addModule(energyConsumption);
	}

	public void addModule(FaultDetection faultDetection) {
		this.faultDetection = faultDetection;
		super.addModule(faultDetection);
	}

	public void addModule(RunMode runMode) {
		this.runMode = runMode;
		super.addModule(runMode);
	}

	public void addModule(Clock clock) {
		this.clock = clock;
		super.addModule(clock);
	}

	public void addModule(EnergyGeneration generationMeter) {
		this.generationMeter = generationMeter;
		super.addModule(generationMeter);
	}

	public EnergyConsumption getEnergyConsumption() {
		return energyConsumption;
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

	public EnergyGeneration getGenerationMeter() {
		return generationMeter;
	}

}
