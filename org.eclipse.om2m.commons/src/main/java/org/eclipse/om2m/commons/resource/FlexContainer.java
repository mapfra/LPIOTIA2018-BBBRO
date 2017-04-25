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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceCameraFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceCoffeeMachineFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceContactDetectorFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceDoorFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceFloodDetectorFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceGasValveFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceLightFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceMotionDetectorFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceSmartElectricMeterFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceSmokeDetectorFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceSmokeExtractorFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceSwitchButtonFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceTemperatureDetectorFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceWarningDeviceFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceWaterHeaterFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceWaterValveFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceWeatherStationFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.FakeClass;
import org.eclipse.om2m.commons.resource.flexcontainerspec.FakeClass2;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleAlarmSpeakerFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleAtmosphericPressureSensorFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleAudioVolumeFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleBinarySwitchFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleBoilerFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleBrewingFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleBrightnessFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleCarbonDioxideSensorFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleCarbonMonoxideSensorFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleClockFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleColourFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleColourSaturationFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleContactSensorFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleDimmingFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleDoorStatusFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleEnergyConsumptionFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleEnergyGenerationFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleEnergyOverloadCircuitBreakerFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleExtendedCarbonDioxideSensorFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleFaultDetectionFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleFoamingFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleGrinderFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleNoiseFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModulePersonSensorFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleRelativeHumidityFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleRunModeFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleSmokeSensorFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleStreamingFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleTemperatureFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleWaterLevelFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ModuleWaterSensorFlexContainer;
import org.w3c.dom.Element;

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
@XmlSeeAlso({FakeClass.class, FakeClass2.class, DeviceLightFlexContainer.class,
	DeviceSmartElectricMeterFlexContainer.class, DeviceWaterHeaterFlexContainer.class,
	DeviceCameraFlexContainer.class, DeviceCoffeeMachineFlexContainer.class,
	DeviceContactDetectorFlexContainer.class, DeviceDoorFlexContainer.class,
	DeviceFloodDetectorFlexContainer.class, DeviceGasValveFlexContainer.class,
	DeviceMotionDetectorFlexContainer.class, DeviceSmokeDetectorFlexContainer.class,
	DeviceSmokeExtractorFlexContainer.class, DeviceSwitchButtonFlexContainer.class,
	DeviceTemperatureDetectorFlexContainer.class, DeviceWarningDeviceFlexContainer.class,
	DeviceWaterValveFlexContainer.class, DeviceWeatherStationFlexContainer.class,
	ModuleAlarmSpeakerFlexContainer.class, ModuleAudioVolumeFlexContainer.class,
	ModuleBinarySwitchFlexContainer.class, ModuleBoilerFlexContainer.class,
	ModuleBrightnessFlexContainer.class, ModuleClockFlexContainer.class,
	ModuleColourFlexContainer.class, ModuleColourSaturationFlexContainer.class,
	ModuleDoorStatusFlexContainer.class, ModuleEnergyConsumptionFlexContainer.class,
	ModuleEnergyGenerationFlexContainer.class, ModuleFaultDetectionFlexContainer.class,
	ModuleRelativeHumidityFlexContainer.class, ModuleRunModeFlexContainer.class,
	ModuleSmokeSensorFlexContainer.class, ModuleTemperatureFlexContainer.class,
	ModuleWaterLevelFlexContainer.class, ModuleWaterSensorFlexContainer.class,
	ModuleAtmosphericPressureSensorFlexContainer.class, ModuleBrewingFlexContainer.class,
	ModuleCarbonDioxideSensorFlexContainer.class, ModuleCarbonMonoxideSensorFlexContainer.class,
	ModuleContactSensorFlexContainer.class, ModuleDimmingFlexContainer.class,
	ModuleEnergyOverloadCircuitBreakerFlexContainer.class, ModuleExtendedCarbonDioxideSensorFlexContainer.class,
	ModuleFoamingFlexContainer.class, ModuleGrinderFlexContainer.class,
	ModuleNoiseFlexContainer.class, ModulePersonSensorFlexContainer.class,
	ModuleStreamingFlexContainer.class})
public class FlexContainer extends AnnounceableResource {

	@XmlTransient
	private String shortName;
	
	@XmlTransient
	private String longName;
	
	@XmlElement(name = ShortName.STATETAG, required = true)
	@XmlSchemaType(name = "nonNegativeInteger")
	protected BigInteger stateTag;
	@XmlElement(name = ShortName.CREATOR, required = true)
	protected String creator;
	@XmlSchemaType(name = "anyURI")
	@XmlElement(name = ShortName.ONTOLOGY_REF)
	protected String ontologyRef;
	@XmlSchemaType(name="anyURI")
	@XmlElement(name = ShortName.CONTAINER_DEFINITION)
	protected String containerDefinition;
	@XmlElement(name = ShortName.CHILD_RESOURCE)
	protected List<ChildResourceRef> childResource;
	@XmlElements({
			@XmlElement(name = ShortName.CNT, namespace = "http://www.onem2m.org/xml/protocols", type = Container.class),
			@XmlElement(name = ShortName.FCNT, namespace = "http://www.onem2m.org/xml/protocols", type = FlexContainer.class),
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
	 * {@link FlexContainer } {@link Container } {@link Subscription }
	 * 
	 * 
	 */
	public List<Resource> getFlexContainerOrContainerOrSubscription() {
		if (flexContainerOrContainerOrSubscription == null) {
			flexContainerOrContainerOrSubscription = new ArrayList<Resource>();
		}
		return this.flexContainerOrContainerOrSubscription;
	}

}
