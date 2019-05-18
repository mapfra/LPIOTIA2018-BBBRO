/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceCoffeeMachine

A coffee machine is a device that is used to brew a coffee, may add foamed milk, and may include some variants, for example a grinder.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceCoffeeMachineFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceCoffeeMachineFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceCoffeeMachineFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceCoffeeMachine";
	public static final String SHORT_NAME = "deCMe";
	
	public DeviceCoffeeMachineFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceCoffeeMachineFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getFaultDetection();
		getRunState();
		getClock();
		getBrewing();
		getWaterStatus();
		getMilkStatus();
		getGrinder();
		getMilkFoaming();
		getMilkQuantity();
		getBrewingSwitch();
		getKeepWarm();
	}
	
	public void finalizeDeserialization() {
		if (this.faultDetection != null) {
			setFaultDetection(this.faultDetection);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.clock != null) {
			setClock(this.clock);
		}
		if (this.brewing != null) {
			setBrewing(this.brewing);
		}
		if (this.waterStatus != null) {
			setWaterStatus(this.waterStatus);
		}
		if (this.milkStatus != null) {
			setMilkStatus(this.milkStatus);
		}
		if (this.grinder != null) {
			setGrinder(this.grinder);
		}
		if (this.milkFoaming != null) {
			setMilkFoaming(this.milkFoaming);
		}
		if (this.milkQuantity != null) {
			setMilkQuantity(this.milkQuantity);
		}
		if (this.brewingSwitch != null) {
			setBrewingSwitch(this.brewingSwitch);
		}
		if (this.keepWarm != null) {
			setKeepWarm(this.keepWarm);
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
	
	@XmlElement(name=BrewingFlexContainer.SHORT_NAME, required=true, type=BrewingFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BrewingFlexContainer brewing;
		
	public void setBrewing(BrewingFlexContainer brewing) {
		this.brewing = brewing;
		getFlexContainerOrContainerOrSubscription().add(brewing);
	}
	
	public BrewingFlexContainer getBrewing() {
		this.brewing = (BrewingFlexContainer) getResourceByName(BrewingFlexContainer.SHORT_NAME);
		return brewing;
	}
	
	@XmlElement(name=LiquidRemainingFlexContainer.SHORT_NAME, required=true, type=LiquidRemainingFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private LiquidRemainingFlexContainer waterStatus;
		
	public void setWaterStatus(LiquidRemainingFlexContainer waterStatus) {
		this.waterStatus = waterStatus;
		getFlexContainerOrContainerOrSubscription().add(waterStatus);
	}
	
	public LiquidRemainingFlexContainer getWaterStatus() {
		this.waterStatus = (LiquidRemainingFlexContainer) getResourceByName(LiquidRemainingFlexContainer.SHORT_NAME);
		return waterStatus;
	}
	
	@XmlElement(name=LiquidRemainingFlexContainer.SHORT_NAME, required=true, type=LiquidRemainingFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private LiquidRemainingFlexContainer milkStatus;
		
	public void setMilkStatus(LiquidRemainingFlexContainer milkStatus) {
		this.milkStatus = milkStatus;
		getFlexContainerOrContainerOrSubscription().add(milkStatus);
	}
	
	public LiquidRemainingFlexContainer getMilkStatus() {
		this.milkStatus = (LiquidRemainingFlexContainer) getResourceByName(LiquidRemainingFlexContainer.SHORT_NAME);
		return milkStatus;
	}
	
	@XmlElement(name=GrinderFlexContainer.SHORT_NAME, required=true, type=GrinderFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private GrinderFlexContainer grinder;
		
	public void setGrinder(GrinderFlexContainer grinder) {
		this.grinder = grinder;
		getFlexContainerOrContainerOrSubscription().add(grinder);
	}
	
	public GrinderFlexContainer getGrinder() {
		this.grinder = (GrinderFlexContainer) getResourceByName(GrinderFlexContainer.SHORT_NAME);
		return grinder;
	}
	
	@XmlElement(name=FoamingFlexContainer.SHORT_NAME, required=true, type=FoamingFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private FoamingFlexContainer milkFoaming;
		
	public void setMilkFoaming(FoamingFlexContainer milkFoaming) {
		this.milkFoaming = milkFoaming;
		getFlexContainerOrContainerOrSubscription().add(milkFoaming);
	}
	
	public FoamingFlexContainer getMilkFoaming() {
		this.milkFoaming = (FoamingFlexContainer) getResourceByName(FoamingFlexContainer.SHORT_NAME);
		return milkFoaming;
	}
	
	@XmlElement(name=LiquidLevelFlexContainer.SHORT_NAME, required=true, type=LiquidLevelFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private LiquidLevelFlexContainer milkQuantity;
		
	public void setMilkQuantity(LiquidLevelFlexContainer milkQuantity) {
		this.milkQuantity = milkQuantity;
		getFlexContainerOrContainerOrSubscription().add(milkQuantity);
	}
	
	public LiquidLevelFlexContainer getMilkQuantity() {
		this.milkQuantity = (LiquidLevelFlexContainer) getResourceByName(LiquidLevelFlexContainer.SHORT_NAME);
		return milkQuantity;
	}
	
	@XmlElement(name=BinarySwitchFlexContainer.SHORT_NAME, required=true, type=BinarySwitchFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BinarySwitchFlexContainer brewingSwitch;
		
	public void setBrewingSwitch(BinarySwitchFlexContainer brewingSwitch) {
		this.brewingSwitch = brewingSwitch;
		getFlexContainerOrContainerOrSubscription().add(brewingSwitch);
	}
	
	public BinarySwitchFlexContainer getBrewingSwitch() {
		this.brewingSwitch = (BinarySwitchFlexContainer) getResourceByName(BinarySwitchFlexContainer.SHORT_NAME);
		return brewingSwitch;
	}
	
	@XmlElement(name=KeepWarmFlexContainer.SHORT_NAME, required=true, type=KeepWarmFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private KeepWarmFlexContainer keepWarm;
		
	public void setKeepWarm(KeepWarmFlexContainer keepWarm) {
		this.keepWarm = keepWarm;
		getFlexContainerOrContainerOrSubscription().add(keepWarm);
	}
	
	public KeepWarmFlexContainer getKeepWarm() {
		this.keepWarm = (KeepWarmFlexContainer) getResourceByName(KeepWarmFlexContainer.SHORT_NAME);
		return keepWarm;
	}
	
}
