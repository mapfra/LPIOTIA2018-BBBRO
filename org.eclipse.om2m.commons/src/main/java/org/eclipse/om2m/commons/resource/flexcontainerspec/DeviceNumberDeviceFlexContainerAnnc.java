/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceNumberDeviceAnnc



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


@XmlRootElement(name = DeviceNumberDeviceFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceNumberDeviceFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceNumberDeviceFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceNumberDeviceAnnc";
	public static final String SHORT_NAME = "deNDeAnnc";
	
	public DeviceNumberDeviceFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceNumberDeviceFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getNumberValue();
		getNumberValueAnnc();
	}
	
	public void finalizeDeserialization() {
		if (this.numberValue != null) {
			setNumberValue(this.numberValue);
		}
		if (this.numberValueAnnc != null) {
			setNumberValueAnnc(this.numberValueAnnc);
			}
		
	}
	
	@XmlElement(name="numVe", required=true, type=NumberValueFlexContainerAnnc.class)
	private NumberValueFlexContainer numberValue;
	
	
	public void setNumberValue(NumberValueFlexContainer numberValue) {
		this.numberValue = numberValue;
		getFlexContainerOrContainerOrSubscription().add(numberValue);
	}
	
	public NumberValueFlexContainer getNumberValue() {
		this.numberValue = (NumberValueFlexContainer) getResourceByName(NumberValueFlexContainer.SHORT_NAME);
		return numberValue;
	}
	
	@XmlElement(name="numVeAnnc", required=true, type=NumberValueFlexContainerAnnc.class)
	private NumberValueFlexContainerAnnc numberValueAnnc;
	
	
	public void setNumberValueAnnc(NumberValueFlexContainerAnnc numberValueAnnc) {
		this.numberValueAnnc = numberValueAnnc;
		getFlexContainerOrContainerOrSubscription().add(numberValueAnnc);
	}
	
	public NumberValueFlexContainerAnnc getNumberValueAnnc() {
		this.numberValueAnnc = (NumberValueFlexContainerAnnc) getResourceByName(NumberValueFlexContainerAnnc.SHORT_NAME);
		return numberValueAnnc;
	}
	
}