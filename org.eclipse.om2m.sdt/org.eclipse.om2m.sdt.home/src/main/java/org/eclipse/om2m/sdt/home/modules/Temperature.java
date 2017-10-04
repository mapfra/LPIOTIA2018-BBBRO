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
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class Temperature extends Module {
	
	private FloatDataPoint currentTemperature;
	private FloatDataPoint targetTemperature;
	private FloatDataPoint minValue;
	private FloatDataPoint maxValue;
	private FloatDataPoint stepValue;
	private StringDataPoint unit;
	
	public Temperature(final String name, final Domain domain, FloatDataPoint currentTemperature) {
		super(name, domain, ModuleType.temperature);

		if ((currentTemperature == null) ||
				! currentTemperature.getShortDefinitionType().equals(DatapointType.currentTemperature.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong currentTemperature datapoint: " + currentTemperature);
		}
		this.currentTemperature = currentTemperature;
		this.currentTemperature.setWritable(false);
		this.currentTemperature.setDoc("The current currentTemperature");
		addDataPoint(this.currentTemperature);
	}

	public Temperature(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (FloatDataPoint) dps.get(DatapointType.currentTemperature.getShortName()));
		
		FloatDataPoint targetTemperature = (FloatDataPoint) dps.get(DatapointType.targetTemperature.getShortName());
		if (targetTemperature != null)
			setTargetTemperature(targetTemperature);
			
		FloatDataPoint minValue = (FloatDataPoint) dps.get(DatapointType.minValue.getShortName());
		if (minValue != null)
			setMinValue(minValue);
		
		FloatDataPoint maxValue = (FloatDataPoint) dps.get(DatapointType.maxValue.getShortName());
		if (maxValue != null)
			setMaxValue(maxValue);
		
		FloatDataPoint stepValue = (FloatDataPoint) dps.get(DatapointType.stepValue.getShortName());
		if (stepValue != null)
			setStepValue(stepValue);
		
		StringDataPoint unit = (StringDataPoint) dps.get(DatapointType.unit.getShortName());
		if (unit != null)
			setUnit(unit);
	}

	public float getCurrentTemperature() throws DataPointException, AccessException {
		return currentTemperature.getValue();
	}

	public void setTargetTemperature(FloatDataPoint dp) {
		this.targetTemperature = dp;
		this.targetTemperature.setWritable(true);
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

	public void setUnit(StringDataPoint dp) {
		this.unit = dp;
		this.unit.setOptional(true);
		this.unit.setWritable(false);
		this.unit.setDoc("The list of units for the temperature values. The default is Celsius only [C].");
		addDataPoint(unit);
	}

	public String getUnit() throws DataPointException, AccessException {
		if (unit == null)
			throw new DataPointException("Not implemented");
		return unit.getValue();
	}

}
