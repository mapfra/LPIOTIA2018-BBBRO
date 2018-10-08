/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceDehumidifier

A dehumidifier is a device that is used to monitor or control the state of a dehumidifying appliance.

Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceDehumidifierFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceDehumidifierFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceDehumidifierFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceDehumidifier";
	public static final String SHORT_NAME = "devDr";
	
	public DeviceDehumidifierFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceDehumidifierFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getBinarySwitch();
		getRelativeHumidity();
		getRunState();
		getDehumidifierJobMode();
		getDehumidifierOperationMode();
		getTimer();
		getPowerSave();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.relativeHumidity != null) {
			setRelativeHumidity(this.relativeHumidity);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.dehumidifierJobMode != null) {
			setDehumidifierJobMode(this.dehumidifierJobMode);
		}
		if (this.dehumidifierOperationMode != null) {
			setDehumidifierOperationMode(this.dehumidifierOperationMode);
		}
		if (this.timer != null) {
			setTimer(this.timer);
		}
		if (this.powerSave != null) {
			setPowerSave(this.powerSave);
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
	
	@XmlElement(name=RelativeHumidityFlexContainer.SHORT_NAME, required=true, type=RelativeHumidityFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private RelativeHumidityFlexContainer relativeHumidity;
		
	public void setRelativeHumidity(RelativeHumidityFlexContainer relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
		getFlexContainerOrContainerOrSubscription().add(relativeHumidity);
	}
	
	public RelativeHumidityFlexContainer getRelativeHumidity() {
		this.relativeHumidity = (RelativeHumidityFlexContainer) getResourceByName(RelativeHumidityFlexContainer.SHORT_NAME);
		return relativeHumidity;
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
	
	@XmlElement(name=DehumidifierJobModeFlexContainer.SHORT_NAME, required=true, type=DehumidifierJobModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private DehumidifierJobModeFlexContainer dehumidifierJobMode;
		
	public void setDehumidifierJobMode(DehumidifierJobModeFlexContainer dehumidifierJobMode) {
		this.dehumidifierJobMode = dehumidifierJobMode;
		getFlexContainerOrContainerOrSubscription().add(dehumidifierJobMode);
	}
	
	public DehumidifierJobModeFlexContainer getDehumidifierJobMode() {
		this.dehumidifierJobMode = (DehumidifierJobModeFlexContainer) getResourceByName(DehumidifierJobModeFlexContainer.SHORT_NAME);
		return dehumidifierJobMode;
	}
	
	@XmlElement(name=OperationModeFlexContainer.SHORT_NAME, required=true, type=OperationModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private OperationModeFlexContainer dehumidifierOperationMode;
		
	public void setDehumidifierOperationMode(OperationModeFlexContainer dehumidifierOperationMode) {
		this.dehumidifierOperationMode = dehumidifierOperationMode;
		getFlexContainerOrContainerOrSubscription().add(dehumidifierOperationMode);
	}
	
	public OperationModeFlexContainer getDehumidifierOperationMode() {
		this.dehumidifierOperationMode = (OperationModeFlexContainer) getResourceByName(OperationModeFlexContainer.SHORT_NAME);
		return dehumidifierOperationMode;
	}
	
	@XmlElement(name=TimerFlexContainer.SHORT_NAME, required=true, type=TimerFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TimerFlexContainer timer;
		
	public void setTimer(TimerFlexContainer timer) {
		this.timer = timer;
		getFlexContainerOrContainerOrSubscription().add(timer);
	}
	
	public TimerFlexContainer getTimer() {
		this.timer = (TimerFlexContainer) getResourceByName(TimerFlexContainer.SHORT_NAME);
		return timer;
	}
	
	@XmlElement(name=PowerSaveFlexContainer.SHORT_NAME, required=true, type=PowerSaveFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private PowerSaveFlexContainer powerSave;
		
	public void setPowerSave(PowerSaveFlexContainer powerSave) {
		this.powerSave = powerSave;
		getFlexContainerOrContainerOrSubscription().add(powerSave);
	}
	
	public PowerSaveFlexContainer getPowerSave() {
		this.powerSave = (PowerSaveFlexContainer) getResourceByName(PowerSaveFlexContainer.SHORT_NAME);
		return powerSave;
	}
	
}
