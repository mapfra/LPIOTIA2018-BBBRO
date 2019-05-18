/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceSmartPlug

A smart plug is a device that can turn on and off a connected appliance.

Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceSmartPlugFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceSmartPlugFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceSmartPlugFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceSmartPlug";
	public static final String SHORT_NAME = "deSPg";
	
	public DeviceSmartPlugFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceSmartPlugFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getBinarySwitch();
		getFaultDetection();
		getRemoteControlEnable();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.faultDetection != null) {
			setFaultDetection(this.faultDetection);
		}
		if (this.remoteControlEnable != null) {
			setRemoteControlEnable(this.remoteControlEnable);
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
	
}
