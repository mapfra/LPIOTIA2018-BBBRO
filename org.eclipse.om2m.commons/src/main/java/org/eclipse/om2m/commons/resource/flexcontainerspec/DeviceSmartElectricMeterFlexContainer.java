/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceSmartElectricMeter

A smart electric meter is a metering device that is used to measure consumption data for electrictricity.

Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

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
		getRunState();
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
		if (this.runState != null) {
			setRunState(this.runState);
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

	@XmlElement(name=FaultDetectionFlexContainer.SHORT_NAME, required=true, type=FaultDetectionFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private FaultDetectionFlexContainer faultDetection;
		
	public void setFaultDetection(FaultDetectionFlexContainer faultDetection) {
		this.faultDetection = faultDetection;
		getFlexContainerOrContainerOrSubscription().add(faultDetection);
	}
	
	public FaultDetectionFlexContainer getFaultDetection() {
		this.faultDetection = (FaultDetectionFlexContainer) getResourceByName(FaultDetectionFlexContainer.SHORT_NAME);
		return faultDetection;
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
	
	@XmlElement(name=ClockFlexContainer.SHORT_NAME, required=true, type=ClockFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ClockFlexContainer clock;
		
	public void setClock(ClockFlexContainer clock) {
		this.clock = clock;
		getFlexContainerOrContainerOrSubscription().add(clock);
	}
	
	public ClockFlexContainer getClock() {
		this.clock = (ClockFlexContainer) getResourceByName(ClockFlexContainer.SHORT_NAME);
		return clock;
	}
	
	@XmlElement(name=EnergyConsumptionFlexContainer.SHORT_NAME, required=true, type=EnergyConsumptionFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private EnergyConsumptionFlexContainer energyConsumption;
		
	public void setEnergyConsumption(EnergyConsumptionFlexContainer energyConsumption) {
		this.energyConsumption = energyConsumption;
		getFlexContainerOrContainerOrSubscription().add(energyConsumption);
	}
	
	public EnergyConsumptionFlexContainer getEnergyConsumption() {
		this.energyConsumption = (EnergyConsumptionFlexContainer) getResourceByName(EnergyConsumptionFlexContainer.SHORT_NAME);
		return energyConsumption;
	}
	
	@XmlElement(name=EnergyGenerationFlexContainer.SHORT_NAME, required=true, type=EnergyGenerationFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
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
