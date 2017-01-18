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

public class ColourSaturation extends Module {
	
	private IntegerDataPoint colourSaturation;

	public ColourSaturation(final String name, final Domain domain, IntegerDataPoint colourSaturation) {
		super(name, domain, ModuleType.colourSaturation.getDefinition());
		setExtends(domain.getName(), "ColourSaturation");
		this.colourSaturation = colourSaturation;
		addDataPoint(this.colourSaturation);
	}

	public ColourSaturation(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (IntegerDataPoint) dps.get("colourSaturation"));
	}

	public int getColourSaturation() throws DataPointException, AccessException {
		return colourSaturation.getValue();
	}
	
	public void setColourSaturation(int value) throws DataPointException, AccessException {
		colourSaturation.setValue(value);
	}
	
}
