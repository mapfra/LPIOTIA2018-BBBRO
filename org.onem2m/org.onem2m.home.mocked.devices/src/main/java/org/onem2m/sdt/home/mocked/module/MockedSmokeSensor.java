package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.SmokeSensor;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.BooleanDataPoint;

public class MockedSmokeSensor extends SmokeSensor {

	public MockedSmokeSensor(String name, Domain domain, BooleanDataPoint alarm) {
		super(name, domain, alarm);
	}

}
