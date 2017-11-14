/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ObjectFactory : ObjectFactory



Created: 2017-09-28 17:26:40
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlRegistry;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRegistry
public class ObjectFactory {
	
	public AbstractFlexContainer createalaSr() {
		return new AlarmSpeakerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createalaSrAnnc() {
		return new AlarmSpeakerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createAuVIt() {
		return new AudioVideoInputFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createAuVItAnnc() {
		return new AudioVideoInputFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createAudVe() {
		return new AudioVolumeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createAudVeAnnc() {
		return new AudioVolumeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createUpoVe() {
		return new UpVolumeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createUpoVeAnnc() {
		return new UpVolumeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDowVe() {
		return new DownVolumeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDowVeAnnc() {
		return new DownVolumeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createBatty() {
		return new BatteryFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createBattyAnnc() {
		return new BatteryFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createBinSh() {
		return new BinarySwitchFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createBinShAnnc() {
		return new BinarySwitchFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createTogge() {
		return new ToggleFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createToggeAnnc() {
		return new ToggleFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createBEIAs() {
		return new BioElectricalImpedanceAnalysisFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createBEIAsAnnc() {
		return new BioElectricalImpedanceAnalysisFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createBoilr() {
		return new BoilerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createBoilrAnnc() {
		return new BoilerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createBrigs() {
		return new BrightnessFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createBrigsAnnc() {
		return new BrightnessFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createClock() {
		return new ClockFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createClockAnnc() {
		return new ClockFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createColor() {
		return new ColourFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createColorAnnc() {
		return new ColourFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createColSn() {
		return new ColourSaturationFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createColSnAnnc() {
		return new ColourSaturationFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDooSs() {
		return new DoorStatusFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDooSsAnnc() {
		return new DoorStatusFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createElVCr() {
		return new ElectricVehicleConnectorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createElVCrAnnc() {
		return new ElectricVehicleConnectorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createEneCn() {
		return new EnergyConsumptionFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createEneCnAnnc() {
		return new EnergyConsumptionFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createEneGn() {
		return new EnergyGenerationFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createEneGnAnnc() {
		return new EnergyGenerationFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createFauDn() {
		return new FaultDetectionFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createFauDnAnnc() {
		return new FaultDetectionFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createHeigt() {
		return new HeightFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createHeigtAnnc() {
		return new HeightFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createHoWSy() {
		return new HotWaterSupplyFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createHoWSyAnnc() {
		return new HotWaterSupplyFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createKeypd() {
		return new KeypadFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createKeypdAnnc() {
		return new KeypadFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createMotSr() {
		return new MotionSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createMotSrAnnc() {
		return new MotionSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createOximr() {
		return new OximeterFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createOximrAnnc() {
		return new OximeterFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createPowSe() {
		return new PowerSaveFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createPowSeAnnc() {
		return new PowerSaveFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createPusBn() {
		return new PushButtonFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createPusBnAnnc() {
		return new PushButtonFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createRecor() {
		return new RecorderFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createRecorAnnc() {
		return new RecorderFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createRefrn() {
		return new RefrigerationFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createRefrnAnnc() {
		return new RefrigerationFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createRelHy() {
		return new RelativeHumidityFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createRelHyAnnc() {
		return new RelativeHumidityFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createRinLl() {
		return new RinseLevelFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createRinLlAnnc() {
		return new RinseLevelFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createRunMe() {
		return new RunModeFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createRunMeAnnc() {
		return new RunModeFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createSigSh() {
		return new SignalStrengthFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createSigShAnnc() {
		return new SignalStrengthFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createSmoSr() {
		return new SmokeSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createSmoSrAnnc() {
		return new SmokeSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createSpiLl() {
		return new SpinLevelFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createSpiLlAnnc() {
		return new SpinLevelFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createTelCl() {
		return new TelevisionChannelFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createTelClAnnc() {
		return new TelevisionChannelFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createUphCl() {
		return new UpChannelFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createUphClAnnc() {
		return new UpChannelFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDowCl() {
		return new DownChannelFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDowClAnnc() {
		return new DownChannelFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createTempe() {
		return new TemperatureFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createTempeAnnc() {
		return new TemperatureFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createTemAm() {
		return new TemperatureAlarmFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createTemAmAnnc() {
		return new TemperatureAlarmFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createTimer() {
		return new TimerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createTimerAnnc() {
		return new TimerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createAcCTr() {
		return new ActivateClockTimerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createAcCTrAnnc() {
		return new ActivateClockTimerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeCTr() {
		return new DeactivateClockTimerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeCTrAnnc() {
		return new DeactivateClockTimerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createTurbo() {
		return new TurboFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createTurboAnnc() {
		return new TurboFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createWatFw() {
		return new WaterFlowFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createWatFwAnnc() {
		return new WaterFlowFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createWatLl() {
		return new WaterLevelFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createWatLlAnnc() {
		return new WaterLevelFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createWatSr() {
		return new WaterSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createWatSrAnnc() {
		return new WaterSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createWeigt() {
		return new WeightFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createWeigtAnnc() {
		return new WeightFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createWind() {
		return new WindFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createWindAnnc() {
		return new WindFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createStreg() {
		return new StreamingFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createStregAnnc() {
		return new StreamingFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createPerSr() {
		return new PersonSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createPerSrAnnc() {
		return new PersonSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createBrewg() {
		return new BrewingFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createBrewgAnnc() {
		return new BrewingFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createLiqLl() {
		return new LiquidLevelFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createLiqLlAnnc() {
		return new LiquidLevelFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createGrinr() {
		return new GrinderFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createGrinrAnnc() {
		return new GrinderFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createFoamg() {
		return new FoamingFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createFoamgAnnc() {
		return new FoamingFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createKeeWm() {
		return new KeepWarmFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createKeeWmAnnc() {
		return new KeepWarmFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createConSr() {
		return new ContactSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createConSrAnnc() {
		return new ContactSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createalSer() {
		return new AlarmSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createalSerAnnc() {
		return new AlarmSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createLock() {
		return new LockFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createLockAnnc() {
		return new LockFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createAtPSr() {
		return new AtmosphericPressureSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createAtPSrAnnc() {
		return new AtmosphericPressureSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createNoise() {
		return new NoiseFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createNoiseAnnc() {
		return new NoiseFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createECDSr() {
		return new ExtendedCarbonDioxideSensorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createECDSrAnnc() {
		return new ExtendedCarbonDioxideSensorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createNumVe() {
		return new NumberValueFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createNumVeAnnc() {
		return new NumberValueFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeNVe() {
		return new DecrementNumberValueFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeNVeAnnc() {
		return new DecrementNumberValueFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createInNVe() {
		return new IncrementNumberValueFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createInNVeAnnc() {
		return new IncrementNumberValueFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createReNVe() {
		return new ResetNumberValueFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createReNVeAnnc() {
		return new ResetNumberValueFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeACr() {
		return new DeviceAirConditionerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeACrAnnc() {
		return new DeviceAirConditionerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeCWr() {
		return new DeviceClothesWasherFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeCWrAnnc() {
		return new DeviceClothesWasherFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDEVCr() {
		return new DeviceElectricVehicleChargerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDEVCrAnnc() {
		return new DeviceElectricVehicleChargerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDevLt() {
		return new DeviceLightFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDevLtAnnc() {
		return new DeviceLightFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDevMn() {
		return new DeviceMicrogenerationFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDevMnAnnc() {
		return new DeviceMicrogenerationFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDevOn() {
		return new DeviceOvenFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDevOnAnnc() {
		return new DeviceOvenFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDevRr() {
		return new DeviceRefrigeratorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDevRrAnnc() {
		return new DeviceRefrigeratorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeRCr() {
		return new DeviceRobotCleanerFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeRCrAnnc() {
		return new DeviceRobotCleanerFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDSEMr() {
		return new DeviceSmartElectricMeterFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDSEMrAnnc() {
		return new DeviceSmartElectricMeterFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeSBy() {
		return new DeviceStorageBatteryFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeSByAnnc() {
		return new DeviceStorageBatteryFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDevTn() {
		return new DeviceTelevisionFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDevTnAnnc() {
		return new DeviceTelevisionFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDevTt() {
		return new DeviceThermostatFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDevTtAnnc() {
		return new DeviceThermostatFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeWHr() {
		return new DeviceWaterHeaterFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeWHrAnnc() {
		return new DeviceWaterHeaterFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDevCa() {
		return new DeviceCameraFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDevCaAnnc() {
		return new DeviceCameraFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeCMe() {
		return new DeviceCoffeeMachineFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeCMeAnnc() {
		return new DeviceCoffeeMachineFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeCDr() {
		return new DeviceContactDetectorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeCDrAnnc() {
		return new DeviceContactDetectorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDevDr() {
		return new DeviceDoorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDevDrAnnc() {
		return new DeviceDoorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeFDr() {
		return new DeviceFloodDetectorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeFDrAnnc() {
		return new DeviceFloodDetectorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeGVe() {
		return new DeviceGasValveFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeGVeAnnc() {
		return new DeviceGasValveFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeMDr() {
		return new DeviceMotionDetectorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeMDrAnnc() {
		return new DeviceMotionDetectorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeSDr() {
		return new DeviceSmokeDetectorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeSDrAnnc() {
		return new DeviceSmokeDetectorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeSEr() {
		return new DeviceSmokeExtractorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeSErAnnc() {
		return new DeviceSmokeExtractorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeSBn() {
		return new DeviceSwitchButtonFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeSBnAnnc() {
		return new DeviceSwitchButtonFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeTDr() {
		return new DeviceTemperatureDetectorFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeTDrAnnc() {
		return new DeviceTemperatureDetectorFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeWDe() {
		return new DeviceWarningDeviceFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeWDeAnnc() {
		return new DeviceWarningDeviceFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeWVe() {
		return new DeviceWaterValveFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeWVeAnnc() {
		return new DeviceWaterValveFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeWSn() {
		return new DeviceWeatherStationFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeWSnAnnc() {
		return new DeviceWeatherStationFlexContainerAnnc();
	}
	
	public AbstractFlexContainer createDeNDe() {
		return new DeviceNumberDeviceFlexContainer();
	}
	
	public AbstractFlexContainerAnnc createDeNDeAnnc() {
		return new DeviceNumberDeviceFlexContainerAnnc();
	}
	
}