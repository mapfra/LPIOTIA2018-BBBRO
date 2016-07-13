package org.onem2m.home.devices;

import org.onem2m.home.modules.SmokeSensor;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;

public class SmokeDetector extends GenericSensor {
	
	public SmokeDetector(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceSmokeDetector, domain);
		setDeviceSubModelName("SMOKE");
	}

	public SmokeSensor getSmokeSensor() {
		return (SmokeSensor) sensor;
	}

}
