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
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.modules.EnergyConsumption;
import org.eclipse.om2m.sdt.home.types.DatapointType;

public class MockedEnergyConsumption extends EnergyConsumption {

	public MockedEnergyConsumption(String name, Domain domain, FloatDataPoint value) {
		super(name, domain, value);
		
		// absolute energy consumption data
		setAbsoluteEnergyConsumption(new FloatDataPoint(DatapointType.absoluteEnergyConsumption) {
			@Override
			public Float doGetValue() throws DataPointException {
				return new Float(Math.random() * 1000);
			}
		});
		
		// rounding energy consumption data
		setRoundingEnergyConsumption(new IntegerDataPoint(DatapointType.roundingEnergyConsumption) {
			@Override
			public Integer doGetValue() throws DataPointException {
				return new Integer((int)(Math.random() * 1000));
			}
		});
		
		// significant figures
		setSignificantDigits(new IntegerDataPoint(DatapointType.significantDigits) {
			private Integer dataPointValue = 1;
			@Override
			public void doSetValue(Integer value) throws DataPointException {
				dataPointValue = value;
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				return dataPointValue;
			}
		});
		
		// multiplying factors
		setMultiplyingFactors(new IntegerDataPoint(DatapointType.multiplyingFactors) {
			private Integer dataPointValue = 2;
			@Override
			public void doSetValue(Integer value) throws DataPointException {
				dataPointValue = value;
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				return dataPointValue;
			}
		});
		
		// voltage
		setVoltage(new FloatDataPoint(DatapointType.voltage) {
			private Float dataPointValue = (float) 220;
			@Override
			public Float doGetValue() throws DataPointException {
				return dataPointValue;
			}
		});
		
		// current
		setCurrent(new FloatDataPoint(DatapointType.current) {
			private Float dataPointValue = (float) 0;
			@Override
			public Float doGetValue() throws DataPointException {
				return dataPointValue;
			}
		});
		
		// frequency
		setFrequency(new FloatDataPoint(DatapointType.frequency) {
			private Float dataPointValue = (float) 50;
			@Override
			public Float doGetValue() throws DataPointException {
				return dataPointValue;
			}
		});
	}

}
