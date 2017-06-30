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
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class TemperatureAlarm extends AbstractAlarmSensor {
	
	private IntegerDataPoint temperature;
	private IntegerDataPoint temperatureThreshhold;
	
	public TemperatureAlarm(final String name, final Domain domain, BooleanDataPoint alarm) {
		super(name, domain, alarm, ModuleType.temperatureAlarm,
				"The detection of abnormal temperature.");
	}

	public TemperatureAlarm(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (BooleanDataPoint) dps.get(DatapointType.alarm.getShortName()));
		IntegerDataPoint temperatureThreshhold = 
				(IntegerDataPoint) dps.get(DatapointType.temperatureThreshhold.getShortName());
		if (temperatureThreshhold != null)
			setTemperatureThreshhold(temperatureThreshhold);
	}

	public void setTemperature(IntegerDataPoint dp) {
		this.temperature = dp;
		temperature.setOptional(true);
		temperature.setWritable(false);
		temperature.setDoc("To report the value of the temperature; the common unit is ºC");
		addDataPoint(temperature);
	}
	
	public int getTemperature() throws DataPointException, AccessException {
		if (temperature == null)
			throw new DataPointException("Not implemented");
		return temperature.getValue();
	}

	public void setTemperatureThreshhold(IntegerDataPoint dp) {
		this.temperatureThreshhold = dp;
		temperatureThreshhold.setOptional(true);
		temperatureThreshhold.setDoc("The threshhold to trigger the alarm.");
		addDataPoint(temperatureThreshhold);
	}
	
	public int getTemperatureThreshhold() throws DataPointException, AccessException {
		if (temperatureThreshhold == null)
			throw new DataPointException("Not implemented");
		return temperatureThreshhold.getValue();
	}
	
	public void setTemperatureThreshhold(int v) throws DataPointException, AccessException {
		if (temperatureThreshhold == null)
			throw new DataPointException("Not implemented");
		temperatureThreshhold.setValue(v);
	}

}
