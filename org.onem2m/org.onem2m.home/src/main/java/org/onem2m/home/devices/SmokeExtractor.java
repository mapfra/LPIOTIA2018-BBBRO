package org.onem2m.home.devices;

import org.onem2m.home.modules.BinarySwitch;
import org.onem2m.home.modules.FaultDetection;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;

public class SmokeExtractor extends GenericDevice {
	
	private BinarySwitch binarySwitch;

	private FaultDetection faultDetection;

	
	public SmokeExtractor(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceSmokeExtractor, domain);
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
