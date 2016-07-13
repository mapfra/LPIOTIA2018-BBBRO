package org.onem2m.home.devices;

import org.onem2m.home.modules.WaterSensor;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;

public class FloodDetector extends GenericSensor {

	public FloodDetector(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceFloodDetector, domain);
		setDeviceSubModelName("WATER");
	}

	public WaterSensor getWaterSensor() {
		return (WaterSensor) sensor;
	}

}
