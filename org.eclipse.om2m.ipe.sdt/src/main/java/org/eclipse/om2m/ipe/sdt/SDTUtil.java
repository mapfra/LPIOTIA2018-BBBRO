package org.eclipse.om2m.ipe.sdt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.onem2m.sdt.types.SimpleType;
import org.onem2m.sdt.types.Array;
import org.onem2m.sdt.types.BasicType;
import org.onem2m.sdt.types.DataType.TypeChoice;

public class SDTUtil {

	private static final String TYPE_PREFIX = "xs:";
	
	public static String simpleTypeToOneM2MType(SimpleType simpleType) {
		if (SimpleType.Tone.equals(simpleType)) {
			return "hd:tone";
		}
		if (SimpleType.LiquidLevel.equals(simpleType)) {
			return "hd:liquidLevel";
		}
		return TYPE_PREFIX + simpleType.getType().getValue();
	}
	
	public static String simpleTypeToOneM2MType(TypeChoice typeChoice) {
		if (typeChoice instanceof Array) {
			return TYPE_PREFIX + "enum";
		}
		if (typeChoice instanceof SimpleType) {
			return simpleTypeToOneM2MType((SimpleType) typeChoice);
		}
		return TYPE_PREFIX + "notDefined";
	}
	
	public static Object stringToObject(String value, String type) {
		Logger.getInstance().logDebug(SDTUtil.class, "stringToObject(value=" + value + ", type=" + type +")");
		Object o = null;
		
		switch (type) {
		case "xs:string":
			o = value;
			break;
		case "xs:integer":
			o = Integer.parseInt(value);
			break;
		case "xs:float":
			o = Float.parseFloat(value);
			break;
		case "xs:boolean":
			o = Boolean.parseBoolean(value);
			break;
		case "xs:date":
			o = Date.parse(value);
			break;
		case "xs:dateTime":
			o = value;
			break;
		case "xs:byte":
			o = Byte.parseByte(value);
			break;
		case "xs:time":
			o = value;
			break;
		case "hd:tone":
			o = Integer.parseInt(value);
			break;
		case "hd:liquidLevel":
			o = Integer.parseInt(value);
			break;
		case "xs:enum":
			// split value using coma character
			String[] array = value.split(",");
			for(int i = 0; i < array.length; i++) {
				array[i] = array[i].trim();
			}
			List<String> list = Arrays.asList(array);
			o = list;
			break;
		default:
			break;
		}
		
		Logger.getInstance().logDebug(SDTUtil.class, "stringToObject(value=" + value + ", type=" + type +") -o=" + o + ",o.getClass=" + o.getClass());
		
		return o;
		
	}
}
