/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : OzoneMeter

This ModuleClass provides capabilities for an ozone meter. The "ozoneValue?" attributes are optional, but one of them SHALL be provided.

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = OzoneMeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = OzoneMeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class OzoneMeterFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "ozoneMeter";
	public static final String SHORT_NAME = "ozoMr";
		
	public OzoneMeterFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + OzoneMeterFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute ozoneValueMG = new CustomAttribute();
		ozoneValueMG.setLongName("ozoneValueMG");
		ozoneValueMG.setShortName("ozVMG");
		ozoneValueMG.setType("xs:float");
		getCustomAttributes().add(ozoneValueMG);
		CustomAttribute ozoneStatus = new CustomAttribute();
		ozoneStatus.setLongName("ozoneStatus");
		ozoneStatus.setShortName("ozoSs");
		ozoneStatus.setType("hd:enumOzoneStatus");
		getCustomAttributes().add(ozoneStatus);
		CustomAttribute maxValue = new CustomAttribute();
		maxValue.setLongName("maxValue");
		maxValue.setShortName("maxVe");
		maxValue.setType("xs:float");
		getCustomAttributes().add(maxValue);
		CustomAttribute ozoneValuePPM = new CustomAttribute();
		ozoneValuePPM.setLongName("ozoneValuePPM");
		ozoneValuePPM.setShortName("oVPPM");
		ozoneValuePPM.setType("xs:float");
		getCustomAttributes().add(ozoneValuePPM);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
