/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.types;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.sdt.types.DataType.TypeChoice;

public class Array<T> implements TypeChoice {
	
	private DataType dataType;
	
	public Array(final DataType dataType) {
		this.dataType = dataType;
	}
	
	public String getOneM2MType() {
		String ret = dataType.getTypeChoice().getOneM2MType();
		return ret.startsWith("[") ? ret : "[" + ret + "]";
	}
	
	public DataType getDataType() {
		return dataType;
	}

	@Override
	public String toString(Object val) throws Exception {
		if (val instanceof List) {
			String ret = "[";
			boolean first = true;
			for (Object item : (List<?>)val) {
				if (first) first = false;
				else ret += ",";
				ret += dataType.getTypeChoice().toString(item);
			}
			return ret + "]";
		}
		return val.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object fromString(String val) throws Exception {
		List<T> ret = new ArrayList<T>();
		String s = val.trim();
		if (s.startsWith("["))
			s = s.substring(1, s.length()-1);
		for (String item : s.split(",")) {
			ret.add((T)dataType.getTypeChoice().fromString(item));
		}
		return ret;
	}

}
