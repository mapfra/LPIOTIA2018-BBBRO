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
		public String toString(Object val) throws Exception;
		public Object fromString(String val) throws Exception;
	}
	
	static public final DataType Boolean = new DataType(SimpleType.Boolean);
	static public final DataType Byte = new DataType(SimpleType.Byte);
	static public final DataType Integer = new DataType(SimpleType.Integer);
	static public final DataType Float = new DataType(SimpleType.Float);
	static public final DataType String = new DataType(SimpleType.String);
	static public final DataType Enum = new DataType(SimpleType.Enum);
	static public final DataType Date = new DataType(SimpleType.Date);
	static public final DataType Time = new DataType(SimpleType.Time);
	static public final DataType Datetime = new DataType(SimpleType.Datetime);
	static public final DataType Blob = new DataType(SimpleType.Blob);
	static public final DataType Uri = new DataType(SimpleType.Uri);
	
	private String unitOfMeasure;
	
	private TypeChoice type;
	
	private Map<String, Constraint> constraints;

	public DataType(final TypeChoice type) {
		super(type.getOneM2MType());
		this.type = type;
		this.constraints = new HashMap<String, Constraint>();
	}
	
	static public DataType getDataType(final String name) {
		String s = name.trim();
		if (s.startsWith("[")) {
			DataType dt = getDataType(s.substring(1, s.length()-1));
			return (dt == null) ? null : new DataType(new Array<>(dt));
		}
		SimpleType st = SimpleType.getSimpleType(s);
		if (st != null)
			return new DataType(st);
		return null;
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

	@Override
	public String toString() {
		return "<" + getClass().getSimpleName() + " name=" + name 
				+ " type=" + getTypeChoice().getOneM2MType() + "/>";
	}

}
