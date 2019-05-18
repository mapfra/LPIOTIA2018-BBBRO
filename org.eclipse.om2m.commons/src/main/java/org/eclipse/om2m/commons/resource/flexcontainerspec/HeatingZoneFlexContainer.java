/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : HeatingZone

This ModuleClass provides the capabilities to monitor the status of the heating zone, for example for a cooktop.

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = HeatingZoneFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = HeatingZoneFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class HeatingZoneFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "heatingZone";
	public static final String SHORT_NAME = "heaZe";
		
	public HeatingZoneFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + HeatingZoneFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute heatingLevel = new CustomAttribute();
		heatingLevel.setLongName("heatingLevel");
		heatingLevel.setShortName("heaLl");
		heatingLevel.setType("xs:integer");
		getCustomAttributes().add(heatingLevel);
		CustomAttribute maxHeatingLevel = new CustomAttribute();
		maxHeatingLevel.setLongName("maxHeatingLevel");
		maxHeatingLevel.setShortName("maHLl");
		maxHeatingLevel.setType("xs:integer");
		getCustomAttributes().add(maxHeatingLevel);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
