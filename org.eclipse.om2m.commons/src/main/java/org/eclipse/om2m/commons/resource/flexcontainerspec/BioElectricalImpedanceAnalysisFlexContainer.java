/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : BioElectricalImpedanceAnalysis

This ModuleClass provides the analysis of human body tissue based on impedance measurement.

Created: 2018-06-29 17:19:51
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = BioElectricalImpedanceAnalysisFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = BioElectricalImpedanceAnalysisFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class BioElectricalImpedanceAnalysisFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "bioElectricalImpedanceAnalysis";
	public static final String SHORT_NAME = "bEIAs";
		
	public BioElectricalImpedanceAnalysisFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + BioElectricalImpedanceAnalysisFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute visceraFat = new CustomAttribute();
		visceraFat.setLongName("visceraFat");
		visceraFat.setShortName("visFt");
		visceraFat.setType("xs:float");
		getCustomAttributes().add(visceraFat);
		CustomAttribute kcal = new CustomAttribute();
		kcal.setLongName("kcal");
		kcal.setShortName("kcal");
		kcal.setType("xs:float");
		getCustomAttributes().add(kcal);
		CustomAttribute muscle = new CustomAttribute();
		muscle.setLongName("muscle");
		muscle.setShortName("musce");
		muscle.setType("xs:float");
		getCustomAttributes().add(muscle);
		CustomAttribute fat = new CustomAttribute();
		fat.setLongName("fat");
		fat.setShortName("fat");
		fat.setType("xs:float");
		getCustomAttributes().add(fat);
		CustomAttribute bone = new CustomAttribute();
		bone.setLongName("bone");
		bone.setShortName("bone");
		bone.setType("xs:float");
		getCustomAttributes().add(bone);
		CustomAttribute water = new CustomAttribute();
		water.setLongName("water");
		water.setShortName("water");
		water.setType("xs:float");
		getCustomAttributes().add(water);
		CustomAttribute resistance = new CustomAttribute();
		resistance.setLongName("resistance");
		resistance.setShortName("resie");
		resistance.setType("xs:boolean");
		getCustomAttributes().add(resistance);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
