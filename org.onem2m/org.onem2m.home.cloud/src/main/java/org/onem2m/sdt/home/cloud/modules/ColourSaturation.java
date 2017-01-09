/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.home.cloud.modules;

import java.util.Map;

import org.onem2m.sdt.DataPoint;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.IntegerDataPoint;

public class ColourSaturation extends org.onem2m.home.modules.ColourSaturation {
	
	public ColourSaturation(final String name, final Domain domain, Map<String, DataPoint> dps) {
		super(name, domain, 
			(IntegerDataPoint) dps.get("colourSaturation"));
	}
	
}
