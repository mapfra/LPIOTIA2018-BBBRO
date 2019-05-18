/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceMultiFunctionPrinter

A Multi Function Printer (MFP) is an office machine which incorporates the functionality of multiple devices in one, so as to have a smaller footprint in home or office. A typical MFP may act as a combination of printer, scanner and more. This MFP information model provides capabilities to control and monitor MFP specific functions and resources.

Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceMultiFunctionPrinterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceMultiFunctionPrinterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceMultiFunctionPrinterFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceMultiFunctionPrinter";
	public static final String SHORT_NAME = "dMFPr";
	
	public DeviceMultiFunctionPrinterFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceMultiFunctionPrinterFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getBinarySwitch();
		getPrinterRunState();
		getScannerRunState();
		getAutoDocumentFeeder();
		getPrintQueue();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.printerRunState != null) {
			setPrinterRunState(this.printerRunState);
		}
		if (this.scannerRunState != null) {
			setScannerRunState(this.scannerRunState);
		}
		if (this.autoDocumentFeeder != null) {
			setAutoDocumentFeeder(this.autoDocumentFeeder);
		}
		if (this.printQueue != null) {
			setPrintQueue(this.printQueue);
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
	
	@XmlElement(name=RunStateFlexContainer.SHORT_NAME, required=true, type=RunStateFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private RunStateFlexContainer printerRunState;
		
	public void setPrinterRunState(RunStateFlexContainer printerRunState) {
		this.printerRunState = printerRunState;
		getFlexContainerOrContainerOrSubscription().add(printerRunState);
	}
	
	public RunStateFlexContainer getPrinterRunState() {
		this.printerRunState = (RunStateFlexContainer) getResourceByName(RunStateFlexContainer.SHORT_NAME);
		return printerRunState;
	}
	
	@XmlElement(name=RunStateFlexContainer.SHORT_NAME, required=true, type=RunStateFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private RunStateFlexContainer scannerRunState;
		
	public void setScannerRunState(RunStateFlexContainer scannerRunState) {
		this.scannerRunState = scannerRunState;
		getFlexContainerOrContainerOrSubscription().add(scannerRunState);
	}
	
	public RunStateFlexContainer getScannerRunState() {
		this.scannerRunState = (RunStateFlexContainer) getResourceByName(RunStateFlexContainer.SHORT_NAME);
		return scannerRunState;
	}
	
	@XmlElement(name=AutoDocumentFeederFlexContainer.SHORT_NAME, required=true, type=AutoDocumentFeederFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AutoDocumentFeederFlexContainer autoDocumentFeeder;
		
	public void setAutoDocumentFeeder(AutoDocumentFeederFlexContainer autoDocumentFeeder) {
		this.autoDocumentFeeder = autoDocumentFeeder;
		getFlexContainerOrContainerOrSubscription().add(autoDocumentFeeder);
	}
	
	public AutoDocumentFeederFlexContainer getAutoDocumentFeeder() {
		this.autoDocumentFeeder = (AutoDocumentFeederFlexContainer) getResourceByName(AutoDocumentFeederFlexContainer.SHORT_NAME);
		return autoDocumentFeeder;
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
	
}
