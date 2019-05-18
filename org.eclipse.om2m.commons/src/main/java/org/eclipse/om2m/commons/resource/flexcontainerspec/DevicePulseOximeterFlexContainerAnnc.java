/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DevicePulseOximeterAnnc

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
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DevicePulseOximeterFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DevicePulseOximeterFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DevicePulseOximeterFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "devicePulseOximeterAnnc";
	public static final String SHORT_NAME = "dePOrAnnc";
	
	public DevicePulseOximeterFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DevicePulseOximeterFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getPulsemeter();
		getPulsemeterAnnc();
		getOximeter();
		getOximeterAnnc();
		getBattery();
		getBatteryAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.pulsemeter != null) {
			setPulsemeter(this.pulsemeter);
		}
		if (this.pulsemeterAnnc != null) {
			setPulsemeterAnnc(this.pulsemeterAnnc);
		}
		if (this.oximeter != null) {
			setOximeter(this.oximeter);
		}
		if (this.oximeterAnnc != null) {
			setOximeterAnnc(this.oximeterAnnc);
		}
		if (this.battery != null) {
			setBattery(this.battery);
		}
		if (this.batteryAnnc != null) {
			setBatteryAnnc(this.batteryAnnc);
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
	
	@XmlElement(name=PulsemeterFlexContainerAnnc.SHORT_NAME, required=true, type=PulsemeterFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private PulsemeterFlexContainerAnnc pulsemeterAnnc;
		
	public void setPulsemeterAnnc(PulsemeterFlexContainerAnnc pulsemeterAnnc) {
		this.pulsemeterAnnc = pulsemeterAnnc;
		getFlexContainerOrContainerOrSubscription().add(pulsemeterAnnc);
	}
	
	public PulsemeterFlexContainerAnnc getPulsemeterAnnc() {
		this.pulsemeterAnnc = (PulsemeterFlexContainerAnnc) getResourceByName(PulsemeterFlexContainerAnnc.SHORT_NAME);
		return pulsemeterAnnc;
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
	
	@XmlElement(name=OximeterFlexContainerAnnc.SHORT_NAME, required=true, type=OximeterFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private OximeterFlexContainerAnnc oximeterAnnc;
		
	public void setOximeterAnnc(OximeterFlexContainerAnnc oximeterAnnc) {
		this.oximeterAnnc = oximeterAnnc;
		getFlexContainerOrContainerOrSubscription().add(oximeterAnnc);
	}
	
	public OximeterFlexContainerAnnc getOximeterAnnc() {
		this.oximeterAnnc = (OximeterFlexContainerAnnc) getResourceByName(OximeterFlexContainerAnnc.SHORT_NAME);
		return oximeterAnnc;
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
