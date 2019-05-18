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
package org.eclipse.om2m.hue.impl.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.om2m.hue.api.types.HueException;
import org.eclipse.om2m.hue.api.types.LightState;
import org.eclipse.om2m.hue.api.types.UnknownHueGatewayException;
import org.json.simple.JSONObject;

public class LightGroup extends PHDevice {

	/**
	 * list of Hue lights of this group
	 * contains {@link Light} objects
	 */
	private List<Light> lights;
	
	// ====================================================================================

	/**
	 * @param IP Hue gateway IP address
	 * @param usr Hue gateway authorized user
	 * @param id group id
	 * @param name group name
	 */
	public LightGroup(final String IP, final String usr, final String id, final String name) {
		super(IP, usr, id, name);
		this.lights = new ArrayList<Light>();
	}

	// ====================================================================================
	
	/**
	 * @param l {@link Light} object 
	 */
	public void addLight(final Light l) {
		lights.add(l);
	}
	
	/**
	 * @param l {@link List} of {@link Light} objects belonging to the group
	 */
	public void setLightList(final List<Light> l) {
		this.lights = l;
	}

	/**
	 * @param l {@link Light} object 
	 */
	public void removeLight(final Light l) {
		lights.remove(l);
	}

	/**
	 * @param id {@link String} of light to remove 
	 */
	public void removeLightById(final String id) {
		for (Iterator<Light> it = lights.iterator(); it.hasNext(); ) {
			Light l = it.next();
			if (l.getId().equals(id)) {
				it.remove();
				return;
			}
		}
	}

	/**
	 * @param name {@link String} of light to remove 
	 */
	public void removeLightByName(final String name) {
		for (Iterator<Light> it = lights.iterator(); it.hasNext(); ) {
			Light l = it.next();
			if (l.getName().equals(name)) {
				it.remove();
				return;
			}
		}
	}

	/**
	 * @return list of {@link Light} objects in the group
	 */
	public List<Light> getLights() {
		return lights;
	}

	/**
	 * @param id 
	 * @return {@link Light} object with id from group, null if not existent
	 * @throws HueException 
	 */
	public Light getLightById(final String id) throws HueException {
		for (Light l : lights) {
			if (l.getId().equals(id)) {
				return l;
			}
		}
		throw new HueException("Not found Light element for id : " + id);
	}

	/**
	 * DO NOT USE! {@link Light} objects contained do not have names
	 * will be available in a future version
	 * @param name 
	 * @return {@link Light} object with name from group, null if not existent
	 * @throws HueException 
	 */
	public Light getLightByName(final String name) throws HueException {
		for (Light l : lights) {
			if (l.getName().equals(name)) {
				return l;
			}
		}
		throw new HueException("Not found Light element for name : " + name);
	}

	/**
	 * @param s state {@link LightState} to set to group
	 * @throws HueException signaling REST resource path incorrect
	 * @throws UnknownHueGatewayException signaling that maybe the IP address of the gateway has changed
	 */
	public void setState(final LightState ls) throws HueException, UnknownHueGatewayException {
		// create JSON format object to send with PUT request
		JSONObject state = setStateObject(ls);
		// make PUT request, get JSON format string response
		String resp = sendPutRequest(GROUPS + "/" + id + "/" + ACTION, state.toString());
		if (resp.substring(3, 8).equals(ERROR)) {
			throw new HueException("ERROR while setting group light state for group : " 
								+ name + ", id : " + id + "\n\t" + resp);
		}
	}

	/**
	 * @return last state {@link LightState} command of the group
	 * @throws HueException signaling REST resource path incorrect
	 * @throws UnknownHueGatewayException 
	 */
	public LightState getLastStateAction() throws HueException, UnknownHueGatewayException {
		// make GET request, get JSON format string response
		String resp = sendGetRequest(GROUPS + "/" + id);
		if (resp.substring(3, 8).equals(ERROR)) {
			throw new HueException("ERROR while geting last group state action for group : " 
					+ name + ", id : " + id + "\n\t" + resp);
		}
		return getState(resp, ACTION);
	}

	public String toString() {
		String s = "<LightGroup id=" + id + " name=" + name + " gateway_IP=" + IP 
				+ " user_name=" + userName + ">";
		for (Light l : lights) {
			s = s + "\n\t" + l;
		}
		return s + "</LightGroup>";
	}

}
