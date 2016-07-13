package org.onem2m.sdt.datapoints;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;
import org.onem2m.sdt.types.DataType;

public abstract class AbstractDateDataPoint extends ValuedDataPoint<Date> {
	
	static final private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	static final private DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	static final private DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	
	private DateFormat df;

	protected AbstractDateDataPoint(String name, DataType type) {
		super(name, type);
		if (type.equals(DataType.Date)) {
			df = dateFormat;
		} else if (type.equals(DataType.Datetime)) {
			df = dateTimeFormat;
		} else if (type.equals(DataType.Time)) {
			df = timeFormat;
		}
	}
	
	public void setValue(String v) throws DataPointException, AccessException {
		try {
			setValue(df.parse(v));
		} catch (ParseException e) {
			throw new DataPointException("Incorrect value", e);
		}
	}

	public void setValue(long value) throws DataPointException, AccessException {
		setValue(new Date(value));
	}
	
	public String getStringValue() throws DataPointException, AccessException {
		return df.format(getValue());
	}

}
