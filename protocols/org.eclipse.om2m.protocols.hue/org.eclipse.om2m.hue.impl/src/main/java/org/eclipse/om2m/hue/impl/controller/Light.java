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
import org.eclipse.om2m.hue.api.types.HueException;
import org.eclipse.om2m.hue.api.types.LightState;
import org.eclipse.om2m.hue.api.types.UnknownHueGatewayException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Represents a light bulb associated to the Hue gateway
 * uses a {@link Utils} tool in order to command Hue gateway and change the light's state 
 */
public class Light extends PHDevice {

	/**
     * Logger
     */
    private static Log Logger = LogFactory.getLog(Light.class);
    
	/**
	 * light type 
	 */
	private String type;

	/**
	 * light modelId 
	 */
	private String modelId;

	/**
	 * light softwareVersion 
	 */
	private String softwareVersion;

	/**
	 * light reachability 
	 */
	private boolean reachable;

	// ====================================================================================

	/**
	 * @param IP Hue gateway IP address
	 * @param usr Hue gateway authorized user
	 * @param id light id
	 */
	public Light(final String IP, final String usr, final String id) {
		super(IP, usr, id, id);
		this.type = "";
		this.modelId = "";
		this.softwareVersion = "";
		this.reachable = false;

		// try to get more info
		try {
			// make GET request to retrieve light's information
			String resp = sendGetRequest(LIGHTS + "/" + id);
			JSONObject jsonLight = (JSONObject) new JSONParser().parse(resp);

			if ((jsonLight != null) && ! jsonLight.containsKey(ERROR)) {
				this.name = (String)jsonLight.get(NAME);
				this.modelId = (String)jsonLight.get(MODEL_ID);
				this.type = (String)jsonLight.get(TYPE);
				this.softwareVersion = (String)jsonLight.get(SW_VERSION);
				if (jsonLight.containsKey(STATE)) {
					JSONObject state = (JSONObject) jsonLight.get(STATE);
					this.reachable = ((Boolean)state.get(REACHABLE)).booleanValue();
				}
			}
		} catch (HueException e) {
			Logger.warn("", e);
		} catch (ParseException e) {
			Logger.warn("", e);
		} catch (UnknownHueGatewayException e) {
			Logger.warn("", e);
		} catch (Exception e) {
			Logger.warn("", e);
		}
	}


	// ====================================================================================

	/**
	 * @param s state {@link LightState} of the light to set  
	 * @throws HueException signaling REST resource path incorrect
	 * @throws UnknownHueGatewayException signaling that maybe the IP address of the gateway has changed
	 */
	public void setState(final LightState ls) throws HueException, UnknownHueGatewayException {
		// create JSON format object to send with PUT request
		JSONObject state = setStateObject(ls);
		// make PUT request, get JSON format string response
		String resp = sendPutRequest(LIGHTS + "/" + id + "/" + STATE, state.toString());
		if (resp.substring(3, 8).equals(ERROR)) {
			throw new HueException("ERROR while setting state for light : " 
					+ name + ", id : " + id + "\n\t" + resp);
		}
	}

	/**
	 * @return state {@link LightState} of the light
	 * @throws HueException signaling REST resource path incorrect
	 * @throws UnknownHueGatewayException 
	 */
	public LightState getState() throws HueException, UnknownHueGatewayException {	
		// make GET request
		String resp = sendGetRequest(LIGHTS + "/" + id);
		if (resp.substring(3, 8).equals(ERROR)) {
			//return null;
			throw new HueException("ERROR while getting state for light : " 
					+ name + ", id : " + id + "\n\t" + resp);
		}
		return getState(resp, STATE);
	}

	public String toString() {
		return "<Light id=" + id + " name=" + name + " gateway_IP=" + IP 
				+ " user_name=" + userName + " reachable=" + reachable + "/>";
	}

	public boolean equals(final Object obj){
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		Light l = (Light)obj;
		return IP.equals(l.IP) && userName.equals(l.userName) && id.equals(l.id);		
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the model id
	 */
	public String getModelId() {
		return modelId;
	}

	/**
	 * @return the software version
	 */
	public String getSoftwareversion() {
		return softwareVersion;
	}
	
	/**
	 * @return the reachability
	 */
	public boolean isReachable() {
		return reachable;
	}

}
