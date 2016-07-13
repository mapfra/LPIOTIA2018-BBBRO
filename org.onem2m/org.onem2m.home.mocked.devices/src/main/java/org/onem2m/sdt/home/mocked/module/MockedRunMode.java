package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.RunMode;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.ArrayDataPoint;

public class MockedRunMode extends RunMode {

	public MockedRunMode(String name, Domain domain,
			ArrayDataPoint<String> operationMode, 
			ArrayDataPoint<String> supportedModes) {
		super(name, domain, operationMode, supportedModes);
	}

}
