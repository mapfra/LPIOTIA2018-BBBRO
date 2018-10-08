/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.types;

import java.net.URI;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BasicType {
	
	static private final String PREFIX = "xs:";
	static private final String S_INTEGER = PREFIX + "integer";
	static private final String S_BOOLEAN = PREFIX + "boolean";
	static private final String S_STRING = PREFIX + "string";
	static private final String S_BYTE = PREFIX + "byte";
	static private final String S_FLOAT = PREFIX + "float";
	static private final String S_ENUM = PREFIX + "enum";
	static private final String S_DATE = PREFIX + "date";
	static private final String S_TIME = PREFIX + "time";
	static private final String S_DATETIME = PREFIX + "datetime";
	static private final String S_BLOB = PREFIX + "blob";
	static private final String S_URI = PREFIX + "uri";
    
	static final private DateFormat dateTimeFormat = DateFormat.getDateTimeInstance();
	static final private DateFormat dateFormat = DateFormat.getDateInstance();
	static final private DateFormat timeFormat = DateFormat.getTimeInstance();

    static private Map<String, BasicType> values = new HashMap<String, BasicType>();

	static public final BasicType INTEGER = new BasicType(S_INTEGER, Integer.class);
	static public final BasicType BOOLEAN = new BasicType(S_BOOLEAN, Boolean.class);
	static public final BasicType STRING = new BasicType(S_STRING, String.class);
	static public final BasicType BYTE = new BasicType(S_BYTE, Byte.class);
	static public final BasicType FLOAT = new BasicType(S_FLOAT, Float.class);
	static public final BasicType ENUM = new BasicType(S_ENUM, Enum.class);
	static public final BasicType DATE = new BasicType(S_DATE, Date.class);
	static public final BasicType TIME = new BasicType(S_TIME, Date.class);
	static public final BasicType DATETIME = new BasicType(S_DATETIME, Date.class);
	static public final BasicType BLOB = new BasicType(S_BLOB, byte[].class);
	static public final BasicType URI = new BasicType(S_URI, URI.class);
	
    private final String value;
    private final Class<?> clazz;

    protected BasicType(String v, Class<?> c) {
        value = v;
        clazz = c;
        values.put(v, this);
    }

    public String getValue() {
        return value;
    }
    
    public Class<?> getClazz() {
    	return clazz;
    }
    
    static public BasicType getBasicType(final String s) {
    	return values.get(s);
    }

	public String toString(Object val) {
		if (val == null) {
			return null;
		}
		switch (value) {
		case S_STRING:
		case S_ENUM:
		case S_INTEGER:
		case S_FLOAT: 
		case S_BOOLEAN: 
		case S_BYTE: 
		case S_URI:
			return val.toString();
		case S_DATETIME: return dateTimeFormat.format((Date)val);
		case S_TIME: return timeFormat.format((Date)val);
		case S_DATE: return dateFormat.format((Date)val);
		case S_BLOB: return val.toString(); // TODO serialize byte array
		}
		return null;
	}
	
	public Object fromString(String val) throws Exception {
		switch (value) {
		case S_STRING: return val;
		case S_ENUM:
		case S_INTEGER:  
			return Integer.parseInt(val);
		case S_FLOAT: return Float.parseFloat(val);
		case S_BOOLEAN: return Boolean.parseBoolean(val);
		case S_DATETIME: return dateTimeFormat.parse(val);
		case S_TIME: return timeFormat.parse(val);
		case S_DATE: return dateFormat.parse(val);
		case S_BYTE: return Byte.parseByte(val);
		case S_BLOB: return val; // TODO serialize byte array
		case S_URI: return new URI(value);
		}
		return null;
	}

	@Override
	public String toString() {
		return clazz.getSimpleName().toLowerCase();
	}

}
