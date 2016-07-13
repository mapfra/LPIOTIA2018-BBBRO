package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.FaultDetection;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.BooleanDataPoint;

public class MockedFaultDetection extends FaultDetection {

	public MockedFaultDetection(String name, Domain domain, BooleanDataPoint status) {
		super(name, domain, status);
	}

}
