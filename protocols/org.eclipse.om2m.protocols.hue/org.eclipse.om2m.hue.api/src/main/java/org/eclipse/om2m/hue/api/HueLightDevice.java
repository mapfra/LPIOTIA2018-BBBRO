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

import org.eclipse.om2m.hue.api.types.AlertMode;
import org.eclipse.om2m.hue.api.types.LightEffect;
import org.eclipse.om2m.hue.api.types.LightState;

/**
 * Hue Light device.
 */
public interface HueLightDevice extends HueDevice {

	/**
	 * The ID of the light (a number in the group)
	 * @return
	 */
	String getId();

	/**
	 * The name of the light
	 * @return
	 */
	String getName();

	/**
	 * Indicate if the light is reachable
	 * @return
	 */
	boolean isReachable();

	/**
	 * Get the state of the Hue Light device
	 * @return state see {@link LightState}
	 */
	LightState getState();

	/**
	 * Update the state of the Hue Light device
	 * @param state see {@link LightState}
	 */
	void setState(LightState state);

	/**
	 * Update the state of the Hue Light device see {@link LightState}
	 * @param on switch on/off the light
	 * @param brightness brightness from 0 to 255
	 * @param saturation saturation from 0 to 255 (0 : white, 255 : most saturated, colored)
	 * @param hue hue value of the light from 0 to 65535 (color)
	 * @param effect see {@link LightEffect}
	 * @param alert see {@link AlertMode}
	 */
	void setState(boolean on, int brightness, int saturation, int hue, int effect, int alert);

	/**
	 * Return true is the light is On.
	 * returning false either means that it's off or 
	 * that the current state of the light is unknown
	 * @return
	 */
	boolean isOn();

}
