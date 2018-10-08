/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceCamera

A camera is an optical instrument for recording or capturing images, which may be stored locally or transmitted to another locations.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

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
		getSessionDescription();
		getPlayerControl();
		getMotionSensor();
	}
	
	public void finalizeDeserialization() {
		if (this.sessionDescription != null) {
			setSessionDescription(this.sessionDescription);
		}
		if (this.playerControl != null) {
			setPlayerControl(this.playerControl);
		}
		if (this.motionSensor != null) {
			setMotionSensor(this.motionSensor);
		}
	}

	@XmlElement(name=SessionDescriptionFlexContainer.SHORT_NAME, required=true, type=SessionDescriptionFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private SessionDescriptionFlexContainer sessionDescription;
		
	public void setSessionDescription(SessionDescriptionFlexContainer sessionDescription) {
		this.sessionDescription = sessionDescription;
		getFlexContainerOrContainerOrSubscription().add(sessionDescription);
	}
	
	public SessionDescriptionFlexContainer getSessionDescription() {
		this.sessionDescription = (SessionDescriptionFlexContainer) getResourceByName(SessionDescriptionFlexContainer.SHORT_NAME);
		return sessionDescription;
	}
	
	@XmlElement(name=PlayerControlFlexContainer.SHORT_NAME, required=true, type=PlayerControlFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private PlayerControlFlexContainer playerControl;
		
	public void setPlayerControl(PlayerControlFlexContainer playerControl) {
		this.playerControl = playerControl;
		getFlexContainerOrContainerOrSubscription().add(playerControl);
	}
	
	public PlayerControlFlexContainer getPlayerControl() {
		this.playerControl = (PlayerControlFlexContainer) getResourceByName(PlayerControlFlexContainer.SHORT_NAME);
		return playerControl;
	}
	
	@XmlElement(name=MotionSensorFlexContainer.SHORT_NAME, required=true, type=MotionSensorFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private MotionSensorFlexContainer motionSensor;
		
	public void setMotionSensor(MotionSensorFlexContainer motionSensor) {
		this.motionSensor = motionSensor;
		getFlexContainerOrContainerOrSubscription().add(motionSensor);
	}
	
	public MotionSensorFlexContainer getMotionSensor() {
		this.motionSensor = (MotionSensorFlexContainer) getResourceByName(MotionSensorFlexContainer.SHORT_NAME);
		return motionSensor;
	}
	
}
