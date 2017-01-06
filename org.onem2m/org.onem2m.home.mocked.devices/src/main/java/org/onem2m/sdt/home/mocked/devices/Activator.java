/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.home.mocked.devices;

import java.util.ArrayList;
import java.util.List;

import org.onem2m.home.devices.GenericDevice;
import org.onem2m.home.driver.Logger;
import org.onem2m.home.driver.Utils;
import org.onem2m.sdt.Domain;
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
	
	static private BundleContext context;
	static public Logger logger;
	
	private List<GenericDevice> devices;
	private Domain domain = new Domain("home");
	private boolean running;
	private int counter = 1;

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
			
			devices.add(new MockedSmartElectricMeter(getName(), getSerial(), domain));
			devices.add(new MockedWaterValve(getName(), getSerial(), domain));
			devices.add(new MockedSmokeDetector(getName(), getSerial(), domain));
			devices.add(new MockedWarningDevice(getName(), getSerial(), domain));
			devices.add(new MockedFloodDetector(getName(), getSerial(), domain));
			
			for (GenericDevice dev : devices) {
				install(dev);
			}

			new Thread(new Runnable() {
				@Override
				public void run() {
					while (running) {
						GenericDevice light = new MockedLight(getName(), getSerial(), domain);
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
			}).start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void install(GenericDevice dev) {
		String name = dev.getClass().getSimpleName();
		if (dev instanceof MockedLight)
			name += " " + (counter-1);
		dev.setDeviceName(name);
		dev.setDeviceAliasName("Simulated device for " + name);
		dev.setProtocol(PROTOCOL);
		dev.setDeviceManufacturer("MockedManufacturer");
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
	public static List<ServiceRegistration> register(GenericDevice device) {
		return Utils.register(device, context);
	}
	
	private final String getName() {
		return "mocked_" + counter;
	}
	
	private final String getSerial() {
		return "serial_mocked_" + counter++;
	}

}
