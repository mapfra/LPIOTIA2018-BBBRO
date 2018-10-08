/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : SpinLevel

This ModuleClass provides capabilities to control and monitor the level of spin. It is intended to be part of devices which use spinning function such as a washing machine and a dryer.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = SpinLevelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = SpinLevelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class SpinLevelFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "spinLevel";
	public static final String SHORT_NAME = "spiLl";
		
	public SpinLevelFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + SpinLevelFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute spinLevelStrength = new CustomAttribute();
		spinLevelStrength.setLongName("spinLevelStrength");
		spinLevelStrength.setShortName("spLSh");
		spinLevelStrength.setType("hd:enumSpinLevelStrength");
		getCustomAttributes().add(spinLevelStrength);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
