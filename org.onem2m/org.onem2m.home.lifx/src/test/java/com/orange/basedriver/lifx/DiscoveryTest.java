/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx;

import java.net.InetAddress;
import java.util.Map;

import com.orange.basedriver.lifx.impl.lan.DiscoveryLan;
import com.orange.basedriver.lifx.impl.lan.LIFXDeviceLan;
import com.orange.basedriver.lifx.impl.lan.Server;

import junit.framework.TestCase;

public class DiscoveryTest extends TestCase {

	public void testLaunchDiscovery() throws Exception {

		// try {
		// for (Enumeration<NetworkInterface> eni =
		// NetworkInterface.getNetworkInterfaces(); eni.hasMoreElements();) {
		// NetworkInterface ni = eni.nextElement();
		// List<InterfaceAddress> interfaceAddresses =
		// ni.getInterfaceAddresses();
		// System.out.println("ni.name=" + ni.getName());
		// for (InterfaceAddress ia : interfaceAddresses) {
		// System.out.println("\t hostAddress=" +
		// ia.getAddress().getHostAddress());
		// System.out.println("\t broadcastAddress=" + ia.getBroadcast());
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		Server.getInstance().init(InetAddress.getByAddress(new byte[] { (byte) 127, (byte)0, 0, 1 }));
		Server.getInstance().startServer();

		DiscoveryLan discovery = new DiscoveryLan();
		discovery.launchDiscovery();

		Thread.sleep(3000);
		Map<String, LIFXDeviceLan> devices = discovery.getLIFXDevices();
		System.out.println("found " + devices.size() + " LIFX Devices ");

		if (!devices.isEmpty()) {
			LIFXDeviceLan device = devices.values().iterator().next();

			if (device != null) {

				try {

					device.executeGetLightMessage();
					
					System.out.println("device.hue" + device.getHue());
					System.out.println("device.brightness" + device.getBrightness());
					System.out.println("device.saturation" + device.getSaturation());
					System.out.println("device.kelvin" + device.getKelvin());

					// device.executeGetLightMessage();

					// device.executeGetPowerMessage();
					// System.out.println("power=" + device.getPower());

					device.executeSetColorMessage(551, 54000, 65535, 3500, 1000);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}

		System.out.println("test ending");

		Server.getInstance().stopServer();

	}
}
