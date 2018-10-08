/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceFloodDetectorAnnc



Created: 2018-06-29 17:19:56
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DeviceFloodDetectorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceFloodDetectorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceFloodDetectorFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceFloodDetectorAnnc";
	public static final String SHORT_NAME = "deFDrAnnc";
	
	public DeviceFloodDetectorFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceFloodDetectorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getWaterSensor();
		getWaterSensorAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.waterSensor != null) {
			setWaterSensor(this.waterSensor);
		}
		if (this.waterSensorAnnc != null) {
			setWaterSensorAnnc(this.waterSensorAnnc);
		}
	}

	@XmlElement(name=WaterSensorFlexContainer.SHORT_NAME, required=true, type=WaterSensorFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private WaterSensorFlexContainer waterSensor;
		
	public void setWaterSensor(WaterSensorFlexContainer waterSensor) {
		this.waterSensor = waterSensor;
		getFlexContainerOrContainerOrSubscription().add(waterSensor);
	}
	
	public WaterSensorFlexContainer getWaterSensor() {
		this.waterSensor = (WaterSensorFlexContainer) getResourceByName(WaterSensorFlexContainer.SHORT_NAME);
		return waterSensor;
	}
	
	@XmlElement(name=WaterSensorFlexContainerAnnc.SHORT_NAME, required=true, type=WaterSensorFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private WaterSensorFlexContainerAnnc waterSensorAnnc;
		
	public void setWaterSensorAnnc(WaterSensorFlexContainerAnnc waterSensorAnnc) {
		this.waterSensorAnnc = waterSensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(waterSensorAnnc);
	}
	
	public WaterSensorFlexContainerAnnc getWaterSensorAnnc() {
		this.waterSensorAnnc = (WaterSensorFlexContainerAnnc) getResourceByName(WaterSensorFlexContainerAnnc.SHORT_NAME);
		return waterSensorAnnc;
	}
	
}
