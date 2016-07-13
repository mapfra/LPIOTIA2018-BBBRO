package org.onem2m.sdt.args;

import org.onem2m.sdt.Arg;
import org.onem2m.sdt.types.DataType;

public class ValuedArg<T> extends Arg {
	
	private T value;

	public ValuedArg(String name, DataType type) {
		super(name, type);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
