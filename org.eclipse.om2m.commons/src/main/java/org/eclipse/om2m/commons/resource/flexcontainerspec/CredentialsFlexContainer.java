/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : Credentials

This ModuleClass provides the capability to manage user credentials which allows a user to authenticate on an appliance or a server that is associated with the appliance. The authentication depends on a user login and password, or on a token. An example appliance which may include this ModuleClass is a camera. 

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = CredentialsFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = CredentialsFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class CredentialsFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "credentials";
	public static final String SHORT_NAME = "creds";
		
	public CredentialsFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + CredentialsFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute password = new CustomAttribute();
		password.setLongName("password");
		password.setShortName("pwd");
		password.setType("xs:string");
		getCustomAttributes().add(password);
		CustomAttribute loginName = new CustomAttribute();
		loginName.setLongName("loginName");
		loginName.setShortName("logNe");
		loginName.setType("xs:string");
		getCustomAttributes().add(loginName);
		CustomAttribute token = new CustomAttribute();
		token.setLongName("token");
		token.setShortName("tk");
		token.setType("xs:string");
		getCustomAttributes().add(token);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
