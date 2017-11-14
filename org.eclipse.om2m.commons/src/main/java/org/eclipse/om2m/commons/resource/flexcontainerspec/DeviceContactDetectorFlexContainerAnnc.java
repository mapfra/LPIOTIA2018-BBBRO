/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceContactDetectorAnnc

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


@XmlRootElement(name = DeviceContactDetectorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceContactDetectorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceContactDetectorFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceContactDetectorAnnc";
	public static final String SHORT_NAME = "deCDrAnnc";
	
	public DeviceContactDetectorFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceContactDetectorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getContactSensor();
		getContactSensorAnnc();
	}
	
	public void finalizeDeserialization() {
		if (this.contactSensor != null) {
			setContactSensor(this.contactSensor);
		}
		if (this.contactSensorAnnc != null) {
			setContactSensorAnnc(this.contactSensorAnnc);
			}
		
	}
	
	@XmlElement(name="conSr", required=true, type=ContactSensorFlexContainerAnnc.class)
	private ContactSensorFlexContainer contactSensor;
	
	
	public void setContactSensor(ContactSensorFlexContainer contactSensor) {
		this.contactSensor = contactSensor;
		getFlexContainerOrContainerOrSubscription().add(contactSensor);
	}
	
	public ContactSensorFlexContainer getContactSensor() {
		this.contactSensor = (ContactSensorFlexContainer) getResourceByName(ContactSensorFlexContainer.SHORT_NAME);
		return contactSensor;
	}
	
	@XmlElement(name="conSrAnnc", required=true, type=ContactSensorFlexContainerAnnc.class)
	private ContactSensorFlexContainerAnnc contactSensorAnnc;
	
	
	public void setContactSensorAnnc(ContactSensorFlexContainerAnnc contactSensorAnnc) {
		this.contactSensorAnnc = contactSensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(contactSensorAnnc);
	}
	
	public ContactSensorFlexContainerAnnc getContactSensorAnnc() {
		this.contactSensorAnnc = (ContactSensorFlexContainerAnnc) getResourceByName(ContactSensorFlexContainerAnnc.SHORT_NAME);
		return contactSensorAnnc;
	}
	
}