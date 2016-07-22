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

	@Override
	public void start(BundleContext ctxt) throws Exception {
		try {
			try {
				logger = new Logger(PROTOCOL);
				logger.setLogService((LogService) ctxt.getService(
						ctxt.getServiceReference(LogService.class.getName())));
			} catch (Exception ignored) {
			}
			Domain domain = new Domain("home");
			context = ctxt;
			devices = new ArrayList<GenericDevice>();
			
			devices.add(new MockedSmartElectricMeter("mocked_1", "serial_mocked_1", domain, "Kitchen"));
			devices.add(new MockedWaterValve("mocked_2", "serial_mocked_2", domain, "Garage"));
			devices.add(new MockedSmokeDetector("mocked_3", "serial_mocked_3", domain, "Bathroom"));
			devices.add(new MockedLight("mocked_4", "serial_mocked_4", domain, "Living Room"));
			devices.add(new MockedWarningDevice("mocked_5", "serial_mocked_5", domain, "Outdoor"));
			devices.add(new MockedFloodDetector("mocked_6", "serial_mocked_6", domain, "Bathroom"));
			
			for (GenericDevice dev : devices) {
				dev.setDeviceName(dev.getClass().getSimpleName());
				dev.setDeviceAliasName("Simulated device for " 
						+ dev.getClass().getSuperclass().getSimpleName());
				dev.setProtocol(PROTOCOL);
				dev.setDeviceManufacturer("MockedManufacturer");
				logger.info("register " + dev);
				((MockedDevice)dev).registerDevice();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		for (GenericDevice dev : devices) {
			logger.info("unregister " + dev);
			((MockedDevice)dev).unregisterDevice();
		}
		devices.clear();
		context = null;
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
