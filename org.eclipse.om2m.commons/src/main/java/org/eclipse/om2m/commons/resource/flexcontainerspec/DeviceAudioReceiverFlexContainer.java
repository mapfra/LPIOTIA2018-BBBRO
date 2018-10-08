/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceAudioReceiver

An audio receiver is a device that receives audio signals from a number of sources, processing them to drive speakers.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceAudioReceiverFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceAudioReceiverFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceAudioReceiverFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceAudioReceiver";
	public static final String SHORT_NAME = "deARr";
	
	public DeviceAudioReceiverFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceAudioReceiverFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getBinarySwitch();
		getAudioVolume();
		getMediaInput();
		getMediaOutput();
	}
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.audioVolume != null) {
			setAudioVolume(this.audioVolume);
		}
		if (this.mediaInput != null) {
			setMediaInput(this.mediaInput);
		}
		if (this.mediaOutput != null) {
			setMediaOutput(this.mediaOutput);
		}
	}

	@XmlElement(name=BinarySwitchFlexContainer.SHORT_NAME, required=true, type=BinarySwitchFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BinarySwitchFlexContainer binarySwitch;
		
	public void setBinarySwitch(BinarySwitchFlexContainer binarySwitch) {
		this.binarySwitch = binarySwitch;
		getFlexContainerOrContainerOrSubscription().add(binarySwitch);
	}
	
	public BinarySwitchFlexContainer getBinarySwitch() {
		this.binarySwitch = (BinarySwitchFlexContainer) getResourceByName(BinarySwitchFlexContainer.SHORT_NAME);
		return binarySwitch;
	}
	
	@XmlElement(name=AudioVolumeFlexContainer.SHORT_NAME, required=true, type=AudioVolumeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AudioVolumeFlexContainer audioVolume;
		
	public void setAudioVolume(AudioVolumeFlexContainer audioVolume) {
		this.audioVolume = audioVolume;
		getFlexContainerOrContainerOrSubscription().add(audioVolume);
	}
	
	public AudioVolumeFlexContainer getAudioVolume() {
		this.audioVolume = (AudioVolumeFlexContainer) getResourceByName(AudioVolumeFlexContainer.SHORT_NAME);
		return audioVolume;
	}
	
	@XmlElement(name=MediaSelectFlexContainer.SHORT_NAME, required=true, type=MediaSelectFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private MediaSelectFlexContainer mediaInput;
		
	public void setMediaInput(MediaSelectFlexContainer mediaInput) {
		this.mediaInput = mediaInput;
		getFlexContainerOrContainerOrSubscription().add(mediaInput);
	}
	
	public MediaSelectFlexContainer getMediaInput() {
		this.mediaInput = (MediaSelectFlexContainer) getResourceByName(MediaSelectFlexContainer.SHORT_NAME);
		return mediaInput;
	}
	
	@XmlElement(name=MediaSelectFlexContainer.SHORT_NAME, required=true, type=MediaSelectFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private MediaSelectFlexContainer mediaOutput;
		
	public void setMediaOutput(MediaSelectFlexContainer mediaOutput) {
		this.mediaOutput = mediaOutput;
		getFlexContainerOrContainerOrSubscription().add(mediaOutput);
	}
	
	public MediaSelectFlexContainer getMediaOutput() {
		this.mediaOutput = (MediaSelectFlexContainer) getResourceByName(MediaSelectFlexContainer.SHORT_NAME);
		return mediaOutput;
	}
	
}
