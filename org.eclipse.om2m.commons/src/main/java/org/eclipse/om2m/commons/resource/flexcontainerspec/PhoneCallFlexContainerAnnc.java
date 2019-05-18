/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : PhoneCallAnnc

This ModuleClass provides the capability get or set the caller and receipient IDs as well as to initate and terminate a call.

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = PhoneCallFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = PhoneCallFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class PhoneCallFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "phoneCallAnnc";
	public static final String SHORT_NAME = "phoClAnnc";
	
	public PhoneCallFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass." + PhoneCallFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
		
	public void finalizeSerialization() {
		getCallAnnc();	  
		getAnswerAnnc();	  
		getHangupAnnc();	  
	}
	
	public void finalizeDeserialization() {
		if (this.callAnnc != null){
			setCallAnnc(callAnnc);	  
		}
		if (this.answerAnnc != null){
			setAnswerAnnc(answerAnnc);	  
		}
		if (this.hangupAnnc != null){
			setHangupAnnc(hangupAnnc);	  
		}
	}
	
	@XmlElement(name=CallFlexContainerAnnc.SHORT_NAME, required=true, type=CallFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private CallFlexContainerAnnc callAnnc;	
	
	public void setCallAnnc(CallFlexContainerAnnc callAnnc) {
		this.callAnnc = callAnnc;
		getFlexContainerOrContainerOrSubscription().add(callAnnc);
	}
	
	public CallFlexContainerAnnc getCallAnnc() {
		this.callAnnc = (CallFlexContainerAnnc) getResourceByName(CallFlexContainerAnnc.SHORT_NAME);
		return callAnnc;
	}
	@XmlElement(name=AnswerFlexContainerAnnc.SHORT_NAME, required=true, type=AnswerFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AnswerFlexContainerAnnc answerAnnc;	
	
	public void setAnswerAnnc(AnswerFlexContainerAnnc answerAnnc) {
		this.answerAnnc = answerAnnc;
		getFlexContainerOrContainerOrSubscription().add(answerAnnc);
	}
	
	public AnswerFlexContainerAnnc getAnswerAnnc() {
		this.answerAnnc = (AnswerFlexContainerAnnc) getResourceByName(AnswerFlexContainerAnnc.SHORT_NAME);
		return answerAnnc;
	}
	@XmlElement(name=HangupFlexContainerAnnc.SHORT_NAME, required=true, type=HangupFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private HangupFlexContainerAnnc hangupAnnc;	
	
	public void setHangupAnnc(HangupFlexContainerAnnc hangupAnnc) {
		this.hangupAnnc = hangupAnnc;
		getFlexContainerOrContainerOrSubscription().add(hangupAnnc);
	}
	
	public HangupFlexContainerAnnc getHangupAnnc() {
		this.hangupAnnc = (HangupFlexContainerAnnc) getResourceByName(HangupFlexContainerAnnc.SHORT_NAME);
		return hangupAnnc;
	}
	
}
