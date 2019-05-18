/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : SmokeSensor

This ModuleClass provides the capabilities to indicate the detection of smoke and raising an alarm if the triggering criterion is met.

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = SmokeSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = SmokeSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class SmokeSensorFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "smokeSensor";
	public static final String SHORT_NAME = "smoSr";
		
	public SmokeSensorFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + SmokeSensorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute detectedTime = new CustomAttribute();
		detectedTime.setLongName("detectedTime");
		detectedTime.setShortName("detTe");
		detectedTime.setType("xs:datetime");
		getCustomAttributes().add(detectedTime);
		CustomAttribute alarm = new CustomAttribute();
		alarm.setLongName("alarm");
		alarm.setShortName("alarm");
		alarm.setType("xs:boolean");
		getCustomAttributes().add(alarm);
		CustomAttribute smokeThreshhold = new CustomAttribute();
		smokeThreshhold.setLongName("smokeThreshhold");
		smokeThreshhold.setShortName("smoTd");
		smokeThreshhold.setType("xs:integer");
		getCustomAttributes().add(smokeThreshhold);
		CustomAttribute currentValue = new CustomAttribute();
		currentValue.setLongName("currentValue");
		currentValue.setShortName("crv");
		currentValue.setType("xs:integer");
		getCustomAttributes().add(currentValue);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
