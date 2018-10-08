/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceSecurityPanelAnnc

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
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DeviceSecurityPanelFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceSecurityPanelFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceSecurityPanelFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceSecurityPanelAnnc";
	public static final String SHORT_NAME = "deSPlAnnc";
	
	public DeviceSecurityPanelFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceSecurityPanelFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getSecurityMode();
		getSecurityModeAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.securityMode != null) {
			setSecurityMode(this.securityMode);
		}
		if (this.securityModeAnnc != null) {
			setSecurityModeAnnc(this.securityModeAnnc);
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
	
	@XmlElement(name=SecurityModeFlexContainerAnnc.SHORT_NAME, required=true, type=SecurityModeFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private SecurityModeFlexContainerAnnc securityModeAnnc;
		
	public void setSecurityModeAnnc(SecurityModeFlexContainerAnnc securityModeAnnc) {
		this.securityModeAnnc = securityModeAnnc;
		getFlexContainerOrContainerOrSubscription().add(securityModeAnnc);
	}
	
	public SecurityModeFlexContainerAnnc getSecurityModeAnnc() {
		this.securityModeAnnc = (SecurityModeFlexContainerAnnc) getResourceByName(SecurityModeFlexContainerAnnc.SHORT_NAME);
		return securityModeAnnc;
	}
	
}
