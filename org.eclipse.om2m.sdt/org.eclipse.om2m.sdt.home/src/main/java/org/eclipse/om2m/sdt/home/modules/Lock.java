/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import javax.xml.bind.PropertyException;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.Property;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;
import org.eclipse.om2m.sdt.home.types.PropertyType;
import org.eclipse.om2m.sdt.types.SimpleType;

public class Lock extends Module {
	
	private BooleanDataPoint doorLock;
	
	private Property openOnly;

	public Lock(final String name, final Domain domain, BooleanDataPoint doorLock) {
		super(name, domain, ModuleType.lock);
		
		if ((doorLock == null) ||
				! doorLock.getShortDefinitionType().equals(DatapointType.doorLock.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong doorLock datapoint: " + doorLock);
		}
		this.doorLock = doorLock;
		this.doorLock.setDoc("\"True\" indicates the door is locked, while \"False\" indicates the door is not locked");
		addDataPoint(this.doorLock);

		openOnly = new Property(PropertyType.openOnly);
		openOnly.setType(SimpleType.Boolean);
		openOnly.setOptional(true);
		addProperty(openOnly);
	}
	
	public Lock(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (BooleanDataPoint) dps.get(DatapointType.doorLock.getShortName()));
	}

	public void setDoorLock(boolean c) throws DataPointException, AccessException {
		doorLock.setValue(c);
	}
	
	public boolean getDoorLock() throws DataPointException, AccessException {
		return doorLock.getValue();
	}
	
	public void setOpenOnly(boolean v) {
		openOnly.setValue(Boolean.toString(v));
	}
	
	public boolean getOpenOnly() throws PropertyException {
		if (openOnly.getValue() == null)
			throw new PropertyException("Not implemented");
		return Boolean.parseBoolean(openOnly.getValue());
	}

}
