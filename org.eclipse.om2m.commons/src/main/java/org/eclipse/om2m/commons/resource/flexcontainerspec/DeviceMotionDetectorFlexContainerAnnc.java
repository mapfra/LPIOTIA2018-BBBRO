/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceMotionDetectorAnnc

A MotionDetector is a device that triggers alarm in case of motion detection.

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


@XmlRootElement(name = DeviceMotionDetectorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceMotionDetectorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceMotionDetectorFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceMotionDetectorAnnc";
	public static final String SHORT_NAME = "deMDrAnnc";
	
	public DeviceMotionDetectorFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceMotionDetectorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getMotionSensor();
		getMotionSensorAnnc();
	}
	
	public void finalizeDeserialization() {
		if (this.motionSensor != null) {
			setMotionSensor(this.motionSensor);
		}
		if (this.motionSensorAnnc != null) {
			setMotionSensorAnnc(this.motionSensorAnnc);
			}
		
	}
	
	@XmlElement(name="motSr", required=true, type=MotionSensorFlexContainerAnnc.class)
	private MotionSensorFlexContainer motionSensor;
	
	
	public void setMotionSensor(MotionSensorFlexContainer motionSensor) {
		this.motionSensor = motionSensor;
		getFlexContainerOrContainerOrSubscription().add(motionSensor);
	}
	
	public MotionSensorFlexContainer getMotionSensor() {
		this.motionSensor = (MotionSensorFlexContainer) getResourceByName(MotionSensorFlexContainer.SHORT_NAME);
		return motionSensor;
	}
	
	@XmlElement(name="motSrAnnc", required=true, type=MotionSensorFlexContainerAnnc.class)
	private MotionSensorFlexContainerAnnc motionSensorAnnc;
	
	
	public void setMotionSensorAnnc(MotionSensorFlexContainerAnnc motionSensorAnnc) {
		this.motionSensorAnnc = motionSensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(motionSensorAnnc);
	}
	
	public MotionSensorFlexContainerAnnc getMotionSensorAnnc() {
		this.motionSensorAnnc = (MotionSensorFlexContainerAnnc) getResourceByName(MotionSensorFlexContainerAnnc.SHORT_NAME);
		return motionSensorAnnc;
	}
	
}