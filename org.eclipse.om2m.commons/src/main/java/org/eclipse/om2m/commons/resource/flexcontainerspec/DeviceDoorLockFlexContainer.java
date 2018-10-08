/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceDoorLock

A door lock is a device that can be used to lock, for example, a door.

Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceDoorLockFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceDoorLockFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceDoorLockFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceDoorLock";
	public static final String SHORT_NAME = "deDLk";
	
	public DeviceDoorLockFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceDoorLockFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getDoorLock();
		getDoorStatus();
		getBattery();
	}
	
	public void finalizeDeserialization() {
		if (this.doorLock != null) {
			setDoorLock(this.doorLock);
		}
		if (this.doorStatus != null) {
			setDoorStatus(this.doorStatus);
		}
		if (this.battery != null) {
			setBattery(this.battery);
		}
	}

	@XmlElement(name=LockFlexContainer.SHORT_NAME, required=true, type=LockFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private LockFlexContainer doorLock;
		
	public void setDoorLock(LockFlexContainer doorLock) {
		this.doorLock = doorLock;
		getFlexContainerOrContainerOrSubscription().add(doorLock);
	}
	
	public LockFlexContainer getDoorLock() {
		this.doorLock = (LockFlexContainer) getResourceByName(LockFlexContainer.SHORT_NAME);
		return doorLock;
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
	
	@XmlElement(name=BatteryFlexContainer.SHORT_NAME, required=true, type=BatteryFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BatteryFlexContainer battery;
		
	public void setBattery(BatteryFlexContainer battery) {
		this.battery = battery;
		getFlexContainerOrContainerOrSubscription().add(battery);
	}
	
	public BatteryFlexContainer getBattery() {
		this.battery = (BatteryFlexContainer) getResourceByName(BatteryFlexContainer.SHORT_NAME);
		return battery;
	}
	
}
