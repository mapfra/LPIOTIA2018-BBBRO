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
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.FloodDetector;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedFaultDetection;
import org.eclipse.om2m.sdt.home.modules.WaterSensor;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.osgi.framework.ServiceRegistration;

@SuppressWarnings("rawtypes")
public class MockedFloodDetector extends FloodDetector implements MockedDevice {

	private List<ServiceRegistration> serviceRegistrations;
	
	private boolean waterAlarm = false;
	private boolean running = false;
	private WaterSensor waterSensor;

	public MockedFloodDetector(String id, String serial, Domain domain) {
		super(id, serial, domain);
		
		waterSensor = new WaterSensor("waterSensor_" + id, domain, 
			new BooleanDataPoint(DatapointType.alarm) {
				@Override
				public Boolean doGetValue() throws DataPointException {
					return waterAlarm;
				}
			}
		);
		addModule(waterSensor);
				
		addModule(new MockedFaultDetection("faultDetection_" + id, domain));
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
						Thread.sleep((int) (30000 + 10000 * Math.random()));
						Activator.logger.info("Generating Flood Alarm event");
						Event evt = new Event("ALARM");
						waterAlarm = ! waterAlarm;
						evt.addDataPoint(waterSensor.getDataPoint("alarm"));
						evt.setValue(waterAlarm);
						waterSensor.addEvent(evt);
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
