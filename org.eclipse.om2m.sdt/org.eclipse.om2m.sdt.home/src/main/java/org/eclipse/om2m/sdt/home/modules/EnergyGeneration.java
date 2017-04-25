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
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class EnergyGeneration extends Module {
	
	private FloatDataPoint powerGenerationData;
	
	private IntegerDataPoint roundingEnergyGeneration;
	private IntegerDataPoint significantDigits;
	private IntegerDataPoint multiplyingFactors;
	
	public EnergyGeneration(final String name, final Domain domain) {
		super(name, domain, ModuleType.energyGeneration.getDefinition(),
				ModuleType.energyGeneration.getLongDefinitionName(),
				ModuleType.energyGeneration.getShortDefinitionName());
	}
	
	public EnergyGeneration(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain);
		FloatDataPoint powerGenerationData = (FloatDataPoint) dps.get("powerGenerationData");
		if (powerGenerationData != null)
			setPowerGenerationData(powerGenerationData);
		IntegerDataPoint roundingEnergyGeneration = (IntegerDataPoint) dps.get("roundingEnergyGeneration");
		if (roundingEnergyGeneration != null)
			setRoundingEnergyGeneration(roundingEnergyGeneration);
		IntegerDataPoint significantDigits = (IntegerDataPoint) dps.get("significantDigits");
		if (significantDigits != null)
			setSignificantDigits(significantDigits);
		IntegerDataPoint multiplyingFactors = (IntegerDataPoint) dps.get("multiplyingFactors");
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
		this.powerGenerationData.setLongDefinitionType("powerGenerationData");
		this.powerGenerationData.setShortDefinitionType("poGDa");
		addDataPoint(powerGenerationData);
	}

	public void setMultiplyingFactors(IntegerDataPoint dp) {
		this.multiplyingFactors = dp;
		this.multiplyingFactors.setOptional(true);
		this.multiplyingFactors.setWritable(true);
		this.multiplyingFactors.setDoc("The unit for data (multiplying factors) Ex. 1kWh, 0.1kWh, 0.01kWh etc.");
		this.multiplyingFactors.setLongDefinitionType("multiplyingFactors");
		this.multiplyingFactors.setShortDefinitionType("mulFs");
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
		this.roundingEnergyGeneration.setLongDefinitionType("roundingEnergyGeneration");
		this.roundingEnergyGeneration.setShortDefinitionType("roEGn");
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
		this.significantDigits.setLongDefinitionType("significantDigits");
		this.significantDigits.setShortDefinitionType("sigDs");
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
