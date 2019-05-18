/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceDoor

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

@XmlRootElement(name = DeviceDoorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceDoorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceDoorFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceDoor";
	public static final String SHORT_NAME = "devD0";
	
	public DeviceDoorFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceDoorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getOpenLevel();
		getDoorlock();
		getDoorStatus();
	}
	
	public void finalizeDeserialization() {
		if (this.openLevel != null) {
			setOpenLevel(this.openLevel);
		}
		if (this.doorlock != null) {
			setDoorlock(this.doorlock);
		}
		if (this.doorStatus != null) {
			setDoorStatus(this.doorStatus);
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
	
	@XmlElement(name=LockFlexContainer.SHORT_NAME, required=true, type=LockFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private LockFlexContainer doorlock;
		
	public void setDoorlock(LockFlexContainer doorlock) {
		this.doorlock = doorlock;
		getFlexContainerOrContainerOrSubscription().add(doorlock);
	}
	
	public LockFlexContainer getDoorlock() {
		this.doorlock = (LockFlexContainer) getResourceByName(LockFlexContainer.SHORT_NAME);
		return doorlock;
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
	
}
