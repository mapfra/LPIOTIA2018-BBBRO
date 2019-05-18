/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : ThreeDPrinter

This ModuleClass describes capabilities of a 3D printer.

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

@XmlRootElement(name = ThreeDPrinterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ThreeDPrinterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class ThreeDPrinterFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "threeDPrinter";
	public static final String SHORT_NAME = "thDPr";
		
	public ThreeDPrinterFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + ThreeDPrinterFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute printSizeY = new CustomAttribute();
		printSizeY.setLongName("printSizeY");
		printSizeY.setShortName("priSY");
		printSizeY.setType("xs:float");
		getCustomAttributes().add(printSizeY);
		CustomAttribute printType = new CustomAttribute();
		printType.setLongName("printType");
		printType.setShortName("priTe");
		printType.setType("hd:enum3DPrinterTechnology");
		getCustomAttributes().add(printType);
		CustomAttribute printSizeX = new CustomAttribute();
		printSizeX.setLongName("printSizeX");
		printSizeX.setShortName("priSX");
		printSizeX.setType("xs:float");
		getCustomAttributes().add(printSizeX);
		CustomAttribute memorySize = new CustomAttribute();
		memorySize.setLongName("memorySize");
		memorySize.setShortName("memSe");
		memorySize.setType("xs:float");
		getCustomAttributes().add(memorySize);
		CustomAttribute printSizeZ = new CustomAttribute();
		printSizeZ.setLongName("printSizeZ");
		printSizeZ.setShortName("priSZ");
		printSizeZ.setType("xs:float");
		getCustomAttributes().add(printSizeZ);
		CustomAttribute network = new CustomAttribute();
		network.setLongName("network");
		network.setShortName("netwk");
		network.setType("xs:boolean");
		getCustomAttributes().add(network);
	}


	@XmlElement(name=Start3DprintFlexContainer.SHORT_NAME, required=true, type=Start3DprintFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private Start3DprintFlexContainer start3Dprint;
	
	public Start3DprintFlexContainer getStart3Dprint() {
		this.start3Dprint = (Start3DprintFlexContainer) getResourceByName(Start3DprintFlexContainer.SHORT_NAME);
		return start3Dprint;
	}
	
	public void setStart3Dprint(Start3DprintFlexContainer newAction) {
		this.start3Dprint = newAction;
		getFlexContainerOrContainerOrSubscription().add(start3Dprint);
	}

	@XmlElement(name=Stop3DprintFlexContainer.SHORT_NAME, required=true, type=Stop3DprintFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private Stop3DprintFlexContainer stop3Dprint;
	
	public Stop3DprintFlexContainer getStop3Dprint() {
		this.stop3Dprint = (Stop3DprintFlexContainer) getResourceByName(Stop3DprintFlexContainer.SHORT_NAME);
		return stop3Dprint;
	}
	
	public void setStop3Dprint(Stop3DprintFlexContainer newAction) {
		this.stop3Dprint = newAction;
		getFlexContainerOrContainerOrSubscription().add(stop3Dprint);
	}
		
	public void finalizeSerialization() {
		getStart3Dprint();
		getStop3Dprint();
	}
	
	public void finalizeDeserialization() {
		if (this.start3Dprint != null) {
			setStart3Dprint(this.start3Dprint);
		}
		if (this.stop3Dprint != null) {
			setStop3Dprint(this.stop3Dprint);
		}
	}
	
}
