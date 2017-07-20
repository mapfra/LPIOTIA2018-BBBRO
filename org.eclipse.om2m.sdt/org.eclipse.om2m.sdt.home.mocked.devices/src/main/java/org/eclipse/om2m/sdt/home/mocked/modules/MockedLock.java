/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.mocked.modules;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.modules.Lock;
import org.eclipse.om2m.sdt.home.types.DatapointType;

public class MockedLock extends Lock {
	
	public MockedLock(String name, Domain domain, boolean openOnly) {
		super(name, domain,
			new BooleanDataPoint(DatapointType.doorLock) {
				private boolean state = true;
				@Override
				public Boolean doGetValue() throws DataPointException {
					return state;
				}
				@Override
				public void doSetValue(Boolean v) throws DataPointException {
					state = v;
				}
			}
		);
		setOpenOnly(openOnly);
	}

}
