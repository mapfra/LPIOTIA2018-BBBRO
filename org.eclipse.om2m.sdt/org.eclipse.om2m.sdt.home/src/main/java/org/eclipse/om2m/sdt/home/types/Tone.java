/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

import java.util.Arrays;
import java.util.List;

import org.eclipse.om2m.sdt.Identifiers;
import org.eclipse.om2m.sdt.datapoints.ClonedEnum;
import org.eclipse.om2m.sdt.datapoints.EnumDataPoint;

public class Tone extends ClonedEnum {
	
	static public final int Fire = 1;
	static public final int Theft = 2;
	static public final int Emergency = 3;
	static public final int Doorbell = 4;
	static public final int DeviceFail = 5;
	static public final int Silent = 6;
	
	static private List<Integer> values = Arrays.asList(
			Fire, Theft, Emergency, Doorbell, DeviceFail, Silent
	);

	public Tone(EnumDataPoint<Integer> dp) {
		this(DatapointType.tone, dp);
	}
	
	public Tone(Identifiers identifiers, EnumDataPoint<Integer> dp) {
		super(identifiers, HomeDataType.Tone, dp);
		setValidValues(values);
	}
	
}
