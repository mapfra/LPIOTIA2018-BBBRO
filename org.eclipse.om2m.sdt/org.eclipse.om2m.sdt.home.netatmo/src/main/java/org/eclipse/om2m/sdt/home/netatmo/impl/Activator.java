/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.netatmo.impl;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.home.driver.Logger;
import org.eclipse.om2m.sdt.home.netatmo.model.Event;
import org.eclipse.om2m.sdt.home.netatmo.model.Home;
import org.eclipse.om2m.sdt.home.netatmo.model.HomeListener;
import org.eclipse.om2m.sdt.home.netatmo.model.Person;
import org.eclipse.om2m.sdt.home.netatmo.model.WeatherStation;
import org.eclipse.om2m.sdt.home.netatmo.model.WeatherStationModule;
import org.eclipse.om2m.sdt.home.netatmo.model.WelcomeCamera;
import org.eclipse.om2m.sdt.home.netatmo.sdt.SDTWeatherStation;
import org.eclipse.om2m.sdt.home.netatmo.sdt.SDTWelcomeCameraDevice;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Activator implements BundleActivator, HomeListener, ManagedService  {
	
	public static final Domain NETATMO_DOMAIN = new Domain("Netatmo_Domain");
	public static final String PROTOCOL = "Netatmo";
	public static Logger logger = new Logger(PROTOCOL);
	private static int detectionThreshold;

	private static final String SERVICE_PID = "netatmo.sdt.driver";
	
	private ServiceTracker logServiceTracker;
	
	private Discovery discovery;
	
	private Map<String, WelcomeCamera> welcomeCameras;
	private Map<String, SDTWelcomeCameraDevice> sdtCameras;
	
	private Map<String, SDTWeatherStation> sdtWeatherStations;
	
	private BundleContext bundleContext;
	
	private ServiceRegistration serviceRegistration;
	
	public Activator() {
		sdtCameras = new HashMap<>();
		welcomeCameras = new HashMap<>();
		sdtWeatherStations = new HashMap<>();
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		// store bundleContext 
		bundleContext = context;
		
		// retrieve LogService and init Logger
		logServiceTracker = new ServiceTracker(context, LogService.class.getName(), 
				new ServiceTrackerCustomizer() {
			@Override
			public void removedService(ServiceReference reference, Object service) {
				logger.unsetLogService();
			}
			@Override
			public void modifiedService(ServiceReference reference, Object service) {
			}
			@Override
			public Object addingService(ServiceReference reference) {
				LogService logService = (LogService) context.getService(reference);
//				logger.setLogService(logService);
				return logService;
			}
		});
		logServiceTracker.open();
		
		// register this as a ManagedService
		Dictionary properties = new Hashtable<>();
		properties.put(Constants.SERVICE_PID, SERVICE_PID);
		serviceRegistration = bundleContext.registerService(ManagedService.class.getName(), 
				this, properties);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (serviceRegistration != null) {
			serviceRegistration.unregister();
			serviceRegistration = null;
		}
		
		logServiceTracker.close();
		logServiceTracker = null;
		
		if (discovery != null) {
			discovery.stopDiscovery();
			discovery.removeHomeListener(this);
			discovery = null;
		}
		
		// unregister all cameras
		for(SDTWelcomeCameraDevice sdtWelcomeCamera : sdtCameras.values()) {
			sdtWelcomeCamera.unregister();
		}
		
		// unregister all weather stations
		for(SDTWeatherStation sdtWeatherStation : sdtWeatherStations.values()) {
			sdtWeatherStation.unregister();
		}
	}

	@Override
	public void notifyCameraAddedOrUpdated(Home home, WelcomeCamera camera, boolean updated) {
		if (updated) {
			// update
			
		} else {
			// add new camera into welcomeCameras map
			synchronized (welcomeCameras) {
				welcomeCameras.put(camera.getId(), camera);
			}
			
			// create SDT device
			SDTWelcomeCameraDevice sdtWelcomeCamera = 
					new SDTWelcomeCameraDevice(camera, detectionThreshold);
			sdtWelcomeCamera.register(bundleContext);
			
			// add into sdtCameras map
			synchronized (sdtCameras) {
				sdtCameras.put(camera.getId(), sdtWelcomeCamera);
			}
		}
	}

	@Override
	public void notifyPersonAddedOrUpdated(Home home, Person person, boolean updated) {
		SDTWelcomeCameraDevice sdtWelcomeCamera = getSDTWelcomeCamera(home.getCameras().values().iterator().next().getId());
		
		if (sdtWelcomeCamera != null) {
			// notify person
			if (person.getPseudo() == null)
//				logger.debug("Unknwown person... Ignore");
			;else if (person.getOutOfSight())
				logger.debug(person.getPseudo() + " out of sight. Ignore...");
			else {
				logger.info("InSight " + person.getPseudo());
				sdtWelcomeCamera.notifyPerson(person.getId(), person.getPseudo(), person.getLastSeen());
			}
		} else {
			logger.info("notifyPersonAddedOrUpdated(" + person.toString() + ") -SDT camera not found");
		}
	}

	@Override
	public void notifyEventsUpdated(Home home, List<Event> events) {
//		logger.info("notifyEventsUpdated");
//		
//		// list of events has changed
//		for(Event e : events) {
//			String cameraId = e.getCameraId();
//			String eventType = e.getType();
//			if ("person".equals(eventType)) {
//				String personId = e.getPersonId();
//				Long time = e.getTime();
//				Person person = home.getPerson(personId);
//				
//				// retrieve SDt device
//				SDTWelcomeCameraDevice sdtWelcomeCamera = getSDTWelcomeCamera(cameraId);
//				
//				if (sdtWelcomeCamera != null) {
//					// notify person
//					sdtWelcomeCamera.notifyPerson(personId, person.getPseudo(), time);
//
//					// no need to continue
//					break;
//				}
//				
//				
//			}
//		}
	}

	@Override
	public void notifyHomeAdded(Home newHome) {
		
	}
	
	@Override
	public void notifyWeatherStationAddedOrUpdated(WeatherStation ws, boolean updated) {
		// register WeatherStation as SDT Device !
		logger.info("notifyWeatherStationAddedOrUpdated(ws=" + ws.getId() + ", updated=" + updated + ")");
		
		if (!updated) {
			// add a new WeatherStation
			SDTWeatherStation sdtWS = new SDTWeatherStation(ws);
			sdtWS.register(bundleContext);
			sdtWeatherStations.put(sdtWS.getId(), sdtWS);
			
			// add new module
			for (WeatherStationModule module : ws.getModules().values()) {
				SDTWeatherStation sdtModuleWS = new SDTWeatherStation(module);
				sdtModuleWS.register(bundleContext);
				sdtWeatherStations.put(sdtModuleWS.getId(), sdtModuleWS);
			}
		}
	}	
	
	private SDTWelcomeCameraDevice getSDTWelcomeCamera(String cameraId) {
		SDTWelcomeCameraDevice sdtWelcomeCamera = null;
		synchronized (sdtCameras) {
			sdtWelcomeCamera = sdtCameras.get(cameraId);
		}
		return sdtWelcomeCamera;
	}

	@Override
	public synchronized void updated(Dictionary properties) throws ConfigurationException {
		logger.info("updated(properties=" + properties + ")");
		// check all parameters are located into properties
		if (! checkParameters(properties)) {
			logger.info("Missing a mandatory property --> Netatmo driver is not started");
			return;
		}

		// stop discovery if it has been already started
		if (discovery != null) {
			discovery.stopDiscovery();
			discovery.removeHomeListener(this);
			discovery = null;
		}

		// start it again
		discovery = new Discovery(properties);
		discovery.addHomeListener(this);
		discovery.startDiscovery();
	}
	
	/**
	 * Return true if all mandatory parameters are provided
	 * @param properties
	 * @return true if all is ok
	 */
	private static boolean checkParameters(Dictionary properties) {
		logger.info("checkParameters");
		if (properties == null) {
			// no properties
			logger.info("No properties to configure SDT Netatmo Driver --> the driver is not started !");
			return false;
		}
		
		logger.info("checkParameter(properties.length=" + properties.size() + ")");
		List<String> missing = new ArrayList<String>();
		if (properties.get(Discovery.CONFIG_CLIENT_ID) == null) {
			missing.add(Discovery.CONFIG_CLIENT_ID);
		}
		if (properties.get(Discovery.CONFIG_CLIENT_SECRET) == null) {
			missing.add(Discovery.CONFIG_CLIENT_SECRET);
		}
		if (properties.get(Discovery.CONFIG_USERNAME) == null) {
			missing.add(Discovery.CONFIG_USERNAME);
		}
		if (properties.get(Discovery.CONFIG_PASSWORD) == null) {
			missing.add(Discovery.CONFIG_PASSWORD);
		}
		// retrieve detection threshold
		try {
			detectionThreshold = 
				Integer.parseInt(properties.get(Discovery.CONFIG_CAMERA_DETECTION_THRESHOLD).toString());
		} catch (Exception e) {
			detectionThreshold = -1;
		}
		if (missing.isEmpty())
			return true;
		logger.info(missing + " parameter(s) missing");
		return false;
	}

}
