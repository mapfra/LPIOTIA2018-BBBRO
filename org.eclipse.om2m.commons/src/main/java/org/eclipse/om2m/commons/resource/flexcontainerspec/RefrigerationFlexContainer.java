/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : Refrigeration

This ModuleClass provides capabilities for a refrigeration function. 

Created: 2018-06-29 17:19:53
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = RefrigerationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = RefrigerationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class RefrigerationFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "refrigeration";
	public static final String SHORT_NAME = "refrn";
		
	public RefrigerationFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + RefrigerationFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute rapidCool = new CustomAttribute();
		rapidCool.setLongName("rapidCool");
		rapidCool.setShortName("rapCl");
		rapidCool.setType("xs:boolean");
		getCustomAttributes().add(rapidCool);
		CustomAttribute defrost = new CustomAttribute();
		defrost.setLongName("defrost");
		defrost.setShortName("defrt");
		defrost.setType("xs:boolean");
		getCustomAttributes().add(defrost);
		CustomAttribute rapidFreeze = new CustomAttribute();
		rapidFreeze.setLongName("rapidFreeze");
		rapidFreeze.setShortName("rapFe");
		rapidFreeze.setType("xs:boolean");
		getCustomAttributes().add(rapidFreeze);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
