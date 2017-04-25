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

public class EnergyConsumption extends Module {
	
	private FloatDataPoint power;
	
	private IntegerDataPoint roundingEnergyConsumption;
	private IntegerDataPoint significantDigits;
	
	private FloatDataPoint absoluteEnergyConsumption;
	private FloatDataPoint voltage;
	private FloatDataPoint current;
	private FloatDataPoint frequency;
	
	private IntegerDataPoint multiplyingFactors;

	
	public EnergyConsumption(final String name, final Domain domain, FloatDataPoint value) {
		super(name, domain, ModuleType.energyConsumption.getDefinition(),
				ModuleType.energyConsumption.getLongDefinitionName(), 
				ModuleType.energyConsumption.getShortDefinitionName());

		this.power = value;
		this.power.setWritable(false);
		this.power.setDoc("The power of the device; The common unit is Watt (W).");
		this.power.setLongDefinitionType("power");
		this.power.setShortDefinitionType("power");
		addDataPoint(this.power);
	}
	
	public EnergyConsumption(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (FloatDataPoint) dps.get("power"));
		
		IntegerDataPoint roundingEnergyConsumption = (IntegerDataPoint) dps.get("roundingEnergyConsumption");
		if (roundingEnergyConsumption != null)
			setRoundingEnergyConsumption(roundingEnergyConsumption);
		IntegerDataPoint significantDigits = (IntegerDataPoint) dps.get("significantDigits");
		if (significantDigits != null)
			setSignificantDigits(significantDigits);
		IntegerDataPoint multiplyingFactors = (IntegerDataPoint) dps.get("multiplyingFactors");
		if (multiplyingFactors != null)
			setMultiplyingFactors(multiplyingFactors);
		FloatDataPoint absoluteEnergyConsumption = (FloatDataPoint) dps.get("absoluteEnergyConsumption");
		if (absoluteEnergyConsumption != null)
			setAbsoluteEnergyConsumption(absoluteEnergyConsumption);
		FloatDataPoint voltage = (FloatDataPoint) dps.get("voltage");
		if (voltage != null)
			setVoltage(voltage);
		FloatDataPoint current = (FloatDataPoint) dps.get("current");
		if (current != null)
			setCurrent(current);
		FloatDataPoint frequency = (FloatDataPoint) dps.get("frequency");
		if (frequency != null)
			setFrequency(frequency);
	}

	public float getPower() throws DataPointException, AccessException {
		return power.getValue();
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

	public void setMultiplyingFactors(Integer s) throws DataPointException, AccessException {
		if (multiplyingFactors == null)
			throw new DataPointException("Not implemented");
		multiplyingFactors.setValue(s);
	}

	public void setAbsoluteEnergyConsumption(FloatDataPoint dp) {
		this.absoluteEnergyConsumption = dp;
		this.absoluteEnergyConsumption.setOptional(true);
		this.absoluteEnergyConsumption.setWritable(false);
		this.absoluteEnergyConsumption.setDoc("The absolute energy consumption, reflecting the real measurement of accumulative energy; The common unit is Watt-hour (Wh).");
		this.absoluteEnergyConsumption.setLongDefinitionType("absoluteEnergyConsumption");
		this.absoluteEnergyConsumption.setShortDefinitionType("abECn");
		addDataPoint(absoluteEnergyConsumption);
	}

	public float getAbsoluteEnergyConsumption() throws DataPointException, AccessException {
		if (absoluteEnergyConsumption == null)
			throw new DataPointException("Not implemented");
		return absoluteEnergyConsumption.getValue();
	}

	public void setVoltage(FloatDataPoint dp) {
		this.voltage = dp;
		this.voltage.setOptional(true);
		this.voltage.setWritable(false);
		this.voltage.setDoc("The voltage of the device; The common unit is Voltage (V).");
		this.voltage.setLongDefinitionType("voltage");
		this.voltage.setShortDefinitionType("volte");
		addDataPoint(voltage);
	}

	public float getVoltage() throws DataPointException, AccessException {
		if (voltage == null)
			throw new DataPointException("Not implemented");
		return voltage.getValue();
	}

	public void setCurrent(FloatDataPoint dp) {
		this.current = dp;
		this.current.setOptional(true);
		this.current.setWritable(false);
		this.current.setDoc("The current of the device; The common unit is Current (A).");
		this.current.setLongDefinitionType("current");
		this.current.setShortDefinitionType("currt");
		addDataPoint(current);
	}

	public float getCurrent() throws DataPointException, AccessException {
		if (current == null)
			throw new DataPointException("Not implemented");
		return current.getValue();
	}

	public void setFrequency(FloatDataPoint dp) {
		this.frequency = dp;
		this.frequency.setOptional(true);
		this.frequency.setWritable(false);
		this.frequency.setDoc("The frequency of the device; The common unit is Hertz (Hz).");
		this.frequency.setLongDefinitionType("frequency");
		this.frequency.setShortDefinitionType("freqy");
		addDataPoint(frequency);
	}

	public float getFrequency() throws DataPointException, AccessException {
		if (frequency == null)
			throw new DataPointException("Not implemented");
		return current.getValue();
	}

	public void setRoundingEnergyConsumption(IntegerDataPoint dp) {
		this.roundingEnergyConsumption = dp;
		this.roundingEnergyConsumption.setOptional(true);
		this.roundingEnergyConsumption.setWritable(false);
		this.roundingEnergyConsumption.setDoc("This energy consumption data can be calculated by using significantDigits and units.");
		this.roundingEnergyConsumption.setLongDefinitionType("roundingEnergyConsumption");
		this.roundingEnergyConsumption.setShortDefinitionType("roECn");
		addDataPoint(roundingEnergyConsumption);
	}

	public int getRoundingEnergyConsumption() throws DataPointException, AccessException {
		if (roundingEnergyConsumption == null)
			throw new DataPointException("Not implemented");
		return roundingEnergyConsumption.getValue();
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
