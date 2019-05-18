/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : OpenLevelAnnc

This ModuleClass provides the capabilities to control and monitor the open status of an entity, for example a curtain.

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = OpenLevelFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = OpenLevelFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class OpenLevelFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "openLevelAnnc";
	public static final String SHORT_NAME = "opeLlAnnc";
	
	public OpenLevelFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass." + OpenLevelFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
		
	public void finalizeSerialization() {
		getOpenAnnc();	  
		getCloseAnnc();	  
	}
	
	public void finalizeDeserialization() {
		if (this.openAnnc != null){
			setOpenAnnc(openAnnc);	  
		}
		if (this.closeAnnc != null){
			setCloseAnnc(closeAnnc);	  
		}
	}
	
	@XmlElement(name=OpenFlexContainerAnnc.SHORT_NAME, required=true, type=OpenFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private OpenFlexContainerAnnc openAnnc;	
	
	public void setOpenAnnc(OpenFlexContainerAnnc openAnnc) {
		this.openAnnc = openAnnc;
		getFlexContainerOrContainerOrSubscription().add(openAnnc);
	}
	
	public OpenFlexContainerAnnc getOpenAnnc() {
		this.openAnnc = (OpenFlexContainerAnnc) getResourceByName(OpenFlexContainerAnnc.SHORT_NAME);
		return openAnnc;
	}
	@XmlElement(name=CloseFlexContainerAnnc.SHORT_NAME, required=true, type=CloseFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private CloseFlexContainerAnnc closeAnnc;	
	
	public void setCloseAnnc(CloseFlexContainerAnnc closeAnnc) {
		this.closeAnnc = closeAnnc;
		getFlexContainerOrContainerOrSubscription().add(closeAnnc);
	}
	
	public CloseFlexContainerAnnc getCloseAnnc() {
		this.closeAnnc = (CloseFlexContainerAnnc) getResourceByName(CloseFlexContainerAnnc.SHORT_NAME);
		return closeAnnc;
	}
	
}
