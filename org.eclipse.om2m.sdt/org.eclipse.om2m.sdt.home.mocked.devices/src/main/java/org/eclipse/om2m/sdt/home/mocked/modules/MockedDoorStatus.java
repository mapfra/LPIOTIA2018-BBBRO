/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.mocked.modules;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.EnumDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.modules.DoorStatus;
import org.eclipse.om2m.sdt.home.types.DoorState;

public class MockedDoorStatus extends DoorStatus {
	
	public MockedDoorStatus(String name, Domain domain) {
		super(name, domain,
			new DoorState(new EnumDataPoint<Integer>(null) {
				private int state = DoorState.Closed;
				@Override
				public Integer doGetValue() throws DataPointException {
					return state;
				}
			}
		));
	}

}
