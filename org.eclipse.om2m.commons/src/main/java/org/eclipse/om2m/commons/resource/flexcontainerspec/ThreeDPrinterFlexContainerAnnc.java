/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : ThreeDPrinterAnnc

This ModuleClass describes capabilities of a 3D printer.

Created: 2018-06-29 17:19:51
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = ThreeDPrinterFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ThreeDPrinterFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class ThreeDPrinterFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "threeDPrinterAnnc";
	public static final String SHORT_NAME = "thDPrAnnc";
	
	public ThreeDPrinterFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass." + ThreeDPrinterFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
		
	public void finalizeSerialization() {
		getStart3DprintAnnc();	  
		getStop3DprintAnnc();	  
	}
	
	public void finalizeDeserialization() {
		if (this.start3DprintAnnc != null){
			setStart3DprintAnnc(start3DprintAnnc);	  
		}
		if (this.stop3DprintAnnc != null){
			setStop3DprintAnnc(stop3DprintAnnc);	  
		}
	}
	
	@XmlElement(name=Start3DprintFlexContainerAnnc.SHORT_NAME, required=true, type=Start3DprintFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private Start3DprintFlexContainerAnnc start3DprintAnnc;	
	
	public void setStart3DprintAnnc(Start3DprintFlexContainerAnnc start3DprintAnnc) {
		this.start3DprintAnnc = start3DprintAnnc;
		getFlexContainerOrContainerOrSubscription().add(start3DprintAnnc);
	}
	
	public Start3DprintFlexContainerAnnc getStart3DprintAnnc() {
		this.start3DprintAnnc = (Start3DprintFlexContainerAnnc) getResourceByName(Start3DprintFlexContainerAnnc.SHORT_NAME);
		return start3DprintAnnc;
	}
	@XmlElement(name=Stop3DprintFlexContainerAnnc.SHORT_NAME, required=true, type=Stop3DprintFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private Stop3DprintFlexContainerAnnc stop3DprintAnnc;	
	
	public void setStop3DprintAnnc(Stop3DprintFlexContainerAnnc stop3DprintAnnc) {
		this.stop3DprintAnnc = stop3DprintAnnc;
		getFlexContainerOrContainerOrSubscription().add(stop3DprintAnnc);
	}
	
	public Stop3DprintFlexContainerAnnc getStop3DprintAnnc() {
		this.stop3DprintAnnc = (Stop3DprintFlexContainerAnnc) getResourceByName(Stop3DprintFlexContainerAnnc.SHORT_NAME);
		return stop3DprintAnnc;
	}
	
}
