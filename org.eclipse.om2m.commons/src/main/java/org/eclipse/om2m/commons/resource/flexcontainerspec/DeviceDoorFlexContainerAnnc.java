/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceDoorAnnc

A door is a device that is used to open and close a door.

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

@XmlRootElement(name = DeviceDoorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceDoorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceDoorFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceDoorAnnc";
	public static final String SHORT_NAME = "devD0Annc";
	
	public DeviceDoorFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceDoorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getOpenLevel();
		getOpenLevelAnnc();
		getDoorStatus();
		getDoorStatusAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.openLevel != null) {
			setOpenLevel(this.openLevel);
		}
		if (this.openLevelAnnc != null) {
			setOpenLevelAnnc(this.openLevelAnnc);
		}
		if (this.doorStatus != null) {
			setDoorStatus(this.doorStatus);
		}
		if (this.doorStatusAnnc != null) {
			setDoorStatusAnnc(this.doorStatusAnnc);
		}
	}

	@XmlElement(name=OpenLevelFlexContainer.SHORT_NAME, required=true, type=OpenLevelFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private OpenLevelFlexContainer openLevel;
		
	public void setOpenLevel(OpenLevelFlexContainer openLevel) {
		this.openLevel = openLevel;
		getFlexContainerOrContainerOrSubscription().add(openLevel);
	}
	
	public OpenLevelFlexContainer getOpenLevel() {
		this.openLevel = (OpenLevelFlexContainer) getResourceByName(OpenLevelFlexContainer.SHORT_NAME);
		return openLevel;
	}
	
	@XmlElement(name=OpenLevelFlexContainerAnnc.SHORT_NAME, required=true, type=OpenLevelFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private OpenLevelFlexContainerAnnc openLevelAnnc;
		
	public void setOpenLevelAnnc(OpenLevelFlexContainerAnnc openLevelAnnc) {
		this.openLevelAnnc = openLevelAnnc;
		getFlexContainerOrContainerOrSubscription().add(openLevelAnnc);
	}
	
	public OpenLevelFlexContainerAnnc getOpenLevelAnnc() {
		this.openLevelAnnc = (OpenLevelFlexContainerAnnc) getResourceByName(OpenLevelFlexContainerAnnc.SHORT_NAME);
		return openLevelAnnc;
	}
	
	@XmlElement(name=DoorStatusFlexContainer.SHORT_NAME, required=true, type=DoorStatusFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private DoorStatusFlexContainer doorStatus;
		
	public void setDoorStatus(DoorStatusFlexContainer doorStatus) {
		this.doorStatus = doorStatus;
		getFlexContainerOrContainerOrSubscription().add(doorStatus);
	}
	
	public DoorStatusFlexContainer getDoorStatus() {
		this.doorStatus = (DoorStatusFlexContainer) getResourceByName(DoorStatusFlexContainer.SHORT_NAME);
		return doorStatus;
	}
	
	@XmlElement(name=DoorStatusFlexContainerAnnc.SHORT_NAME, required=true, type=DoorStatusFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private DoorStatusFlexContainerAnnc doorStatusAnnc;
		
	public void setDoorStatusAnnc(DoorStatusFlexContainerAnnc doorStatusAnnc) {
		this.doorStatusAnnc = doorStatusAnnc;
		getFlexContainerOrContainerOrSubscription().add(doorStatusAnnc);
	}
	
	public DoorStatusFlexContainerAnnc getDoorStatusAnnc() {
		this.doorStatusAnnc = (DoorStatusFlexContainerAnnc) getResourceByName(DoorStatusFlexContainerAnnc.SHORT_NAME);
		return doorStatusAnnc;
	}
	
}
