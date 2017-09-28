/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceWeatherStation

A WeatherStation is a device that provides weather information.

Created: 2017-09-28 17:26:40
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;


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
		getTemperature();
		getRelativeHumidity();
		getAtmosphericPressureSensor();
		getNoise();
		getExtendedCarbonDioxideSensor();
	}
	
	public void finalizeDeserialization() {
		if (this.temperature != null) {
			setTemperature(this.temperature);
		}
		if (this.relativeHumidity != null) {
			setRelativeHumidity(this.relativeHumidity);
		}
		if (this.atmosphericPressureSensor != null) {
			setAtmosphericPressureSensor(this.atmosphericPressureSensor);
		}
		if (this.noise != null) {
			setNoise(this.noise);
		}
		if (this.extendedCarbonDioxideSensor != null) {
			setExtendedCarbonDioxideSensor(this.extendedCarbonDioxideSensor);
		}
	}
	
	@XmlElement(name="tempe", required=true, type=TemperatureFlexContainer.class)
	private TemperatureFlexContainer temperature;
	
	
	public void setTemperature(TemperatureFlexContainer temperature) {
		this.temperature = temperature;
		getFlexContainerOrContainerOrSubscription().add(temperature);
	}
	
	public TemperatureFlexContainer getTemperature() {
		this.temperature = (TemperatureFlexContainer) getResourceByName(TemperatureFlexContainer.SHORT_NAME);
		return temperature;
	}
	
	@XmlElement(name="relHy", required=true, type=RelativeHumidityFlexContainer.class)
	private RelativeHumidityFlexContainer relativeHumidity;
	
	
	public void setRelativeHumidity(RelativeHumidityFlexContainer relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
		getFlexContainerOrContainerOrSubscription().add(relativeHumidity);
	}
	
	public RelativeHumidityFlexContainer getRelativeHumidity() {
		this.relativeHumidity = (RelativeHumidityFlexContainer) getResourceByName(RelativeHumidityFlexContainer.SHORT_NAME);
		return relativeHumidity;
	}
	
	@XmlElement(name="atPSr", required=true, type=AtmosphericPressureSensorFlexContainer.class)
	private AtmosphericPressureSensorFlexContainer atmosphericPressureSensor;
	
	
	public void setAtmosphericPressureSensor(AtmosphericPressureSensorFlexContainer atmosphericPressureSensor) {
		this.atmosphericPressureSensor = atmosphericPressureSensor;
		getFlexContainerOrContainerOrSubscription().add(atmosphericPressureSensor);
	}
	
	public AtmosphericPressureSensorFlexContainer getAtmosphericPressureSensor() {
		this.atmosphericPressureSensor = (AtmosphericPressureSensorFlexContainer) getResourceByName(AtmosphericPressureSensorFlexContainer.SHORT_NAME);
		return atmosphericPressureSensor;
	}
	
	@XmlElement(name="noise", required=true, type=NoiseFlexContainer.class)
	private NoiseFlexContainer noise;
	
	
	public void setNoise(NoiseFlexContainer noise) {
		this.noise = noise;
		getFlexContainerOrContainerOrSubscription().add(noise);
	}
	
	public NoiseFlexContainer getNoise() {
		this.noise = (NoiseFlexContainer) getResourceByName(NoiseFlexContainer.SHORT_NAME);
		return noise;
	}
	
	@XmlElement(name="eCDSr", required=true, type=ExtendedCarbonDioxideSensorFlexContainer.class)
	private ExtendedCarbonDioxideSensorFlexContainer extendedCarbonDioxideSensor;
	
	
	public void setExtendedCarbonDioxideSensor(ExtendedCarbonDioxideSensorFlexContainer extendedCarbonDioxideSensor) {
		this.extendedCarbonDioxideSensor = extendedCarbonDioxideSensor;
		getFlexContainerOrContainerOrSubscription().add(extendedCarbonDioxideSensor);
	}
	
	public ExtendedCarbonDioxideSensorFlexContainer getExtendedCarbonDioxideSensor() {
		this.extendedCarbonDioxideSensor = (ExtendedCarbonDioxideSensorFlexContainer) getResourceByName(ExtendedCarbonDioxideSensorFlexContainer.SHORT_NAME);
		return extendedCarbonDioxideSensor;
	}
	
}