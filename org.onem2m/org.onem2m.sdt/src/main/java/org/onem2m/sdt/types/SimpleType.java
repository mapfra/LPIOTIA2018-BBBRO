package org.onem2m.sdt.types;

import org.onem2m.sdt.types.DataType.TypeChoice;

public class SimpleType implements TypeChoice {
	
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
	static public final SimpleType Tone = new SimpleType(BasicType.TONE);
	static public final SimpleType LiquidLevel = new SimpleType(BasicType.LIQUIDLEVEL);

	
	private BasicType type;

	private SimpleType(final BasicType type) {
		if (type == null)
			throw new IllegalArgumentException();
		this.type = type;
	}

	public BasicType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "<SimpleType type=\"" + type + "\"/>";
	}

}
