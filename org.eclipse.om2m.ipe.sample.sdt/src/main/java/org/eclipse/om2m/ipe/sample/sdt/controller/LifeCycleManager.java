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
package org.eclipse.om2m.ipe.sample.sdt.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.ipe.sample.sdt.constants.SampleConstants;
import org.eclipse.om2m.ipe.sample.sdt.gui.GUI;
import org.eclipse.om2m.ipe.sample.sdt.model.Lamp;
import org.eclipse.om2m.ipe.sample.sdt.model.SampleModel;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.home.devices.Light;
import org.osgi.framework.BundleContext;

public class LifeCycleManager {

	private static Log LOGGER = LogFactory.getLog(LifeCycleManager.class); 

	/**
	 * Handle the start of the plugin with the resource representation and the GUI
	 * @param bundleContext 
	 */
	public static void start(BundleContext bundleContext) {
		Domain domain = new Domain("SAMPLE");
		Map<String, Light> lights = new HashMap<String, Light>();
		for (int i = 0; i < 2; i++) {
			String lampId = "LAMP_" + i;
			lights.put(lampId, new Lamp(lampId, "SERIAL_" + lampId, domain, bundleContext));
		}
		SampleModel.setModel(lights);

		// Start the GUI
		if (SampleConstants.GUI) {
			GUI.init(bundleContext);
		}
	}

	/**
	 * Stop the GUI if it is present
	 */
	public static void stop(){
		if (SampleConstants.GUI) {
			GUI.stop();
		}
	}

}
