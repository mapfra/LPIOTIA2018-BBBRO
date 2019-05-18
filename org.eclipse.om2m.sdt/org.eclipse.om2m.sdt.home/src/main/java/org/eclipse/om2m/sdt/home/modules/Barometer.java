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
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class Barometer extends Module {

	private FloatDataPoint atmosphericPressure;
	private StringDataPoint unit;
	private IntegerDataPoint minPressureThreshhold;
	private IntegerDataPoint maxPressureThreshhold;

	public Barometer(final String name, final Domain domain, 
			FloatDataPoint atmosphericPressure) {
		super(name, domain, ModuleType.barometer);
		
		if ((atmosphericPressure == null) ||
				! atmosphericPressure.getShortName().equals(DatapointType.atmosphericPressure.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong atmosphericPressure datapoint: " + atmosphericPressure);
		}
		this.atmosphericPressure = atmosphericPressure;
		this.atmosphericPressure.setWritable(false);
		this.atmosphericPressure.getDataType().setUnitOfMeasure("Mbar");
		this.atmosphericPressure.setDoc("Current Atmospheric Pressure In Mbar");
		addDataPoint(this.atmosphericPressure);
	}
	
	public Barometer(final String name, final Domain domain, 
			Map<String, DataPoint> dps) {
		this(name, domain, (FloatDataPoint) dps.get(DatapointType.atmosphericPressure.getShortName()));
		IntegerDataPoint idp = (IntegerDataPoint) dps.get(DatapointType.minPressureThreshhold.getShortName());
		if (idp != null)
			setMinPressureThreshhold(idp);
		idp = (IntegerDataPoint) dps.get(DatapointType.maxPressureThreshhold.getShortName());
		if (idp != null)
			setMaxPressureThreshhold(idp);
		StringDataPoint sdp = (StringDataPoint) dps.get(DatapointType.unit.getShortName());
		if (sdp != null)
			setUnit(sdp);
	}

	public float getAtmosphericPressure() throws DataPointException, AccessException {
		return atmosphericPressure.getValue();
	}

	private void setMinPressureThreshhold(IntegerDataPoint dp) {
		this.minPressureThreshhold = dp;
		this.minPressureThreshhold.setOptional(true);
		this.minPressureThreshhold.setWritable(true);
		this.minPressureThreshhold.setDoc("The min threshhold to trigger the alarm.");
		addDataPoint(minPressureThreshhold);
	}

	public int getMinPressureThreshhold() throws DataPointException, AccessException {
		if (minPressureThreshhold == null)
			throw new DataPointException("Not implemented");
		return minPressureThreshhold.getValue();
	}

	public void setMinPressureThreshhold(int v) throws DataPointException, AccessException {
		if (minPressureThreshhold == null)
			throw new DataPointException("Not implemented");
		minPressureThreshhold.setValue(v);
	}

	private void setMaxPressureThreshhold(IntegerDataPoint dp) {
		this.maxPressureThreshhold = dp;
		this.maxPressureThreshhold.setOptional(true);
		this.maxPressureThreshhold.setWritable(true);
		this.maxPressureThreshhold.setDoc("The max threshhold to trigger the alarm.");
		addDataPoint(maxPressureThreshhold);
	}

	public int getMaxPressureThreshhold() throws DataPointException, AccessException {
		if (maxPressureThreshhold == null)
			throw new DataPointException("Not implemented");
		return maxPressureThreshhold.getValue();
	}

	public void setMaxPressureThreshhold(int v) throws DataPointException, AccessException {
		if (maxPressureThreshhold == null)
			throw new DataPointException("Not implemented");
		maxPressureThreshhold.setValue(v);
	}

	private void setUnit(StringDataPoint dp) {
		this.unit = dp;
		this.unit.setOptional(true);
		this.unit.setWritable(false);
		this.unit.setDoc("The unit used for atmospheric pressure. The default unit is hectopascal (hPa).");
		addDataPoint(unit);
	}

	public String getUnit() throws DataPointException, AccessException {
		if (unit == null)
			throw new DataPointException("Not implemented");
		return unit.getValue();
	}

}
