/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.netatmo.impl;

import java.io.FileInputStream;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.eclipse.om2m.sdt.home.netatmo.model.Home;
import org.eclipse.om2m.sdt.home.netatmo.model.WelcomeCamera;

import junit.framework.TestCase;

public class DiscoveryTestCase extends TestCase {

	private Dictionary<String, String> configuration;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		Properties properties = new Properties();
		properties.load(new FileInputStream("src/test/resources/netatmo.sdt.driver.properties"));

		configuration = new Hashtable<>();
		configuration.put(Discovery.CONFIG_CLIENT_ID, properties.getProperty(Discovery.CONFIG_CLIENT_ID));
		configuration.put(Discovery.CONFIG_CLIENT_SECRET, properties.getProperty(Discovery.CONFIG_CLIENT_SECRET));
		configuration.put(Discovery.CONFIG_USERNAME, properties.getProperty(Discovery.CONFIG_USERNAME));
		configuration.put(Discovery.CONFIG_PASSWORD, properties.getProperty(Discovery.CONFIG_PASSWORD));
		configuration.put(Discovery.CONFIG_WEATHER_STATION_SAMPLING,
				properties.getProperty(Discovery.CONFIG_WEATHER_STATION_SAMPLING));
		configuration.put(Discovery.CONFIG_WELCOME_CAMERA_SAMPLING,
				properties.getProperty(Discovery.CONFIG_WELCOME_CAMERA_SAMPLING));
		
		

	}

	public void testDiscovery() throws Exception {
		Discovery disco = new Discovery(configuration);
		
		if (disco.checkConnectivity() != null) {
			disco.startDiscovery();
	
			Thread.sleep(40000);
	
			disco.stopDiscovery();
	
			Thread.sleep(10000);
	
			disco.getCurrentHome();
		} else {
			printWarningMessage();
		}
	}

	public void testLocalAddress() throws Exception {
		configuration.put(Discovery.CONFIG_CAMERA_USE_LOCAL_URL, "true");
		Discovery disco = new Discovery(configuration);
		if (disco.checkConnectivity() != null) {
			disco.startDiscovery();
	
			Thread.sleep(10000);
			Home home = disco.getCurrentHome();
	
			if (home != null) {
	
				Map<String, WelcomeCamera> cameras = home.getCameras();
	
				for (WelcomeCamera camera : cameras.values()) {
					System.out.println(camera.getUseLocalUrl());
					assertNotNull(camera);
					// useLocalUrl must be true
					assertTrue(camera.getUseLocalUrl());
				}
	
			}
	
			disco.stopDiscovery();
		} else {
			printWarningMessage();
		}

	}

	public void testDefaultAddress() throws Exception {
		Discovery disco = new Discovery(configuration);
		
		if (disco.checkConnectivity() != null) {
			disco.startDiscovery();
	
			Thread.sleep(10000);
			Home home = disco.getCurrentHome();
			if (home != null) {
	
				Map<String, WelcomeCamera> cameras = home.getCameras();
	
				for (WelcomeCamera camera : cameras.values()) {
					System.out.println(camera.getUseLocalUrl());
					// useLocalUrl must be true
					assertFalse(camera.getUseLocalUrl());
				}
			}
	
			disco.stopDiscovery();
		} else {
			printWarningMessage();
		}

	}

	public void testRemoteAddress() throws Exception {
		configuration.put(Discovery.CONFIG_CAMERA_USE_LOCAL_URL, "false");
		Discovery disco = new Discovery(configuration);
		if (disco.checkConnectivity() != null) {
			disco.startDiscovery();
	
			Thread.sleep(10000);
			Home home = disco.getCurrentHome();
	
			if (home != null) {
	
				Map<String, WelcomeCamera> cameras = home.getCameras();
	
				for (WelcomeCamera camera : cameras.values()) {
					System.out.println(camera.getUseLocalUrl());
					// useLocalUrl must be true
					assertFalse(camera.getUseLocalUrl());
				}
	
			}
	
			disco.stopDiscovery();
		} else {
			printWarningMessage();
		}

	}
	
	private void printWarningMessage() {
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("#                                                                              #");
		System.out.println("# Please configure properly src/test/resources/netatmo.sdt.driver.properties   #");
		System.out.println("#                                                                              #");
		System.out.println("--------------------------------------------------------------------------------");
	}
}
