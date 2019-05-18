/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : PrintQueue

This ModuleClass provides the capabilities for monitoring printing list information.

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = PrintQueueFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = PrintQueueFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class PrintQueueFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "printQueue";
	public static final String SHORT_NAME = "priQe";
		
	public PrintQueueFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + PrintQueueFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute printingState = new CustomAttribute();
		printingState.setLongName("printingState");
		printingState.setShortName("priSe");
		printingState.setType("[hd:enumJobState]");
		getCustomAttributes().add(printingState);
		CustomAttribute uri = new CustomAttribute();
		uri.setLongName("uri");
		uri.setShortName("ur0");
		uri.setType("[xs:uri]");
		getCustomAttributes().add(uri);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
