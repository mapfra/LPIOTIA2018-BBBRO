/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceElectricVehicleCharger

An electric vehicle charger is a device that is used for charging or discharging electric vehicles.

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


@XmlRootElement(name = DeviceElectricVehicleChargerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceElectricVehicleChargerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceElectricVehicleChargerFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceElectricVehicleCharger";
	public static final String SHORT_NAME = "dEVCr";
	
	public DeviceElectricVehicleChargerFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceElectricVehicleChargerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getFaultDetection();
		getBinarySwitch();
		getRunMode();
		getBattery();
		getElectricVehicleConnector();
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
		if (this.battery != null) {
			setBattery(this.battery);
		}
		if (this.electricVehicleConnector != null) {
			setElectricVehicleConnector(this.electricVehicleConnector);
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
	
	@XmlElement(name="batty", required=true, type=BatteryFlexContainer.class)
	private BatteryFlexContainer battery;
	
	
	public void setBattery(BatteryFlexContainer battery) {
		this.battery = battery;
		getFlexContainerOrContainerOrSubscription().add(battery);
	}
	
	public BatteryFlexContainer getBattery() {
		this.battery = (BatteryFlexContainer) getResourceByName(BatteryFlexContainer.SHORT_NAME);
		return battery;
	}
	
	@XmlElement(name="elVCr", required=true, type=ElectricVehicleConnectorFlexContainer.class)
	private ElectricVehicleConnectorFlexContainer electricVehicleConnector;
	
	
	public void setElectricVehicleConnector(ElectricVehicleConnectorFlexContainer electricVehicleConnector) {
		this.electricVehicleConnector = electricVehicleConnector;
		getFlexContainerOrContainerOrSubscription().add(electricVehicleConnector);
	}
	
	public ElectricVehicleConnectorFlexContainer getElectricVehicleConnector() {
		this.electricVehicleConnector = (ElectricVehicleConnectorFlexContainer) getResourceByName(ElectricVehicleConnectorFlexContainer.SHORT_NAME);
		return electricVehicleConnector;
	}
	
}