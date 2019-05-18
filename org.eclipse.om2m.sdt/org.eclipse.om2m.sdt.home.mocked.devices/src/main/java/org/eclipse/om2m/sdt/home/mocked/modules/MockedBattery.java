/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.mocked.modules;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.modules.Battery;
import org.eclipse.om2m.sdt.home.types.DatapointType;

public class MockedBattery extends Battery {
	
	static private final int CAPACITY = 37;

	public MockedBattery(String name, Domain domain) {
		super(name, domain, new IntegerDataPoint(DatapointType.level) {
			@Override
			protected Integer doGetValue() throws DataPointException {
				return (int)(Math.random() * CAPACITY);
			}
		});
		
		setCapacity(new IntegerDataPoint(DatapointType.capacity) {
			@Override
			protected Integer doGetValue() throws DataPointException {
				return CAPACITY;
			}
		});
		
		setCharging(new BooleanDataPoint(DatapointType.charging) {
			@Override
			protected Boolean doGetValue() throws DataPointException {
				return true;
			}
		});
		
		setDischarging(new BooleanDataPoint(DatapointType.discharging) {
			@Override
			protected Boolean doGetValue() throws DataPointException {
				return false;
			}
		});
	}

}
