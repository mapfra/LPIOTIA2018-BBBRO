/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : AirFlow

This ModuleClass provides capabilities for controlling the air flow of a device.

Created: 2018-06-29 17:19:51
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = AirFlowFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = AirFlowFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class AirFlowFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "airFlow";
	public static final String SHORT_NAME = "airFw";
		
	public AirFlowFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + AirFlowFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute horizontalDirection = new CustomAttribute();
		horizontalDirection.setLongName("horizontalDirection");
		horizontalDirection.setShortName("horDn");
		horizontalDirection.setType("hd:enumHorizontalDirection");
		getCustomAttributes().add(horizontalDirection);
		CustomAttribute verticalDirection = new CustomAttribute();
		verticalDirection.setLongName("verticalDirection");
		verticalDirection.setShortName("verDn");
		verticalDirection.setType("hd:enumVerticalDirection");
		getCustomAttributes().add(verticalDirection);
		CustomAttribute supportedHorizontalDirection = new CustomAttribute();
		supportedHorizontalDirection.setLongName("supportedHorizontalDirection");
		supportedHorizontalDirection.setShortName("suHDn");
		supportedHorizontalDirection.setType("[hd:enumHorizontalDirection]");
		getCustomAttributes().add(supportedHorizontalDirection);
		CustomAttribute maxSpeed = new CustomAttribute();
		maxSpeed.setLongName("maxSpeed");
		maxSpeed.setShortName("maxSd");
		maxSpeed.setType("xs:integer");
		getCustomAttributes().add(maxSpeed);
		CustomAttribute supportedVerticalDirection = new CustomAttribute();
		supportedVerticalDirection.setLongName("supportedVerticalDirection");
		supportedVerticalDirection.setShortName("suVDn");
		supportedVerticalDirection.setType("[hd:enumVerticalDirection]");
		getCustomAttributes().add(supportedVerticalDirection);
		CustomAttribute minSpeed = new CustomAttribute();
		minSpeed.setLongName("minSpeed");
		minSpeed.setShortName("minSd");
		minSpeed.setType("xs:integer");
		getCustomAttributes().add(minSpeed);
		CustomAttribute speed = new CustomAttribute();
		speed.setLongName("speed");
		speed.setShortName("speed");
		speed.setType("xs:integer");
		getCustomAttributes().add(speed);
		CustomAttribute automode = new CustomAttribute();
		automode.setLongName("automode");
		automode.setShortName("autoe");
		automode.setType("xs:boolean");
		getCustomAttributes().add(automode);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
