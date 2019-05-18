/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceSwitch



Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceSwitchFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceSwitchFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceSwitchFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceSwitch";
	public static final String SHORT_NAME = "devSh";
	
	public DeviceSwitchFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceSwitchFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getPushButton();
	}
	
	public void finalizeDeserialization() {
		if (this.pushButton != null) {
			setPushButton(this.pushButton);
		}
	}

	@XmlElement(name=PushButtonFlexContainer.SHORT_NAME, required=true, type=PushButtonFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private PushButtonFlexContainer pushButton;
		
	public void setPushButton(PushButtonFlexContainer pushButton) {
		this.pushButton = pushButton;
		getFlexContainerOrContainerOrSubscription().add(pushButton);
	}
	
	public PushButtonFlexContainer getPushButton() {
		this.pushButton = (PushButtonFlexContainer) getResourceByName(PushButtonFlexContainer.SHORT_NAME);
		return pushButton;
	}
	
}
