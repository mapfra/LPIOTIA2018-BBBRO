/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.mocked.devices;

import java.util.Arrays;
import java.util.List;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.Camera;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedStreaming;
import org.eclipse.om2m.sdt.home.modules.PersonSensor;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.osgi.framework.ServiceRegistration;

@SuppressWarnings("rawtypes")
public class MockedCamera extends Camera implements MockedDevice {

	private List<ServiceRegistration> serviceRegistrations;

	public MockedCamera(String id, String serial, Domain domain) {
		super(id, serial, domain);

		// Module FaultDetection
		addModule(new MockedStreaming("streaming_" + id, domain));

		addModule(new PersonSensor("personSensor_" + id, domain, 
			new ArrayDataPoint<String>(DatapointType.detectedPersons) {
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
