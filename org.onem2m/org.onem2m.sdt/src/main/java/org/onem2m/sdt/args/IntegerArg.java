package org.onem2m.sdt.args;

import org.onem2m.sdt.types.DataType;

public class IntegerArg extends ValuedArg<Integer> {

	public IntegerArg(String name) {
		super(name, DataType.Integer);
	}

}
