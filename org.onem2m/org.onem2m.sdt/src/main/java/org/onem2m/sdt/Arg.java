package org.onem2m.sdt;

import org.onem2m.sdt.types.DataType;

public class Arg extends Element {
	
	private DataType type;

	public Arg(final String name, final DataType type) {
		super(name);
		if (type == null)
			throw new IllegalArgumentException();
		this.type = type;
	}

	public DataType getDataType() {
		return type;
	}

	@Override
	protected String prettyPrint(String t1) {
		String t2 = t1 + "\t";
		StringBuffer ret = new StringBuffer(t1).append("<Arg name=\"")
				.append(getName()).append("\">");
		if (getDoc() != null) ret.append("\n").append(t2).append(getDoc());
		return ret.append("\n").append(type.prettyPrint(t2))
				.append("\n").append(t1).append("</Arg>").toString();
	}

}
