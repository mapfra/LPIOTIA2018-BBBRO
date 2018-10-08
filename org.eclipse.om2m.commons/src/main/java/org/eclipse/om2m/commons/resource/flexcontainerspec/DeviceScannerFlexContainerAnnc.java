/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceScannerAnnc

A scanner is a device that optically scans images, printed text, handwriting or an object, and converts it to a digital image.

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

@XmlRootElement(name = DeviceScannerFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceScannerFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceScannerFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceScannerAnnc";
	public static final String SHORT_NAME = "devSrAnnc";
	
	public DeviceScannerFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceScannerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getRunState();
		getRunStateAnnc();
		getAutoDocumentFeeder();
		getAutoDocumentFeederAnnc();
    }
	
	public void finalizeDeserialization() {
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
		if (this.autoDocumentFeeder != null) {
			setAutoDocumentFeeder(this.autoDocumentFeeder);
		}
		if (this.autoDocumentFeederAnnc != null) {
			setAutoDocumentFeederAnnc(this.autoDocumentFeederAnnc);
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
	
}
