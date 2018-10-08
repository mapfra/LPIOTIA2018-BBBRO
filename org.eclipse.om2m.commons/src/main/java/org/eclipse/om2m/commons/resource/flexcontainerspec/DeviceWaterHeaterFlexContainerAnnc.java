/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceWaterHeaterAnnc

A water heater is a device that is used to provide hot water through home facilities.

Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DeviceWaterHeaterFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceWaterHeaterFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceWaterHeaterFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceWaterHeaterAnnc";
	public static final String SHORT_NAME = "deWHrAnnc";
	
	public DeviceWaterHeaterFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceWaterHeaterFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getFaultDetection();
		getFaultDetectionAnnc();
		getBinarySwitch();
		getBinarySwitchAnnc();
		getRunState();
		getRunStateAnnc();
		getClock();
		getClockAnnc();
		getBoiler();
		getBoilerAnnc();
		getHotWaterSupply();
		getHotWaterSupplyAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.faultDetection != null) {
			setFaultDetection(this.faultDetection);
		}
		if (this.faultDetectionAnnc != null) {
			setFaultDetectionAnnc(this.faultDetectionAnnc);
		}
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
		if (this.clock != null) {
			setClock(this.clock);
		}
		if (this.clockAnnc != null) {
			setClockAnnc(this.clockAnnc);
		}
		if (this.boiler != null) {
			setBoiler(this.boiler);
		}
		if (this.boilerAnnc != null) {
			setBoilerAnnc(this.boilerAnnc);
		}
		if (this.hotWaterSupply != null) {
			setHotWaterSupply(this.hotWaterSupply);
		}
		if (this.hotWaterSupplyAnnc != null) {
			setHotWaterSupplyAnnc(this.hotWaterSupplyAnnc);
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
	
	@XmlElement(name=BoilerFlexContainer.SHORT_NAME, required=true, type=BoilerFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BoilerFlexContainer boiler;
		
	public void setBoiler(BoilerFlexContainer boiler) {
		this.boiler = boiler;
		getFlexContainerOrContainerOrSubscription().add(boiler);
	}
	
	public BoilerFlexContainer getBoiler() {
		this.boiler = (BoilerFlexContainer) getResourceByName(BoilerFlexContainer.SHORT_NAME);
		return boiler;
	}
	
	@XmlElement(name=BoilerFlexContainerAnnc.SHORT_NAME, required=true, type=BoilerFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BoilerFlexContainerAnnc boilerAnnc;
		
	public void setBoilerAnnc(BoilerFlexContainerAnnc boilerAnnc) {
		this.boilerAnnc = boilerAnnc;
		getFlexContainerOrContainerOrSubscription().add(boilerAnnc);
	}
	
	public BoilerFlexContainerAnnc getBoilerAnnc() {
		this.boilerAnnc = (BoilerFlexContainerAnnc) getResourceByName(BoilerFlexContainerAnnc.SHORT_NAME);
		return boilerAnnc;
	}
	
	@XmlElement(name=HotWaterSupplyFlexContainer.SHORT_NAME, required=true, type=HotWaterSupplyFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private HotWaterSupplyFlexContainer hotWaterSupply;
		
	public void setHotWaterSupply(HotWaterSupplyFlexContainer hotWaterSupply) {
		this.hotWaterSupply = hotWaterSupply;
		getFlexContainerOrContainerOrSubscription().add(hotWaterSupply);
	}
	
	public HotWaterSupplyFlexContainer getHotWaterSupply() {
		this.hotWaterSupply = (HotWaterSupplyFlexContainer) getResourceByName(HotWaterSupplyFlexContainer.SHORT_NAME);
		return hotWaterSupply;
	}
	
	@XmlElement(name=HotWaterSupplyFlexContainerAnnc.SHORT_NAME, required=true, type=HotWaterSupplyFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private HotWaterSupplyFlexContainerAnnc hotWaterSupplyAnnc;
		
	public void setHotWaterSupplyAnnc(HotWaterSupplyFlexContainerAnnc hotWaterSupplyAnnc) {
		this.hotWaterSupplyAnnc = hotWaterSupplyAnnc;
		getFlexContainerOrContainerOrSubscription().add(hotWaterSupplyAnnc);
	}
	
	public HotWaterSupplyFlexContainerAnnc getHotWaterSupplyAnnc() {
		this.hotWaterSupplyAnnc = (HotWaterSupplyFlexContainerAnnc) getResourceByName(HotWaterSupplyFlexContainerAnnc.SHORT_NAME);
		return hotWaterSupplyAnnc;
	}
	
}
