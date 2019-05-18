/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceRobotCleanerAnnc

A robot cleaner is an autonomous robotic vacuum cleaner that has intelligent programming and a limited vacuum cleaning system. This robot cleaner information model provides capabilities to control and monitor robot cleaner specific functions and resources.

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

@XmlRootElement(name = DeviceRobotCleanerFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceRobotCleanerFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceRobotCleanerFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceRobotCleanerAnnc";
	public static final String SHORT_NAME = "deRCrAnnc";
	
	public DeviceRobotCleanerFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceRobotCleanerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getRunState();
		getRunStateAnnc();
		getRobotCleanerJobMode();
		getRobotCleanerJobModeAnnc();
		getBattery();
		getBatteryAnnc();
		getTimer();
		getTimerAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.binarySwitchAnnc != null) {
			setBinarySwitchAnnc(this.binarySwitchAnnc);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.runStateAnnc != null) {
			setRunStateAnnc(this.runStateAnnc);
		}
		if (this.robotCleanerJobMode != null) {
			setRobotCleanerJobMode(this.robotCleanerJobMode);
		}
		if (this.robotCleanerJobModeAnnc != null) {
			setRobotCleanerJobModeAnnc(this.robotCleanerJobModeAnnc);
		}
		if (this.battery != null) {
			setBattery(this.battery);
		}
		if (this.batteryAnnc != null) {
			setBatteryAnnc(this.batteryAnnc);
		}
		if (this.timer != null) {
			setTimer(this.timer);
		}
		if (this.timerAnnc != null) {
			setTimerAnnc(this.timerAnnc);
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
	
	@XmlElement(name=RobotCleanerJobModeFlexContainer.SHORT_NAME, required=true, type=RobotCleanerJobModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private RobotCleanerJobModeFlexContainer robotCleanerJobMode;
		
	public void setRobotCleanerJobMode(RobotCleanerJobModeFlexContainer robotCleanerJobMode) {
		this.robotCleanerJobMode = robotCleanerJobMode;
		getFlexContainerOrContainerOrSubscription().add(robotCleanerJobMode);
	}
	
	public RobotCleanerJobModeFlexContainer getRobotCleanerJobMode() {
		this.robotCleanerJobMode = (RobotCleanerJobModeFlexContainer) getResourceByName(RobotCleanerJobModeFlexContainer.SHORT_NAME);
		return robotCleanerJobMode;
	}
	
	@XmlElement(name=RobotCleanerJobModeFlexContainerAnnc.SHORT_NAME, required=true, type=RobotCleanerJobModeFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private RobotCleanerJobModeFlexContainerAnnc robotCleanerJobModeAnnc;
		
	public void setRobotCleanerJobModeAnnc(RobotCleanerJobModeFlexContainerAnnc robotCleanerJobModeAnnc) {
		this.robotCleanerJobModeAnnc = robotCleanerJobModeAnnc;
		getFlexContainerOrContainerOrSubscription().add(robotCleanerJobModeAnnc);
	}
	
	public RobotCleanerJobModeFlexContainerAnnc getRobotCleanerJobModeAnnc() {
		this.robotCleanerJobModeAnnc = (RobotCleanerJobModeFlexContainerAnnc) getResourceByName(RobotCleanerJobModeFlexContainerAnnc.SHORT_NAME);
		return robotCleanerJobModeAnnc;
	}
	
	@XmlElement(name=BatteryFlexContainer.SHORT_NAME, required=true, type=BatteryFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BatteryFlexContainer battery;
		
	public void setBattery(BatteryFlexContainer battery) {
		this.battery = battery;
		getFlexContainerOrContainerOrSubscription().add(battery);
	}
	
	public BatteryFlexContainer getBattery() {
		this.battery = (BatteryFlexContainer) getResourceByName(BatteryFlexContainer.SHORT_NAME);
		return battery;
	}
	
	@XmlElement(name=BatteryFlexContainerAnnc.SHORT_NAME, required=true, type=BatteryFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BatteryFlexContainerAnnc batteryAnnc;
		
	public void setBatteryAnnc(BatteryFlexContainerAnnc batteryAnnc) {
		this.batteryAnnc = batteryAnnc;
		getFlexContainerOrContainerOrSubscription().add(batteryAnnc);
	}
	
	public BatteryFlexContainerAnnc getBatteryAnnc() {
		this.batteryAnnc = (BatteryFlexContainerAnnc) getResourceByName(BatteryFlexContainerAnnc.SHORT_NAME);
		return batteryAnnc;
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
	
}
