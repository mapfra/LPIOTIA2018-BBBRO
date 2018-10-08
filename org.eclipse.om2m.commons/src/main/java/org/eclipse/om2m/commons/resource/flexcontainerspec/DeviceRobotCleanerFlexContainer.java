/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceRobotCleaner

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

@XmlRootElement(name = DeviceRobotCleanerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceRobotCleanerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceRobotCleanerFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceRobotCleaner";
	public static final String SHORT_NAME = "deRCr";
	
	public DeviceRobotCleanerFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceRobotCleanerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getBinarySwitch();
		getRunState();
		getRobotCleanerJobMode();
		getRobotCleanerOperationMode();
		getBattery();
		getTimer();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.robotCleanerJobMode != null) {
			setRobotCleanerJobMode(this.robotCleanerJobMode);
		}
		if (this.robotCleanerOperationMode != null) {
			setRobotCleanerOperationMode(this.robotCleanerOperationMode);
		}
		if (this.battery != null) {
			setBattery(this.battery);
		}
		if (this.timer != null) {
			setTimer(this.timer);
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
	
	@XmlElement(name=OperationModeFlexContainer.SHORT_NAME, required=true, type=OperationModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private OperationModeFlexContainer robotCleanerOperationMode;
		
	public void setRobotCleanerOperationMode(OperationModeFlexContainer robotCleanerOperationMode) {
		this.robotCleanerOperationMode = robotCleanerOperationMode;
		getFlexContainerOrContainerOrSubscription().add(robotCleanerOperationMode);
	}
	
	public OperationModeFlexContainer getRobotCleanerOperationMode() {
		this.robotCleanerOperationMode = (OperationModeFlexContainer) getResourceByName(OperationModeFlexContainer.SHORT_NAME);
		return robotCleanerOperationMode;
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
	
}
