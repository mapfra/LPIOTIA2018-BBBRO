/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : OpenLevel

This ModuleClass provides the capabilities to control and monitor the open status of an entity, for example a curtain.

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = OpenLevelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = OpenLevelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class OpenLevelFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "openLevel";
	public static final String SHORT_NAME = "opeLl";
		
	public OpenLevelFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + OpenLevelFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute minLevel = new CustomAttribute();
		minLevel.setLongName("minLevel");
		minLevel.setShortName("minLl");
		minLevel.setType("xs:integer");
		getCustomAttributes().add(minLevel);
		CustomAttribute maxLevel = new CustomAttribute();
		maxLevel.setLongName("maxLevel");
		maxLevel.setShortName("maxLl");
		maxLevel.setType("xs:integer");
		getCustomAttributes().add(maxLevel);
		CustomAttribute openLevel = new CustomAttribute();
		openLevel.setLongName("openLevel");
		openLevel.setShortName("opeLl");
		openLevel.setType("xs:integer");
		getCustomAttributes().add(openLevel);
		CustomAttribute stepValue = new CustomAttribute();
		stepValue.setLongName("stepValue");
		stepValue.setShortName("steVe");
		stepValue.setType("xs:integer");
		getCustomAttributes().add(stepValue);
	}


	@XmlElement(name=OpenFlexContainer.SHORT_NAME, required=true, type=OpenFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private OpenFlexContainer open;
	
	public OpenFlexContainer getOpen() {
		this.open = (OpenFlexContainer) getResourceByName(OpenFlexContainer.SHORT_NAME);
		return open;
	}
	
	public void setOpen(OpenFlexContainer newAction) {
		this.open = newAction;
		getFlexContainerOrContainerOrSubscription().add(open);
	}

	@XmlElement(name=CloseFlexContainer.SHORT_NAME, required=true, type=CloseFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private CloseFlexContainer close;
	
	public CloseFlexContainer getClose() {
		this.close = (CloseFlexContainer) getResourceByName(CloseFlexContainer.SHORT_NAME);
		return close;
	}
	
	public void setClose(CloseFlexContainer newAction) {
		this.close = newAction;
		getFlexContainerOrContainerOrSubscription().add(close);
	}
		
	public void finalizeSerialization() {
		getOpen();
		getClose();
	}
	
	public void finalizeDeserialization() {
		if (this.open != null) {
			setOpen(this.open);
		}
		if (this.close != null) {
			setClose(this.close);
		}
	}
	
}
