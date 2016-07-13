package org.onem2m.sdt;

import org.onem2m.sdt.types.BasicType;

public class Constraint extends Element {
	
	private String value;
	
	private BasicType type;

	public Constraint(final String name) {
		super(name);
	}

	public BasicType getType() {
		return type;
	}

	public void setType(final BasicType type) {
		this.type= type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

}
