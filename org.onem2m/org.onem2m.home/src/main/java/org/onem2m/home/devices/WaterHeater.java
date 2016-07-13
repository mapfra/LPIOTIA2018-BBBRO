package org.onem2m.home.devices;

import org.onem2m.home.modules.BinarySwitch;
import org.onem2m.home.modules.Boiler;
import org.onem2m.home.modules.Clock;
import org.onem2m.home.modules.FaultDetection;
import org.onem2m.home.modules.HotWaterSupply;
import org.onem2m.home.modules.RunMode;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;

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
