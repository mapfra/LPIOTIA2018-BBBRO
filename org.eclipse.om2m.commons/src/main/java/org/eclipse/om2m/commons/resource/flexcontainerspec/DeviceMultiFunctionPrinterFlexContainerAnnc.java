/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceMultiFunctionPrinterAnnc

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
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DeviceMultiFunctionPrinterFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceMultiFunctionPrinterFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceMultiFunctionPrinterFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceMultiFunctionPrinterAnnc";
	public static final String SHORT_NAME = "dMFPrAnnc";
	
	public DeviceMultiFunctionPrinterFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceMultiFunctionPrinterFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getAutoDocumentFeeder();
		getAutoDocumentFeederAnnc();
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
		if (this.autoDocumentFeeder != null) {
			setAutoDocumentFeeder(this.autoDocumentFeeder);
		}
		if (this.autoDocumentFeederAnnc != null) {
			setAutoDocumentFeederAnnc(this.autoDocumentFeederAnnc);
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
	
	@XmlElement(name=AutoDocumentFeederFlexContainerAnnc.SHORT_NAME, required=true, type=AutoDocumentFeederFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AutoDocumentFeederFlexContainerAnnc autoDocumentFeederAnnc;
		
	public void setAutoDocumentFeederAnnc(AutoDocumentFeederFlexContainerAnnc autoDocumentFeederAnnc) {
		this.autoDocumentFeederAnnc = autoDocumentFeederAnnc;
		getFlexContainerOrContainerOrSubscription().add(autoDocumentFeederAnnc);
	}
	
	public AutoDocumentFeederFlexContainerAnnc getAutoDocumentFeederAnnc() {
		this.autoDocumentFeederAnnc = (AutoDocumentFeederFlexContainerAnnc) getResourceByName(AutoDocumentFeederFlexContainerAnnc.SHORT_NAME);
		return autoDocumentFeederAnnc;
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
