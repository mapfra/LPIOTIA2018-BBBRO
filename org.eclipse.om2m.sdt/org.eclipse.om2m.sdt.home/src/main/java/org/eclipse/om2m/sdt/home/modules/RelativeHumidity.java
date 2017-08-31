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
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class RelativeHumidity extends Module {
	
	private FloatDataPoint relativeHumidity;
	private IntegerDataPoint desiredHumidity;
	
	public RelativeHumidity(final String name, final Domain domain, 
			FloatDataPoint relativeHumidity) {
		super(name, domain, ModuleType.relativeHumidity);

		if ((relativeHumidity == null) ||
				! relativeHumidity.getShortDefinitionType().equals(DatapointType.relativeHumidity.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong relativeHumidity datapoint: " + relativeHumidity);
		}
		this.relativeHumidity = relativeHumidity;
		this.relativeHumidity.setWritable(false);
		this.relativeHumidity.setDoc("The measurement of the relative humidity value; the common unit is percentage.");
		addDataPoint(relativeHumidity);
	}

	public RelativeHumidity(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (FloatDataPoint) dps.get(DatapointType.relativeHumidity.getShortName()));
		IntegerDataPoint desiredHumidity = (IntegerDataPoint) dps.get(DatapointType.desiredHumidity.getShortName());
		if (desiredHumidity != null)
			setDesiredHumidity(desiredHumidity);
	}

	public float getRelativeHumidity() throws DataPointException, AccessException {
		return relativeHumidity.getValue();
	}

	public void setDesiredHumidity(IntegerDataPoint dp) {
		this.desiredHumidity = dp;
		this.desiredHumidity.setOptional(true);
		this.desiredHumidity.setDoc("Desired value for Humidity.");
		addDataPoint(desiredHumidity);
	}

	public int getDesiredHumidity() throws DataPointException, AccessException {
		if (desiredHumidity == null)
			throw new DataPointException("Not implemented");
		return desiredHumidity.getValue();
	}

	public void setDesiredHumidity(int b) throws DataPointException, AccessException {
		if (desiredHumidity == null)
			throw new DataPointException("Not implemented");
		desiredHumidity.setValue(b);
	}

}
