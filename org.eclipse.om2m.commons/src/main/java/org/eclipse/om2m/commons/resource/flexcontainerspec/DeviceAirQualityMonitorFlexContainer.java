/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceAirQualityMonitor

An air quality monitor is a home appliance for monitoring the air quality. This airQualityMonitor information model provides capabilities to monitor the airQualityMonitor functions and resources.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceAirQualityMonitorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceAirQualityMonitorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceAirQualityMonitorFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceAirQualityMonitor";
	public static final String SHORT_NAME = "dAQMr";
	
	public DeviceAirQualityMonitorFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceAirQualityMonitorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getAirQualitySensor();
	}
	
	public void finalizeDeserialization() {
		if (this.airQualitySensor != null) {
			setAirQualitySensor(this.airQualitySensor);
		}
	}

	@XmlElement(name=AirQualitySensorFlexContainer.SHORT_NAME, required=true, type=AirQualitySensorFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AirQualitySensorFlexContainer airQualitySensor;
		
	public void setAirQualitySensor(AirQualitySensorFlexContainer airQualitySensor) {
		this.airQualitySensor = airQualitySensor;
		getFlexContainerOrContainerOrSubscription().add(airQualitySensor);
	}
	
	public AirQualitySensorFlexContainer getAirQualitySensor() {
		this.airQualitySensor = (AirQualitySensorFlexContainer) getResourceByName(AirQualitySensorFlexContainer.SHORT_NAME);
		return airQualitySensor;
	}
	
}
