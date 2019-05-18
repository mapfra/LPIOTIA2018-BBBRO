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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.hue.api.types.AlertMode;
import org.eclipse.om2m.hue.api.types.HueException;
import org.eclipse.om2m.hue.api.types.LightEffect;
import org.eclipse.om2m.hue.api.types.LightState;
import org.eclipse.om2m.hue.api.types.UnknownHueGatewayException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Abstract parent class for {@link Light} and {@link LightGroup} objects.
 */
abstract public class PHDevice implements HueConstants {

	/**
     * Logger
     */
    private static Log Logger = LogFactory.getLog(PHDevice.class);
	
	/**
	 * IP address of Hue gateway
	 */
	protected String IP;

	/**
	 * authorized userName
	 * previously peered with Hue gateway
	 */
	protected String userName;

	/**
	 * id 
	 */
	protected String id;

	/**
	 * name 
	 */
	protected String name;
	
	// ====================================================================================

	/**
	 * @param IP Hue gateway IP address
	 * @param usr Hue gateway authorized user
	 * @param id group id
	 * @param name group name
	 */
	protected PHDevice(final String IP, final String usr, final String id, final String name) {
		this.IP = IP;
		this.userName = usr;
		this.id = id;
		this.name = name;
	}

	// ====================================================================================

	/**
	 * @param s state {@link LightState} to set to group
	 * @throws HueException signaling REST resource path incorrect
	 * @throws UnknownHueGatewayException signaling that maybe the IP address of the gateway has changed
	 */
	@SuppressWarnings("unchecked")
	protected JSONObject setStateObject(final LightState s) throws HueException, UnknownHueGatewayException {
		// create JSON format object to send with PUT request
		JSONObject obj = new JSONObject();
		if (s.getSat() != -1)
			obj.put(SAT, new Integer(s.getSat()));
		if (s.getBri() != -1)
			obj.put(BRI, new Integer(s.getBri()));
		if (s.getHue() != -1)
			obj.put(HUE, new Integer(s.getHue()));			
		obj.put(ALERT, AlertMode.getAlertMode(s.getAlert()));
		obj.put(EFFECT, LightEffect.getLightEffect(s.getEffect()));
		obj.put(ON, new Boolean(s.isOn()));
		return obj;
	}
	
	protected LightState getState(final String resp, final String param) {	
		// parse JSON format response in order to retrieve state parameters
		try {
			JSONObject responseObj = (JSONObject) new JSONParser().parse(resp);
			// we are interested only in state parameter
			JSONObject state = (JSONObject) responseObj.get(param); 

			// create LightState object to return
			LightState s = new LightState();
			s.setOn(((Boolean)state.get(ON)).booleanValue());
			s.setBri(Integer.parseInt(state.get(BRI).toString()));
			s.setSat(Integer.parseInt(state.get(SAT).toString()));
			s.setHue(Integer.parseInt(state.get(HUE).toString()));
			s.setAlert(AlertMode.getAlertMode((String)state.get(ALERT)));
			s.setEffect(LightEffect.getLightEffect((String)state.get(EFFECT)));
			return s;
		} catch (ParseException e) {
			Logger.warn("", e);
		}
		return null;
	}

	/**
	 * @return the IP address of Hue gateway
	 */
	public String getIP() {
		return IP;
	}

	/**
	 * @param iP the IP address of Hue gateway to set
	 */
	public void setIP(final String iP) {
		IP = iP;
	}

	/**
	 * @return the userName peered with Hue gateway
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName peered with Hue gateway to set
	 */
	public void setUserName(final String userName) {
		this.userName = userName;
	}

	/**
	 * @return the light's id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the light's id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the light's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the light's name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	protected final String sendGetRequest(final String req) throws HueException, UnknownHueGatewayException {
		return Utils.sendGetRequest(IP + "/api/" + userName, req);
	}

	protected final String sendPutRequest(final String req, final String val) throws HueException, UnknownHueGatewayException {
		return Utils.sendPutRequest(IP + "/api/" + userName, req, val);
	}

}
