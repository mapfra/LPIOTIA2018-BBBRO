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

public class SmokeSensor extends AbstractAlarmSensor {
	
	private IntegerDataPoint detectedTime;
	
	public SmokeSensor(final String name, final Domain domain, BooleanDataPoint alarm) {
		super(name, domain, alarm, ModuleType.smokeSensor, "The detection of smoke.");
	}
	
	public SmokeSensor(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (BooleanDataPoint) dps.get(DatapointType.alarm.getShortName()));
		IntegerDataPoint detectedTime = (IntegerDataPoint) dps.get(DatapointType.detectedTime.getShortName());
		if (detectedTime != null)
			setDetectedTime(detectedTime);
	}

	public void setDetectedTime(IntegerDataPoint dp) {
		detectedTime = dp;
		detectedTime.setOptional(true);
		detectedTime.setDoc("The time the smoke is detected.");
		addDataPoint(detectedTime);
	}
	
	public int getDetectedTime() throws DataPointException, AccessException {
		if (detectedTime == null)
			throw new DataPointException("Not implemented");
		return detectedTime.getValue();
	}

	public void setDetectedTime(int v) throws DataPointException, AccessException {
		if (detectedTime == null)
			throw new DataPointException("Not implemented");
		detectedTime.setValue(v);
	}

}
