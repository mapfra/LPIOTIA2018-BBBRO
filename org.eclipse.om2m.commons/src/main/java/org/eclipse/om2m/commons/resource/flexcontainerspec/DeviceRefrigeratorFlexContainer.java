/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceRefrigerator

A refrigerator is a home appliance used to store food at temperatures which are a few degrees above the freezing point of water. This information model provides capabilities to interact with specific functions and resource of refrigerators.

Created: 2017-09-28 17:26:40
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;


@XmlRootElement(name = DeviceRefrigeratorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceRefrigeratorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceRefrigeratorFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceRefrigerator";
	public static final String SHORT_NAME = "devRr";
	
	public DeviceRefrigeratorFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceRefrigeratorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getPowerSave();
		getDoorStatus();
		getRefrigeration();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.powerSave != null) {
			setPowerSave(this.powerSave);
		}
		if (this.doorStatus != null) {
			setDoorStatus(this.doorStatus);
		}
		if (this.refrigeration != null) {
			setRefrigeration(this.refrigeration);
		}
	}
	
	@XmlElement(name="binSh", required=true, type=BinarySwitchFlexContainer.class)
	private BinarySwitchFlexContainer binarySwitch;
	
	
	public void setBinarySwitch(BinarySwitchFlexContainer binarySwitch) {
		this.binarySwitch = binarySwitch;
		getFlexContainerOrContainerOrSubscription().add(binarySwitch);
	}
	
	public BinarySwitchFlexContainer getBinarySwitch() {
		this.binarySwitch = (BinarySwitchFlexContainer) getResourceByName(BinarySwitchFlexContainer.SHORT_NAME);
		return binarySwitch;
	}
	
	@XmlElement(name="powSe", required=true, type=PowerSaveFlexContainer.class)
	private PowerSaveFlexContainer powerSave;
	
	
	public void setPowerSave(PowerSaveFlexContainer powerSave) {
		this.powerSave = powerSave;
		getFlexContainerOrContainerOrSubscription().add(powerSave);
	}
	
	public PowerSaveFlexContainer getPowerSave() {
		this.powerSave = (PowerSaveFlexContainer) getResourceByName(PowerSaveFlexContainer.SHORT_NAME);
		return powerSave;
	}
	
	@XmlElement(name="dooSs", required=true, type=DoorStatusFlexContainer.class)
	private DoorStatusFlexContainer doorStatus;
	
	
	public void setDoorStatus(DoorStatusFlexContainer doorStatus) {
		this.doorStatus = doorStatus;
		getFlexContainerOrContainerOrSubscription().add(doorStatus);
	}
	
	public DoorStatusFlexContainer getDoorStatus() {
		this.doorStatus = (DoorStatusFlexContainer) getResourceByName(DoorStatusFlexContainer.SHORT_NAME);
		return doorStatus;
	}
	
	@XmlElement(name="refrn", required=true, type=RefrigerationFlexContainer.class)
	private RefrigerationFlexContainer refrigeration;
	
	
	public void setRefrigeration(RefrigerationFlexContainer refrigeration) {
		this.refrigeration = refrigeration;
		getFlexContainerOrContainerOrSubscription().add(refrigeration);
	}
	
	public RefrigerationFlexContainer getRefrigeration() {
		this.refrigeration = (RefrigerationFlexContainer) getResourceByName(RefrigerationFlexContainer.SHORT_NAME);
		return refrigeration;
	}
	
}