/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : BinaryObject

This ModuleClass describes the handling of a binary object (blob). 

Created: 2018-06-29 17:19:51
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = BinaryObjectFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = BinaryObjectFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class BinaryObjectFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "binaryObject";
	public static final String SHORT_NAME = "binOt";
		
	public BinaryObjectFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + BinaryObjectFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute size = new CustomAttribute();
		size.setLongName("size");
		size.setShortName("size");
		size.setType("xs:integer");
		getCustomAttributes().add(size);
		CustomAttribute hash = new CustomAttribute();
		hash.setLongName("hash");
		hash.setShortName("hash");
		hash.setType("xs:string");
		getCustomAttributes().add(hash);
		CustomAttribute object = new CustomAttribute();
		object.setLongName("object");
		object.setShortName("objet");
		object.setType("xs:string");
		getCustomAttributes().add(object);
		CustomAttribute objectType = new CustomAttribute();
		objectType.setLongName("objectType");
		objectType.setShortName("objTe");
		objectType.setType("xs:string");
		getCustomAttributes().add(objectType);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
