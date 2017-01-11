/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.netatmo.impl;

import java.io.FileInputStream;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;

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
		configuration.put(Discovery.CONFIG_WEATHER_STATION_SAMPLING, properties.getProperty(Discovery.CONFIG_WEATHER_STATION_SAMPLING));
		configuration.put(Discovery.CONFIG_WELCOME_CAMERA_SAMPLING, properties.getProperty(Discovery.CONFIG_WELCOME_CAMERA_SAMPLING));
		
	}
	
	public void testDiscovery() throws Exception {
		Discovery disco = new Discovery(configuration);
		disco.startDiscovery();
		
		
		Thread.sleep(40000);
		
		disco.stopDiscovery();
		
		Thread.sleep(10000);
	}
}
