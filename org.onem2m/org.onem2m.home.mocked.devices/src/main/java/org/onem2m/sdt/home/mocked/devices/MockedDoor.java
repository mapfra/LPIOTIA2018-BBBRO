/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.home.mocked.devices;

import java.util.List;

import org.onem2m.home.devices.Door;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.home.mocked.module.MockedDoorStatus;
import org.onem2m.sdt.home.mocked.module.MockedFaultDetection;
import org.onem2m.sdt.home.mocked.module.MockedLock;
import org.osgi.framework.ServiceRegistration;

public class MockedDoor extends Door implements MockedDevice {

	private List<ServiceRegistration> serviceRegistrations;

	public MockedDoor(String id, String serial, Domain domain) {
		super(id, serial, domain);

		// Module FaultDetection
		addModule(new MockedFaultDetection("faultDetection_" + id, domain));

		// Module DoorStatus
		addModule(new MockedDoorStatus("doorStatus_" + id, domain));

		// Module Lock
		addModule(new MockedLock("lock_" + id, domain));
		
		setLocation("Porte d\'entree");
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
