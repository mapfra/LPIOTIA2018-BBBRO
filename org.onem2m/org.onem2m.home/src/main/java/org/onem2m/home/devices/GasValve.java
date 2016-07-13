package org.onem2m.home.devices;

import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;

public class GasValve extends GenericActuator {

	public GasValve(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceGasValve, domain);
		setDeviceSubModelName("GAS");
	}

}
