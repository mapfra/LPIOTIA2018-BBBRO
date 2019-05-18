/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : Sphygmomanometer

This ModuleClass provides the capability to report the measurement of blood pressure characteristics.

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = SphygmomanometerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = SphygmomanometerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class SphygmomanometerFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "sphygmomanometer";
	public static final String SHORT_NAME = "sphyr";
		
	public SphygmomanometerFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + SphygmomanometerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute diastolicPressure = new CustomAttribute();
		diastolicPressure.setLongName("diastolicPressure");
		diastolicPressure.setShortName("diaPe");
		diastolicPressure.setType("xs:float");
		getCustomAttributes().add(diastolicPressure);
		CustomAttribute meanPressure = new CustomAttribute();
		meanPressure.setLongName("meanPressure");
		meanPressure.setShortName("meaPe");
		meanPressure.setType("xs:float");
		getCustomAttributes().add(meanPressure);
		CustomAttribute systolicPressure = new CustomAttribute();
		systolicPressure.setLongName("systolicPressure");
		systolicPressure.setShortName("sysPe");
		systolicPressure.setType("xs:float");
		getCustomAttributes().add(systolicPressure);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
