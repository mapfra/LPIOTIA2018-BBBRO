package org.onem2m.sdt.datapoints;

import org.onem2m.sdt.types.DataType;

public abstract class FloatDataPoint extends ValuedDataPoint<Float> {

	public FloatDataPoint(String name) {
		super(name, DataType.Float);
	}

}
