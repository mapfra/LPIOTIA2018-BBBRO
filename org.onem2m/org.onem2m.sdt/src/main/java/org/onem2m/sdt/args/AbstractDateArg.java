package org.onem2m.sdt.args;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.onem2m.sdt.types.DataType;

public abstract class AbstractDateArg extends ValuedArg<Date> {
	
	static final private DateFormat dateFormat = DateFormat.getDateInstance();
	static final private DateFormat dateTimeFormat = DateFormat.getDateTimeInstance();
	static final private DateFormat timeFormat = DateFormat.getTimeInstance();
	
	private DateFormat df;

	protected AbstractDateArg(String name, DataType type) {
		super(name, type);
		if (type.equals(DataType.Date)) {
			df = dateFormat;
		} else if (type.equals(DataType.Datetime)) {
			df = dateTimeFormat;
		} else if (type.equals(DataType.Time)) {
			df = timeFormat;
		}
	}
	
	public void setValue(String v) {
		try {
			setValue(df.parse(v));
		} catch (ParseException e) {
			throw new IllegalArgumentException("", e);
		}
	}

	public void setValue(long value) {
		setValue(new Date(value));
	}
	
	public String getStringValue() {
		return df.format(getValue());
	}

}
