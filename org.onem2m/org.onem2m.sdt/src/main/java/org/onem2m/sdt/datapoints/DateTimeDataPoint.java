package org.onem2m.sdt.datapoints;

import org.onem2m.sdt.types.DataType;

public abstract class DateTimeDataPoint extends AbstractDateDataPoint {

	public DateTimeDataPoint(String name) {
		super(name, DataType.Datetime);
	}

}
