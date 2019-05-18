/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : Battery

This ModuleClass provides capabilities to indicate the detection of low battery and gives an alarm if triggering criterion is met. The level data point in the module represents the current battery charge level.

Created: 2018-06-29 17:19:51
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = BatteryFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = BatteryFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class BatteryFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "battery";
	public static final String SHORT_NAME = "bat";
		
	public BatteryFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + BatteryFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute discharging = new CustomAttribute();
		discharging.setLongName("discharging");
		discharging.setShortName("discg");
		discharging.setType("xs:boolean");
		getCustomAttributes().add(discharging);
		CustomAttribute lowBattery = new CustomAttribute();
		lowBattery.setLongName("lowBattery");
		lowBattery.setShortName("lowBy");
		lowBattery.setType("xs:boolean");
		getCustomAttributes().add(lowBattery);
		CustomAttribute material = new CustomAttribute();
		material.setLongName("material");
		material.setShortName("matel");
		material.setType("xs:string");
		getCustomAttributes().add(material);
		CustomAttribute level = new CustomAttribute();
		level.setLongName("level");
		level.setShortName("lvl");
		level.setType("xs:integer");
		getCustomAttributes().add(level);
		CustomAttribute charging = new CustomAttribute();
		charging.setLongName("charging");
		charging.setShortName("charg");
		charging.setType("xs:boolean");
		getCustomAttributes().add(charging);
		CustomAttribute electricEnergy = new CustomAttribute();
		electricEnergy.setLongName("electricEnergy");
		electricEnergy.setShortName("eleEy");
		electricEnergy.setType("xs:integer");
		getCustomAttributes().add(electricEnergy);
		CustomAttribute batteryThreshold = new CustomAttribute();
		batteryThreshold.setLongName("batteryThreshold");
		batteryThreshold.setShortName("batTd");
		batteryThreshold.setType("xs:integer");
		getCustomAttributes().add(batteryThreshold);
		CustomAttribute capacity = new CustomAttribute();
		capacity.setLongName("capacity");
		capacity.setShortName("capay");
		capacity.setType("xs:integer");
		getCustomAttributes().add(capacity);
		CustomAttribute voltage = new CustomAttribute();
		voltage.setLongName("voltage");
		voltage.setShortName("volte");
		voltage.setType("xs:integer");
		getCustomAttributes().add(voltage);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
