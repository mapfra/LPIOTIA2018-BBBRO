/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class AbstractAlarmSensor extends Module {
	
	private BooleanDataPoint alarm;
	
	public AbstractAlarmSensor(final String name, final Domain domain, BooleanDataPoint alarm) {
		this(name, domain, alarm, ModuleType.abstractAlarmSensor);
	}
	
	public AbstractAlarmSensor(final String name, final Domain domain, 
			BooleanDataPoint alarm, ModuleType type) {
		this(name, domain, alarm, type, "True = Sensed, False = Not Sensed");
	}

	public AbstractAlarmSensor(final String name, final Domain domain, 
			BooleanDataPoint alarm, ModuleType type, String doc) {
		super(name, domain, type);
		if ((alarm == null) ||
				! alarm.getShortDefinitionType().equals(DatapointType.alarm.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong alarm datapoint: " + alarm);
		}
		this.alarm = alarm;
		this.alarm.setWritable(false);
		this.alarm.setDoc(doc);
		addDataPoint(this.alarm);
	}

	public boolean getAlarm() throws DataPointException, AccessException {
		return alarm.getValue();
	}

}
