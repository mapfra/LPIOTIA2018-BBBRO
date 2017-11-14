/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceNumberDevice



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


@XmlRootElement(name = DeviceNumberDeviceFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceNumberDeviceFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceNumberDeviceFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceNumberDevice";
	public static final String SHORT_NAME = "deNDe";
	
	public DeviceNumberDeviceFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceNumberDeviceFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getNumberValue();
	}
	
	public void finalizeDeserialization() {
		if (this.numberValue != null) {
			setNumberValue(this.numberValue);
		}
	}
	
	@XmlElement(name="numVe", required=true, type=NumberValueFlexContainer.class)
	private NumberValueFlexContainer numberValue;
	
	
	public void setNumberValue(NumberValueFlexContainer numberValue) {
		this.numberValue = numberValue;
		getFlexContainerOrContainerOrSubscription().add(numberValue);
	}
	
	public NumberValueFlexContainer getNumberValue() {
		this.numberValue = (NumberValueFlexContainer) getResourceByName(NumberValueFlexContainer.SHORT_NAME);
		return numberValue;
	}
	
}