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
import org.eclipse.om2m.commons.resource.flexcontainerspec.*;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.onem2m.org/xml/protocols}announceableResource">
 *       &lt;sequence>
 *         &lt;element name="stateTag" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="creator" type="{http://www.onem2m.org/xml/protocols}ID"/>
 *         &lt;element name="maxNrOfInstances" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *         &lt;element name="maxByteSize" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *         &lt;element name="maxInstanceAge" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *         &lt;element name="currentNrOfInstances" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="currentByteSize" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="locationID" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="ontologyRef" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="latest" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="oldest" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="childResource" type="{http://www.onem2m.org/xml/protocols}childResourceRef" maxOccurs="unbounded"/>
 *           &lt;choice maxOccurs="unbounded">
 *             &lt;element ref="{http://www.onem2m.org/xml/protocols}contentInstance"/>
 *             &lt;element ref="{http://www.onem2m.org/xml/protocols}container"/>
 *             &lt;element ref="{http://www.onem2m.org/xml/protocols}subscription"/>
 *           &lt;/choice>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractFlexContainer extends AnnounceableResource {
	
	@XmlTransient
	private String shortName;
	
	@XmlTransient
	private String longName;
	
	@XmlElement(name = ShortName.STATETAG, required = true, namespace="")
	@XmlSchemaType(name = "nonNegativeInteger")
	protected BigInteger stateTag;
	
	@XmlElement(name = ShortName.CREATOR, required = false, namespace="")
	protected String creator;
	
	@XmlSchemaType(name = "anyURI")
	@XmlElement(name = ShortName.ONTOLOGY_REF, required = false, namespace="")
	protected String ontologyRef;
	
	@XmlSchemaType(name="anyURI")
	@XmlElement(name = ShortName.CONTAINER_DEFINITION, required = true, namespace="")
	protected String containerDefinition;

	@XmlSchemaType(name = "anyURI")
	@XmlElement(name = ShortName.NODE_LINK, required = false, namespace="")
	protected String nodeLink;

	@XmlElement(name = ShortName.CHILD_RESOURCE, namespace="")
	protected List<ChildResourceRef> childResource;
	
	@XmlElements({
//			@XmlElement(name = ShortName.CNT, namespace = "http://www.onem2m.org/xml/protocols", type = Container.class),
//			@XmlElement(name = ShortName.FCNT, namespace = "http://www.onem2m.org/xml/protocols", type = AbstractFlexContainer.class),
//			@XmlElement(name = DeviceLightFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceLightFlexContainer.class),
//			@XmlElement(name = DeviceSmartElectricMeterFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSmartElectricMeterFlexContainer.class),
//			@XmlElement(name = DeviceWaterHeaterFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceWaterHeaterFlexContainer.class),
//			@XmlElement(name = DeviceCameraFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceCameraFlexContainer.class),
//			@XmlElement(name = DeviceCoffeeMachineFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceCoffeeMachineFlexContainer.class),
//			@XmlElement(name = DeviceContactDetectorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceContactDetectorFlexContainer.class),
//			@XmlElement(name = DeviceDoorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceDoorFlexContainer.class),
//			@XmlElement(name = DeviceFloodDetectorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceFloodDetectorFlexContainer.class),
//			@XmlElement(name = DeviceGasValveFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceGasValveFlexContainer.class),
//			@XmlElement(name = DeviceMotionDetectorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceMotionDetectorFlexContainer.class),
//			@XmlElement(name = DeviceSmokeDetectorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSmokeDetectorFlexContainer.class),
//			@XmlElement(name = DeviceSmokeExtractorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSmokeExtractorFlexContainer.class),
//			@XmlElement(name = DeviceSwitchButtonFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSwitchButtonFlexContainer.class),
//			@XmlElement(name = DeviceTemperatureDetectorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceTemperatureDetectorFlexContainer.class),
//			@XmlElement(name = DeviceThermostatFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceThermostatFlexContainer.class),
//			@XmlElement(name = DeviceWarningDeviceFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceWarningDeviceFlexContainer.class),
//			@XmlElement(name = DeviceWaterValveFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceWaterValveFlexContainer.class),
//			@XmlElement(name = DeviceWeatherStationFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceWeatherStationFlexContainer.class),
//			@XmlElement(name = ModuleAlarmSpeakerFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleAlarmSpeakerFlexContainer.class),
//			@XmlElement(name = ModuleAudioVolumeFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleAudioVolumeFlexContainer.class),
//			@XmlElement(name = ModuleBinarySwitchFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleBinarySwitchFlexContainer.class),
//			@XmlElement(name = ModuleBoilerFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleBoilerFlexContainer.class),
//			@XmlElement(name = ModuleBrightnessFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleBrightnessFlexContainer.class),
//			@XmlElement(name = ModuleClockFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleClockFlexContainer.class),
//			@XmlElement(name = ModuleColourFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleColourFlexContainer.class),
//			@XmlElement(name = ModuleColourSaturationFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleColourSaturationFlexContainer.class),
//			@XmlElement(name = ModuleDoorStatusFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleDoorStatusFlexContainer.class),
//			@XmlElement(name = ModuleEnergyConsumptionFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleEnergyConsumptionFlexContainer.class),
//			@XmlElement(name = ModuleEnergyGenerationFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleEnergyGenerationFlexContainer.class),
//			@XmlElement(name = ModuleFaultDetectionFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleFaultDetectionFlexContainer.class),
//			@XmlElement(name = ModuleRelativeHumidityFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleRelativeHumidityFlexContainer.class),
//			@XmlElement(name = ModuleRunModeFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleRunModeFlexContainer.class),
//			@XmlElement(name = ModuleSmokeSensorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleSmokeSensorFlexContainer.class),
//			@XmlElement(name = ModuleTemperatureFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleTemperatureFlexContainer.class),
//			@XmlElement(name = ModuleWaterLevelFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleWaterLevelFlexContainer.class),
//			@XmlElement(name = ModuleWaterSensorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleWaterSensorFlexContainer.class),
//			@XmlElement(name = ModuleAtmosphericPressureSensorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleAtmosphericPressureSensorFlexContainer.class),
//			@XmlElement(name = ModuleBrewingFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleBrewingFlexContainer.class),
//			@XmlElement(name = ModuleCarbonDioxideSensorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleCarbonDioxideSensorFlexContainer.class),
//			@XmlElement(name = ModuleCarbonMonoxideSensorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleCarbonMonoxideSensorFlexContainer.class),
//			@XmlElement(name = ModuleContactSensorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleContactSensorFlexContainer.class),
//			@XmlElement(name = ModuleDimmingFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleDimmingFlexContainer.class),
//			@XmlElement(name = ModuleEnergyOverloadCircuitBreakerFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleEnergyOverloadCircuitBreakerFlexContainer.class),
//			@XmlElement(name = ModuleExtendedCarbonDioxideSensorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleExtendedCarbonDioxideSensorFlexContainer.class),
//			@XmlElement(name = ModuleFoamingFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleFoamingFlexContainer.class),
//			@XmlElement(name = ModuleGrinderFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleGrinderFlexContainer.class),
//			@XmlElement(name = ModuleNoiseFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleNoiseFlexContainer.class),
//			@XmlElement(name = ModulePersonSensorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModulePersonSensorFlexContainer.class),
//			@XmlElement(name = ModuleStreamingFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleStreamingFlexContainer.class),
//			@XmlElement(name = ModuleRunStateFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleRunStateFlexContainer.class),
//			@XmlElement(name = ModuleBatteryFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleBatteryFlexContainer.class),
//			@XmlElement(name = ModuleLiquidLevelFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleLiquidLevelFlexContainer.class),
//			@XmlElement(name = ModuleLockFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleLockFlexContainer.class),
//			@XmlElement(name = ModuleTimerFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ModuleTimerFlexContainer.class),
//			@XmlElement(name = ActionToggleFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ActionToggleFlexContainer.class),
			@XmlElement(name = ShortName.SUB, namespace = "http://www.onem2m.org/xml/protocols", type = Subscription.class) })
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
		
		for(CustomAttribute ca : getCustomAttributes()) {
			names.add(ca.getCustomAttributeName());
		}
		
		return names;
	}
	
	@XmlTransient
	public CustomAttribute getCustomAttribute(String name) {
		for(CustomAttribute ca : getCustomAttributes()) {
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
	 * @param value allowed object is {@link String}
	 */
	public void setContainerDefinition(String value) {
		this.containerDefinition = value;
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
	
	public abstract void finalizeSerialization();
	
	public abstract void finalizeDeserialization();

	public Resource getResourceByName(String name) {
		for(Resource r : getFlexContainerOrContainerOrSubscription()) {
			if (r instanceof AbstractFlexContainer) {
				AbstractFlexContainer absFcnt = (AbstractFlexContainer) r ;
				if (absFcnt.getShortName().equals(name)) {
					return absFcnt;
				}
			}
		}
		return null;
	}
}
