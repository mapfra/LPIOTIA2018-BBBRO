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
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.ActionException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.actions.Toggle;
import org.eclipse.om2m.sdt.home.mocked.devices.Activator;
import org.eclipse.om2m.sdt.home.modules.BinarySwitch;
import org.eclipse.om2m.sdt.home.types.DatapointType;

public class MockedBinarySwitch extends BinarySwitch {

	public MockedBinarySwitch(String name, Domain domain) {
		super(name, domain,
			new BooleanDataPoint(DatapointType.powerState) {
				private Boolean powerState = Boolean.TRUE;
				@Override
				public void doSetValue(Boolean value) throws DataPointException {
					powerState = value;
				}
				@Override
				public Boolean doGetValue() throws DataPointException {
					return powerState;
				}
			}
		);
		
		setToggle(new Toggle("toggle") {
			@Override
			protected void doToggle() throws ActionException {
				Activator.logger.info("Toggle binary switch");
				try {
					setPowerState(! getPowerState());
				} catch (DataPointException e) {
					throw new ActionException(e);
				} catch (AccessException e) {
					throw new ActionException(e);
				}
			}
		});
	}

}
