/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : ColourSaturation

This ModuleClass provides cababilities to control and monitor a colour saturation value.

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = ColourSaturationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ColourSaturationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class ColourSaturationFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "colourSaturation";
	public static final String SHORT_NAME = "colSn";
		
	public ColourSaturationFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + ColourSaturationFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute colourSaturation = new CustomAttribute();
		colourSaturation.setLongName("colourSaturation");
		colourSaturation.setShortName("colSn");
		colourSaturation.setType("xs:integer");
		getCustomAttributes().add(colourSaturation);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
