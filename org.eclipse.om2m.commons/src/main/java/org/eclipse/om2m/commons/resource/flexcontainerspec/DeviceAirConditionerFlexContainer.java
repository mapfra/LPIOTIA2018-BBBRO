/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceAirConditioner

An air conditioner is a home appliance used to alter the properties of air (primarily temperature and humidity) to more comfortable conditions. This air conditioner information model provides capabilities to control and monitor air conditioner specific functions and resources.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceAirConditionerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceAirConditionerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceAirConditionerFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceAirConditioner";
	public static final String SHORT_NAME = "deACr";
	
	public DeviceAirConditionerFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceAirConditionerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getBinarySwitch();
		getRunState();
		getAirConJobMode();
		getAirConOperationMode();
		getAirCleanOperationMode();
		getTemperature();
		getTimer();
		getSleepTimer();
		getTurbo();
		getAirFlow();
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
		if (this.airConJobMode != null) {
			setAirConJobMode(this.airConJobMode);
		}
		if (this.airConOperationMode != null) {
			setAirConOperationMode(this.airConOperationMode);
		}
		if (this.airCleanOperationMode != null) {
			setAirCleanOperationMode(this.airCleanOperationMode);
		}
		if (this.temperature != null) {
			setTemperature(this.temperature);
		}
		if (this.timer != null) {
			setTimer(this.timer);
		}
		if (this.sleepTimer != null) {
			setSleepTimer(this.sleepTimer);
		}
		if (this.turbo != null) {
			setTurbo(this.turbo);
		}
		if (this.airFlow != null) {
			setAirFlow(this.airFlow);
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
	
	@XmlElement(name=AirConJobModeFlexContainer.SHORT_NAME, required=true, type=AirConJobModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AirConJobModeFlexContainer airConJobMode;
		
	public void setAirConJobMode(AirConJobModeFlexContainer airConJobMode) {
		this.airConJobMode = airConJobMode;
		getFlexContainerOrContainerOrSubscription().add(airConJobMode);
	}
	
	public AirConJobModeFlexContainer getAirConJobMode() {
		this.airConJobMode = (AirConJobModeFlexContainer) getResourceByName(AirConJobModeFlexContainer.SHORT_NAME);
		return airConJobMode;
	}
	
	@XmlElement(name=OperationModeFlexContainer.SHORT_NAME, required=true, type=OperationModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private OperationModeFlexContainer airConOperationMode;
		
	public void setAirConOperationMode(OperationModeFlexContainer airConOperationMode) {
		this.airConOperationMode = airConOperationMode;
		getFlexContainerOrContainerOrSubscription().add(airConOperationMode);
	}
	
	public OperationModeFlexContainer getAirConOperationMode() {
		this.airConOperationMode = (OperationModeFlexContainer) getResourceByName(OperationModeFlexContainer.SHORT_NAME);
		return airConOperationMode;
	}
	
	@XmlElement(name=OperationModeFlexContainer.SHORT_NAME, required=true, type=OperationModeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private OperationModeFlexContainer airCleanOperationMode;
		
	public void setAirCleanOperationMode(OperationModeFlexContainer airCleanOperationMode) {
		this.airCleanOperationMode = airCleanOperationMode;
		getFlexContainerOrContainerOrSubscription().add(airCleanOperationMode);
	}
	
	public OperationModeFlexContainer getAirCleanOperationMode() {
		this.airCleanOperationMode = (OperationModeFlexContainer) getResourceByName(OperationModeFlexContainer.SHORT_NAME);
		return airCleanOperationMode;
	}
	
	@XmlElement(name=TemperatureFlexContainer.SHORT_NAME, required=true, type=TemperatureFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TemperatureFlexContainer temperature;
		
	public void setTemperature(TemperatureFlexContainer temperature) {
		this.temperature = temperature;
		getFlexContainerOrContainerOrSubscription().add(temperature);
	}
	
	public TemperatureFlexContainer getTemperature() {
		this.temperature = (TemperatureFlexContainer) getResourceByName(TemperatureFlexContainer.SHORT_NAME);
		return temperature;
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
	
	@XmlElement(name=TimerFlexContainer.SHORT_NAME, required=true, type=TimerFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TimerFlexContainer sleepTimer;
		
	public void setSleepTimer(TimerFlexContainer sleepTimer) {
		this.sleepTimer = sleepTimer;
		getFlexContainerOrContainerOrSubscription().add(sleepTimer);
	}
	
	public TimerFlexContainer getSleepTimer() {
		this.sleepTimer = (TimerFlexContainer) getResourceByName(TimerFlexContainer.SHORT_NAME);
		return sleepTimer;
	}
	
	@XmlElement(name=TurboFlexContainer.SHORT_NAME, required=true, type=TurboFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TurboFlexContainer turbo;
		
	public void setTurbo(TurboFlexContainer turbo) {
		this.turbo = turbo;
		getFlexContainerOrContainerOrSubscription().add(turbo);
	}
	
	public TurboFlexContainer getTurbo() {
		this.turbo = (TurboFlexContainer) getResourceByName(TurboFlexContainer.SHORT_NAME);
		return turbo;
	}
	
	@XmlElement(name=AirFlowFlexContainer.SHORT_NAME, required=true, type=AirFlowFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AirFlowFlexContainer airFlow;
		
	public void setAirFlow(AirFlowFlexContainer airFlow) {
		this.airFlow = airFlow;
		getFlexContainerOrContainerOrSubscription().add(airFlow);
	}
	
	public AirFlowFlexContainer getAirFlow() {
		this.airFlow = (AirFlowFlexContainer) getResourceByName(AirFlowFlexContainer.SHORT_NAME);
		return airFlow;
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
