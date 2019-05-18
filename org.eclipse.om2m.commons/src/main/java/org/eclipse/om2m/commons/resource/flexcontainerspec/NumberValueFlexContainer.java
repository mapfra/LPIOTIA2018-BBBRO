/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : NumberValue

This ModuleClass provides the capabilities to represent a number. It also has capabilities for controlled increment and decrement a counter. It can be used to present a number-related functionality in a technology where there is only a weak semantic specification of that functionality.

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

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
		CustomAttribute minValue = new CustomAttribute();
		minValue.setLongName("minValue");
		minValue.setShortName("minVe");
		minValue.setType("xs:float");
		getCustomAttributes().add(minValue);
		CustomAttribute maxValue = new CustomAttribute();
		maxValue.setLongName("maxValue");
		maxValue.setShortName("maxVe");
		maxValue.setType("xs:float");
		getCustomAttributes().add(maxValue);
		CustomAttribute defaultValue = new CustomAttribute();
		defaultValue.setLongName("defaultValue");
		defaultValue.setShortName("defVe");
		defaultValue.setType("xs:float");
		getCustomAttributes().add(defaultValue);
		CustomAttribute step = new CustomAttribute();
		step.setLongName("step");
		step.setShortName("step");
		step.setType("xs:float");
		getCustomAttributes().add(step);
		CustomAttribute numberValue = new CustomAttribute();
		numberValue.setLongName("numberValue");
		numberValue.setShortName("numVe");
		numberValue.setType("xs:float");
		getCustomAttributes().add(numberValue);
	}


	@XmlElement(name=DecrementNumberValueFlexContainer.SHORT_NAME, required=true, type=DecrementNumberValueFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private DecrementNumberValueFlexContainer decrementNumberValue;
	
	public DecrementNumberValueFlexContainer getDecrementNumberValue() {
		this.decrementNumberValue = (DecrementNumberValueFlexContainer) getResourceByName(DecrementNumberValueFlexContainer.SHORT_NAME);
		return decrementNumberValue;
	}
	
	public void setDecrementNumberValue(DecrementNumberValueFlexContainer newAction) {
		this.decrementNumberValue = newAction;
		getFlexContainerOrContainerOrSubscription().add(decrementNumberValue);
	}

	@XmlElement(name=IncrementNumberValueFlexContainer.SHORT_NAME, required=true, type=IncrementNumberValueFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private IncrementNumberValueFlexContainer incrementNumberValue;
	
	public IncrementNumberValueFlexContainer getIncrementNumberValue() {
		this.incrementNumberValue = (IncrementNumberValueFlexContainer) getResourceByName(IncrementNumberValueFlexContainer.SHORT_NAME);
		return incrementNumberValue;
	}
	
	public void setIncrementNumberValue(IncrementNumberValueFlexContainer newAction) {
		this.incrementNumberValue = newAction;
		getFlexContainerOrContainerOrSubscription().add(incrementNumberValue);
	}

	@XmlElement(name=ResetNumberValueFlexContainer.SHORT_NAME, required=true, type=ResetNumberValueFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ResetNumberValueFlexContainer resetNumberValue;
	
	public ResetNumberValueFlexContainer getResetNumberValue() {
		this.resetNumberValue = (ResetNumberValueFlexContainer) getResourceByName(ResetNumberValueFlexContainer.SHORT_NAME);
		return resetNumberValue;
	}
	
	public void setResetNumberValue(ResetNumberValueFlexContainer newAction) {
		this.resetNumberValue = newAction;
		getFlexContainerOrContainerOrSubscription().add(resetNumberValue);
	}
		
	public void finalizeSerialization() {
		getDecrementNumberValue();
		getIncrementNumberValue();
		getResetNumberValue();
	}
	
	public void finalizeDeserialization() {
		if (this.decrementNumberValue != null) {
			setDecrementNumberValue(this.decrementNumberValue);
		}
		if (this.incrementNumberValue != null) {
			setIncrementNumberValue(this.incrementNumberValue);
		}
		if (this.resetNumberValue != null) {
			setResetNumberValue(this.resetNumberValue);
		}
	}
	
}
