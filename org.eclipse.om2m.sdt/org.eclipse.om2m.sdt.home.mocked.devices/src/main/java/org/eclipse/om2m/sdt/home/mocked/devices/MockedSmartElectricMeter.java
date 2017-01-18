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

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Event;
import org.eclipse.om2m.sdt.datapoints.FloatDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.SmartElectricMeter;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedBinarySwitch;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedClock;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedEnergyConsumption;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedEnergyGeneration;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedFaultDetection;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedRunMode;
import org.eclipse.om2m.sdt.home.modules.EnergyConsumption;
import org.osgi.framework.ServiceRegistration;

public class MockedSmartElectricMeter extends SmartElectricMeter implements MockedDevice {

	private boolean running;
	private float power = 0;
	private List<ServiceRegistration> serviceRegistrations;
	private EnergyConsumption energyConsumption;

	public MockedSmartElectricMeter(final String id, String serial, Domain domain) {
		super(id, serial, domain);

		// Binary Switch
		addModule(new MockedBinarySwitch("binarySwitch_" + id, domain));

		// EnergyConsumption
		energyConsumption = new MockedEnergyConsumption("energyConsumption_" + id, domain, 
			new FloatDataPoint("power") {
				@Override
				public Float doGetValue() throws DataPointException {
					return power;
				}
			});
		addModule(energyConsumption);
		
		// FaultDetection
		addModule(new MockedFaultDetection("faultDetection_" + id, domain));
		
		// clock
		addModule(new MockedClock("clock_" + id, domain));
		
		// runMode
		addModule(new MockedRunMode("runMode_" + id, domain));
		
		// energyGeneration
		addModule(new MockedEnergyGeneration("energyGeneration_" + id, domain));
	}

	public void registerDevice() {
		running = true;
		if (! ((serviceRegistrations == null) || serviceRegistrations.isEmpty())) {
			// already registered
			return;
		}
		serviceRegistrations = Activator.register(this);
		try {
			getRunMode().setSupportedModes(Arrays.asList("mode1", "mode2", "mode3", "mode4"));
			getRunMode().setOperationMode(Arrays.asList("mode2", "mode3"));
		} catch (Exception e) {
			Activator.logger.warning("", e);
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (running) {
					try {
						Thread.sleep((long)(30000 * (1 + Math.random())));
						Activator.logger.info("Generating Power event");
						power = (float) (100 * Math.random());
						DataPoint dp = energyConsumption.getDataPoint("power");
						Event evt = new Event("power change");
						evt.addDataPoint(dp);
						evt.setValue(power);
						energyConsumption.addEvent(evt);
					} catch (Throwable e) {
						Activator.logger.warning("Error generating event", e);
					}
				}
			}
		}).start();
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
