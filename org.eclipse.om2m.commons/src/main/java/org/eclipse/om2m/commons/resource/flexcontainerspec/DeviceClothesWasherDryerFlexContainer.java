/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceClothesWasherDryer

A clothes washer dryer is a home appliance that is a combination of cloth washer and cloth dryer in a single cabinet. This information model provides capabilities to interact with specific functions and resources of clothes washers and dryers.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceClothesWasherDryerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceClothesWasherDryerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceClothesWasherDryerFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceClothesWasherDryer";
	public static final String SHORT_NAME = "dCWDr";
	
	public DeviceClothesWasherDryerFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceClothesWasherDryerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getBinarySwitch();
		getClothesWasherDryerOperationMode();
		getRunState();
		getClothesWasherDryerJobMode();
		getClothesWasherJobModeOption();
		getRemoteControlEnable();
		getTimer();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.clothesWasherDryerOperationMode != null) {
			setClothesWasherDryerOperationMode(this.clothesWasherDryerOperationMode);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.clothesWasherDryerJobMode != null) {
			setClothesWasherDryerJobMode(this.clothesWasherDryerJobMode);
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
	private OperationModeFlexContainer clothesWasherDryerOperationMode;
		
	public void setClothesWasherDryerOperationMode(OperationModeFlexContainer clothesWasherDryerOperationMode) {
		this.clothesWasherDryerOperationMode = clothesWasherDryerOperationMode;
		getFlexContainerOrContainerOrSubscription().add(clothesWasherDryerOperationMode);
	}
	
	public OperationModeFlexContainer getClothesWasherDryerOperationMode() {
		this.clothesWasherDryerOperationMode = (OperationModeFlexContainer) getResourceByName(OperationModeFlexContainer.SHORT_NAME);
		return clothesWasherDryerOperationMode;
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
	
	@XmlElement(name=ClothesWasherDryerJobModeFlexContainer.SHORT_NAME, required=true, type=ClothesWasherDryerJobModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ClothesWasherDryerJobModeFlexContainer clothesWasherDryerJobMode;
		
	public void setClothesWasherDryerJobMode(ClothesWasherDryerJobModeFlexContainer clothesWasherDryerJobMode) {
		this.clothesWasherDryerJobMode = clothesWasherDryerJobMode;
		getFlexContainerOrContainerOrSubscription().add(clothesWasherDryerJobMode);
	}
	
	public ClothesWasherDryerJobModeFlexContainer getClothesWasherDryerJobMode() {
		this.clothesWasherDryerJobMode = (ClothesWasherDryerJobModeFlexContainer) getResourceByName(ClothesWasherDryerJobModeFlexContainer.SHORT_NAME);
		return clothesWasherDryerJobMode;
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
