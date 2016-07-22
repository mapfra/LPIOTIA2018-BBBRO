/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.modules;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.datapoints.StringDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class Dimming extends Module {
	
	private IntegerDataPoint dimmingSetting;
	
	private StringDataPoint range;
	
	private IntegerDataPoint step;
	
	public Dimming(final String name, final Domain domain, 
			IntegerDataPoint value) {
		super(name, domain, ModuleType.dimming.getDefinition());

		this.dimmingSetting = value;
		this.dimmingSetting.setDoc("Current dimming value.");
		addDataPoint(this.dimmingSetting);
	}

	public int getDimmingSetting() throws DataPointException, AccessException {
		return dimmingSetting.getValue();
	}

	public void setDimmingSetting(int b) throws DataPointException, AccessException {
		dimmingSetting.setValue(b);
	}

	public void setRange(StringDataPoint dp) {
		this.range = dp;
		this.range.setOptional(true);
		this.range.setWritable(false);
		this.range.setDoc("Min And Max Values For The Dimming Setting.");
		addDataPoint(range);
	}

	public String getRange() throws DataPointException, AccessException {
		if (range == null)
			throw new DataPointException("Not implemented");
		return range.getValue();
	}

	public void setStep(IntegerDataPoint dp) {
		this.step = dp;
		this.step.setOptional(true);
		this.step.setWritable(false);
		this.step.setDoc("Step Increment For Dimming Values.");
		addDataPoint(step);
	}

	public int getStep() throws DataPointException, AccessException {
		if (step == null)
			throw new DataPointException("Not implemented");
		return step.getValue();
	}

}
