/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceCameraAnnc

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


@XmlRootElement(name = DeviceCameraFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceCameraFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceCameraFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceCameraAnnc";
	public static final String SHORT_NAME = "devCaAnnc";
	
	public DeviceCameraFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceCameraFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getMotionSensor();
		getMotionSensorAnnc();
		getStreaming();
		getStreamingAnnc();
		getPersonSensor();
		getPersonSensorAnnc();
	}
	
	public void finalizeDeserialization() {
		if (this.motionSensor != null) {
			setMotionSensor(this.motionSensor);
		}
		if (this.motionSensorAnnc != null) {
			setMotionSensorAnnc(this.motionSensorAnnc);
			}
		
		if (this.streaming != null) {
			setStreaming(this.streaming);
		}
		if (this.streamingAnnc != null) {
			setStreamingAnnc(this.streamingAnnc);
			}
		
		if (this.personSensor != null) {
			setPersonSensor(this.personSensor);
		}
		if (this.personSensorAnnc != null) {
			setPersonSensorAnnc(this.personSensorAnnc);
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
	
	@XmlElement(name="streg", required=true, type=StreamingFlexContainerAnnc.class)
	private StreamingFlexContainer streaming;
	
	
	public void setStreaming(StreamingFlexContainer streaming) {
		this.streaming = streaming;
		getFlexContainerOrContainerOrSubscription().add(streaming);
	}
	
	public StreamingFlexContainer getStreaming() {
		this.streaming = (StreamingFlexContainer) getResourceByName(StreamingFlexContainer.SHORT_NAME);
		return streaming;
	}
	
	@XmlElement(name="stregAnnc", required=true, type=StreamingFlexContainerAnnc.class)
	private StreamingFlexContainerAnnc streamingAnnc;
	
	
	public void setStreamingAnnc(StreamingFlexContainerAnnc streamingAnnc) {
		this.streamingAnnc = streamingAnnc;
		getFlexContainerOrContainerOrSubscription().add(streamingAnnc);
	}
	
	public StreamingFlexContainerAnnc getStreamingAnnc() {
		this.streamingAnnc = (StreamingFlexContainerAnnc) getResourceByName(StreamingFlexContainerAnnc.SHORT_NAME);
		return streamingAnnc;
	}
	
	@XmlElement(name="perSr", required=true, type=PersonSensorFlexContainerAnnc.class)
	private PersonSensorFlexContainer personSensor;
	
	
	public void setPersonSensor(PersonSensorFlexContainer personSensor) {
		this.personSensor = personSensor;
		getFlexContainerOrContainerOrSubscription().add(personSensor);
	}
	
	public PersonSensorFlexContainer getPersonSensor() {
		this.personSensor = (PersonSensorFlexContainer) getResourceByName(PersonSensorFlexContainer.SHORT_NAME);
		return personSensor;
	}
	
	@XmlElement(name="perSrAnnc", required=true, type=PersonSensorFlexContainerAnnc.class)
	private PersonSensorFlexContainerAnnc personSensorAnnc;
	
	
	public void setPersonSensorAnnc(PersonSensorFlexContainerAnnc personSensorAnnc) {
		this.personSensorAnnc = personSensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(personSensorAnnc);
	}
	
	public PersonSensorFlexContainerAnnc getPersonSensorAnnc() {
		this.personSensorAnnc = (PersonSensorFlexContainerAnnc) getResourceByName(PersonSensorFlexContainerAnnc.SHORT_NAME);
		return personSensorAnnc;
	}
	
}