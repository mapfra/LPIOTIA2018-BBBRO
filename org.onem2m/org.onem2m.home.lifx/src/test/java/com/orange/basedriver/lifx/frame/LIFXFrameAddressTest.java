/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx.frame;

import java.net.InetAddress;
import java.net.NetworkInterface;

import com.orange.basedriver.lifx.impl.lan.frame.LIFXFrameAddress;

import junit.framework.TestCase;

public class LIFXFrameAddressTest extends TestCase {

	public void testNewFrame() throws Exception {
		LIFXFrameAddress frameAddress = new LIFXFrameAddress();
		NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
		if (ni != null) {
			byte[] pcMacAddress = ni.getHardwareAddress();

			frameAddress.setTarget(pcMacAddress);
			byte[] frameAddressByte = frameAddress.getBytes();

			assertTrue(frameAddressByte.length == 16);
			for (int i = 0; i < 6; i++) {
				assertTrue(frameAddressByte[i] == pcMacAddress[i]);
			}
			assertTrue(frameAddressByte[6] == 0);
			assertTrue(frameAddressByte[7] == 0);
		}
	}
}
