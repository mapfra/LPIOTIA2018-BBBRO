/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : Glucometer

This ModuleClass provides the capability to report the measurement of glucose characteristics.

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = GlucometerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = GlucometerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class GlucometerFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "glucometer";
	public static final String SHORT_NAME = "glucr";
		
	public GlucometerFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + GlucometerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute contextLocation = new CustomAttribute();
		contextLocation.setLongName("contextLocation");
		contextLocation.setShortName("conLn");
		contextLocation.setType("xs:string");
		getCustomAttributes().add(contextLocation);
		CustomAttribute contextCarbohydratesSource = new CustomAttribute();
		contextCarbohydratesSource.setLongName("contextCarbohydratesSource");
		contextCarbohydratesSource.setShortName("coCSe");
		contextCarbohydratesSource.setType("xs:string");
		getCustomAttributes().add(contextCarbohydratesSource);
		CustomAttribute contextExercise = new CustomAttribute();
		contextExercise.setLongName("contextExercise");
		contextExercise.setShortName("conEe");
		contextExercise.setType("xs:float");
		getCustomAttributes().add(contextExercise);
		CustomAttribute contextTester = new CustomAttribute();
		contextTester.setLongName("contextTester");
		contextTester.setShortName("conTr");
		contextTester.setType("xs:string");
		getCustomAttributes().add(contextTester);
		CustomAttribute contextMedication = new CustomAttribute();
		contextMedication.setLongName("contextMedication");
		contextMedication.setShortName("conMn");
		contextMedication.setType("xs:float");
		getCustomAttributes().add(contextMedication);
		CustomAttribute contextMeal = new CustomAttribute();
		contextMeal.setLongName("contextMeal");
		contextMeal.setShortName("conMl");
		contextMeal.setType("xs:string");
		getCustomAttributes().add(contextMeal);
		CustomAttribute concentration = new CustomAttribute();
		concentration.setLongName("concentration");
		concentration.setShortName("concn");
		concentration.setType("xs:float");
		getCustomAttributes().add(concentration);
		CustomAttribute contextCarbohydratesAmount = new CustomAttribute();
		contextCarbohydratesAmount.setLongName("contextCarbohydratesAmount");
		contextCarbohydratesAmount.setShortName("coCAt");
		contextCarbohydratesAmount.setType("xs:float");
		getCustomAttributes().add(contextCarbohydratesAmount);
		CustomAttribute contextHealth = new CustomAttribute();
		contextHealth.setLongName("contextHealth");
		contextHealth.setShortName("conHh");
		contextHealth.setType("xs:string");
		getCustomAttributes().add(contextHealth);
		CustomAttribute hba1c = new CustomAttribute();
		hba1c.setLongName("hba1c");
		hba1c.setShortName("hba1c");
		hba1c.setType("xs:float");
		getCustomAttributes().add(hba1c);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
