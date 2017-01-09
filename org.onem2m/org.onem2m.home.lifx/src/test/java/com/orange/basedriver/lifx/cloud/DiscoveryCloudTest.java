/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx.cloud;

import java.util.List;

import com.orange.basedriver.lifx.impl.cloud.DiscoveryCloud;
import com.orange.basedriver.lifx.impl.cloud.LIFXDeviceCloud;

import junit.framework.TestCase;

public class DiscoveryCloudTest extends TestCase {

	public void testDiscovery() throws Exception {
		DiscoveryCloud discoveryCloud = new DiscoveryCloud();
		
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
