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

public class HotWaterSupply extends Module {
	
	private BooleanDataPoint status;
	private BooleanDataPoint bath;

	public HotWaterSupply(final String name, final Domain domain, BooleanDataPoint status) {
		super(name, domain, ModuleType.hotWaterSupply);
		
		if ((status == null) ||
				! status.getShortDefinitionType().equals(DatapointType.status.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong status datapoint: " + status);
		}
		this.status = status;
		this.status.setWritable(false);
		this.status.setDoc("The status of watering operation");
		addDataPoint(this.status);
	}

	public HotWaterSupply(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (BooleanDataPoint) dps.get(DatapointType.status.getShortName()));
		BooleanDataPoint bath = (BooleanDataPoint) dps.get(DatapointType.bath.getShortName());
		if (bath != null)
			setBath(bath);
	}

	public boolean getStatus() throws DataPointException, AccessException {
		return status.getValue();
	}

	public void setBath(BooleanDataPoint dp) {
		bath = dp;
		bath.setDoc("The status of filling bath tub");
		bath.setOptional(true);
		addDataPoint(bath);
	}

	public boolean getBath() throws DataPointException, AccessException {
		if (bath == null)
			throw new DataPointException("Not implemented");
		return bath.getValue();
	}

	public void setBath(boolean v) throws DataPointException, AccessException {
		if (bath == null)
			throw new DataPointException("Not implemented");
		bath.setValue(v);
	}

}
