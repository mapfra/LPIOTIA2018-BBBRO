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
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class PushButton extends Module {
	
	private BooleanDataPoint pushed;
	
	public PushButton(final String name, final Domain domain, 
			BooleanDataPoint pressed) {
		super(name, domain, ModuleType.pushButton.getDefinition(),
				ModuleType.pushButton.getLongDefinitionName(),
				ModuleType.pushButton.getShortDefinitionName());

		this.pushed = pressed;
		this.pushed.setWritable(false);
		this.pushed.setDoc("To indicate the press of the button.");
		this.pushed.setLongDefinitionType("pushed");
		this.pushed.setShortDefinitionType("pushd");
		addDataPoint(this.pushed);
	}

	public PushButton(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (BooleanDataPoint) dps.get("pushd"));
	}

	public boolean isPushed() throws DataPointException, AccessException {
		return pushed.getValue();
	}

}
