/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceRefrigerator

A refrigerator is a home appliance used to store food at temperatures which are a few degrees above the freezing point of water. This information model provides capabilities to interact with specific functions and resource of refrigerators.

Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

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
		getFrozenTemperature();
		getFridgeTemperature();
		getCustomTemperature();
		getRefrigeration();
		getControlPanelLock();
		getWaterFilterInfo();
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
		if (this.frozenTemperature != null) {
			setFrozenTemperature(this.frozenTemperature);
		}
		if (this.fridgeTemperature != null) {
			setFridgeTemperature(this.fridgeTemperature);
		}
		if (this.customTemperature != null) {
			setCustomTemperature(this.customTemperature);
		}
		if (this.refrigeration != null) {
			setRefrigeration(this.refrigeration);
		}
		if (this.controlPanelLock != null) {
			setControlPanelLock(this.controlPanelLock);
		}
		if (this.waterFilterInfo != null) {
			setWaterFilterInfo(this.waterFilterInfo);
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
	
	@XmlElement(name=PowerSaveFlexContainer.SHORT_NAME, required=true, type=PowerSaveFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private PowerSaveFlexContainer powerSave;
		
	public void setPowerSave(PowerSaveFlexContainer powerSave) {
		this.powerSave = powerSave;
		getFlexContainerOrContainerOrSubscription().add(powerSave);
	}
	
	public PowerSaveFlexContainer getPowerSave() {
		this.powerSave = (PowerSaveFlexContainer) getResourceByName(PowerSaveFlexContainer.SHORT_NAME);
		return powerSave;
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
	
	@XmlElement(name=TemperatureFlexContainer.SHORT_NAME, required=true, type=TemperatureFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TemperatureFlexContainer frozenTemperature;
		
	public void setFrozenTemperature(TemperatureFlexContainer frozenTemperature) {
		this.frozenTemperature = frozenTemperature;
		getFlexContainerOrContainerOrSubscription().add(frozenTemperature);
	}
	
	public TemperatureFlexContainer getFrozenTemperature() {
		this.frozenTemperature = (TemperatureFlexContainer) getResourceByName(TemperatureFlexContainer.SHORT_NAME);
		return frozenTemperature;
	}
	
	@XmlElement(name=TemperatureFlexContainer.SHORT_NAME, required=true, type=TemperatureFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TemperatureFlexContainer fridgeTemperature;
		
	public void setFridgeTemperature(TemperatureFlexContainer fridgeTemperature) {
		this.fridgeTemperature = fridgeTemperature;
		getFlexContainerOrContainerOrSubscription().add(fridgeTemperature);
	}
	
	public TemperatureFlexContainer getFridgeTemperature() {
		this.fridgeTemperature = (TemperatureFlexContainer) getResourceByName(TemperatureFlexContainer.SHORT_NAME);
		return fridgeTemperature;
	}
	
	@XmlElement(name=TemperatureFlexContainer.SHORT_NAME, required=true, type=TemperatureFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TemperatureFlexContainer customTemperature;
		
	public void setCustomTemperature(TemperatureFlexContainer customTemperature) {
		this.customTemperature = customTemperature;
		getFlexContainerOrContainerOrSubscription().add(customTemperature);
	}
	
	public TemperatureFlexContainer getCustomTemperature() {
		this.customTemperature = (TemperatureFlexContainer) getResourceByName(TemperatureFlexContainer.SHORT_NAME);
		return customTemperature;
	}
	
	@XmlElement(name=RefrigerationFlexContainer.SHORT_NAME, required=true, type=RefrigerationFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private RefrigerationFlexContainer refrigeration;
		
	public void setRefrigeration(RefrigerationFlexContainer refrigeration) {
		this.refrigeration = refrigeration;
		getFlexContainerOrContainerOrSubscription().add(refrigeration);
	}
	
	public RefrigerationFlexContainer getRefrigeration() {
		this.refrigeration = (RefrigerationFlexContainer) getResourceByName(RefrigerationFlexContainer.SHORT_NAME);
		return refrigeration;
	}
	
	@XmlElement(name=LockFlexContainer.SHORT_NAME, required=true, type=LockFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private LockFlexContainer controlPanelLock;
		
	public void setControlPanelLock(LockFlexContainer controlPanelLock) {
		this.controlPanelLock = controlPanelLock;
		getFlexContainerOrContainerOrSubscription().add(controlPanelLock);
	}
	
	public LockFlexContainer getControlPanelLock() {
		this.controlPanelLock = (LockFlexContainer) getResourceByName(LockFlexContainer.SHORT_NAME);
		return controlPanelLock;
	}
	
	@XmlElement(name=FilterInfoFlexContainer.SHORT_NAME, required=true, type=FilterInfoFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private FilterInfoFlexContainer waterFilterInfo;
		
	public void setWaterFilterInfo(FilterInfoFlexContainer waterFilterInfo) {
		this.waterFilterInfo = waterFilterInfo;
		getFlexContainerOrContainerOrSubscription().add(waterFilterInfo);
	}
	
	public FilterInfoFlexContainer getWaterFilterInfo() {
		this.waterFilterInfo = (FilterInfoFlexContainer) getResourceByName(FilterInfoFlexContainer.SHORT_NAME);
		return waterFilterInfo;
	}
	
}
