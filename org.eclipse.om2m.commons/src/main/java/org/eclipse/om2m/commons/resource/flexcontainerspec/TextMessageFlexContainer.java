/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : TextMessage

This ModuleClass provides capabilities to set and get a text message.

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

@XmlRootElement(name = TextMessageFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = TextMessageFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class TextMessageFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "textMessage";
	public static final String SHORT_NAME = "texMe";
		
	public TextMessageFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + TextMessageFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute textMessage = new CustomAttribute();
		textMessage.setLongName("textMessage");
		textMessage.setShortName("texMe");
		textMessage.setType("xs:string");
		getCustomAttributes().add(textMessage);
		CustomAttribute defaultValue = new CustomAttribute();
		defaultValue.setLongName("defaultValue");
		defaultValue.setShortName("defVe");
		defaultValue.setType("xs:string");
		getCustomAttributes().add(defaultValue);
		CustomAttribute minLength = new CustomAttribute();
		minLength.setLongName("minLength");
		minLength.setShortName("minLh");
		minLength.setType("xs:integer");
		getCustomAttributes().add(minLength);
		CustomAttribute messageEncoding = new CustomAttribute();
		messageEncoding.setLongName("messageEncoding");
		messageEncoding.setShortName("mesEg");
		messageEncoding.setType("xs:string");
		getCustomAttributes().add(messageEncoding);
		CustomAttribute supportedMessageValues = new CustomAttribute();
		supportedMessageValues.setLongName("supportedMessageValues");
		supportedMessageValues.setShortName("suMVs");
		supportedMessageValues.setType("[xs:string]");
		getCustomAttributes().add(supportedMessageValues);
		CustomAttribute maxLength = new CustomAttribute();
		maxLength.setLongName("maxLength");
		maxLength.setShortName("maxLh");
		maxLength.setType("xs:integer");
		getCustomAttributes().add(maxLength);
	}


	@XmlElement(name=ResetTextMessageFlexContainer.SHORT_NAME, required=true, type=ResetTextMessageFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ResetTextMessageFlexContainer resetTextMessage;
	
	public ResetTextMessageFlexContainer getResetTextMessage() {
		this.resetTextMessage = (ResetTextMessageFlexContainer) getResourceByName(ResetTextMessageFlexContainer.SHORT_NAME);
		return resetTextMessage;
	}
	
	public void setResetTextMessage(ResetTextMessageFlexContainer newAction) {
		this.resetTextMessage = newAction;
		getFlexContainerOrContainerOrSubscription().add(resetTextMessage);
	}
		
	public void finalizeSerialization() {
		getResetTextMessage();
	}
	
	public void finalizeDeserialization() {
		if (this.resetTextMessage != null) {
			setResetTextMessage(this.resetTextMessage);
		}
	}
	
}
