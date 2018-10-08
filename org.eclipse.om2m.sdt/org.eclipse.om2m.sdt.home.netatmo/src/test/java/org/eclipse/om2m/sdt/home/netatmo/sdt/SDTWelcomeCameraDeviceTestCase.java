/*******************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.netatmo.sdt;

import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.home.netatmo.impl.Activator;
import org.eclipse.om2m.sdt.home.netatmo.model.WelcomeCamera;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import junit.framework.TestCase;

public class SDTWelcomeCameraDeviceTestCase extends TestCase {
	
	private static final String PID = "fake.pid";
	private static final String TYPE = "fake.type";
	private static final String NAME = "fake.name";
	private static final String VPN_URL = "http://fake.url.com";
	
	private String currentPid;
	private String currentName;
	private WelcomeCamera welcomeCamera;
	private SDTWelcomeCameraDevice sdtWelcomeCamera;
	
	protected void setUp() throws Exception {
		
		currentPid = PID + "_" + System.currentTimeMillis();
		currentName = NAME + "_" + System.currentTimeMillis();
		welcomeCamera = new WelcomeCamera(currentPid, TYPE, currentName);
		welcomeCamera.setVpnUrl(VPN_URL);
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		for(Module module : sdtWelcomeCamera.getModules()) {
			Activator.NETATMO_DOMAIN.removeModule(module.getName());
		}
		
		Activator.NETATMO_DOMAIN.removeDevice(sdtWelcomeCamera.getName());
		
	}

	
	public void testLocalURL() throws Exception {
		
		welcomeCamera.setUseLocalUrl(Boolean.TRUE);
		sdtWelcomeCamera = new SDTWelcomeCameraDevice(welcomeCamera, 1000);

		assertNotNull(sdtWelcomeCamera.getSessionDescription().getUrl());
		assertTrue(sdtWelcomeCamera.getSessionDescription().getUrl().toString().equals(VPN_URL + "/live/index_local.m3u8"));
		
	}
	
	public void testRemoteUrl() throws Exception {
		welcomeCamera.setUseLocalUrl(Boolean.FALSE);
		sdtWelcomeCamera = new SDTWelcomeCameraDevice(welcomeCamera, 1000);

		assertNotNull(sdtWelcomeCamera.getSessionDescription().getUrl());
		assertTrue(sdtWelcomeCamera.getSessionDescription().getUrl().toString().equals(VPN_URL + "/live/index.m3u8"));
		
	}
	
//	public void testPassword() throws Exception {
//		sdtWelcomeCamera = new SDTWelcomeCameraDevice(welcomeCamera, 1000);
//		assertEquals(sdtWelcomeCamera.getSessionDescription().getPassword(), "");
//	}
//	
//	public void testLogin() throws Exception {
//		sdtWelcomeCamera = new SDTWelcomeCameraDevice(welcomeCamera, 1000);
//		assertEquals(sdtWelcomeCamera.getSessionDescription().getLogin(), "");
//	}
	
	public void testFormat() throws Exception {
		sdtWelcomeCamera = new SDTWelcomeCameraDevice(welcomeCamera, 1000);
		assertEquals(sdtWelcomeCamera.getSessionDescription().getSdp(), "HLS");
	}
}
