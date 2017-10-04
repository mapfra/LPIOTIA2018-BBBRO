/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.types;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.om2m.sdt.types.DataType.TypeChoice;

public class SimpleType implements TypeChoice {
    
    static private Map<String, SimpleType> values = new HashMap<String, SimpleType>();
	
	static public final SimpleType Boolean = new SimpleType(BasicType.BOOLEAN);
	static public final SimpleType Byte = new SimpleType(BasicType.BYTE);
	static public final SimpleType Integer = new SimpleType(BasicType.INTEGER);
	static public final SimpleType Float = new SimpleType(BasicType.FLOAT);
	static public final SimpleType String = new SimpleType(BasicType.STRING);
	static public final SimpleType Enum = new SimpleType(BasicType.ENUM);
	static public final SimpleType Date = new SimpleType(BasicType.DATE);
	static public final SimpleType Time = new SimpleType(BasicType.TIME);
	static public final SimpleType Datetime = new SimpleType(BasicType.DATETIME);
	static public final SimpleType Blob = new SimpleType(BasicType.BLOB);
	static public final SimpleType Uri = new SimpleType(BasicType.URI);

	private BasicType type;

	protected SimpleType(final BasicType type) {
		if (type == null)
			throw new IllegalArgumentException();
		this.type = type;
        values.put(type.getValue(), this);
	}

	public BasicType getType() {
		return type;
	}
	
	public String getOneM2MType() {
		return "xs:" + getType().getValue();
	}
    
    static public SimpleType getSimpleType(final String s) {
    	return values.get(s);
    }

	@Override
	public String toString() {
		return "<SimpleType type=\"" + type + "\"/>";
	}

}
