package org.onem2m.sdt.datapoints;

import org.onem2m.sdt.DataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;
import org.onem2m.sdt.types.DataType;
import org.onem2m.sdt.utils.Activator;

public abstract class ValuedDataPoint<T> extends DataPoint {

	public ValuedDataPoint(String name, DataType type) {
		super(name, type);
	}

	public T getValue() throws DataPointException, AccessException {
		if (! isReadable()) {
			throw new DataPointException("Not readable");
		}
		if (! Activator.isGrantedReadAccess(this)) {
			throw new AccessException("No read access allowed");
		}
		return doGetValue();
	}

	public void setValue(T value) throws DataPointException, AccessException {
		if (! isWritable()) {
			throw new DataPointException("Not writable");
		}
		if (! Activator.isGrantedWriteAccess(this)) {
			throw new AccessException("No write access allowed");
		}
		doSetValue(value);
	}

	protected abstract T doGetValue() throws DataPointException;

	protected void doSetValue(T value) throws DataPointException {
	}
	
}
