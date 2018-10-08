/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceWeightScaleAndBodyCompositionAnalyser

A weight scale and body composition analyser is a device that can be used to monitor the weight and body composition.

Created: 2018-06-29 17:19:55
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name = DeviceWeightScaleAndBodyCompositionAnalyserFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceWeightScaleAndBodyCompositionAnalyserFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceWeightScaleAndBodyCompositionAnalyserFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceWeightScaleAndBodyCompositionAnalyser";
	public static final String SHORT_NAME = "dWSAB";
	
	public DeviceWeightScaleAndBodyCompositionAnalyserFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + DeviceWeightScaleAndBodyCompositionAnalyserFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
    public void finalizeSerialization() {
		getWeight();
		getBodyCompositionAnalyser();
		getBioElectricalImpedanceAnalysis();
		getBattery();
	}
	
	public void finalizeDeserialization() {
		if (this.weight != null) {
			setWeight(this.weight);
		}
		if (this.bodyCompositionAnalyser != null) {
			setBodyCompositionAnalyser(this.bodyCompositionAnalyser);
		}
		if (this.bioElectricalImpedanceAnalysis != null) {
			setBioElectricalImpedanceAnalysis(this.bioElectricalImpedanceAnalysis);
		}
		if (this.battery != null) {
			setBattery(this.battery);
		}
	}

	@XmlElement(name=WeightFlexContainer.SHORT_NAME, required=true, type=WeightFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private WeightFlexContainer weight;
		
	public void setWeight(WeightFlexContainer weight) {
		this.weight = weight;
		getFlexContainerOrContainerOrSubscription().add(weight);
	}
	
	public WeightFlexContainer getWeight() {
		this.weight = (WeightFlexContainer) getResourceByName(WeightFlexContainer.SHORT_NAME);
		return weight;
	}
	
	@XmlElement(name=BodyCompositionAnalyserFlexContainer.SHORT_NAME, required=true, type=BodyCompositionAnalyserFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BodyCompositionAnalyserFlexContainer bodyCompositionAnalyser;
		
	public void setBodyCompositionAnalyser(BodyCompositionAnalyserFlexContainer bodyCompositionAnalyser) {
		this.bodyCompositionAnalyser = bodyCompositionAnalyser;
		getFlexContainerOrContainerOrSubscription().add(bodyCompositionAnalyser);
	}
	
	public BodyCompositionAnalyserFlexContainer getBodyCompositionAnalyser() {
		this.bodyCompositionAnalyser = (BodyCompositionAnalyserFlexContainer) getResourceByName(BodyCompositionAnalyserFlexContainer.SHORT_NAME);
		return bodyCompositionAnalyser;
	}
	
	@XmlElement(name=BioElectricalImpedanceAnalysisFlexContainer.SHORT_NAME, required=true, type=BioElectricalImpedanceAnalysisFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BioElectricalImpedanceAnalysisFlexContainer bioElectricalImpedanceAnalysis;
		
	public void setBioElectricalImpedanceAnalysis(BioElectricalImpedanceAnalysisFlexContainer bioElectricalImpedanceAnalysis) {
		this.bioElectricalImpedanceAnalysis = bioElectricalImpedanceAnalysis;
		getFlexContainerOrContainerOrSubscription().add(bioElectricalImpedanceAnalysis);
	}
	
	public BioElectricalImpedanceAnalysisFlexContainer getBioElectricalImpedanceAnalysis() {
		this.bioElectricalImpedanceAnalysis = (BioElectricalImpedanceAnalysisFlexContainer) getResourceByName(BioElectricalImpedanceAnalysisFlexContainer.SHORT_NAME);
		return bioElectricalImpedanceAnalysis;
	}
	
	@XmlElement(name=BatteryFlexContainer.SHORT_NAME, required=true, type=BatteryFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BatteryFlexContainer battery;
		
	public void setBattery(BatteryFlexContainer battery) {
		this.battery = battery;
		getFlexContainerOrContainerOrSubscription().add(battery);
	}
	
	public BatteryFlexContainer getBattery() {
		this.battery = (BatteryFlexContainer) getResourceByName(BatteryFlexContainer.SHORT_NAME);
		return battery;
	}
	
}
