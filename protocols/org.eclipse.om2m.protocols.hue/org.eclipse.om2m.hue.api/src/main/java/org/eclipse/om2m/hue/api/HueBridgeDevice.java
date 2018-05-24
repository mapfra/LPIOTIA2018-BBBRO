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
*******************************************************************************/
package org.eclipse.om2m.hue.api;

import java.util.List;

import org.eclipse.om2m.hue.api.types.HueException;
import org.eclipse.om2m.hue.api.types.UnknownHueGatewayException;

/**
 * Hue Bridge device.
 */
public interface HueBridgeDevice extends HueDevice {

	/**
	 * UDN address of the Hue Bridge
	 * @return
	 */
	String getUDN();

	/**
	 * Get the list of Hue Light devices associated with the bridge
	 * @return a list of {@link HueLightDevice} objects (can be empty but not null)
	 * @throws HueException
	 * @throws UnknownHueGatewayException
	 */
	List<HueLightDevice> getLights() throws HueException, UnknownHueGatewayException;

	/**
	 * set WakeUp mode in group
	 * turns on the light, white color, medium brightness
	 * @param group use {@link Group} class for values
	 * @throws HueException 
	 */
	void setWakeUp(int group) throws HueException;

	/**
	 * set Meal mode in group
	 * turns on the light, light orange color, medium brightness
	 * @param group use {@link Group} class for values
	 * @throws HueException 
	 */
	void setMeal(int group) throws HueException;

	/**
	 * set Night mode in group
	 * turns off the light
	 * @param group use {@link Group} class for values
	 * @throws HueException 
	 */
	void setNight(int group) throws HueException;

	/**
	 * set HomeCinema mode in group
	 * turns on the light, red color, low brightness
	 * @param group use {@link Group} class for values
	 * @throws HueException 
	 */
	void setHomeCinema(int group) throws HueException;

	/**
	 * set HomeCinema mode in group
	 * turns on the light, colorloop effect on the lights, low brightness
	 * @param group use {@link Group} class for values
	 * @throws HueException 
	 */
	void setParty(int group) throws HueException;

	/**
	 * set On/Off : turn on/off the light 
	 * used when turning on/off a mode (Meal, Party....)
	 * turns on the light (white color, maximum brightness) or off
	 * @param group use {@link Group} class for values
	 * @param on
	 * @throws HueException 
	 */
	void setOnOff(int group, boolean on) throws HueException;

}
