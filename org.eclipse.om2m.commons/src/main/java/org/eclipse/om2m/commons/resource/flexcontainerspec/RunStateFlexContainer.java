/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : RunState

This ModuleClasses provides capabilities to control and the monitor machine state of appliances.

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = RunStateFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = RunStateFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class RunStateFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "runState";
	public static final String SHORT_NAME = "runSe";
		
	public RunStateFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + RunStateFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute machineStates = new CustomAttribute();
		machineStates.setLongName("machineStates");
		machineStates.setShortName("macSs");
		machineStates.setType("[hd:enumMachineState]");
		getCustomAttributes().add(machineStates);
		CustomAttribute jobStates = new CustomAttribute();
		jobStates.setLongName("jobStates");
		jobStates.setShortName("jobSs");
		jobStates.setType("[hd:enumJobState]");
		getCustomAttributes().add(jobStates);
		CustomAttribute currentMachineState = new CustomAttribute();
		currentMachineState.setLongName("currentMachineState");
		currentMachineState.setShortName("cuMSe");
		currentMachineState.setType("hd:enumMachineState");
		getCustomAttributes().add(currentMachineState);
		CustomAttribute currentJobState = new CustomAttribute();
		currentJobState.setLongName("currentJobState");
		currentJobState.setShortName("cuJSe");
		currentJobState.setType("hd:enumJobState");
		getCustomAttributes().add(currentJobState);
		CustomAttribute progressPercentage = new CustomAttribute();
		progressPercentage.setLongName("progressPercentage");
		progressPercentage.setShortName("proPe");
		progressPercentage.setType("xs:float");
		getCustomAttributes().add(progressPercentage);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
