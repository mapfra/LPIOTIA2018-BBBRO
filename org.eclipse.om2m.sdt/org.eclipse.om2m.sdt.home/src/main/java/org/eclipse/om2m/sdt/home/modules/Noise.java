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

public class Noise extends Module {

	private IntegerDataPoint noise;
	
	public Noise(String name, Domain domain, IntegerDataPoint noise) {
		super(name, domain, ModuleType.noise);
		if ((noise == null) ||
				! noise.getShortDefinitionType().equals(DatapointType.noise.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong noise datapoint: " + noise);
		}
		this.noise = noise;
		this.noise.setWritable(false);
		addDataPoint(this.noise);
	}

	public Noise(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (IntegerDataPoint) dps.get(DatapointType.noise.getShortName()));
	}

	public int getNoise() throws DataPointException, AccessException {
		return noise.getValue();
	}
	
}
