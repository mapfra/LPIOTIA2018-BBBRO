/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : ImpactSensor

This ModuleClass describes the capabilities on an impact sensor. The impact is a high force or shock over a short time period and the impactSensor detects this.

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = ImpactSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ImpactSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class ImpactSensorFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "impactSensor";
	public static final String SHORT_NAME = "impSr";
		
	public ImpactSensorFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + ImpactSensorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute impactLevel = new CustomAttribute();
		impactLevel.setLongName("impactLevel");
		impactLevel.setShortName("impLl");
		impactLevel.setType("xs:float");
		getCustomAttributes().add(impactLevel);
		CustomAttribute impactDirectionVertical = new CustomAttribute();
		impactDirectionVertical.setLongName("impactDirectionVertical");
		impactDirectionVertical.setShortName("imDVl");
		impactDirectionVertical.setType("xs:float");
		getCustomAttributes().add(impactDirectionVertical);
		CustomAttribute impactDirectionHorizontal = new CustomAttribute();
		impactDirectionHorizontal.setLongName("impactDirectionHorizontal");
		impactDirectionHorizontal.setShortName("imDHl");
		impactDirectionHorizontal.setType("xs:float");
		getCustomAttributes().add(impactDirectionHorizontal);
		CustomAttribute impactStatus = new CustomAttribute();
		impactStatus.setLongName("impactStatus");
		impactStatus.setShortName("impSs");
		impactStatus.setType("xs:boolean");
		getCustomAttributes().add(impactStatus);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
