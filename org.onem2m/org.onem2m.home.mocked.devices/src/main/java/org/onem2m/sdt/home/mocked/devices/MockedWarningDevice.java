/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.home.mocked.devices;

import java.util.List;

import org.onem2m.home.devices.WarningDevice;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.home.mocked.module.MockedAlarmSpeaker;
import org.onem2m.sdt.home.mocked.module.MockedFaultDetection;
import org.osgi.framework.ServiceRegistration;

public class MockedWarningDevice extends WarningDevice implements MockedDevice {

	private List<ServiceRegistration> serviceRegistrations;

	public MockedWarningDevice(String id, String serial, Domain domain) {
		super(id, serial, domain);
		
		// Alarm Speaker
		addModule(new MockedAlarmSpeaker("alarmSpeaker_" + id, domain));
		
		// FaultDetection
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
