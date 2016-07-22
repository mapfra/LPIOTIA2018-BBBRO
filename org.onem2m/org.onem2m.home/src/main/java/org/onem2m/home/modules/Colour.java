/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.modules;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class Colour extends Module {
	
	private IntegerDataPoint red;
	private IntegerDataPoint green;
	private IntegerDataPoint blue;

	public Colour(final String name, final Domain domain, IntegerDataPoint red, 
			IntegerDataPoint green, IntegerDataPoint blue) {
		super(name, domain, ModuleType.colour.getDefinition());
		setExtends(domain.getName(), "Colour");
		
		this.red = red;
		this.red.setDoc("The R value of RGB; the range is [0,255]");
		addDataPoint(this.red);
		this.green = green;
		this.green.setDoc("The G value of RGB; the range is [0,255]");
		addDataPoint(this.green);
		this.blue = blue;
		this.blue.setDoc("The B value of RGB; the range is [0,255]");
		addDataPoint(this.blue);
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
