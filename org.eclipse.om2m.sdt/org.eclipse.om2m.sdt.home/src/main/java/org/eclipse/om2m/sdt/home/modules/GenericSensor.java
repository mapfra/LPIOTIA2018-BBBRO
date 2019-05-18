/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Identifiers;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;

public class GenericSensor extends Module {
	
	private BooleanDataPoint value;
	
	public GenericSensor(final String name, final Domain domain, 
			BooleanDataPoint value, Identifiers identifiers) {
		super(name, domain, identifiers); 

		if (value == null) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong value datapoint: " + value);
		}
		this.value = value;
		this.value.setWritable(false);
		this.value.setDoc("True = Sensed, False = Not Sensed");
		addDataPoint(this.value);
	}

	public boolean getValue() throws DataPointException, AccessException {
		return value.getValue();
	}

}
