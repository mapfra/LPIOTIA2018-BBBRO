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
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class Brightness extends Module {

	private IntegerDataPoint brightness;

	public Brightness(final String name, final Domain domain, IntegerDataPoint brightness) {
		super(name, domain, ModuleType.brightness);
		
		if ((brightness == null) ||
				! brightness.getShortDefinitionType().equals(DatapointType.brightness.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong brightness datapoint: " + brightness);
		}
		this.brightness = brightness;
		this.brightness.setDoc("Current sensed or set value for Brightness");
		addDataPoint(this.brightness);
	}

	public Brightness(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (IntegerDataPoint) dps.get(DatapointType.brightness.getShortName()));
	}

	public int getBrightness() throws DataPointException, AccessException {
		return brightness.getValue();
	}

	public void setBrightness(int v) throws DataPointException, AccessException {
		brightness.setValue(v);
	}
	
}
