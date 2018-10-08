/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : MotionSensor

This ModuleClass provides the capabilities to indicate the occurrence of motion and raising of an alarm if the triggering criterion is met.

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = MotionSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = MotionSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class MotionSensorFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "motionSensor";
	public static final String SHORT_NAME = "motSr";
		
	public MotionSensorFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + MotionSensorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute silentTime = new CustomAttribute();
		silentTime.setLongName("silentTime");
		silentTime.setShortName("silTe");
		silentTime.setType("xs:integer");
		getCustomAttributes().add(silentTime);
		CustomAttribute alarm = new CustomAttribute();
		alarm.setLongName("alarm");
		alarm.setShortName("alarm");
		alarm.setType("xs:boolean");
		getCustomAttributes().add(alarm);
		CustomAttribute sensitivity = new CustomAttribute();
		sensitivity.setLongName("sensitivity");
		sensitivity.setShortName("sensy");
		sensitivity.setType("xs:integer");
		getCustomAttributes().add(sensitivity);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
