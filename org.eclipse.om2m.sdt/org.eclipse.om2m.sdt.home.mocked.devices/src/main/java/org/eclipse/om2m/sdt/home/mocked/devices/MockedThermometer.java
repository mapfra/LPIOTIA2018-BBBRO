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
import org.eclipse.om2m.sdt.Event;
import org.eclipse.om2m.sdt.datapoints.FloatDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.TemperatureDetector;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedTemperature;
import org.eclipse.om2m.sdt.home.modules.Temperature;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.osgi.framework.ServiceRegistration;

@SuppressWarnings("rawtypes")
public class MockedThermometer extends TemperatureDetector implements MockedDevice {
	
	static private final int MIN = 15;
	static private final int MAX = 35;

	private List<ServiceRegistration> serviceRegistrations;
	private float temp;
	private Temperature temperature;
	private boolean running;
	private int delta;

	public MockedThermometer(String id, String serial, Domain domain) {
		super(id, serial, domain);
		
		temperature = new MockedTemperature("temperature_" + id, domain, 
			new FloatDataPoint(DatapointType.currentTemperature) {
				@Override
				public Float doGetValue() throws DataPointException {
					return temp;
				}
			});
		addModule(temperature);
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
	
	private class ThermoThread extends Thread {
		public void run() {
			while (running) {
				try {
					sleep(20000);
					float oldT = temp;
					temp += delta;
					if ((temp <= MIN) || (temp >= MAX)) {
						delta = -delta;
					}
//					informListeners(oldT);
					Event evt = new Event("ALARM");
					evt.addDataPoint(temperature.getDataPoint("currentTemperature"));
					evt.setValue(temp);
					temperature.addEvent(evt);
//					sleep(2000);
//					sendEvent();
				} catch (InterruptedException e) {
					running = false;
				}
			}
		}
	}

}
