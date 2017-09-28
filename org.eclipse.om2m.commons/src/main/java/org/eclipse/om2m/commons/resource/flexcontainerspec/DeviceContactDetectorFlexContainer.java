/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceContactDetector

A ContactDetector is a device that trigger alarm when contact is lost.

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


@XmlRootElement(name = DeviceContactDetectorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceContactDetectorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceContactDetectorFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceContactDetector";
	public static final String SHORT_NAME = "deCDr";
	
	public DeviceContactDetectorFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceContactDetectorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getContactSensor();
	}
	
	public void finalizeDeserialization() {
		if (this.contactSensor != null) {
			setContactSensor(this.contactSensor);
		}
	}
	
	@XmlElement(name="conSr", required=true, type=ContactSensorFlexContainer.class)
	private ContactSensorFlexContainer contactSensor;
	
	
	public void setContactSensor(ContactSensorFlexContainer contactSensor) {
		this.contactSensor = contactSensor;
		getFlexContainerOrContainerOrSubscription().add(contactSensor);
	}
	
	public ContactSensorFlexContainer getContactSensor() {
		this.contactSensor = (ContactSensorFlexContainer) getResourceByName(ContactSensorFlexContainer.SHORT_NAME);
		return contactSensor;
	}
	
}