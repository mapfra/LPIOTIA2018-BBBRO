/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceSteamClosetAnnc

A deviceSteamCloset is a home appliance that de-wrinkles, sanitizes and dries to clean fabrics similar to a dry cleaner. This information model provides capabilities to interact with specific functions and resources of the steam closet.

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

@XmlRootElement(name = DeviceSteamClosetFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceSteamClosetFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceSteamClosetFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceSteamClosetAnnc";
	public static final String SHORT_NAME = "deSCtAnnc";
	
	public DeviceSteamClosetFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceSteamClosetFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getRunState();
		getRunStateAnnc();
		getSteamClosetJobMode();
		getSteamClosetJobModeAnnc();
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
		if (this.steamClosetJobMode != null) {
			setSteamClosetJobMode(this.steamClosetJobMode);
		}
		if (this.steamClosetJobModeAnnc != null) {
			setSteamClosetJobModeAnnc(this.steamClosetJobModeAnnc);
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
	
	@XmlElement(name=SteamClosetJobModeFlexContainer.SHORT_NAME, required=true, type=SteamClosetJobModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private SteamClosetJobModeFlexContainer steamClosetJobMode;
		
	public void setSteamClosetJobMode(SteamClosetJobModeFlexContainer steamClosetJobMode) {
		this.steamClosetJobMode = steamClosetJobMode;
		getFlexContainerOrContainerOrSubscription().add(steamClosetJobMode);
	}
	
	public SteamClosetJobModeFlexContainer getSteamClosetJobMode() {
		this.steamClosetJobMode = (SteamClosetJobModeFlexContainer) getResourceByName(SteamClosetJobModeFlexContainer.SHORT_NAME);
		return steamClosetJobMode;
	}
	
	@XmlElement(name=SteamClosetJobModeFlexContainerAnnc.SHORT_NAME, required=true, type=SteamClosetJobModeFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private SteamClosetJobModeFlexContainerAnnc steamClosetJobModeAnnc;
		
	public void setSteamClosetJobModeAnnc(SteamClosetJobModeFlexContainerAnnc steamClosetJobModeAnnc) {
		this.steamClosetJobModeAnnc = steamClosetJobModeAnnc;
		getFlexContainerOrContainerOrSubscription().add(steamClosetJobModeAnnc);
	}
	
	public SteamClosetJobModeFlexContainerAnnc getSteamClosetJobModeAnnc() {
		this.steamClosetJobModeAnnc = (SteamClosetJobModeFlexContainerAnnc) getResourceByName(SteamClosetJobModeFlexContainerAnnc.SHORT_NAME);
		return steamClosetJobModeAnnc;
	}
	
}
