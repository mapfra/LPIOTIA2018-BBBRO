/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : Temperature

This ModuleClass provides capabilities to represent the current temperature and target temperature of devices such as an air conditioner, refrigerator, oven and etc.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = TemperatureFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = TemperatureFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class TemperatureFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "temperature";
	public static final String SHORT_NAME = "tempe";
		
	public TemperatureFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + TemperatureFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute currentTemperature = new CustomAttribute();
		currentTemperature.setLongName("currentTemperature");
		currentTemperature.setShortName("curT0");
		currentTemperature.setType("xs:float");
		getCustomAttributes().add(currentTemperature);
		CustomAttribute unit = new CustomAttribute();
		unit.setLongName("unit");
		unit.setShortName("unit");
		unit.setType("xs:string");
		getCustomAttributes().add(unit);
		CustomAttribute minValue = new CustomAttribute();
		minValue.setLongName("minValue");
		minValue.setShortName("minVe");
		minValue.setType("xs:float");
		getCustomAttributes().add(minValue);
		CustomAttribute targetTemperature = new CustomAttribute();
		targetTemperature.setLongName("targetTemperature");
		targetTemperature.setShortName("tarTe");
		targetTemperature.setType("xs:float");
		getCustomAttributes().add(targetTemperature);
		CustomAttribute maxValue = new CustomAttribute();
		maxValue.setLongName("maxValue");
		maxValue.setShortName("maxVe");
		maxValue.setType("xs:float");
		getCustomAttributes().add(maxValue);
		CustomAttribute stepValue = new CustomAttribute();
		stepValue.setLongName("stepValue");
		stepValue.setShortName("steVe");
		stepValue.setType("xs:float");
		getCustomAttributes().add(stepValue);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
