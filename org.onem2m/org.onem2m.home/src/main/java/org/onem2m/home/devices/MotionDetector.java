package org.onem2m.home.devices;

import org.onem2m.home.modules.MotionSensor;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;

public class MotionDetector extends GenericSensor {

	public MotionDetector(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceSmokeDetector, domain);
		setDeviceSubModelName("MOTION");
	}

	public MotionSensor getMotionSensor() {
		return (MotionSensor) sensor;
	}

}
