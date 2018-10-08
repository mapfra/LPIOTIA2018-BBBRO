/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceThreeDPrinterAnnc

A 3D printer is a smart home appliance to provide 3D printing capabilities.

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

@XmlRootElement(name = DeviceThreeDPrinterFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceThreeDPrinterFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceThreeDPrinterFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceThreeDPrinterAnnc";
	public static final String SHORT_NAME = "dTDPrAnnc";
	
	public DeviceThreeDPrinterFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceThreeDPrinterFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getFaultDetection();
		getFaultDetectionAnnc();
		getThreeDPrinter();
		getThreeDPrinterAnnc();
		getRunState();
		getRunStateAnnc();
		getTemperature();
		getTemperatureAnnc();
		getPrintQueue();
		getPrintQueueAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.binarySwitchAnnc != null) {
			setBinarySwitchAnnc(this.binarySwitchAnnc);
		}
		if (this.faultDetection != null) {
			setFaultDetection(this.faultDetection);
		}
		if (this.faultDetectionAnnc != null) {
			setFaultDetectionAnnc(this.faultDetectionAnnc);
		}
		if (this.threeDPrinter != null) {
			setThreeDPrinter(this.threeDPrinter);
		}
		if (this.threeDPrinterAnnc != null) {
			setThreeDPrinterAnnc(this.threeDPrinterAnnc);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.runStateAnnc != null) {
			setRunStateAnnc(this.runStateAnnc);
		}
		if (this.temperature != null) {
			setTemperature(this.temperature);
		}
		if (this.temperatureAnnc != null) {
			setTemperatureAnnc(this.temperatureAnnc);
		}
		if (this.printQueue != null) {
			setPrintQueue(this.printQueue);
		}
		if (this.printQueueAnnc != null) {
			setPrintQueueAnnc(this.printQueueAnnc);
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
	
	@XmlElement(name=ThreeDPrinterFlexContainer.SHORT_NAME, required=true, type=ThreeDPrinterFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ThreeDPrinterFlexContainer threeDPrinter;
		
	public void setThreeDPrinter(ThreeDPrinterFlexContainer threeDPrinter) {
		this.threeDPrinter = threeDPrinter;
		getFlexContainerOrContainerOrSubscription().add(threeDPrinter);
	}
	
	public ThreeDPrinterFlexContainer getThreeDPrinter() {
		this.threeDPrinter = (ThreeDPrinterFlexContainer) getResourceByName(ThreeDPrinterFlexContainer.SHORT_NAME);
		return threeDPrinter;
	}
	
	@XmlElement(name=ThreeDPrinterFlexContainerAnnc.SHORT_NAME, required=true, type=ThreeDPrinterFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ThreeDPrinterFlexContainerAnnc threeDPrinterAnnc;
		
	public void setThreeDPrinterAnnc(ThreeDPrinterFlexContainerAnnc threeDPrinterAnnc) {
		this.threeDPrinterAnnc = threeDPrinterAnnc;
		getFlexContainerOrContainerOrSubscription().add(threeDPrinterAnnc);
	}
	
	public ThreeDPrinterFlexContainerAnnc getThreeDPrinterAnnc() {
		this.threeDPrinterAnnc = (ThreeDPrinterFlexContainerAnnc) getResourceByName(ThreeDPrinterFlexContainerAnnc.SHORT_NAME);
		return threeDPrinterAnnc;
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
	
	@XmlElement(name=PrintQueueFlexContainer.SHORT_NAME, required=true, type=PrintQueueFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private PrintQueueFlexContainer printQueue;
		
	public void setPrintQueue(PrintQueueFlexContainer printQueue) {
		this.printQueue = printQueue;
		getFlexContainerOrContainerOrSubscription().add(printQueue);
	}
	
	public PrintQueueFlexContainer getPrintQueue() {
		this.printQueue = (PrintQueueFlexContainer) getResourceByName(PrintQueueFlexContainer.SHORT_NAME);
		return printQueue;
	}
	
	@XmlElement(name=PrintQueueFlexContainerAnnc.SHORT_NAME, required=true, type=PrintQueueFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private PrintQueueFlexContainerAnnc printQueueAnnc;
		
	public void setPrintQueueAnnc(PrintQueueFlexContainerAnnc printQueueAnnc) {
		this.printQueueAnnc = printQueueAnnc;
		getFlexContainerOrContainerOrSubscription().add(printQueueAnnc);
	}
	
	public PrintQueueFlexContainerAnnc getPrintQueueAnnc() {
		this.printQueueAnnc = (PrintQueueFlexContainerAnnc) getResourceByName(PrintQueueFlexContainerAnnc.SHORT_NAME);
		return printQueueAnnc;
	}
	
}
