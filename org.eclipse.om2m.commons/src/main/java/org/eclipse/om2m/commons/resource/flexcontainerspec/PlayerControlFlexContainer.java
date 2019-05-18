/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : PlayerControl

This ModuleClass provides capabilities to control and monitor the operational modes of a media player functionality.

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = PlayerControlFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = PlayerControlFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class PlayerControlFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "playerControl";
	public static final String SHORT_NAME = "plaCl";
		
	public PlayerControlFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + PlayerControlFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute currentPlayerModeName = new CustomAttribute();
		currentPlayerModeName.setLongName("currentPlayerModeName");
		currentPlayerModeName.setShortName("cPMNe");
		currentPlayerModeName.setType("xs:string");
		getCustomAttributes().add(currentPlayerModeName);
		CustomAttribute speedFactor = new CustomAttribute();
		speedFactor.setLongName("speedFactor");
		speedFactor.setShortName("speFr");
		speedFactor.setType("xs:float");
		getCustomAttributes().add(speedFactor);
		CustomAttribute currentPlayerMode = new CustomAttribute();
		currentPlayerMode.setLongName("currentPlayerMode");
		currentPlayerMode.setShortName("cuPMe");
		currentPlayerMode.setType("hd:enumPlayerMode");
		getCustomAttributes().add(currentPlayerMode);
		CustomAttribute supportedPlayerModes = new CustomAttribute();
		supportedPlayerModes.setLongName("supportedPlayerModes");
		supportedPlayerModes.setShortName("suPMs");
		supportedPlayerModes.setType("[hd:enumPlayerMode]");
		getCustomAttributes().add(supportedPlayerModes);
	}


	@XmlElement(name=NextTrackFlexContainer.SHORT_NAME, required=true, type=NextTrackFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private NextTrackFlexContainer nextTrack;
	
	public NextTrackFlexContainer getNextTrack() {
		this.nextTrack = (NextTrackFlexContainer) getResourceByName(NextTrackFlexContainer.SHORT_NAME);
		return nextTrack;
	}
	
	public void setNextTrack(NextTrackFlexContainer newAction) {
		this.nextTrack = newAction;
		getFlexContainerOrContainerOrSubscription().add(nextTrack);
	}

	@XmlElement(name=PreviousTrackFlexContainer.SHORT_NAME, required=true, type=PreviousTrackFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private PreviousTrackFlexContainer previousTrack;
	
	public PreviousTrackFlexContainer getPreviousTrack() {
		this.previousTrack = (PreviousTrackFlexContainer) getResourceByName(PreviousTrackFlexContainer.SHORT_NAME);
		return previousTrack;
	}
	
	public void setPreviousTrack(PreviousTrackFlexContainer newAction) {
		this.previousTrack = newAction;
		getFlexContainerOrContainerOrSubscription().add(previousTrack);
	}
		
	public void finalizeSerialization() {
		getNextTrack();
		getPreviousTrack();
	}
	
	public void finalizeDeserialization() {
		if (this.nextTrack != null) {
			setNextTrack(this.nextTrack);
		}
		if (this.previousTrack != null) {
			setPreviousTrack(this.previousTrack);
		}
	}
	
}
