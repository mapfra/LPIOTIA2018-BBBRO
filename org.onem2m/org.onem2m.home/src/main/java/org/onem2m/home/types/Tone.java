/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.types;

import org.onem2m.sdt.datapoints.EnumDataPoint;

public abstract class Tone extends EnumDataPoint<Integer> {
	
	static public final int Fire = 1;
	static public final int Theft = 2;
	static public final int Emergency = 3;
	static public final int Doorbell = 4;
	static public final int DeviceFail = 5;
	static public final int Silent = 6;
	
	public Tone(String name) {
		super(name, HomeDataType.Tone);
		setValidValues(new Integer[] { Fire, Theft, Emergency, Doorbell, DeviceFail, Silent });
	}
	
}
