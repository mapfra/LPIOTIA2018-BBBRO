/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.datapoints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.om2m.sdt.Identifiers;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.types.DataType;

public abstract class EnumDataPoint<T> extends ValuedDataPoint<T> {
	
	static final private Identifiers anon = new Identifiers() {
		@Override
		public String getShortName() {
			return "enum";
		}
		@Override
		public String getLongName() {
			return "enumDataPoint";
		}
		@Override
		public String getDefinition() {
			return "enum";
		}
	};

	private List<T> values;

	public EnumDataPoint(Identifiers name) {
		this(name, DataType.Enum);
	}
	
	public EnumDataPoint(Identifiers name, DataType type) {
		super((name == null) ? anon : name, type);
		values = new ArrayList<T>();
	}
	
	public void setValue(T v) throws DataPointException, AccessException {
		if (! values.contains(v))
			throw new DataPointException("Unknown value");
		super.setValue(v);
	}
	
	public void setValidValues(T[] v) {
		this.values = Arrays.asList(v);
	}
	
	public void setValidValues(Collection<T> v) {
		this.values.clear();
		this.values.addAll(v);
	}

	public List<T> getValidValues() {
		return values;
	}
	
}
