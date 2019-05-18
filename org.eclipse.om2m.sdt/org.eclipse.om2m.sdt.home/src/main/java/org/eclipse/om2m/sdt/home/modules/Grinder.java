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
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class Grinder extends Module {
	
	private BooleanDataPoint useGrinder;
	
	private IntegerDataPoint coarseness;
	
	public Grinder(final String name, final Domain domain, 
			BooleanDataPoint useGrinder, IntegerDataPoint coarseness){
		super(name, domain, ModuleType.grinder);
		setExtends(domain.getName(), "Grinder");
		
		if ((useGrinder == null) ||
				! useGrinder.getShortName().equals(DatapointType.useGrinder.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong useGrinder datapoint: " + useGrinder);
		}
		this.useGrinder = useGrinder;
		this.useGrinder.setDoc("The current status of the grinder enablement");
		addDataPoint(this.useGrinder);
		
		if ((coarseness == null) ||
				! coarseness.getShortName().equals(DatapointType.coarseness.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong coarseness datapoint: " + coarseness);
		}
		this.coarseness = coarseness;
		this.coarseness.setDoc("The current coarseness of the object after grinding.");
		addDataPoint(this.coarseness);
	}
	
    public Grinder(final String name, final Domain domain, Map<String, DataPoint> dps) {
        this(name, domain, 
        		(BooleanDataPoint) dps.get(DatapointType.useGrinder.getShortName()), 
        		(IntegerDataPoint) dps.get(DatapointType.coarseness.getShortName()));
    }
	
	public boolean getUseGrinder() throws DataPointException, AccessException {
		return useGrinder.getValue();
	}

	public void setUseGrinder(boolean v) throws DataPointException, AccessException {
		this.useGrinder.setValue(v);
	}

	public int getGrindCoarsenes() throws DataPointException, AccessException  {
		return coarseness.getValue();
	}

	public void setGrindCoarsenes(int v) throws DataPointException, AccessException  {
		this.coarseness.setValue(v);
	}
	
}