/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : CredentialsAnnc

This ModuleClass provides the capability to manage user credentials which allows a user to authenticate on an appliance or a server that is associated with the appliance. The authentication depends on a user login and password, or on a token. An example appliance which may include this ModuleClass is a camera. 

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = CredentialsFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = CredentialsFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class CredentialsFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "credentialsAnnc";
	public static final String SHORT_NAME = "credsAnnc";
	
	public CredentialsFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass." + CredentialsFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
	
}
