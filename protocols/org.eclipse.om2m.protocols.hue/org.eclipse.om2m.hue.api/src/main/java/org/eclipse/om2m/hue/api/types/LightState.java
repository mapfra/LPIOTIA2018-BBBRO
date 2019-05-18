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
 * State of a Hue Light device
 */
public class LightState {
	
	/**
	 * state of the light on/off
	 */
	private boolean on;
	
	/**
	 * brightness from 0 to 255
	 */
	private int bri;
	
	/**
	 * saturation from 0 to 255 <p>
	 * 0 : white <p>
	 * 255 : most saturated, colored <p>
	 */
	private int sat;
	
	/**
	 * hue value of the light from 0 to 65535 <p>
	 * 0 : red <p>
	 * 12750 : yellow <p>
	 * 25500 : light green <p>
	 * 36210 : dark green <p>
	 * 46920 : blue <p>
	 * 56100 : violet <p>
	 * 65280 : red <p>
	 */
	private int hue;
	
	/**
	 * dynamic effect of the light <p>
	 * use {@link LightEffect} class for effect parameters <p>
	 */
	private int effect;
	
	/**
	 * alert effect of the light <p>
	 * use {@link AlarmMode} class for alarm parameters <p>
	 */
	private int alert;
	
	// ====================================================================================
	
	/**
	 * 
	 */
	public LightState(final boolean on) {
		this.on = on;
		bri = -1;
		sat = -1;
		hue = -1;
		effect = -1;
		alert = -1;
	}

	/**
	 * 
	 */
	public LightState() {
		this(false);
	}
	
	// ====================================================================================
	
	/**
	 * @return state of the light on/off
	 */
	public boolean isOn() {
		return on;
	}

	/**
	 * @param pon state of the light on/off
	 */
	public void setOn(final boolean on) {
		this.on = on;
	}

	/**
	 * @return brightness from 0 to 255, -1 means not set
	 */
	public int getBri() {
		return bri;
	}

	/**
	 * @param index brightness from 0 to 255
	 */
	public void setBri(final int index) {
		if ((index >= 0) && (index <= 255))
			this.bri = index;
	}

	/**
	 * 0 : white <p>
	 * 255 : most saturated, colored <p>
	 * -1 : not set <p>
	 * @return saturation from 0 to 255
	 */
	public int getSat() {
		return sat;
	}

	/**
	 * 0 : white <p>
	 * 255 : most saturated, colored <p>
	 * @param index saturation from 0 to 255
	 */
	public void setSat(final int index) {
		if ((index >= 0) && (index <= 255))
			this.sat = index;
	}

	/**
	 * 0 : red <p>
	 * 12750 : yellow <p>
	 * 25500 : light green <p>
	 * 36210 : dark green <p>
	 * 46920 : blue <p>
	 * 56100 : violet <p>
	 * 65280 : red <p>
	 * -1 : not set <p>
	 * @return the hue value of the light from 0 to 65535
	 */
	public int getHue() {
		return hue;
	}

	/**
	 * 0 : red <p>
	 * 12750 : yellow <p>
	 * 25500 : light green <p>
	 * 36210 : dark green <p>
	 * 46920 : blue <p>
	 * 56100 : violet <p>
	 * 65280 : red <p>
	 * @param index hue value of the light from 0 to 65535
	 */
	public void setHue(final int index) {
		if ((index >= 0) && (index <= 65535))
			this.hue = index;
	}

	/**
	 * @return {@link LightEffect} dynamic effect of the light
	 */
	public int getEffect() {
		return effect;
	}

	/**
	 * @param pEffect {@link LightEffect} dynamic effect of the light
	 */
	public void setEffect(final int effect) {
		this.effect = effect;
	}

	/**
	 * @return {@link AlarmMode} alert effect of the light
	 */
	public int getAlert() {
		return alert;
	}

	/**
	 * @param pAlert {@link AlarmMode} alert effect of the light
	 */
	public void setAlert(final int alert) {
		this.alert = alert;
	}
	
	// ====================================================================================
	
	/**
	 * here compares all attributes except 'alert' (because of "lselect" that turns into "select" after one cycle) <p>
	 * @see java.lang.Object#equals(java.lang.Object) 
	 */
	public boolean equals(final Object obj){
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		LightState l = (LightState) obj;
		return (on == l.on) && (bri == l.bri) && (hue == l.hue)
				&& (sat == l.sat) && (effect == l.effect);
	}
	
	public String toString() {
		return "<LightState on=" + on + " bri=" + bri + " sat=" + sat + " hue=" + hue + "/>";
	}
		
}
