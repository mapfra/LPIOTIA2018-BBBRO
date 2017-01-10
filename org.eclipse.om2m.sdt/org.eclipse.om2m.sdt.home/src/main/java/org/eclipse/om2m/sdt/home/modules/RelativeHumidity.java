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
import org.eclipse.om2m.sdt.home.types.ModuleType;
import org.eclipse.om2m.sdt.impl.AccessException;
import org.eclipse.om2m.sdt.impl.DataPointException;

public class RelativeHumidity extends Module {
	
	private FloatDataPoint relativeHumidity;
	private IntegerDataPoint desiredHumidity;
	
	public RelativeHumidity(final String name, final Domain domain, FloatDataPoint dp) {
		super(name, domain, ModuleType.relativeHumidity.getDefinition());

		this.relativeHumidity = dp;
		this.relativeHumidity.setWritable(false);
		this.relativeHumidity.setDoc("The measurement of the relative humidity value; the common unit is percentage.");
		addDataPoint(relativeHumidity);
	}

	public RelativeHumidity(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (FloatDataPoint) dps.get("relativeHumidity"));
		IntegerDataPoint desiredHumidity = (IntegerDataPoint) dps.get("desiredHumidity");
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
