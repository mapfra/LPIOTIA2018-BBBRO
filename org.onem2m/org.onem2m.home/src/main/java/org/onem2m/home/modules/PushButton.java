/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.modules;

import java.util.Map;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.DataPoint;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class PushButton extends Module {
	
	private BooleanDataPoint pushed;
	
	public PushButton(final String name, final Domain domain, 
			BooleanDataPoint pressed) {
		super(name, domain, ModuleType.pushButton.getDefinition());

		this.pushed = pressed;
		this.pushed.setWritable(false);
		this.pushed.setDoc("To indicate the press of the button.");
		addDataPoint(this.pushed);
	}

	public PushButton(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (BooleanDataPoint) dps.get("pushed"));
	}

	public boolean isPushed() throws DataPointException, AccessException {
		return pushed.getValue();
	}

}
