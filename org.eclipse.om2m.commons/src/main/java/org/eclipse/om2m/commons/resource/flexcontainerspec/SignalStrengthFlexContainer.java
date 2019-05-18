/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : SignalStrength

This ModuleClass provides the capability to monitor the strength of the signal.

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = SignalStrengthFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = SignalStrengthFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class SignalStrengthFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "signalStrength";
	public static final String SHORT_NAME = "sigSh";
		
	public SignalStrengthFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + SignalStrengthFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute rssi = new CustomAttribute();
		rssi.setLongName("rssi");
		rssi.setShortName("rssi");
		rssi.setType("xs:float");
		getCustomAttributes().add(rssi);
		CustomAttribute lqi = new CustomAttribute();
		lqi.setLongName("lqi");
		lqi.setShortName("lqi");
		lqi.setType("xs:integer");
		getCustomAttributes().add(lqi);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
