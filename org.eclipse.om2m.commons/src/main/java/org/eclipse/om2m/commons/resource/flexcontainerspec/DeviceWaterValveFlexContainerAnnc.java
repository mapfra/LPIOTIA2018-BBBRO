/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceWaterValveAnnc

A WaterValve is a device that controls liquid flux.

Created: 2017-09-28 17:26:40
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;


@XmlRootElement(name = DeviceWaterValveFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceWaterValveFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceWaterValveFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceWaterValveAnnc";
	public static final String SHORT_NAME = "deWVeAnnc";
	
	public DeviceWaterValveFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceWaterValveFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getWaterLevel();
		getWaterLevelAnnc();
	}
	
	public void finalizeDeserialization() {
		if (this.waterLevel != null) {
			setWaterLevel(this.waterLevel);
		}
		if (this.waterLevelAnnc != null) {
			setWaterLevelAnnc(this.waterLevelAnnc);
			}
		
	}
	
	@XmlElement(name="watLl", required=true, type=LiquidLevelFlexContainerAnnc.class)
	private LiquidLevelFlexContainer waterLevel;
	
	
	public void setWaterLevel(LiquidLevelFlexContainer waterLevel) {
		this.waterLevel = waterLevel;
		getFlexContainerOrContainerOrSubscription().add(waterLevel);
	}
	
	public LiquidLevelFlexContainer getWaterLevel() {
		this.waterLevel = (LiquidLevelFlexContainer) getResourceByName(LiquidLevelFlexContainer.SHORT_NAME);
		return waterLevel;
	}
	
	@XmlElement(name="watLlAnnc", required=true, type=LiquidLevelFlexContainerAnnc.class)
	private LiquidLevelFlexContainerAnnc waterLevelAnnc;
	
	
	public void setWaterLevelAnnc(LiquidLevelFlexContainerAnnc waterLevelAnnc) {
		this.waterLevelAnnc = waterLevelAnnc;
		getFlexContainerOrContainerOrSubscription().add(waterLevelAnnc);
	}
	
	public LiquidLevelFlexContainerAnnc getWaterLevelAnnc() {
		this.waterLevelAnnc = (LiquidLevelFlexContainerAnnc) getResourceByName(LiquidLevelFlexContainerAnnc.SHORT_NAME);
		return waterLevelAnnc;
	}
	
}