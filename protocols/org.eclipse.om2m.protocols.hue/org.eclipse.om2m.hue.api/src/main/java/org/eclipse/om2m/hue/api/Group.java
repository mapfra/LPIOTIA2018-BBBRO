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

/**
 */
public class Group {
	
	/**
	 * the string corresponding to the groups possible values
	 *  0 = "all"
	 *  1 = "livingroom"
	 *  2 = "kitchen"
	 *  3 = "bedroom"
	 */
	public static final int ALL = 0;
	public static final int LIVINGROOM = 1;
	public static final int KITCHEN = 2;
	public static final int BEDROOM = 3;
	
	private static final String[] values = 
			new String[] { "all", "livingroom", "kitchen", "bedroom" };
	
	/**
	 * returns the string corresponding to the group possible values
	 * @param index
	 * @return
	 */
	public static final String getGroup(final int index) {
		return ((index < 0) || (index >= values.length)) ? null : values[index];
	}
	
	/**
	 * returns the int corresponding to the group possible values
	 * @param s
	 * @return
	 */
	public static final int getGroup(final String s) {
		for (int i = 0; i < values.length; i++) {
			if (s.equalsIgnoreCase(values[i])) {
				return i;
			}
		}
		return -1;
	}
	
}
