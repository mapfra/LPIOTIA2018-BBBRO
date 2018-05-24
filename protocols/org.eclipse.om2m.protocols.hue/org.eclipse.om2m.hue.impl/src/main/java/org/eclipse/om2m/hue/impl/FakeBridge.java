/*******************************************************************************
* Copyright (c) 2014-2018 Orange.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    BAREAU Cyrille <cyrille.bareau@orange.com>
*    BONNARDEL Gregory <gbonnardel.ext@orange.com>
*    OSKO Tomasz <tomasz.osko@orange.com>
*    RATUSZEK Przemyslaw <przemyslaw.ratuszek@orange.com>
*******************************************************************************/
package org.eclipse.om2m.hue.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.hue.api.HueBridgeDevice;
import org.eclipse.om2m.hue.api.HueLightDevice;
import org.eclipse.om2m.hue.api.types.HueException;
import org.eclipse.om2m.hue.api.types.LightState;
import org.eclipse.om2m.hue.api.types.UnknownHueGatewayException;
import org.eclipse.om2m.hue.impl.controller.HueConstants;
import org.eclipse.om2m.hue.impl.controller.Light;

public class FakeBridge extends HueDeviceImpl implements HueBridgeDevice {

    private static Log Logger = LogFactory.getLog(FakeBridge.class);
	
	private List<HueLightDevice> lights;

	@SuppressWarnings("unchecked")
	public FakeBridge(String xml_description) {
		super(xml_description);
		String id = "" + System.currentTimeMillis();
		properties.put(org.osgi.framework.Constants.SERVICE_PID, "fake_bridge_pid_" + id);
		properties.put(org.osgi.service.device.Constants.DEVICE_SERIAL, "fake_serial_" + id);
		properties.put(org.osgi.service.device.Constants.DEVICE_DESCRIPTION, "Fake Hue bridge");
		properties.put(HueConstants.OSGI_DEVICE_MANUFACTURER, "Orange");
		properties.put(HueConstants.OSGI_DEVICE_FRIENDLY_NAME, "Fake Hue bridge");
		lights = new ArrayList<HueLightDevice>();
		lights.add(createFakeLight(1));
		lights.add(createFakeLight(2));
		lights.add(createFakeLight(3));
		lights.add(createFakeLight(4));
	}

	public String getUDN() {
		return "_udn_";
	}

	public List<HueLightDevice> getLights() throws HueException, UnknownHueGatewayException {
		return lights;
	}

	public void setWakeUp(int group) throws HueException {
	}

	public void setMeal(int group) throws HueException {
	}

	public void setNight(int group) throws HueException {
	}

	public void setHomeCinema(int group) throws HueException {
	}

	public void setParty(int group) throws HueException {
	}

	public void setOn(int group) throws HueException {
	}

	public void setOff(int group) throws HueException {
	}

	public void setOnOff(int group, boolean on) throws HueException {
		if (on) setOn(group);
		else setOff(group);
	}

	private HueLightDevice createFakeLight(final int i) {
		return new HueLightDeviceImpl(this, new FakeLight("" + i));
	}
	
	private class FakeLight extends Light {
		private LightState state;
		FakeLight(String id) {
			super("", "foo", id);
		}
		public String getName() {
			return getId();
		}
		public String getType() {
			return "type";
		}
		public boolean isReachable() {
			return true;
		}
		public LightState getState() throws HueException, UnknownHueGatewayException {	
			Logger.info("get " + state + " on " + getName());
			return state;
		}
		public void setState(final LightState ls) throws HueException, UnknownHueGatewayException {
			Logger.info("set " + ls + " on " + getName());
			this.state = ls;
		}
	}

}
