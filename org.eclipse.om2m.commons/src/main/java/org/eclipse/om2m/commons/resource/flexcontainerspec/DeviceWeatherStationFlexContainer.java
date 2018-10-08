/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceWeatherStation



Created: 2018-07-04 10:25:10
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceWeatherStationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceWeatherStationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceWeatherStationFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceWeatherStation";
	public static final String SHORT_NAME = "deWSn";
	
	public DeviceWeatherStationFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceWeatherStationFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getIndoorTemperature();
		getOutdoorTemperature();
		getAirQualitySensor();
		getAcousticSensor();
		getAnemometer();
		getBarometer();
		getUvSensor();
	}
	
	public void finalizeDeserialization() {
		if (this.indoorTemperature != null) {
			setIndoorTemperature(this.indoorTemperature);
		}
		if (this.outdoorTemperature != null) {
			setOutdoorTemperature(this.outdoorTemperature);
		}
		if (this.airQualitySensor != null) {
			setAirQualitySensor(this.airQualitySensor);
		}
		if (this.acousticSensor != null) {
			setAcousticSensor(this.acousticSensor);
		}
		if (this.anemometer != null) {
			setAnemometer(this.anemometer);
		}
		if (this.barometer != null) {
			setBarometer(this.barometer);
		}
		if (this.uvSensor != null) {
			setUvSensor(this.uvSensor);
		}
	}

	@XmlElement(name=TemperatureFlexContainer.SHORT_NAME, required=true, type=TemperatureFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TemperatureFlexContainer indoorTemperature;
		
	public void setIndoorTemperature(TemperatureFlexContainer indoorTemperature) {
		this.indoorTemperature = indoorTemperature;
		getFlexContainerOrContainerOrSubscription().add(indoorTemperature);
	}
	
	public TemperatureFlexContainer getIndoorTemperature() {
		this.indoorTemperature = (TemperatureFlexContainer) getResourceByName(TemperatureFlexContainer.SHORT_NAME);
		return indoorTemperature;
	}
	
	@XmlElement(name=TemperatureFlexContainer.SHORT_NAME, required=true, type=TemperatureFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TemperatureFlexContainer outdoorTemperature;
		
	public void setOutdoorTemperature(TemperatureFlexContainer outdoorTemperature) {
		this.outdoorTemperature = outdoorTemperature;
		getFlexContainerOrContainerOrSubscription().add(outdoorTemperature);
	}
	
	public TemperatureFlexContainer getOutdoorTemperature() {
		this.outdoorTemperature = (TemperatureFlexContainer) getResourceByName(TemperatureFlexContainer.SHORT_NAME);
		return outdoorTemperature;
	}
	
	@XmlElement(name=AirQualitySensorFlexContainer.SHORT_NAME, required=true, type=AirQualitySensorFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AirQualitySensorFlexContainer airQualitySensor;
		
	public void setAirQualitySensor(AirQualitySensorFlexContainer airQualitySensor) {
		this.airQualitySensor = airQualitySensor;
		getFlexContainerOrContainerOrSubscription().add(airQualitySensor);
	}
	
	public AirQualitySensorFlexContainer getAirQualitySensor() {
		this.airQualitySensor = (AirQualitySensorFlexContainer) getResourceByName(AirQualitySensorFlexContainer.SHORT_NAME);
		return airQualitySensor;
	}
	
	@XmlElement(name=AcousticSensorFlexContainer.SHORT_NAME, required=true, type=AcousticSensorFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AcousticSensorFlexContainer acousticSensor;
		
	public void setAcousticSensor(AcousticSensorFlexContainer acousticSensor) {
		this.acousticSensor = acousticSensor;
		getFlexContainerOrContainerOrSubscription().add(acousticSensor);
	}
	
	public AcousticSensorFlexContainer getAcousticSensor() {
		this.acousticSensor = (AcousticSensorFlexContainer) getResourceByName(AcousticSensorFlexContainer.SHORT_NAME);
		return acousticSensor;
	}
	
	@XmlElement(name=AnemometerFlexContainer.SHORT_NAME, required=true, type=AnemometerFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AnemometerFlexContainer anemometer;
		
	public void setAnemometer(AnemometerFlexContainer anemometer) {
		this.anemometer = anemometer;
		getFlexContainerOrContainerOrSubscription().add(anemometer);
	}
	
	public AnemometerFlexContainer getAnemometer() {
		this.anemometer = (AnemometerFlexContainer) getResourceByName(AnemometerFlexContainer.SHORT_NAME);
		return anemometer;
	}
	
	@XmlElement(name=BarometerFlexContainer.SHORT_NAME, required=true, type=BarometerFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BarometerFlexContainer barometer;
		
	public void setBarometer(BarometerFlexContainer barometer) {
		this.barometer = barometer;
		getFlexContainerOrContainerOrSubscription().add(barometer);
	}
	
	public BarometerFlexContainer getBarometer() {
		this.barometer = (BarometerFlexContainer) getResourceByName(BarometerFlexContainer.SHORT_NAME);
		return barometer;
	}
	
	@XmlElement(name=UvSensorFlexContainer.SHORT_NAME, required=true, type=UvSensorFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private UvSensorFlexContainer uvSensor;
		
	public void setUvSensor(UvSensorFlexContainer uvSensor) {
		this.uvSensor = uvSensor;
		getFlexContainerOrContainerOrSubscription().add(uvSensor);
	}
	
	public UvSensorFlexContainer getUvSensor() {
		this.uvSensor = (UvSensorFlexContainer) getResourceByName(UvSensorFlexContainer.SHORT_NAME);
		return uvSensor;
	}
	
}
