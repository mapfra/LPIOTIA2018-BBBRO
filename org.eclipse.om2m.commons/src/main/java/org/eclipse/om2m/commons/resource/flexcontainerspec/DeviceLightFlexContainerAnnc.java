/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceLightAnnc

A light is a device that is used to control the state of an illumination appliance.

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

@XmlRootElement(name = DeviceLightFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceLightFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceLightFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceLightAnnc";
	public static final String SHORT_NAME = "devLtAnnc";
	
	public DeviceLightFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceLightFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getFaultDetection();
		getFaultDetectionAnnc();
		getBinarySwitch();
		getBinarySwitchAnnc();
		getRunState();
		getRunStateAnnc();
		getColour();
		getColourAnnc();
		getColourSaturation();
		getColourSaturationAnnc();
		getBrightness();
		getBrightnessAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.faultDetection != null) {
			setFaultDetection(this.faultDetection);
		}
		if (this.faultDetectionAnnc != null) {
			setFaultDetectionAnnc(this.faultDetectionAnnc);
		}
		if (this.binarySwitch != null) {
			setBinarySwitch(this.binarySwitch);
		}
		if (this.binarySwitchAnnc != null) {
			setBinarySwitchAnnc(this.binarySwitchAnnc);
		}
		if (this.runState != null) {
			setRunState(this.runState);
		}
		if (this.runStateAnnc != null) {
			setRunStateAnnc(this.runStateAnnc);
		}
		if (this.colour != null) {
			setColour(this.colour);
		}
		if (this.colourAnnc != null) {
			setColourAnnc(this.colourAnnc);
		}
		if (this.colourSaturation != null) {
			setColourSaturation(this.colourSaturation);
		}
		if (this.colourSaturationAnnc != null) {
			setColourSaturationAnnc(this.colourSaturationAnnc);
		}
		if (this.brightness != null) {
			setBrightness(this.brightness);
		}
		if (this.brightnessAnnc != null) {
			setBrightnessAnnc(this.brightnessAnnc);
		}
	}

	@XmlElement(name=FaultDetectionFlexContainer.SHORT_NAME, required=true, type=FaultDetectionFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private FaultDetectionFlexContainer faultDetection;
		
	public void setFaultDetection(FaultDetectionFlexContainer faultDetection) {
		this.faultDetection = faultDetection;
		getFlexContainerOrContainerOrSubscription().add(faultDetection);
	}
	
	public FaultDetectionFlexContainer getFaultDetection() {
		this.faultDetection = (FaultDetectionFlexContainer) getResourceByName(FaultDetectionFlexContainer.SHORT_NAME);
		return faultDetection;
	}
	
	@XmlElement(name=FaultDetectionFlexContainerAnnc.SHORT_NAME, required=true, type=FaultDetectionFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private FaultDetectionFlexContainerAnnc faultDetectionAnnc;
		
	public void setFaultDetectionAnnc(FaultDetectionFlexContainerAnnc faultDetectionAnnc) {
		this.faultDetectionAnnc = faultDetectionAnnc;
		getFlexContainerOrContainerOrSubscription().add(faultDetectionAnnc);
	}
	
	public FaultDetectionFlexContainerAnnc getFaultDetectionAnnc() {
		this.faultDetectionAnnc = (FaultDetectionFlexContainerAnnc) getResourceByName(FaultDetectionFlexContainerAnnc.SHORT_NAME);
		return faultDetectionAnnc;
	}
	
	@XmlElement(name=BinarySwitchFlexContainer.SHORT_NAME, required=true, type=BinarySwitchFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BinarySwitchFlexContainer binarySwitch;
		
	public void setBinarySwitch(BinarySwitchFlexContainer binarySwitch) {
		this.binarySwitch = binarySwitch;
		getFlexContainerOrContainerOrSubscription().add(binarySwitch);
	}
	
	public BinarySwitchFlexContainer getBinarySwitch() {
		this.binarySwitch = (BinarySwitchFlexContainer) getResourceByName(BinarySwitchFlexContainer.SHORT_NAME);
		return binarySwitch;
	}
	
	@XmlElement(name=BinarySwitchFlexContainerAnnc.SHORT_NAME, required=true, type=BinarySwitchFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BinarySwitchFlexContainerAnnc binarySwitchAnnc;
		
	public void setBinarySwitchAnnc(BinarySwitchFlexContainerAnnc binarySwitchAnnc) {
		this.binarySwitchAnnc = binarySwitchAnnc;
		getFlexContainerOrContainerOrSubscription().add(binarySwitchAnnc);
	}
	
	public BinarySwitchFlexContainerAnnc getBinarySwitchAnnc() {
		this.binarySwitchAnnc = (BinarySwitchFlexContainerAnnc) getResourceByName(BinarySwitchFlexContainerAnnc.SHORT_NAME);
		return binarySwitchAnnc;
	}
	
	@XmlElement(name=RunStateFlexContainer.SHORT_NAME, required=true, type=RunStateFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private RunStateFlexContainer runState;
		
	public void setRunState(RunStateFlexContainer runState) {
		this.runState = runState;
		getFlexContainerOrContainerOrSubscription().add(runState);
	}
	
	public RunStateFlexContainer getRunState() {
		this.runState = (RunStateFlexContainer) getResourceByName(RunStateFlexContainer.SHORT_NAME);
		return runState;
	}
	
	@XmlElement(name=RunStateFlexContainerAnnc.SHORT_NAME, required=true, type=RunStateFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private RunStateFlexContainerAnnc runStateAnnc;
		
	public void setRunStateAnnc(RunStateFlexContainerAnnc runStateAnnc) {
		this.runStateAnnc = runStateAnnc;
		getFlexContainerOrContainerOrSubscription().add(runStateAnnc);
	}
	
	public RunStateFlexContainerAnnc getRunStateAnnc() {
		this.runStateAnnc = (RunStateFlexContainerAnnc) getResourceByName(RunStateFlexContainerAnnc.SHORT_NAME);
		return runStateAnnc;
	}
	
	@XmlElement(name=ColourFlexContainer.SHORT_NAME, required=true, type=ColourFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ColourFlexContainer colour;
		
	public void setColour(ColourFlexContainer colour) {
		this.colour = colour;
		getFlexContainerOrContainerOrSubscription().add(colour);
	}
	
	public ColourFlexContainer getColour() {
		this.colour = (ColourFlexContainer) getResourceByName(ColourFlexContainer.SHORT_NAME);
		return colour;
	}
	
	@XmlElement(name=ColourFlexContainerAnnc.SHORT_NAME, required=true, type=ColourFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ColourFlexContainerAnnc colourAnnc;
		
	public void setColourAnnc(ColourFlexContainerAnnc colourAnnc) {
		this.colourAnnc = colourAnnc;
		getFlexContainerOrContainerOrSubscription().add(colourAnnc);
	}
	
	public ColourFlexContainerAnnc getColourAnnc() {
		this.colourAnnc = (ColourFlexContainerAnnc) getResourceByName(ColourFlexContainerAnnc.SHORT_NAME);
		return colourAnnc;
	}
	
	@XmlElement(name=ColourSaturationFlexContainer.SHORT_NAME, required=true, type=ColourSaturationFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ColourSaturationFlexContainer colourSaturation;
		
	public void setColourSaturation(ColourSaturationFlexContainer colourSaturation) {
		this.colourSaturation = colourSaturation;
		getFlexContainerOrContainerOrSubscription().add(colourSaturation);
	}
	
	public ColourSaturationFlexContainer getColourSaturation() {
		this.colourSaturation = (ColourSaturationFlexContainer) getResourceByName(ColourSaturationFlexContainer.SHORT_NAME);
		return colourSaturation;
	}
	
	@XmlElement(name=ColourSaturationFlexContainerAnnc.SHORT_NAME, required=true, type=ColourSaturationFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private ColourSaturationFlexContainerAnnc colourSaturationAnnc;
		
	public void setColourSaturationAnnc(ColourSaturationFlexContainerAnnc colourSaturationAnnc) {
		this.colourSaturationAnnc = colourSaturationAnnc;
		getFlexContainerOrContainerOrSubscription().add(colourSaturationAnnc);
	}
	
	public ColourSaturationFlexContainerAnnc getColourSaturationAnnc() {
		this.colourSaturationAnnc = (ColourSaturationFlexContainerAnnc) getResourceByName(ColourSaturationFlexContainerAnnc.SHORT_NAME);
		return colourSaturationAnnc;
	}
	
	@XmlElement(name=BrightnessFlexContainer.SHORT_NAME, required=true, type=BrightnessFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BrightnessFlexContainer brightness;
		
	public void setBrightness(BrightnessFlexContainer brightness) {
		this.brightness = brightness;
		getFlexContainerOrContainerOrSubscription().add(brightness);
	}
	
	public BrightnessFlexContainer getBrightness() {
		this.brightness = (BrightnessFlexContainer) getResourceByName(BrightnessFlexContainer.SHORT_NAME);
		return brightness;
	}
	
	@XmlElement(name=BrightnessFlexContainerAnnc.SHORT_NAME, required=true, type=BrightnessFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BrightnessFlexContainerAnnc brightnessAnnc;
		
	public void setBrightnessAnnc(BrightnessFlexContainerAnnc brightnessAnnc) {
		this.brightnessAnnc = brightnessAnnc;
		getFlexContainerOrContainerOrSubscription().add(brightnessAnnc);
	}
	
	public BrightnessFlexContainerAnnc getBrightnessAnnc() {
		this.brightnessAnnc = (BrightnessFlexContainerAnnc) getResourceByName(BrightnessFlexContainerAnnc.SHORT_NAME);
		return brightnessAnnc;
	}
	
}
