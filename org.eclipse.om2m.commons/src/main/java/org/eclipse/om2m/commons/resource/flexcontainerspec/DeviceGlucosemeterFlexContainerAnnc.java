/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceGlucosemeterAnnc

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
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DeviceGlucosemeterFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceGlucosemeterFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceGlucosemeterFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceGlucosemeterAnnc";
	public static final String SHORT_NAME = "devGrAnnc";
	
	public DeviceGlucosemeterFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceGlucosemeterFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getGlucometer();
		getGlucometerAnnc();
		getBattery();
		getBatteryAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.glucometer != null) {
			setGlucometer(this.glucometer);
		}
		if (this.glucometerAnnc != null) {
			setGlucometerAnnc(this.glucometerAnnc);
		}
		if (this.battery != null) {
			setBattery(this.battery);
		}
		if (this.batteryAnnc != null) {
			setBatteryAnnc(this.batteryAnnc);
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
	
	@XmlElement(name=GlucometerFlexContainerAnnc.SHORT_NAME, required=true, type=GlucometerFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private GlucometerFlexContainerAnnc glucometerAnnc;
		
	public void setGlucometerAnnc(GlucometerFlexContainerAnnc glucometerAnnc) {
		this.glucometerAnnc = glucometerAnnc;
		getFlexContainerOrContainerOrSubscription().add(glucometerAnnc);
	}
	
	public GlucometerFlexContainerAnnc getGlucometerAnnc() {
		this.glucometerAnnc = (GlucometerFlexContainerAnnc) getResourceByName(GlucometerFlexContainerAnnc.SHORT_NAME);
		return glucometerAnnc;
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
