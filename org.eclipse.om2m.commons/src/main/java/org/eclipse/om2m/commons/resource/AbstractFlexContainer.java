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
import org.eclipse.om2m.commons.utils.CustomAttributList;

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
		@XmlElement(name = ShortName.CNT, namespace = "http://www.onem2m.org/xml/protocols", type = Container.class),
		@XmlElement(name = ShortName.FCNT, namespace = "http://www.onem2m.org/xml/protocols", type = AbstractFlexContainer.class),
		@XmlElement(name = ShortName.SUB, namespace = "http://www.onem2m.org/xml/protocols", type = Subscription.class),
	    @XmlElement(name = DeviceThreeDPrinterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceThreeDPrinterFlexContainer.class),
	    @XmlElement(name = DeviceAirConditionerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceAirConditionerFlexContainer.class),
	    @XmlElement(name = DeviceAirPurifierFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceAirPurifierFlexContainer.class),
	    @XmlElement(name = DeviceAirQualityMonitorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceAirQualityMonitorFlexContainer.class),
	    @XmlElement(name = DeviceAudioReceiverFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceAudioReceiverFlexContainer.class),
	    @XmlElement(name = DeviceBloodPressureMonitorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceBloodPressureMonitorFlexContainer.class),
	    @XmlElement(name = DeviceCameraFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceCameraFlexContainer.class),
	    @XmlElement(name = DeviceClothesDryerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceClothesDryerFlexContainer.class),
	    @XmlElement(name = DeviceClothesWasherFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceClothesWasherFlexContainer.class),
	    @XmlElement(name = DeviceClothesWasherDryerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceClothesWasherDryerFlexContainer.class),
	    @XmlElement(name = DeviceCoffeeMachineFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceCoffeeMachineFlexContainer.class),
	    @XmlElement(name = DeviceCookerHoodFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceCookerHoodFlexContainer.class),
	    @XmlElement(name = DeviceCooktopFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceCooktopFlexContainer.class),
	    @XmlElement(name = DeviceDehumidifierFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceDehumidifierFlexContainer.class),
	    @XmlElement(name = DeviceDishWasherFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceDishWasherFlexContainer.class),
	    @XmlElement(name = DeviceDoorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceDoorFlexContainer.class),
	    @XmlElement(name = DeviceDoorLockFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceDoorLockFlexContainer.class),
	    @XmlElement(name = DeviceElectricVehicleChargerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceElectricVehicleChargerFlexContainer.class),
	    @XmlElement(name = DeviceFanFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceFanFlexContainer.class),
	    @XmlElement(name = DeviceFoodProbeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceFoodProbeFlexContainer.class),
	    @XmlElement(name = DeviceFreezerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceFreezerFlexContainer.class),
	    @XmlElement(name = DeviceGlucosemeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceGlucosemeterFlexContainer.class),
	    @XmlElement(name = DeviceHeartRateMonitorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceHeartRateMonitorFlexContainer.class),
	    @XmlElement(name = DeviceHomeCCTVFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceHomeCCTVFlexContainer.class),
	    @XmlElement(name = DeviceHumidifierFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceHumidifierFlexContainer.class),
	    @XmlElement(name = DeviceKettleFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceKettleFlexContainer.class),
	    @XmlElement(name = DeviceLightFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceLightFlexContainer.class),
	    @XmlElement(name = DeviceMicrogenerationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceMicrogenerationFlexContainer.class),
	    @XmlElement(name = DeviceMultiFunctionPrinterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceMultiFunctionPrinterFlexContainer.class),
	    @XmlElement(name = DeviceOutdoorLampFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceOutdoorLampFlexContainer.class),
	    @XmlElement(name = DeviceOvenFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceOvenFlexContainer.class),
	    @XmlElement(name = DevicePrinterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DevicePrinterFlexContainer.class),
	    @XmlElement(name = DevicePulseOximeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DevicePulseOximeterFlexContainer.class),
	    @XmlElement(name = DeviceRefrigeratorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceRefrigeratorFlexContainer.class),
	    @XmlElement(name = DeviceRobotCleanerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceRobotCleanerFlexContainer.class),
	    @XmlElement(name = DeviceScannerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceScannerFlexContainer.class),
	    @XmlElement(name = DeviceSecurityPanelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSecurityPanelFlexContainer.class),
	    @XmlElement(name = DeviceSetTopBoxFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSetTopBoxFlexContainer.class),
	    @XmlElement(name = DeviceSmartElectricMeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSmartElectricMeterFlexContainer.class),
	    @XmlElement(name = DeviceSmartPlugFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSmartPlugFlexContainer.class),
	    @XmlElement(name = DeviceSteamClosetFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSteamClosetFlexContainer.class),
	    @XmlElement(name = DeviceStorageBatteryFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceStorageBatteryFlexContainer.class),
	    @XmlElement(name = DeviceSwitchFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSwitchFlexContainer.class),
	    @XmlElement(name = DeviceTelevisionFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceTelevisionFlexContainer.class),
	    @XmlElement(name = DeviceThermometerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceThermometerFlexContainer.class),
	    @XmlElement(name = DeviceThermostatFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceThermostatFlexContainer.class),
	    @XmlElement(name = DeviceWaterHeaterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceWaterHeaterFlexContainer.class),
	    @XmlElement(name = DeviceWaterValveFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceWaterValveFlexContainer.class),
	    @XmlElement(name = DeviceWeightScaleAndBodyCompositionAnalyserFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceWeightScaleAndBodyCompositionAnalyserFlexContainer.class),
	    @XmlElement(name = DeviceWindowShadeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceWindowShadeFlexContainer.class),
	    @XmlElement(name = DeviceSwitchFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSwitchFlexContainer.class),
	    @XmlElement(name = DeviceSmokeDetectorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceSmokeDetectorFlexContainer.class),
	    @XmlElement(name = DeviceContactDetectorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceContactDetectorFlexContainer.class),
	    @XmlElement(name = DeviceMotionDetectorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceMotionDetectorFlexContainer.class),
	    @XmlElement(name = DeviceFloodDetectorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceFloodDetectorFlexContainer.class),
	    @XmlElement(name = DeviceTemperatureDetectorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceTemperatureDetectorFlexContainer.class),
	    @XmlElement(name = DeviceWeatherStationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceWeatherStationFlexContainer.class),
	    @XmlElement(name = DeviceGasValveFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceGasValveFlexContainer.class),
	    @XmlElement(name = DeviceKettleFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceKettleFlexContainer.class),
	    @XmlElement(name = DeviceWarningFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DeviceWarningFlexContainer.class),
	    @XmlElement(name = LiquidLevelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = LiquidLevelFlexContainer.class),
	    @XmlElement(name = TemperatureAlarmFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = TemperatureAlarmFlexContainer.class),
	    @XmlElement(name = ContactSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = ContactSensorFlexContainer.class),
	    @XmlElement(name = ClothesDryerJobModeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = ClothesDryerJobModeFlexContainer.class),
	    @XmlElement(name = OximeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = OximeterFlexContainer.class),
	    @XmlElement(name = TurboFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = TurboFlexContainer.class),
	    @XmlElement(name = AirConJobModeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = AirConJobModeFlexContainer.class),
	    @XmlElement(name = EnergyGenerationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = EnergyGenerationFlexContainer.class),
	    @XmlElement(name = KeypadFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = KeypadFlexContainer.class),
	    @XmlElement(name = MotionSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = MotionSensorFlexContainer.class),
	    @XmlElement(name = SmokeSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = SmokeSensorFlexContainer.class),
	    @XmlElement(name = MediaSelectFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = MediaSelectFlexContainer.class),
	    @XmlElement(name = ElectricVehicleConnectorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = ElectricVehicleConnectorFlexContainer.class),
	    @XmlElement(name = AlarmSpeakerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = AlarmSpeakerFlexContainer.class),
	    @XmlElement(name = LockFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = LockFlexContainer.class),
	    @XmlElement(name = AirPurifierJobModeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = AirPurifierJobModeFlexContainer.class),
	    @XmlElement(name = GlucometerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = GlucometerFlexContainer.class),
	    @XmlElement(name = RemoteControlEnableFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = RemoteControlEnableFlexContainer.class),
	    @XmlElement(name = HeightFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = HeightFlexContainer.class),
	    @XmlElement(name = SpinLevelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = SpinLevelFlexContainer.class),
	    @XmlElement(name = AudioVolumeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = AudioVolumeFlexContainer.class),
	    @XmlElement(name = FoamingFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = FoamingFlexContainer.class),
	    @XmlElement(name = OvercurrentSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = OvercurrentSensorFlexContainer.class),
	    @XmlElement(name = WeightFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = WeightFlexContainer.class),
	    @XmlElement(name = SecurityModeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = SecurityModeFlexContainer.class),
	    @XmlElement(name = BodyCompositionAnalyserFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = BodyCompositionAnalyserFlexContainer.class),
	    @XmlElement(name = ClockFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = ClockFlexContainer.class),
	    @XmlElement(name = ClothesWasherDryerJobModeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = ClothesWasherDryerJobModeFlexContainer.class),
	    @XmlElement(name = FilterInfoFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = FilterInfoFlexContainer.class),
	    @XmlElement(name = PushButtonFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = PushButtonFlexContainer.class),
	    @XmlElement(name = ClothesWasherJobModeOptionFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = ClothesWasherJobModeOptionFlexContainer.class),
	    @XmlElement(name = KeepWarmFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = KeepWarmFlexContainer.class),
	    @XmlElement(name = PersonSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = PersonSensorFlexContainer.class),
	    @XmlElement(name = ColourSaturationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = ColourSaturationFlexContainer.class),
	    @XmlElement(name = ImpactSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = ImpactSensorFlexContainer.class),
	    @XmlElement(name = ClothesWasherJobModeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = ClothesWasherJobModeFlexContainer.class),
	    @XmlElement(name = RunStateFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = RunStateFlexContainer.class),
	    @XmlElement(name = TextMessageFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = TextMessageFlexContainer.class),
	    @XmlElement(name = BioElectricalImpedanceAnalysisFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = BioElectricalImpedanceAnalysisFlexContainer.class),
	    @XmlElement(name = OpenLevelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = OpenLevelFlexContainer.class),
	    @XmlElement(name = CredentialsFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = CredentialsFlexContainer.class),
	    @XmlElement(name = HotWaterSupplyFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = HotWaterSupplyFlexContainer.class),
	    @XmlElement(name = AirFlowFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = AirFlowFlexContainer.class),
	    @XmlElement(name = BatteryFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = BatteryFlexContainer.class),
	    @XmlElement(name = PulsemeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = PulsemeterFlexContainer.class),
	    @XmlElement(name = RobotCleanerJobModeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = RobotCleanerJobModeFlexContainer.class),
	    @XmlElement(name = LiquidRemainingFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = LiquidRemainingFlexContainer.class),
	    @XmlElement(name = AirQualitySensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = AirQualitySensorFlexContainer.class),
	    @XmlElement(name = PhoneCallFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = PhoneCallFlexContainer.class),
	    @XmlElement(name = WaterFlowFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = WaterFlowFlexContainer.class),
	    @XmlElement(name = TimerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = TimerFlexContainer.class),
	    @XmlElement(name = BrewingFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = BrewingFlexContainer.class),
	    @XmlElement(name = DishWasherJobModeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DishWasherJobModeFlexContainer.class),
	    @XmlElement(name = OperationModeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = OperationModeFlexContainer.class),
	    @XmlElement(name = FaultDetectionFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = FaultDetectionFlexContainer.class),
	    @XmlElement(name = BoilerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = BoilerFlexContainer.class),
	    @XmlElement(name = UvSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = UvSensorFlexContainer.class),
	    @XmlElement(name = BinarySwitchFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = BinarySwitchFlexContainer.class),
	    @XmlElement(name = WaterSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = WaterSensorFlexContainer.class),
	    @XmlElement(name = AcousticSensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = AcousticSensorFlexContainer.class),
	    @XmlElement(name = TemperatureFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = TemperatureFlexContainer.class),
	    @XmlElement(name = NumberValueFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = NumberValueFlexContainer.class),
	    @XmlElement(name = DehumidifierJobModeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DehumidifierJobModeFlexContainer.class),
	    @XmlElement(name = EnergyConsumptionFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = EnergyConsumptionFlexContainer.class),
	    @XmlElement(name = RecorderFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = RecorderFlexContainer.class),
	    @XmlElement(name = SphygmomanometerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = SphygmomanometerFlexContainer.class),
	    @XmlElement(name = GrinderFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = GrinderFlexContainer.class),
	    @XmlElement(name = PlayerControlFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = PlayerControlFlexContainer.class),
	    @XmlElement(name = OzoneMeterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = OzoneMeterFlexContainer.class),
	    @XmlElement(name = PrintQueueFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = PrintQueueFlexContainer.class),
	    @XmlElement(name = SessionDescriptionFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = SessionDescriptionFlexContainer.class),
	    @XmlElement(name = SignalStrengthFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = SignalStrengthFlexContainer.class),
	    @XmlElement(name = BinaryObjectFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = BinaryObjectFlexContainer.class),
	    @XmlElement(name = BarometerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = BarometerFlexContainer.class),
	    @XmlElement(name = HeatingZoneFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = HeatingZoneFlexContainer.class),
	    @XmlElement(name = CookerHoodJobModeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = CookerHoodJobModeFlexContainer.class),
	    @XmlElement(name = ColourFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = ColourFlexContainer.class),
	    @XmlElement(name = BrightnessFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = BrightnessFlexContainer.class),
	    @XmlElement(name = RefrigerationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = RefrigerationFlexContainer.class),
	    @XmlElement(name = AutoDocumentFeederFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = AutoDocumentFeederFlexContainer.class),
	    @XmlElement(name = TelevisionChannelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = TelevisionChannelFlexContainer.class),
	    @XmlElement(name = ThreeDPrinterFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = ThreeDPrinterFlexContainer.class),
	    @XmlElement(name = GeoLocationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = GeoLocationFlexContainer.class),
	    @XmlElement(name = DoorStatusFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = DoorStatusFlexContainer.class),
	    @XmlElement(name = RelativeHumidityFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = RelativeHumidityFlexContainer.class),
	    @XmlElement(name = SteamClosetJobModeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = SteamClosetJobModeFlexContainer.class),
	    @XmlElement(name = AnemometerFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = AnemometerFlexContainer.class),
	    @XmlElement(name = PowerSaveFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain", type = PowerSaveFlexContainer.class),
	})
	protected List<Resource> flexContainerOrContainerOrSubscription;

	@XmlAnyElement()
	protected CustomAttributList customAttributes;

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
			customAttributes = new CustomAttributList();
		}
		return customAttributes;
	}

//	public void setCustomAttributes(List<CustomAttribute> customAttributes) {
//		this.customAttributes = customAttributes;
//	}

	@XmlTransient
	public List<String> getCustomAttributeNames() {
		List<String> names = new ArrayList<String>();
		for(CustomAttribute ca : getCustomAttributes()) {
			names.add(ca.getShortName());
		}

		return names;
	}

	@XmlTransient
	public CustomAttribute getCustomAttribute(String name) {
		for(CustomAttribute ca : getCustomAttributes()) {
			if (ca.getShortName().equals(name)) {
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
