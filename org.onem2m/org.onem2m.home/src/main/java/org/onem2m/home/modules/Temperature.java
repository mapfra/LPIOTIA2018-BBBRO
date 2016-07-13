package org.onem2m.home.modules;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.FloatDataPoint;
import org.onem2m.sdt.datapoints.StringDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

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
