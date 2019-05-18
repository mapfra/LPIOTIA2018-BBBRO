/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : HotWaterSupply

This ModuleClass provides the information about the status of supplying hot water into tanks or bath tubes.

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = HotWaterSupplyFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = HotWaterSupplyFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class HotWaterSupplyFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "hotWaterSupply";
	public static final String SHORT_NAME = "hoWSy";
		
	public HotWaterSupplyFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + HotWaterSupplyFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute bath = new CustomAttribute();
		bath.setLongName("bath");
		bath.setShortName("bath");
		bath.setType("xs:boolean");
		getCustomAttributes().add(bath);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
