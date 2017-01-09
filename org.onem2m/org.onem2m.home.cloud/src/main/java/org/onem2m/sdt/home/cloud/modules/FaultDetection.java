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
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.datapoints.StringDataPoint;

public class FaultDetection extends org.onem2m.home.modules.FaultDetection {

	public FaultDetection(final String name, final Domain domain, Map<String, DataPoint> dps) {
		super(name, domain, (BooleanDataPoint) dps.get("status"));
		IntegerDataPoint code = (IntegerDataPoint) dps.get("code");
		if (code != null)
			setCode(code);
		StringDataPoint description = (StringDataPoint) dps.get("description");
		if (description != null)
			setDescription(description);
	}

}
