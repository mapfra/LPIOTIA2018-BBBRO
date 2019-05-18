/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : RemoteControlEnable

This ModuleClasses provides capabilities to monitor the remote controllability of the appliance.

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = RemoteControlEnableFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = RemoteControlEnableFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class RemoteControlEnableFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "remoteControlEnable";
	public static final String SHORT_NAME = "reCEe";
		
	public RemoteControlEnableFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + RemoteControlEnableFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute remoteControlEnabled = new CustomAttribute();
		remoteControlEnabled.setLongName("remoteControlEnabled");
		remoteControlEnabled.setShortName("reCEd");
		remoteControlEnabled.setType("xs:boolean");
		getCustomAttributes().add(remoteControlEnabled);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
