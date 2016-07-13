package org.onem2m.sdt.datapoints;

import org.onem2m.sdt.types.DataType;

public abstract class StringDataPoint extends ValuedDataPoint<String> {

	public StringDataPoint(String name) {
		super(name, DataType.String);
	}
	
}
