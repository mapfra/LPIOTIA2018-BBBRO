/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : KeepWarmAnnc

This module allows to control the ?keep warm? feature in devices like coffe machines, kettles etc. It allows to keep water warm for a desired time. This ModuleClass inherits from binarySwitch (see clause 5.3.12) to store setting for the ?keep warm? feature. If the "powerState" data point in a keepWarmSwitch is "True" then the ?keep warm? function will be performed just after boiling (or heating) process is finished (otherwise this function will not be applied).

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = KeepWarmFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = KeepWarmFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class KeepWarmFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "keepWarmAnnc";
	public static final String SHORT_NAME = "keeWmAnnc";
	
	public KeepWarmFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass." + KeepWarmFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
	
}
