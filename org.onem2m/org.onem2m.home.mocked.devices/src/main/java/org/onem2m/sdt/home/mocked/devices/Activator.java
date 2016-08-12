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
			
			devices.add(new MockedSmartElectricMeter("mocked_1", "serial_mocked_1", domain, "Kitchen"));
			devices.add(new MockedWaterValve("mocked_2", "serial_mocked_2", domain, "Garage"));
			devices.add(new MockedSmokeDetector("mocked_3", "serial_mocked_3", domain, "Bathroom"));
			devices.add(new MockedWarningDevice("mocked_4", "serial_mocked_4", domain, "Outdoor"));
			devices.add(new MockedFloodDetector("mocked_5", "serial_mocked_5", domain, "Bathroom"));
			
			for (GenericDevice dev : devices) {
				install(dev);
			}

			new Thread(new Runnable() {
				private int counter = 6;
				@Override
				public void run() {
					while (running) {
						String name = "mocked_" + counter++;
						GenericDevice light = new MockedLight(name, "serial_"
								+ name, domain, "Living Room");
						devices.add(light);
						logger.info("\n*************************************************");
						logger.info("start new light " + light);
						logger.info("\n*************************************************");
						install(light);
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
		dev.setDeviceName(dev.getClass().getSimpleName());
		dev.setDeviceAliasName("Simulated device for " 
				+ dev.getClass().getSuperclass().getSimpleName());
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

}
