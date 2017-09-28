/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceWarningDevice

A WarningDevice is a device that prevents users about an alarm (ie a siren).

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


@XmlRootElement(name = DeviceWarningDeviceFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceWarningDeviceFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceWarningDeviceFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceWarningDevice";
	public static final String SHORT_NAME = "deWDe";
	
	public DeviceWarningDeviceFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceWarningDeviceFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getAlarmSpeaker();
		getFaultDetection();
	}
	
	public void finalizeDeserialization() {
		if (this.alarmSpeaker != null) {
			setAlarmSpeaker(this.alarmSpeaker);
		}
		if (this.faultDetection != null) {
			setFaultDetection(this.faultDetection);
		}
	}
	
	@XmlElement(name="alaSr", required=true, type=AlarmSpeakerFlexContainer.class)
	private AlarmSpeakerFlexContainer alarmSpeaker;
	
	
	public void setAlarmSpeaker(AlarmSpeakerFlexContainer alarmSpeaker) {
		this.alarmSpeaker = alarmSpeaker;
		getFlexContainerOrContainerOrSubscription().add(alarmSpeaker);
	}
	
	public AlarmSpeakerFlexContainer getAlarmSpeaker() {
		this.alarmSpeaker = (AlarmSpeakerFlexContainer) getResourceByName(AlarmSpeakerFlexContainer.SHORT_NAME);
		return alarmSpeaker;
	}
	
	@XmlElement(name="fauDn", required=true, type=FaultDetectionFlexContainer.class)
	private FaultDetectionFlexContainer faultDetection;
	
	
	public void setFaultDetection(FaultDetectionFlexContainer faultDetection) {
		this.faultDetection = faultDetection;
		getFlexContainerOrContainerOrSubscription().add(faultDetection);
	}
	
	public FaultDetectionFlexContainer getFaultDetection() {
		this.faultDetection = (FaultDetectionFlexContainer) getResourceByName(FaultDetectionFlexContainer.SHORT_NAME);
		return faultDetection;
	}
	
}