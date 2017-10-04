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

public class AlertColourCode extends ClonedEnum {
	
	static public final int Red = 1;
	static public final int Green = 2;
	
	static private List<Integer> values = Arrays.asList(
			Red, Green
	);

	public AlertColourCode(Identifiers identifiers, EnumDataPoint<Integer> dp) {
		super(identifiers, HomeDataType.AlertColourCode, dp);
		setValidValues(values);
	}

}
