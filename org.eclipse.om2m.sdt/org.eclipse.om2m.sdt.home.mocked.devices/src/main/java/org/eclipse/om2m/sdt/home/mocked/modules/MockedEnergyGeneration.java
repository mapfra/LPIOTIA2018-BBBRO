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
import org.eclipse.om2m.sdt.home.modules.EnergyGeneration;
import org.eclipse.om2m.sdt.home.types.DatapointType;

public class MockedEnergyGeneration extends EnergyGeneration {

	public MockedEnergyGeneration(String name, Domain domain) {
		super(name, domain);

		setPowerGenerationData(new FloatDataPoint(DatapointType.powerGenerationData) {
			@Override
			public Float doGetValue() throws DataPointException {
				return new Float(Math.random() * 1000);
			}
		});
		
		setRoundingEnergyGeneration(new IntegerDataPoint(DatapointType.roundingEnergyGeneration) {
			@Override
			public Integer doGetValue() throws DataPointException {
				return new Integer((int)(Math.random() * 1000));
			}
		});
		
		setSignificantDigits(new IntegerDataPoint(DatapointType.significantDigits) {
			private Integer significantDigits = new Integer(1);
			@Override
			public void doSetValue(Integer value) throws DataPointException {
				significantDigits = value;
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				return significantDigits;
			}
		});
		
		setMultiplyingFactors(new IntegerDataPoint(DatapointType.multiplyingFactors) {
			Integer multiplyingFactors = new Integer(2);
			@Override
			public void doSetValue(Integer value) throws DataPointException {
				multiplyingFactors = value;
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				return multiplyingFactors;
			}
		});
	}

}
