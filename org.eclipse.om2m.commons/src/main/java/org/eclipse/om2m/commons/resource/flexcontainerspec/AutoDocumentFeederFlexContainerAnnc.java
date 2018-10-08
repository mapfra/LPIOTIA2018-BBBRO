/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : AutoDocumentFeederAnnc

This ModuleClasses provides capabilities to monitor the state of autoDocumentFeeder (ADF). ADF is a feature which takes several pages and feeds the paper one page at a time into a scanner or printer, allowing the user to scan, print or fax, multiple-page documents without having to manually replace each page.

Created: 2018-06-29 17:19:51
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = AutoDocumentFeederFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = AutoDocumentFeederFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class AutoDocumentFeederFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "autoDocumentFeederAnnc";
	public static final String SHORT_NAME = "auDFrAnnc";
	
	public AutoDocumentFeederFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass." + AutoDocumentFeederFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
	
}
