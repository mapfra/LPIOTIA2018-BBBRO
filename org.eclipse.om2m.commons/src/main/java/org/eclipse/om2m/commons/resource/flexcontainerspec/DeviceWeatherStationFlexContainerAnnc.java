/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceWeatherStationAnnc

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


@XmlRootElement(name = DeviceWeatherStationFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceWeatherStationFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceWeatherStationFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceWeatherStationAnnc";
	public static final String SHORT_NAME = "deWSnAnnc";
	
	public DeviceWeatherStationFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceWeatherStationFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getTemperature();
		getTemperatureAnnc();
		getRelativeHumidity();
		getRelativeHumidityAnnc();
		getAtmosphericPressureSensor();
		getAtmosphericPressureSensorAnnc();
		getNoise();
		getNoiseAnnc();
		getExtendedCarbonDioxideSensor();
		getExtendedCarbonDioxideSensorAnnc();
	}
	
	public void finalizeDeserialization() {
		if (this.temperature != null) {
			setTemperature(this.temperature);
		}
		if (this.temperatureAnnc != null) {
			setTemperatureAnnc(this.temperatureAnnc);
			}
		
		if (this.relativeHumidity != null) {
			setRelativeHumidity(this.relativeHumidity);
		}
		if (this.relativeHumidityAnnc != null) {
			setRelativeHumidityAnnc(this.relativeHumidityAnnc);
			}
		
		if (this.atmosphericPressureSensor != null) {
			setAtmosphericPressureSensor(this.atmosphericPressureSensor);
		}
		if (this.atmosphericPressureSensorAnnc != null) {
			setAtmosphericPressureSensorAnnc(this.atmosphericPressureSensorAnnc);
			}
		
		if (this.noise != null) {
			setNoise(this.noise);
		}
		if (this.noiseAnnc != null) {
			setNoiseAnnc(this.noiseAnnc);
			}
		
		if (this.extendedCarbonDioxideSensor != null) {
			setExtendedCarbonDioxideSensor(this.extendedCarbonDioxideSensor);
		}
		if (this.extendedCarbonDioxideSensorAnnc != null) {
			setExtendedCarbonDioxideSensorAnnc(this.extendedCarbonDioxideSensorAnnc);
			}
		
	}
	
	@XmlElement(name="tempe", required=true, type=TemperatureFlexContainerAnnc.class)
	private TemperatureFlexContainer temperature;
	
	
	public void setTemperature(TemperatureFlexContainer temperature) {
		this.temperature = temperature;
		getFlexContainerOrContainerOrSubscription().add(temperature);
	}
	
	public TemperatureFlexContainer getTemperature() {
		this.temperature = (TemperatureFlexContainer) getResourceByName(TemperatureFlexContainer.SHORT_NAME);
		return temperature;
	}
	
	@XmlElement(name="tempeAnnc", required=true, type=TemperatureFlexContainerAnnc.class)
	private TemperatureFlexContainerAnnc temperatureAnnc;
	
	
	public void setTemperatureAnnc(TemperatureFlexContainerAnnc temperatureAnnc) {
		this.temperatureAnnc = temperatureAnnc;
		getFlexContainerOrContainerOrSubscription().add(temperatureAnnc);
	}
	
	public TemperatureFlexContainerAnnc getTemperatureAnnc() {
		this.temperatureAnnc = (TemperatureFlexContainerAnnc) getResourceByName(TemperatureFlexContainerAnnc.SHORT_NAME);
		return temperatureAnnc;
	}
	
	@XmlElement(name="relHy", required=true, type=RelativeHumidityFlexContainerAnnc.class)
	private RelativeHumidityFlexContainer relativeHumidity;
	
	
	public void setRelativeHumidity(RelativeHumidityFlexContainer relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
		getFlexContainerOrContainerOrSubscription().add(relativeHumidity);
	}
	
	public RelativeHumidityFlexContainer getRelativeHumidity() {
		this.relativeHumidity = (RelativeHumidityFlexContainer) getResourceByName(RelativeHumidityFlexContainer.SHORT_NAME);
		return relativeHumidity;
	}
	
	@XmlElement(name="relHyAnnc", required=true, type=RelativeHumidityFlexContainerAnnc.class)
	private RelativeHumidityFlexContainerAnnc relativeHumidityAnnc;
	
	
	public void setRelativeHumidityAnnc(RelativeHumidityFlexContainerAnnc relativeHumidityAnnc) {
		this.relativeHumidityAnnc = relativeHumidityAnnc;
		getFlexContainerOrContainerOrSubscription().add(relativeHumidityAnnc);
	}
	
	public RelativeHumidityFlexContainerAnnc getRelativeHumidityAnnc() {
		this.relativeHumidityAnnc = (RelativeHumidityFlexContainerAnnc) getResourceByName(RelativeHumidityFlexContainerAnnc.SHORT_NAME);
		return relativeHumidityAnnc;
	}
	
	@XmlElement(name="atPSr", required=true, type=AtmosphericPressureSensorFlexContainerAnnc.class)
	private AtmosphericPressureSensorFlexContainer atmosphericPressureSensor;
	
	
	public void setAtmosphericPressureSensor(AtmosphericPressureSensorFlexContainer atmosphericPressureSensor) {
		this.atmosphericPressureSensor = atmosphericPressureSensor;
		getFlexContainerOrContainerOrSubscription().add(atmosphericPressureSensor);
	}
	
	public AtmosphericPressureSensorFlexContainer getAtmosphericPressureSensor() {
		this.atmosphericPressureSensor = (AtmosphericPressureSensorFlexContainer) getResourceByName(AtmosphericPressureSensorFlexContainer.SHORT_NAME);
		return atmosphericPressureSensor;
	}
	
	@XmlElement(name="atPSrAnnc", required=true, type=AtmosphericPressureSensorFlexContainerAnnc.class)
	private AtmosphericPressureSensorFlexContainerAnnc atmosphericPressureSensorAnnc;
	
	
	public void setAtmosphericPressureSensorAnnc(AtmosphericPressureSensorFlexContainerAnnc atmosphericPressureSensorAnnc) {
		this.atmosphericPressureSensorAnnc = atmosphericPressureSensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(atmosphericPressureSensorAnnc);
	}
	
	public AtmosphericPressureSensorFlexContainerAnnc getAtmosphericPressureSensorAnnc() {
		this.atmosphericPressureSensorAnnc = (AtmosphericPressureSensorFlexContainerAnnc) getResourceByName(AtmosphericPressureSensorFlexContainerAnnc.SHORT_NAME);
		return atmosphericPressureSensorAnnc;
	}
	
	@XmlElement(name="noise", required=true, type=NoiseFlexContainerAnnc.class)
	private NoiseFlexContainer noise;
	
	
	public void setNoise(NoiseFlexContainer noise) {
		this.noise = noise;
		getFlexContainerOrContainerOrSubscription().add(noise);
	}
	
	public NoiseFlexContainer getNoise() {
		this.noise = (NoiseFlexContainer) getResourceByName(NoiseFlexContainer.SHORT_NAME);
		return noise;
	}
	
	@XmlElement(name="noiseAnnc", required=true, type=NoiseFlexContainerAnnc.class)
	private NoiseFlexContainerAnnc noiseAnnc;
	
	
	public void setNoiseAnnc(NoiseFlexContainerAnnc noiseAnnc) {
		this.noiseAnnc = noiseAnnc;
		getFlexContainerOrContainerOrSubscription().add(noiseAnnc);
	}
	
	public NoiseFlexContainerAnnc getNoiseAnnc() {
		this.noiseAnnc = (NoiseFlexContainerAnnc) getResourceByName(NoiseFlexContainerAnnc.SHORT_NAME);
		return noiseAnnc;
	}
	
	@XmlElement(name="eCDSr", required=true, type=ExtendedCarbonDioxideSensorFlexContainerAnnc.class)
	private ExtendedCarbonDioxideSensorFlexContainer extendedCarbonDioxideSensor;
	
	
	public void setExtendedCarbonDioxideSensor(ExtendedCarbonDioxideSensorFlexContainer extendedCarbonDioxideSensor) {
		this.extendedCarbonDioxideSensor = extendedCarbonDioxideSensor;
		getFlexContainerOrContainerOrSubscription().add(extendedCarbonDioxideSensor);
	}
	
	public ExtendedCarbonDioxideSensorFlexContainer getExtendedCarbonDioxideSensor() {
		this.extendedCarbonDioxideSensor = (ExtendedCarbonDioxideSensorFlexContainer) getResourceByName(ExtendedCarbonDioxideSensorFlexContainer.SHORT_NAME);
		return extendedCarbonDioxideSensor;
	}
	
	@XmlElement(name="eCDSrAnnc", required=true, type=ExtendedCarbonDioxideSensorFlexContainerAnnc.class)
	private ExtendedCarbonDioxideSensorFlexContainerAnnc extendedCarbonDioxideSensorAnnc;
	
	
	public void setExtendedCarbonDioxideSensorAnnc(ExtendedCarbonDioxideSensorFlexContainerAnnc extendedCarbonDioxideSensorAnnc) {
		this.extendedCarbonDioxideSensorAnnc = extendedCarbonDioxideSensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(extendedCarbonDioxideSensorAnnc);
	}
	
	public ExtendedCarbonDioxideSensorFlexContainerAnnc getExtendedCarbonDioxideSensorAnnc() {
		this.extendedCarbonDioxideSensorAnnc = (ExtendedCarbonDioxideSensorFlexContainerAnnc) getResourceByName(ExtendedCarbonDioxideSensorFlexContainerAnnc.SHORT_NAME);
		return extendedCarbonDioxideSensorAnnc;
	}
	
}