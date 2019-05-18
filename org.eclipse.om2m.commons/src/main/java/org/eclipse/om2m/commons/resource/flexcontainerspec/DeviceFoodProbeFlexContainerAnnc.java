/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceFoodProbeAnnc

A food probe is a device that is used to measure the internal temperature of food.

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

@XmlRootElement(name = DeviceFoodProbeFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceFoodProbeFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceFoodProbeFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceFoodProbeAnnc";
	public static final String SHORT_NAME = "deFPeAnnc";
	
	public DeviceFoodProbeFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceFoodProbeFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getTemperature();
		getTemperatureAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.temperature != null) {
			setTemperature(this.temperature);
		}
		if (this.temperatureAnnc != null) {
			setTemperatureAnnc(this.temperatureAnnc);
		}
	}

	@XmlElement(name=TemperatureFlexContainer.SHORT_NAME, required=true, type=TemperatureFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TemperatureFlexContainer temperature;
		
	public void setTemperature(TemperatureFlexContainer temperature) {
		this.temperature = temperature;
		getFlexContainerOrContainerOrSubscription().add(temperature);
	}
	
	public TemperatureFlexContainer getTemperature() {
		this.temperature = (TemperatureFlexContainer) getResourceByName(TemperatureFlexContainer.SHORT_NAME);
		return temperature;
	}
	
	@XmlElement(name=TemperatureFlexContainerAnnc.SHORT_NAME, required=true, type=TemperatureFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TemperatureFlexContainerAnnc temperatureAnnc;
		
	public void setTemperatureAnnc(TemperatureFlexContainerAnnc temperatureAnnc) {
		this.temperatureAnnc = temperatureAnnc;
		getFlexContainerOrContainerOrSubscription().add(temperatureAnnc);
	}
	
	public TemperatureFlexContainerAnnc getTemperatureAnnc() {
		this.temperatureAnnc = (TemperatureFlexContainerAnnc) getResourceByName(TemperatureFlexContainerAnnc.SHORT_NAME);
		return temperatureAnnc;
	}
	
}
