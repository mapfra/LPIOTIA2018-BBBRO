/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : AudioVolume

This ModuleClass provides capabilities to control and monitor volume.

Created: 2018-06-29 17:19:51
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = AudioVolumeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = AudioVolumeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class AudioVolumeFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "audioVolume";
	public static final String SHORT_NAME = "audVe";
		
	public AudioVolumeFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + AudioVolumeFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute volumePercentage = new CustomAttribute();
		volumePercentage.setLongName("volumePercentage");
		volumePercentage.setShortName("volPe");
		volumePercentage.setType("xs:integer");
		getCustomAttributes().add(volumePercentage);
		CustomAttribute stepValue = new CustomAttribute();
		stepValue.setLongName("stepValue");
		stepValue.setShortName("steVe");
		stepValue.setType("xs:integer");
		getCustomAttributes().add(stepValue);
		CustomAttribute maxValue = new CustomAttribute();
		maxValue.setLongName("maxValue");
		maxValue.setShortName("maxVe");
		maxValue.setType("xs:integer");
		getCustomAttributes().add(maxValue);
		CustomAttribute muteEnabled = new CustomAttribute();
		muteEnabled.setLongName("muteEnabled");
		muteEnabled.setShortName("mutEd");
		muteEnabled.setType("xs:boolean");
		getCustomAttributes().add(muteEnabled);
	}


	@XmlElement(name=UpVolumeFlexContainer.SHORT_NAME, required=true, type=UpVolumeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private UpVolumeFlexContainer upVolume;
	
	public UpVolumeFlexContainer getUpVolume() {
		this.upVolume = (UpVolumeFlexContainer) getResourceByName(UpVolumeFlexContainer.SHORT_NAME);
		return upVolume;
	}
	
	public void setUpVolume(UpVolumeFlexContainer newAction) {
		this.upVolume = newAction;
		getFlexContainerOrContainerOrSubscription().add(upVolume);
	}

	@XmlElement(name=DownVolumeFlexContainer.SHORT_NAME, required=true, type=DownVolumeFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private DownVolumeFlexContainer downVolume;
	
	public DownVolumeFlexContainer getDownVolume() {
		this.downVolume = (DownVolumeFlexContainer) getResourceByName(DownVolumeFlexContainer.SHORT_NAME);
		return downVolume;
	}
	
	public void setDownVolume(DownVolumeFlexContainer newAction) {
		this.downVolume = newAction;
		getFlexContainerOrContainerOrSubscription().add(downVolume);
	}
		
	public void finalizeSerialization() {
		getUpVolume();
		getDownVolume();
	}
	
	public void finalizeDeserialization() {
		if (this.upVolume != null) {
			setUpVolume(this.upVolume);
		}
		if (this.downVolume != null) {
			setDownVolume(this.downVolume);
		}
	}
	
}
