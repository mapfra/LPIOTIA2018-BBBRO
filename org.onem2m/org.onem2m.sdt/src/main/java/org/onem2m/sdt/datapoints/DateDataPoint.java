package org.onem2m.sdt.datapoints;

import org.onem2m.sdt.types.DataType;

public abstract class DateDataPoint extends AbstractDateDataPoint {

	public DateDataPoint(String name) {
		super(name, DataType.Date);
	}

}
