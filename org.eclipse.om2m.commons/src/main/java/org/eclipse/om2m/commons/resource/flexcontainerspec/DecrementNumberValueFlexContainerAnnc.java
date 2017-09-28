/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Action : decrementNumberValue

Decrement the "numberValue" by the value of "step", down to the value of "minimum".

Created: 2017-09-28 17:26:40
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;


@XmlRootElement(name = DecrementNumberValueFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DecrementNumberValueFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DecrementNumberValueFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "decrementNumberValueAnnc";
	public static final String SHORT_NAME = "deNVeAnnc";
	
	public DecrementNumberValueFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass.numbervalue." + DecrementNumberValueFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}