/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceHomeCCTV

A home CCTV is a smart home appliance to provide monitoring capabilities when people stay way from their home or a room, or to monitor the environmental status of their home or room.

Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceHomeCCTVFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceHomeCCTVFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceHomeCCTVFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceHomeCCTV";
	public static final String SHORT_NAME = "dHCCT";
	
	public DeviceHomeCCTVFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceHomeCCTVFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getBinarySwitch();
		getSessionDescription();
		getPlayerControl();
		getRunState();
		getMotionSensor();
		getAirQualitySensor();
		getOzoneMeter();
		getSmokeSensor();
		getAcousticSensor();
		getImpactSensor();
		getFaultDetection();
		getAlarmSpeaker();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.sessionDescription != null) {
			setSessionDescription(this.sessionDescription);
		}
		if (this.playerControl != null) {
			setPlayerControl(this.playerControl);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.motionSensor != null) {
			setMotionSensor(this.motionSensor);
		}
		if (this.airQualitySensor != null) {
			setAirQualitySensor(this.airQualitySensor);
		}
		if (this.ozoneMeter != null) {
			setOzoneMeter(this.ozoneMeter);
		}
		if (this.smokeSensor != null) {
			setSmokeSensor(this.smokeSensor);
		}
		if (this.acousticSensor != null) {
			setAcousticSensor(this.acousticSensor);
		}
		if (this.impactSensor != null) {
			setImpactSensor(this.impactSensor);
		}
		if (this.faultDetection != null) {
			setFaultDetection(this.faultDetection);
		}
		if (this.alarmSpeaker != null) {
			setAlarmSpeaker(this.alarmSpeaker);
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
	
	@XmlElement(name=SessionDescriptionFlexContainer.SHORT_NAME, required=true, type=SessionDescriptionFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private SessionDescriptionFlexContainer sessionDescription;
		
	public void setSessionDescription(SessionDescriptionFlexContainer sessionDescription) {
		this.sessionDescription = sessionDescription;
		getFlexContainerOrContainerOrSubscription().add(sessionDescription);
	}
	
	public SessionDescriptionFlexContainer getSessionDescription() {
		this.sessionDescription = (SessionDescriptionFlexContainer) getResourceByName(SessionDescriptionFlexContainer.SHORT_NAME);
		return sessionDescription;
	}
	
	@XmlElement(name=PlayerControlFlexContainer.SHORT_NAME, required=true, type=PlayerControlFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private PlayerControlFlexContainer playerControl;
		
	public void setPlayerControl(PlayerControlFlexContainer playerControl) {
		this.playerControl = playerControl;
		getFlexContainerOrContainerOrSubscription().add(playerControl);
	}
	
	public PlayerControlFlexContainer getPlayerControl() {
		this.playerControl = (PlayerControlFlexContainer) getResourceByName(PlayerControlFlexContainer.SHORT_NAME);
		return playerControl;
	}
	
	@XmlElement(name=RunStateFlexContainer.SHORT_NAME, required=true, type=RunStateFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private RunStateFlexContainer runState;
		
	public void setRunState(RunStateFlexContainer runState) {
		this.runState = runState;
		getFlexContainerOrContainerOrSubscription().add(runState);
	}
	
	public RunStateFlexContainer getRunState() {
		this.runState = (RunStateFlexContainer) getResourceByName(RunStateFlexContainer.SHORT_NAME);
		return runState;
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
	
	@XmlElement(name=OzoneMeterFlexContainer.SHORT_NAME, required=true, type=OzoneMeterFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private OzoneMeterFlexContainer ozoneMeter;
		
	public void setOzoneMeter(OzoneMeterFlexContainer ozoneMeter) {
		this.ozoneMeter = ozoneMeter;
		getFlexContainerOrContainerOrSubscription().add(ozoneMeter);
	}
	
	public OzoneMeterFlexContainer getOzoneMeter() {
		this.ozoneMeter = (OzoneMeterFlexContainer) getResourceByName(OzoneMeterFlexContainer.SHORT_NAME);
		return ozoneMeter;
	}
	
	@XmlElement(name=SmokeSensorFlexContainer.SHORT_NAME, required=true, type=SmokeSensorFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private SmokeSensorFlexContainer smokeSensor;
		
	public void setSmokeSensor(SmokeSensorFlexContainer smokeSensor) {
		this.smokeSensor = smokeSensor;
		getFlexContainerOrContainerOrSubscription().add(smokeSensor);
	}
	
	public SmokeSensorFlexContainer getSmokeSensor() {
		this.smokeSensor = (SmokeSensorFlexContainer) getResourceByName(SmokeSensorFlexContainer.SHORT_NAME);
		return smokeSensor;
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
	
	@XmlElement(name=ImpactSensorFlexContainer.SHORT_NAME, required=true, type=ImpactSensorFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ImpactSensorFlexContainer impactSensor;
		
	public void setImpactSensor(ImpactSensorFlexContainer impactSensor) {
		this.impactSensor = impactSensor;
		getFlexContainerOrContainerOrSubscription().add(impactSensor);
	}
	
	public ImpactSensorFlexContainer getImpactSensor() {
		this.impactSensor = (ImpactSensorFlexContainer) getResourceByName(ImpactSensorFlexContainer.SHORT_NAME);
		return impactSensor;
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
	
	@XmlElement(name=AlarmSpeakerFlexContainer.SHORT_NAME, required=true, type=AlarmSpeakerFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AlarmSpeakerFlexContainer alarmSpeaker;
		
	public void setAlarmSpeaker(AlarmSpeakerFlexContainer alarmSpeaker) {
		this.alarmSpeaker = alarmSpeaker;
		getFlexContainerOrContainerOrSubscription().add(alarmSpeaker);
	}
	
	public AlarmSpeakerFlexContainer getAlarmSpeaker() {
		this.alarmSpeaker = (AlarmSpeakerFlexContainer) getResourceByName(AlarmSpeakerFlexContainer.SHORT_NAME);
		return alarmSpeaker;
	}
	
}
