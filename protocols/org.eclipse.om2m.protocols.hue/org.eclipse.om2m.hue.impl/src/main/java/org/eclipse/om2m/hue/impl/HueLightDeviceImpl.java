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
*    BORAWSKI Pawel <pawel.borawski@orange.com>
*    RATUSZEK Przemyslaw <przemyslaw.ratuszek@orange.com>
*    WIERZBOWSKI Jacek <jacek.wierzbowski@orange.com>
*******************************************************************************/
package org.eclipse.om2m.hue.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.hue.api.HueBridgeDevice;
import org.eclipse.om2m.hue.api.HueLightDevice;
import org.eclipse.om2m.hue.api.types.LightState;
import org.eclipse.om2m.hue.impl.controller.HueConstants;
import org.eclipse.om2m.hue.impl.controller.Light;
import org.osgi.service.upnp.UPnPDevice;

/**
 * Implementation of the {@link HueLightDevice}
 */
public class HueLightDeviceImpl extends HueDeviceImpl implements HueLightDevice {
	
    private static Log Logger = LogFactory.getLog(HueLightDeviceImpl.class);
	private Light light;
	private HueBridgeDevice bridge;
	
	@SuppressWarnings("unchecked")
	public HueLightDeviceImpl(final HueBridgeDevice bridge, final Light light) {
		super(bridge.getXmlDescription());
		
		this.light = light;
		this.bridge = bridge;
			
		properties.put(org.osgi.framework.Constants.SERVICE_PID,
			bridge.getProperties().get(org.osgi.framework.Constants.SERVICE_PID) + "_" + light.getId());

		properties.put(org.osgi.service.device.Constants.DEVICE_SERIAL,
				getProperties().get(UPnPDevice.SERIAL_NUMBER) + "_" + light.getId());
		properties.put(org.osgi.service.device.Constants.DEVICE_DESCRIPTION, light.getType());
		
		String manuf = (String) bridge.getProperties().get(HueConstants.OSGI_DEVICE_MANUFACTURER);
		properties.put(HueConstants.OSGI_DEVICE_MANUFACTURER, (manuf == null) ? "" : manuf);
		properties.put(HueConstants.OSGI_DEVICE_FRIENDLY_NAME, light.getName());
		
		properties.put("export.api.Read", "getId,getName,isReachable,isOn");
		properties.put("export.api.Action", "setState");
	}

	/* (non-Javadoc)
	 * @see com.orange.basedrivers.hue.HueLightDevice#getId()
	 */
	public String getId() {
		return this.light.getId();
	}

	/* (non-Javadoc)
	 * @see com.orange.basedrivers.hue.HueLightDevice#getName()
	 */
	public String getName() {
		return this.light.getName();
	}

	/* (non-Javadoc)
	 * @see com.orange.basedrivers.hue.HueLightDevice#isReachable()
	 */
	public boolean isReachable() {
		return this.light.isReachable();
	}

	/* (non-Javadoc)
	 * @see com.orange.basedrivers.hue.HueLightDevice#getState()
	 */
	public LightState getState() {
		try {
			return this.light.getState();
		} catch (Exception e) {
			Logger.warn("", e);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.orange.basedrivers.hue.HueLightDevice#setState(LightState)
	 */
	public void setState(final LightState state) {
		try {
			this.light.setState(state);
		} catch (Exception e) {
			Logger.warn("", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.orange.basedrivers.hue.HueLightDevice#setState(boolean, int, int, int, int, int)
	 */
	public void setState(final boolean on, final int brightness, final int saturation, 
			final int hue, final int effect, final int alert) {
		LightState state = new LightState(on);
		state.setAlert(alert);
		state.setBri(brightness);
		state.setEffect(effect);
		state.setHue(hue);
		state.setSat(saturation);
		setState(state);
	}

	/* (non-Javadoc)
	 * @see com.orange.basedrivers.hue.HueLightDevice#isOn()
	 */
	public boolean isOn() {
		try {
			return light.getState().isOn();
		} catch (Exception e) {
			return false;
		}
	}

	public HueBridgeDevice getBridge() {
		return bridge;
	}
	
}
