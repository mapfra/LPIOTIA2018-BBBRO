/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.commons.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name=FlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name=FlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols")
public class FlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "flexContainer";
	public static final String SHORT_NAME = "fcnt";
	
	
	public FlexContainer() {
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}


	@Override
	public void finalizeSerialization() {
	}


	@Override
	public void finalizeDeserialization() {
	}

}
