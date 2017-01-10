/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.mocked.devices;

import java.util.List;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.home.devices.WaterValve;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedFaultDetection;
import org.eclipse.om2m.sdt.home.modules.Level;
import org.eclipse.om2m.sdt.home.types.LevelType;
import org.eclipse.om2m.sdt.impl.DataPointException;
import org.osgi.framework.ServiceRegistration;

public class MockedWaterValve extends WaterValve implements MockedDevice {

	private List<ServiceRegistration> serviceRegistrations;

	public MockedWaterValve(String id, String serial, Domain domain) {
		super(id, serial, domain);

		// Datapoints
		addModule(new Level("waterLevel_" + id, domain, 
			new LevelType("quantity") {
				private Integer openLevel = LevelType.zero;
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					openLevel = value;
					Activator.logger.info("openLevel set " + value);
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					return openLevel;
				}
			},
			new LevelType("status") {
				private Integer openLevel = LevelType.zero;
				@Override
				public Integer doGetValue() throws DataPointException {
					return openLevel;
				}
			}));
		
		addModule(new MockedFaultDetection("faultDetection_" + id, domain));
	}

	public void registerDevice() {
		if (! ((serviceRegistrations == null) || serviceRegistrations.isEmpty())) {
			// already registered
			return;
		}
		serviceRegistrations = Activator.register(this);
	}

	public void unregisterDevice() {
		if (serviceRegistrations == null)
			return;
		for (ServiceRegistration reg : serviceRegistrations) {
			reg.unregister();
		}
		serviceRegistrations.clear();
	}

}
