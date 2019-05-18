/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : TemperatureAlarm

This ModuleClass provides the capabilities to indicate the detection of abnormal temperatures and raises an alarm if the triggering criterion is met.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = TemperatureAlarmFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = TemperatureAlarmFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class TemperatureAlarmFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "temperatureAlarm";
	public static final String SHORT_NAME = "temAm";
		
	public TemperatureAlarmFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + TemperatureAlarmFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute alarm = new CustomAttribute();
		alarm.setLongName("alarm");
		alarm.setShortName("alarm");
		alarm.setType("xs:boolean");
		getCustomAttributes().add(alarm);
		CustomAttribute temperature = new CustomAttribute();
		temperature.setLongName("temperature");
		temperature.setShortName("tempe");
		temperature.setType("xs:float");
		getCustomAttributes().add(temperature);
		CustomAttribute temperatureThreshhold = new CustomAttribute();
		temperatureThreshhold.setLongName("temperatureThreshhold");
		temperatureThreshhold.setShortName("temTd");
		temperatureThreshhold.setType("xs:integer");
		getCustomAttributes().add(temperatureThreshhold);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
