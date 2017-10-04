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
import org.eclipse.om2m.sdt.home.devices.Thermostat;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedTemperature;
import org.eclipse.om2m.sdt.home.modules.Temperature;
import org.eclipse.om2m.sdt.home.modules.Timer;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.osgi.framework.ServiceRegistration;

@SuppressWarnings("rawtypes")
public class MockedThermostat extends Thermostat implements MockedDevice {

	private List<ServiceRegistration> serviceRegistrations;
	private float currentTemp;
	private float targetTemp;
	private Temperature temperature;
	private boolean running;

	public MockedThermostat(String id, String serial, Domain domain) {
		super(id, serial, domain);
		
		temperature = new MockedTemperature("temperature_" + id, domain, 
				new FloatDataPoint(DatapointType.currentTemperature) {
				@Override
				public Float doGetValue() throws DataPointException {
					return currentTemp;
				}
			});
		
		temperature.setTargetTemperature(new FloatDataPoint(DatapointType.targetTemperature) {
			@Override
			protected Float doGetValue() throws DataPointException {
				return targetTemp;
			}
			@Override
			protected void doSetValue(Float temp) throws DataPointException {
				targetTemp = temp;
			}
		});
		addModule(temperature);
		
		Timer timer = new Timer("timer_" + id, domain);
//		timer.setActivated(new BooleanDataPoint(DatapointType.activated) {
//			@Override
//			protected Boolean doGetValue() throws DataPointException {
//				return running;
//			}
//			@Override
//			protected void doSetValue(Boolean b) throws DataPointException {
//				running = b;
//			}
//		});
		addModule(timer);
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
