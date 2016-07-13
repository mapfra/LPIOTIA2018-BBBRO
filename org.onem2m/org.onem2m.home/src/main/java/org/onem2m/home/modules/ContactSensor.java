package org.onem2m.home.modules;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.BooleanDataPoint;

public class ContactSensor extends AbstractAlarmSensor {

	public ContactSensor(String name, Domain domain, BooleanDataPoint alarm) {
		super(name, domain, alarm, ModuleType.contactSensor);
	}

}
