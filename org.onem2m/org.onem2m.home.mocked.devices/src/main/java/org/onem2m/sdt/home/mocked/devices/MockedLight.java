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

import org.onem2m.home.devices.Light;
import org.onem2m.home.modules.ColourSaturation;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.home.mocked.module.MockedBinarySwitch;
import org.onem2m.sdt.home.mocked.module.MockedColour;
import org.onem2m.sdt.home.mocked.module.MockedFaultDetection;
import org.onem2m.sdt.home.mocked.module.MockedRunMode;
import org.onem2m.sdt.impl.DataPointException;
import org.osgi.framework.ServiceRegistration;

public class MockedLight extends Light implements MockedDevice {

	private List<ServiceRegistration> serviceRegistrations;
	
	int counter;

	public MockedLight(int counter, Domain domain) {
		super("mocked_" + counter, "serial_mocked_" + counter, domain);
		String id = "mocked_" + counter;
		this.counter = counter;

		// Module FaultDetection
		addModule(new MockedFaultDetection("faultDetection_" + id, domain));

		// Module BinarySwitch
		addModule(new MockedBinarySwitch("binarySwitch_" + id, domain));

		// Module RunMode
		addModule(new MockedRunMode("runMode_" + id, domain));

		// Module Colour
		addModule(new MockedColour("colour_" + id, domain));

		addModule(new ColourSaturation("colourSaturation_" + id, domain, 
			new IntegerDataPoint("colourSaturation") {
				private Integer v = new Integer((int)(Math.random() * 100));
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					v = value;
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					return v;
				}
			})
		);
	}

	public void registerDevice() {
		if (! ((serviceRegistrations == null) || serviceRegistrations.isEmpty())) {
			return;
		}
		serviceRegistrations = Activator.register(this);
		try {
			getRunMode().setSupportedModes(Arrays.asList("mode1", "mode2", "mode3"));
			getRunMode().setOperationMode(Arrays.asList("mode1", "mode3"));
		} catch (Exception e) {
			Activator.logger.warning("", e);
		}
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
