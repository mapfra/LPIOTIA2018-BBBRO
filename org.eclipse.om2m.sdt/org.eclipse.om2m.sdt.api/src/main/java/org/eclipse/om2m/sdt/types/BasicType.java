/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.types;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BasicType {
    
    static private Map<String, BasicType> values = new HashMap<String, BasicType>();

	static public final BasicType INTEGER = new BasicType("integer", Integer.class);
	static public final BasicType BOOLEAN = new BasicType("boolean", Boolean.class);
	static public final BasicType STRING = new BasicType("string", String.class);
	static public final BasicType BYTE = new BasicType("byte", Byte.class);
	static public final BasicType FLOAT = new BasicType("float", Float.class);
	static public final BasicType ENUM = new BasicType("enum", Enum.class);
	static public final BasicType DATE = new BasicType("date", Date.class);
	static public final BasicType TIME = new BasicType("time", Date.class);
	static public final BasicType DATETIME = new BasicType("datetime", Date.class);
	static public final BasicType BLOB = new BasicType("blob", byte[].class);
	static public final BasicType URI = new BasicType("uri", URI.class);
	
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
	
	@Override
	public String toString() {
		return clazz.getSimpleName().toLowerCase();
	}

}
