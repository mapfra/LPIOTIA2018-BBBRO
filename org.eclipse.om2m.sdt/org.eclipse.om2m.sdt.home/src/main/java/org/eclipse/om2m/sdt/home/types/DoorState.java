/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

import org.eclipse.om2m.sdt.datapoints.EnumDataPoint;

public abstract class DoorState extends EnumDataPoint<Integer> {
	
	static public final int Closed = 1;
	static public final int Open = 2;
	static public final int Opening = 3;
	static public final int Closing = 4;
	static public final int Stopped = 5;
	
	public DoorState(String name) {
		super(name, HomeDataType.DoorState);
		setValidValues(new Integer[] { Closed, Open, Opening, Closing, Stopped });
	}

}
