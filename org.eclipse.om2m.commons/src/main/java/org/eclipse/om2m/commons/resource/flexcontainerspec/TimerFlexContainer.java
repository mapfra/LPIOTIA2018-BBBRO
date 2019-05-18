/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : Timer

This ModuleClass provides capabilities to monitor and control the times when the appliance executes its operations, that means when it starts, when it ends etc.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = TimerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = TimerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class TimerFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "timer";
	public static final String SHORT_NAME = "timer";
		
	public TimerFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + TimerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute absoluteStartTime = new CustomAttribute();
		absoluteStartTime.setLongName("absoluteStartTime");
		absoluteStartTime.setShortName("abSTe");
		absoluteStartTime.setType("xs:datetime");
		getCustomAttributes().add(absoluteStartTime);
		CustomAttribute targetTimeToStop = new CustomAttribute();
		targetTimeToStop.setLongName("targetTimeToStop");
		targetTimeToStop.setShortName("tTTSp");
		targetTimeToStop.setType("xs:integer");
		getCustomAttributes().add(targetTimeToStop);
		CustomAttribute estimatedTimeToEnd = new CustomAttribute();
		estimatedTimeToEnd.setLongName("estimatedTimeToEnd");
		estimatedTimeToEnd.setShortName("eTTEd");
		estimatedTimeToEnd.setType("xs:integer");
		getCustomAttributes().add(estimatedTimeToEnd);
		CustomAttribute absoluteStopTime = new CustomAttribute();
		absoluteStopTime.setLongName("absoluteStopTime");
		absoluteStopTime.setShortName("abST0");
		absoluteStopTime.setType("xs:datetime");
		getCustomAttributes().add(absoluteStopTime);
		CustomAttribute targetTimeToStart = new CustomAttribute();
		targetTimeToStart.setLongName("targetTimeToStart");
		targetTimeToStart.setShortName("tTTSt");
		targetTimeToStart.setType("xs:integer");
		getCustomAttributes().add(targetTimeToStart);
		CustomAttribute referenceTimer = new CustomAttribute();
		referenceTimer.setLongName("referenceTimer");
		referenceTimer.setShortName("refTr");
		referenceTimer.setType("xs:integer");
		getCustomAttributes().add(referenceTimer);
		CustomAttribute targetDuration = new CustomAttribute();
		targetDuration.setLongName("targetDuration");
		targetDuration.setShortName("tarDn");
		targetDuration.setType("xs:integer");
		getCustomAttributes().add(targetDuration);
		CustomAttribute runningTime = new CustomAttribute();
		runningTime.setLongName("runningTime");
		runningTime.setShortName("runTe");
		runningTime.setType("xs:integer");
		getCustomAttributes().add(runningTime);
	}


	@XmlElement(name=ActivateClockTimerFlexContainer.SHORT_NAME, required=true, type=ActivateClockTimerFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ActivateClockTimerFlexContainer activateClockTimer;
	
	public ActivateClockTimerFlexContainer getActivateClockTimer() {
		this.activateClockTimer = (ActivateClockTimerFlexContainer) getResourceByName(ActivateClockTimerFlexContainer.SHORT_NAME);
		return activateClockTimer;
	}
	
	public void setActivateClockTimer(ActivateClockTimerFlexContainer newAction) {
		this.activateClockTimer = newAction;
		getFlexContainerOrContainerOrSubscription().add(activateClockTimer);
	}

	@XmlElement(name=DeactivateClockTimerFlexContainer.SHORT_NAME, required=true, type=DeactivateClockTimerFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private DeactivateClockTimerFlexContainer deactivateClockTimer;
	
	public DeactivateClockTimerFlexContainer getDeactivateClockTimer() {
		this.deactivateClockTimer = (DeactivateClockTimerFlexContainer) getResourceByName(DeactivateClockTimerFlexContainer.SHORT_NAME);
		return deactivateClockTimer;
	}
	
	public void setDeactivateClockTimer(DeactivateClockTimerFlexContainer newAction) {
		this.deactivateClockTimer = newAction;
		getFlexContainerOrContainerOrSubscription().add(deactivateClockTimer);
	}
		
	public void finalizeSerialization() {
		getActivateClockTimer();
		getDeactivateClockTimer();
	}
	
	public void finalizeDeserialization() {
		if (this.activateClockTimer != null) {
			setActivateClockTimer(this.activateClockTimer);
		}
		if (this.deactivateClockTimer != null) {
			setDeactivateClockTimer(this.deactivateClockTimer);
		}
	}
	
}
