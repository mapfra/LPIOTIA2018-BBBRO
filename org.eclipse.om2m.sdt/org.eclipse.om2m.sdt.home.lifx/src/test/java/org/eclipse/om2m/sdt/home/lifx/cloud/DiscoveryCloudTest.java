/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.lifx.cloud;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.eclipse.om2m.sdt.home.lifx.impl.cloud.DiscoveryCloud;
import org.eclipse.om2m.sdt.home.lifx.impl.cloud.LIFXDeviceCloud;

import junit.framework.TestCase;

public class DiscoveryCloudTest extends TestCase {

	public void testDiscovery() throws Exception {
		Properties properties =  new Properties();
		properties.load(new FileInputStream("src/test/resources/lifx.basedriver.properties"));
		
		DiscoveryCloud discoveryCloud = new DiscoveryCloud(properties.getProperty("cloud.token"));
		
		discoveryCloud.retrieveLIFXDevices();
		
		List<LIFXDeviceCloud> devices = discoveryCloud.getLIFXDeviceCloud();
		System.out.println("nb of devices=" + devices.size());
		
		if ((devices != null) && (!devices.isEmpty())) {
			LIFXDeviceCloud device = devices.get(0);
			System.out.println(device.toString());
			
			device.setPower(65535, 1000);
			System.out.println(device.toString());
			
			System.out.println(device.getPower());
			
			device.setHue(120.026779583428702, 1000);
			System.out.println(device.getHue());
			
			device.setBrightness(12500, 0);
			Thread.sleep(1000);
			System.out.println(device.getBrightness());
//			
//			device.setPower(0, 1000);
//			System.out.println(device.toString());
		}
	}
	
}
