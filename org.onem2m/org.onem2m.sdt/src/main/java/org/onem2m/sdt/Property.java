package org.onem2m.sdt;

import org.onem2m.sdt.types.SimpleType;

public class Property extends Element {
	
	private boolean optional;
	
	private String value;
	
	private SimpleType type;

	public Property(final String name) {
		super(name);
		optional = false;
	}

	public Property(final String name, final String value) {
		this(name);
		setValue(value);
	}

	public String getName() {
		return name;
	}

	public SimpleType getType() {
		return type;
	}

	public void setType(final SimpleType type) {
		this.type= type ;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(final boolean optional) {
		this.optional = optional;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}
	
	@Override
	protected String prettyPrint(String t1) {
		return t1 + "<Property name=\"" + name 
			+ "\" value=" + ((value == null) ? value : "\"" + value + "\"")
			+ "\n" + t1 + "\t" + type + "\n" + t1 + "</Property>";
	}
	
	@Override
	public String toString() {
		return "<" + getClass().getSimpleName() + " \"" + name + "\"="
			+ ((value == null) ? value : "\"" + value + "\"") + "/>";
	}

}
