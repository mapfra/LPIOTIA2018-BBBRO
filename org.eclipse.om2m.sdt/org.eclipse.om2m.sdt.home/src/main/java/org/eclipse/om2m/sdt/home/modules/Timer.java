/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import java.util.Date;
import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.datapoints.TimeDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class Timer extends Module {
	
	private IntegerDataPoint referenceTimer;
	private IntegerDataPoint targetTimeToStart;
	private IntegerDataPoint targetTimeToStop;
	private IntegerDataPoint estimatedTimeToEnd;
	private IntegerDataPoint runningTime;
	private IntegerDataPoint targetDuration;
	private TimeDataPoint absoluteStartTime;
	private TimeDataPoint absoluteStopTime;
	
	public Timer(final String name, final Domain domain) {
		super(name, domain, ModuleType.timer);
	}

	public Timer(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain);
		IntegerDataPoint referenceTimer = 
				(IntegerDataPoint) dps.get(DatapointType.referenceTimer.getShortName());
		if (referenceTimer != null)
			setReferenceTimer(referenceTimer);
		
		IntegerDataPoint targetTimeToStart = 
				(IntegerDataPoint) dps.get(DatapointType.targetTimeToStart.getShortName());
		if (targetTimeToStart != null)
			setTargetTimeToStart(targetTimeToStart);
		
		IntegerDataPoint targetTimeToStop = 
				(IntegerDataPoint) dps.get(DatapointType.targetTimeToStop.getShortName());
		if (targetTimeToStop != null)
			setTargetTimeToStop(targetTimeToStop);
		
		IntegerDataPoint estimatedTimeToEnd = 
				(IntegerDataPoint) dps.get(DatapointType.estimatedTimeToEnd.getShortName());
		if (estimatedTimeToEnd != null)
			setEstimatedTimeToEnd(estimatedTimeToEnd);
		
		IntegerDataPoint runningTime = 
				(IntegerDataPoint) dps.get(DatapointType.runningTime.getShortName());
		if (runningTime != null)
			setRunningTime(runningTime);
		
		IntegerDataPoint targetDuration = 
				(IntegerDataPoint) dps.get(DatapointType.targetDuration.getShortName());
		if (targetDuration != null)
			setTargetDuration(targetDuration);
	}

	public void setReferenceTimer(IntegerDataPoint dp) {
		referenceTimer = dp;
		referenceTimer.setOptional(true);
		referenceTimer.setWritable(false);
		referenceTimer.setDoc("...");
		addDataPoint(referenceTimer);
	}

	public int getReferenceTimer() throws DataPointException, AccessException {
		if (referenceTimer == null)
			throw new DataPointException("Not implemented");
		return referenceTimer.getValue();
	}

	public void setTargetTimeToStart(IntegerDataPoint dp) {
		this.targetTimeToStart = dp;
		this.targetTimeToStart.setOptional(true);
		this.targetTimeToStart.setDoc("...");
		addDataPoint(targetTimeToStart);
	}

	public int getTargetTimeToStart() throws DataPointException, AccessException {
		if (targetTimeToStart == null)
			throw new DataPointException("Not implemented");
		return targetTimeToStart.getValue();
	}

	public void setTargetTimeToStart(int b) throws DataPointException, AccessException {
		if (targetTimeToStart == null)
			throw new DataPointException("Not implemented");
		targetTimeToStart.setValue(b);
	}

	public void setTargetTimeToStop(IntegerDataPoint dp) {
		this.targetTimeToStop = dp;
		this.targetTimeToStop.setOptional(true);
		this.targetTimeToStop.setDoc("...");
		addDataPoint(targetTimeToStop);
	}

	public int getTargetTimeToStop() throws DataPointException, AccessException {
		if (targetTimeToStop == null)
			throw new DataPointException("Not implemented");
		return targetTimeToStop.getValue();
	}

	public void setTargetTimeToStop(int b) throws DataPointException, AccessException {
		if (targetTimeToStop == null)
			throw new DataPointException("Not implemented");
		targetTimeToStop.setValue(b);
	}

	public void setEstimatedTimeToEnd(IntegerDataPoint dp) {
		this.estimatedTimeToEnd = dp;
		this.estimatedTimeToEnd.setOptional(true);
		this.estimatedTimeToEnd.setWritable(false);
		this.estimatedTimeToEnd.setDoc("...");
		addDataPoint(estimatedTimeToEnd);
	}

	public int getEstimatedTimeToEnd() throws DataPointException, AccessException {
		if (estimatedTimeToEnd == null)
			throw new DataPointException("Not implemented");
		return estimatedTimeToEnd.getValue();
	}

	public void setRunningTime(IntegerDataPoint dp) {
		this.runningTime = dp;
		this.runningTime.setOptional(true);
		this.runningTime.setWritable(false);
		this.runningTime.setDoc("...");
		addDataPoint(runningTime);
	}

	public int getRunningTime() throws DataPointException, AccessException {
		if (runningTime == null)
			throw new DataPointException("Not implemented");
		return runningTime.getValue();
	}

	public void setTargetDuration(IntegerDataPoint dp) {
		this.targetDuration = dp;
		this.targetDuration.setOptional(true);
		this.targetDuration.setWritable(false);
		this.targetDuration.setDoc("...");
		addDataPoint(targetDuration);
	}

	public int getTargetDuration() throws DataPointException, AccessException {
		if (targetDuration == null)
			throw new DataPointException("Not implemented");
		return targetDuration.getValue();
	}

	public void setAbsoluteStartTime(TimeDataPoint dp) {
		this.absoluteStartTime = dp;
		this.absoluteStartTime.setOptional(true);
		this.absoluteStartTime.setDoc("...");
		addDataPoint(absoluteStartTime);
	}

	public Date getAbsoluteStartTime() throws DataPointException, AccessException {
		if (absoluteStartTime == null)
			throw new DataPointException("Not implemented");
		return absoluteStartTime.getValue();
	}

	public void setAbsoluteStartTime(long b) throws DataPointException, AccessException {
		if (absoluteStartTime == null)
			throw new DataPointException("Not implemented");
		absoluteStartTime.setValue(b);
	}

	public void setAbsoluteStartTime(Date d) throws DataPointException, AccessException {
		if (absoluteStartTime == null)
			throw new DataPointException("Not implemented");
		absoluteStartTime.setValue(d);
	}

	public void setAbsoluteStartTime(String d) throws DataPointException, AccessException {
		if (absoluteStartTime == null)
			throw new DataPointException("Not implemented");
		absoluteStartTime.setValue(d);
	}

	public void setAbsoluteStopTime(TimeDataPoint dp) {
		this.absoluteStopTime = dp;
		this.absoluteStopTime.setOptional(true);
		this.absoluteStopTime.setDoc("...");
		addDataPoint(absoluteStopTime);
	}

	public Date getAbsoluteStopTime() throws DataPointException, AccessException {
		if (absoluteStopTime == null)
			throw new DataPointException("Not implemented");
		return absoluteStopTime.getValue();
	}

	public void setAbsoluteStopTime(long b) throws DataPointException, AccessException {
		if (absoluteStopTime == null)
			throw new DataPointException("Not implemented");
		absoluteStopTime.setValue(b);
	}

	public void setAbsoluteStopTime(Date d) throws DataPointException, AccessException {
		if (absoluteStopTime == null)
			throw new DataPointException("Not implemented");
		absoluteStopTime.setValue(d);
	}

	public void setAbsoluteStopTime(String d) throws DataPointException, AccessException {
		if (absoluteStopTime == null)
			throw new DataPointException("Not implemented");
		absoluteStopTime.setValue(d);
	}

}
