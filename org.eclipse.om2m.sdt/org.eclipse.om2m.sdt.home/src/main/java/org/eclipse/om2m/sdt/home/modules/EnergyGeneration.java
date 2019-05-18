/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.FloatDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class EnergyGeneration extends Module {
	
	private FloatDataPoint powerGenerationData;
	
	private IntegerDataPoint roundingEnergyGeneration;
	private IntegerDataPoint significantDigits;
	private IntegerDataPoint multiplyingFactors;
	
	public EnergyGeneration(final String name, final Domain domain) {
		super(name, domain, ModuleType.energyGeneration);
	}
	
	public EnergyGeneration(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain);
		FloatDataPoint powerGenerationData = 
				(FloatDataPoint) dps.get(DatapointType.powerGenerationData.getShortName());
		if (powerGenerationData != null)
			setPowerGenerationData(powerGenerationData);
		IntegerDataPoint roundingEnergyGeneration = 
				(IntegerDataPoint) dps.get(DatapointType.roundingEnergyGeneration.getShortName());
		if (roundingEnergyGeneration != null)
			setRoundingEnergyGeneration(roundingEnergyGeneration);
		IntegerDataPoint significantDigits = 
				(IntegerDataPoint) dps.get(DatapointType.significantDigits.getShortName());
		if (significantDigits != null)
			setSignificantDigits(significantDigits);
		IntegerDataPoint multiplyingFactors = 
				(IntegerDataPoint) dps.get(DatapointType.multiplyingFactors.getShortName());
		if (multiplyingFactors != null)
			setMultiplyingFactors(multiplyingFactors);
	}

	public float getPowerGenerationData() throws DataPointException, AccessException {
		return powerGenerationData.getValue();
	}

	public void setPowerGenerationData(float b) throws DataPointException {
		throw new DataPointException("Not writable");
	}

	public void setPowerGenerationData(FloatDataPoint dp) {
		this.powerGenerationData = dp;
		this.powerGenerationData.setOptional(true);
		this.powerGenerationData.setWritable(false);
		this.powerGenerationData.setDoc("Amount of instaneous generation data.");
		addDataPoint(powerGenerationData);
	}

	public void setMultiplyingFactors(IntegerDataPoint dp) {
		this.multiplyingFactors = dp;
		this.multiplyingFactors.setOptional(true);
		this.multiplyingFactors.setWritable(true);
		this.multiplyingFactors.setDoc("The unit for data (multiplying factors) Ex. 1kWh, 0.1kWh, 0.01kWh etc.");
		addDataPoint(multiplyingFactors);
	}

	public Integer getMultiplyingFactors() throws DataPointException, AccessException {
		if (multiplyingFactors == null)
			throw new DataPointException("Not implemented");
		return multiplyingFactors.getValue();
	}

	public void setMultiplyingFactors(Integer i) throws DataPointException, AccessException {
		if (multiplyingFactors == null)
			throw new DataPointException("Not implemented");
		multiplyingFactors.setValue(i);
	}

	public void setRoundingEnergyGeneration(IntegerDataPoint dp) {
		this.roundingEnergyGeneration = dp;
		this.roundingEnergyGeneration.setOptional(true);
		this.roundingEnergyGeneration.setWritable(false);
		this.roundingEnergyGeneration.setDoc("This energy generation data can be calculated by using significantFigures and units.");
		addDataPoint(roundingEnergyGeneration);
	}

	public int getRoundingEnergyGeneration() throws DataPointException, AccessException {
		if (roundingEnergyGeneration == null)
			throw new DataPointException("Not implemented");
		return roundingEnergyGeneration.getValue();
	}

	public void setRoundingEnergyGeneration(int b) throws DataPointException {
		throw new DataPointException("Not writable");
	}

	public void setSignificantDigits(IntegerDataPoint dp) {
		this.significantDigits = dp;
		this.significantDigits.setOptional(true);
		this.significantDigits.setWritable(true);
		this.significantDigits.setDoc("The number of effective digits for data.");
		addDataPoint(significantDigits);
	}

	public int getSignificantDigits() throws DataPointException, AccessException {
		if (significantDigits == null)
			throw new DataPointException("Not implemented");
		return significantDigits.getValue();
	}

	public void setSignificantDigits(int b) throws DataPointException, AccessException {
		if (significantDigits == null)
			throw new DataPointException("Not implemented");
		if (b < 0)
			throw new DataPointException("Negative value");
		significantDigits.setValue(b);
	}

}
