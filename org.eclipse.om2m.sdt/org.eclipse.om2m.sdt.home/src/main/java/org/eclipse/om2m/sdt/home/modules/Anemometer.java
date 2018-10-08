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

public class Anemometer extends Module {
	
	private FloatDataPoint speed;
	
	public Anemometer(final String name, final Domain domain, FloatDataPoint speed) {
		super(name, domain, ModuleType.anemometer);

		if ((speed == null) ||
				! speed.getShortName().equals(DatapointType.speed.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong speed datapoint: " + speed);
		}
		this.speed = speed;
		this.speed.setWritable(false);
		this.speed.setDoc("The unit of measure of the speed is \"dB2\".");
		addDataPoint(this.speed);
	}
	
	public Anemometer(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (FloatDataPoint) dps.get(DatapointType.loudness.getShortName()));
	}
	
	public float getSpeed() throws DataPointException, AccessException {
		return speed.getValue();
	}

}
