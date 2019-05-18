/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class KeepWarm extends BinarySwitch {
	
	private IntegerDataPoint time;
	
	public KeepWarm(String name, Domain domain, BooleanDataPoint keepWarmSwitch) {
		super(name, domain, keepWarmSwitch, ModuleType.keepWarm);
	}

	public int getTime() throws DataPointException, AccessException {
		return time.getValue();
	}

	public void setTime(IntegerDataPoint time) {
		this.time = time;
		addDataPoint(this.time);
	}
	
}
