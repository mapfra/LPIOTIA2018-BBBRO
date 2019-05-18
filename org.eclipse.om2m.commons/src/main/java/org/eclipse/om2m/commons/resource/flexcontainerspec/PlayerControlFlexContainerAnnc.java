/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : PlayerControlAnnc

This ModuleClass provides capabilities to control and monitor the operational modes of a media player functionality.

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = PlayerControlFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = PlayerControlFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class PlayerControlFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "playerControlAnnc";
	public static final String SHORT_NAME = "plaClAnnc";
	
	public PlayerControlFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass." + PlayerControlFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
		
	public void finalizeSerialization() {
		getNextTrackAnnc();	  
		getPreviousTrackAnnc();	  
	}
	
	public void finalizeDeserialization() {
		if (this.nextTrackAnnc != null){
			setNextTrackAnnc(nextTrackAnnc);	  
		}
		if (this.previousTrackAnnc != null){
			setPreviousTrackAnnc(previousTrackAnnc);	  
		}
	}
	
	@XmlElement(name=NextTrackFlexContainerAnnc.SHORT_NAME, required=true, type=NextTrackFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private NextTrackFlexContainerAnnc nextTrackAnnc;	
	
	public void setNextTrackAnnc(NextTrackFlexContainerAnnc nextTrackAnnc) {
		this.nextTrackAnnc = nextTrackAnnc;
		getFlexContainerOrContainerOrSubscription().add(nextTrackAnnc);
	}
	
	public NextTrackFlexContainerAnnc getNextTrackAnnc() {
		this.nextTrackAnnc = (NextTrackFlexContainerAnnc) getResourceByName(NextTrackFlexContainerAnnc.SHORT_NAME);
		return nextTrackAnnc;
	}
	@XmlElement(name=PreviousTrackFlexContainerAnnc.SHORT_NAME, required=true, type=PreviousTrackFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private PreviousTrackFlexContainerAnnc previousTrackAnnc;	
	
	public void setPreviousTrackAnnc(PreviousTrackFlexContainerAnnc previousTrackAnnc) {
		this.previousTrackAnnc = previousTrackAnnc;
		getFlexContainerOrContainerOrSubscription().add(previousTrackAnnc);
	}
	
	public PreviousTrackFlexContainerAnnc getPreviousTrackAnnc() {
		this.previousTrackAnnc = (PreviousTrackFlexContainerAnnc) getResourceByName(PreviousTrackFlexContainerAnnc.SHORT_NAME);
		return previousTrackAnnc;
	}
	
}
