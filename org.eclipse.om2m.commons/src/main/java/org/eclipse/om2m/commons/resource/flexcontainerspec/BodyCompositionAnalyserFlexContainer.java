/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : BodyCompositionAnalyser

This ModuleClass provides the capability to report the measurement of a body composition analyser.

Created: 2018-06-29 17:19:51
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = BodyCompositionAnalyserFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = BodyCompositionAnalyserFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class BodyCompositionAnalyserFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "bodyCompositionAnalyser";
	public static final String SHORT_NAME = "boCAr";
		
	public BodyCompositionAnalyserFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + BodyCompositionAnalyserFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute fatFreeMass = new CustomAttribute();
		fatFreeMass.setLongName("fatFreeMass");
		fatFreeMass.setShortName("faFMs");
		fatFreeMass.setType("xs:float");
		getCustomAttributes().add(fatFreeMass);
		CustomAttribute softLeanMass = new CustomAttribute();
		softLeanMass.setLongName("softLeanMass");
		softLeanMass.setShortName("soLMs");
		softLeanMass.setType("xs:float");
		getCustomAttributes().add(softLeanMass);
		CustomAttribute impedance = new CustomAttribute();
		impedance.setLongName("impedance");
		impedance.setShortName("impee");
		impedance.setType("xs:float");
		getCustomAttributes().add(impedance);
		CustomAttribute bodyLength = new CustomAttribute();
		bodyLength.setLongName("bodyLength");
		bodyLength.setShortName("bodLh");
		bodyLength.setType("xs:float");
		getCustomAttributes().add(bodyLength);
		CustomAttribute basalMetabolism = new CustomAttribute();
		basalMetabolism.setLongName("basalMetabolism");
		basalMetabolism.setShortName("basMm");
		basalMetabolism.setType("xs:float");
		getCustomAttributes().add(basalMetabolism);
		CustomAttribute muscleMass = new CustomAttribute();
		muscleMass.setLongName("muscleMass");
		muscleMass.setShortName("musMs");
		muscleMass.setType("xs:float");
		getCustomAttributes().add(muscleMass);
		CustomAttribute bmi = new CustomAttribute();
		bmi.setLongName("bmi");
		bmi.setShortName("bmi");
		bmi.setType("xs:float");
		getCustomAttributes().add(bmi);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
