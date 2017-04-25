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
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class Colour extends Module {
	
	private IntegerDataPoint red;
	private IntegerDataPoint green;
	private IntegerDataPoint blue;

	public Colour(final String name, final Domain domain, IntegerDataPoint red, 
			IntegerDataPoint green, IntegerDataPoint blue) {
		super(name, domain, ModuleType.colour.getDefinition(),
				ModuleType.colour.getLongDefinitionName(),
				ModuleType.colour.getShortDefinitionName());
		setExtends(domain.getName(), "Colour");
		
		this.red = red;
		this.red.setDoc("The R value of RGB; the range is [0,255]");
		this.red.setLongDefinitionType("red");
		this.red.setShortDefinitionType("red");
		addDataPoint(this.red);
		
		this.green = green;
		this.green.setDoc("The G value of RGB; the range is [0,255]");
		this.green.setLongDefinitionType("green");
		this.green.setShortDefinitionType("green");
		addDataPoint(this.green);
		
		this.blue = blue;
		this.blue.setDoc("The B value of RGB; the range is [0,255]");
		this.blue.setLongDefinitionType("blue");
		this.blue.setShortDefinitionType("blue");
		addDataPoint(this.blue);
	}

	public Colour(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, 
			(IntegerDataPoint) dps.get("red"),
			(IntegerDataPoint) dps.get("green"),
			(IntegerDataPoint) dps.get("blue"));
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
	
	public void setValues(Integer red, Integer green, Integer blue) throws DataPointException, AccessException {
		if (red != null)
			setRed(red);
		if (green != null)
			setGreen(green);
		if (blue != null)
			setBlue(blue);
	}
	
}
