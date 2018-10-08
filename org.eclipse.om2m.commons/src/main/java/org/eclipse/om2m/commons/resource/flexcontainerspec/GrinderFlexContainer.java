/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : Grinder

This ModuleClass is for controlling a grinder, for example in a coffee machine.

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = GrinderFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = GrinderFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class GrinderFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "grinder";
	public static final String SHORT_NAME = "grinr";
		
	public GrinderFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + GrinderFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute grainsRemaining = new CustomAttribute();
		grainsRemaining.setLongName("grainsRemaining");
		grainsRemaining.setShortName("graRg");
		grainsRemaining.setType("hd:enumGrainsLevel");
		getCustomAttributes().add(grainsRemaining);
		CustomAttribute useGrinder = new CustomAttribute();
		useGrinder.setLongName("useGrinder");
		useGrinder.setShortName("useGr");
		useGrinder.setType("xs:boolean");
		getCustomAttributes().add(useGrinder);
		CustomAttribute coarseness = new CustomAttribute();
		coarseness.setLongName("coarseness");
		coarseness.setShortName("coars");
		coarseness.setType("hd:enumGrindCoarseness");
		getCustomAttributes().add(coarseness);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
