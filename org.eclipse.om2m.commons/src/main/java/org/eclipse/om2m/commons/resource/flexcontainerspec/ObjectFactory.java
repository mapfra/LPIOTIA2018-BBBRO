/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ObjectFactory : ObjectFactory

Created: 2018-07-04 10:25:10
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlRegistry;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRegistry
public class ObjectFactory {
	
	static public ObjectFactory instance = new ObjectFactory();
	
	static public ObjectFactory getInstance() {
		return instance;
	}
	
	private ObjectFactory() {
	}
	
	public AbstractFlexContainer createdeWSn() {
		return new DeviceWeatherStationFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeWSnAnnc() {
		return new DeviceWeatherStationFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeSPg() {
		return new DeviceSmartPlugFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeSPgAnnc() {
		return new DeviceSmartPlugFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createconSr() {
		return new ContactSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createconSrAnnc() {
		return new ContactSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createoximr() {
		return new OximeterFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createoximrAnnc() {
		return new OximeterFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdowCl() {
		return new DownChannelFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdowClAnnc() {
		return new DownChannelFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeDWr() {
		return new DeviceDishWasherFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeDWrAnnc() {
		return new DeviceDishWasherFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createmotSr() {
		return new MotionSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createmotSrAnnc() {
		return new MotionSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeWVe() {
		return new DeviceWaterValveFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeWVeAnnc() {
		return new DeviceWaterValveFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createsmoSr() {
		return new SmokeSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createsmoSrAnnc() {
		return new SmokeSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createmedSt() {
		return new MediaSelectFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createmedStAnnc() {
		return new MediaSelectFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdePOr() {
		return new DevicePulseOximeterFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdePOrAnnc() {
		return new DevicePulseOximeterFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createelVCr() {
		return new ElectricVehicleConnectorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createelVCrAnnc() {
		return new ElectricVehicleConnectorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createpreTk() {
		return new PreviousTrackFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createpreTkAnnc() {
		return new PreviousTrackFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeWHr() {
		return new DeviceWaterHeaterFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeWHrAnnc() {
		return new DeviceWaterHeaterFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createalaSr() {
		return new AlarmSpeakerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createalaSrAnnc() {
		return new AlarmSpeakerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createlock() {
		return new LockFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createlockAnnc() {
		return new LockFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createaPJMe() {
		return new AirPurifierJobModeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createaPJMeAnnc() {
		return new AirPurifierJobModeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevHr() {
		return new DeviceHumidifierFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevHrAnnc() {
		return new DeviceHumidifierFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createheigt() {
		return new HeightFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createheigtAnnc() {
		return new HeightFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdWSAB() {
		return new DeviceWeightScaleAndBodyCompositionAnalyserFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdWSABAnnc() {
		return new DeviceWeightScaleAndBodyCompositionAnalyserFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createupoVe() {
		return new UpVolumeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createupoVeAnnc() {
		return new UpVolumeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createoveSr() {
		return new OvercurrentSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createoveSrAnnc() {
		return new OvercurrentSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeCTr() {
		return new DeactivateClockTimerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeCTrAnnc() {
		return new DeactivateClockTimerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeMDr() {
		return new DeviceMotionDetectorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeMDrAnnc() {
		return new DeviceMotionDetectorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createsecMe() {
		return new SecurityModeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createsecMeAnnc() {
		return new SecurityModeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createboCAr() {
		return new BodyCompositionAnalyserFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createboCArAnnc() {
		return new BodyCompositionAnalyserFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdCDr() {
		return new DeviceContactDetectorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdCDrAnnc() {
		return new DeviceContactDetectorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeSBy() {
		return new DeviceStorageBatteryFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeSByAnnc() {
		return new DeviceStorageBatteryFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createfilIo() {
		return new FilterInfoFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createfilIoAnnc() {
		return new FilterInfoFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createstaDt() {
		return new Start3DprintFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createstaDtAnnc() {
		return new Start3DprintFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevDr() {
		return new DeviceDehumidifierFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevDrAnnc() {
		return new DeviceDehumidifierFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createcWJMO() {
		return new ClothesWasherJobModeOptionFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createcWJMOAnnc() {
		return new ClothesWasherJobModeOptionFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createkeeWm() {
		return new KeepWarmFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createkeeWmAnnc() {
		return new KeepWarmFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createperSr() {
		return new PersonSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createperSrAnnc() {
		return new PersonSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeOLp() {
		return new DeviceOutdoorLampFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeOLpAnnc() {
		return new DeviceOutdoorLampFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createcWJMe() {
		return new ClothesWasherJobModeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createcWJMeAnnc() {
		return new ClothesWasherJobModeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevTr() {
		return new DeviceThermometerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevTrAnnc() {
		return new DeviceThermometerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevWg() {
		return new DeviceWarningFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevWgAnnc() {
		return new DeviceWarningFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createrunSe() {
		return new RunStateFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createrunSeAnnc() {
		return new RunStateFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeFDr() {
		return new DeviceFloodDetectorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeFDrAnnc() {
		return new DeviceFloodDetectorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createopeLl() {
		return new OpenLevelFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createopeLlAnnc() {
		return new OpenLevelFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevCa() {
		return new DeviceCameraFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevCaAnnc() {
		return new DeviceCameraFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createairFw() {
		return new AirFlowFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createairFwAnnc() {
		return new AirFlowFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevTt() {
		return new DeviceThermostatFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevTtAnnc() {
		return new DeviceThermostatFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createbat() {
		return new BatteryFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createbatAnnc() {
		return new BatteryFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createrCJMe() {
		return new RobotCleanerJobModeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createrCJMeAnnc() {
		return new RobotCleanerJobModeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createliqRg() {
		return new LiquidRemainingFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createliqRgAnnc() {
		return new LiquidRemainingFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createphoCl() {
		return new PhoneCallFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createphoClAnnc() {
		return new PhoneCallFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeCHd() {
		return new DeviceCookerHoodFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeCHdAnnc() {
		return new DeviceCookerHoodFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdWJMe() {
		return new DishWasherJobModeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdWJMeAnnc() {
		return new DishWasherJobModeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createboilr() {
		return new BoilerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createboilrAnnc() {
		return new BoilerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createbinSh() {
		return new BinarySwitchFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createbinShAnnc() {
		return new BinarySwitchFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createwatSr() {
		return new WaterSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createwatSrAnnc() {
		return new WaterSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createacoSr() {
		return new AcousticSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createacoSrAnnc() {
		return new AcousticSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createtempe() {
		return new TemperatureFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createtempeAnnc() {
		return new TemperatureFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdowVe() {
		return new DownVolumeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdowVeAnnc() {
		return new DownVolumeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeJMe() {
		return new DehumidifierJobModeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeJMeAnnc() {
		return new DehumidifierJobModeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevFn() {
		return new DeviceFanFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevFnAnnc() {
		return new DeviceFanFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createclose() {
		return new CloseFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createcloseAnnc() {
		return new CloseFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdSTBx() {
		return new DeviceSetTopBoxFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdSTBxAnnc() {
		return new DeviceSetTopBoxFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createeneCn() {
		return new EnergyConsumptionFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createeneCnAnnc() {
		return new EnergyConsumptionFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createsphyr() {
		return new SphygmomanometerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createsphyrAnnc() {
		return new SphygmomanometerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeAPr() {
		return new DeviceAirPurifierFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeAPrAnnc() {
		return new DeviceAirPurifierFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdHRMr() {
		return new DeviceHeartRateMonitorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdHRMrAnnc() {
		return new DeviceHeartRateMonitorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createozoMr() {
		return new OzoneMeterFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createozoMrAnnc() {
		return new OzoneMeterFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createsigSh() {
		return new SignalStrengthFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createsigShAnnc() {
		return new SignalStrengthFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createbinOt() {
		return new BinaryObjectFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createbinOtAnnc() {
		return new BinaryObjectFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeCWr() {
		return new DeviceClothesWasherFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeCWrAnnc() {
		return new DeviceClothesWasherFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createtogge() {
		return new ToggleFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createtoggeAnnc() {
		return new ToggleFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeFPe() {
		return new DeviceFoodProbeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeFPeAnnc() {
		return new DeviceFoodProbeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createheaZe() {
		return new HeatingZoneFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createheaZeAnnc() {
		return new HeatingZoneFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createcall() {
		return new CallFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createcallAnnc() {
		return new CallFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createinNVe() {
		return new IncrementNumberValueFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createinNVeAnnc() {
		return new IncrementNumberValueFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdCWDr() {
		return new DeviceClothesWasherDryerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdCWDrAnnc() {
		return new DeviceClothesWasherDryerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createanswr() {
		return new AnswerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createanswrAnnc() {
		return new AnswerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdSEMr() {
		return new DeviceSmartElectricMeterFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdSEMrAnnc() {
		return new DeviceSmartElectricMeterFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createthDPr() {
		return new ThreeDPrinterFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createthDPrAnnc() {
		return new ThreeDPrinterFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevPr() {
		return new DevicePrinterFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevPrAnnc() {
		return new DevicePrinterFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeWSe() {
		return new DeviceWindowShadeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeWSeAnnc() {
		return new DeviceWindowShadeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeSDr() {
		return new DeviceSmokeDetectorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeSDrAnnc() {
		return new DeviceSmokeDetectorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevKe() {
		return new DeviceKettleFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevKeAnnc() {
		return new DeviceKettleFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createpowS0() {
		return new PowerSaveFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createpowS0Annc() {
		return new PowerSaveFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createopen() {
		return new OpenFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createopenAnnc() {
		return new OpenFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createliqLl() {
		return new LiquidLevelFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createliqLlAnnc() {
		return new LiquidLevelFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createtemAm() {
		return new TemperatureAlarmFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createtemAmAnnc() {
		return new TemperatureAlarmFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createcDJMe() {
		return new ClothesDryerJobModeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createcDJMeAnnc() {
		return new ClothesDryerJobModeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createturbo() {
		return new TurboFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createturboAnnc() {
		return new TurboFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createaCJMe() {
		return new AirConJobModeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createaCJMeAnnc() {
		return new AirConJobModeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createeneGn() {
		return new EnergyGenerationFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createeneGnAnnc() {
		return new EnergyGenerationFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createkeypd() {
		return new KeypadFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createkeypdAnnc() {
		return new KeypadFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeDLk() {
		return new DeviceDoorLockFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeDLkAnnc() {
		return new DeviceDoorLockFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevRr() {
		return new DeviceRefrigeratorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevRrAnnc() {
		return new DeviceRefrigeratorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeSPl() {
		return new DeviceSecurityPanelFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeSPlAnnc() {
		return new DeviceSecurityPanelFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createhangp() {
		return new HangupFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createhangpAnnc() {
		return new HangupFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevLt() {
		return new DeviceLightFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevLtAnnc() {
		return new DeviceLightFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createglucr() {
		return new GlucometerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createglucrAnnc() {
		return new GlucometerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdBPMr() {
		return new DeviceBloodPressureMonitorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdBPMrAnnc() {
		return new DeviceBloodPressureMonitorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createreCEe() {
		return new RemoteControlEnableFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createreCEeAnnc() {
		return new RemoteControlEnableFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeCDr() {
		return new DeviceClothesDryerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeCDrAnnc() {
		return new DeviceClothesDryerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createspiLl() {
		return new SpinLevelFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createspiLlAnnc() {
		return new SpinLevelFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createaudVe() {
		return new AudioVolumeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createaudVeAnnc() {
		return new AudioVolumeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createfoamg() {
		return new FoamingFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createfoamgAnnc() {
		return new FoamingFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createweigt() {
		return new WeightFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createweigtAnnc() {
		return new WeightFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createclock() {
		return new ClockFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createclockAnnc() {
		return new ClockFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevD0() {
		return new DeviceDoorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevD0Annc() {
		return new DeviceDoorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createcWDJM() {
		return new ClothesWasherDryerJobModeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createcWDJMAnnc() {
		return new ClothesWasherDryerJobModeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeCMe() {
		return new DeviceCoffeeMachineFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeCMeAnnc() {
		return new DeviceCoffeeMachineFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevTn() {
		return new DeviceTelevisionFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevTnAnnc() {
		return new DeviceTelevisionFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeSCt() {
		return new DeviceSteamClosetFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeSCtAnnc() {
		return new DeviceSteamClosetFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createpusBn() {
		return new PushButtonFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createpusBnAnnc() {
		return new PushButtonFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeNVe() {
		return new DecrementNumberValueFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeNVeAnnc() {
		return new DecrementNumberValueFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createcolSn() {
		return new ColourSaturationFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createcolSnAnnc() {
		return new ColourSaturationFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createimpSr() {
		return new ImpactSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createimpSrAnnc() {
		return new ImpactSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeRCr() {
		return new DeviceRobotCleanerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeRCrAnnc() {
		return new DeviceRobotCleanerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeACr() {
		return new DeviceAirConditionerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeACrAnnc() {
		return new DeviceAirConditionerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createtexMe() {
		return new TextMessageFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createtexMeAnnc() {
		return new TextMessageFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevCp() {
		return new DeviceCooktopFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevCpAnnc() {
		return new DeviceCooktopFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createbEIAs() {
		return new BioElectricalImpedanceAnalysisFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createbEIAsAnnc() {
		return new BioElectricalImpedanceAnalysisFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevGr() {
		return new DeviceGlucosemeterFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevGrAnnc() {
		return new DeviceGlucosemeterFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createcreds() {
		return new CredentialsFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createcredsAnnc() {
		return new CredentialsFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createhoWSy() {
		return new HotWaterSupplyFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createhoWSyAnnc() {
		return new HotWaterSupplyFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdTDPr() {
		return new DeviceThreeDPrinterFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdTDPrAnnc() {
		return new DeviceThreeDPrinterFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevOn() {
		return new DeviceOvenFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevOnAnnc() {
		return new DeviceOvenFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createpulsr() {
		return new PulsemeterFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createpulsrAnnc() {
		return new PulsemeterFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createaiQSr() {
		return new AirQualitySensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createaiQSrAnnc() {
		return new AirQualitySensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createwatFw() {
		return new WaterFlowFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createwatFwAnnc() {
		return new WaterFlowFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createtimer() {
		return new TimerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createtimerAnnc() {
		return new TimerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevFr() {
		return new DeviceFreezerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevFrAnnc() {
		return new DeviceFreezerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createbrewg() {
		return new BrewingFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createbrewgAnnc() {
		return new BrewingFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createopeMe() {
		return new OperationModeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createopeMeAnnc() {
		return new OperationModeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createfauDn() {
		return new FaultDetectionFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createfauDnAnnc() {
		return new FaultDetectionFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createuveSr() {
		return new UvSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createuveSrAnnc() {
		return new UvSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createreTMe() {
		return new ResetTextMessageFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createreTMeAnnc() {
		return new ResetTextMessageFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createnumVe() {
		return new NumberValueFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createnumVeAnnc() {
		return new NumberValueFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createstoDt() {
		return new Stop3DprintFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createstoDtAnnc() {
		return new Stop3DprintFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createrecor() {
		return new RecorderFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createrecorAnnc() {
		return new RecorderFlexContainerAnnc();
	}
	
	public AbstractFlexContainer creategrinr() {
		return new GrinderFlexContainer();
	}
	
	public AbstractFlexContainerAnnc creategrinrAnnc() {
		return new GrinderFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createplaCl() {
		return new PlayerControlFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createplaClAnnc() {
		return new PlayerControlFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createpriQe() {
		return new PrintQueueFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createpriQeAnnc() {
		return new PrintQueueFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createsesDn() {
		return new SessionDescriptionFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createsesDnAnnc() {
		return new SessionDescriptionFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createuphCl() {
		return new UpChannelFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createuphClAnnc() {
		return new UpChannelFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdMFPr() {
		return new DeviceMultiFunctionPrinterFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdMFPrAnnc() {
		return new DeviceMultiFunctionPrinterFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createnexTk() {
		return new NextTrackFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createnexTkAnnc() {
		return new NextTrackFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createbaror() {
		return new BarometerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createbarorAnnc() {
		return new BarometerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeGVe() {
		return new DeviceGasValveFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeGVeAnnc() {
		return new DeviceGasValveFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createreNVe() {
		return new ResetNumberValueFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createreNVeAnnc() {
		return new ResetNumberValueFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createcHJMe() {
		return new CookerHoodJobModeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createcHJMeAnnc() {
		return new CookerHoodJobModeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdEVCr() {
		return new DeviceElectricVehicleChargerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdEVCrAnnc() {
		return new DeviceElectricVehicleChargerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeTDr() {
		return new DeviceTemperatureDetectorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeTDrAnnc() {
		return new DeviceTemperatureDetectorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createcolor() {
		return new ColourFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createcolorAnnc() {
		return new ColourFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevMn() {
		return new DeviceMicrogenerationFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevMnAnnc() {
		return new DeviceMicrogenerationFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevSr() {
		return new DeviceScannerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevSrAnnc() {
		return new DeviceScannerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createbrigs() {
		return new BrightnessFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createbrigsAnnc() {
		return new BrightnessFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createrefrn() {
		return new RefrigerationFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createrefrnAnnc() {
		return new RefrigerationFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createauDFr() {
		return new AutoDocumentFeederFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createauDFrAnnc() {
		return new AutoDocumentFeederFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createtelCl() {
		return new TelevisionChannelFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createtelClAnnc() {
		return new TelevisionChannelFlexContainerAnnc();
	}
	
	public AbstractFlexContainer creategeoLn() {
		return new GeoLocationFlexContainer();
	}
	
	public AbstractFlexContainerAnnc creategeoLnAnnc() {
		return new GeoLocationFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdHCCT() {
		return new DeviceHomeCCTVFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdHCCTAnnc() {
		return new DeviceHomeCCTVFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdooSs() {
		return new DoorStatusFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdooSsAnnc() {
		return new DoorStatusFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createacCTr() {
		return new ActivateClockTimerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createacCTrAnnc() {
		return new ActivateClockTimerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createrelHy() {
		return new RelativeHumidityFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createrelHyAnnc() {
		return new RelativeHumidityFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createsCJMe() {
		return new SteamClosetJobModeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createsCJMeAnnc() {
		return new SteamClosetJobModeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdAQMr() {
		return new DeviceAirQualityMonitorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdAQMrAnnc() {
		return new DeviceAirQualityMonitorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdeARr() {
		return new DeviceAudioReceiverFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdeARrAnnc() {
		return new DeviceAudioReceiverFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createanemr() {
		return new AnemometerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createanemrAnnc() {
		return new AnemometerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createdevSh() {
		return new DeviceSwitchFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createdevShAnnc() {
		return new DeviceSwitchFlexContainerAnnc();
	}
	
    
}
