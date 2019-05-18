/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceOutdoorLampAnnc

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
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DeviceOutdoorLampFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceOutdoorLampFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceOutdoorLampFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceOutdoorLampAnnc";
	public static final String SHORT_NAME = "deOLpAnnc";
	
	public DeviceOutdoorLampFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceOutdoorLampFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getBrightness();
		getBrightnessAnnc();
		getMotionSensor();
		getMotionSensorAnnc();
		getAirQualitySensor();
		getAirQualitySensorAnnc();
		getUvSensor();
		getUvSensorAnnc();
		getTimer();
		getTimerAnnc();
		getFaultDetection();
		getFaultDetectionAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.binarySwitchAnnc != null) {
			setBinarySwitchAnnc(this.binarySwitchAnnc);
		}
		if (this.brightness != null) {
			setBrightness(this.brightness);
		}
		if (this.brightnessAnnc != null) {
			setBrightnessAnnc(this.brightnessAnnc);
		}
		if (this.motionSensor != null) {
			setMotionSensor(this.motionSensor);
		}
		if (this.motionSensorAnnc != null) {
			setMotionSensorAnnc(this.motionSensorAnnc);
		}
		if (this.airQualitySensor != null) {
			setAirQualitySensor(this.airQualitySensor);
		}
		if (this.airQualitySensorAnnc != null) {
			setAirQualitySensorAnnc(this.airQualitySensorAnnc);
		}
		if (this.uvSensor != null) {
			setUvSensor(this.uvSensor);
		}
		if (this.uvSensorAnnc != null) {
			setUvSensorAnnc(this.uvSensorAnnc);
		}
		if (this.timer != null) {
			setTimer(this.timer);
		}
		if (this.timerAnnc != null) {
			setTimerAnnc(this.timerAnnc);
		}
		if (this.faultDetection != null) {
			setFaultDetection(this.faultDetection);
		}
		if (this.faultDetectionAnnc != null) {
			setFaultDetectionAnnc(this.faultDetectionAnnc);
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
	
	@XmlElement(name=BinarySwitchFlexContainerAnnc.SHORT_NAME, required=true, type=BinarySwitchFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BinarySwitchFlexContainerAnnc binarySwitchAnnc;
		
	public void setBinarySwitchAnnc(BinarySwitchFlexContainerAnnc binarySwitchAnnc) {
		this.binarySwitchAnnc = binarySwitchAnnc;
		getFlexContainerOrContainerOrSubscription().add(binarySwitchAnnc);
	}
	
	public BinarySwitchFlexContainerAnnc getBinarySwitchAnnc() {
		this.binarySwitchAnnc = (BinarySwitchFlexContainerAnnc) getResourceByName(BinarySwitchFlexContainerAnnc.SHORT_NAME);
		return binarySwitchAnnc;
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
	
	@XmlElement(name=BrightnessFlexContainerAnnc.SHORT_NAME, required=true, type=BrightnessFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BrightnessFlexContainerAnnc brightnessAnnc;
		
	public void setBrightnessAnnc(BrightnessFlexContainerAnnc brightnessAnnc) {
		this.brightnessAnnc = brightnessAnnc;
		getFlexContainerOrContainerOrSubscription().add(brightnessAnnc);
	}
	
	public BrightnessFlexContainerAnnc getBrightnessAnnc() {
		this.brightnessAnnc = (BrightnessFlexContainerAnnc) getResourceByName(BrightnessFlexContainerAnnc.SHORT_NAME);
		return brightnessAnnc;
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
	
	@XmlElement(name=MotionSensorFlexContainerAnnc.SHORT_NAME, required=true, type=MotionSensorFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private MotionSensorFlexContainerAnnc motionSensorAnnc;
		
	public void setMotionSensorAnnc(MotionSensorFlexContainerAnnc motionSensorAnnc) {
		this.motionSensorAnnc = motionSensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(motionSensorAnnc);
	}
	
	public MotionSensorFlexContainerAnnc getMotionSensorAnnc() {
		this.motionSensorAnnc = (MotionSensorFlexContainerAnnc) getResourceByName(MotionSensorFlexContainerAnnc.SHORT_NAME);
		return motionSensorAnnc;
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
	
	@XmlElement(name=AirQualitySensorFlexContainerAnnc.SHORT_NAME, required=true, type=AirQualitySensorFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AirQualitySensorFlexContainerAnnc airQualitySensorAnnc;
		
	public void setAirQualitySensorAnnc(AirQualitySensorFlexContainerAnnc airQualitySensorAnnc) {
		this.airQualitySensorAnnc = airQualitySensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(airQualitySensorAnnc);
	}
	
	public AirQualitySensorFlexContainerAnnc getAirQualitySensorAnnc() {
		this.airQualitySensorAnnc = (AirQualitySensorFlexContainerAnnc) getResourceByName(AirQualitySensorFlexContainerAnnc.SHORT_NAME);
		return airQualitySensorAnnc;
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
	
	@XmlElement(name=UvSensorFlexContainerAnnc.SHORT_NAME, required=true, type=UvSensorFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private UvSensorFlexContainerAnnc uvSensorAnnc;
		
	public void setUvSensorAnnc(UvSensorFlexContainerAnnc uvSensorAnnc) {
		this.uvSensorAnnc = uvSensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(uvSensorAnnc);
	}
	
	public UvSensorFlexContainerAnnc getUvSensorAnnc() {
		this.uvSensorAnnc = (UvSensorFlexContainerAnnc) getResourceByName(UvSensorFlexContainerAnnc.SHORT_NAME);
		return uvSensorAnnc;
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
	
	@XmlElement(name=TimerFlexContainerAnnc.SHORT_NAME, required=true, type=TimerFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TimerFlexContainerAnnc timerAnnc;
		
	public void setTimerAnnc(TimerFlexContainerAnnc timerAnnc) {
		this.timerAnnc = timerAnnc;
		getFlexContainerOrContainerOrSubscription().add(timerAnnc);
	}
	
	public TimerFlexContainerAnnc getTimerAnnc() {
		this.timerAnnc = (TimerFlexContainerAnnc) getResourceByName(TimerFlexContainerAnnc.SHORT_NAME);
		return timerAnnc;
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
	
	@XmlElement(name=FaultDetectionFlexContainerAnnc.SHORT_NAME, required=true, type=FaultDetectionFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private FaultDetectionFlexContainerAnnc faultDetectionAnnc;
		
	public void setFaultDetectionAnnc(FaultDetectionFlexContainerAnnc faultDetectionAnnc) {
		this.faultDetectionAnnc = faultDetectionAnnc;
		getFlexContainerOrContainerOrSubscription().add(faultDetectionAnnc);
	}
	
	public FaultDetectionFlexContainerAnnc getFaultDetectionAnnc() {
		this.faultDetectionAnnc = (FaultDetectionFlexContainerAnnc) getResourceByName(FaultDetectionFlexContainerAnnc.SHORT_NAME);
		return faultDetectionAnnc;
	}
	
}
