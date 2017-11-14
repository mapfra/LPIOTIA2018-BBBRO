/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceRefrigeratorAnnc

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


@XmlRootElement(name = DeviceRefrigeratorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceRefrigeratorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceRefrigeratorFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceRefrigeratorAnnc";
	public static final String SHORT_NAME = "devRrAnnc";
	
	public DeviceRefrigeratorFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceRefrigeratorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getPowerSave();
		getPowerSaveAnnc();
		getDoorStatus();
		getDoorStatusAnnc();
		getRefrigeration();
		getRefrigerationAnnc();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.binarySwitchAnnc != null) {
			setBinarySwitchAnnc(this.binarySwitchAnnc);
			}
		
		if (this.powerSave != null) {
			setPowerSave(this.powerSave);
		}
		if (this.powerSaveAnnc != null) {
			setPowerSaveAnnc(this.powerSaveAnnc);
			}
		
		if (this.doorStatus != null) {
			setDoorStatus(this.doorStatus);
		}
		if (this.doorStatusAnnc != null) {
			setDoorStatusAnnc(this.doorStatusAnnc);
			}
		
		if (this.refrigeration != null) {
			setRefrigeration(this.refrigeration);
		}
		if (this.refrigerationAnnc != null) {
			setRefrigerationAnnc(this.refrigerationAnnc);
			}
		
	}
	
	@XmlElement(name="binSh", required=true, type=BinarySwitchFlexContainerAnnc.class)
	private BinarySwitchFlexContainer binarySwitch;
	
	
	public void setBinarySwitch(BinarySwitchFlexContainer binarySwitch) {
		this.binarySwitch = binarySwitch;
		getFlexContainerOrContainerOrSubscription().add(binarySwitch);
	}
	
	public BinarySwitchFlexContainer getBinarySwitch() {
		this.binarySwitch = (BinarySwitchFlexContainer) getResourceByName(BinarySwitchFlexContainer.SHORT_NAME);
		return binarySwitch;
	}
	
	@XmlElement(name="binShAnnc", required=true, type=BinarySwitchFlexContainerAnnc.class)
	private BinarySwitchFlexContainerAnnc binarySwitchAnnc;
	
	
	public void setBinarySwitchAnnc(BinarySwitchFlexContainerAnnc binarySwitchAnnc) {
		this.binarySwitchAnnc = binarySwitchAnnc;
		getFlexContainerOrContainerOrSubscription().add(binarySwitchAnnc);
	}
	
	public BinarySwitchFlexContainerAnnc getBinarySwitchAnnc() {
		this.binarySwitchAnnc = (BinarySwitchFlexContainerAnnc) getResourceByName(BinarySwitchFlexContainerAnnc.SHORT_NAME);
		return binarySwitchAnnc;
	}
	
	@XmlElement(name="powSe", required=true, type=PowerSaveFlexContainerAnnc.class)
	private PowerSaveFlexContainer powerSave;
	
	
	public void setPowerSave(PowerSaveFlexContainer powerSave) {
		this.powerSave = powerSave;
		getFlexContainerOrContainerOrSubscription().add(powerSave);
	}
	
	public PowerSaveFlexContainer getPowerSave() {
		this.powerSave = (PowerSaveFlexContainer) getResourceByName(PowerSaveFlexContainer.SHORT_NAME);
		return powerSave;
	}
	
	@XmlElement(name="powSeAnnc", required=true, type=PowerSaveFlexContainerAnnc.class)
	private PowerSaveFlexContainerAnnc powerSaveAnnc;
	
	
	public void setPowerSaveAnnc(PowerSaveFlexContainerAnnc powerSaveAnnc) {
		this.powerSaveAnnc = powerSaveAnnc;
		getFlexContainerOrContainerOrSubscription().add(powerSaveAnnc);
	}
	
	public PowerSaveFlexContainerAnnc getPowerSaveAnnc() {
		this.powerSaveAnnc = (PowerSaveFlexContainerAnnc) getResourceByName(PowerSaveFlexContainerAnnc.SHORT_NAME);
		return powerSaveAnnc;
	}
	
	@XmlElement(name="dooSs", required=true, type=DoorStatusFlexContainerAnnc.class)
	private DoorStatusFlexContainer doorStatus;
	
	
	public void setDoorStatus(DoorStatusFlexContainer doorStatus) {
		this.doorStatus = doorStatus;
		getFlexContainerOrContainerOrSubscription().add(doorStatus);
	}
	
	public DoorStatusFlexContainer getDoorStatus() {
		this.doorStatus = (DoorStatusFlexContainer) getResourceByName(DoorStatusFlexContainer.SHORT_NAME);
		return doorStatus;
	}
	
	@XmlElement(name="dooSsAnnc", required=true, type=DoorStatusFlexContainerAnnc.class)
	private DoorStatusFlexContainerAnnc doorStatusAnnc;
	
	
	public void setDoorStatusAnnc(DoorStatusFlexContainerAnnc doorStatusAnnc) {
		this.doorStatusAnnc = doorStatusAnnc;
		getFlexContainerOrContainerOrSubscription().add(doorStatusAnnc);
	}
	
	public DoorStatusFlexContainerAnnc getDoorStatusAnnc() {
		this.doorStatusAnnc = (DoorStatusFlexContainerAnnc) getResourceByName(DoorStatusFlexContainerAnnc.SHORT_NAME);
		return doorStatusAnnc;
	}
	
	@XmlElement(name="refrn", required=true, type=RefrigerationFlexContainerAnnc.class)
	private RefrigerationFlexContainer refrigeration;
	
	
	public void setRefrigeration(RefrigerationFlexContainer refrigeration) {
		this.refrigeration = refrigeration;
		getFlexContainerOrContainerOrSubscription().add(refrigeration);
	}
	
	public RefrigerationFlexContainer getRefrigeration() {
		this.refrigeration = (RefrigerationFlexContainer) getResourceByName(RefrigerationFlexContainer.SHORT_NAME);
		return refrigeration;
	}
	
	@XmlElement(name="refrnAnnc", required=true, type=RefrigerationFlexContainerAnnc.class)
	private RefrigerationFlexContainerAnnc refrigerationAnnc;
	
	
	public void setRefrigerationAnnc(RefrigerationFlexContainerAnnc refrigerationAnnc) {
		this.refrigerationAnnc = refrigerationAnnc;
		getFlexContainerOrContainerOrSubscription().add(refrigerationAnnc);
	}
	
	public RefrigerationFlexContainerAnnc getRefrigerationAnnc() {
		this.refrigerationAnnc = (RefrigerationFlexContainerAnnc) getResourceByName(RefrigerationFlexContainerAnnc.SHORT_NAME);
		return refrigerationAnnc;
	}
	
}