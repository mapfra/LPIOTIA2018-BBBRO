/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : Pulsemeter

This ModuleClass provides the capability to report the measurement of pulse characteristics.

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = PulsemeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = PulsemeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class PulsemeterFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "pulsemeter";
	public static final String SHORT_NAME = "pulsr";
		
	public PulsemeterFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + PulsemeterFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute rr = new CustomAttribute();
		rr.setLongName("rr");
		rr.setShortName("r0");
		rr.setType("xs:float");
		getCustomAttributes().add(rr);
		CustomAttribute modality = new CustomAttribute();
		modality.setLongName("modality");
		modality.setShortName("moday");
		modality.setType("xs:string");
		getCustomAttributes().add(modality);
		CustomAttribute pulseRate = new CustomAttribute();
		pulseRate.setLongName("pulseRate");
		pulseRate.setShortName("pulRe");
		pulseRate.setType("xs:float");
		getCustomAttributes().add(pulseRate);
		CustomAttribute energy = new CustomAttribute();
		energy.setLongName("energy");
		energy.setShortName("enery");
		energy.setType("xs:float");
		getCustomAttributes().add(energy);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
