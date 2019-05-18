/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceCookerHoodAnnc

A cooker hood is a device containing a mechanical fan that hangs above the stove or cooktop in the kitchen.

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

@XmlRootElement(name = DeviceCookerHoodFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceCookerHoodFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceCookerHoodFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceCookerHoodAnnc";
	public static final String SHORT_NAME = "deCHdAnnc";
	
	public DeviceCookerHoodFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceCookerHoodFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getRunState();
		getRunStateAnnc();
		getAirFlow();
		getAirFlowAnnc();
		getCookerHoodJobMode();
		getCookerHoodJobModeAnnc();
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
		if (this.airFlow != null) {
			setAirFlow(this.airFlow);
		}
		if (this.airFlowAnnc != null) {
			setAirFlowAnnc(this.airFlowAnnc);
		}
		if (this.cookerHoodJobMode != null) {
			setCookerHoodJobMode(this.cookerHoodJobMode);
		}
		if (this.cookerHoodJobModeAnnc != null) {
			setCookerHoodJobModeAnnc(this.cookerHoodJobModeAnnc);
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
	
	@XmlElement(name=AirFlowFlexContainer.SHORT_NAME, required=true, type=AirFlowFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AirFlowFlexContainer airFlow;
		
	public void setAirFlow(AirFlowFlexContainer airFlow) {
		this.airFlow = airFlow;
		getFlexContainerOrContainerOrSubscription().add(airFlow);
	}
	
	public AirFlowFlexContainer getAirFlow() {
		this.airFlow = (AirFlowFlexContainer) getResourceByName(AirFlowFlexContainer.SHORT_NAME);
		return airFlow;
	}
	
	@XmlElement(name=AirFlowFlexContainerAnnc.SHORT_NAME, required=true, type=AirFlowFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AirFlowFlexContainerAnnc airFlowAnnc;
		
	public void setAirFlowAnnc(AirFlowFlexContainerAnnc airFlowAnnc) {
		this.airFlowAnnc = airFlowAnnc;
		getFlexContainerOrContainerOrSubscription().add(airFlowAnnc);
	}
	
	public AirFlowFlexContainerAnnc getAirFlowAnnc() {
		this.airFlowAnnc = (AirFlowFlexContainerAnnc) getResourceByName(AirFlowFlexContainerAnnc.SHORT_NAME);
		return airFlowAnnc;
	}
	
	@XmlElement(name=CookerHoodJobModeFlexContainer.SHORT_NAME, required=true, type=CookerHoodJobModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private CookerHoodJobModeFlexContainer cookerHoodJobMode;
		
	public void setCookerHoodJobMode(CookerHoodJobModeFlexContainer cookerHoodJobMode) {
		this.cookerHoodJobMode = cookerHoodJobMode;
		getFlexContainerOrContainerOrSubscription().add(cookerHoodJobMode);
	}
	
	public CookerHoodJobModeFlexContainer getCookerHoodJobMode() {
		this.cookerHoodJobMode = (CookerHoodJobModeFlexContainer) getResourceByName(CookerHoodJobModeFlexContainer.SHORT_NAME);
		return cookerHoodJobMode;
	}
	
	@XmlElement(name=CookerHoodJobModeFlexContainerAnnc.SHORT_NAME, required=true, type=CookerHoodJobModeFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private CookerHoodJobModeFlexContainerAnnc cookerHoodJobModeAnnc;
		
	public void setCookerHoodJobModeAnnc(CookerHoodJobModeFlexContainerAnnc cookerHoodJobModeAnnc) {
		this.cookerHoodJobModeAnnc = cookerHoodJobModeAnnc;
		getFlexContainerOrContainerOrSubscription().add(cookerHoodJobModeAnnc);
	}
	
	public CookerHoodJobModeFlexContainerAnnc getCookerHoodJobModeAnnc() {
		this.cookerHoodJobModeAnnc = (CookerHoodJobModeFlexContainerAnnc) getResourceByName(CookerHoodJobModeFlexContainerAnnc.SHORT_NAME);
		return cookerHoodJobModeAnnc;
	}
	
}
