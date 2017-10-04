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
package org.eclipse.om2m.ipe.sample.sdt.monitor;

import org.eclipse.om2m.ipe.sample.sdt.controller.SampleController;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;

/**
 * This class is simply to show the architecture and to reflect the action
 * from the real devices. Here we simulate the reception of the switch signal.
 *
 */
public class SampleMonitor {
	
	/**
	 * Switch on or off a specific lamp
	 * @param lampId
	 * @throws AccessException  
	 * @throws DataPointException 
	 */
	public static void switchLamp(String lampId) throws DataPointException, AccessException {
		SampleController.toggleLamp(lampId);
	}
	
	/**
	 * Toggle all lamps 
	 */
	public static void switchAll() throws DataPointException, AccessException {
		SampleController.toogleAll();
	}

	/**
	 * Set a color to all lamps
	 * @param red value for RED in RGB format (0-255) 
	 * @param green value for GREEN in RGB format (0-255) 
	 * @param blue value for BLUE in RGB format (0-255) 
	 */
	public static void setColor(int red, int green, int blue) throws DataPointException, AccessException {
		SampleController.setColor(red, green, blue);
	}

}
