/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceCamera

A Camera is a device that provides video streaming feature.

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


@XmlRootElement(name = DeviceCameraFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceCameraFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceCameraFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceCamera";
	public static final String SHORT_NAME = "devCa";
	
	public DeviceCameraFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceCameraFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getMotionSensor();
		getStreaming();
		getPersonSensor();
	}
	
	public void finalizeDeserialization() {
		if (this.motionSensor != null) {
			setMotionSensor(this.motionSensor);
		}
		if (this.streaming != null) {
			setStreaming(this.streaming);
		}
		if (this.personSensor != null) {
			setPersonSensor(this.personSensor);
		}
	}
	
	@XmlElement(name="motSr", required=true, type=MotionSensorFlexContainer.class)
	private MotionSensorFlexContainer motionSensor;
	
	
	public void setMotionSensor(MotionSensorFlexContainer motionSensor) {
		this.motionSensor = motionSensor;
		getFlexContainerOrContainerOrSubscription().add(motionSensor);
	}
	
	public MotionSensorFlexContainer getMotionSensor() {
		this.motionSensor = (MotionSensorFlexContainer) getResourceByName(MotionSensorFlexContainer.SHORT_NAME);
		return motionSensor;
	}
	
	@XmlElement(name="streg", required=true, type=StreamingFlexContainer.class)
	private StreamingFlexContainer streaming;
	
	
	public void setStreaming(StreamingFlexContainer streaming) {
		this.streaming = streaming;
		getFlexContainerOrContainerOrSubscription().add(streaming);
	}
	
	public StreamingFlexContainer getStreaming() {
		this.streaming = (StreamingFlexContainer) getResourceByName(StreamingFlexContainer.SHORT_NAME);
		return streaming;
	}
	
	@XmlElement(name="perSr", required=true, type=PersonSensorFlexContainer.class)
	private PersonSensorFlexContainer personSensor;
	
	
	public void setPersonSensor(PersonSensorFlexContainer personSensor) {
		this.personSensor = personSensor;
		getFlexContainerOrContainerOrSubscription().add(personSensor);
	}
	
	public PersonSensorFlexContainer getPersonSensor() {
		this.personSensor = (PersonSensorFlexContainer) getResourceByName(PersonSensorFlexContainer.SHORT_NAME);
		return personSensor;
	}
	
}