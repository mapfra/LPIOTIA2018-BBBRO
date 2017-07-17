/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.utils;

import java.net.URI;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.om2m.commons.resource.CustomAttribute;

public class SDTUtil {
	
	static final private DateFormat dateTimeFormat = DateFormat.getDateTimeInstance();
	static final private DateFormat dateFormat = DateFormat.getDateInstance();
	static final private DateFormat timeFormat = DateFormat.getTimeInstance();

	public static Object getValue(CustomAttribute attr, String type) throws Exception {
		return (attr == null) ? null
			: getValue(attr.getCustomAttributeValue(), type);
	}
	
	public static Object getValue(String value, String type) throws Exception {
		if (value == null)
			return null;
		switch (type) {
		case "xs:string": return value;
		case "xs:integer": return Integer.parseInt(value);
		case "xs:float": return Float.parseFloat(value);
		case "xs:boolean": return Boolean.parseBoolean(value);
		case "xs:datetime": return dateTimeFormat.parse(value);
		case "xs:time": return timeFormat.parse(value);
		case "xs:date": return dateFormat.parse(value);
		case "xs:byte": return Byte.parseByte(value);
		case "xs:enum":
			List<String> ret = new ArrayList<String>();
			value = value.trim();
			if (value.charAt(0) == '[')
				value = value.substring(1);
			int last = value.length() - 1;
			if (value.charAt(last) == ']')
				value = value.substring(0, last);
			for (String val : value.split(",")) {
					String valueToAdd = val.trim();
					if (valueToAdd.length() > 0) {
						ret.add(valueToAdd);
					}
			}
			return ret;
		case "xs:uri": return new URI(value);
		case "xs:blob": return value;
		default:
			return type.startsWith("hd:") ? Integer.parseInt(value) : null;
		}
	}
	
	public static void setValue(CustomAttribute attr, Object val, String type) throws Exception {
		if (val == null) {
			attr.setCustomAttributeValue(null);
			return;
		}
		switch (type) {
		case "xs:string":
		case "xs:integer": 
		case "xs:float":
		case "xs:boolean":
		case "xs:byte":
		case "xs:uri":
			attr.setCustomAttributeValue(val.toString()); return;
		case "xs:datetime": attr.setCustomAttributeValue(dateTimeFormat.format((Date)val)); return;
		case "xs:time": attr.setCustomAttributeValue(timeFormat.format((Date)val)); return;
		case "xs:date": attr.setCustomAttributeValue(dateFormat.format((Date)val)); return;
		case "xs:enum":
			String ret = "";
			boolean first = true;
			for (Object s : (List<?>)val) {
				if (first) ret += ",";
				else first = false;
				ret += s.toString();
			}
			attr.setCustomAttributeValue(ret); return;
		case "xs:blob": return;// TODO serialize byte array
		default:
			if (type.startsWith("hd:")) 
				attr.setCustomAttributeValue(val.toString());
			return;
		}
	}

}
