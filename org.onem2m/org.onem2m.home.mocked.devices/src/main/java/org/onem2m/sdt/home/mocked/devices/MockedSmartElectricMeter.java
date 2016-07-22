/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.home.mocked.devices;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.onem2m.home.devices.SmartElectricMeter;
import org.onem2m.home.modules.EnergyConsumption;
import org.onem2m.sdt.DataPoint;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Event;
import org.onem2m.sdt.datapoints.ArrayDataPoint;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.datapoints.DateDataPoint;
import org.onem2m.sdt.datapoints.FloatDataPoint;
import org.onem2m.sdt.datapoints.TimeDataPoint;
import org.onem2m.sdt.home.mocked.module.MockedBinarySwitch;
import org.onem2m.sdt.home.mocked.module.MockedClock;
import org.onem2m.sdt.home.mocked.module.MockedEnergyConsumption;
import org.onem2m.sdt.home.mocked.module.MockedEnergyGeneration;
import org.onem2m.sdt.home.mocked.module.MockedFaultDetection;
import org.onem2m.sdt.home.mocked.module.MockedRunMode;
import org.onem2m.sdt.impl.AccessException;
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
		addModule(new MockedBinarySwitch("binarySwitch_" + id, domain, 
				new BooleanDataPoint("powerState") {
			Boolean powerState = Boolean.FALSE;
			@Override
			public void doSetValue(Boolean value) throws DataPointException {
				powerState = value;
			}
			@Override
			public Boolean doGetValue() throws DataPointException {
				return powerState;
			}
		}));

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
		addModule(new MockedFaultDetection("faultDetection_" + id, domain, 
				new BooleanDataPoint("status") {
			Boolean status = Boolean.FALSE;
			@Override
			public void doSetValue(Boolean value) throws DataPointException {
				throw new DataPointException("Not Writable");
			}
			@Override
			public Boolean doGetValue() throws DataPointException {
				return status;
			}
		}));
		
		// clock
		addModule(new MockedClock("clock", domain, 
				new TimeDataPoint("currentTime") {
			Date d = new Date();
			@Override
			public void doSetValue(Date value) throws DataPointException {
				d = value;
			}
			@Override
			public Date doGetValue() throws DataPointException {
				return d;
			}
		}, 
		new DateDataPoint("currentDate") {
			Date d = new Date();
			@Override
			public void doSetValue(Date value) throws DataPointException {
				d = value;
			}
			@Override
			public Date doGetValue() throws DataPointException {
				return d;
			}
		}));
		
		// runMode
		addModule(new MockedRunMode("runMode", domain, 
				new ArrayDataPoint<String>("operationMode") {
			List<String> operationMode = Arrays.asList("mode1");
			@Override
			public List<String> doGetValue() throws DataPointException {
				return operationMode;
			}
			@Override
			public void doSetValue(List<String> value) throws DataPointException {
				try {
					List<String> possibleValues = getRunMode().getSupportedModes();
					for (String v : value) {
						if (! possibleValues.contains(v)) {
							throw new DataPointException("value " + v + " is not permitted");
						}
					}
					operationMode = value;
				} catch (AccessException e) {
					throw new DataPointException(e);	
				}
				
			}
		}, 
		new ArrayDataPoint<String>("supportedModes") {
			List<String> supportedModes = Arrays.asList("mode1", "mode2");
			@Override
			public List<String> doGetValue() throws DataPointException {
				return supportedModes;
			}
			@Override
			public void doSetValue(List<String> value) throws DataPointException {
				supportedModes = value;
			}
		}));
		
		// energyGeneration
		addModule(new MockedEnergyGeneration("energyGeneration", domain));
	}

	public void registerDevice() {
		running = true;
		if (! ((serviceRegistrations == null) || serviceRegistrations.isEmpty())) {
			// already registered
			return;
		}
		serviceRegistrations = Activator.register(this);

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (running) {
					try {
						Thread.sleep((long)(10000 * (1 + Math.random())));
						Activator.logger.info("Generating event");
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
