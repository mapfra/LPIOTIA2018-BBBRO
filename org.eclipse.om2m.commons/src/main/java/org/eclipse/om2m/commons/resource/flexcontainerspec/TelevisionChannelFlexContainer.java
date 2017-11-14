/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : TelevisionChannel

This ModuleClass provides capabilities to set and get channels  of a device that has a channel list.

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


@XmlRootElement(name = TelevisionChannelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = TelevisionChannelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class TelevisionChannelFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "televisionChannel";
	public static final String SHORT_NAME = "telCl";
	
	public TelevisionChannelFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + TelevisionChannelFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getUpChannel();
		getDownChannel();
	}
	
	public void finalizeDeserialization() {
		if (this.upChannel != null) {
			setUpChannel(upChannel);
		}
		if (this.downChannel != null) {
			setDownChannel(downChannel);
		}
	}
	
	@XmlElement(name=UpChannelFlexContainer.SHORT_NAME, required=true, type=UpChannelFlexContainer.class)
	private UpChannelFlexContainer upChannel;
	
	
	public void setUpChannel(UpChannelFlexContainer upChannel) {
		this.upChannel = upChannel;
		getFlexContainerOrContainerOrSubscription().add(upChannel);
	}
	
	public UpChannelFlexContainer getUpChannel() {
		this.upChannel = (UpChannelFlexContainer) getResourceByName(UpChannelFlexContainer.SHORT_NAME);
		return upChannel;
	}
	
	@XmlElement(name=DownChannelFlexContainer.SHORT_NAME, required=true, type=DownChannelFlexContainer.class)
	private DownChannelFlexContainer downChannel;
	
	
	public void setDownChannel(DownChannelFlexContainer downChannel) {
		this.downChannel = downChannel;
		getFlexContainerOrContainerOrSubscription().add(downChannel);
	}
	
	public DownChannelFlexContainer getDownChannel() {
		this.downChannel = (DownChannelFlexContainer) getResourceByName(DownChannelFlexContainer.SHORT_NAME);
		return downChannel;
	}
	
}