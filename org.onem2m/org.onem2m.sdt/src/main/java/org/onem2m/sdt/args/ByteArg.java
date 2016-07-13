package org.onem2m.sdt.args;

import org.onem2m.sdt.types.DataType;

public class ByteArg extends ValuedArg<Byte> {

	public ByteArg(String name) {
		super(name, DataType.Byte);
	}

}
