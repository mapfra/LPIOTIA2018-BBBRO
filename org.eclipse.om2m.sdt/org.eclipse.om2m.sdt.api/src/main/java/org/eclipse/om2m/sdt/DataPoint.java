/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt;

import org.eclipse.om2m.sdt.types.DataType;

public class DataPoint extends Element {
	
	private boolean optional;
	private boolean readable;
	private boolean writable;
	
	private DataType type;
	
	private Device owner;
	
	private Module parent;
	
	private String longDefinitionType;
	private String shortDefinitionType;

	public DataPoint(final Identifiers name, final DataType type) {
		super(name.getShortName());
		if (type == null)
			throw new IllegalArgumentException();
		this.type = type;
		optional = false;
		readable = true;
		writable = true;
		longDefinitionType = name.getLongName();
		shortDefinitionType = name.getShortName();
	}

	public DataType getDataType() {
		return type;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(final boolean optional) {
		this.optional = optional;
	}

	public boolean isReadable() {
		return readable;
	}

	public void setReadable(final boolean readable) {
		this.readable = readable;
	}

	public boolean isWritable() {
		return writable;
	}

	public void setWritable(final boolean writable) {
		this.writable = writable;
	}

	@Override
	protected String prettyPrint(String t1) {
		String t2 = t1 + "\t";
		StringBuffer ret = new StringBuffer(t1).append("<DataPoint name=\"" + getName()
			+ "\" readable=\"" + readable + "\" writable=\"" + writable + "\">");
		if (getDoc() != null) ret.append("\n").append(t2).append(getDoc());
		return ret.append("\n").append(type.prettyPrint(t2))
				.append("\n").append(t1).append("</DataPoint>").toString();
	}

	void setOwner(Device owner) {
		this.owner = owner;
	}

	public Device getOwner() {
		return owner;
	}
	
	void setParent(Module parent) {
		this.parent = parent;
	}

	public Module getParent() {
		return parent;
	}
	
	/**
	 * @return the longDefinitionType
	 */
	public String getLongDefinitionType() {
		return longDefinitionType;
	}

	/**
	 * @return the shortDefinitionType
	 */
	public String getShortDefinitionType() {
		return shortDefinitionType;
	}

}
