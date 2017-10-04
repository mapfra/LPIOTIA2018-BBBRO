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

public class DoorState extends ClonedEnum {
	
	static public final int Closed = 1;
	static public final int Open = 2;
	static public final int Opening = 3;
	static public final int Closing = 4;
	static public final int Stopped = 5;
	
	static private List<Integer> values = Arrays.asList(
			Closed, Open, Opening, Closing, Stopped
	);

	public DoorState(EnumDataPoint<Integer> dp) {
		this(DatapointType.doorState, dp);
	}
	
	public DoorState(Identifiers name, EnumDataPoint<Integer> dp) {
		super(name, HomeDataType.DoorState, dp);
		setValidValues(values);
	}

}
