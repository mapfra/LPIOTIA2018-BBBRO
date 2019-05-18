/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : Brewing

This ModuleClass provides capabilities to control and monitor a brewing process. It is intended to be part of devices that prepare hot drinks such as a coffee or a tea.

Created: 2018-06-29 17:19:51
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = BrewingFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = BrewingFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class BrewingFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "brewing";
	public static final String SHORT_NAME = "brewg";
		
	public BrewingFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + BrewingFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute strength = new CustomAttribute();
		strength.setLongName("strength");
		strength.setShortName("streh");
		strength.setType("hd:enumTasteStrength");
		getCustomAttributes().add(strength);
		CustomAttribute cupsNumber = new CustomAttribute();
		cupsNumber.setLongName("cupsNumber");
		cupsNumber.setShortName("cupNr");
		cupsNumber.setType("xs:integer");
		getCustomAttributes().add(cupsNumber);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
