/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : UvSensor

This ModuleClass describes the capabilities of an ultraviolet sensor.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = UvSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = UvSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class UvSensorFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "uvSensor";
	public static final String SHORT_NAME = "uveSr";
		
	public UvSensorFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + UvSensorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute uvStatus = new CustomAttribute();
		uvStatus.setLongName("uvStatus");
		uvStatus.setShortName("uvtSs");
		uvStatus.setType("hd:enumUvStatus");
		getCustomAttributes().add(uvStatus);
		CustomAttribute uvValue = new CustomAttribute();
		uvValue.setLongName("uvValue");
		uvValue.setShortName("uvaVe");
		uvValue.setType("xs:float");
		getCustomAttributes().add(uvValue);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
