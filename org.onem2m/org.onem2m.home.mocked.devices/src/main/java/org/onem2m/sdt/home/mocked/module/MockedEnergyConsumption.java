/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.EnergyConsumption;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.FloatDataPoint;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.impl.DataPointException;

public class MockedEnergyConsumption extends EnergyConsumption {

	public MockedEnergyConsumption(String name, Domain domain, FloatDataPoint value) {
		super(name, domain, value);
		
		// absolute energy consumption data
		setAbsoluteEnergyConsumption(new FloatDataPoint("absoluteEnergyConsumption") {
			@Override
			public Float doGetValue() throws DataPointException {
				return new Float(Math.random() * 1000);
			}
		});
		
		// rounding energy consumption data
		setRoundingEnergyConsumption(new IntegerDataPoint("roundingEnergyConsumption") {
			@Override
			public Integer doGetValue() throws DataPointException {
				return new Integer((int)(Math.random() * 1000));
			}
		});
		
		// significant figures
		setSignificantDigits(new IntegerDataPoint("significantDigits") {
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
		setMultiplyingFactors(new IntegerDataPoint("multiplyingFactors") {
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
		setVoltage(new FloatDataPoint("voltage") {
			private Float dataPointValue = (float) 220;
			@Override
			public Float doGetValue() throws DataPointException {
				return dataPointValue;
			}
		});
		
		// current
		setCurrent(new FloatDataPoint("current") {
			private Float dataPointValue = (float) 0;
			@Override
			public Float doGetValue() throws DataPointException {
				return dataPointValue;
			}
		});
		
		// frequency
		setFrequency(new FloatDataPoint("frequency") {
			private Float dataPointValue = (float) 50;
			@Override
			public Float doGetValue() throws DataPointException {
				return dataPointValue;
			}
		});
	}

}
