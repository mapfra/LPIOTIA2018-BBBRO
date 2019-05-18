/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceAirConditionerAnnc

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
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DeviceAirConditionerFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceAirConditionerFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceAirConditionerFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceAirConditionerAnnc";
	public static final String SHORT_NAME = "deACrAnnc";
	
	public DeviceAirConditionerFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceAirConditionerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getRunState();
		getRunStateAnnc();
		getAirConJobMode();
		getAirConJobModeAnnc();
		getTemperature();
		getTemperatureAnnc();
		getTimer();
		getTimerAnnc();
		getTurbo();
		getTurboAnnc();
		getAirFlow();
		getAirFlowAnnc();
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
		if (this.airConJobMode != null) {
			setAirConJobMode(this.airConJobMode);
		}
		if (this.airConJobModeAnnc != null) {
			setAirConJobModeAnnc(this.airConJobModeAnnc);
		}
		if (this.temperature != null) {
			setTemperature(this.temperature);
		}
		if (this.temperatureAnnc != null) {
			setTemperatureAnnc(this.temperatureAnnc);
		}
		if (this.timer != null) {
			setTimer(this.timer);
		}
		if (this.timerAnnc != null) {
			setTimerAnnc(this.timerAnnc);
		}
		if (this.turbo != null) {
			setTurbo(this.turbo);
		}
		if (this.turboAnnc != null) {
			setTurboAnnc(this.turboAnnc);
		}
		if (this.airFlow != null) {
			setAirFlow(this.airFlow);
		}
		if (this.airFlowAnnc != null) {
			setAirFlowAnnc(this.airFlowAnnc);
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
	
	@XmlElement(name=AirConJobModeFlexContainerAnnc.SHORT_NAME, required=true, type=AirConJobModeFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AirConJobModeFlexContainerAnnc airConJobModeAnnc;
		
	public void setAirConJobModeAnnc(AirConJobModeFlexContainerAnnc airConJobModeAnnc) {
		this.airConJobModeAnnc = airConJobModeAnnc;
		getFlexContainerOrContainerOrSubscription().add(airConJobModeAnnc);
	}
	
	public AirConJobModeFlexContainerAnnc getAirConJobModeAnnc() {
		this.airConJobModeAnnc = (AirConJobModeFlexContainerAnnc) getResourceByName(AirConJobModeFlexContainerAnnc.SHORT_NAME);
		return airConJobModeAnnc;
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
	
	@XmlElement(name=TemperatureFlexContainerAnnc.SHORT_NAME, required=true, type=TemperatureFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TemperatureFlexContainerAnnc temperatureAnnc;
		
	public void setTemperatureAnnc(TemperatureFlexContainerAnnc temperatureAnnc) {
		this.temperatureAnnc = temperatureAnnc;
		getFlexContainerOrContainerOrSubscription().add(temperatureAnnc);
	}
	
	public TemperatureFlexContainerAnnc getTemperatureAnnc() {
		this.temperatureAnnc = (TemperatureFlexContainerAnnc) getResourceByName(TemperatureFlexContainerAnnc.SHORT_NAME);
		return temperatureAnnc;
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
	
	@XmlElement(name=TurboFlexContainerAnnc.SHORT_NAME, required=true, type=TurboFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TurboFlexContainerAnnc turboAnnc;
		
	public void setTurboAnnc(TurboFlexContainerAnnc turboAnnc) {
		this.turboAnnc = turboAnnc;
		getFlexContainerOrContainerOrSubscription().add(turboAnnc);
	}
	
	public TurboFlexContainerAnnc getTurboAnnc() {
		this.turboAnnc = (TurboFlexContainerAnnc) getResourceByName(TurboFlexContainerAnnc.SHORT_NAME);
		return turboAnnc;
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
	
	@XmlElement(name=AirFlowFlexContainerAnnc.SHORT_NAME, required=true, type=AirFlowFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AirFlowFlexContainerAnnc airFlowAnnc;
		
	public void setAirFlowAnnc(AirFlowFlexContainerAnnc airFlowAnnc) {
		this.airFlowAnnc = airFlowAnnc;
		getFlexContainerOrContainerOrSubscription().add(airFlowAnnc);
	}
	
	public AirFlowFlexContainerAnnc getAirFlowAnnc() {
		this.airFlowAnnc = (AirFlowFlexContainerAnnc) getResourceByName(AirFlowFlexContainerAnnc.SHORT_NAME);
		return airFlowAnnc;
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
