/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceClothesWasher

A clothes washer is a home appliance that is used to wash laundry, such as clothing and sheets. This information model provides capabilities to interact with specific functions and resources of clothes washers.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceClothesWasherFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceClothesWasherFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceClothesWasherFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceClothesWasher";
	public static final String SHORT_NAME = "deCWr";
	
	public DeviceClothesWasherFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceClothesWasherFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getBinarySwitch();
		getClothesWasherOperationMode();
		getRunState();
		getClothesWasherJobMode();
		getClothesWasherJobModeOption();
		getRemoteControlEnable();
		getTimer();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.clothesWasherOperationMode != null) {
			setClothesWasherOperationMode(this.clothesWasherOperationMode);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.clothesWasherJobMode != null) {
			setClothesWasherJobMode(this.clothesWasherJobMode);
		}
		if (this.clothesWasherJobModeOption != null) {
			setClothesWasherJobModeOption(this.clothesWasherJobModeOption);
		}
		if (this.remoteControlEnable != null) {
			setRemoteControlEnable(this.remoteControlEnable);
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
	
	@XmlElement(name=OperationModeFlexContainer.SHORT_NAME, required=true, type=OperationModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private OperationModeFlexContainer clothesWasherOperationMode;
		
	public void setClothesWasherOperationMode(OperationModeFlexContainer clothesWasherOperationMode) {
		this.clothesWasherOperationMode = clothesWasherOperationMode;
		getFlexContainerOrContainerOrSubscription().add(clothesWasherOperationMode);
	}
	
	public OperationModeFlexContainer getClothesWasherOperationMode() {
		this.clothesWasherOperationMode = (OperationModeFlexContainer) getResourceByName(OperationModeFlexContainer.SHORT_NAME);
		return clothesWasherOperationMode;
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
	
	@XmlElement(name=ClothesWasherJobModeFlexContainer.SHORT_NAME, required=true, type=ClothesWasherJobModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ClothesWasherJobModeFlexContainer clothesWasherJobMode;
		
	public void setClothesWasherJobMode(ClothesWasherJobModeFlexContainer clothesWasherJobMode) {
		this.clothesWasherJobMode = clothesWasherJobMode;
		getFlexContainerOrContainerOrSubscription().add(clothesWasherJobMode);
	}
	
	public ClothesWasherJobModeFlexContainer getClothesWasherJobMode() {
		this.clothesWasherJobMode = (ClothesWasherJobModeFlexContainer) getResourceByName(ClothesWasherJobModeFlexContainer.SHORT_NAME);
		return clothesWasherJobMode;
	}
	
	@XmlElement(name=ClothesWasherJobModeOptionFlexContainer.SHORT_NAME, required=true, type=ClothesWasherJobModeOptionFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ClothesWasherJobModeOptionFlexContainer clothesWasherJobModeOption;
		
	public void setClothesWasherJobModeOption(ClothesWasherJobModeOptionFlexContainer clothesWasherJobModeOption) {
		this.clothesWasherJobModeOption = clothesWasherJobModeOption;
		getFlexContainerOrContainerOrSubscription().add(clothesWasherJobModeOption);
	}
	
	public ClothesWasherJobModeOptionFlexContainer getClothesWasherJobModeOption() {
		this.clothesWasherJobModeOption = (ClothesWasherJobModeOptionFlexContainer) getResourceByName(ClothesWasherJobModeOptionFlexContainer.SHORT_NAME);
		return clothesWasherJobModeOption;
	}
	
	@XmlElement(name=RemoteControlEnableFlexContainer.SHORT_NAME, required=true, type=RemoteControlEnableFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private RemoteControlEnableFlexContainer remoteControlEnable;
		
	public void setRemoteControlEnable(RemoteControlEnableFlexContainer remoteControlEnable) {
		this.remoteControlEnable = remoteControlEnable;
		getFlexContainerOrContainerOrSubscription().add(remoteControlEnable);
	}
	
	public RemoteControlEnableFlexContainer getRemoteControlEnable() {
		this.remoteControlEnable = (RemoteControlEnableFlexContainer) getResourceByName(RemoteControlEnableFlexContainer.SHORT_NAME);
		return remoteControlEnable;
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
