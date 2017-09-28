/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : ColourSaturationAnnc

This ModuleClass describes a colour saturation value. The value  is an integer. A colourSaturation has a range of [0,100]. A  colourSaturation value of 0 means producing black and white images.  A colourSaturation value of 50 means producing device specific  normal colour images. A colourSaturation value of 100 means  producing device very colourfull images.

Created: 2017-09-28 17:26:40
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;


@XmlRootElement(name = ColourSaturationFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ColourSaturationFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class ColourSaturationFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "colourSaturationAnnc";
	public static final String SHORT_NAME = "colSnAnnc";
	
	public ColourSaturationFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass." + ColourSaturationFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}