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
import org.eclipse.om2m.sdt.home.types.UvStatus;

public class UvSensor extends Module {
	
	private UvStatus uvStatus;
	private FloatDataPoint uvValue;
	
	public UvSensor(final String name, final Domain domain, FloatDataPoint uvValue) {
		super(name, domain, ModuleType.uvSensor);
		if ((uvValue == null) ||
				! uvValue.getShortName().equals(DatapointType.uvValue.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong uvValue datapoint: " + uvValue);
		}
		setDoc("This ModuleClass describes the capabilities of an ultraviolet sensor.");
		this.uvValue = uvValue;
		this.uvValue.setWritable(false);
		this.uvValue.setDoc("The measure of the UV intensity of radiation in mW/cm^2");
		addDataPoint(uvValue);
	}

	public UvSensor(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (FloatDataPoint) dps.get(DatapointType.uvValue.getShortName()));
		UvStatus uvStatus = (UvStatus) dps.get(DatapointType.uvStatus.getShortName());
		if (uvStatus != null)
			setUvStatus(uvStatus);
	}

	protected void setUvStatus(UvStatus dp) {
		uvStatus = dp;
		uvStatus.setOptional(true);
		uvStatus.setWritable(false);
		uvStatus.setDoc("Indicates the level of the UV radiation status (see clause 5.6.30).");
		addDataPoint(uvStatus);
	}
	
	public float getUvValue() throws DataPointException, AccessException {
		return uvValue.getValue();
	}
	
	public UvStatus.Values getSensitivity() throws DataPointException, AccessException {
		if (uvStatus == null)
			throw new DataPointException("Not implemented");
		return uvStatus.getValue();
	}

}
