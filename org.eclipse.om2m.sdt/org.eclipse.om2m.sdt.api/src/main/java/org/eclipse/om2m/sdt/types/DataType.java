/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.types;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.om2m.sdt.Constraint;
import org.eclipse.om2m.sdt.Element;

public class DataType extends Element {
	
	public interface TypeChoice {
		public String getOneM2MType();
	}
	
	static public final DataType Boolean = new DataType("boolean", SimpleType.Boolean);
	static public final DataType Byte = new DataType("byte", SimpleType.Byte);
	static public final DataType Integer = new DataType("integer", SimpleType.Integer);
	static public final DataType Float = new DataType("float", SimpleType.Float);
	static public final DataType String = new DataType("string", SimpleType.String);
	static public final DataType Enum = new DataType("enum", SimpleType.Enum);
	static public final DataType Date = new DataType("date", SimpleType.Date);
	static public final DataType Time = new DataType("time", SimpleType.Time);
	static public final DataType Datetime = new DataType("datetime", SimpleType.Datetime);
	static public final DataType Blob = new DataType("blob", SimpleType.Blob);
	static public final DataType Uri = new DataType("uri", SimpleType.Uri);
	
	private String unitOfMeasure;
	
	private TypeChoice type;
	
	private Map<String, Constraint> constraints;

	public DataType(final String name, final TypeChoice type) {
		super(name);
		this.type = type;
		this.constraints = new HashMap<String, Constraint>();
	}

	public TypeChoice getTypeChoice() {
		return type;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(final String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public Collection<String> getConstraintNames() {
		return constraints.keySet();
	}

	public Collection<Constraint> getConstraints() {
		return constraints.values();
	}

	public Constraint getConstraint(final String name) {
		return constraints.get(name);
	}

	public void addConstraint(final Constraint constraint) {
		this.constraints.put(constraint.getName(), constraint);
	}

	public void removeConstraint(final String name) {
		this.constraints.remove(name);
	}

	@Override
	public String prettyPrint(String t1) {
		String t2 = t1 + "\t";
		StringBuffer ret = new StringBuffer(t1).append("<DataType>");
		if (getDoc() != null) ret.append("\n").append(t2).append(getDoc());
		ret.append("\n").append(t2).append(type);
		prettyPrint(ret, constraints.values(), "Constraints", t1);
		return ret.append("\n").append(t1).append("</DataType>").toString();
	}

}
