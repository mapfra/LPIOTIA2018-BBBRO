/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.types;

import java.net.URI;
import java.util.Date;

public enum BasicType {

    INTEGER("integer", Integer.class),
    BOOLEAN("boolean", Boolean.class),
    STRING("string", String.class),
    BYTE("byte", Byte.class),
    FLOAT("float", Float.class),
    ENUM("enum", Enum.class),
    DATE("date", Date.class),
    TIME("time", String.class),
    DATETIME("datetime", String.class),
    BLOB("blob", byte[].class),
    URI("uri", URI.class),
	TONE("tone", Integer.class),
	LIQUIDLEVEL("liquidLevel", Integer.class);
	

    private final String value;
    private final Class<?> clazz;

    private BasicType(String v, Class<?> c) {
        value = v;
        clazz = c;
    }

    public String getValue() {
        return value;
    }
    
    public Class<?> getClazz() {
    	return clazz;
    }

    public static BasicType fromValue(String v) {
        for (BasicType c : values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
	
	@Override
	public String toString() {
		return clazz.getSimpleName().toLowerCase();
	}

}
