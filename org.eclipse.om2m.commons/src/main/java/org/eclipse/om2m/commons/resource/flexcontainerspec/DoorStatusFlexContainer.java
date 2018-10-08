/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : DoorStatus

This ModuleClass provides the status of a door. It is intended to be part of a device such as a refrigerator and an oven that might have multiple doors. 

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = DoorStatusFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DoorStatusFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DoorStatusFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "doorStatus";
	public static final String SHORT_NAME = "dooSs";
		
	public DoorStatusFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + DoorStatusFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute doorState = new CustomAttribute();
		doorState.setLongName("doorState");
		doorState.setShortName("dooSe");
		doorState.setType("hd:enumDoorState");
		getCustomAttributes().add(doorState);
		CustomAttribute openAlarm = new CustomAttribute();
		openAlarm.setLongName("openAlarm");
		openAlarm.setShortName("opeAm");
		openAlarm.setType("xs:boolean");
		getCustomAttributes().add(openAlarm);
		CustomAttribute openDuration = new CustomAttribute();
		openDuration.setLongName("openDuration");
		openDuration.setShortName("opeDn");
		openDuration.setType("xs:datetime");
		getCustomAttributes().add(openDuration);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
