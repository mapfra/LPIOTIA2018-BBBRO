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
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.om2m.sdt.home.netatmo.model.Event;
import org.eclipse.om2m.sdt.home.netatmo.model.Home;
import org.eclipse.om2m.sdt.home.netatmo.model.HomeListener;
import org.eclipse.om2m.sdt.home.netatmo.model.Person;
import org.eclipse.om2m.sdt.home.netatmo.model.Token;
import org.eclipse.om2m.sdt.home.netatmo.model.WeatherStation;
import org.eclipse.om2m.sdt.home.netatmo.model.WeatherStationModule;
import org.eclipse.om2m.sdt.home.netatmo.model.WelcomeCamera;

public class Discovery {

	public static final String CONFIG_CLIENT_ID = "client.id";
	public static final String CONFIG_CLIENT_SECRET = "client.secret";
	public static final String CONFIG_USERNAME = "username";
	public static final String CONFIG_PASSWORD = "password";
	public static final String CONFIG_WELCOME_CAMERA_SAMPLING = "welcome.camera.sampling";
	public static final String CONFIG_WEATHER_STATION_SAMPLING = "weather.station.sampling";
	public static final String CONFIG_CAMERA_DETECTION_THRESHOLD = "camera.detection.threshold";
	public static final String CONFIG_CAMERA_USE_LOCAL_URL = "camera.use.local.url";

	private static final int WELCOME_CAMERA_SAMPLING_DEFAULT_VALUE = 10000;
	private static final int WEATHER_STATION_SAMPLING_DEFAULT_VALUE = 500000;

	private Timer discoveryWelcomeTimer;
	private TimerTask discoveryWelcomeTimerTask;
	private Timer discoveryWeatherStationTimer;
	private TimerTask discoveryWeatherStationTimerTask;
	private Server server;
	private final List<HomeListener> homeListeners;

	private Home currentHome;
	private Map<String /* id */, WeatherStation> currentWeatherStations;
	
	private int welcomeCameraSampling;
	private int weatherStationSampling;
	private boolean useLocalUrl = false;
	
	@SuppressWarnings("rawtypes")
	public Discovery(Dictionary properties) {
		
		// retrieve useLocalUrl
		Object useLocalUrlProp = properties.get(CONFIG_CAMERA_USE_LOCAL_URL);
		if (useLocalUrlProp != null) {
			useLocalUrl = Boolean.parseBoolean(useLocalUrlProp.toString());
		} else {
			// default value = false
		}
		
		server = new Server((String) properties.get(CONFIG_CLIENT_ID), 
				(String) properties.get(CONFIG_CLIENT_SECRET),
				(String) properties.get(CONFIG_USERNAME), 
				(String) properties.get(CONFIG_PASSWORD));
		
		// retrieve sampling time for welcome camera
		try {
			welcomeCameraSampling = 
				Integer.parseInt(properties.get(CONFIG_WELCOME_CAMERA_SAMPLING).toString());
		} catch (Exception e) {
			e.printStackTrace();
			welcomeCameraSampling = WELCOME_CAMERA_SAMPLING_DEFAULT_VALUE;
		}
		
		// retrieve sampling time for weather camera
		try {
			weatherStationSampling = 
				Integer.parseInt(properties.get(CONFIG_WEATHER_STATION_SAMPLING).toString());
		} catch (Exception e) {
			e.printStackTrace();
			weatherStationSampling = WEATHER_STATION_SAMPLING_DEFAULT_VALUE;
		}
		
		currentWeatherStations = new HashMap<String, WeatherStation>();
		homeListeners = new ArrayList<HomeListener>();

		// welcome camera discovery
		discoveryWelcomeTimerTask = new TimerTask() {
			@Override
			public void run() {
				List<Home> homes = server.getHomeData(null, 5l);
				if (homes.isEmpty()) {
					Activator.logger.info("list of homes is empty !!!!!!", Discovery.class);
					return;
				}
				Home updatedHome = homes.get(0);
				
				// update useLocal parameter
				for(WelcomeCamera c : updatedHome.getCameras().values()) {
					c.setUseLocalUrl(useLocalUrl);
				}
				
				if (currentHome == null) {
					currentHome = updatedHome;
					
					// notify new Home
					notifyHomeAdded(currentHome);
					
				} else {
					if (updatedHome.getId().equals(currentHome.getId())) {
						// camera
						for (WelcomeCamera c : updatedHome.getCameras().values()) {
							boolean updated = currentHome.addOrUpdateCamera(c);
							WelcomeCamera currentCamera = currentHome.getCamera(c.getId());
							notifyCameraAddedOrUpdated(currentHome, currentCamera, updated);
						}

						// person
						for (Person p : updatedHome.getPersons().values()) {
							currentHome.addOrUpdatePerson(p);
							notifyPersonAddedOrUpdated(currentHome, p, true);
						}

						// events
						currentHome.setEvents(updatedHome.getEvents());
						notifyEventsUpdated(currentHome, currentHome.getEvents());
					} else {
						Activator.logger.info("updated home(id" + updatedHome.getId()
								+ ") != currentHome(id=" + currentHome.getId() + ")",
								Discovery.class);
					}
				}
			}
		};

		// weather station discovery
		discoveryWeatherStationTimerTask = new TimerTask() {
			@Override
			public void run() {
				List<WeatherStation> wss = server.getStationsData();
				for (WeatherStation ws : wss) {
					WeatherStation alreadyDiscoveryWS = null;
					synchronized (currentWeatherStations) {
						alreadyDiscoveryWS = currentWeatherStations.get(ws.getId());
					}
					if (alreadyDiscoveryWS != null) {
						// update
						alreadyDiscoveryWS.updateData(ws);

						// update each sub module
						for (WeatherStationModule module : ws.getModules().values()) {
							alreadyDiscoveryWS.addOrUpdateModule(module);
						}

						notifyWeatherStationAddedOrUpdated(alreadyDiscoveryWS, true);
					} else {
						// new
						synchronized (currentWeatherStations) {
							currentWeatherStations.put(ws.getId(), ws);
						}
						notifyWeatherStationAddedOrUpdated(ws, false);
					}
				}
			}
		};
	}

