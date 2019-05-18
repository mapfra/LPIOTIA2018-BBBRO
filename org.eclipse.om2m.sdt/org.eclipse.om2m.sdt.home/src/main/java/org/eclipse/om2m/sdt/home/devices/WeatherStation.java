/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.devices;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.home.modules.AcousticSensor;
import org.eclipse.om2m.sdt.home.modules.AirQualitySensor;
import org.eclipse.om2m.sdt.home.modules.Anemometer;
import org.eclipse.om2m.sdt.home.modules.Barometer;
import org.eclipse.om2m.sdt.home.modules.Temperature;
import org.eclipse.om2m.sdt.home.modules.UvSensor;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class WeatherStation extends GenericDevice {

	private Temperature indoorTemp;
	private Temperature outdoorTemp;
	private Barometer barometer;
	private AcousticSensor noise;
	private AirQualitySensor airQuality;
	private Anemometer anemometer;
	private UvSensor uvSensor;

	public WeatherStation(String id, String serial, Domain domain) {
		super(id, serial, DeviceType.deviceWeatherStation, domain);
	}

	public void setIndoorTemperature(Temperature temp) {
		this.indoorTemp = temp;
		super.addModule(indoorTemp);
	}

	public Temperature getIndoorTemperature() {
		return indoorTemp;
	}

	public void setOutdoorTemperature(Temperature temp) {
		this.outdoorTemp = temp;
		super.addModule(outdoorTemp);
	}

	public Temperature getOutdoorTemperature() {
		return outdoorTemp;
	}

	public void setAirQualitySensor(AirQualitySensor airQuality) {
		this.airQuality = airQuality;
		super.addModule(airQuality);
	}

	public AirQualitySensor getAirQualitySensor() {
		return airQuality;
	}

	public void setBarometer(Barometer pAtmosphericPressureSensor) {
		this.barometer = pAtmosphericPressureSensor;
		super.addModule(barometer);
	}

	public Barometer getBarometer() {
		return barometer;
	}

	public void setAcousticSensor(AcousticSensor pNoise) {
		this.noise = pNoise;
		super.addModule(noise);
	}
	
	public AcousticSensor getAcousticSensor() {
		return noise;
	}

	public void setUvSensor(UvSensor uvSensor) {
		this.uvSensor = uvSensor;
		super.addModule(uvSensor);
	}
	
	public UvSensor getUvSensor() {
		return uvSensor;
	}

	public void setAnemometer(Anemometer anemometer) {
		this.anemometer = anemometer;
		super.addModule(anemometer);
	}
	
	public Anemometer getAnemometer() {
		return anemometer;
	}
	
}
