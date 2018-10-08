/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.netatmo.sdt;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.datapoints.UriDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.Camera;
import org.eclipse.om2m.sdt.home.driver.Logger;
import org.eclipse.om2m.sdt.home.driver.Utils;
import org.eclipse.om2m.sdt.home.modules.PersonSensor;
import org.eclipse.om2m.sdt.home.modules.PlayerControl;
import org.eclipse.om2m.sdt.home.modules.SessionDescription;
import org.eclipse.om2m.sdt.home.netatmo.impl.Activator;
import org.eclipse.om2m.sdt.home.netatmo.model.DetectedPerson;
import org.eclipse.om2m.sdt.home.netatmo.model.WelcomeCamera;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.PlayerMode;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

@SuppressWarnings("rawtypes")
public class SDTWelcomeCameraDevice extends Camera {

	private static final long DEFAULT_DETECTION_THRESHOLD = 75000; // 75 s
	private static final Logger LOGGER = new Logger("Netatmo");
	
	static private List<PlayerMode.Values> supportedPlayerModes = 
			Arrays.asList(PlayerMode.Values.play, PlayerMode.Values.stop, 
					PlayerMode.Values.pause, PlayerMode.Values.resume);

	private final WelcomeCamera camera;
	private List<ServiceRegistration> serviceRegistrations;
	private long detectionThreshold;

	// personSensor
	private Map<String /*id*/, DetectedPerson> detectedPersons = new HashMap<>();
	
	public SDTWelcomeCameraDevice(WelcomeCamera pCamera, int detectionThreshold) {
		super(computeId(pCamera.getName()), 
				pCamera.getId(), Activator.NETATMO_DOMAIN);
		camera = pCamera;
		this.detectionThreshold = (detectionThreshold <= 0) ? DEFAULT_DETECTION_THRESHOLD 
				: (detectionThreshold);

		// SDT properties
		setDeviceManufacturer("Netatmo");
		setDeviceName(pCamera.getName());
		setDeviceModelName(camera.getType());
		setProtocol(Activator.PROTOCOL);
		try {
			setPresentationURL(new URL("https://www.netatmo.com/product/security/welcome"));
		} catch (MalformedURLException ignored) {
		}

		// create properties for vpn
		// TODO check if needed
//		addProperty(new Property("vpnUrl", camera.getVpnUrl()));

		setPersonSensor(new PersonSensor("personSensor_" + getId(), Activator.NETATMO_DOMAIN,
			new ArrayDataPoint<String>(DatapointType.detectedPersons) {
				@Override
				protected List<String> doGetValue() throws DataPointException {
					List<String> ret = new ArrayList<>();
					synchronized (detectedPersons) {
						for (DetectedPerson dp : detectedPersons.values()) {
							Activator.logger.info("check " + dp.getPersonName() + " date=" 
									+ new Date(dp.getTime()*1000l), SDTWelcomeCameraDevice.class);
							if (recentlyDetected(dp)) {
								ret.add(dp.getPersonName());
							}
						}
					}
					return ret;
				}
			}));
		
		SessionDescription sdp = new SessionDescription("sdp_" + getId(), Activator.NETATMO_DOMAIN);
		sdp.setUrl(new UriDataPoint(DatapointType.url) {
			@Override
			protected URI doGetValue() throws DataPointException {
				try {
					return new URI(camera.getVpnUrl() + "/live/"
						+ (camera.getUseLocalUrl() ? "index_local.m3u8" : "index.m3u8"));
				} catch (URISyntaxException e) {
					throw new DataPointException(e);
				}
			}
		});
		sdp.setSdp(new StringDataPoint(DatapointType.sdp) {
			@Override
			protected String doGetValue() throws DataPointException {
				return "HLS";
			}
		});
//		sdp.setLogin(new StringDataPoint(DatapointType.login) {
//				@Override
//				protected String doGetValue() throws DataPointException {
//					return "";
//				}
//			});
//		sdp.setPassword(new StringDataPoint(DatapointType.password) {
//			@Override
//			protected String doGetValue() throws DataPointException {
//				return "";
//			}
//		});
		setSessionDescription(sdp);
		
		setPlayerControl(new PlayerControl("player_" + getId(), Activator.NETATMO_DOMAIN,
			new PlayerMode() {
				@Override
				protected PlayerMode.Values doGetValue() throws DataPointException {
					return camera.getAlimOk() ?
							(camera.getIsOn() ? PlayerMode.Values.play : PlayerMode.Values.stop)
							: PlayerMode.Values.pause;
				}
				@Override
				protected void doSetValue(PlayerMode.Values v) throws DataPointException {
					throw new DataPointException("Not implemented");
				}
			},
			new ArrayDataPoint<PlayerMode.Values>(DatapointType.supportedPlayerModes) {
				@Override
				public List<PlayerMode.Values> doGetValue() throws DataPointException {
					return supportedPlayerModes;
				}
			}));
	}

	public void register(BundleContext context) {
		if (serviceRegistrations == null) {
			serviceRegistrations = Utils.register(this, context);
		}
	}

	public void unregister() {
		if (serviceRegistrations != null) {
			for (ServiceRegistration sr : serviceRegistrations) {
				sr.unregister();
			}
		}
		// remove modules and device object from Domain
		Activator.NETATMO_DOMAIN.removeDevice(getName());
	}

	public void notifyPerson(String personId, String personName, Long time) {
		synchronized (detectedPersons) {
			DetectedPerson dp = detectedPersons.get(personId);
			if (dp == null) {
				dp = new DetectedPerson(personId, personName);
				detectedPersons.put(personId, dp);
			}
			dp.setTime(time);
		}
	}
	
	private boolean recentlyDetected(final DetectedPerson dp) {
		long diff = System.currentTimeMillis() - dp.getTime() * 1000;
		boolean ret = diff < detectionThreshold;
		Activator.logger.info(dp.getPersonName() 
			+ (ret ? " detected recently " : "detected too long ago ") + diff);
		LOGGER.info(dp.getPersonName() 
				+ (ret ? " detected recently " : "detected too long ago ") + diff + " " +  detectionThreshold);
		return ret;
	}

	private static String computeId(String pId) {
		return pId.replaceAll("[^a-zA-Z0-9]+", "_").trim();
	}

}
