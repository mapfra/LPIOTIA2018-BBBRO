/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceGlucosemeter

A glucometer is a device that can be used to monitor the blood glucose level.

Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceGlucosemeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceGlucosemeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceGlucosemeterFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceGlucosemeter";
	public static final String SHORT_NAME = "devGr";
	
	public DeviceGlucosemeterFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceGlucosemeterFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getGlucometer();
		getBattery();
	}
	
	public void finalizeDeserialization() {
		if (this.glucometer != null) {
			setGlucometer(this.glucometer);
		}
		if (this.battery != null) {
			setBattery(this.battery);
		}
	}

	@XmlElement(name=GlucometerFlexContainer.SHORT_NAME, required=true, type=GlucometerFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private GlucometerFlexContainer glucometer;
		
	public void setGlucometer(GlucometerFlexContainer glucometer) {
		this.glucometer = glucometer;
		getFlexContainerOrContainerOrSubscription().add(glucometer);
	}
	
	public GlucometerFlexContainer getGlucometer() {
		this.glucometer = (GlucometerFlexContainer) getResourceByName(GlucometerFlexContainer.SHORT_NAME);
		return glucometer;
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
