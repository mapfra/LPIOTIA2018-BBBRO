/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.datapoints;

import org.eclipse.om2m.sdt.Identifiers;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.types.DataType;

public abstract class ClonedEnum extends EnumDataPoint<Integer> {

	private EnumDataPoint<Integer> dp;
	
	public ClonedEnum(Identifiers names, DataType type, EnumDataPoint<Integer> dp) {
		super(names, type);
		this.dp = dp;
	}
	
	@Override
	protected void doSetValue(Integer val) throws DataPointException {
		dp.doSetValue(val);
	}
	
	@Override
	protected Integer doGetValue() throws DataPointException {
		return dp.doGetValue();
	}

}
