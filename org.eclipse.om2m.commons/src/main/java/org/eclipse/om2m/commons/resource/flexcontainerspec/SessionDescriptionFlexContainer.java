/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : SessionDescription

This ModuleClass provides the capabilities for a sessionDescription containing a URL at twhich the specified media can be accessed and the definition of media using SDP.

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = SessionDescriptionFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = SessionDescriptionFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class SessionDescriptionFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "sessionDescription";
	public static final String SHORT_NAME = "sesDn";
		
	public SessionDescriptionFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + SessionDescriptionFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute url = new CustomAttribute();
		url.setLongName("url");
		url.setShortName("ur1");
		url.setType("xs:uri");
		getCustomAttributes().add(url);
		CustomAttribute sdp = new CustomAttribute();
		sdp.setLongName("sdp");
		sdp.setShortName("sdp");
		sdp.setType("xs:string");
		getCustomAttributes().add(sdp);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
