/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceCookerHood

A cooker hood is a device containing a mechanical fan that hangs above the stove or cooktop in the kitchen.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceCookerHoodFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceCookerHoodFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceCookerHoodFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceCookerHood";
	public static final String SHORT_NAME = "deCHd";
	
	public DeviceCookerHoodFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceCookerHoodFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getBinarySwitch();
		getRunState();
		getAirFlow();
		getCookerHoodJobMode();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.airFlow != null) {
			setAirFlow(this.airFlow);
		}
		if (this.cookerHoodJobMode != null) {
			setCookerHoodJobMode(this.cookerHoodJobMode);
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
	
}
