/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceScanner

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

@XmlRootElement(name = DeviceScannerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceScannerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceScannerFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceScanner";
	public static final String SHORT_NAME = "devSr";
	
	public DeviceScannerFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceScannerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getBinarySwitch();
		getRunState();
		getAutoDocumentFeeder();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.autoDocumentFeeder != null) {
			setAutoDocumentFeeder(this.autoDocumentFeeder);
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
	private RunStateFlexContainer runState;
		
	public void setRunState(RunStateFlexContainer runState) {
		this.runState = runState;
		getFlexContainerOrContainerOrSubscription().add(runState);
	}
	
	public RunStateFlexContainer getRunState() {
		this.runState = (RunStateFlexContainer) getResourceByName(RunStateFlexContainer.SHORT_NAME);
		return runState;
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
	
}
