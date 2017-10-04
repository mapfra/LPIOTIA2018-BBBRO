/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import java.util.List;
import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class PersonSensor extends Module {

	private final ArrayDataPoint<String> detectedPersons;

	public PersonSensor(final String name, final Domain domain,
			final ArrayDataPoint<String> detectedPersons) {
		super(name, domain, ModuleType.personSensor);
		if ((detectedPersons == null) ||
				! detectedPersons.getShortDefinitionType().equals(DatapointType.detectedPersons.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong detectedPersons datapoint: " + detectedPersons);
		}
		this.detectedPersons = detectedPersons;
		this.detectedPersons.setWritable(false);
		addDataPoint(this.detectedPersons);
	}

	@SuppressWarnings("unchecked")
	public PersonSensor(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, 
				(ArrayDataPoint<String>) dps.get(DatapointType.detectedPersons.getShortName()));
	}

	public List<String> getDetectedPersons() throws DataPointException, AccessException  {
		return detectedPersons.getValue();
	}

}
