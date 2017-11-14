/*******************************************************************************
 * Copyright (c) 2017 Deutsche Telekom.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.Action;
import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.args.Command;
import org.eclipse.om2m.sdt.datapoints.FloatDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.ActionException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.actions.DecrementNumberValue;
import org.eclipse.om2m.sdt.home.actions.IncrementNumberValue;
import org.eclipse.om2m.sdt.home.actions.ResetNumberValue;
import org.eclipse.om2m.sdt.home.types.ActionType;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class NumberValue extends Module {
	
	private FloatDataPoint numberValue;
	private FloatDataPoint minValue;
	private FloatDataPoint maxValue;
	private FloatDataPoint defaultValue;
	private FloatDataPoint step;
	
	private Action decrementNumberValue;
	private Action incrementNumberValue;
	private Action resetNumberValue;

	public NumberValue(final String name, final Domain domain, FloatDataPoint numberValue, FloatDataPoint minValue, FloatDataPoint maxValue, FloatDataPoint defaultValue, FloatDataPoint step) {
		this(name, domain, numberValue, minValue, maxValue, defaultValue, step, ModuleType.numberValue);
	}
	
	public NumberValue(final String name, final Domain domain, FloatDataPoint numberValue) {
		this(name, domain, numberValue, null, null, null, null);
	}
	
	protected NumberValue(final String name, final Domain domain, FloatDataPoint numberValue, FloatDataPoint minValue, FloatDataPoint maxValue, FloatDataPoint defaultValue, FloatDataPoint step, ModuleType type) {
		super(name, domain, type);

		// numberValue is a mandatory data point
		if ((numberValue == null) ||	! numberValue.getShortDefinitionType().equals(DatapointType.numberValue.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong numberValue datapoint: " + numberValue);
		}
		this.numberValue = numberValue;
		this.numberValue.setDoc("The actual value of the number.");
		this.numberValue.setOptional(false);
		this.numberValue.setReadable(true);
		addDataPoint(this.numberValue);
		

		if (minValue != null) {
			if (! minValue.getShortDefinitionType().equals(DatapointType.minValue.getShortName())) {
				domain.removeModule(getName());
				throw new IllegalArgumentException("Wrong minValue datapoint: " + minValue);
			}
			this.minValue = minValue;
			this.minValue.setDoc("The optional minimum value of the number. The default is the system-specific minimum value for a float value.");
			this.minValue.setOptional(true);
			this.minValue.setReadable(true);
			addDataPoint(this.minValue);
		}
		
		if (maxValue != null) {
			if (! maxValue.getShortDefinitionType().equals(DatapointType.maxValue.getShortName())) {
				domain.removeModule(getName());
				throw new IllegalArgumentException("Wrong maxValue datapoint: " + maxValue);
			}
			this.maxValue = maxValue;
			this.maxValue.setDoc("The optional maximum value of the number. The default is the system-specific maximum value for a float value.");
			this.maxValue.setOptional(true);
			this.maxValue.setReadable(true);
			addDataPoint(this.maxValue);
		}
		
		if (defaultValue != null) {
			if (! defaultValue.getShortDefinitionType().equals(DatapointType.defaultValue.getShortName())) {
				domain.removeModule(getName());
				throw new IllegalArgumentException("Wrong defaultValue datapoint: " + defaultValue);
			}
			this.defaultValue = defaultValue;
			this.defaultValue.setDoc("The optional default value for the number. The default is 0.0.");
			this.defaultValue.setOptional(true);
			this.defaultValue.setReadable(true);
			addDataPoint(this.defaultValue);
		}
		
		if (step != null) {
			if (! step.getShortDefinitionType().equals(DatapointType.step.getShortName())) {
				domain.removeModule(getName());
				throw new IllegalArgumentException("Wrong step datapoint: " + step);
			}
			this.step = step;
			this.step.setDoc("The optional step size for controlled increment and decrement. The default is 1.0 , even when this data point is not implemeneted.");
			this.step.setOptional(true);
			this.step.setReadable(true);
			addDataPoint(this.step);
		}
	}


	public NumberValue(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (FloatDataPoint) dps.get(DatapointType.numberValue.getShortName()),
				(FloatDataPoint) dps.get(DatapointType.minValue.getShortName()),
				(FloatDataPoint) dps.get(DatapointType.maxValue.getShortName()),
				(FloatDataPoint) dps.get(DatapointType.defaultValue.getShortName()),
				(FloatDataPoint) dps.get(DatapointType.step.getShortName()));
	}

	
	public void addAction(Action action) {
		if (action.getShortDefinitionName().equals(ActionType.resetNumberValue.getShortName())) {
			this.resetNumberValue = action;
			this.resetNumberValue.setOptional(true);
			this.resetNumberValue.setDoc("Reset the \"numberValue\" to its \"defaultValue\".");
			super.addAction(this.resetNumberValue);
		} else if (action.getShortDefinitionName().equals(ActionType.decrementNumberValue.getShortName())) {
			this.decrementNumberValue = action;
			this.decrementNumberValue.setOptional(true);
			this.decrementNumberValue.setDoc("Decrement the \"numberValue\" by the value of \"step\", down to the value of \"minimum\".");
			super.addAction(this.decrementNumberValue);
		} else if (action.getShortDefinitionName().equals(ActionType.incrementNumberValue.getShortName())) {
			this.incrementNumberValue = action;
			this.incrementNumberValue.setOptional(true);
			this.incrementNumberValue.setDoc("Increment the \"numberValue\" by the value of \"step\", down to the value of \"minimum\".");
			super.addAction(this.incrementNumberValue);		
		} else {
			throw new IllegalArgumentException("Wrong action: " + action);
		}
	}

	// Setting/getting values from DataPoints
	
	public float getNumberValue() throws DataPointException, AccessException {
		return numberValue.getValue();
	}

	public void setNumberValue(float v) throws DataPointException, AccessException {
		numberValue.setValue(v);
	}

	public float getMinValue() throws DataPointException, AccessException {
		return minValue.getValue();
	}

	public void setMinValue(float v) throws DataPointException, AccessException {
		minValue.setValue(v);
	}
	
	public float getMaxValue() throws DataPointException, AccessException {
		return maxValue.getValue();
	}

	public void setMaxValue(float v) throws DataPointException, AccessException {
		maxValue.setValue(v);
	}
	
	public float getDefaultValue() throws DataPointException, AccessException {
		return defaultValue.getValue();
	}

	public void setDefaultValue(float v) throws DataPointException, AccessException {
		defaultValue.setValue(v);
	}
		
	public float getStep() throws DataPointException, AccessException {
		return step.getValue();
	}

	public void setStep(float v) throws DataPointException, AccessException {
		step.setValue(v);
	}
	
	
	// Implement actions 
		
	public void setDecrementNumberValue(DecrementNumberValue a) {
		addAction(a);
	}
	
	public void decrementNumberValue() throws ActionException, AccessException {
		if (decrementNumberValue == null)
			throw new ActionException("Not implemented");
		if (decrementNumberValue instanceof DecrementNumberValue)
			((DecrementNumberValue) decrementNumberValue).decrementNumberValue();
		else
			((Command)decrementNumberValue).invoke(null);
	}
	
	public void setIncrementNumberValue(IncrementNumberValue a) {
		addAction(a);
	}
	
	public void incrementNumberValue() throws ActionException, AccessException {
		if (incrementNumberValue == null)
			throw new ActionException("Not implemented");
		if (incrementNumberValue instanceof IncrementNumberValue)
			((IncrementNumberValue) incrementNumberValue).incrementNumberValue();
		else
			((Command)incrementNumberValue).invoke(null);
	}
	

	public void resetNumberValue(ResetNumberValue a) {
		addAction(a);
	}
	
	public void resetNumberValue() throws ActionException, AccessException {
		if (resetNumberValue == null)
			throw new ActionException("Not implemented");
		if (resetNumberValue instanceof ResetNumberValue)
			((ResetNumberValue) resetNumberValue).resetNumberValue();
		else
			((Command)resetNumberValue).invoke(null);
	}
}
