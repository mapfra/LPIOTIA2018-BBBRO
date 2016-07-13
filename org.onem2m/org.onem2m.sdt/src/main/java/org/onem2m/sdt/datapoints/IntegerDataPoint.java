package org.onem2m.sdt.datapoints;

import org.onem2m.sdt.types.DataType;

public abstract class IntegerDataPoint extends ValuedDataPoint<Integer> {

	public IntegerDataPoint(String name) {
		super(name, DataType.Integer);
	}
	
}
