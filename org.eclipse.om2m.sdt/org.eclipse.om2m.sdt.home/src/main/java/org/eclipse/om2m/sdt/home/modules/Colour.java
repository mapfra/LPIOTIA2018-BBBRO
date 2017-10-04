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

public class Colour extends Module {
	
	private IntegerDataPoint red;
	private IntegerDataPoint green;
	private IntegerDataPoint blue;

	public Colour(final String name, final Domain domain, IntegerDataPoint red, 
			IntegerDataPoint green, IntegerDataPoint blue) {
		super(name, domain, ModuleType.colour);
		setExtends(domain.getName(), "Colour");
		
		if ((red == null) ||
				! red.getShortDefinitionType().equals(DatapointType.red.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong red datapoint: " + red);
		}
		this.red = red;
		this.red.setDoc("The R value of RGB; the range is [0,255]");
		addDataPoint(this.red);
		
		if ((green == null) ||
				! green.getShortDefinitionType().equals(DatapointType.green.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong green datapoint: " + green);
		}
		this.green = green;
		this.green.setDoc("The G value of RGB; the range is [0,255]");
		addDataPoint(this.green);
		
		if ((blue == null) ||
				! blue.getShortDefinitionType().equals(DatapointType.blue.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong blue datapoint: " + blue);
		}
		this.blue = blue;
		this.blue.setDoc("The B value of RGB; the range is [0,255]");
		addDataPoint(this.blue);
	}

	public Colour(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, 
			(IntegerDataPoint) dps.get(DatapointType.red.getShortName()),
			(IntegerDataPoint) dps.get(DatapointType.green.getShortName()),
			(IntegerDataPoint) dps.get(DatapointType.blue.getShortName()));
	}

	public int getRed() throws DataPointException, AccessException {
		return red.getValue();
	}
	
	public void setRed(int value) throws DataPointException, AccessException {
		red.setValue(value);
	}
	
	public int getGreen() throws DataPointException, AccessException {
		return green.getValue();
	}
	
	public void setGreen(int value) throws DataPointException, AccessException {
		green.setValue(value);
	}
	
	public int getBlue() throws DataPointException, AccessException {
		return blue.getValue();
	}
	
	public void setBlue(int value) throws DataPointException, AccessException {
		blue.setValue(value);
	}
	
}
