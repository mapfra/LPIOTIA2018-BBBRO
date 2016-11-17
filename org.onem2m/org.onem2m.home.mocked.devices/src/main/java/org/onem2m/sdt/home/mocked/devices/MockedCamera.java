/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.home.mocked.devices;

import java.util.Arrays;
import java.util.List;

import org.onem2m.home.devices.Camera;
import org.onem2m.home.modules.PersonSensor;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.ArrayDataPoint;
import org.onem2m.sdt.home.mocked.module.MockedStreaming;
import org.onem2m.sdt.impl.DataPointException;
import org.osgi.framework.ServiceRegistration;

public class MockedCamera extends Camera implements MockedDevice {

	private List<ServiceRegistration> serviceRegistrations;

	public MockedCamera(String id, String serial, Domain domain) {
		super(id, serial, domain);

		// Module FaultDetection
		addModule(new MockedStreaming("mockedStreaming-" + id, domain));

		addModule(new PersonSensor("personSensor_" + id, domain, 
			new ArrayDataPoint<String>("detectedPersons") {
				@Override
				public List<String> doGetValue() throws DataPointException {
					return Arrays.asList("admin", "Phil");
				}
			}));
	}

	public void registerDevice() {
		if (! ((serviceRegistrations == null) || serviceRegistrations.isEmpty())) {
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
