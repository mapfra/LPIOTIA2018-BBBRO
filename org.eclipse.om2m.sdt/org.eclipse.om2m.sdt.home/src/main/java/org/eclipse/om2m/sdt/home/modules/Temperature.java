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
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class Temperature extends Module {
	
	private FloatDataPoint currentTemperature;
	private FloatDataPoint targetTemperature;
	private FloatDataPoint minValue;
	private FloatDataPoint maxValue;
	private FloatDataPoint stepValue;
	private StringDataPoint units;
	
	public Temperature(final String name, final Domain domain, FloatDataPoint dp) {
		super(name, domain, ModuleType.temperature.getDefinition());

		currentTemperature = dp;
		currentTemperature.setWritable(false);
		currentTemperature.setDoc("The current currentTemperature");
		addDataPoint(currentTemperature);
	}

	public Temperature(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (FloatDataPoint) dps.get("currentTemperature"));
		FloatDataPoint targetTemperature = (FloatDataPoint) dps.get("targetTemperature");
		if (targetTemperature != null){
			setTargetTemperature(targetTemperature);
			targetTemperature.setWritable(true);
		}
			
		FloatDataPoint minValue = (FloatDataPoint) dps.get("minValue");
		if (minValue != null)
			setMinValue(minValue);
		FloatDataPoint maxValue = (FloatDataPoint) dps.get("maxValue");
		if (maxValue != null)
			setMaxValue(maxValue);
		FloatDataPoint stepValue = (FloatDataPoint) dps.get("stepValue");
		if (stepValue != null)
			setStepValue(stepValue);
		StringDataPoint units = (StringDataPoint) dps.get("units");
		if (units != null)
			setUnits(units);
	}



	public Temperature(String name, Domain domain, FloatDataPoint currentTemperature, FloatDataPoint targetTemperature,
			StringDataPoint unit, FloatDataPoint minValue, FloatDataPoint maxValue,
			FloatDataPoint stepValue) {
		// TODO Auto-generated constructor stub
		this(name, domain, (FloatDataPoint) currentTemperature);
		
		
		if(targetTemperature!=null)
			setTargetTemperature(targetTemperature);
		if(unit!=null)
			setUnits(unit);
		if(minValue!=null)
			setMinValue(minValue);
		if(maxValue!=null)
			setMaxValue(maxValue);
		if(stepValue!=null)
			setStepValue(stepValue);
		
		
	}

	public float getCurrentTemperature() throws DataPointException, AccessException {
		return currentTemperature.getValue();
	}

	public void setTargetTemperature(FloatDataPoint dp) {
		this.targetTemperature = dp;
		this.targetTemperature.setOptional(true);
		this.targetTemperature.setDoc("The desired temperature to reach.");
		addDataPoint(targetTemperature);
	}

	public float getTargetTemperature() throws DataPointException, AccessException {
		if (targetTemperature == null)
			throw new DataPointException("Not implemented");
		return targetTemperature.getValue();
	}

	public void setTargetTemperature(float b) throws DataPointException, AccessException {
		System.out.println("Korzysta z funkcji setTargetTemperature(float b) --- " + b);

		if(targetTemperature == null)
			System.out.println("WYJATEK");
		
		if (targetTemperature == null)
			throw new DataPointException("Not implemented");
		
		targetTemperature.setValue(b);
		
	}

	public void setMinValue(FloatDataPoint dp) {
		this.minValue = dp;
		this.minValue.setOptional(true);
		this.minValue.setWritable(false);
		this.minValue.setDoc("Minimum value of targetTemperature.");
		addDataPoint(minValue);
	}

	public float getMinValue() throws DataPointException, AccessException {
		if (minValue == null)
			throw new DataPointException("Not implemented");
		return minValue.getValue();
	}

	public void setMaxValue(FloatDataPoint dp) {
		this.maxValue = dp;
		this.maxValue.setOptional(true);
		this.maxValue.setWritable(false);
		this.maxValue.setDoc("Maximum value of targetTemperature.");
		addDataPoint(maxValue);
	}

	public float getMaxValue() throws DataPointException, AccessException {
		if (maxValue == null)
			throw new DataPointException("Not implemented");
		return maxValue.getValue();
	}

	public void setStepValue(FloatDataPoint dp) {
		this.stepValue = dp;
		this.stepValue.setOptional(true);
		this.stepValue.setWritable(false);
		this.stepValue.setDoc("Step value allowed for targetTemperature.");
		addDataPoint(stepValue);
	}

	public float getStepValue() throws DataPointException, AccessException {
		if (stepValue == null)
			throw new DataPointException("Not implemented");
		return stepValue.getValue();
	}

	public void setUnits(StringDataPoint dp) {
		this.units = dp;
		this.units.setOptional(true);
		this.units.setWritable(false);
		this.units.setDoc("The list of units for the temperature values. The default is Celsius only [C].");
		addDataPoint(units);
	}

	public String getUnits() throws DataPointException, AccessException {
		if (units == null)
			throw new DataPointException("Not implemented");
		return units.getValue();
	}

}
