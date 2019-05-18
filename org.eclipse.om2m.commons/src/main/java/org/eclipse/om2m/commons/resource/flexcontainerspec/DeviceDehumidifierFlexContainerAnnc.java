/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceDehumidifierAnnc

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
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DeviceDehumidifierFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceDehumidifierFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceDehumidifierFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceDehumidifierAnnc";
	public static final String SHORT_NAME = "devDrAnnc";
	
	public DeviceDehumidifierFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceDehumidifierFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getRelativeHumidity();
		getRelativeHumidityAnnc();
		getRunState();
		getRunStateAnnc();
		getDehumidifierJobMode();
		getDehumidifierJobModeAnnc();
		getTimer();
		getTimerAnnc();
		getPowerSave();
		getPowerSaveAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.binarySwitchAnnc != null) {
			setBinarySwitchAnnc(this.binarySwitchAnnc);
		}
		if (this.relativeHumidity != null) {
			setRelativeHumidity(this.relativeHumidity);
		}
		if (this.relativeHumidityAnnc != null) {
			setRelativeHumidityAnnc(this.relativeHumidityAnnc);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.runStateAnnc != null) {
			setRunStateAnnc(this.runStateAnnc);
		}
		if (this.dehumidifierJobMode != null) {
			setDehumidifierJobMode(this.dehumidifierJobMode);
		}
		if (this.dehumidifierJobModeAnnc != null) {
			setDehumidifierJobModeAnnc(this.dehumidifierJobModeAnnc);
		}
		if (this.timer != null) {
			setTimer(this.timer);
		}
		if (this.timerAnnc != null) {
			setTimerAnnc(this.timerAnnc);
		}
		if (this.powerSave != null) {
			setPowerSave(this.powerSave);
		}
		if (this.powerSaveAnnc != null) {
			setPowerSaveAnnc(this.powerSaveAnnc);
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
	
	@XmlElement(name=BinarySwitchFlexContainerAnnc.SHORT_NAME, required=true, type=BinarySwitchFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BinarySwitchFlexContainerAnnc binarySwitchAnnc;
		
	public void setBinarySwitchAnnc(BinarySwitchFlexContainerAnnc binarySwitchAnnc) {
		this.binarySwitchAnnc = binarySwitchAnnc;
		getFlexContainerOrContainerOrSubscription().add(binarySwitchAnnc);
	}
	
	public BinarySwitchFlexContainerAnnc getBinarySwitchAnnc() {
		this.binarySwitchAnnc = (BinarySwitchFlexContainerAnnc) getResourceByName(BinarySwitchFlexContainerAnnc.SHORT_NAME);
		return binarySwitchAnnc;
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
	
	@XmlElement(name=RelativeHumidityFlexContainerAnnc.SHORT_NAME, required=true, type=RelativeHumidityFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private RelativeHumidityFlexContainerAnnc relativeHumidityAnnc;
		
	public void setRelativeHumidityAnnc(RelativeHumidityFlexContainerAnnc relativeHumidityAnnc) {
		this.relativeHumidityAnnc = relativeHumidityAnnc;
		getFlexContainerOrContainerOrSubscription().add(relativeHumidityAnnc);
	}
	
	public RelativeHumidityFlexContainerAnnc getRelativeHumidityAnnc() {
		this.relativeHumidityAnnc = (RelativeHumidityFlexContainerAnnc) getResourceByName(RelativeHumidityFlexContainerAnnc.SHORT_NAME);
		return relativeHumidityAnnc;
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
	
	@XmlElement(name=RunStateFlexContainerAnnc.SHORT_NAME, required=true, type=RunStateFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private RunStateFlexContainerAnnc runStateAnnc;
		
	public void setRunStateAnnc(RunStateFlexContainerAnnc runStateAnnc) {
		this.runStateAnnc = runStateAnnc;
		getFlexContainerOrContainerOrSubscription().add(runStateAnnc);
	}
	
	public RunStateFlexContainerAnnc getRunStateAnnc() {
		this.runStateAnnc = (RunStateFlexContainerAnnc) getResourceByName(RunStateFlexContainerAnnc.SHORT_NAME);
		return runStateAnnc;
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
	
	@XmlElement(name=DehumidifierJobModeFlexContainerAnnc.SHORT_NAME, required=true, type=DehumidifierJobModeFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private DehumidifierJobModeFlexContainerAnnc dehumidifierJobModeAnnc;
		
	public void setDehumidifierJobModeAnnc(DehumidifierJobModeFlexContainerAnnc dehumidifierJobModeAnnc) {
		this.dehumidifierJobModeAnnc = dehumidifierJobModeAnnc;
		getFlexContainerOrContainerOrSubscription().add(dehumidifierJobModeAnnc);
	}
	
	public DehumidifierJobModeFlexContainerAnnc getDehumidifierJobModeAnnc() {
		this.dehumidifierJobModeAnnc = (DehumidifierJobModeFlexContainerAnnc) getResourceByName(DehumidifierJobModeFlexContainerAnnc.SHORT_NAME);
		return dehumidifierJobModeAnnc;
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
	
	@XmlElement(name=TimerFlexContainerAnnc.SHORT_NAME, required=true, type=TimerFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TimerFlexContainerAnnc timerAnnc;
		
	public void setTimerAnnc(TimerFlexContainerAnnc timerAnnc) {
		this.timerAnnc = timerAnnc;
		getFlexContainerOrContainerOrSubscription().add(timerAnnc);
	}
	
	public TimerFlexContainerAnnc getTimerAnnc() {
		this.timerAnnc = (TimerFlexContainerAnnc) getResourceByName(TimerFlexContainerAnnc.SHORT_NAME);
		return timerAnnc;
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
	
	@XmlElement(name=PowerSaveFlexContainerAnnc.SHORT_NAME, required=true, type=PowerSaveFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private PowerSaveFlexContainerAnnc powerSaveAnnc;
		
	public void setPowerSaveAnnc(PowerSaveFlexContainerAnnc powerSaveAnnc) {
		this.powerSaveAnnc = powerSaveAnnc;
		getFlexContainerOrContainerOrSubscription().add(powerSaveAnnc);
	}
	
	public PowerSaveFlexContainerAnnc getPowerSaveAnnc() {
		this.powerSaveAnnc = (PowerSaveFlexContainerAnnc) getResourceByName(PowerSaveFlexContainerAnnc.SHORT_NAME);
		return powerSaveAnnc;
	}
	
}
