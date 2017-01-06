/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.modules;

import java.util.Map;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.DataPoint;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.FloatDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class AtmosphericPressureSensor extends Module {

	private FloatDataPoint atmosphericPressure;

	public AtmosphericPressureSensor(final String name, final Domain domain, 
			FloatDataPoint atmosphericPressure) {
		super(name, domain, ModuleType.atmosphericPressureSensor.getDefinition());
		
		this.atmosphericPressure = atmosphericPressure;
		this.atmosphericPressure.setWritable(false);
		this.atmosphericPressure.getDataType().setUnitOfMeasure("Mbar");
		this.atmosphericPressure.setDoc("Current Atmospheric Pressure In Mbar");
		addDataPoint(this.atmosphericPressure);
	}
	
	public AtmosphericPressureSensor(final String name, final Domain domain, 
			Map<String, DataPoint> dps) {
		this(name, domain, (FloatDataPoint) dps.get("atmosphericPressure"));
	}

	public float getAtmosphericPressure() throws DataPointException, AccessException {
		return atmosphericPressure.getValue();
	}

}
