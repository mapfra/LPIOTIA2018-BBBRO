/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceCoffeeMachine

A CoffeeMachine is a device that produces coffee.

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
		getRunMode();
		getClock();
		getBrewing();
		getWaterStatus();
		getMilkStatus();
		getBeansStatus();
		getGrinder();
		getFoamedMilk();
		getMilkQuantity();
		getKeepWarm();
		getBrewingSwitch();
	}
	
	public void finalizeDeserialization() {
		if (this.faultDetection != null) {
			setFaultDetection(this.faultDetection);
		}
		if (this.runMode != null) {
			setRunMode(this.runMode);
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
		if (this.beansStatus != null) {
			setBeansStatus(this.beansStatus);
		}
		if (this.grinder != null) {
			setGrinder(this.grinder);
		}
		if (this.foamedMilk != null) {
			setFoamedMilk(this.foamedMilk);
		}
		if (this.milkQuantity != null) {
			setMilkQuantity(this.milkQuantity);
		}
		if (this.keepWarm != null) {
			setKeepWarm(this.keepWarm);
		}
		if (this.brewingSwitch != null) {
			setBrewingSwitch(this.brewingSwitch);
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
	
	@XmlElement(name="brewg", required=true, type=BrewingFlexContainer.class)
	private BrewingFlexContainer brewing;
	
	
	public void setBrewing(BrewingFlexContainer brewing) {
		this.brewing = brewing;
		getFlexContainerOrContainerOrSubscription().add(brewing);
	}
	
	public BrewingFlexContainer getBrewing() {
		this.brewing = (BrewingFlexContainer) getResourceByName(BrewingFlexContainer.SHORT_NAME);
		return brewing;
	}
	
	@XmlElement(name="watSs", required=true, type=LiquidLevelFlexContainer.class)
	private LiquidLevelFlexContainer waterStatus;
	
	
	public void setWaterStatus(LiquidLevelFlexContainer waterStatus) {
		this.waterStatus = waterStatus;
		getFlexContainerOrContainerOrSubscription().add(waterStatus);
	}
	
	public LiquidLevelFlexContainer getWaterStatus() {
		this.waterStatus = (LiquidLevelFlexContainer) getResourceByName(LiquidLevelFlexContainer.SHORT_NAME);
		return waterStatus;
	}
	
	@XmlElement(name="milSs", required=true, type=LiquidLevelFlexContainer.class)
	private LiquidLevelFlexContainer milkStatus;
	
	
	public void setMilkStatus(LiquidLevelFlexContainer milkStatus) {
		this.milkStatus = milkStatus;
		getFlexContainerOrContainerOrSubscription().add(milkStatus);
	}
	
	public LiquidLevelFlexContainer getMilkStatus() {
		this.milkStatus = (LiquidLevelFlexContainer) getResourceByName(LiquidLevelFlexContainer.SHORT_NAME);
		return milkStatus;
	}
	
	@XmlElement(name="beaSs", required=true, type=LiquidLevelFlexContainer.class)
	private LiquidLevelFlexContainer beansStatus;
	
	
	public void setBeansStatus(LiquidLevelFlexContainer beansStatus) {
		this.beansStatus = beansStatus;
		getFlexContainerOrContainerOrSubscription().add(beansStatus);
	}
	
	public LiquidLevelFlexContainer getBeansStatus() {
		this.beansStatus = (LiquidLevelFlexContainer) getResourceByName(LiquidLevelFlexContainer.SHORT_NAME);
		return beansStatus;
	}
	
	@XmlElement(name="grinr", required=true, type=GrinderFlexContainer.class)
	private GrinderFlexContainer grinder;
	
	
	public void setGrinder(GrinderFlexContainer grinder) {
		this.grinder = grinder;
		getFlexContainerOrContainerOrSubscription().add(grinder);
	}
	
	public GrinderFlexContainer getGrinder() {
		this.grinder = (GrinderFlexContainer) getResourceByName(GrinderFlexContainer.SHORT_NAME);
		return grinder;
	}
	
	@XmlElement(name="foaMk", required=true, type=FoamingFlexContainer.class)
	private FoamingFlexContainer foamedMilk;
	
	
	public void setFoamedMilk(FoamingFlexContainer foamedMilk) {
		this.foamedMilk = foamedMilk;
		getFlexContainerOrContainerOrSubscription().add(foamedMilk);
	}
	
	public FoamingFlexContainer getFoamedMilk() {
		this.foamedMilk = (FoamingFlexContainer) getResourceByName(FoamingFlexContainer.SHORT_NAME);
		return foamedMilk;
	}
	
	@XmlElement(name="milQy", required=true, type=LiquidLevelFlexContainer.class)
	private LiquidLevelFlexContainer milkQuantity;
	
	
	public void setMilkQuantity(LiquidLevelFlexContainer milkQuantity) {
		this.milkQuantity = milkQuantity;
		getFlexContainerOrContainerOrSubscription().add(milkQuantity);
	}
	
	public LiquidLevelFlexContainer getMilkQuantity() {
		this.milkQuantity = (LiquidLevelFlexContainer) getResourceByName(LiquidLevelFlexContainer.SHORT_NAME);
		return milkQuantity;
	}
	
	@XmlElement(name="keeWm", required=true, type=KeepWarmFlexContainer.class)
	private KeepWarmFlexContainer keepWarm;
	
	
	public void setKeepWarm(KeepWarmFlexContainer keepWarm) {
		this.keepWarm = keepWarm;
		getFlexContainerOrContainerOrSubscription().add(keepWarm);
	}
	
	public KeepWarmFlexContainer getKeepWarm() {
		this.keepWarm = (KeepWarmFlexContainer) getResourceByName(KeepWarmFlexContainer.SHORT_NAME);
		return keepWarm;
	}
	
	@XmlElement(name="breSh", required=true, type=BinarySwitchFlexContainer.class)
	private BinarySwitchFlexContainer brewingSwitch;
	
	
	public void setBrewingSwitch(BinarySwitchFlexContainer brewingSwitch) {
		this.brewingSwitch = brewingSwitch;
		getFlexContainerOrContainerOrSubscription().add(brewingSwitch);
	}
	
	public BinarySwitchFlexContainer getBrewingSwitch() {
		this.brewingSwitch = (BinarySwitchFlexContainer) getResourceByName(BinarySwitchFlexContainer.SHORT_NAME);
		return brewingSwitch;
	}
	
}