package org.onem2m.sdt.datapoints;

import org.onem2m.sdt.types.DataType;

public abstract class TimeDataPoint extends AbstractDateDataPoint {

	public TimeDataPoint(String name) {
		super(name, DataType.Time);
	}

}
