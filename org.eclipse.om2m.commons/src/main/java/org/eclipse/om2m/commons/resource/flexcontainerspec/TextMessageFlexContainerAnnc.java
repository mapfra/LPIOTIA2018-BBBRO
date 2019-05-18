/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : TextMessageAnnc

This ModuleClass provides capabilities to set and get a text message.

Created: 2018-06-29 17:19:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = TextMessageFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = TextMessageFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class TextMessageFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "textMessageAnnc";
	public static final String SHORT_NAME = "texMeAnnc";
	
	public TextMessageFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass." + TextMessageFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
		
	public void finalizeSerialization() {
		getResetTextMessageAnnc();	  
	}
	
	public void finalizeDeserialization() {
		if (this.resetTextMessageAnnc != null){
			setResetTextMessageAnnc(resetTextMessageAnnc);	  
		}
	}
	
	@XmlElement(name=ResetTextMessageFlexContainerAnnc.SHORT_NAME, required=true, type=ResetTextMessageFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ResetTextMessageFlexContainerAnnc resetTextMessageAnnc;	
	
	public void setResetTextMessageAnnc(ResetTextMessageFlexContainerAnnc resetTextMessageAnnc) {
		this.resetTextMessageAnnc = resetTextMessageAnnc;
		getFlexContainerOrContainerOrSubscription().add(resetTextMessageAnnc);
	}
	
	public ResetTextMessageFlexContainerAnnc getResetTextMessageAnnc() {
		this.resetTextMessageAnnc = (ResetTextMessageFlexContainerAnnc) getResourceByName(ResetTextMessageFlexContainerAnnc.SHORT_NAME);
		return resetTextMessageAnnc;
	}
	
}
