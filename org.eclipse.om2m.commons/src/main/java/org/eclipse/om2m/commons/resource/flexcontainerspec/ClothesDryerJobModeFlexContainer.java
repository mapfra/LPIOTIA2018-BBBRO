/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : ClothesDryerJobMode

This ModuleClasses provides capabilities to control and monitor the job modes of a clothes dryer.

Created: 2018-06-29 17:19:51
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = ClothesDryerJobModeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ClothesDryerJobModeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class ClothesDryerJobModeFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "clothesDryerJobMode";
	public static final String SHORT_NAME = "cDJMe";
		
	public ClothesDryerJobModeFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + ClothesDryerJobModeFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute currentJobModeName = new CustomAttribute();
		currentJobModeName.setLongName("currentJobModeName");
		currentJobModeName.setShortName("cJMNe");
		currentJobModeName.setType("xs:string");
		getCustomAttributes().add(currentJobModeName);
		CustomAttribute currentJobMode = new CustomAttribute();
		currentJobMode.setLongName("currentJobMode");
		currentJobMode.setShortName("cuJMe");
		currentJobMode.setType("hd:enumClothesDryerJobMode");
		getCustomAttributes().add(currentJobMode);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
