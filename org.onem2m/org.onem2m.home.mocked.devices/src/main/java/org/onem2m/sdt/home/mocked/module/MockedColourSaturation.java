package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.ColourSaturation;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.IntegerDataPoint;

public class MockedColourSaturation extends ColourSaturation {

	public MockedColourSaturation(String name, Domain domain, IntegerDataPoint colourSaturation) {
		super(name, domain, colourSaturation);
	}

}
