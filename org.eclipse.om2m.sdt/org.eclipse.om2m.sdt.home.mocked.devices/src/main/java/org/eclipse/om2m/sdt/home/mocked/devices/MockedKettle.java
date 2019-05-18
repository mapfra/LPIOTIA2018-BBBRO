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
import org.eclipse.om2m.sdt.datapoints.FloatDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.Kettle;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedBinarySwitch;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedFaultDetection;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedTemperature;
import org.eclipse.om2m.sdt.home.modules.LiquidLevel;
import org.eclipse.om2m.sdt.home.modules.Temperature;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.osgi.framework.ServiceRegistration;

@SuppressWarnings("rawtypes")
public class MockedKettle extends Kettle implements MockedDevice {
	
	static private final int MIN = 15;
	static private final int MAX = 35;

	private List<ServiceRegistration> serviceRegistrations;
	private float temp;
	private Temperature temperature;
	private boolean running;
	private int delta;

	public MockedKettle(String id, String serial, Domain domain) {
		super(id, serial, domain);

		// Module FaultDetection
		addModule(new MockedFaultDetection("faultDetection_" + id, domain));

		// Module BinarySwitch
		addModule(new MockedBinarySwitch("binarySwitch_" + id, domain));

		temperature = new MockedTemperature("temperature_" + id, domain, 
			new FloatDataPoint(DatapointType.currentTemperature) {
				@Override
				public Float doGetValue() throws DataPointException {
					return temp;
				}
			});
		addModule(temperature);
		
		addModule(new LiquidLevel("LiquidLevel_" + getId(), domain, 
			new org.eclipse.om2m.sdt.home.types.LiquidLevel() {
				@Override
				public Values doGetValue() throws DataPointException {
					return Values.values()[(int)(Math.random() * 5)];
				}
			}));
	}

	public void registerDevice() {
		running = true;
		if (! ((serviceRegistrations == null) || serviceRegistrations.isEmpty())) {
			// already registered
			return;
		}
		serviceRegistrations = Activator.register(this);
	}

	public void unregisterDevice() {
		running = false;
		if (serviceRegistrations == null)
			return;
		for (ServiceRegistration reg : serviceRegistrations) {
			reg.unregister();
		}
		serviceRegistrations.clear();
	}

}
