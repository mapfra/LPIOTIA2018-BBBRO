package org.onem2m.home.devices;

import org.onem2m.home.modules.Temperature;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;

public class TemperatureDetector extends GenericSensor {
	
	private Temperature temperature;
	
	public TemperatureDetector(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceTemperatureDetector, domain);
		setDeviceSubModelName("TEMPERATURE");
	}

	public void addModule(Temperature temperature) {
		this.temperature = temperature;
		super.addModule(temperature);
	}

	public Temperature getTemperature() {
		return temperature;
	}

}
