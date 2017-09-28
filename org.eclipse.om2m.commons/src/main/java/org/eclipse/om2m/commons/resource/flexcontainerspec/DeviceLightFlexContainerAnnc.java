/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceLightAnnc

A light is a device that is used to control the state of an illumination device.

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
		getRunMode();
		getRunModeAnnc();
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
		
		if (this.runMode != null) {
			setRunMode(this.runMode);
		}
		if (this.runModeAnnc != null) {
			setRunModeAnnc(this.runModeAnnc);
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
	
	@XmlElement(name="fauDn", required=true, type=FaultDetectionFlexContainerAnnc.class)
	private FaultDetectionFlexContainer faultDetection;
	
	
	public void setFaultDetection(FaultDetectionFlexContainer faultDetection) {
		this.faultDetection = faultDetection;
		getFlexContainerOrContainerOrSubscription().add(faultDetection);
	}
	
	public FaultDetectionFlexContainer getFaultDetection() {
		this.faultDetection = (FaultDetectionFlexContainer) getResourceByName(FaultDetectionFlexContainer.SHORT_NAME);
		return faultDetection;
	}
	
	@XmlElement(name="fauDnAnnc", required=true, type=FaultDetectionFlexContainerAnnc.class)
	private FaultDetectionFlexContainerAnnc faultDetectionAnnc;
	
	
	public void setFaultDetectionAnnc(FaultDetectionFlexContainerAnnc faultDetectionAnnc) {
		this.faultDetectionAnnc = faultDetectionAnnc;
		getFlexContainerOrContainerOrSubscription().add(faultDetectionAnnc);
	}
	
	public FaultDetectionFlexContainerAnnc getFaultDetectionAnnc() {
		this.faultDetectionAnnc = (FaultDetectionFlexContainerAnnc) getResourceByName(FaultDetectionFlexContainerAnnc.SHORT_NAME);
		return faultDetectionAnnc;
	}
	
	@XmlElement(name="binSh", required=true, type=BinarySwitchFlexContainerAnnc.class)
	private BinarySwitchFlexContainer binarySwitch;
	
	
	public void setBinarySwitch(BinarySwitchFlexContainer binarySwitch) {
		this.binarySwitch = binarySwitch;
		getFlexContainerOrContainerOrSubscription().add(binarySwitch);
	}
	
	public BinarySwitchFlexContainer getBinarySwitch() {
		this.binarySwitch = (BinarySwitchFlexContainer) getResourceByName(BinarySwitchFlexContainer.SHORT_NAME);
		return binarySwitch;
	}
	
	@XmlElement(name="binShAnnc", required=true, type=BinarySwitchFlexContainerAnnc.class)
	private BinarySwitchFlexContainerAnnc binarySwitchAnnc;
	
	
	public void setBinarySwitchAnnc(BinarySwitchFlexContainerAnnc binarySwitchAnnc) {
		this.binarySwitchAnnc = binarySwitchAnnc;
		getFlexContainerOrContainerOrSubscription().add(binarySwitchAnnc);
	}
	
	public BinarySwitchFlexContainerAnnc getBinarySwitchAnnc() {
		this.binarySwitchAnnc = (BinarySwitchFlexContainerAnnc) getResourceByName(BinarySwitchFlexContainerAnnc.SHORT_NAME);
		return binarySwitchAnnc;
	}
	
	@XmlElement(name="runMe", required=true, type=RunModeFlexContainerAnnc.class)
	private RunModeFlexContainer runMode;
	
	
	public void setRunMode(RunModeFlexContainer runMode) {
		this.runMode = runMode;
		getFlexContainerOrContainerOrSubscription().add(runMode);
	}
	
	public RunModeFlexContainer getRunMode() {
		this.runMode = (RunModeFlexContainer) getResourceByName(RunModeFlexContainer.SHORT_NAME);
		return runMode;
	}
	
	@XmlElement(name="runMeAnnc", required=true, type=RunModeFlexContainerAnnc.class)
	private RunModeFlexContainerAnnc runModeAnnc;
	
	
	public void setRunModeAnnc(RunModeFlexContainerAnnc runModeAnnc) {
		this.runModeAnnc = runModeAnnc;
		getFlexContainerOrContainerOrSubscription().add(runModeAnnc);
	}
	
	public RunModeFlexContainerAnnc getRunModeAnnc() {
		this.runModeAnnc = (RunModeFlexContainerAnnc) getResourceByName(RunModeFlexContainerAnnc.SHORT_NAME);
		return runModeAnnc;
	}
	
	@XmlElement(name="color", required=true, type=ColourFlexContainerAnnc.class)
	private ColourFlexContainer colour;
	
	
	public void setColour(ColourFlexContainer colour) {
		this.colour = colour;
		getFlexContainerOrContainerOrSubscription().add(colour);
	}
	
	public ColourFlexContainer getColour() {
		this.colour = (ColourFlexContainer) getResourceByName(ColourFlexContainer.SHORT_NAME);
		return colour;
	}
	
	@XmlElement(name="colorAnnc", required=true, type=ColourFlexContainerAnnc.class)
	private ColourFlexContainerAnnc colourAnnc;
	
	
	public void setColourAnnc(ColourFlexContainerAnnc colourAnnc) {
		this.colourAnnc = colourAnnc;
		getFlexContainerOrContainerOrSubscription().add(colourAnnc);
	}
	
	public ColourFlexContainerAnnc getColourAnnc() {
		this.colourAnnc = (ColourFlexContainerAnnc) getResourceByName(ColourFlexContainerAnnc.SHORT_NAME);
		return colourAnnc;
	}
	
	@XmlElement(name="colSn", required=true, type=ColourSaturationFlexContainerAnnc.class)
	private ColourSaturationFlexContainer colourSaturation;
	
	
	public void setColourSaturation(ColourSaturationFlexContainer colourSaturation) {
		this.colourSaturation = colourSaturation;
		getFlexContainerOrContainerOrSubscription().add(colourSaturation);
	}
	
	public ColourSaturationFlexContainer getColourSaturation() {
		this.colourSaturation = (ColourSaturationFlexContainer) getResourceByName(ColourSaturationFlexContainer.SHORT_NAME);
		return colourSaturation;
	}
	
	@XmlElement(name="colSnAnnc", required=true, type=ColourSaturationFlexContainerAnnc.class)
	private ColourSaturationFlexContainerAnnc colourSaturationAnnc;
	
	
	public void setColourSaturationAnnc(ColourSaturationFlexContainerAnnc colourSaturationAnnc) {
		this.colourSaturationAnnc = colourSaturationAnnc;
		getFlexContainerOrContainerOrSubscription().add(colourSaturationAnnc);
	}
	
	public ColourSaturationFlexContainerAnnc getColourSaturationAnnc() {
		this.colourSaturationAnnc = (ColourSaturationFlexContainerAnnc) getResourceByName(ColourSaturationFlexContainerAnnc.SHORT_NAME);
		return colourSaturationAnnc;
	}
	
	@XmlElement(name="brigs", required=true, type=BrightnessFlexContainerAnnc.class)
	private BrightnessFlexContainer brightness;
	
	
	public void setBrightness(BrightnessFlexContainer brightness) {
		this.brightness = brightness;
		getFlexContainerOrContainerOrSubscription().add(brightness);
	}
	
	public BrightnessFlexContainer getBrightness() {
		this.brightness = (BrightnessFlexContainer) getResourceByName(BrightnessFlexContainer.SHORT_NAME);
		return brightness;
	}
	
	@XmlElement(name="brigsAnnc", required=true, type=BrightnessFlexContainerAnnc.class)
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