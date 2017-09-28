/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceFloodDetectorAnnc

A door is a door.

Created: 2017-09-28 17:26:40
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
	
	@XmlElement(name="watSr", required=true, type=WaterSensorFlexContainerAnnc.class)
	private WaterSensorFlexContainer waterSensor;
	
	
	public void setWaterSensor(WaterSensorFlexContainer waterSensor) {
		this.waterSensor = waterSensor;
		getFlexContainerOrContainerOrSubscription().add(waterSensor);
	}
	
	public WaterSensorFlexContainer getWaterSensor() {
		this.waterSensor = (WaterSensorFlexContainer) getResourceByName(WaterSensorFlexContainer.SHORT_NAME);
		return waterSensor;
	}
	
	@XmlElement(name="watSrAnnc", required=true, type=WaterSensorFlexContainerAnnc.class)
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