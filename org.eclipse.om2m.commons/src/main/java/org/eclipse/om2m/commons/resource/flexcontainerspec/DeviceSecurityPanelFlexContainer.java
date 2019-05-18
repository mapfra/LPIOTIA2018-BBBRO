/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceSecurityPanel

A security pannel is a device that can change the security mode of, for example, an alarm system.

Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceSecurityPanelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceSecurityPanelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceSecurityPanelFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceSecurityPanel";
	public static final String SHORT_NAME = "deSPl";
	
	public DeviceSecurityPanelFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceSecurityPanelFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getSecurityMode();
	}
	
	public void finalizeDeserialization() {
		if (this.securityMode != null) {
			setSecurityMode(this.securityMode);
		}
	}

	@XmlElement(name=SecurityModeFlexContainer.SHORT_NAME, required=true, type=SecurityModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private SecurityModeFlexContainer securityMode;
		
	public void setSecurityMode(SecurityModeFlexContainer securityMode) {
		this.securityMode = securityMode;
		getFlexContainerOrContainerOrSubscription().add(securityMode);
	}
	
	public SecurityModeFlexContainer getSecurityMode() {
		this.securityMode = (SecurityModeFlexContainer) getResourceByName(SecurityModeFlexContainer.SHORT_NAME);
		return securityMode;
	}
	
}
