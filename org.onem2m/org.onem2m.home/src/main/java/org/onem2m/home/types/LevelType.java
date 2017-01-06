/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.types;

import org.onem2m.sdt.datapoints.EnumDataPoint;

public abstract class LevelType extends EnumDataPoint<Integer> {
	
	static public final int zero = 1;
	static public final int low = 2;
	static public final int medium = 3;
	static public final int high = 4;
	static public final int maximum = 5;
	
	public LevelType(String name) {
		super(name, HomeDataType.Level);
		setValidValues(new Integer[] { zero, low, medium, high, maximum });
	}

}
