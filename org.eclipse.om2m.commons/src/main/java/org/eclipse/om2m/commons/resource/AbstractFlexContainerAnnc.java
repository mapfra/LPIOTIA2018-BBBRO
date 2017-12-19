/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.commons.resource;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.resource.flexcontainerspec.AlarmSpeakerFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.AtmosphericPressureSensorFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.AudioVolumeFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.BatteryFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.BinarySwitchFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.BoilerFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.BrewingFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.BrightnessFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ClockFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ColourFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ColourSaturationFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ContactSensorFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DecrementNumberValueFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceCameraFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceCoffeeMachineFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceContactDetectorFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceDoorFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceFloodDetectorFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceGasValveFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceLightFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceMotionDetectorFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceNumberDeviceFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceSmartElectricMeterFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceSmokeDetectorFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceSmokeExtractorFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceSwitchButtonFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceTemperatureDetectorFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceThermostatFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceWarningDeviceFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceWaterHeaterFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceWaterValveFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceWeatherStationFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DoorStatusFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.EnergyConsumptionFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.EnergyGenerationFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ExtendedCarbonDioxideSensorFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.FaultDetectionFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.FoamingFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.GrinderFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.IncrementNumberValueFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.LiquidLevelFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.LockFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.NoiseFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.NumberValueFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.PersonSensorFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.RelativeHumidityFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ResetNumberValueFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.RunModeFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.SmokeSensorFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.StreamingFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.TemperatureFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.TimerFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ToggleFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.WaterLevelFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.WaterSensorFlexContainerAnnc;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({DeviceLightFlexContainerAnnc.class,
	DeviceSmartElectricMeterFlexContainerAnnc.class, DeviceWaterHeaterFlexContainerAnnc.class,
	DeviceCameraFlexContainerAnnc.class, DeviceCoffeeMachineFlexContainerAnnc.class,
	DeviceContactDetectorFlexContainerAnnc.class, DeviceDoorFlexContainerAnnc.class,
	DeviceFloodDetectorFlexContainerAnnc.class, DeviceGasValveFlexContainerAnnc.class,
	DeviceMotionDetectorFlexContainerAnnc.class, DeviceSmokeDetectorFlexContainerAnnc.class,
	DeviceSmokeExtractorFlexContainerAnnc.class, DeviceSwitchButtonFlexContainerAnnc.class,
	DeviceTemperatureDetectorFlexContainerAnnc.class, DeviceThermostatFlexContainerAnnc.class,
	DeviceWarningDeviceFlexContainerAnnc.class,
	DeviceWaterValveFlexContainerAnnc.class, DeviceWeatherStationFlexContainerAnnc.class,
	DeviceThermostatFlexContainerAnnc.class, DeviceNumberDeviceFlexContainerAnnc.class,
	AlarmSpeakerFlexContainerAnnc.class, AudioVolumeFlexContainerAnnc.class,
	BinarySwitchFlexContainerAnnc.class, BoilerFlexContainerAnnc.class,
	BrightnessFlexContainerAnnc.class, ClockFlexContainerAnnc.class,
	ColourFlexContainerAnnc.class, ColourSaturationFlexContainerAnnc.class,
	DoorStatusFlexContainerAnnc.class, EnergyConsumptionFlexContainerAnnc.class,
	EnergyGenerationFlexContainerAnnc.class, FaultDetectionFlexContainerAnnc.class,
	RelativeHumidityFlexContainerAnnc.class, RunModeFlexContainerAnnc.class,
	SmokeSensorFlexContainerAnnc.class, TemperatureFlexContainerAnnc.class,
	WaterLevelFlexContainerAnnc.class, WaterSensorFlexContainerAnnc.class,
	AtmosphericPressureSensorFlexContainerAnnc.class, BrewingFlexContainerAnnc.class,
	ContactSensorFlexContainerAnnc.class, ExtendedCarbonDioxideSensorFlexContainerAnnc.class,
	FoamingFlexContainerAnnc.class, GrinderFlexContainerAnnc.class,
	NoiseFlexContainerAnnc.class, PersonSensorFlexContainerAnnc.class,
	StreamingFlexContainerAnnc.class, LockFlexContainerAnnc.class,
	BatteryFlexContainerAnnc.class,
	LiquidLevelFlexContainerAnnc.class, TimerFlexContainerAnnc.class,
	NumberValueFlexContainerAnnc.class, IncrementNumberValueFlexContainerAnnc.class,
	DecrementNumberValueFlexContainerAnnc.class, ResetNumberValueFlexContainerAnnc.class,
	ToggleFlexContainerAnnc.class})
public class AbstractFlexContainerAnnc extends AnnouncedResource {
	
	@XmlTransient
	private String shortName;
	
	@XmlTransient
	private String longName;
	
