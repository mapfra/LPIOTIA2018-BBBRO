/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceHomeCCTVAnnc

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
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DeviceHomeCCTVFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceHomeCCTVFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceHomeCCTVFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceHomeCCTVAnnc";
	public static final String SHORT_NAME = "dHCCTAnnc";
	
	public DeviceHomeCCTVFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceHomeCCTVFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getSessionDescription();
		getSessionDescriptionAnnc();
		getPlayerControl();
		getPlayerControlAnnc();
		getRunState();
		getRunStateAnnc();
		getMotionSensor();
		getMotionSensorAnnc();
		getAirQualitySensor();
		getAirQualitySensorAnnc();
		getOzoneMeter();
		getOzoneMeterAnnc();
		getSmokeSensor();
		getSmokeSensorAnnc();
		getAcousticSensor();
		getAcousticSensorAnnc();
		getImpactSensor();
		getImpactSensorAnnc();
		getFaultDetection();
		getFaultDetectionAnnc();
		getAlarmSpeaker();
		getAlarmSpeakerAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.binarySwitchAnnc != null) {
			setBinarySwitchAnnc(this.binarySwitchAnnc);
		}
		if (this.sessionDescription != null) {
			setSessionDescription(this.sessionDescription);
		}
		if (this.sessionDescriptionAnnc != null) {
			setSessionDescriptionAnnc(this.sessionDescriptionAnnc);
		}
		if (this.playerControl != null) {
			setPlayerControl(this.playerControl);
		}
		if (this.playerControlAnnc != null) {
			setPlayerControlAnnc(this.playerControlAnnc);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.runStateAnnc != null) {
			setRunStateAnnc(this.runStateAnnc);
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
		if (this.ozoneMeter != null) {
			setOzoneMeter(this.ozoneMeter);
		}
		if (this.ozoneMeterAnnc != null) {
			setOzoneMeterAnnc(this.ozoneMeterAnnc);
		}
		if (this.smokeSensor != null) {
			setSmokeSensor(this.smokeSensor);
		}
		if (this.smokeSensorAnnc != null) {
			setSmokeSensorAnnc(this.smokeSensorAnnc);
		}
		if (this.acousticSensor != null) {
			setAcousticSensor(this.acousticSensor);
		}
		if (this.acousticSensorAnnc != null) {
			setAcousticSensorAnnc(this.acousticSensorAnnc);
		}
		if (this.impactSensor != null) {
			setImpactSensor(this.impactSensor);
		}
		if (this.impactSensorAnnc != null) {
			setImpactSensorAnnc(this.impactSensorAnnc);
		}
		if (this.faultDetection != null) {
			setFaultDetection(this.faultDetection);
		}
		if (this.faultDetectionAnnc != null) {
			setFaultDetectionAnnc(this.faultDetectionAnnc);
		}
		if (this.alarmSpeaker != null) {
			setAlarmSpeaker(this.alarmSpeaker);
		}
		if (this.alarmSpeakerAnnc != null) {
			setAlarmSpeakerAnnc(this.alarmSpeakerAnnc);
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
	
	@XmlElement(name=SessionDescriptionFlexContainerAnnc.SHORT_NAME, required=true, type=SessionDescriptionFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private SessionDescriptionFlexContainerAnnc sessionDescriptionAnnc;
		
	public void setSessionDescriptionAnnc(SessionDescriptionFlexContainerAnnc sessionDescriptionAnnc) {
		this.sessionDescriptionAnnc = sessionDescriptionAnnc;
		getFlexContainerOrContainerOrSubscription().add(sessionDescriptionAnnc);
	}
	
	public SessionDescriptionFlexContainerAnnc getSessionDescriptionAnnc() {
		this.sessionDescriptionAnnc = (SessionDescriptionFlexContainerAnnc) getResourceByName(SessionDescriptionFlexContainerAnnc.SHORT_NAME);
		return sessionDescriptionAnnc;
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
	
	@XmlElement(name=PlayerControlFlexContainerAnnc.SHORT_NAME, required=true, type=PlayerControlFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private PlayerControlFlexContainerAnnc playerControlAnnc;
		
	public void setPlayerControlAnnc(PlayerControlFlexContainerAnnc playerControlAnnc) {
		this.playerControlAnnc = playerControlAnnc;
		getFlexContainerOrContainerOrSubscription().add(playerControlAnnc);
	}
	
	public PlayerControlFlexContainerAnnc getPlayerControlAnnc() {
		this.playerControlAnnc = (PlayerControlFlexContainerAnnc) getResourceByName(PlayerControlFlexContainerAnnc.SHORT_NAME);
		return playerControlAnnc;
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
	
	@XmlElement(name=RunStateFlexContainerAnnc.SHORT_NAME, required=true, type=RunStateFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private RunStateFlexContainerAnnc runStateAnnc;
		
	public void setRunStateAnnc(RunStateFlexContainerAnnc runStateAnnc) {
		this.runStateAnnc = runStateAnnc;
		getFlexContainerOrContainerOrSubscription().add(runStateAnnc);
	}
	
	public RunStateFlexContainerAnnc getRunStateAnnc() {
		this.runStateAnnc = (RunStateFlexContainerAnnc) getResourceByName(RunStateFlexContainerAnnc.SHORT_NAME);
		return runStateAnnc;
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
	
	@XmlElement(name=OzoneMeterFlexContainerAnnc.SHORT_NAME, required=true, type=OzoneMeterFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private OzoneMeterFlexContainerAnnc ozoneMeterAnnc;
		
	public void setOzoneMeterAnnc(OzoneMeterFlexContainerAnnc ozoneMeterAnnc) {
		this.ozoneMeterAnnc = ozoneMeterAnnc;
		getFlexContainerOrContainerOrSubscription().add(ozoneMeterAnnc);
	}
	
	public OzoneMeterFlexContainerAnnc getOzoneMeterAnnc() {
		this.ozoneMeterAnnc = (OzoneMeterFlexContainerAnnc) getResourceByName(OzoneMeterFlexContainerAnnc.SHORT_NAME);
		return ozoneMeterAnnc;
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
	
	@XmlElement(name=SmokeSensorFlexContainerAnnc.SHORT_NAME, required=true, type=SmokeSensorFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private SmokeSensorFlexContainerAnnc smokeSensorAnnc;
		
	public void setSmokeSensorAnnc(SmokeSensorFlexContainerAnnc smokeSensorAnnc) {
		this.smokeSensorAnnc = smokeSensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(smokeSensorAnnc);
	}
	
	public SmokeSensorFlexContainerAnnc getSmokeSensorAnnc() {
		this.smokeSensorAnnc = (SmokeSensorFlexContainerAnnc) getResourceByName(SmokeSensorFlexContainerAnnc.SHORT_NAME);
		return smokeSensorAnnc;
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
	
	@XmlElement(name=AcousticSensorFlexContainerAnnc.SHORT_NAME, required=true, type=AcousticSensorFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AcousticSensorFlexContainerAnnc acousticSensorAnnc;
		
	public void setAcousticSensorAnnc(AcousticSensorFlexContainerAnnc acousticSensorAnnc) {
		this.acousticSensorAnnc = acousticSensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(acousticSensorAnnc);
	}
	
	public AcousticSensorFlexContainerAnnc getAcousticSensorAnnc() {
		this.acousticSensorAnnc = (AcousticSensorFlexContainerAnnc) getResourceByName(AcousticSensorFlexContainerAnnc.SHORT_NAME);
		return acousticSensorAnnc;
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
	
	@XmlElement(name=ImpactSensorFlexContainerAnnc.SHORT_NAME, required=true, type=ImpactSensorFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ImpactSensorFlexContainerAnnc impactSensorAnnc;
		
	public void setImpactSensorAnnc(ImpactSensorFlexContainerAnnc impactSensorAnnc) {
		this.impactSensorAnnc = impactSensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(impactSensorAnnc);
	}
	
	public ImpactSensorFlexContainerAnnc getImpactSensorAnnc() {
		this.impactSensorAnnc = (ImpactSensorFlexContainerAnnc) getResourceByName(ImpactSensorFlexContainerAnnc.SHORT_NAME);
		return impactSensorAnnc;
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
	
	@XmlElement(name=AlarmSpeakerFlexContainerAnnc.SHORT_NAME, required=true, type=AlarmSpeakerFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AlarmSpeakerFlexContainerAnnc alarmSpeakerAnnc;
		
	public void setAlarmSpeakerAnnc(AlarmSpeakerFlexContainerAnnc alarmSpeakerAnnc) {
		this.alarmSpeakerAnnc = alarmSpeakerAnnc;
		getFlexContainerOrContainerOrSubscription().add(alarmSpeakerAnnc);
	}
	
	public AlarmSpeakerFlexContainerAnnc getAlarmSpeakerAnnc() {
		this.alarmSpeakerAnnc = (AlarmSpeakerFlexContainerAnnc) getResourceByName(AlarmSpeakerFlexContainerAnnc.SHORT_NAME);
		return alarmSpeakerAnnc;
	}
	
}
