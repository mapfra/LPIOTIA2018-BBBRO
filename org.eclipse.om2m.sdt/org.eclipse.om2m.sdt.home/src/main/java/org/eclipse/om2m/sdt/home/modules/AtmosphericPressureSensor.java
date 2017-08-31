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
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class AtmosphericPressureSensor extends Module {

	private FloatDataPoint atmosphericPressure;

	public AtmosphericPressureSensor(final String name, final Domain domain, 
			FloatDataPoint atmosphericPressure) {
		super(name, domain, ModuleType.atmosphericPressureSensor);
		
		if ((atmosphericPressure == null) ||
				! atmosphericPressure.getShortDefinitionType().equals(DatapointType.atmosphericPressure.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong atmosphericPressure datapoint: " + atmosphericPressure);
		}
		this.atmosphericPressure = atmosphericPressure;
		this.atmosphericPressure.setWritable(false);
		this.atmosphericPressure.getDataType().setUnitOfMeasure("Mbar");
		this.atmosphericPressure.setDoc("Current Atmospheric Pressure In Mbar");
		addDataPoint(this.atmosphericPressure);
	}
	
	public AtmosphericPressureSensor(final String name, final Domain domain, 
			Map<String, DataPoint> dps) {
		this(name, domain, (FloatDataPoint) dps.get(DatapointType.atmosphericPressure.getShortName()));
	}

	public float getAtmosphericPressure() throws DataPointException, AccessException {
		return atmosphericPressure.getValue();
	}

}
