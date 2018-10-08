/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceKettle

A kettle is a device that is used to heat water.

Created: 2018-06-29 17:19:56
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceKettleFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceKettleFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceKettleFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceKettle";
	public static final String SHORT_NAME = "devKe";
	
	public DeviceKettleFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceKettleFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getFaultDetection();
		getRunState();
		getWaterStatus();
		getBoilingSwitch();
		getKeepWarm();
	}
	
	public void finalizeDeserialization() {
		if (this.faultDetection != null) {
			setFaultDetection(this.faultDetection);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.waterStatus != null) {
			setWaterStatus(this.waterStatus);
		}
		if (this.boilingSwitch != null) {
			setBoilingSwitch(this.boilingSwitch);
		}
		if (this.keepWarm != null) {
			setKeepWarm(this.keepWarm);
		}
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
	
	@XmlElement(name=LiquidRemainingFlexContainer.SHORT_NAME, required=true, type=LiquidRemainingFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private LiquidRemainingFlexContainer waterStatus;
		
	public void setWaterStatus(LiquidRemainingFlexContainer waterStatus) {
		this.waterStatus = waterStatus;
		getFlexContainerOrContainerOrSubscription().add(waterStatus);
	}
	
	public LiquidRemainingFlexContainer getWaterStatus() {
		this.waterStatus = (LiquidRemainingFlexContainer) getResourceByName(LiquidRemainingFlexContainer.SHORT_NAME);
		return waterStatus;
	}
	
	@XmlElement(name=BinarySwitchFlexContainer.SHORT_NAME, required=true, type=BinarySwitchFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BinarySwitchFlexContainer boilingSwitch;
		
	public void setBoilingSwitch(BinarySwitchFlexContainer boilingSwitch) {
		this.boilingSwitch = boilingSwitch;
		getFlexContainerOrContainerOrSubscription().add(boilingSwitch);
	}
	
	public BinarySwitchFlexContainer getBoilingSwitch() {
		this.boilingSwitch = (BinarySwitchFlexContainer) getResourceByName(BinarySwitchFlexContainer.SHORT_NAME);
		return boilingSwitch;
	}
	
	@XmlElement(name=KeepWarmFlexContainer.SHORT_NAME, required=true, type=KeepWarmFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private KeepWarmFlexContainer keepWarm;
		
	public void setKeepWarm(KeepWarmFlexContainer keepWarm) {
		this.keepWarm = keepWarm;
		getFlexContainerOrContainerOrSubscription().add(keepWarm);
	}
	
	public KeepWarmFlexContainer getKeepWarm() {
		this.keepWarm = (KeepWarmFlexContainer) getResourceByName(KeepWarmFlexContainer.SHORT_NAME);
		return keepWarm;
	}
	
}
