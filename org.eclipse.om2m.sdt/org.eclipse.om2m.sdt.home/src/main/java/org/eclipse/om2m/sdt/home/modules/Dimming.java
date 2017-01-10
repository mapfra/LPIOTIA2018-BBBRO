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
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.home.types.ModuleType;
import org.eclipse.om2m.sdt.impl.AccessException;
import org.eclipse.om2m.sdt.impl.DataPointException;

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

	public Dimming(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (IntegerDataPoint) dps.get("dimmingSetting"));
		StringDataPoint range = (StringDataPoint) dps.get("range");
		if (range != null)
			setRange(range);
		IntegerDataPoint step = (IntegerDataPoint) dps.get("step");
		if (step != null)
			setStep(step);
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
