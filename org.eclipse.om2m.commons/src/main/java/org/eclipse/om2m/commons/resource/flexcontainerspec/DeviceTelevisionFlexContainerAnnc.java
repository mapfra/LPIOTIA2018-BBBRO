/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceTelevisionAnnc

A television (TV) is a home appliance used to show audio and visual content such as broadcasting programs and network streaming. This TV information model provides capabilities to control and monitor TV specific resources.

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

@XmlRootElement(name = DeviceTelevisionFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceTelevisionFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceTelevisionFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceTelevisionAnnc";
	public static final String SHORT_NAME = "devTnAnnc";
	
	public DeviceTelevisionFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceTelevisionFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getAudioVolume();
		getAudioVolumeAnnc();
		getTelevisionChannel();
		getTelevisionChannelAnnc();
		getPlayerControl();
		getPlayerControlAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.binarySwitchAnnc != null) {
			setBinarySwitchAnnc(this.binarySwitchAnnc);
		}
		if (this.audioVolume != null) {
			setAudioVolume(this.audioVolume);
		}
		if (this.audioVolumeAnnc != null) {
			setAudioVolumeAnnc(this.audioVolumeAnnc);
		}
		if (this.televisionChannel != null) {
			setTelevisionChannel(this.televisionChannel);
		}
		if (this.televisionChannelAnnc != null) {
			setTelevisionChannelAnnc(this.televisionChannelAnnc);
		}
		if (this.playerControl != null) {
			setPlayerControl(this.playerControl);
		}
		if (this.playerControlAnnc != null) {
			setPlayerControlAnnc(this.playerControlAnnc);
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
	
	@XmlElement(name=BinarySwitchFlexContainerAnnc.SHORT_NAME, required=true, type=BinarySwitchFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BinarySwitchFlexContainerAnnc binarySwitchAnnc;
		
	public void setBinarySwitchAnnc(BinarySwitchFlexContainerAnnc binarySwitchAnnc) {
		this.binarySwitchAnnc = binarySwitchAnnc;
		getFlexContainerOrContainerOrSubscription().add(binarySwitchAnnc);
	}
	
	public BinarySwitchFlexContainerAnnc getBinarySwitchAnnc() {
		this.binarySwitchAnnc = (BinarySwitchFlexContainerAnnc) getResourceByName(BinarySwitchFlexContainerAnnc.SHORT_NAME);
		return binarySwitchAnnc;
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
	
	@XmlElement(name=AudioVolumeFlexContainerAnnc.SHORT_NAME, required=true, type=AudioVolumeFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AudioVolumeFlexContainerAnnc audioVolumeAnnc;
		
	public void setAudioVolumeAnnc(AudioVolumeFlexContainerAnnc audioVolumeAnnc) {
		this.audioVolumeAnnc = audioVolumeAnnc;
		getFlexContainerOrContainerOrSubscription().add(audioVolumeAnnc);
	}
	
	public AudioVolumeFlexContainerAnnc getAudioVolumeAnnc() {
		this.audioVolumeAnnc = (AudioVolumeFlexContainerAnnc) getResourceByName(AudioVolumeFlexContainerAnnc.SHORT_NAME);
		return audioVolumeAnnc;
	}
	
	@XmlElement(name=TelevisionChannelFlexContainer.SHORT_NAME, required=true, type=TelevisionChannelFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TelevisionChannelFlexContainer televisionChannel;
		
	public void setTelevisionChannel(TelevisionChannelFlexContainer televisionChannel) {
		this.televisionChannel = televisionChannel;
		getFlexContainerOrContainerOrSubscription().add(televisionChannel);
	}
	
	public TelevisionChannelFlexContainer getTelevisionChannel() {
		this.televisionChannel = (TelevisionChannelFlexContainer) getResourceByName(TelevisionChannelFlexContainer.SHORT_NAME);
		return televisionChannel;
	}
	
	@XmlElement(name=TelevisionChannelFlexContainerAnnc.SHORT_NAME, required=true, type=TelevisionChannelFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private TelevisionChannelFlexContainerAnnc televisionChannelAnnc;
		
	public void setTelevisionChannelAnnc(TelevisionChannelFlexContainerAnnc televisionChannelAnnc) {
		this.televisionChannelAnnc = televisionChannelAnnc;
		getFlexContainerOrContainerOrSubscription().add(televisionChannelAnnc);
	}
	
	public TelevisionChannelFlexContainerAnnc getTelevisionChannelAnnc() {
		this.televisionChannelAnnc = (TelevisionChannelFlexContainerAnnc) getResourceByName(TelevisionChannelFlexContainerAnnc.SHORT_NAME);
		return televisionChannelAnnc;
	}
	
	@XmlElement(name=PlayerControlFlexContainer.SHORT_NAME, required=true, type=PlayerControlFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private PlayerControlFlexContainer playerControl;
		
	public void setPlayerControl(PlayerControlFlexContainer playerControl) {
		this.playerControl = playerControl;
		getFlexContainerOrContainerOrSubscription().add(playerControl);
	}
	
	public PlayerControlFlexContainer getPlayerControl() {
		this.playerControl = (PlayerControlFlexContainer) getResourceByName(PlayerControlFlexContainer.SHORT_NAME);
		return playerControl;
	}
	
	@XmlElement(name=PlayerControlFlexContainerAnnc.SHORT_NAME, required=true, type=PlayerControlFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private PlayerControlFlexContainerAnnc playerControlAnnc;
		
	public void setPlayerControlAnnc(PlayerControlFlexContainerAnnc playerControlAnnc) {
		this.playerControlAnnc = playerControlAnnc;
		getFlexContainerOrContainerOrSubscription().add(playerControlAnnc);
	}
	
	public PlayerControlFlexContainerAnnc getPlayerControlAnnc() {
		this.playerControlAnnc = (PlayerControlFlexContainerAnnc) getResourceByName(PlayerControlFlexContainerAnnc.SHORT_NAME);
		return playerControlAnnc;
	}
	
}
