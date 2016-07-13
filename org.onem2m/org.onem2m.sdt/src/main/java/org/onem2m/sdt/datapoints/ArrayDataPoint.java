package org.onem2m.sdt.datapoints;

import java.util.List;

import org.onem2m.sdt.types.Array;
import org.onem2m.sdt.types.DataType;

public abstract class ArrayDataPoint<T> extends ValuedDataPoint<List<T>> {

	public ArrayDataPoint(String name) {
		super(name, new DataType("array", new Array<T>()));
	}

}
