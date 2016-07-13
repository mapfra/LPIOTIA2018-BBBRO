package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.Colour;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.IntegerDataPoint;

public class MockedColour extends Colour {

	public MockedColour(String name, Domain domain, IntegerDataPoint red, IntegerDataPoint green,
			IntegerDataPoint blue) {
		super(name, domain, red, green, blue);
	}

}
