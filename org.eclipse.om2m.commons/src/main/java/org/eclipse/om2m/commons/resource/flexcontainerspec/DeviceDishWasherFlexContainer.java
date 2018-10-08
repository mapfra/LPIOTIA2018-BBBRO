/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceDishWasher

A dish washer is a home appliance used to wash dishes. This information model provides capabilities to interact with specific functions and resources of a dish washer.

Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceDishWasherFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceDishWasherFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceDishWasherFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceDishWasher";
	public static final String SHORT_NAME = "deDWr";
	
	public DeviceDishWasherFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceDishWasherFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getBinarySwitch();
		getRunState();
		getDishWasherJobMode();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.dishWasherJobMode != null) {
			setDishWasherJobMode(this.dishWasherJobMode);
		}
	}

	@XmlElement(name=BinarySwitchFlexContainer.SHORT_NAME, required=true, type=BinarySwitchFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BinarySwitchFlexContainer binarySwitch;
		
	public void setBinarySwitch(BinarySwitchFlexContainer binarySwitch) {
		this.binarySwitch = binarySwitch;
		getFlexContainerOrContainerOrSubscription().add(binarySwitch);
	}
	
	public BinarySwitchFlexContainer getBinarySwitch() {
		this.binarySwitch = (BinarySwitchFlexContainer) getResourceByName(BinarySwitchFlexContainer.SHORT_NAME);
		return binarySwitch;
	}
	
	@XmlElement(name=RunStateFlexContainer.SHORT_NAME, required=true, type=RunStateFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private RunStateFlexContainer runState;
		
	public void setRunState(RunStateFlexContainer runState) {
		this.runState = runState;
		getFlexContainerOrContainerOrSubscription().add(runState);
	}
	
	public RunStateFlexContainer getRunState() {
		this.runState = (RunStateFlexContainer) getResourceByName(RunStateFlexContainer.SHORT_NAME);
		return runState;
	}
	
	@XmlElement(name=DishWasherJobModeFlexContainer.SHORT_NAME, required=true, type=DishWasherJobModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private DishWasherJobModeFlexContainer dishWasherJobMode;
		
	public void setDishWasherJobMode(DishWasherJobModeFlexContainer dishWasherJobMode) {
		this.dishWasherJobMode = dishWasherJobMode;
		getFlexContainerOrContainerOrSubscription().add(dishWasherJobMode);
	}
	
	public DishWasherJobModeFlexContainer getDishWasherJobMode() {
		this.dishWasherJobMode = (DishWasherJobModeFlexContainer) getResourceByName(DishWasherJobModeFlexContainer.SHORT_NAME);
		return dishWasherJobMode;
	}
	
}
