/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

import org.eclipse.om2m.sdt.datapoints.EnumDataPoint;

public abstract class LockState extends EnumDataPoint<Integer> {
	
	static public final int Locked  = 1;
	static public final int Unlocked = 2;
	static public final int NotfullyLocked = 3;
	static public final int Unknown = 4;
	
	public LockState(String name) {
		super(name, HomeDataType.LockState);
		setValidValues(new Integer[] { Locked, Unlocked, NotfullyLocked, Unknown });
	}

}
