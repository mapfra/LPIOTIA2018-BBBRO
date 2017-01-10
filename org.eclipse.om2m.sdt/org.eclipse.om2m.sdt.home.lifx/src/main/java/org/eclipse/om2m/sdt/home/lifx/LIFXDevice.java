/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.lifx;

public abstract class LIFXDevice {

	private String id;
	
	/** hue : from 0 to 65535 */ 
	private double hue;
	
	/** saturation : from 0 to 65535*/
	private double saturation;
	
	/** brightness: from 0 to 65535*/
	private double brightness;
	
	/** kelvin: from 2500 to 9000 */ 
	private long kelvin;
	
	/** power: 0=off, 65535=on */ 
	private int power;
	private String label;
	private long lastDataFromDevice;
	
	private LIFXDevice() {
		
	}
	
	public LIFXDevice(String id) {
		this.id = id;
	}

	

	public String getId() {
		return id;
	}

	/**
	 * 
	 * @return hue (0 to 65535)
	 * @throws Exception
	 */
	public double getHue() {
		return hue;
	}
	
	/**
	 * Retrieve Hue value on the device
	 * @return current hue value
	 * @throws Exception
	 */
	public abstract double  getHueRemotely() throws Exception;
	
	public double getHue(boolean cache) throws Exception {
		if (cache) {
			return getHue();
		} else {
			return getHueRemotely();
		}
	}
	
	/**
	 * 
	 * @param hue value from 0 to 65535
	 */
	public void setHue(double hue) {
		this.hue = hue;
		updateLastDataFromDevice();
	}
	
	/**
	 * 
	 * @param hue value from 0 to 65535
	 * @param duration
	 * @throws Exception
	 */
	public abstract void setHue(double hue, double duration) throws Exception;

	/**
	 * 
	 * @return saturation (value from 0 to 65535)
	 * @throws Exception
	 */
	public double getSaturation()  {
		return saturation;
	}
	
	public abstract double getSaturationRemotely() throws Exception;
	
	/**
	 * Return saturation value
	 * @param cache if true, retrieve the current cache value
	 * @return saturation
	 */
	public double getSaturation(boolean cache) throws Exception {
		if (cache) {
			return getSaturation();
		} else {
			return getSaturationRemotely();
		}
	}
	
	/**
	 * 
	 * @param saturation (value from 0 to 65535)
	 */
	public void setSaturation(double saturation) {
		this.saturation = saturation;
		updateLastDataFromDevice();
	}
	
	/**
	 * 
	 * @param saturation value from 0 to 65535
	 * @param duration
	 * @throws Exception
	 */
	public void setSaturation(double saturation, double duration) throws Exception {
		setSaturation(saturation);
	}

	/**
	 * 
	 * @return brightness value from 0 to 65535
	 * @throws Exception
	 */
	public double getBrightness() {
		return brightness;
	}
	
	public abstract double getBrightnessRemotely()  throws Exception;
	
	/**
	 * Get brightness (locally or remotely)
	 * @param cache
	 * @return
	 * @throws Exception
	 */
	public double getBrightness(boolean cache) throws Exception {
		if (cache) {
			return getBrightness();
		} else {
			return getBrightnessRemotely();
		}
	}
	
	/**
	 * 
	 * @param brightness value from 0 to 65535
	 */
	public void setBrightness(double brightness) {
		this.brightness = brightness;
		updateLastDataFromDevice();
	}
	
	/**
	 * 
	 * @param brightness value from 0 to 65535
	 * @param duration
	 * @throws Exception
	 */
	public void setBrightness(double brightness, double duration) throws Exception {
		setBrightness(brightness);
	}

	/**
	 * 
	 * @return kelvin value from 2500 to 9000
	 * @throws Exception
	 */
	public long getKelvin(){
		return kelvin;
	}
	
	public abstract long getKelvinRemotely() throws Exception;
	
	public long getKelvin(boolean cache) throws Exception {
		if (cache) {
			return getKelvin();
		} else {
			return getKelvinRemotely();
		}
	}
	
	/**
	 * 
	 * @param kelvin value from 2500 to 9000
	 */
	public void setKelvin(long kelvin) {
		this.kelvin = kelvin;
		updateLastDataFromDevice();
	}

	/**
	 * 
	 * @return power value 0=off, 65535=on
	 * @throws Exception
	 */
	public int getPower() {
		return power;
	}
	
	public abstract int getPowerRemotely() throws Exception;
	
	public int getPower(boolean cache) throws Exception {
		if (cache) {
			return getPower();
		} else {
			return getPowerRemotely();
		}
	}

	/**
	 * 
	 * @param newPower value 0=off 65535=on
	 * @param duration
	 * @throws Exception
	 */
	public void setPower(int newPower, int duration) throws Exception {
		this.power = newPower;
		updateLastDataFromDevice();
	}
	
	/**
	 * 
	 * @param newPower value 0=off 65535=on
	 */
	public void setPower(int newPower)  {
		this.power = newPower;
		updateLastDataFromDevice();
	}
	
	
	public abstract void setLightState(int newPower, double newHue, double newSaturation, long newKelvin, double newBrightness, int duration) throws Exception;

	public String getLabel()  throws Exception {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
		updateLastDataFromDevice();
	}

	
	
	public void notifyDiscoveryPacket() {
		updateLastDataFromDevice();
	}
	
	public long getLastDataFromDevice() {
		return lastDataFromDevice;
	}



	protected void updateLastDataFromDevice() {
		lastDataFromDevice = System.currentTimeMillis();
	}
}