/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceClothesWasherDryerAnnc

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
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DeviceClothesWasherDryerFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceClothesWasherDryerFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceClothesWasherDryerFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceClothesWasherDryerAnnc";
	public static final String SHORT_NAME = "dCWDrAnnc";
	
	public DeviceClothesWasherDryerFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceClothesWasherDryerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getRunState();
		getRunStateAnnc();
		getClothesWasherDryerJobMode();
		getClothesWasherDryerJobModeAnnc();
		getClothesWasherJobModeOption();
		getClothesWasherJobModeOptionAnnc();
		getRemoteControlEnable();
		getRemoteControlEnableAnnc();
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
		if (this.clothesWasherDryerJobMode != null) {
			setClothesWasherDryerJobMode(this.clothesWasherDryerJobMode);
		}
		if (this.clothesWasherDryerJobModeAnnc != null) {
			setClothesWasherDryerJobModeAnnc(this.clothesWasherDryerJobModeAnnc);
		}
		if (this.clothesWasherJobModeOption != null) {
			setClothesWasherJobModeOption(this.clothesWasherJobModeOption);
		}
		if (this.clothesWasherJobModeOptionAnnc != null) {
			setClothesWasherJobModeOptionAnnc(this.clothesWasherJobModeOptionAnnc);
		}
		if (this.remoteControlEnable != null) {
			setRemoteControlEnable(this.remoteControlEnable);
		}
		if (this.remoteControlEnableAnnc != null) {
			setRemoteControlEnableAnnc(this.remoteControlEnableAnnc);
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
	
	@XmlElement(name=ClothesWasherDryerJobModeFlexContainerAnnc.SHORT_NAME, required=true, type=ClothesWasherDryerJobModeFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ClothesWasherDryerJobModeFlexContainerAnnc clothesWasherDryerJobModeAnnc;
		
	public void setClothesWasherDryerJobModeAnnc(ClothesWasherDryerJobModeFlexContainerAnnc clothesWasherDryerJobModeAnnc) {
		this.clothesWasherDryerJobModeAnnc = clothesWasherDryerJobModeAnnc;
		getFlexContainerOrContainerOrSubscription().add(clothesWasherDryerJobModeAnnc);
	}
	
	public ClothesWasherDryerJobModeFlexContainerAnnc getClothesWasherDryerJobModeAnnc() {
		this.clothesWasherDryerJobModeAnnc = (ClothesWasherDryerJobModeFlexContainerAnnc) getResourceByName(ClothesWasherDryerJobModeFlexContainerAnnc.SHORT_NAME);
		return clothesWasherDryerJobModeAnnc;
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
	
	@XmlElement(name=ClothesWasherJobModeOptionFlexContainerAnnc.SHORT_NAME, required=true, type=ClothesWasherJobModeOptionFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ClothesWasherJobModeOptionFlexContainerAnnc clothesWasherJobModeOptionAnnc;
		
	public void setClothesWasherJobModeOptionAnnc(ClothesWasherJobModeOptionFlexContainerAnnc clothesWasherJobModeOptionAnnc) {
		this.clothesWasherJobModeOptionAnnc = clothesWasherJobModeOptionAnnc;
		getFlexContainerOrContainerOrSubscription().add(clothesWasherJobModeOptionAnnc);
	}
	
	public ClothesWasherJobModeOptionFlexContainerAnnc getClothesWasherJobModeOptionAnnc() {
		this.clothesWasherJobModeOptionAnnc = (ClothesWasherJobModeOptionFlexContainerAnnc) getResourceByName(ClothesWasherJobModeOptionFlexContainerAnnc.SHORT_NAME);
		return clothesWasherJobModeOptionAnnc;
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
	
	@XmlElement(name=RemoteControlEnableFlexContainerAnnc.SHORT_NAME, required=true, type=RemoteControlEnableFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private RemoteControlEnableFlexContainerAnnc remoteControlEnableAnnc;
		
	public void setRemoteControlEnableAnnc(RemoteControlEnableFlexContainerAnnc remoteControlEnableAnnc) {
		this.remoteControlEnableAnnc = remoteControlEnableAnnc;
		getFlexContainerOrContainerOrSubscription().add(remoteControlEnableAnnc);
	}
	
	public RemoteControlEnableFlexContainerAnnc getRemoteControlEnableAnnc() {
		this.remoteControlEnableAnnc = (RemoteControlEnableFlexContainerAnnc) getResourceByName(RemoteControlEnableFlexContainerAnnc.SHORT_NAME);
		return remoteControlEnableAnnc;
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
