/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceAirPurifierAnnc

An airPurifieris a home appliance is used to prevent dust and other particles from air by filtering, washing or electrostatic precipitation. This airPurifier information model provides capabilities to control and monitor airPurifier specific functions and resources.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DeviceAirPurifierFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceAirPurifierFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceAirPurifierFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceAirPurifierAnnc";
	public static final String SHORT_NAME = "deAPrAnnc";
	
	public DeviceAirPurifierFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceAirPurifierFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getRunState();
		getRunStateAnnc();
		getAirPurifierJobMode();
		getAirPurifierJobModeAnnc();
		getTimer();
		getTimerAnnc();
		getPowerSave();
		getPowerSaveAnnc();
		getAirQualitySensor();
		getAirQualitySensorAnnc();
		getFilterInfo();
		getFilterInfoAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.binarySwitchAnnc != null) {
			setBinarySwitchAnnc(this.binarySwitchAnnc);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.runStateAnnc != null) {
			setRunStateAnnc(this.runStateAnnc);
		}
		if (this.airPurifierJobMode != null) {
			setAirPurifierJobMode(this.airPurifierJobMode);
		}
		if (this.airPurifierJobModeAnnc != null) {
			setAirPurifierJobModeAnnc(this.airPurifierJobModeAnnc);
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
		if (this.airQualitySensor != null) {
			setAirQualitySensor(this.airQualitySensor);
		}
		if (this.airQualitySensorAnnc != null) {
			setAirQualitySensorAnnc(this.airQualitySensorAnnc);
		}
		if (this.filterInfo != null) {
			setFilterInfo(this.filterInfo);
		}
		if (this.filterInfoAnnc != null) {
			setFilterInfoAnnc(this.filterInfoAnnc);
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
	
	@XmlElement(name=AirPurifierJobModeFlexContainer.SHORT_NAME, required=true, type=AirPurifierJobModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AirPurifierJobModeFlexContainer airPurifierJobMode;
		
	public void setAirPurifierJobMode(AirPurifierJobModeFlexContainer airPurifierJobMode) {
		this.airPurifierJobMode = airPurifierJobMode;
		getFlexContainerOrContainerOrSubscription().add(airPurifierJobMode);
	}
	
	public AirPurifierJobModeFlexContainer getAirPurifierJobMode() {
		this.airPurifierJobMode = (AirPurifierJobModeFlexContainer) getResourceByName(AirPurifierJobModeFlexContainer.SHORT_NAME);
		return airPurifierJobMode;
	}
	
	@XmlElement(name=AirPurifierJobModeFlexContainerAnnc.SHORT_NAME, required=true, type=AirPurifierJobModeFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AirPurifierJobModeFlexContainerAnnc airPurifierJobModeAnnc;
		
	public void setAirPurifierJobModeAnnc(AirPurifierJobModeFlexContainerAnnc airPurifierJobModeAnnc) {
		this.airPurifierJobModeAnnc = airPurifierJobModeAnnc;
		getFlexContainerOrContainerOrSubscription().add(airPurifierJobModeAnnc);
	}
	
	public AirPurifierJobModeFlexContainerAnnc getAirPurifierJobModeAnnc() {
		this.airPurifierJobModeAnnc = (AirPurifierJobModeFlexContainerAnnc) getResourceByName(AirPurifierJobModeFlexContainerAnnc.SHORT_NAME);
		return airPurifierJobModeAnnc;
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
	
	@XmlElement(name=AirQualitySensorFlexContainer.SHORT_NAME, required=true, type=AirQualitySensorFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AirQualitySensorFlexContainer airQualitySensor;
		
	public void setAirQualitySensor(AirQualitySensorFlexContainer airQualitySensor) {
		this.airQualitySensor = airQualitySensor;
		getFlexContainerOrContainerOrSubscription().add(airQualitySensor);
	}
	
	public AirQualitySensorFlexContainer getAirQualitySensor() {
		this.airQualitySensor = (AirQualitySensorFlexContainer) getResourceByName(AirQualitySensorFlexContainer.SHORT_NAME);
		return airQualitySensor;
	}
	
	@XmlElement(name=AirQualitySensorFlexContainerAnnc.SHORT_NAME, required=true, type=AirQualitySensorFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AirQualitySensorFlexContainerAnnc airQualitySensorAnnc;
		
	public void setAirQualitySensorAnnc(AirQualitySensorFlexContainerAnnc airQualitySensorAnnc) {
		this.airQualitySensorAnnc = airQualitySensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(airQualitySensorAnnc);
	}
	
	public AirQualitySensorFlexContainerAnnc getAirQualitySensorAnnc() {
		this.airQualitySensorAnnc = (AirQualitySensorFlexContainerAnnc) getResourceByName(AirQualitySensorFlexContainerAnnc.SHORT_NAME);
		return airQualitySensorAnnc;
	}
	
	@XmlElement(name=FilterInfoFlexContainer.SHORT_NAME, required=true, type=FilterInfoFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private FilterInfoFlexContainer filterInfo;
		
	public void setFilterInfo(FilterInfoFlexContainer filterInfo) {
		this.filterInfo = filterInfo;
		getFlexContainerOrContainerOrSubscription().add(filterInfo);
	}
	
	public FilterInfoFlexContainer getFilterInfo() {
		this.filterInfo = (FilterInfoFlexContainer) getResourceByName(FilterInfoFlexContainer.SHORT_NAME);
		return filterInfo;
	}
	
	@XmlElement(name=FilterInfoFlexContainerAnnc.SHORT_NAME, required=true, type=FilterInfoFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private FilterInfoFlexContainerAnnc filterInfoAnnc;
		
	public void setFilterInfoAnnc(FilterInfoFlexContainerAnnc filterInfoAnnc) {
		this.filterInfoAnnc = filterInfoAnnc;
		getFlexContainerOrContainerOrSubscription().add(filterInfoAnnc);
	}
	
	public FilterInfoFlexContainerAnnc getFilterInfoAnnc() {
		this.filterInfoAnnc = (FilterInfoFlexContainerAnnc) getResourceByName(FilterInfoFlexContainerAnnc.SHORT_NAME);
		return filterInfoAnnc;
	}
	
}
