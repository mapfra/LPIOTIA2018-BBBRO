/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceContactDetectorAnnc



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

@XmlRootElement(name = DeviceContactDetectorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceContactDetectorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceContactDetectorFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceContactDetectorAnnc";
	public static final String SHORT_NAME = "dCDrAnnc";
	
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

	@XmlElement(name=ContactSensorFlexContainer.SHORT_NAME, required=true, type=ContactSensorFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ContactSensorFlexContainer contactSensor;
		
	public void setContactSensor(ContactSensorFlexContainer contactSensor) {
		this.contactSensor = contactSensor;
		getFlexContainerOrContainerOrSubscription().add(contactSensor);
	}
	
	public ContactSensorFlexContainer getContactSensor() {
		this.contactSensor = (ContactSensorFlexContainer) getResourceByName(ContactSensorFlexContainer.SHORT_NAME);
		return contactSensor;
	}
	
	@XmlElement(name=ContactSensorFlexContainerAnnc.SHORT_NAME, required=true, type=ContactSensorFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
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
