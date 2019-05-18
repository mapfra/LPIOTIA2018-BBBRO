/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceAudioReceiverAnnc

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
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DeviceAudioReceiverFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceAudioReceiverFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceAudioReceiverFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceAudioReceiverAnnc";
	public static final String SHORT_NAME = "deARrAnnc";
	
	public DeviceAudioReceiverFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceAudioReceiverFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getAudioVolume();
		getAudioVolumeAnnc();
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
	
}
