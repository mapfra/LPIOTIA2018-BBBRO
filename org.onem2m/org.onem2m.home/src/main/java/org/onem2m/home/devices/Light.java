/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.devices;

import org.onem2m.home.modules.BinarySwitch;
import org.onem2m.home.modules.Colour;
import org.onem2m.home.modules.ColourSaturation;
import org.onem2m.home.modules.FaultDetection;
import org.onem2m.home.modules.RunMode;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;

public class Light extends GenericDevice {
	
	private FaultDetection faultDetection;
	
	private BinarySwitch binarySwitch;
	
	private RunMode runMode;
	
	private Colour colour;
	
	private ColourSaturation colourSaturation;
	
	public Light(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceLight, domain);
	}
	
	public void addModule(Module module) {
		if (module instanceof FaultDetection)
			addModule((FaultDetection)module);
		else if (module instanceof BinarySwitch)
			addModule((BinarySwitch)module);
		else if (module instanceof RunMode)
			addModule((RunMode)module);
		else if (module instanceof Colour)
			addModule((Colour)module);
		else if (module instanceof ColourSaturation)
			addModule((ColourSaturation)module);
		else
			super.addModule(module);
	}
	
	public void addModule(Colour mod) {
		this.colour = mod;
		super.addModule(colour);
	}
	
	public void addModule(ColourSaturation mod) {
		this.colourSaturation = mod;
		super.addModule(colourSaturation);
	}

	public void addModule(FaultDetection mod) {
		this.faultDetection = mod;
		super.addModule(faultDetection);
	}

	public void addModule(BinarySwitch mod) {
		this.binarySwitch = mod;
		super.addModule(binarySwitch);
	}

	public void addModule(RunMode mod) {
		this.runMode = mod;
		super.addModule(runMode);
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

	public Colour getColour() {
		return colour;
	}
	
	public ColourSaturation getColourSaturation() {
		return colourSaturation;
	}
}
