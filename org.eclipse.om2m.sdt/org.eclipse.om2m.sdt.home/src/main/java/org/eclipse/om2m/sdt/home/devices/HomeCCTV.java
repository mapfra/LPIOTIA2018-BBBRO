/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.devices;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.home.modules.AcousticSensor;
import org.eclipse.om2m.sdt.home.modules.AirQualitySensor;
import org.eclipse.om2m.sdt.home.modules.AlarmSpeaker;
import org.eclipse.om2m.sdt.home.modules.BinarySwitch;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.modules.MotionSensor;
import org.eclipse.om2m.sdt.home.modules.PlayerControl;
import org.eclipse.om2m.sdt.home.modules.RunState;
import org.eclipse.om2m.sdt.home.modules.SessionDescription;
import org.eclipse.om2m.sdt.home.modules.SmokeSensor;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class HomeCCTV extends GenericDevice {

	private BinarySwitch binarySwitch;
	private SessionDescription sessionDescription;
	private PlayerControl playerControl;
	private RunState runState;
	private MotionSensor motionSensor;
	private AirQualitySensor airQualitySensor;
//	private OzoneMeter ozoneMeter;
	private SmokeSensor smokeSensor;
	private AcousticSensor acousticSensor;
//	private ImpactSensor impactSensor;
	private FaultDetection faultDetection;
	private AlarmSpeaker alarmSpeaker;
;

	public HomeCCTV(String id, String serial, Domain domain) {
		super(id, serial, DeviceType.deviceHomeCCTV, domain);
	}
	
	public void addModule(Module module) {
		if (module instanceof BinarySwitch)
			setBinarySwitch((BinarySwitch)module);
		else if (module instanceof SessionDescription)
			setSessionDescription((SessionDescription)module);
		else if (module instanceof PlayerControl)
			setPlayerControl((PlayerControl)module);
		else if (module instanceof RunState)
			setRunState((RunState)module);
		else if (module instanceof MotionSensor)
			setMotionSensor((MotionSensor)module);
		else if (module instanceof AirQualitySensor)
			setAirQualitySensor((AirQualitySensor)module);
		else if (module instanceof SmokeSensor)
			setSmokeSensor((SmokeSensor)module);
		else if (module instanceof AcousticSensor)
			setAcousticSensor((AcousticSensor)module);
		else if (module instanceof FaultDetection)
			setFaultDetection((FaultDetection)module);
		else if (module instanceof AlarmSpeaker)
			setAlarmSpeaker((AlarmSpeaker)module);
		else 
			super.addModule(module);
	}

	public void setBinarySwitch(BinarySwitch pBinarySwitch) {
		this.binarySwitch = pBinarySwitch;
		super.addModule(binarySwitch);
	}

	public void unsetBinarySwitch(BinarySwitch pBinarySwitch) {
		if (binarySwitch != null) {
			removeModule(binarySwitch.getName());
			this.binarySwitch = null;
		}
	}

	public BinarySwitch getBinarySwitch() {
		return binarySwitch;
	}

	public void setSessionDescription(SessionDescription pSessionDescription) {
		this.sessionDescription = pSessionDescription;
		super.addModule(sessionDescription);
	}

	public void unsetSessionDescription(SessionDescription pSessionDescription) {
		if (sessionDescription != null) {
			removeModule(sessionDescription.getName());
			this.sessionDescription = null;
		}
	}

	public SessionDescription getSessionDescription() {
		return sessionDescription;
	}

	public void setPlayerControl(PlayerControl pPlayerControl) {
		this.playerControl = pPlayerControl;
		super.addModule(playerControl);
	}

	public void unsetPlayerControl(PlayerControl pPlayerControl) {
		if (playerControl != null) {
			removeModule(playerControl.getName());
			this.playerControl = null;
		}
	}

	public PlayerControl getPlayerControl() {
		return playerControl;
	}

	public void setRunState(RunState pRunState) {
		this.runState = pRunState;
		super.addModule(runState);
	}

	public void unsetRunState(RunState runState) {
		if (runState != null) {
			removeModule(runState.getName());
			this.runState = null;
		}
	}
	
	public RunState getRunState() {
		return runState;
	}
	
	public void setMotionSensor(MotionSensor pMotionSensor) {
		this.motionSensor = pMotionSensor;
		super.addModule(motionSensor);
	}
	
	public MotionSensor getMotionSensor() {
		return motionSensor;
	}

	public void setAlarmSpeaker(AlarmSpeaker alarmSpeaker) {
		this.alarmSpeaker = alarmSpeaker;
		super.addModule(alarmSpeaker);
	}

	public AlarmSpeaker getAlarmSpeaker() {
		return alarmSpeaker;
	}
	
	public void setFaultDetection(FaultDetection faultDetection) {
		this.faultDetection = faultDetection;
		super.addModule(faultDetection);
	}
	
	public FaultDetection getFaultDetection() {
		return faultDetection;
	}

	public void setAcousticSensor(AcousticSensor acousticSensor) {
		this.acousticSensor = acousticSensor;
		super.addModule(acousticSensor);
	}

	public AcousticSensor getAcousticSensor() {
		return acousticSensor;
	}

	public SmokeSensor setSmokeSensor() {
		return smokeSensor;
	}

	public void setSmokeSensor(SmokeSensor smokeSensor) {
		this.smokeSensor = smokeSensor;
		super.addModule(smokeSensor);
	}

	public void setAirQualitySensor(AirQualitySensor airQualitySensor) {
		this.airQualitySensor = airQualitySensor;
		super.addModule(airQualitySensor);
	}

	public AirQualitySensor getAirQualitySensor() {
		return airQualitySensor;
	}
	
}
