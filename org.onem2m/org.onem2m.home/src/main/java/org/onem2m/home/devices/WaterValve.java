package org.onem2m.home.devices;

import org.onem2m.home.modules.WaterLevel;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;

public class WaterValve extends GenericActuator {
	
	private WaterLevel waterLevel;

	public WaterValve(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceWaterValve, domain);
		setDeviceSubModelName("WATER");
	}
	
	public void addModule(WaterLevel waterLevel) {
		this.waterLevel = waterLevel;
		super.addModule(waterLevel);
	}

	public WaterLevel getWaterLevel() {
		return waterLevel;
	}

}
