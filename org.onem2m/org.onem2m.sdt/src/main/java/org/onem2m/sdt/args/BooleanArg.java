package org.onem2m.sdt.args;

import org.onem2m.sdt.types.DataType;

public class BooleanArg extends ValuedArg<Boolean> {
	
	public BooleanArg(String name) {
		super(name, DataType.Boolean);
	}

}
