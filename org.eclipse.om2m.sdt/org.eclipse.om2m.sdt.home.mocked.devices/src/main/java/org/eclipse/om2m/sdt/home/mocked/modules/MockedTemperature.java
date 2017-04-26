/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.mocked.modules;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.FloatDataPoint;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.modules.Temperature;

public class MockedTemperature extends Temperature {

	public MockedTemperature(String name, Domain domain, FloatDataPoint value) {
		super(name, domain, value);
		
		setStepValue(new FloatDataPoint("stepValue") {
			@Override
			public Float doGetValue() throws DataPointException {
				return (float) 1;
			}
		});
		
		setUnits(new StringDataPoint("units") {
			@Override
			protected String doGetValue() throws DataPointException {
				return "°C";
			}
		});
	}

}
