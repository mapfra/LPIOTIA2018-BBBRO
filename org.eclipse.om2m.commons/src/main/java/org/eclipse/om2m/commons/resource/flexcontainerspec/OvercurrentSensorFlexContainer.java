/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : OvercurrentSensor

This ModuleClass provides capabilities for an over-current sensor. 

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = OvercurrentSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = OvercurrentSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class OvercurrentSensorFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "overcurrentSensor";
	public static final String SHORT_NAME = "oveSr";
		
	public OvercurrentSensorFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + OvercurrentSensorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute detectedTime = new CustomAttribute();
		detectedTime.setLongName("detectedTime");
		detectedTime.setShortName("detTe");
		detectedTime.setType("xs:datetime");
		getCustomAttributes().add(detectedTime);
		CustomAttribute duration = new CustomAttribute();
		duration.setLongName("duration");
		duration.setShortName("dur");
		duration.setType("xs:float");
		getCustomAttributes().add(duration);
		CustomAttribute overcurrentStatus = new CustomAttribute();
		overcurrentStatus.setLongName("overcurrentStatus");
		overcurrentStatus.setShortName("oveSs");
		overcurrentStatus.setType("xs:boolean");
		getCustomAttributes().add(overcurrentStatus);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
