package org.onem2m.sdt.args;

import org.onem2m.sdt.types.DataType;

public class DateTimeArg extends AbstractDateArg {
	
	public DateTimeArg(String name) {
		super(name, DataType.Datetime);
	}

}
