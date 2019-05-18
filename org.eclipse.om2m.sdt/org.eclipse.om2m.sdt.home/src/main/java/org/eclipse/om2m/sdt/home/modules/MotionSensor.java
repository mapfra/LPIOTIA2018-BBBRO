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

public class MotionSensor extends AbstractAlarmSensor {
	
	private IntegerDataPoint silentTime;
	private IntegerDataPoint sensitivity;
	
	public MotionSensor(final String name, final Domain domain, BooleanDataPoint alarm) {
		super(name, domain, alarm, ModuleType.motionSensor,
				"The detection of the motion occurrence.");
	}

	public MotionSensor(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (BooleanDataPoint) dps.get(DatapointType.alarm.getShortName()));
		IntegerDataPoint silentTime = 
				(IntegerDataPoint) dps.get(DatapointType.silentTime.getShortName());
		if (silentTime != null)
			setSilentTime(silentTime);
		IntegerDataPoint sensitivity = 
				(IntegerDataPoint) dps.get(DatapointType.sensitivity.getShortName());
		if (sensitivity != null)
			setSensitivity(sensitivity);
	}

	public void setSilentTime(IntegerDataPoint dp) {
		silentTime = dp;
		silentTime.setOptional(true);
		silentTime.setDoc("The time that the motionSensor restrains from sending an alarm in case continous motions are detected after one alarm is produced. This DataPoint can be used to avoid repeated alarm reports.");
		addDataPoint(silentTime);
	}
	
	public int getSilentTime() throws DataPointException, AccessException {
		if (silentTime == null)
			throw new DataPointException("Not implemented");
		return silentTime.getValue();
	}

	public void setSilentTime(int v) throws DataPointException, AccessException {
		if (silentTime == null)
			throw new DataPointException("Not implemented");
		silentTime.setValue(v);
	}

	public void setSensitivity(IntegerDataPoint dp) {
		sensitivity = dp;
		sensitivity.setOptional(true);
		sensitivity.setDoc("The level of the detection accuracy of the motion sensor. This DataPoint can be used to control the number of the report.");
		addDataPoint(sensitivity);
	}
	
	public int getSensitivity() throws DataPointException, AccessException {
		if (sensitivity == null)
			throw new DataPointException("Not implemented");
		return sensitivity.getValue();
	}

	public void setSensitivity(int v) throws DataPointException, AccessException {
		if (sensitivity == null)
			throw new DataPointException("Not implemented");
		sensitivity.setValue(v);
	}

}
