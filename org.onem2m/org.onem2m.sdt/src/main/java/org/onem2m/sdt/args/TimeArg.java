package org.onem2m.sdt.args;

import org.onem2m.sdt.types.DataType;

public class TimeArg extends AbstractDateArg {
	
	public TimeArg(String name) {
		super(name, DataType.Time);
	}

}