	public void startDiscovery() {
		Activator.logger.info("startDiscovery", Discovery.class);
		discoveryWelcomeTimer = new Timer();
		discoveryWelcomeTimer.schedule(discoveryWelcomeTimerTask, 0, welcomeCameraSampling);

		discoveryWeatherStationTimer = new Timer();
		discoveryWeatherStationTimer.schedule(discoveryWeatherStationTimerTask, 0, weatherStationSampling);
	}

	public void stopDiscovery() {
		Activator.logger.info("stopDiscovery", Discovery.class);
		if (discoveryWelcomeTimer != null) {
			discoveryWelcomeTimer.cancel();
			discoveryWelcomeTimer = null;
		}

		if (discoveryWeatherStationTimer != null) {
			discoveryWeatherStationTimer.cancel();
			discoveryWeatherStationTimer = null;
		}
	}
	
	public Token checkConnectivity() {
		return server.getToken();
	}

	public Home getCurrentHome() {
		return currentHome;
	}

	public void addHomeListener(HomeListener homeListener) {
		synchronized (homeListeners) {
			homeListeners.add(homeListener);
		}
	}

	public void removeHomeListener(HomeListener homeListener) {
		synchronized (homeListeners) {
			homeListeners.remove(homeListener);
		}
	}

	private List<HomeListener> getHomeListeners() {
		List<HomeListener> toBeReturned = new ArrayList<>();
		synchronized (homeListeners) {
			toBeReturned.addAll(homeListeners);
		}
		return toBeReturned;
	}

	private void notifyCameraAddedOrUpdated(Home home, WelcomeCamera camera, boolean updated) {
		List<HomeListener> listeners = getHomeListeners();
		for (HomeListener listener : listeners) {
			try {
				listener.notifyCameraAddedOrUpdated(home, camera, updated);
			} catch (Exception e) {
				Activator.logger.warning(e.toString(), Discovery.class);
			}
		}
	}

	private void notifyHomeAdded(Home newHome) {
		List<HomeListener> listeners = getHomeListeners();
		for (HomeListener listener : listeners) {
			try {
				listener.notifyHomeAdded(newHome);
				// for each camera, notify
				for (WelcomeCamera wc : newHome.getCameras().values()) {
					listener.notifyCameraAddedOrUpdated(newHome, wc, false);
				}

				// for each person, notify
				for (Person p : newHome.getPersons().values()) {
					listener.notifyPersonAddedOrUpdated(newHome, p, false);
				}

				// events
				listener.notifyEventsUpdated(newHome, newHome.getEvents());
			} catch (Exception e) {
				Activator.logger.warning(e.toString(), Discovery.class);
			}
		}
	}

	private void notifyEventsUpdated(Home home, List<Event> events) {
		List<HomeListener> listeners = getHomeListeners();
		for (HomeListener listener : listeners) {
			try {
				listener.notifyEventsUpdated(home, events);
			} catch (Exception e) {
				Activator.logger.warning(e.toString(), Discovery.class);
			}
		}
	}

	private void notifyPersonAddedOrUpdated(Home home, Person person, boolean updated) {
		List<HomeListener> listeners = getHomeListeners();
		for (HomeListener listener : listeners) {
			try {
				listener.notifyPersonAddedOrUpdated(home, person, updated);
			} catch (Exception e) {
				Activator.logger.warning(e.toString(), Discovery.class);
			}
		}
	}

	private void notifyWeatherStationAddedOrUpdated(WeatherStation ws, boolean updated) {
		Activator.logger.info("notifyWeatherStationAddedOrUpdated(ws=" + ws.getId() 
				+ ", updated=" + updated + ")", Discovery.class);
		List<HomeListener> listeners = getHomeListeners();
		for (HomeListener listener : listeners) {
			try {
				listener.notifyWeatherStationAddedOrUpdated(ws, updated);
			} catch (Exception e) {
				Activator.logger.warning(e.toString(), Discovery.class);
			}
		}
	}
	
}
