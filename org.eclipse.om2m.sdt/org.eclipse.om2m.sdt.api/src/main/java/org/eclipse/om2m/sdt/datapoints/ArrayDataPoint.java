/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.datapoints;

import java.util.List;

import org.eclipse.om2m.sdt.Identifiers;
import org.eclipse.om2m.sdt.types.Array;
import org.eclipse.om2m.sdt.types.DataType;

public abstract class ArrayDataPoint<T> extends ValuedDataPoint<List<T>> {

	public ArrayDataPoint(Identifiers name) {
		super(name, new DataType("array", new Array<T>()));
	}

}
