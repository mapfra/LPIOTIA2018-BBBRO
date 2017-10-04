/*******************************************************************************
 * Copyright (c) 2013-2016 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Thierry Monteil : Project manager, technical co-manager
 *     Mahdi Ben Alaya : Technical co-manager
 *     Samir Medjiah : Technical co-manager
 *     Khalil Drira : Strategy expert
 *     Guillaume Garzone : Developer
 *     François Aïssaoui : Developer
 *
 * New contributors :
 *******************************************************************************/
package org.eclipse.om2m.ipe.sample.sdt.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.exceptions.ModuleException;
import org.eclipse.om2m.sdt.home.devices.Light;
import org.eclipse.om2m.sdt.home.modules.Colour;

public class SampleModel {
	
    private static Log logger = LogFactory.getLog(SampleModel.class);
    
	private static Map<String,Light> LAMPS = new HashMap<String, Light>();
	
	private SampleModel() {
	}
	
	/**
	 * Sets the lamp state.
	 * @param lampId - Application ID
	 * @param value - measured state
	 * @throws AccessException 
	 * @throws DataPointException 
	 */
	public static void setLampState(final String lampId, boolean value) throws DataPointException, AccessException {
		Light light = getLamp(lampId);
		try {
			light.getBinarySwitch().setPowerState(value);
		} catch (ModuleException e) {
			throw new DataPointException(e);
		}
	}
	
	/**
	 * Gets the direct current lamp state
	 * @param lampId
	 * @return the direct current lamp state
	 * @throws AccessException 
	 * @throws DataPointException 
	 */
	public static boolean getLampValue(String lampId) throws DataPointException, AccessException {
		try {
			return getLamp(lampId).getBinarySwitch().getPowerState();
		} catch (ModuleException e) {
			throw new DataPointException(e);
		}
	}

	public static void setColor(int red, int green, int blue) throws DataPointException, AccessException {
		logger.info("Set RGB " + red + " " + green + " " + blue);
		for (Light light : LAMPS.values()) {
			Colour color = light.getColour();
			color.setRed(red);
			color.setGreen(green);
			color.setBlue(blue);
		}
	}

	public static void setColor(String id, int red, int green, int blue) throws DataPointException, AccessException {
		logger.info("Set RGB " + red + " " + green + " " + blue);
		Light light = getLamp(id);
		Colour color = light.getColour();
		color.setRed(red);
		color.setGreen(green);
		color.setBlue(blue);
	}
	
	/**
	 * Gets the direct current lamp
	 * @param lampId
	 * @return the direct current lamp
	 * @throws AccessException 
	 * @throws DataPointException 
	 */
	public static Light getLamp(String lampId) {
		Light ret = LAMPS.get(lampId);
		if (ret == null) {
			throw new IllegalAccessError("Unknow lamp id");
		}
		return ret;
	}

	public static void setModel(Map<String, Light> lamps) {
		LAMPS = lamps;
	}

}
