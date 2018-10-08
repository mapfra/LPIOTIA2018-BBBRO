/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : MediaSelect

This ModuleClass provides capabilities to control and monitor media input and output of device such as TV or SetTopBox.

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = MediaSelectFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = MediaSelectFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class MediaSelectFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "mediaSelect";
	public static final String SHORT_NAME = "medSt";
		
	public MediaSelectFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + MediaSelectFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute supportedMediaSources = new CustomAttribute();
		supportedMediaSources.setLongName("supportedMediaSources");
		supportedMediaSources.setShortName("suMSs");
		supportedMediaSources.setType("[hd:enumSupportedMediaSource]");
		getCustomAttributes().add(supportedMediaSources);
		CustomAttribute mediaType = new CustomAttribute();
		mediaType.setLongName("mediaType");
		mediaType.setShortName("medTe");
		mediaType.setType("hd:enumSupportedMediaSource");
		getCustomAttributes().add(mediaType);
		CustomAttribute mediaID = new CustomAttribute();
		mediaID.setLongName("mediaID");
		mediaID.setShortName("medID");
		mediaID.setType("xs:integer");
		getCustomAttributes().add(mediaID);
		CustomAttribute mediaName = new CustomAttribute();
		mediaName.setLongName("mediaName");
		mediaName.setShortName("medNe");
		mediaName.setType("xs:string");
		getCustomAttributes().add(mediaName);
		CustomAttribute status = new CustomAttribute();
		status.setLongName("status");
		status.setShortName("sus");
		status.setType("xs:boolean");
		getCustomAttributes().add(status);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
