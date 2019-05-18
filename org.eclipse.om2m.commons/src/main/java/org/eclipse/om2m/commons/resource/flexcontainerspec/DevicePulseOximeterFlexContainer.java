/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DevicePulseOximeter

A pulseoximeter is a device that can be used to monitor the blood characteristics.

Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DevicePulseOximeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DevicePulseOximeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DevicePulseOximeterFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "devicePulseOximeter";
	public static final String SHORT_NAME = "dePOr";
	
	public DevicePulseOximeterFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DevicePulseOximeterFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getPulsemeter();
		getOximeter();
		getBattery();
	}
	
	public void finalizeDeserialization() {
		if (this.pulsemeter != null) {
			setPulsemeter(this.pulsemeter);
		}
		if (this.oximeter != null) {
			setOximeter(this.oximeter);
		}
		if (this.battery != null) {
			setBattery(this.battery);
		}
	}

	@XmlElement(name=PulsemeterFlexContainer.SHORT_NAME, required=true, type=PulsemeterFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private PulsemeterFlexContainer pulsemeter;
		
	public void setPulsemeter(PulsemeterFlexContainer pulsemeter) {
		this.pulsemeter = pulsemeter;
		getFlexContainerOrContainerOrSubscription().add(pulsemeter);
	}
	
	public PulsemeterFlexContainer getPulsemeter() {
		this.pulsemeter = (PulsemeterFlexContainer) getResourceByName(PulsemeterFlexContainer.SHORT_NAME);
		return pulsemeter;
	}
	
	@XmlElement(name=OximeterFlexContainer.SHORT_NAME, required=true, type=OximeterFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private OximeterFlexContainer oximeter;
		
	public void setOximeter(OximeterFlexContainer oximeter) {
		this.oximeter = oximeter;
		getFlexContainerOrContainerOrSubscription().add(oximeter);
	}
	
	public OximeterFlexContainer getOximeter() {
		this.oximeter = (OximeterFlexContainer) getResourceByName(OximeterFlexContainer.SHORT_NAME);
		return oximeter;
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
