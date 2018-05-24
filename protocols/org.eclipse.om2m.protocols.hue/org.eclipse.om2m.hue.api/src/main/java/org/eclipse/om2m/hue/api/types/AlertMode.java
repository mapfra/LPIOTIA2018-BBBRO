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
package org.eclipse.om2m.hue.api.types;

/**
 * for "alert" field of Hue light state, created in order to represent an enumeration in java 1.4<p>
 * enum AlertMode with 3 possible values : <p>
 * 1. "select" the light is performing breathe cycles every 30s until "none" is set <p>
 * 2. "lselect" the light is performing one breathe cycle <p>
 * 3. "none" the light is not performing any alert effect <p>
 */
public class AlertMode {
	
	public static int NONE = 0;
	
	/**
	 * the light is performing one breathe cycle
	 */
	public static int L_SELECT = 1;

	/**
	 * the light is performing breathe cycles every 30s until "none" is set
	 */
	public static int SELECT = 2;
	
	private static final String[] values = {
			"none", "lselect", "select"
	};

	// ====================================================================================
	
	/**
	 *  0 = "none" <p>
	 *  1 = "lselect" <p>
	 *  2 = "select" <p>
	 * @param index use {@link AlertMode} static attributes
	 * @return the string corresponding to the alert mode possible values, "none" if no match 
	 */
	public static final String getAlertMode(final int index) {
		return ((index < 0) || (index >= values.length)) ? values[NONE] : values[index];
	}
	
	/**
	 *  0 = "none" <p>
	 *  1 = "lselect" <p>
	 *  2 = "select" <p>
	 * @param s string 
	 * @return the int corresponding to the alert mode possible values, 0 ("none") if no match
	 */
	public static int getAlertMode(final String s) {
		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(s))
				return i;
		}
		return NONE;
	}

}
