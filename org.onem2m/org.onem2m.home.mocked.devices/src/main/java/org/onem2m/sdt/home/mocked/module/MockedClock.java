package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.Clock;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.DateDataPoint;
import org.onem2m.sdt.datapoints.TimeDataPoint;

public class MockedClock extends Clock {

	public MockedClock(String name, Domain domain, TimeDataPoint currentTime, DateDataPoint currentDate) {
		super(name, domain, currentTime, currentDate);
	}

}
