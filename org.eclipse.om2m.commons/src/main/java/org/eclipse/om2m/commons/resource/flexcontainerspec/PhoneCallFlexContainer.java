/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : PhoneCall

This ModuleClass provides the capability get or set the caller and receipient IDs as well as to initate and terminate a call.

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

@XmlRootElement(name = PhoneCallFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = PhoneCallFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class PhoneCallFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "phoneCall";
	public static final String SHORT_NAME = "phoCl";
		
	public PhoneCallFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + PhoneCallFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute recipientID = new CustomAttribute();
		recipientID.setLongName("recipientID");
		recipientID.setShortName("recID");
		recipientID.setType("xs:string");
		getCustomAttributes().add(recipientID);
		CustomAttribute callerID = new CustomAttribute();
		callerID.setLongName("callerID");
		callerID.setShortName("calID");
		callerID.setType("xs:string");
		getCustomAttributes().add(callerID);
		CustomAttribute callState = new CustomAttribute();
		callState.setLongName("callState");
		callState.setShortName("calSe");
		callState.setType("hd:enumCallState");
		getCustomAttributes().add(callState);
	}


	@XmlElement(name=CallFlexContainer.SHORT_NAME, required=true, type=CallFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private CallFlexContainer call;
	
	public CallFlexContainer getCall() {
		this.call = (CallFlexContainer) getResourceByName(CallFlexContainer.SHORT_NAME);
		return call;
	}
	
	public void setCall(CallFlexContainer newAction) {
		this.call = newAction;
		getFlexContainerOrContainerOrSubscription().add(call);
	}

	@XmlElement(name=AnswerFlexContainer.SHORT_NAME, required=true, type=AnswerFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AnswerFlexContainer answer;
	
	public AnswerFlexContainer getAnswer() {
		this.answer = (AnswerFlexContainer) getResourceByName(AnswerFlexContainer.SHORT_NAME);
		return answer;
	}
	
	public void setAnswer(AnswerFlexContainer newAction) {
		this.answer = newAction;
		getFlexContainerOrContainerOrSubscription().add(answer);
	}

	@XmlElement(name=HangupFlexContainer.SHORT_NAME, required=true, type=HangupFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private HangupFlexContainer hangup;
	
	public HangupFlexContainer getHangup() {
		this.hangup = (HangupFlexContainer) getResourceByName(HangupFlexContainer.SHORT_NAME);
		return hangup;
	}
	
	public void setHangup(HangupFlexContainer newAction) {
		this.hangup = newAction;
		getFlexContainerOrContainerOrSubscription().add(hangup);
	}
		
	public void finalizeSerialization() {
		getCall();
		getAnswer();
		getHangup();
	}
	
	public void finalizeDeserialization() {
		if (this.call != null) {
			setCall(this.call);
		}
		if (this.answer != null) {
			setAnswer(this.answer);
		}
		if (this.hangup != null) {
			setHangup(this.hangup);
		}
	}
	
}
