/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : Barometer

This ModuleClass provides the capabilities to measure the atomospheric pressure and indicate the detection of abnormal pressures and raises an alarm if the triggering criterion is met.

Created: 2018-07-04 10:25:08
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = BarometerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = BarometerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class BarometerFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "barometer";
	public static final String SHORT_NAME = "baror";
		
	public BarometerFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + BarometerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute minPressureThreshhold = new CustomAttribute();
		minPressureThreshhold.setLongName("minPressureThreshhold");
		minPressureThreshhold.setShortName("miPTd");
		minPressureThreshhold.setType("xs:integer");
		getCustomAttributes().add(minPressureThreshhold);
		CustomAttribute unit = new CustomAttribute();
		unit.setLongName("unit");
		unit.setShortName("unit");
		unit.setType("xs:string");
		getCustomAttributes().add(unit);
		CustomAttribute maxPressureThreshhold = new CustomAttribute();
		maxPressureThreshhold.setLongName("maxPressureThreshhold");
		maxPressureThreshhold.setShortName("maPTd");
		maxPressureThreshhold.setType("xs:integer");
		getCustomAttributes().add(maxPressureThreshhold);
		CustomAttribute atmosphericPressure = new CustomAttribute();
		atmosphericPressure.setLongName("atmosphericPressure");
		atmosphericPressure.setShortName("atmPe");
		atmosphericPressure.setType("xs:float");
		getCustomAttributes().add(atmosphericPressure);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
