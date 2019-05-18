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

import org.eclipse.om2m.ipe.sample.sdt.constants.SampleConstants;
import org.eclipse.om2m.ipe.sample.sdt.model.SampleModel;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;

public class SampleController {
	
	public static void setLampState(String lampId, boolean value) throws DataPointException, AccessException {
		SampleModel.setLampState(lampId, value);
	}
	
//	public static String getFormatedLampState(String lampId) throws DataPointException, AccessException {
//		return getStateRep(lampId, getLampState(lampId));
//	}

	public static boolean getLampState(String lampId) throws DataPointException, AccessException {
		return SampleModel.getLampValue(lampId);
	}
	
	public static void toggleLamp(String lampId) throws DataPointException, AccessException {
		boolean newState = !SampleModel.getLampValue(lampId);
		SampleModel.setLampState(lampId, newState);
	}
	
	public static void setAllOn() throws DataPointException, AccessException {
		SampleModel.setLampState(SampleConstants.LAMP_0, true);
		SampleModel.setLampState(SampleConstants.LAMP_1, true);
	}
	
	public static void setAllOff() throws DataPointException, AccessException {
		SampleModel.setLampState(SampleConstants.LAMP_0, false);
		SampleModel.setLampState(SampleConstants.LAMP_1, false);
	}
	
	public static void toogleAll() throws DataPointException, AccessException {
		boolean newState = !(SampleModel.getLampValue(SampleConstants.LAMP_0) 
				&& SampleModel.getLampValue(SampleConstants.LAMP_1));
		SampleModel.setLampState(SampleConstants.LAMP_0, newState);
		SampleModel.setLampState(SampleConstants.LAMP_1, newState);
	}

	public static void setColor(int red, int green, int blue) throws DataPointException, AccessException {
		SampleModel.setColor(red, green, blue);
	}
//
//	/**
//	 * Returns an obix XML representation describing the current state.
//	 * @param lampId - Application Id
//	 * @param value - current lamp state
//	 * @return Obix XML representation
//	 */
//	private static String getStateRep(String lampId, boolean value) {
//		// oBIX
//		Light light = SampleModel.getLamp(lampId);
//		Obj obj = new Obj();
//		obj.add(new Str("type", light.getDefinition()));
//		obj.add(new Str("location", light.getLocation()));
//		obj.add(new Str("lampId", light.getId()));
//		obj.add(new Bool("state", value));
//		return ObixEncoder.toString(obj);
//	}

}
