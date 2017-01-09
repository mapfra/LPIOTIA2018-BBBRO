/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import junit.framework.TestCase;

public class IPTest extends TestCase {
	
	public void test() throws SocketException {

		for(Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements();) {
			NetworkInterface ni = e.nextElement();
			System.out.println("ni.name=" + ni.getName());
			for(InterfaceAddress ia :ni.getInterfaceAddresses()) {
				System.out.println("\t" + ia.getAddress());
			}
		}
	}

}
