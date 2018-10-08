/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceAirPurifier

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

@XmlRootElement(name = DeviceAirPurifierFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceAirPurifierFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceAirPurifierFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceAirPurifier";
	public static final String SHORT_NAME = "deAPr";
	
	public DeviceAirPurifierFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceAirPurifierFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getBinarySwitch();
		getRunState();
		getAirPurifierJobMode();
		getAirPurifierOperationMode();
		getTimer();
		getPowerSave();
		getAirQualitySensor();
		getFilterInfo();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.airPurifierJobMode != null) {
			setAirPurifierJobMode(this.airPurifierJobMode);
		}
		if (this.airPurifierOperationMode != null) {
			setAirPurifierOperationMode(this.airPurifierOperationMode);
		}
		if (this.timer != null) {
			setTimer(this.timer);
		}
		if (this.powerSave != null) {
			setPowerSave(this.powerSave);
		}
		if (this.airQualitySensor != null) {
			setAirQualitySensor(this.airQualitySensor);
		}
		if (this.filterInfo != null) {
			setFilterInfo(this.filterInfo);
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
	
	@XmlElement(name=OperationModeFlexContainer.SHORT_NAME, required=true, type=OperationModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private OperationModeFlexContainer airPurifierOperationMode;
		
	public void setAirPurifierOperationMode(OperationModeFlexContainer airPurifierOperationMode) {
		this.airPurifierOperationMode = airPurifierOperationMode;
		getFlexContainerOrContainerOrSubscription().add(airPurifierOperationMode);
	}
	
	public OperationModeFlexContainer getAirPurifierOperationMode() {
		this.airPurifierOperationMode = (OperationModeFlexContainer) getResourceByName(OperationModeFlexContainer.SHORT_NAME);
		return airPurifierOperationMode;
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
	
}
