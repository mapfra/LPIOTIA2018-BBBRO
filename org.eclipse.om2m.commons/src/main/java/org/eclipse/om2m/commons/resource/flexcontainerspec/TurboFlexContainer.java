/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : Turbo

This ModuleClass provides capabilities to enable turbo mode and monitor the current status of the turbo function. It is intended to be part of devices which use turbo function such as an air conditioner, a washing machine etc.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = TurboFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = TurboFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class TurboFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "turbo";
	public static final String SHORT_NAME = "turbo";
		
	public TurboFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + TurboFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute turboEnabled = new CustomAttribute();
		turboEnabled.setLongName("turboEnabled");
		turboEnabled.setShortName("turEd");
		turboEnabled.setType("xs:boolean");
		getCustomAttributes().add(turboEnabled);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
