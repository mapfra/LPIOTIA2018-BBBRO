/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : EnergyConsumption

This ModuleClass describes the measured energy consumed by the device since power up. One particular use case for the energyConsumption ModuleClass is a smart meter

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = EnergyConsumptionFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = EnergyConsumptionFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class EnergyConsumptionFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "energyConsumption";
	public static final String SHORT_NAME = "eneCn";
		
	public EnergyConsumptionFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + EnergyConsumptionFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute roundingEnergyConsumption = new CustomAttribute();
		roundingEnergyConsumption.setLongName("roundingEnergyConsumption");
		roundingEnergyConsumption.setShortName("roECn");
		roundingEnergyConsumption.setType("xs:integer");
		getCustomAttributes().add(roundingEnergyConsumption);
		CustomAttribute current = new CustomAttribute();
		current.setLongName("current");
		current.setShortName("currt");
		current.setType("xs:float");
		getCustomAttributes().add(current);
		CustomAttribute absoluteEnergyConsumption = new CustomAttribute();
		absoluteEnergyConsumption.setLongName("absoluteEnergyConsumption");
		absoluteEnergyConsumption.setShortName("abECn");
		absoluteEnergyConsumption.setType("xs:float");
		getCustomAttributes().add(absoluteEnergyConsumption);
		CustomAttribute multiplyingFactors = new CustomAttribute();
		multiplyingFactors.setLongName("multiplyingFactors");
		multiplyingFactors.setShortName("mulFs");
		multiplyingFactors.setType("xs:integer");
		getCustomAttributes().add(multiplyingFactors);
		CustomAttribute power = new CustomAttribute();
		power.setLongName("power");
		power.setShortName("power");
		power.setType("xs:float");
		getCustomAttributes().add(power);
		CustomAttribute measuringScope = new CustomAttribute();
		measuringScope.setLongName("measuringScope");
		measuringScope.setShortName("meaSe");
		measuringScope.setType("xs:string");
		getCustomAttributes().add(measuringScope);
		CustomAttribute significantDigits = new CustomAttribute();
		significantDigits.setLongName("significantDigits");
		significantDigits.setShortName("sigDs");
		significantDigits.setType("xs:integer");
		getCustomAttributes().add(significantDigits);
		CustomAttribute voltage = new CustomAttribute();
		voltage.setLongName("voltage");
		voltage.setShortName("volte");
		voltage.setType("xs:float");
		getCustomAttributes().add(voltage);
		CustomAttribute frequency = new CustomAttribute();
		frequency.setLongName("frequency");
		frequency.setShortName("freqy");
		frequency.setType("xs:float");
		getCustomAttributes().add(frequency);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
