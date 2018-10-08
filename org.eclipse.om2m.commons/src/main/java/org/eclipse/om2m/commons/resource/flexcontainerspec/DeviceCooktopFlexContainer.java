/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceCooktop

A cooktop is a device that is a kitchen appliance designed for the purpose of cooking food.

Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceCooktopFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceCooktopFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceCooktopFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceCooktop";
	public static final String SHORT_NAME = "devCp";
	
	public DeviceCooktopFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceCooktopFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getHeatingZone0();
		getHeatingZone1();
		getHeatingZone2();
		getHeatingZone3();
		getHeatingZone4();
		getHeatingZone5();
	}
	
	public void finalizeDeserialization() {
		if (this.heatingZone0 != null) {
			setHeatingZone0(this.heatingZone0);
		}
		if (this.heatingZone1 != null) {
			setHeatingZone1(this.heatingZone1);
		}
		if (this.heatingZone2 != null) {
			setHeatingZone2(this.heatingZone2);
		}
		if (this.heatingZone3 != null) {
			setHeatingZone3(this.heatingZone3);
		}
		if (this.heatingZone4 != null) {
			setHeatingZone4(this.heatingZone4);
		}
		if (this.heatingZone5 != null) {
			setHeatingZone5(this.heatingZone5);
		}
	}

	@XmlElement(name=HeatingZoneFlexContainer.SHORT_NAME, required=true, type=HeatingZoneFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private HeatingZoneFlexContainer heatingZone0;
		
	public void setHeatingZone0(HeatingZoneFlexContainer heatingZone0) {
		this.heatingZone0 = heatingZone0;
		getFlexContainerOrContainerOrSubscription().add(heatingZone0);
	}
	
	public HeatingZoneFlexContainer getHeatingZone0() {
		this.heatingZone0 = (HeatingZoneFlexContainer) getResourceByName(HeatingZoneFlexContainer.SHORT_NAME);
		return heatingZone0;
	}
	
	@XmlElement(name=HeatingZoneFlexContainer.SHORT_NAME, required=true, type=HeatingZoneFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private HeatingZoneFlexContainer heatingZone1;
		
	public void setHeatingZone1(HeatingZoneFlexContainer heatingZone1) {
		this.heatingZone1 = heatingZone1;
		getFlexContainerOrContainerOrSubscription().add(heatingZone1);
	}
	
	public HeatingZoneFlexContainer getHeatingZone1() {
		this.heatingZone1 = (HeatingZoneFlexContainer) getResourceByName(HeatingZoneFlexContainer.SHORT_NAME);
		return heatingZone1;
	}
	
	@XmlElement(name=HeatingZoneFlexContainer.SHORT_NAME, required=true, type=HeatingZoneFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private HeatingZoneFlexContainer heatingZone2;
		
	public void setHeatingZone2(HeatingZoneFlexContainer heatingZone2) {
		this.heatingZone2 = heatingZone2;
		getFlexContainerOrContainerOrSubscription().add(heatingZone2);
	}
	
	public HeatingZoneFlexContainer getHeatingZone2() {
		this.heatingZone2 = (HeatingZoneFlexContainer) getResourceByName(HeatingZoneFlexContainer.SHORT_NAME);
		return heatingZone2;
	}
	
	@XmlElement(name=HeatingZoneFlexContainer.SHORT_NAME, required=true, type=HeatingZoneFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private HeatingZoneFlexContainer heatingZone3;
		
	public void setHeatingZone3(HeatingZoneFlexContainer heatingZone3) {
		this.heatingZone3 = heatingZone3;
		getFlexContainerOrContainerOrSubscription().add(heatingZone3);
	}
	
	public HeatingZoneFlexContainer getHeatingZone3() {
		this.heatingZone3 = (HeatingZoneFlexContainer) getResourceByName(HeatingZoneFlexContainer.SHORT_NAME);
		return heatingZone3;
	}
	
	@XmlElement(name=HeatingZoneFlexContainer.SHORT_NAME, required=true, type=HeatingZoneFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private HeatingZoneFlexContainer heatingZone4;
		
	public void setHeatingZone4(HeatingZoneFlexContainer heatingZone4) {
		this.heatingZone4 = heatingZone4;
		getFlexContainerOrContainerOrSubscription().add(heatingZone4);
	}
	
	public HeatingZoneFlexContainer getHeatingZone4() {
		this.heatingZone4 = (HeatingZoneFlexContainer) getResourceByName(HeatingZoneFlexContainer.SHORT_NAME);
		return heatingZone4;
	}
	
	@XmlElement(name=HeatingZoneFlexContainer.SHORT_NAME, required=true, type=HeatingZoneFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private HeatingZoneFlexContainer heatingZone5;
		
	public void setHeatingZone5(HeatingZoneFlexContainer heatingZone5) {
		this.heatingZone5 = heatingZone5;
		getFlexContainerOrContainerOrSubscription().add(heatingZone5);
	}
	
	public HeatingZoneFlexContainer getHeatingZone5() {
		this.heatingZone5 = (HeatingZoneFlexContainer) getResourceByName(HeatingZoneFlexContainer.SHORT_NAME);
		return heatingZone5;
	}
	
}
