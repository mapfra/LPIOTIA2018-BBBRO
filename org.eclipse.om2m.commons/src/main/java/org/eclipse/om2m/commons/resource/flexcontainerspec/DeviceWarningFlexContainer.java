/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceWarning

A warning device.

Created: 2018-06-29 17:19:56
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceWarningFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceWarningFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceWarningFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceWarning";
	public static final String SHORT_NAME = "devWg";
	
	public DeviceWarningFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceWarningFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getFaultDetection();
		getAlarmSpeaker();
	}
	
	public void finalizeDeserialization() {
		if (this.faultDetection != null) {
			setFaultDetection(this.faultDetection);
		}
		if (this.alarmSpeaker != null) {
			setAlarmSpeaker(this.alarmSpeaker);
		}
	}

	@XmlElement(name=FaultDetectionFlexContainer.SHORT_NAME, required=true, type=FaultDetectionFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private FaultDetectionFlexContainer faultDetection;
		
	public void setFaultDetection(FaultDetectionFlexContainer faultDetection) {
		this.faultDetection = faultDetection;
		getFlexContainerOrContainerOrSubscription().add(faultDetection);
	}
	
	public FaultDetectionFlexContainer getFaultDetection() {
		this.faultDetection = (FaultDetectionFlexContainer) getResourceByName(FaultDetectionFlexContainer.SHORT_NAME);
		return faultDetection;
	}
	
	@XmlElement(name=AlarmSpeakerFlexContainer.SHORT_NAME, required=true, type=AlarmSpeakerFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AlarmSpeakerFlexContainer alarmSpeaker;
		
	public void setAlarmSpeaker(AlarmSpeakerFlexContainer alarmSpeaker) {
		this.alarmSpeaker = alarmSpeaker;
		getFlexContainerOrContainerOrSubscription().add(alarmSpeaker);
	}
	
	public AlarmSpeakerFlexContainer getAlarmSpeaker() {
		this.alarmSpeaker = (AlarmSpeakerFlexContainer) getResourceByName(AlarmSpeakerFlexContainer.SHORT_NAME);
		return alarmSpeaker;
	}
	
}
