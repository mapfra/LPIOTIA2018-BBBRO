/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceCoffeeMachineAnnc

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
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DeviceCoffeeMachineFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceCoffeeMachineFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceCoffeeMachineFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceCoffeeMachineAnnc";
	public static final String SHORT_NAME = "deCMeAnnc";
	
	public DeviceCoffeeMachineFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceCoffeeMachineFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getFaultDetection();
		getFaultDetectionAnnc();
		getRunState();
		getRunStateAnnc();
		getClock();
		getClockAnnc();
		getBrewing();
		getBrewingAnnc();
		getGrinder();
		getGrinderAnnc();
		getKeepWarm();
		getKeepWarmAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.faultDetection != null) {
			setFaultDetection(this.faultDetection);
		}
		if (this.faultDetectionAnnc != null) {
			setFaultDetectionAnnc(this.faultDetectionAnnc);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.runStateAnnc != null) {
			setRunStateAnnc(this.runStateAnnc);
		}
		if (this.clock != null) {
			setClock(this.clock);
		}
		if (this.clockAnnc != null) {
			setClockAnnc(this.clockAnnc);
		}
		if (this.brewing != null) {
			setBrewing(this.brewing);
		}
		if (this.brewingAnnc != null) {
			setBrewingAnnc(this.brewingAnnc);
		}
		if (this.grinder != null) {
			setGrinder(this.grinder);
		}
		if (this.grinderAnnc != null) {
			setGrinderAnnc(this.grinderAnnc);
		}
		if (this.keepWarm != null) {
			setKeepWarm(this.keepWarm);
		}
		if (this.keepWarmAnnc != null) {
			setKeepWarmAnnc(this.keepWarmAnnc);
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
	
	@XmlElement(name=FaultDetectionFlexContainerAnnc.SHORT_NAME, required=true, type=FaultDetectionFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private FaultDetectionFlexContainerAnnc faultDetectionAnnc;
		
	public void setFaultDetectionAnnc(FaultDetectionFlexContainerAnnc faultDetectionAnnc) {
		this.faultDetectionAnnc = faultDetectionAnnc;
		getFlexContainerOrContainerOrSubscription().add(faultDetectionAnnc);
	}
	
	public FaultDetectionFlexContainerAnnc getFaultDetectionAnnc() {
		this.faultDetectionAnnc = (FaultDetectionFlexContainerAnnc) getResourceByName(FaultDetectionFlexContainerAnnc.SHORT_NAME);
		return faultDetectionAnnc;
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
	
	@XmlElement(name=ClockFlexContainerAnnc.SHORT_NAME, required=true, type=ClockFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ClockFlexContainerAnnc clockAnnc;
		
	public void setClockAnnc(ClockFlexContainerAnnc clockAnnc) {
		this.clockAnnc = clockAnnc;
		getFlexContainerOrContainerOrSubscription().add(clockAnnc);
	}
	
	public ClockFlexContainerAnnc getClockAnnc() {
		this.clockAnnc = (ClockFlexContainerAnnc) getResourceByName(ClockFlexContainerAnnc.SHORT_NAME);
		return clockAnnc;
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
	
	@XmlElement(name=BrewingFlexContainerAnnc.SHORT_NAME, required=true, type=BrewingFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BrewingFlexContainerAnnc brewingAnnc;
		
	public void setBrewingAnnc(BrewingFlexContainerAnnc brewingAnnc) {
		this.brewingAnnc = brewingAnnc;
		getFlexContainerOrContainerOrSubscription().add(brewingAnnc);
	}
	
	public BrewingFlexContainerAnnc getBrewingAnnc() {
		this.brewingAnnc = (BrewingFlexContainerAnnc) getResourceByName(BrewingFlexContainerAnnc.SHORT_NAME);
		return brewingAnnc;
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
	
	@XmlElement(name=GrinderFlexContainerAnnc.SHORT_NAME, required=true, type=GrinderFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private GrinderFlexContainerAnnc grinderAnnc;
		
	public void setGrinderAnnc(GrinderFlexContainerAnnc grinderAnnc) {
		this.grinderAnnc = grinderAnnc;
		getFlexContainerOrContainerOrSubscription().add(grinderAnnc);
	}
	
	public GrinderFlexContainerAnnc getGrinderAnnc() {
		this.grinderAnnc = (GrinderFlexContainerAnnc) getResourceByName(GrinderFlexContainerAnnc.SHORT_NAME);
		return grinderAnnc;
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
	
	@XmlElement(name=KeepWarmFlexContainerAnnc.SHORT_NAME, required=true, type=KeepWarmFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private KeepWarmFlexContainerAnnc keepWarmAnnc;
		
	public void setKeepWarmAnnc(KeepWarmFlexContainerAnnc keepWarmAnnc) {
		this.keepWarmAnnc = keepWarmAnnc;
		getFlexContainerOrContainerOrSubscription().add(keepWarmAnnc);
	}
	
	public KeepWarmFlexContainerAnnc getKeepWarmAnnc() {
		this.keepWarmAnnc = (KeepWarmFlexContainerAnnc) getResourceByName(KeepWarmFlexContainerAnnc.SHORT_NAME);
		return keepWarmAnnc;
	}
	
}
