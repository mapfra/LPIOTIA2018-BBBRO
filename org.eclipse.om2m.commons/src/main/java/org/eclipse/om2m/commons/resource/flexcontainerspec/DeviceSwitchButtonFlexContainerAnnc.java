/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceSwitchButtonAnnc

A SwitchButton is a device that provides button.

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


@XmlRootElement(name = DeviceSwitchButtonFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceSwitchButtonFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceSwitchButtonFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceSwitchButtonAnnc";
	public static final String SHORT_NAME = "deSBnAnnc";
	
	public DeviceSwitchButtonFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceSwitchButtonFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getButtonSwitch();
		getButtonSwitchAnnc();
	}
	
	public void finalizeDeserialization() {
		if (this.buttonSwitch != null) {
			setButtonSwitch(this.buttonSwitch);
		}
		if (this.buttonSwitchAnnc != null) {
			setButtonSwitchAnnc(this.buttonSwitchAnnc);
			}
		
	}
	
	@XmlElement(name="butSh", required=true, type=PushButtonFlexContainerAnnc.class)
	private PushButtonFlexContainer buttonSwitch;
	
	
	public void setButtonSwitch(PushButtonFlexContainer buttonSwitch) {
		this.buttonSwitch = buttonSwitch;
		getFlexContainerOrContainerOrSubscription().add(buttonSwitch);
	}
	
	public PushButtonFlexContainer getButtonSwitch() {
		this.buttonSwitch = (PushButtonFlexContainer) getResourceByName(PushButtonFlexContainer.SHORT_NAME);
		return buttonSwitch;
	}
	
	@XmlElement(name="butShAnnc", required=true, type=PushButtonFlexContainerAnnc.class)
	private PushButtonFlexContainerAnnc buttonSwitchAnnc;
	
	
	public void setButtonSwitchAnnc(PushButtonFlexContainerAnnc buttonSwitchAnnc) {
		this.buttonSwitchAnnc = buttonSwitchAnnc;
		getFlexContainerOrContainerOrSubscription().add(buttonSwitchAnnc);
	}
	
	public PushButtonFlexContainerAnnc getButtonSwitchAnnc() {
		this.buttonSwitchAnnc = (PushButtonFlexContainerAnnc) getResourceByName(PushButtonFlexContainerAnnc.SHORT_NAME);
		return buttonSwitchAnnc;
	}
	
}