/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceSmokeDetectorAnnc



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

@XmlRootElement(name = DeviceSmokeDetectorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceSmokeDetectorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceSmokeDetectorFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceSmokeDetectorAnnc";
	public static final String SHORT_NAME = "deSDrAnnc";
	
	public DeviceSmokeDetectorFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceSmokeDetectorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getSmokeSensor();
		getSmokeSensorAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.smokeSensor != null) {
			setSmokeSensor(this.smokeSensor);
		}
		if (this.smokeSensorAnnc != null) {
			setSmokeSensorAnnc(this.smokeSensorAnnc);
		}
	}

	@XmlElement(name=SmokeSensorFlexContainer.SHORT_NAME, required=true, type=SmokeSensorFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private SmokeSensorFlexContainer smokeSensor;
		
	public void setSmokeSensor(SmokeSensorFlexContainer smokeSensor) {
		this.smokeSensor = smokeSensor;
		getFlexContainerOrContainerOrSubscription().add(smokeSensor);
	}
	
	public SmokeSensorFlexContainer getSmokeSensor() {
		this.smokeSensor = (SmokeSensorFlexContainer) getResourceByName(SmokeSensorFlexContainer.SHORT_NAME);
		return smokeSensor;
	}
	
	@XmlElement(name=SmokeSensorFlexContainerAnnc.SHORT_NAME, required=true, type=SmokeSensorFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private SmokeSensorFlexContainerAnnc smokeSensorAnnc;
		
	public void setSmokeSensorAnnc(SmokeSensorFlexContainerAnnc smokeSensorAnnc) {
		this.smokeSensorAnnc = smokeSensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(smokeSensorAnnc);
	}
	
	public SmokeSensorFlexContainerAnnc getSmokeSensorAnnc() {
		this.smokeSensorAnnc = (SmokeSensorFlexContainerAnnc) getResourceByName(SmokeSensorFlexContainerAnnc.SHORT_NAME);
		return smokeSensorAnnc;
	}
	
}
