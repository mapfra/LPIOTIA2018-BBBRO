package org.onem2m.sdt.datapoints;

import org.onem2m.sdt.types.DataType;

public abstract class ByteDataPoint extends ValuedDataPoint<Byte> {

	public ByteDataPoint(String name) {
		super(name, DataType.Byte);
	}

}
