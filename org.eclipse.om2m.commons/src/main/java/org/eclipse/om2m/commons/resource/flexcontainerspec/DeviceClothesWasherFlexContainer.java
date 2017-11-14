/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceClothesWasher

A clothes washer is a home appliance that is used to wash laundry, such as clothing and sheets. This information model provides capabilities to interact with specific functions and resources of clothes washers.

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


@XmlRootElement(name = DeviceClothesWasherFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceClothesWasherFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceClothesWasherFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceClothesWasher";
	public static final String SHORT_NAME = "deCWr";
	
	public DeviceClothesWasherFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceClothesWasherFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getTimer();
		getRunMode();
		getTemperature();
		getWaterLevel();
		getRinseLevel();
		getWaterFlow();
		getSpinLevel();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.timer != null) {
			setTimer(this.timer);
		}
		if (this.runMode != null) {
			setRunMode(this.runMode);
		}
		if (this.temperature != null) {
			setTemperature(this.temperature);
		}
		if (this.waterLevel != null) {
			setWaterLevel(this.waterLevel);
		}
		if (this.rinseLevel != null) {
			setRinseLevel(this.rinseLevel);
		}
		if (this.waterFlow != null) {
			setWaterFlow(this.waterFlow);
		}
		if (this.spinLevel != null) {
			setSpinLevel(this.spinLevel);
		}
	}
	
	@XmlElement(name="binSh", required=true, type=BinarySwitchFlexContainer.class)
	private BinarySwitchFlexContainer binarySwitch;
	
	
	public void setBinarySwitch(BinarySwitchFlexContainer binarySwitch) {
		this.binarySwitch = binarySwitch;
		getFlexContainerOrContainerOrSubscription().add(binarySwitch);
	}
	
	public BinarySwitchFlexContainer getBinarySwitch() {
		this.binarySwitch = (BinarySwitchFlexContainer) getResourceByName(BinarySwitchFlexContainer.SHORT_NAME);
		return binarySwitch;
	}
	
	@XmlElement(name="timer", required=true, type=TimerFlexContainer.class)
	private TimerFlexContainer timer;
	
	
	public void setTimer(TimerFlexContainer timer) {
		this.timer = timer;
		getFlexContainerOrContainerOrSubscription().add(timer);
	}
	
	public TimerFlexContainer getTimer() {
		this.timer = (TimerFlexContainer) getResourceByName(TimerFlexContainer.SHORT_NAME);
		return timer;
	}
	
	@XmlElement(name="runMe", required=true, type=RunModeFlexContainer.class)
	private RunModeFlexContainer runMode;
	
	
	public void setRunMode(RunModeFlexContainer runMode) {
		this.runMode = runMode;
		getFlexContainerOrContainerOrSubscription().add(runMode);
	}
	
	public RunModeFlexContainer getRunMode() {
		this.runMode = (RunModeFlexContainer) getResourceByName(RunModeFlexContainer.SHORT_NAME);
		return runMode;
	}
	
	@XmlElement(name="tempe", required=true, type=TemperatureFlexContainer.class)
	private TemperatureFlexContainer temperature;
	
	
	public void setTemperature(TemperatureFlexContainer temperature) {
		this.temperature = temperature;
		getFlexContainerOrContainerOrSubscription().add(temperature);
	}
	
	public TemperatureFlexContainer getTemperature() {
		this.temperature = (TemperatureFlexContainer) getResourceByName(TemperatureFlexContainer.SHORT_NAME);
		return temperature;
	}
	
	@XmlElement(name="watLl", required=true, type=WaterLevelFlexContainer.class)
	private WaterLevelFlexContainer waterLevel;
	
	
	public void setWaterLevel(WaterLevelFlexContainer waterLevel) {
		this.waterLevel = waterLevel;
		getFlexContainerOrContainerOrSubscription().add(waterLevel);
	}
	
	public WaterLevelFlexContainer getWaterLevel() {
		this.waterLevel = (WaterLevelFlexContainer) getResourceByName(WaterLevelFlexContainer.SHORT_NAME);
		return waterLevel;
	}
	
	@XmlElement(name="rinLl", required=true, type=RinseLevelFlexContainer.class)
	private RinseLevelFlexContainer rinseLevel;
	
	
	public void setRinseLevel(RinseLevelFlexContainer rinseLevel) {
		this.rinseLevel = rinseLevel;
		getFlexContainerOrContainerOrSubscription().add(rinseLevel);
	}
	
	public RinseLevelFlexContainer getRinseLevel() {
		this.rinseLevel = (RinseLevelFlexContainer) getResourceByName(RinseLevelFlexContainer.SHORT_NAME);
		return rinseLevel;
	}
	
	@XmlElement(name="watFw", required=true, type=WaterFlowFlexContainer.class)
	private WaterFlowFlexContainer waterFlow;
	
	
	public void setWaterFlow(WaterFlowFlexContainer waterFlow) {
		this.waterFlow = waterFlow;
		getFlexContainerOrContainerOrSubscription().add(waterFlow);
	}
	
	public WaterFlowFlexContainer getWaterFlow() {
		this.waterFlow = (WaterFlowFlexContainer) getResourceByName(WaterFlowFlexContainer.SHORT_NAME);
		return waterFlow;
	}
	
	@XmlElement(name="spiLl", required=true, type=SpinLevelFlexContainer.class)
	private SpinLevelFlexContainer spinLevel;
	
	
	public void setSpinLevel(SpinLevelFlexContainer spinLevel) {
		this.spinLevel = spinLevel;
		getFlexContainerOrContainerOrSubscription().add(spinLevel);
	}
	
	public SpinLevelFlexContainer getSpinLevel() {
		this.spinLevel = (SpinLevelFlexContainer) getResourceByName(SpinLevelFlexContainer.SHORT_NAME);
		return spinLevel;
	}
	
}