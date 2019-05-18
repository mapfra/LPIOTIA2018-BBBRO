/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : AlarmSpeaker

This ModuleClass provides the capabilites to initiate and monitor an alarm.

Created: 2018-06-29 17:19:51
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = AlarmSpeakerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = AlarmSpeakerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class AlarmSpeakerFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "alarmSpeaker";
	public static final String SHORT_NAME = "alaSr";
		
	public AlarmSpeakerFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + AlarmSpeakerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute alarmStatus = new CustomAttribute();
		alarmStatus.setLongName("alarmStatus");
		alarmStatus.setShortName("alaSs");
		alarmStatus.setType("xs:boolean");
		getCustomAttributes().add(alarmStatus);
		CustomAttribute tone = new CustomAttribute();
		tone.setLongName("tone");
		tone.setShortName("tone");
		tone.setType("hd:enumTone");
		getCustomAttributes().add(tone);
		CustomAttribute light = new CustomAttribute();
		light.setLongName("light");
		light.setShortName("light");
		light.setType("hd:enumAlertColourCode");
		getCustomAttributes().add(light);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
