package org.onem2m.home.devices;

import org.onem2m.home.modules.BinarySwitch;
import org.onem2m.home.modules.Colour;
import org.onem2m.home.modules.ColourSaturation;
import org.onem2m.home.modules.FaultDetection;
import org.onem2m.home.modules.RunMode;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;

public class Light extends GenericDevice {
	
	private FaultDetection faultDetection;
	
	private BinarySwitch binarySwitch;
	
	private RunMode runMode;
	
	private Colour colour;
	
	private ColourSaturation colorSaturation;
	
	public Light(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceLight, domain);
	}
	
	public void addModule(Colour colour) {
		this.colour = colour;
		super.addModule(colour);
	}
	
	public void addModule(ColourSaturation colourSaturation) {
		this.colorSaturation = colourSaturation;
		super.addModule(colourSaturation);
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
		return colorSaturation;
	}
}
