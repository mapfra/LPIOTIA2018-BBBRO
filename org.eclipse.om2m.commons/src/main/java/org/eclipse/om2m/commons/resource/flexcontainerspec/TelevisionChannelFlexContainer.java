/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : TelevisionChannel

This ModuleClass provides capabilities to set and get channels of a device that has a channel list.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

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
		CustomAttribute previousChannel = new CustomAttribute();
		previousChannel.setLongName("previousChannel");
		previousChannel.setShortName("preCl");
		previousChannel.setType("xs:integer");
		getCustomAttributes().add(previousChannel);
		CustomAttribute availableChannels = new CustomAttribute();
		availableChannels.setLongName("availableChannels");
		availableChannels.setShortName("avaCs");
		availableChannels.setType("[xs:integer]");
		getCustomAttributes().add(availableChannels);
		CustomAttribute channelName = new CustomAttribute();
		channelName.setLongName("channelName");
		channelName.setShortName("chaNe");
		channelName.setType("xs:string");
		getCustomAttributes().add(channelName);
		CustomAttribute channelId = new CustomAttribute();
		channelId.setLongName("channelId");
		channelId.setShortName("chaId");
		channelId.setType("xs:integer");
		getCustomAttributes().add(channelId);
	}


	@XmlElement(name=UpChannelFlexContainer.SHORT_NAME, required=true, type=UpChannelFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private UpChannelFlexContainer upChannel;
	
	public UpChannelFlexContainer getUpChannel() {
		this.upChannel = (UpChannelFlexContainer) getResourceByName(UpChannelFlexContainer.SHORT_NAME);
		return upChannel;
	}
	
	public void setUpChannel(UpChannelFlexContainer newAction) {
		this.upChannel = newAction;
		getFlexContainerOrContainerOrSubscription().add(upChannel);
	}

	@XmlElement(name=DownChannelFlexContainer.SHORT_NAME, required=true, type=DownChannelFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private DownChannelFlexContainer downChannel;
	
	public DownChannelFlexContainer getDownChannel() {
		this.downChannel = (DownChannelFlexContainer) getResourceByName(DownChannelFlexContainer.SHORT_NAME);
		return downChannel;
	}
	
	public void setDownChannel(DownChannelFlexContainer newAction) {
		this.downChannel = newAction;
		getFlexContainerOrContainerOrSubscription().add(downChannel);
	}
		
	public void finalizeSerialization() {
		getUpChannel();
		getDownChannel();
	}
	
	public void finalizeDeserialization() {
		if (this.upChannel != null) {
			setUpChannel(this.upChannel);
		}
		if (this.downChannel != null) {
			setDownChannel(this.downChannel);
		}
	}
	
}
