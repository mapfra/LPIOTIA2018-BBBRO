/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceClothesDryerAnnc

A clothes dryer is a home appliance for drying clothes. This clothesDryer information model provides capabilities to control and monitor clothes dryer specific functions and resources.

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

@XmlRootElement(name = DeviceClothesDryerFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceClothesDryerFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceClothesDryerFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceClothesDryerAnnc";
	public static final String SHORT_NAME = "deCDrAnnc";
	
	public DeviceClothesDryerFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceClothesDryerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getRunState();
		getRunStateAnnc();
		getClothesDryerJobMode();
		getClothesDryerJobModeAnnc();
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
		if (this.clothesDryerJobMode != null) {
			setClothesDryerJobMode(this.clothesDryerJobMode);
		}
		if (this.clothesDryerJobModeAnnc != null) {
			setClothesDryerJobModeAnnc(this.clothesDryerJobModeAnnc);
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
	
	@XmlElement(name=ClothesDryerJobModeFlexContainer.SHORT_NAME, required=true, type=ClothesDryerJobModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ClothesDryerJobModeFlexContainer clothesDryerJobMode;
		
	public void setClothesDryerJobMode(ClothesDryerJobModeFlexContainer clothesDryerJobMode) {
		this.clothesDryerJobMode = clothesDryerJobMode;
		getFlexContainerOrContainerOrSubscription().add(clothesDryerJobMode);
	}
	
	public ClothesDryerJobModeFlexContainer getClothesDryerJobMode() {
		this.clothesDryerJobMode = (ClothesDryerJobModeFlexContainer) getResourceByName(ClothesDryerJobModeFlexContainer.SHORT_NAME);
		return clothesDryerJobMode;
	}
	
	@XmlElement(name=ClothesDryerJobModeFlexContainerAnnc.SHORT_NAME, required=true, type=ClothesDryerJobModeFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ClothesDryerJobModeFlexContainerAnnc clothesDryerJobModeAnnc;
		
	public void setClothesDryerJobModeAnnc(ClothesDryerJobModeFlexContainerAnnc clothesDryerJobModeAnnc) {
		this.clothesDryerJobModeAnnc = clothesDryerJobModeAnnc;
		getFlexContainerOrContainerOrSubscription().add(clothesDryerJobModeAnnc);
	}
	
	public ClothesDryerJobModeFlexContainerAnnc getClothesDryerJobModeAnnc() {
		this.clothesDryerJobModeAnnc = (ClothesDryerJobModeFlexContainerAnnc) getResourceByName(ClothesDryerJobModeFlexContainerAnnc.SHORT_NAME);
		return clothesDryerJobModeAnnc;
	}
	
}
