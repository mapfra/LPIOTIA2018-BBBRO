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
 * for "effect" field of Hue light state, created in order to represent an enumeration in java 1.4<p>
 * enum HueLightEffect with 2 values: <p>
 * 1. "colorloop" cycle through all hues using the current bri and sat <p>
 * 2. "none" no effect
 */
public class LightEffect {
	
	public static int NONE = 0;
	
	/**
	 * cycle through all hues using the current bri and sat
	 */
	public static int COLORLOOP = 1;
	
	private static final String[] values = {
			"none", "colorloop"
	};
	
	/** 
	 *  0 = "none" <p>
	 *  1 = "colorloop" <p>
	 * @param index use {@link LightEffect} static attributes
	 * @return the string corresponding to the light effect possible values, "none" if no match
	 */
	public static String getLightEffect(final int index) {
		return ((index < 0) || (index >= values.length)) ? values[NONE] : values[index];
	}
	
	/** 
	 *  0 = "none" <p>
	 *  1 = "colorloop" <p>
	 * @param s string
	 * @return the int corresponding to the light effect possible values, 0 ("none") if no match
	 */
	public static int getLightEffect(final String s) {
		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(s))
				return i;
		}
		return NONE;
	}

}
