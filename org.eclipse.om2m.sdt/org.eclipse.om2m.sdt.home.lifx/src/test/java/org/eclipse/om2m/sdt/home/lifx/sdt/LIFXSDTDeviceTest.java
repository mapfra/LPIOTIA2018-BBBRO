/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.lifx.sdt;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.eclipse.om2m.sdt.home.HomeDomain;
import org.eclipse.om2m.sdt.home.lifx.impl.cloud.DiscoveryCloud;
import org.eclipse.om2m.sdt.home.lifx.impl.cloud.LIFXDeviceCloud;

import junit.framework.TestCase;

public class LIFXSDTDeviceTest extends TestCase {

	public void testNewDevice() throws Exception {
		
		Properties properties =  new Properties();
		properties.load(new FileInputStream("src/test/resources/lifx.basedriver.properties"));
		
		HomeDomain homeDomain = new HomeDomain("LIFXDomain");
		
		DiscoveryCloud discoveryCloud = new DiscoveryCloud(properties.getProperty("token.cloud"));
		discoveryCloud.startDiscoveryTask();
		
		Thread.sleep(2000);
		List<LIFXDeviceCloud> devices = discoveryCloud.getLIFXDeviceCloud();
		
		if ((devices != null) && (!devices.isEmpty())) {
			LIFXSDTDevice lifxDevice = new LIFXSDTDevice(homeDomain, devices.get(0));
			lifxDevice.getBinarySwitch().setPowerState(false);
			
			Thread.sleep(1500);
			lifxDevice.getBinarySwitch().setPowerState(true);
			
			// tout bleu
			lifxDevice.getColour().setRed(0);
			lifxDevice.getColour().setGreen(0);
			lifxDevice.getColour().setBlue(255);
			
			Thread.sleep(1000);
			
			// tout rouge
			lifxDevice.getColour().setRed(255);
			lifxDevice.getColour().setGreen(0);
			lifxDevice.getColour().setBlue(0);
			
			// tout vert
			lifxDevice.getColour().setRed(0);
			lifxDevice.getColour().setGreen(255);
			lifxDevice.getColour().setBlue(0);
		}
		
	}
}
