/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt;

import org.eclipse.om2m.sdt.types.SimpleType;

public class Property extends Element {
	
	private final String shortName;
	
	private boolean optional;
	
	private String value;
	
	private SimpleType type;

	public Property(final Identifiers name) {
		super(name.getLongName());
		optional = false;
		type = SimpleType.String;
		this.shortName = name.getShortName();
	}

	public Property(final Identifiers name, final String value) {
		this(name);
		setValue(value);
	}

	public String getName() {
		return name;
	}
	
	public String getShortName() {
		return shortName;
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
		return "<" + getClass().getSimpleName() + " \"" + name + "/" + shortName
			+ "\"=" + ((value == null) ? value : "\"" + value + "\"") + "/>";
	}

}
