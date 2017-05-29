/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.lifx.impl.cloud;

import org.eclipse.om2m.sdt.home.lifx.LIFXDevice;
import org.json.simple.JSONObject;

public class LIFXDeviceCloud extends LIFXDevice {

	protected static final String ID = "id";
	protected static final String UUID = "uuid";
	protected static final String LABEL = "label";
	protected static final String CONNECTED = "connected";
	protected static final String POWER = "power";
	protected static final String COLOR = "color";
	protected static final String HUE = "hue";
	protected static final String SATURATION = "saturation";
	protected static final String KELVIN = "kelvin";
	protected static final String BRIGHTNESS = "brightness";

	private final String uuid;
	private boolean connected = false;
	private final String authenticationToken;

	/**
	 * 
	 * @param id
	 * @param uuid
	 * @param label
	 * @param connected
	 * @param power
	 * @param hue
	 *            value from 0 to 65535
	 * @param saturation
	 *            value from 0 to 65535
	 * @param kelvin
	 *            value from 2500 to 9000
	 * @param brightness
	 *            value from 0 to 65535
	 * @param authenticationToken
	 *            authenticationToken
	 * 
	 */
	public LIFXDeviceCloud(final String id, final String uuid, final String label, final boolean connected,
			String power, final double hue, final double saturation, final double kelvin, final double brightness,
			final String authenticationToken) {
		super(id);
		this.uuid = uuid;
		this.connected = connected;
		this.authenticationToken = authenticationToken;
		super.setPower("off".equals(power) ? 0 : 65535);
		super.setLabel(label);
		super.setHue(hue);
		super.setSaturation(saturation);
		super.setKelvin(kelvin);
		super.setBrightness(brightness);
	}

	@Override
	public String toString() {
		try {
			return "device(id=" + getId() + ", uuid=" + getUuid() + ", connected=" + connected + ", power="
					+ super.getPower() + ", label=" + super.getLabel() + ", hue=" + super.getHue() + ", saturation="
					+ super.getSaturation() + ", brightness=" + super.getBrightness() + ", kelvin=" + super.getKelvin()
					+ ")";
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public String getUuid() {
		return uuid;
	}
	
	public String getAuthenticationToken() {
		return authenticationToken;
	}

	@Override
	public double getBrightnessRemotely() throws Exception {
		DiscoveryCloud.updateLightState(this);
		return super.getBrightness();
	}

	@Override
	public void setBrightness(double brightness, double duration) throws Exception {
		DiscoveryCloud.setLightPower(this, null, null, null, null, brightness / 65535d, duration);
		super.setBrightness(brightness);
	}

	@Override
	public double getHueRemotely() throws Exception {
		DiscoveryCloud.updateLightState(this);
		return super.getHue();
	}

	@Override
	public void setHue(double hue, double duration) throws Exception {
		DiscoveryCloud.setLightPower(this, null, hue / 65535d * 360d, null, null, null, duration);
		super.setHue(hue);
	}

	@Override
	public double getSaturationRemotely() throws Exception {
		DiscoveryCloud.updateLightState(this);
		return super.getSaturation();
	}

	@Override
	public void setSaturation(double saturation, double duration) throws Exception {
		DiscoveryCloud.setLightPower(this, null, null, saturation / 65535d, null, null, duration);
		super.setSaturation(saturation);
	}

	@Override
	public double getKelvinRemotely() throws Exception {
		DiscoveryCloud.updateLightState(this);
		return super.getKelvin();
	}

	@Override
	public int getPowerRemotely() throws Exception {
		DiscoveryCloud.updateLightState(this);
		return super.getPower();
	}

	@Override
	public void setPower(int newPower, int duration) throws Exception {
		DiscoveryCloud.setLightPower(this, (newPower == 0 ? "off" : "on"), null, null, null, null, (double) duration);
		super.setPower(newPower, duration);
	}

	@Override
	public void setLightState(int newPower, double newHue, double newSaturation, double newKelvin, double newBrightness,
			int duration) throws Exception {
		DiscoveryCloud.setLightPower(this, (newPower == 0 ? "off" : "on"), newHue / 65535d * 360d,
				newSaturation / 65535d, newKelvin, newBrightness / 65535d, (double) duration);
		super.setPower(newPower);
		super.setHue(newHue);
		super.setSaturation(newSaturation);
		super.setKelvin(newKelvin);
		super.setBrightness(newBrightness);
	}

	public static LIFXDeviceCloud fromJson(JSONObject json, String pAuthenticationToken) {

		String id = (String) json.get("id");
		String uuid = (String) json.get(UUID);
		String label = (String) json.get(LABEL);
		String power = (String) json.get(POWER);
		Boolean connected = (Boolean) json.get(CONNECTED);
		JSONObject colorJsonObject = (JSONObject) json.get(COLOR);
		double hue = getDoubleValue(colorJsonObject.get(HUE));
		double saturation = getDoubleValue(colorJsonObject.get(SATURATION));
		double kelvin = getDoubleValue(colorJsonObject.get(KELVIN));
		double brightness = (double) json.get(BRIGHTNESS);

		// convert cloud value to lan value
		// hue (0 to 360) -> (0 to 65535)
		hue = hue / 360d * 65535d;
		// brightness (0.0 to 1.0) -> (0 to 65535)
		brightness = brightness * 65535d;
		// saturation (0.0 to 1.0) -> (0 to 65535)
		saturation = saturation * 65535d;

		LIFXDeviceCloud lifxDevice = new LIFXDeviceCloud(id, uuid, label, connected, power, hue, saturation, kelvin,
				brightness, pAuthenticationToken);

		return lifxDevice;
	}

	protected void updateLightState(JSONObject json) {
		String label = (String) json.get(LABEL);
		String power = (String) json.get(POWER);
		Boolean connected = (Boolean) json.get(CONNECTED);
		JSONObject colorJsonObject = (JSONObject) json.get(COLOR);
		double hue = getDoubleValue(colorJsonObject.get(HUE));
		double saturation = getDoubleValue(colorJsonObject.get(SATURATION));
		double kelvin = getDoubleValue(colorJsonObject.get(KELVIN));
		double brightness = (double) json.get(BRIGHTNESS);
		
		// convert cloud value to lan value
		// hue (0 to 360) -> (0 to 65535)
		hue = hue / 360d * 65535d;
		// brightness (0.0 to 1.0) -> (0 to 65535)
		brightness = brightness * 65535d;
		// saturation (0.0 to 1.0) -> (0 to 65535)
		saturation = saturation * 65535d;

		this.connected = connected;
		super.setPower("off".equals(power) ? 0 : 65535);
		super.setLabel(label);
		super.setHue(hue);
		super.setSaturation(saturation);
		super.setKelvin(kelvin);
		super.setBrightness(brightness);
	}
	
	private static double getDoubleValue(Object object) {
		double value = 0;
		try {
			value = (double) object;
		} catch (Exception e) {
			value = (long) object;
		}
		
		return value;
	}
}
