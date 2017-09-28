/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : AudioVolume

This ModuleClass provides capabilities to control and monitor  volume.

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
	}
	
	public void finalizeSerialization() {
		getUpVolume();
		getDownVolume();
	}
	
	public void finalizeDeserialization() {
		if (this.upVolume != null) {
			setUpVolume(upVolume);
		}
		if (this.downVolume != null) {
			setDownVolume(downVolume);
		}
	}
	
	@XmlElement(name=UpVolumeFlexContainer.SHORT_NAME, required=true, type=UpVolumeFlexContainer.class)
	private UpVolumeFlexContainer upVolume;
	
	
	public void setUpVolume(UpVolumeFlexContainer upVolume) {
		this.upVolume = upVolume;
		getFlexContainerOrContainerOrSubscription().add(upVolume);
	}
	
	public UpVolumeFlexContainer getUpVolume() {
		this.upVolume = (UpVolumeFlexContainer) getResourceByName(UpVolumeFlexContainer.SHORT_NAME);
		return upVolume;
	}
	
	@XmlElement(name=DownVolumeFlexContainer.SHORT_NAME, required=true, type=DownVolumeFlexContainer.class)
	private DownVolumeFlexContainer downVolume;
	
	
	public void setDownVolume(DownVolumeFlexContainer downVolume) {
		this.downVolume = downVolume;
		getFlexContainerOrContainerOrSubscription().add(downVolume);
	}
	
	public DownVolumeFlexContainer getDownVolume() {
		this.downVolume = (DownVolumeFlexContainer) getResourceByName(DownVolumeFlexContainer.SHORT_NAME);
		return downVolume;
	}
	
}