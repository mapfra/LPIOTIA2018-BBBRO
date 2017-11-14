/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : NumberValue

This ModuleClass adds the functionalities to represent a number. It also has capabilities for controlled increment and decrement a counter. It can be used to present a number-related functionality in a technology where there is only a weak semantic specification of that functionality.

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


@XmlRootElement(name = NumberValueFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = NumberValueFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class NumberValueFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "numberValue";
	public static final String SHORT_NAME = "numVe";
	
	public NumberValueFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + NumberValueFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getDecrementNumberValue();
		getIncrementNumberValue();
		getResetNumberValue();
	}
	
	public void finalizeDeserialization() {
		if (this.decrementNumberValue != null) {
			setDecrementNumberValue(decrementNumberValue);
		}
		if (this.incrementNumberValue != null) {
			setIncrementNumberValue(incrementNumberValue);
		}
		if (this.resetNumberValue != null) {
			setResetNumberValue(resetNumberValue);
		}
	}
	
	@XmlElement(name=DecrementNumberValueFlexContainer.SHORT_NAME, required=true, type=DecrementNumberValueFlexContainer.class)
	private DecrementNumberValueFlexContainer decrementNumberValue;
	
	
	public void setDecrementNumberValue(DecrementNumberValueFlexContainer decrementNumberValue) {
		this.decrementNumberValue = decrementNumberValue;
		getFlexContainerOrContainerOrSubscription().add(decrementNumberValue);
	}
	
	public DecrementNumberValueFlexContainer getDecrementNumberValue() {
		this.decrementNumberValue = (DecrementNumberValueFlexContainer) getResourceByName(DecrementNumberValueFlexContainer.SHORT_NAME);
		return decrementNumberValue;
	}
	
	@XmlElement(name=IncrementNumberValueFlexContainer.SHORT_NAME, required=true, type=IncrementNumberValueFlexContainer.class)
	private IncrementNumberValueFlexContainer incrementNumberValue;
	
	
	public void setIncrementNumberValue(IncrementNumberValueFlexContainer incrementNumberValue) {
		this.incrementNumberValue = incrementNumberValue;
		getFlexContainerOrContainerOrSubscription().add(incrementNumberValue);
	}
	
	public IncrementNumberValueFlexContainer getIncrementNumberValue() {
		this.incrementNumberValue = (IncrementNumberValueFlexContainer) getResourceByName(IncrementNumberValueFlexContainer.SHORT_NAME);
		return incrementNumberValue;
	}
	
	@XmlElement(name=ResetNumberValueFlexContainer.SHORT_NAME, required=true, type=ResetNumberValueFlexContainer.class)
	private ResetNumberValueFlexContainer resetNumberValue;
	
	
	public void setResetNumberValue(ResetNumberValueFlexContainer resetNumberValue) {
		this.resetNumberValue = resetNumberValue;
		getFlexContainerOrContainerOrSubscription().add(resetNumberValue);
	}
	
	public ResetNumberValueFlexContainer getResetNumberValue() {
		this.resetNumberValue = (ResetNumberValueFlexContainer) getResourceByName(ResetNumberValueFlexContainer.SHORT_NAME);
		return resetNumberValue;
	}
	
}