	@XmlElement(name = ShortName.STATETAG, required = true, namespace="")
	@XmlSchemaType(name = "nonNegativeInteger")
	protected BigInteger stateTag;
	@XmlElement(name = ShortName.CREATOR, required = true, namespace="")
	protected String creator;
	@XmlSchemaType(name = "anyURI")
	@XmlElement(name = ShortName.ONTOLOGY_REF, namespace="")
	protected String ontologyRef;
	@XmlSchemaType(name = "anyURI")
	@XmlElement(name = ShortName.CONTAINER_DEFINITION, namespace="")
	protected String containerDefinition;
	@XmlSchemaType(name = "anyURI")
	@XmlElement(name = ShortName.NODE_LINK, required = false, namespace="")
	protected String nodeLink;
	@XmlElement(name = ShortName.CHILD_RESOURCE, namespace="")
	protected List<ChildResourceRef> childResource;
	@XmlElements({
//			@XmlElement(name = ShortName.CNT, namespace = "http://www.onem2m.org/xml/protocols", type = Container.class),
//			@XmlElement(name = ShortName.FCNT, namespace = "http://www.onem2m.org/xml/protocols", type = AbstractFlexContainer.class),
			@XmlElement(name = ShortName.SUB, namespace = "http://www.onem2m.org/xml/protocols", type = Subscription.class)
//			@XmlElement(name = ShortName.FCNTA, namespace = "http://www.onem2m.org/xml/protocols", type = AbstractFlexContainerAnnc.class)
			})
	protected List<Resource> flexContainerOrContainerOrSubscription;

	@XmlAnyElement()
	protected List<CustomAttribute> customAttributes;
	
	
	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the longName
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * @param longName the longName to set
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}
	

	public List<CustomAttribute> getCustomAttributes() {
		if (customAttributes == null) {
			customAttributes = new ArrayList<CustomAttribute>();
		}
		return customAttributes;
	}

	public void setCustomAttributes(List<CustomAttribute> customAttributes) {
		this.customAttributes = customAttributes;
	}

	@XmlTransient
	public List<String> getCustomAttributeNames() {
		List<String> names = new ArrayList<String>();

		for (CustomAttribute ca : getCustomAttributes()) {
			names.add(ca.getCustomAttributeName());
		}

		return names;
	}

	@XmlTransient
	public CustomAttribute getCustomAttribute(String name) {
		for (CustomAttribute ca : getCustomAttributes()) {
			if (ca.getCustomAttributeName().equals(name)) {
				return ca;
			}
		}
		return null;
	}

	/**
	 * Gets the value of the stateTag property.
	 * 
	 * @return possible object is {@link BigInteger }
	 * 
	 */
	public BigInteger getStateTag() {
		return stateTag;
	}

	/**
	 * Sets the value of the stateTag property.
	 * 
	 * @param value
	 *            allowed object is {@link BigInteger }
	 * 
	 */
	public void setStateTag(BigInteger value) {
		this.stateTag = value;
	}

	/**
	 * Gets the value of the creator property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * Sets the value of the creator property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCreator(String value) {
		this.creator = value;
	}

	/**
	 * Gets the value of the ontologyRef property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOntologyRef() {
		return ontologyRef;
	}

	/**
	 * Sets the value of the ontologyRef property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOntologyRef(String value) {
		this.ontologyRef = value;
	}

	/**
	 * Gets the value of the containerDefinition property.
	 * 
	 * @return object is {@link String}
	 */
	public String getContainerDefinition() {
		return containerDefinition;
	}

	/**
	 * Sets the value of the containerDefinition property.
	 * 
	 * @param value
	 *            allowed object is {@link String}
	 */
	public void setContainerDefinition(String value) {
		this.containerDefinition = value;
	}

	/**
	 * Gets the value of the childResource property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the childResource property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getChildResource().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link ChildResourceRef }
	 * 
	 * 
	 */
	public List<ChildResourceRef> getChildResource() {
		if (childResource == null) {
			childResource = new ArrayList<ChildResourceRef>();
		}
		return this.childResource;
	}

	/**
	 * Gets the value of the flexContainerOrContainerOrSubscription property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the flexContainerOrContainerOrSubscription
	 * property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getFlexContainerOrContainerOrSubscription().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link AbstractFlexContainer } {@link Container } {@link Subscription }
	 * 
	 * 
	 */
	public List<Resource> getFlexContainerOrContainerOrSubscription() {
		if (flexContainerOrContainerOrSubscription == null) {
			flexContainerOrContainerOrSubscription = new ArrayList<Resource>();
		}
		return this.flexContainerOrContainerOrSubscription;
	}

	/**
	 * Gets the value of the nodeLink property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNodeLink() {
		return nodeLink;
	}

	/**
	 * Sets the value of the nodeLink property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNodeLink(String value) {
		this.nodeLink = value;
	}

	public void finalizeSerialization() {
		// do nothing
		// should be overwrote 
	}

	public Resource getResourceByName(String name) {
		for(Resource r : getFlexContainerOrContainerOrSubscription()) {
			if (r instanceof AbstractFlexContainer) {
				AbstractFlexContainer absFcnt = (AbstractFlexContainer) r ;
				if (absFcnt.getShortName().equals(name)) {
					return absFcnt;
				}
			} else if (r instanceof AbstractFlexContainerAnnc) {
				AbstractFlexContainerAnnc absFcntAnnc = (AbstractFlexContainerAnnc) r ;
				if (absFcntAnnc.getShortName().equals(name)) {
					return absFcntAnnc;
				}
			}
		}
		return null;
	}
}
