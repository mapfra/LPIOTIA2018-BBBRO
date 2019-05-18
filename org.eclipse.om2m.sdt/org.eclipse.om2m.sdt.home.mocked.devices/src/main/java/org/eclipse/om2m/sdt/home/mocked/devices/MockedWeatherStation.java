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
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.WeatherStation;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedTemperature;
import org.eclipse.om2m.sdt.home.modules.AcousticSensor;
import org.eclipse.om2m.sdt.home.modules.AirQualitySensor;
import org.eclipse.om2m.sdt.home.modules.Barometer;
import org.eclipse.om2m.sdt.home.modules.Temperature;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.osgi.framework.ServiceRegistration;

@SuppressWarnings("rawtypes")
public class MockedWeatherStation extends WeatherStation implements MockedDevice {
	
	static private final int MIN = 15;
	static private final int MAX = 35;

	private List<ServiceRegistration> serviceRegistrations;
	private float temp;
	private Temperature temperature;
	private boolean running;
	private int delta = 1;

	public MockedWeatherStation(String id, String serial, Domain domain) {
		super(id, serial, domain);
		
		temperature = new MockedTemperature("temperature_" + id, domain, 
			new FloatDataPoint(DatapointType.currentTemperature) {
				@Override
				public Float doGetValue() throws DataPointException {
					return temp;
				}
			});
		addModule(temperature);
		
		AirQualitySensor airQuality = new AirQualitySensor("airQuality_" + id, domain);
		setAirQualitySensor(airQuality);
		airQuality.setSensorHumidity(new IntegerDataPoint(DatapointType.sensorHumidity) {
			@Override
			protected Integer doGetValue() throws DataPointException {
				return (int) (Math.random() * 100);
			}
		});
		// co2
		airQuality.setCo2(new IntegerDataPoint(DatapointType.co2) {
			@Override
			protected Integer doGetValue() throws DataPointException {
				return (int) (Math.random() * 100000);
			}
		});

		// noise
		setAcousticSensor(new AcousticSensor("noise_" + id, domain, 
				new FloatDataPoint(DatapointType.loudness) {
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float) (Math.random() * 100);
			}
		}));

		// pressure
		setBarometer(new Barometer("atmosphericPressure_" + id, 
				domain, 
				new FloatDataPoint(DatapointType.atmosphericPressure) {
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float) (990 + Math.random() * 70);
			}
		}));

		new MyThread().start();
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
	
	private class MyThread extends Thread {
		public void run() {
			while (running) {
				try {
					sleep(20000);
					temp += delta;
					if ((temp <= MIN) || (temp >= MAX)) {
						delta = -delta;
					}
					Event evt = new Event("ALARM");
					evt.addDataPoint(temperature.getDataPointByShortName(
							DatapointType.currentTemperature.getShortName()));
					evt.setValue(temp);
					temperature.addEvent(evt);
//					sendEvent();
				} catch (InterruptedException e) {
					running = false;
				}
			}
		}
	}

}
