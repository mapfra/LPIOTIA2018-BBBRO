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
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.resource.flexcontainerspec.*;

@XmlAccessorType(XmlAccessType.FIELD)
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
		@XmlElement(name = ShortName.CNT, namespace = "http://www.onem2m.org/xml/protocols", type = Container.class),
		@XmlElement(name = ShortName.SUB, namespace = "http://www.onem2m.org/xml/protocols", type = Subscription.class),
		@XmlElement(name = ShortName.FCNT, namespace = "http://www.onem2m.org/xml/protocols", type = AbstractFlexContainer.class),
		@XmlElement(name = ShortName.FCNTA, namespace = "http://www.onem2m.org/xml/protocols", type = AbstractFlexContainerAnnc.class),
		@XmlElement(name = AlarmSpeakerFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = AlarmSpeakerFlexContainerAnnc.class),
		@XmlElement(name = AudioVideoInputFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = AudioVideoInputFlexContainerAnnc.class),
		@XmlElement(name = AudioVolumeFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = AudioVolumeFlexContainerAnnc.class),
		@XmlElement(name = UpVolumeFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = UpVolumeFlexContainerAnnc.class),
		@XmlElement(name = DownVolumeFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DownVolumeFlexContainerAnnc.class),
		@XmlElement(name = BatteryFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = BatteryFlexContainerAnnc.class),
		@XmlElement(name = BinarySwitchFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = BinarySwitchFlexContainerAnnc.class),
		@XmlElement(name = ToggleFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ToggleFlexContainerAnnc.class),
		@XmlElement(name = BioElectricalImpedanceAnalysisFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = BioElectricalImpedanceAnalysisFlexContainerAnnc.class),
		@XmlElement(name = BoilerFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = BoilerFlexContainerAnnc.class),
		@XmlElement(name = BrightnessFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = BrightnessFlexContainerAnnc.class),
		@XmlElement(name = ClockFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ClockFlexContainerAnnc.class),
		@XmlElement(name = ColourFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ColourFlexContainerAnnc.class),
		@XmlElement(name = ColourSaturationFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ColourSaturationFlexContainerAnnc.class),
		@XmlElement(name = DoorStatusFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DoorStatusFlexContainerAnnc.class),
		@XmlElement(name = ElectricVehicleConnectorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ElectricVehicleConnectorFlexContainerAnnc.class),
		@XmlElement(name = EnergyConsumptionFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = EnergyConsumptionFlexContainerAnnc.class),
		@XmlElement(name = EnergyGenerationFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = EnergyGenerationFlexContainerAnnc.class),
		@XmlElement(name = FaultDetectionFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = FaultDetectionFlexContainerAnnc.class),
		@XmlElement(name = HeightFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = HeightFlexContainerAnnc.class),
		@XmlElement(name = HotWaterSupplyFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = HotWaterSupplyFlexContainerAnnc.class),
		@XmlElement(name = KeypadFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = KeypadFlexContainerAnnc.class),
		@XmlElement(name = MotionSensorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = MotionSensorFlexContainerAnnc.class),
		@XmlElement(name = OximeterFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = OximeterFlexContainerAnnc.class),
		@XmlElement(name = PowerSaveFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = PowerSaveFlexContainerAnnc.class),
		@XmlElement(name = PushButtonFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = PushButtonFlexContainerAnnc.class),
		@XmlElement(name = RecorderFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = RecorderFlexContainerAnnc.class),
		@XmlElement(name = RefrigerationFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = RefrigerationFlexContainerAnnc.class),
		@XmlElement(name = RelativeHumidityFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = RelativeHumidityFlexContainerAnnc.class),
		@XmlElement(name = RinseLevelFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = RinseLevelFlexContainerAnnc.class),
		@XmlElement(name = RunModeFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = RunModeFlexContainerAnnc.class),
		@XmlElement(name = SignalStrengthFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = SignalStrengthFlexContainerAnnc.class),
		@XmlElement(name = SmokeSensorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = SmokeSensorFlexContainerAnnc.class),
		@XmlElement(name = SpinLevelFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = SpinLevelFlexContainerAnnc.class),
		@XmlElement(name = TelevisionChannelFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = TelevisionChannelFlexContainerAnnc.class),
		@XmlElement(name = UpChannelFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = UpChannelFlexContainerAnnc.class),
		@XmlElement(name = DownChannelFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DownChannelFlexContainerAnnc.class),
		@XmlElement(name = TemperatureFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = TemperatureFlexContainerAnnc.class),
		@XmlElement(name = TemperatureAlarmFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = TemperatureAlarmFlexContainerAnnc.class),
		@XmlElement(name = TimerFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = TimerFlexContainerAnnc.class),
		@XmlElement(name = ActivateClockTimerFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ActivateClockTimerFlexContainerAnnc.class),
		@XmlElement(name = DeactivateClockTimerFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeactivateClockTimerFlexContainerAnnc.class),
		@XmlElement(name = TurboFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = TurboFlexContainerAnnc.class),
		@XmlElement(name = WaterFlowFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = WaterFlowFlexContainerAnnc.class),
		@XmlElement(name = WaterLevelFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = WaterLevelFlexContainerAnnc.class),
		@XmlElement(name = WaterSensorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = WaterSensorFlexContainerAnnc.class),
		@XmlElement(name = WeightFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = WeightFlexContainerAnnc.class),
		@XmlElement(name = WindFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = WindFlexContainerAnnc.class),
		@XmlElement(name = StreamingFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = StreamingFlexContainerAnnc.class),
		@XmlElement(name = PersonSensorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = PersonSensorFlexContainerAnnc.class),
		@XmlElement(name = BrewingFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = BrewingFlexContainerAnnc.class),
		@XmlElement(name = LiquidLevelFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = LiquidLevelFlexContainerAnnc.class),
		@XmlElement(name = GrinderFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = GrinderFlexContainerAnnc.class),
		@XmlElement(name = FoamingFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = FoamingFlexContainerAnnc.class),
		@XmlElement(name = KeepWarmFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = KeepWarmFlexContainerAnnc.class),
		@XmlElement(name = ContactSensorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ContactSensorFlexContainerAnnc.class),
		@XmlElement(name = AlarmSensorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = AlarmSensorFlexContainerAnnc.class),
		@XmlElement(name = LockFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = LockFlexContainerAnnc.class),
		@XmlElement(name = AtmosphericPressureSensorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = AtmosphericPressureSensorFlexContainerAnnc.class),
		@XmlElement(name = NoiseFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = NoiseFlexContainerAnnc.class),
		@XmlElement(name = ExtendedCarbonDioxideSensorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ExtendedCarbonDioxideSensorFlexContainerAnnc.class),
		@XmlElement(name = NumberValueFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = NumberValueFlexContainerAnnc.class),
		@XmlElement(name = DecrementNumberValueFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DecrementNumberValueFlexContainerAnnc.class),
		@XmlElement(name = IncrementNumberValueFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = IncrementNumberValueFlexContainerAnnc.class),
		@XmlElement(name = ResetNumberValueFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = ResetNumberValueFlexContainerAnnc.class),
		@XmlElement(name = DeviceAirConditionerFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceAirConditionerFlexContainerAnnc.class),
		@XmlElement(name = DeviceClothesWasherFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceClothesWasherFlexContainerAnnc.class),
		@XmlElement(name = DeviceElectricVehicleChargerFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceElectricVehicleChargerFlexContainerAnnc.class),
		@XmlElement(name = DeviceLightFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceLightFlexContainerAnnc.class),
		@XmlElement(name = DeviceMicrogenerationFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceMicrogenerationFlexContainerAnnc.class),
		@XmlElement(name = DeviceOvenFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceOvenFlexContainerAnnc.class),
		@XmlElement(name = DeviceRefrigeratorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceRefrigeratorFlexContainerAnnc.class),
		@XmlElement(name = DeviceRobotCleanerFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceRobotCleanerFlexContainerAnnc.class),
		@XmlElement(name = DeviceSmartElectricMeterFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSmartElectricMeterFlexContainerAnnc.class),
		@XmlElement(name = DeviceStorageBatteryFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceStorageBatteryFlexContainerAnnc.class),
		@XmlElement(name = DeviceTelevisionFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceTelevisionFlexContainerAnnc.class),
		@XmlElement(name = DeviceThermostatFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceThermostatFlexContainerAnnc.class),
		@XmlElement(name = DeviceWaterHeaterFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceWaterHeaterFlexContainerAnnc.class),
		@XmlElement(name = DeviceCameraFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceCameraFlexContainerAnnc.class),
		@XmlElement(name = DeviceCoffeeMachineFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceCoffeeMachineFlexContainerAnnc.class),
		@XmlElement(name = DeviceContactDetectorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceContactDetectorFlexContainerAnnc.class),
		@XmlElement(name = DeviceDoorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceDoorFlexContainerAnnc.class),
		@XmlElement(name = DeviceFloodDetectorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceFloodDetectorFlexContainerAnnc.class),
		@XmlElement(name = DeviceGasValveFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceGasValveFlexContainerAnnc.class),
		@XmlElement(name = DeviceMotionDetectorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceMotionDetectorFlexContainerAnnc.class),
		@XmlElement(name = DeviceSmokeDetectorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSmokeDetectorFlexContainerAnnc.class),
		@XmlElement(name = DeviceSmokeExtractorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSmokeExtractorFlexContainerAnnc.class),
		@XmlElement(name = DeviceSwitchButtonFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSwitchButtonFlexContainerAnnc.class),
		@XmlElement(name = DeviceTemperatureDetectorFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceTemperatureDetectorFlexContainerAnnc.class),
		@XmlElement(name = DeviceWarningDeviceFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceWarningDeviceFlexContainerAnnc.class),
		@XmlElement(name = DeviceWaterValveFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceWaterValveFlexContainerAnnc.class),
		@XmlElement(name = DeviceWeatherStationFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceWeatherStationFlexContainerAnnc.class),
		@XmlElement(name = DeviceNumberDeviceFlexContainerAnnc.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain", type = DeviceNumberDeviceFlexContainerAnnc.class)
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
