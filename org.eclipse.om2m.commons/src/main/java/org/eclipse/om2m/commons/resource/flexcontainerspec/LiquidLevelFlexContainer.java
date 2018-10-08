/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : LiquidLevel

This ModuleClass provides the desired level of water (or other liquid)  for an appliance, for example the desired level of milk for a cup of coffee from a coffee machine.

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = LiquidLevelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = LiquidLevelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class LiquidLevelFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "liquidLevel";
	public static final String SHORT_NAME = "liqLl";
		
	public LiquidLevelFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + LiquidLevelFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute liquidLevel = new CustomAttribute();
		liquidLevel.setLongName("liquidLevel");
		liquidLevel.setShortName("liqLl");
		liquidLevel.setType("hd:enumLiquidLevel");
		getCustomAttributes().add(liquidLevel);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
