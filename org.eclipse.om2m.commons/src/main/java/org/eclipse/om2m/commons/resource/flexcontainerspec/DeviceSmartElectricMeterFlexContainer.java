/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceSmartElectricMeter

A smart electric meter is a metering device that is used to measure consumption data for electrictricity.

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


@XmlRootElement(name = DeviceSmartElectricMeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceSmartElectricMeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceSmartElectricMeterFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceSmartElectricMeter";
	public static final String SHORT_NAME = "dSEMr";
	
	public DeviceSmartElectricMeterFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceSmartElectricMeterFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getFaultDetection();
		getBinarySwitch();
		getRunMode();
		getClock();
		getEnergyConsumption();
		getEnergyGeneration();
	}
	
	public void finalizeDeserialization() {
		if (this.faultDetection != null) {
			setFaultDetection(this.faultDetection);
		}
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.runMode != null) {
			setRunMode(this.runMode);
		}
		if (this.clock != null) {
			setClock(this.clock);
		}
		if (this.energyConsumption != null) {
			setEnergyConsumption(this.energyConsumption);
		}
		if (this.energyGeneration != null) {
			setEnergyGeneration(this.energyGeneration);
		}
	}
	
	@XmlElement(name="fauDn", required=true, type=FaultDetectionFlexContainer.class)
	private FaultDetectionFlexContainer faultDetection;
	
	
	public void setFaultDetection(FaultDetectionFlexContainer faultDetection) {
		this.faultDetection = faultDetection;
		getFlexContainerOrContainerOrSubscription().add(faultDetection);
	}
	
	public FaultDetectionFlexContainer getFaultDetection() {
		this.faultDetection = (FaultDetectionFlexContainer) getResourceByName(FaultDetectionFlexContainer.SHORT_NAME);
		return faultDetection;
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
	
	@XmlElement(name="clock", required=true, type=ClockFlexContainer.class)
	private ClockFlexContainer clock;
	
	
	public void setClock(ClockFlexContainer clock) {
		this.clock = clock;
		getFlexContainerOrContainerOrSubscription().add(clock);
	}
	
	public ClockFlexContainer getClock() {
		this.clock = (ClockFlexContainer) getResourceByName(ClockFlexContainer.SHORT_NAME);
		return clock;
	}
	
	@XmlElement(name="eneCn", required=true, type=EnergyConsumptionFlexContainer.class)
	private EnergyConsumptionFlexContainer energyConsumption;
	
	
	public void setEnergyConsumption(EnergyConsumptionFlexContainer energyConsumption) {
		this.energyConsumption = energyConsumption;
		getFlexContainerOrContainerOrSubscription().add(energyConsumption);
	}
	
	public EnergyConsumptionFlexContainer getEnergyConsumption() {
		this.energyConsumption = (EnergyConsumptionFlexContainer) getResourceByName(EnergyConsumptionFlexContainer.SHORT_NAME);
		return energyConsumption;
	}
	
	@XmlElement(name="eneGn", required=true, type=EnergyGenerationFlexContainer.class)
	private EnergyGenerationFlexContainer energyGeneration;
	
	
	public void setEnergyGeneration(EnergyGenerationFlexContainer energyGeneration) {
		this.energyGeneration = energyGeneration;
		getFlexContainerOrContainerOrSubscription().add(energyGeneration);
	}
	
	public EnergyGenerationFlexContainer getEnergyGeneration() {
		this.energyGeneration = (EnergyGenerationFlexContainer) getResourceByName(EnergyGenerationFlexContainer.SHORT_NAME);
		return energyGeneration;
	}
	
}