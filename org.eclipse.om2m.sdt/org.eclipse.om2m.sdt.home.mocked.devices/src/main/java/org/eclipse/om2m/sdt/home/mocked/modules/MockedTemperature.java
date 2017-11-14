/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.mocked.modules;

import java.nio.charset.Charset;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.FloatDataPoint;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.modules.Temperature;
import org.eclipse.om2m.sdt.home.types.DatapointType;

public class MockedTemperature extends Temperature {

	public MockedTemperature(String name, Domain domain, FloatDataPoint currentTemperature) {
		super(name, domain, currentTemperature);
		
		setStepValue(new FloatDataPoint(DatapointType.stepValue) {
			@Override
			public Float doGetValue() throws DataPointException {
				return (float) 1;
			}
		});
		
		setUnit(new StringDataPoint(DatapointType.unit) {
			@Override
			protected String doGetValue() throws DataPointException {
				return "°C";
			}
		});
	}

}
