/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceWeightScaleAndBodyCompositionAnalyserAnnc

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
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DeviceWeightScaleAndBodyCompositionAnalyserFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceWeightScaleAndBodyCompositionAnalyserFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceWeightScaleAndBodyCompositionAnalyserFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceWeightScaleAndBodyCompositionAnalyserAnnc";
	public static final String SHORT_NAME = "dWSABAnnc";
	
	public DeviceWeightScaleAndBodyCompositionAnalyserFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceWeightScaleAndBodyCompositionAnalyserFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getWeight();
		getWeightAnnc();
		getBodyCompositionAnalyser();
		getBodyCompositionAnalyserAnnc();
		getBioElectricalImpedanceAnalysis();
		getBioElectricalImpedanceAnalysisAnnc();
		getBattery();
		getBatteryAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.weight != null) {
			setWeight(this.weight);
		}
		if (this.weightAnnc != null) {
			setWeightAnnc(this.weightAnnc);
		}
		if (this.bodyCompositionAnalyser != null) {
			setBodyCompositionAnalyser(this.bodyCompositionAnalyser);
		}
		if (this.bodyCompositionAnalyserAnnc != null) {
			setBodyCompositionAnalyserAnnc(this.bodyCompositionAnalyserAnnc);
		}
		if (this.bioElectricalImpedanceAnalysis != null) {
			setBioElectricalImpedanceAnalysis(this.bioElectricalImpedanceAnalysis);
		}
		if (this.bioElectricalImpedanceAnalysisAnnc != null) {
			setBioElectricalImpedanceAnalysisAnnc(this.bioElectricalImpedanceAnalysisAnnc);
		}
		if (this.battery != null) {
			setBattery(this.battery);
		}
		if (this.batteryAnnc != null) {
			setBatteryAnnc(this.batteryAnnc);
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
	
	@XmlElement(name=WeightFlexContainerAnnc.SHORT_NAME, required=true, type=WeightFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private WeightFlexContainerAnnc weightAnnc;
		
	public void setWeightAnnc(WeightFlexContainerAnnc weightAnnc) {
		this.weightAnnc = weightAnnc;
		getFlexContainerOrContainerOrSubscription().add(weightAnnc);
	}
	
	public WeightFlexContainerAnnc getWeightAnnc() {
		this.weightAnnc = (WeightFlexContainerAnnc) getResourceByName(WeightFlexContainerAnnc.SHORT_NAME);
		return weightAnnc;
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
	
	@XmlElement(name=BodyCompositionAnalyserFlexContainerAnnc.SHORT_NAME, required=true, type=BodyCompositionAnalyserFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BodyCompositionAnalyserFlexContainerAnnc bodyCompositionAnalyserAnnc;
		
	public void setBodyCompositionAnalyserAnnc(BodyCompositionAnalyserFlexContainerAnnc bodyCompositionAnalyserAnnc) {
		this.bodyCompositionAnalyserAnnc = bodyCompositionAnalyserAnnc;
		getFlexContainerOrContainerOrSubscription().add(bodyCompositionAnalyserAnnc);
	}
	
	public BodyCompositionAnalyserFlexContainerAnnc getBodyCompositionAnalyserAnnc() {
		this.bodyCompositionAnalyserAnnc = (BodyCompositionAnalyserFlexContainerAnnc) getResourceByName(BodyCompositionAnalyserFlexContainerAnnc.SHORT_NAME);
		return bodyCompositionAnalyserAnnc;
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
	
	@XmlElement(name=BioElectricalImpedanceAnalysisFlexContainerAnnc.SHORT_NAME, required=true, type=BioElectricalImpedanceAnalysisFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BioElectricalImpedanceAnalysisFlexContainerAnnc bioElectricalImpedanceAnalysisAnnc;
		
	public void setBioElectricalImpedanceAnalysisAnnc(BioElectricalImpedanceAnalysisFlexContainerAnnc bioElectricalImpedanceAnalysisAnnc) {
		this.bioElectricalImpedanceAnalysisAnnc = bioElectricalImpedanceAnalysisAnnc;
		getFlexContainerOrContainerOrSubscription().add(bioElectricalImpedanceAnalysisAnnc);
	}
	
	public BioElectricalImpedanceAnalysisFlexContainerAnnc getBioElectricalImpedanceAnalysisAnnc() {
		this.bioElectricalImpedanceAnalysisAnnc = (BioElectricalImpedanceAnalysisFlexContainerAnnc) getResourceByName(BioElectricalImpedanceAnalysisFlexContainerAnnc.SHORT_NAME);
		return bioElectricalImpedanceAnalysisAnnc;
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
	
	@XmlElement(name=BatteryFlexContainerAnnc.SHORT_NAME, required=true, type=BatteryFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BatteryFlexContainerAnnc batteryAnnc;
		
	public void setBatteryAnnc(BatteryFlexContainerAnnc batteryAnnc) {
		this.batteryAnnc = batteryAnnc;
		getFlexContainerOrContainerOrSubscription().add(batteryAnnc);
	}
	
	public BatteryFlexContainerAnnc getBatteryAnnc() {
		this.batteryAnnc = (BatteryFlexContainerAnnc) getResourceByName(BatteryFlexContainerAnnc.SHORT_NAME);
		return batteryAnnc;
	}
	
}
