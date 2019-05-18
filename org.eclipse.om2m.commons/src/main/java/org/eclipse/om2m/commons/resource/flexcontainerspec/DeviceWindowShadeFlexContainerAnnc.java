/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceWindowShadeAnnc

The window shade is an appliance that provides the ability to cover windows. This device type includes but not limited to roller shades, drapes, and tilt-only blinds.

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

@XmlRootElement(name = DeviceWindowShadeFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceWindowShadeFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceWindowShadeFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceWindowShadeAnnc";
	public static final String SHORT_NAME = "deWSeAnnc";
	
	public DeviceWindowShadeFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceWindowShadeFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getOpenLevel();
		getOpenLevelAnnc();
		getBattery();
		getBatteryAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.openLevel != null) {
			setOpenLevel(this.openLevel);
		}
		if (this.openLevelAnnc != null) {
			setOpenLevelAnnc(this.openLevelAnnc);
		}
		if (this.battery != null) {
			setBattery(this.battery);
		}
		if (this.batteryAnnc != null) {
			setBatteryAnnc(this.batteryAnnc);
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
	
	@XmlElement(name=BatteryFlexContainerAnnc.SHORT_NAME, required=true, type=BatteryFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BatteryFlexContainerAnnc batteryAnnc;
		
	public void setBatteryAnnc(BatteryFlexContainerAnnc batteryAnnc) {
		this.batteryAnnc = batteryAnnc;
		getFlexContainerOrContainerOrSubscription().add(batteryAnnc);
	}
	
	public BatteryFlexContainerAnnc getBatteryAnnc() {
		this.batteryAnnc = (BatteryFlexContainerAnnc) getResourceByName(BatteryFlexContainerAnnc.SHORT_NAME);
		return batteryAnnc;
	}
	
}
