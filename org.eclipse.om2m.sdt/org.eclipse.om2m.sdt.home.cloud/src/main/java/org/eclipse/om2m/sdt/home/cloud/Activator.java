/*******************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.cloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.home.devices.GenericDevice;
import org.eclipse.om2m.sdt.home.driver.Logger;
import org.eclipse.om2m.sdt.home.driver.Utils;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Activator implements BundleActivator {

	static private final String PROTOCOL = "Cloud";
	static public final Domain DOMAIN = new Domain("home");

	static private BundleContext context;
	static public Logger logger;

	private Map<String, GenericDevice> devices;
	private Map<String, List<ServiceRegistration>> registrations;
	private boolean running;
	private ServiceTracker cseServiceTracker;

	private Thread readingThread = null;
	
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
			devices = new HashMap<String, GenericDevice>();
			registrations = new HashMap<String, List<ServiceRegistration>>();
			

			readingThread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (running) {
						readDevices();
						try {
							Thread.sleep(20000);
						} catch (Exception ignored) {
						}
					}
				}
			});
			
			
			initCseServiceTracker();
		} catch (Exception e) {
			logger.error("Error starting cloud connector", e);
		}
	}
	
	private void initCseServiceTracker() {
		cseServiceTracker = new ServiceTracker(context, CseService.class.getName(), null) {
            public void removedService(ServiceReference reference, Object service) {
            	logger.info("CSEService removed");
            	running = false;
            	readingThread.interrupt();
            }
            public Object addingService(ServiceReference reference) {
            	logger.info("CSE Service found");
            	CseService cseService = (CseService) this.context.getService(reference); 
            	ResourceDiscovery.initCseService(cseService);
    			running = true;
    			readingThread.start();
                return cseService;
            }
        };
        cseServiceTracker.open();
	}

	private void readDevices() {
		List<String> newDevices = new ArrayList<String>();
		try {
			newDevices.addAll(ResourceDiscovery.readDeviceURIs());
		} catch (Throwable e) {
			logger.error("Error reading remote devices: " + e.getMessage());
		}
		logger.info("newDevices[size:" + newDevices.size() + "]");
		
		List<String> toUninstall = new ArrayList<String>();
		for (String uri : devices.keySet()) {
			if (! newDevices.contains(uri)) {
				toUninstall.add(uri);
			}
		}
		logger.info("No longer present devices: " + toUninstall);
		for (String uri : toUninstall) {
			uninstall(uri);
		}
		for (String uri : newDevices) {
			logger.info("newDevices[uri: " + uri + "]");
			if (devices.containsKey(uri)) {
				logger.info("Already installed device " + uri);
			} else {
				install(uri);
			}
		}
	}

	private void install(String uri) {
		try {
			GenericDevice device = ResourceDiscovery.readDevice(uri);
			String protocol = device.getProtocol();
			if (! isEmpty(protocol)) protocol = "." + protocol;
			device.setProtocol(PROTOCOL + protocol);
			registrations.put(device.getId(), Utils.register(device, context));
			devices.put(uri, device);
			logger.info("Installed device " + device);
		} catch (Throwable e) {
			logger.error("Error installing remote device: " + uri, e);
		}
	}
	
	private void uninstall(String uri) {
		try {
			GenericDevice device = devices.remove(uri);
			logger.info("Uninstall device " + device);
			if (device == null) {
				// Should not happen
				return;
			}
			for (ServiceRegistration reg : registrations.remove(device.getId())) {
				reg.unregister();
			}
			DOMAIN.removeDevice(device.getName());
		} catch (Exception e) {
			logger.error("Error uninstalling remote device: " + uri, e);
		}
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		running = false;
		for (String uri : new ArrayList<String>(devices.keySet())) {
			uninstall(uri);
		}
		cseServiceTracker.close();
		devices.clear();
		registrations.clear();
		context = null;
	}

	private static boolean isEmpty(final String str) {
		return (str == null) || str.trim().equals("");
	}

}
