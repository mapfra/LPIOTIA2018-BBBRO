/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceOutdoorLamp

An outdoor lamp is a smart home appliance to provide lights and information for outside of home with smart sensing capabilities such as ultraviolet sensing.

Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceOutdoorLampFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceOutdoorLampFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceOutdoorLampFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceOutdoorLamp";
	public static final String SHORT_NAME = "deOLp";
	
	public DeviceOutdoorLampFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceOutdoorLampFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getBinarySwitch();
		getBrightness();
		getMotionSensor();
		getAirQualitySensor();
		getUvSensor();
		getTimer();
		getFaultDetection();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.brightness != null) {
			setBrightness(this.brightness);
		}
		if (this.motionSensor != null) {
			setMotionSensor(this.motionSensor);
		}
		if (this.airQualitySensor != null) {
			setAirQualitySensor(this.airQualitySensor);
		}
		if (this.uvSensor != null) {
			setUvSensor(this.uvSensor);
		}
		if (this.timer != null) {
			setTimer(this.timer);
		}
		if (this.faultDetection != null) {
			setFaultDetection(this.faultDetection);
		}
	}

	@XmlElement(name=BinarySwitchFlexContainer.SHORT_NAME, required=true, type=BinarySwitchFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BinarySwitchFlexContainer binarySwitch;
		
	public void setBinarySwitch(BinarySwitchFlexContainer binarySwitch) {
		this.binarySwitch = binarySwitch;
		getFlexContainerOrContainerOrSubscription().add(binarySwitch);
	}
	
	public BinarySwitchFlexContainer getBinarySwitch() {
		this.binarySwitch = (BinarySwitchFlexContainer) getResourceByName(BinarySwitchFlexContainer.SHORT_NAME);
		return binarySwitch;
	}
	
	@XmlElement(name=BrightnessFlexContainer.SHORT_NAME, required=true, type=BrightnessFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BrightnessFlexContainer brightness;
		
	public void setBrightness(BrightnessFlexContainer brightness) {
		this.brightness = brightness;
		getFlexContainerOrContainerOrSubscription().add(brightness);
	}
	
	public BrightnessFlexContainer getBrightness() {
		this.brightness = (BrightnessFlexContainer) getResourceByName(BrightnessFlexContainer.SHORT_NAME);
		return brightness;
	}
	
	@XmlElement(name=MotionSensorFlexContainer.SHORT_NAME, required=true, type=MotionSensorFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private MotionSensorFlexContainer motionSensor;
		
	public void setMotionSensor(MotionSensorFlexContainer motionSensor) {
		this.motionSensor = motionSensor;
		getFlexContainerOrContainerOrSubscription().add(motionSensor);
	}
	
	public MotionSensorFlexContainer getMotionSensor() {
		this.motionSensor = (MotionSensorFlexContainer) getResourceByName(MotionSensorFlexContainer.SHORT_NAME);
		return motionSensor;
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
	
	@XmlElement(name=TimerFlexContainer.SHORT_NAME, required=true, type=TimerFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TimerFlexContainer timer;
		
	public void setTimer(TimerFlexContainer timer) {
		this.timer = timer;
		getFlexContainerOrContainerOrSubscription().add(timer);
	}
	
	public TimerFlexContainer getTimer() {
		this.timer = (TimerFlexContainer) getResourceByName(TimerFlexContainer.SHORT_NAME);
		return timer;
	}
	
	@XmlElement(name=FaultDetectionFlexContainer.SHORT_NAME, required=true, type=FaultDetectionFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private FaultDetectionFlexContainer faultDetection;
		
	public void setFaultDetection(FaultDetectionFlexContainer faultDetection) {
		this.faultDetection = faultDetection;
		getFlexContainerOrContainerOrSubscription().add(faultDetection);
	}
	
	public FaultDetectionFlexContainer getFaultDetection() {
		this.faultDetection = (FaultDetectionFlexContainer) getResourceByName(FaultDetectionFlexContainer.SHORT_NAME);
		return faultDetection;
	}
	
}
