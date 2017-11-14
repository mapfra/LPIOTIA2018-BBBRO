/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.mocked.devices;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.home.devices.GenericDevice;
import org.eclipse.om2m.sdt.home.driver.Logger;
import org.eclipse.om2m.sdt.home.driver.Utils;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.log.LogService;

interface MockedDevice {
	
	public void registerDevice();
	
	public void unregisterDevice();

}

public class Activator implements BundleActivator {

	static private final String PROTOCOL = "Mocked";
	static private final String MANUFACTURER = "MockedManufacturer";
//	static private final String ALIAS = "Simulated device for ";
	
	static private BundleContext context;
	static public Logger logger;
	
	private List<GenericDevice> devices;
	private Domain domain = new Domain("home");
	private boolean running;
	private int counter = (int)(Math.random() * 100);

	@Override
	public void start(BundleContext ctxt) throws Exception {
		try {
			try {
				logger = new Logger(PROTOCOL);
				logger.setLogService((LogService) ctxt.getService(
						ctxt.getServiceReference(LogService.class.getName())));
			} catch (Exception ignored) {
			}
			context = ctxt;
			running = true;
			devices = new ArrayList<GenericDevice>();
			
//			devices.add(new MockedWaterValve(getId(), getSerial(), domain));
//			devices.add(new MockedSmokeDetector(getId(), getSerial(), domain));
//			devices.add(new MockedWarningDevice(getId(), getSerial(), domain));
//			devices.add(new MockedFloodDetector(getId(), getSerial(), domain));
//			devices.add(new MockedSmartElectricMeter(getId(), getSerial(), domain));
			devices.add(new MockedLight(getId(), getSerial(), domain));
			devices.add(new MockedDoor(getId(), getSerial(), domain, true));
			devices.add(new MockedDoor(getId(), getSerial(), domain, false));
			devices.add(new MockedCamera(getId(), getSerial(), domain));
			devices.add(new MockedWeatherStation(getId(), getSerial(), domain));
			devices.add(new MockedSwitchButton(getId(), getSerial(), domain));
			devices.add(new MockedDeviceNumberDevice(getId(), getSerial(), domain));
//			devices.add(new MockedThermometer(getId(), getSerial(), domain));
//			devices.add(new MockedThermostat(getId(), getSerial(), domain));
//			devices.add(new MockedDoor(getId(), getSerial(), domain));
//			devices.add(new MockedCamera(getId(), getSerial(), domain));
			
			for (GenericDevice dev : devices) {
				install(dev);
			}

			new Thread(new Runnable() {
				@Override
				public void run() {
					while (running) {
						GenericDevice light = new MockedLight(getId(), getSerial(), domain);
						devices.add(light);
						logger.info("\n*************************************************");
						logger.info("start new light " + light);
						logger.info("\n*************************************************");
						install(light);
						running = false;
						try {
							Thread.sleep(180000);
						} catch (Exception ignored) {
						}
					}
				}
			});//.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void install(GenericDevice dev) {
		String name = dev.getClass().getSimpleName() + " " + dev.getName();
		dev.setDeviceName(name);
//		dev.setDeviceAliasName(ALIAS + name);
		dev.setProtocol(PROTOCOL);
		dev.setDeviceManufacturer(MANUFACTURER);
		logger.info("register " + dev);
		((MockedDevice)dev).registerDevice();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		for (GenericDevice dev : devices) {
			logger.info("unregister " + dev);
			((MockedDevice)dev).unregisterDevice();
		}
		devices.clear();
		context = null;
		running = false;
	}

	/**
	 * Register the current instance as an OSGi service
	 * 
	 * @param bundleContext
	 * @return true if successful registration
	 */
	@SuppressWarnings("rawtypes")
	public static List<ServiceRegistration> register(GenericDevice device) {
		return Utils.register(device, context);
	}
	
	private final String getId() {
		return "mocked_" + counter;
	}
	
	private final String getSerial() {
		return "serial_mocked_" + counter++;
	}

}
