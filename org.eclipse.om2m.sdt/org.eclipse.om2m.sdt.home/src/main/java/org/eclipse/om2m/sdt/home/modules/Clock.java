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
import org.eclipse.om2m.sdt.datapoints.DateDataPoint;
import org.eclipse.om2m.sdt.datapoints.TimeDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class Clock extends Module {
	
	private TimeDataPoint currentTime;
	private DateDataPoint currentDate;

	public Clock(final String name, final Domain domain, TimeDataPoint currentTime,
			DateDataPoint currentDate) {
		super(name, domain, ModuleType.clock);
		
		if ((currentDate == null) ||
				! currentDate.getShortDefinitionType().equals(DatapointType.currentDate.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong currentDate datapoint: " + currentDate);
		}
		this.currentDate = currentDate;
		currentDate.setDoc("Information of the current date");
		addDataPoint(currentDate);
		
		if ((currentTime == null) ||
				! currentTime.getShortDefinitionType().equals(DatapointType.currentTime.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong currentTime datapoint: " + currentTime);
		}
		this.currentTime = currentTime;
		currentTime.setDoc("Information of the current time");
		addDataPoint(currentTime);
	}

	public Clock(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, 
				(TimeDataPoint) dps.get(DatapointType.currentTime.getShortName()), 
				(DateDataPoint) dps.get(DatapointType.currentDate.getShortName()));
	}

	public Date getCurrentTime() throws DataPointException, AccessException {
		return currentTime.getValue();
	}

	public String getStringCurrentTime() throws DataPointException, AccessException {
		return currentTime.getStringValue();
	}

	public void setCurrentTime(Date value) throws DataPointException, AccessException {
		currentTime.setValue(value);
	}

	public Date getCurrentDate() throws DataPointException, AccessException {
		return currentDate.getValue();
	}

	public String getStringCurrentDate() throws DataPointException, AccessException {
		return currentDate.getStringValue();
	}

	public void setCurrentDate(Date value) throws DataPointException, AccessException {
		currentDate.setValue(value);
	}

}
