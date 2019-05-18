/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : ClothesWasherJobModeOption

This ModuleClasses provides capabilities to control and monitor the washing job mode options of a washer.

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = ClothesWasherJobModeOptionFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ClothesWasherJobModeOptionFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class ClothesWasherJobModeOptionFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "clothesWasherJobModeOption";
	public static final String SHORT_NAME = "cWJMO";
		
	public ClothesWasherJobModeOptionFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + ClothesWasherJobModeOptionFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute steamTreat = new CustomAttribute();
		steamTreat.setLongName("steamTreat");
		steamTreat.setShortName("steTt");
		steamTreat.setType("xs:boolean");
		getCustomAttributes().add(steamTreat);
		CustomAttribute washTemp = new CustomAttribute();
		washTemp.setLongName("washTemp");
		washTemp.setShortName("wasTp");
		washTemp.setType("hd:enumGeneralTemperature");
		getCustomAttributes().add(washTemp);
		CustomAttribute preWash = new CustomAttribute();
		preWash.setLongName("preWash");
		preWash.setShortName("preWh");
		preWash.setType("xs:boolean");
		getCustomAttributes().add(preWash);
		CustomAttribute speedWash = new CustomAttribute();
		speedWash.setLongName("speedWash");
		speedWash.setShortName("speWh");
		speedWash.setType("xs:boolean");
		getCustomAttributes().add(speedWash);
		CustomAttribute soilLevel = new CustomAttribute();
		soilLevel.setLongName("soilLevel");
		soilLevel.setShortName("soiLl");
		soilLevel.setType("hd:enumGeneralLevel");
		getCustomAttributes().add(soilLevel);
		CustomAttribute coldWash = new CustomAttribute();
		coldWash.setLongName("coldWash");
		coldWash.setShortName("colWh");
		coldWash.setType("xs:boolean");
		getCustomAttributes().add(coldWash);
		CustomAttribute extraRinse = new CustomAttribute();
		extraRinse.setLongName("extraRinse");
		extraRinse.setShortName("extRe");
		extraRinse.setType("xs:boolean");
		getCustomAttributes().add(extraRinse);
		CustomAttribute spinSpeed = new CustomAttribute();
		spinSpeed.setLongName("spinSpeed");
		spinSpeed.setShortName("spiSd");
		spinSpeed.setType("hd:enumGeneralSpeed");
		getCustomAttributes().add(spinSpeed);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
