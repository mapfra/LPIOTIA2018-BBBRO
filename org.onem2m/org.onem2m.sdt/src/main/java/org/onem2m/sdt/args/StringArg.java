package org.onem2m.sdt.args;

import org.onem2m.sdt.types.DataType;

public class StringArg extends ValuedArg<String> {

	public StringArg(String name) {
		super(name, DataType.String);
	}

}
