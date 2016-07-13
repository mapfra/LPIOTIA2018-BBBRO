package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.WaterSensor;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.BooleanDataPoint;

public class MockedWaterSensor extends WaterSensor {

	public MockedWaterSensor(String name, Domain domain, BooleanDataPoint alarm) {
		super(name, domain, alarm);
	}

}
