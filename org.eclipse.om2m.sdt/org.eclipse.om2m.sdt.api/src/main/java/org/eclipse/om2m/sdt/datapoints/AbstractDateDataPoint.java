/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.datapoints;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.eclipse.om2m.sdt.Identifiers;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.types.DataType;

public abstract class AbstractDateDataPoint extends ValuedDataPoint<Date> {
	
	static final private DateFormat dateTimeFormat = DateFormat.getDateTimeInstance();
	static final private DateFormat dateFormat = DateFormat.getDateInstance();
	static final private DateFormat timeFormat = DateFormat.getTimeInstance();
	
	private DateFormat df;

	protected AbstractDateDataPoint(Identifiers name, DataType type) {
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
