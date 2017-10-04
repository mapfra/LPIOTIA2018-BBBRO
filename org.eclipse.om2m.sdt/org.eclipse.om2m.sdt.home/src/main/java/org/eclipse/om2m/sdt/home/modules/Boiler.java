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
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class Boiler extends Module {
	
	private BooleanDataPoint status;

	public Boiler(final String name, final Domain domain, BooleanDataPoint status) {
		super(name, domain, ModuleType.boiler);
		
		if ((status == null) ||
				! status.getShortDefinitionType().equals(DatapointType.status.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong status datapoint: " + status);
		}
		this.status = status;
		this.status.setDoc("The status of boiling");
		addDataPoint(status);
	}

	public Boiler(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (BooleanDataPoint) dps.get(DatapointType.status.getShortName()));
	}

	public boolean getStatus() throws DataPointException, AccessException {
		return status.getValue();
	}

	public void setStatus(boolean v) throws DataPointException, AccessException {
		status.setValue(v);
	}

}
