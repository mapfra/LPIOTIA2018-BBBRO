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

import org.onem2m.home.devices.SmartElectricMeter;
import org.onem2m.home.modules.EnergyConsumption;
import org.onem2m.sdt.DataPoint;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Event;
import org.onem2m.sdt.datapoints.FloatDataPoint;
import org.onem2m.sdt.home.mocked.module.MockedBinarySwitch;
import org.onem2m.sdt.home.mocked.module.MockedClock;
import org.onem2m.sdt.home.mocked.module.MockedEnergyConsumption;
import org.onem2m.sdt.home.mocked.module.MockedEnergyGeneration;
import org.onem2m.sdt.home.mocked.module.MockedFaultDetection;
import org.onem2m.sdt.home.mocked.module.MockedRunMode;
import org.onem2m.sdt.impl.DataPointException;
import org.osgi.framework.ServiceRegistration;

public class MockedSmartElectricMeter extends SmartElectricMeter implements MockedDevice {

	private boolean running;
	private float power = 0;
	private List<ServiceRegistration> serviceRegistrations;
	private EnergyConsumption energyConsumption;

	public MockedSmartElectricMeter(final String id, String serial, Domain domain,
			String deviceLocation) {
		super(id, serial, domain);

		// set property
		setLocation(deviceLocation);

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
