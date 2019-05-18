/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : AcousticSensor

This ModuleClass provides capabilities for an acoustic sensor.

Created: 2018-06-29 17:19:51
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = AcousticSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = AcousticSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class AcousticSensorFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "acousticSensor";
	public static final String SHORT_NAME = "acoSr";
		
	public AcousticSensorFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + AcousticSensorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute loudness = new CustomAttribute();
		loudness.setLongName("loudness");
		loudness.setShortName("louds");
		loudness.setType("xs:float");
		getCustomAttributes().add(loudness);
		CustomAttribute acousticStatus = new CustomAttribute();
		acousticStatus.setLongName("acousticStatus");
		acousticStatus.setShortName("acoSs");
		acousticStatus.setType("xs:integer");
		getCustomAttributes().add(acousticStatus);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
