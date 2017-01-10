/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

import org.eclipse.om2m.sdt.datapoints.EnumDataPoint;

public abstract class AlertColourCode extends EnumDataPoint<Integer> {
	
	static public final int Red = 1;
	static public final int Green = 2;
	
	public AlertColourCode(String name) {
		super(name, HomeDataType.AlertColourCode);
		setValidValues(new Integer[] { Red, Green });
	}

}
