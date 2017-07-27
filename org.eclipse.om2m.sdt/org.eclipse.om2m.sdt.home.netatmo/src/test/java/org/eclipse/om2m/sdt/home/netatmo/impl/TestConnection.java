/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.netatmo.impl;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.eclipse.om2m.sdt.home.netatmo.model.WeatherStation;

import junit.framework.TestCase;

public class TestConnection extends TestCase {

	private Properties properties;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		properties = new Properties();
		properties.load(new FileInputStream("src/test/resources/netatmo.sdt.driver.properties"));
	}

	public void testConnection() throws Exception {
		Server server = new Server(properties.getProperty(Discovery.CONFIG_CLIENT_ID),
				properties.getProperty(Discovery.CONFIG_CLIENT_SECRET),
				properties.getProperty(Discovery.CONFIG_USERNAME), properties.getProperty(Discovery.CONFIG_PASSWORD));
		
		if (server.getToken() != null) {
			server.getHomeData(null, null);
		} else {
			printWarningMessage();
		}
	}

	public void testWeatherStationConnection() throws Exception {
		Server server = new Server(properties.getProperty(Discovery.CONFIG_CLIENT_ID),
				properties.getProperty(Discovery.CONFIG_CLIENT_SECRET),
				properties.getProperty(Discovery.CONFIG_USERNAME), properties.getProperty(Discovery.CONFIG_PASSWORD));
		
		if (server.getToken() != null) {
		
			List<WeatherStation> wss = server.getStationsData();
	
			for (WeatherStation ws : wss) {
				System.out.println(ws.toString());
			}
			
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
