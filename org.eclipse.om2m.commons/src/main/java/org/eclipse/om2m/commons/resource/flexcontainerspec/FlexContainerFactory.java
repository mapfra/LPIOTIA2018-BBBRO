/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

FlexContainerFactory : FlexContainerFactory



Created: 2017-09-28 17:26:40
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.FlexContainerAnnc;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

public class FlexContainerFactory {
	
	public static AbstractFlexContainer getSpecializationFlexContainer(String shortName) {
		switch(shortName) {
		case AlarmSpeakerFlexContainer.SHORT_NAME:
			return new AlarmSpeakerFlexContainer();
		case AudioVideoInputFlexContainer.SHORT_NAME:
			return new AudioVideoInputFlexContainer();
		case AudioVolumeFlexContainer.SHORT_NAME:
			return new AudioVolumeFlexContainer();
		case UpVolumeFlexContainer.SHORT_NAME:
			return new UpVolumeFlexContainer();
		case DownVolumeFlexContainer.SHORT_NAME:
			return new DownVolumeFlexContainer();
		case BatteryFlexContainer.SHORT_NAME:
			return new BatteryFlexContainer();
		case BinarySwitchFlexContainer.SHORT_NAME:
			return new BinarySwitchFlexContainer();
		case ToggleFlexContainer.SHORT_NAME:
			return new ToggleFlexContainer();
		case BioElectricalImpedanceAnalysisFlexContainer.SHORT_NAME:
			return new BioElectricalImpedanceAnalysisFlexContainer();
		case BoilerFlexContainer.SHORT_NAME:
			return new BoilerFlexContainer();
		case BrightnessFlexContainer.SHORT_NAME:
			return new BrightnessFlexContainer();
		case ClockFlexContainer.SHORT_NAME:
			return new ClockFlexContainer();
		case ColourFlexContainer.SHORT_NAME:
			return new ColourFlexContainer();
		case ColourSaturationFlexContainer.SHORT_NAME:
			return new ColourSaturationFlexContainer();
		case DoorStatusFlexContainer.SHORT_NAME:
			return new DoorStatusFlexContainer();
		case ElectricVehicleConnectorFlexContainer.SHORT_NAME:
			return new ElectricVehicleConnectorFlexContainer();
		case EnergyConsumptionFlexContainer.SHORT_NAME:
			return new EnergyConsumptionFlexContainer();
		case EnergyGenerationFlexContainer.SHORT_NAME:
			return new EnergyGenerationFlexContainer();
		case FaultDetectionFlexContainer.SHORT_NAME:
			return new FaultDetectionFlexContainer();
		case HeightFlexContainer.SHORT_NAME:
			return new HeightFlexContainer();
		case HotWaterSupplyFlexContainer.SHORT_NAME:
			return new HotWaterSupplyFlexContainer();
		case KeypadFlexContainer.SHORT_NAME:
			return new KeypadFlexContainer();
		case MotionSensorFlexContainer.SHORT_NAME:
			return new MotionSensorFlexContainer();
		case OximeterFlexContainer.SHORT_NAME:
			return new OximeterFlexContainer();
		case PowerSaveFlexContainer.SHORT_NAME:
			return new PowerSaveFlexContainer();
		case PushButtonFlexContainer.SHORT_NAME:
			return new PushButtonFlexContainer();
		case RecorderFlexContainer.SHORT_NAME:
			return new RecorderFlexContainer();
		case RefrigerationFlexContainer.SHORT_NAME:
			return new RefrigerationFlexContainer();
		case RelativeHumidityFlexContainer.SHORT_NAME:
			return new RelativeHumidityFlexContainer();
		case RinseLevelFlexContainer.SHORT_NAME:
			return new RinseLevelFlexContainer();
		case RunModeFlexContainer.SHORT_NAME:
			return new RunModeFlexContainer();
		case SignalStrengthFlexContainer.SHORT_NAME:
			return new SignalStrengthFlexContainer();
		case SmokeSensorFlexContainer.SHORT_NAME:
			return new SmokeSensorFlexContainer();
		case SpinLevelFlexContainer.SHORT_NAME:
			return new SpinLevelFlexContainer();
		case TelevisionChannelFlexContainer.SHORT_NAME:
			return new TelevisionChannelFlexContainer();
		case UpChannelFlexContainer.SHORT_NAME:
			return new UpChannelFlexContainer();
		case DownChannelFlexContainer.SHORT_NAME:
			return new DownChannelFlexContainer();
		case TemperatureFlexContainer.SHORT_NAME:
			return new TemperatureFlexContainer();
		case TemperatureAlarmFlexContainer.SHORT_NAME:
			return new TemperatureAlarmFlexContainer();
		case TimerFlexContainer.SHORT_NAME:
			return new TimerFlexContainer();
		case ActivateClockTimerFlexContainer.SHORT_NAME:
			return new ActivateClockTimerFlexContainer();
		case DeactivateClockTimerFlexContainer.SHORT_NAME:
			return new DeactivateClockTimerFlexContainer();
		case TurboFlexContainer.SHORT_NAME:
			return new TurboFlexContainer();
		case WaterFlowFlexContainer.SHORT_NAME:
			return new WaterFlowFlexContainer();
		case WaterLevelFlexContainer.SHORT_NAME:
			return new WaterLevelFlexContainer();
		case WaterSensorFlexContainer.SHORT_NAME:
			return new WaterSensorFlexContainer();
		case WeightFlexContainer.SHORT_NAME:
			return new WeightFlexContainer();
		case WindFlexContainer.SHORT_NAME:
			return new WindFlexContainer();
		case StreamingFlexContainer.SHORT_NAME:
			return new StreamingFlexContainer();
		case PersonSensorFlexContainer.SHORT_NAME:
			return new PersonSensorFlexContainer();
		case BrewingFlexContainer.SHORT_NAME:
			return new BrewingFlexContainer();
		case LiquidLevelFlexContainer.SHORT_NAME:
			return new LiquidLevelFlexContainer();
		case GrinderFlexContainer.SHORT_NAME:
			return new GrinderFlexContainer();
		case FoamingFlexContainer.SHORT_NAME:
			return new FoamingFlexContainer();
		case KeepWarmFlexContainer.SHORT_NAME:
			return new KeepWarmFlexContainer();
		case ContactSensorFlexContainer.SHORT_NAME:
			return new ContactSensorFlexContainer();
		case AlarmSensorFlexContainer.SHORT_NAME:
			return new AlarmSensorFlexContainer();
		case LockFlexContainer.SHORT_NAME:
			return new LockFlexContainer();
		case AtmosphericPressureSensorFlexContainer.SHORT_NAME:
			return new AtmosphericPressureSensorFlexContainer();
		case NoiseFlexContainer.SHORT_NAME:
			return new NoiseFlexContainer();
		case ExtendedCarbonDioxideSensorFlexContainer.SHORT_NAME:
			return new ExtendedCarbonDioxideSensorFlexContainer();
		case NumberValueFlexContainer.SHORT_NAME:
			return new NumberValueFlexContainer();
		case DecrementNumberValueFlexContainer.SHORT_NAME:
			return new DecrementNumberValueFlexContainer();
		case IncrementNumberValueFlexContainer.SHORT_NAME:
			return new IncrementNumberValueFlexContainer();
		case ResetNumberValueFlexContainer.SHORT_NAME:
			return new ResetNumberValueFlexContainer();
		case DeviceAirConditionerFlexContainer.SHORT_NAME:
			return new DeviceAirConditionerFlexContainer();
		case DeviceClothesWasherFlexContainer.SHORT_NAME:
			return new DeviceClothesWasherFlexContainer();
		case DeviceElectricVehicleChargerFlexContainer.SHORT_NAME:
			return new DeviceElectricVehicleChargerFlexContainer();
		case DeviceLightFlexContainer.SHORT_NAME:
			return new DeviceLightFlexContainer();
		case DeviceMicrogenerationFlexContainer.SHORT_NAME:
			return new DeviceMicrogenerationFlexContainer();
		case DeviceOvenFlexContainer.SHORT_NAME:
			return new DeviceOvenFlexContainer();
		case DeviceRefrigeratorFlexContainer.SHORT_NAME:
			return new DeviceRefrigeratorFlexContainer();
		case DeviceRobotCleanerFlexContainer.SHORT_NAME:
			return new DeviceRobotCleanerFlexContainer();
		case DeviceSmartElectricMeterFlexContainer.SHORT_NAME:
			return new DeviceSmartElectricMeterFlexContainer();
		case DeviceStorageBatteryFlexContainer.SHORT_NAME:
			return new DeviceStorageBatteryFlexContainer();
		case DeviceTelevisionFlexContainer.SHORT_NAME:
			return new DeviceTelevisionFlexContainer();
		case DeviceThermostatFlexContainer.SHORT_NAME:
			return new DeviceThermostatFlexContainer();
		case DeviceWaterHeaterFlexContainer.SHORT_NAME:
			return new DeviceWaterHeaterFlexContainer();
		case DeviceCameraFlexContainer.SHORT_NAME:
			return new DeviceCameraFlexContainer();
		case DeviceCoffeeMachineFlexContainer.SHORT_NAME:
			return new DeviceCoffeeMachineFlexContainer();
		case DeviceContactDetectorFlexContainer.SHORT_NAME:
			return new DeviceContactDetectorFlexContainer();
		case DeviceDoorFlexContainer.SHORT_NAME:
			return new DeviceDoorFlexContainer();
		case DeviceFloodDetectorFlexContainer.SHORT_NAME:
			return new DeviceFloodDetectorFlexContainer();
		case DeviceGasValveFlexContainer.SHORT_NAME:
			return new DeviceGasValveFlexContainer();
		case DeviceMotionDetectorFlexContainer.SHORT_NAME:
			return new DeviceMotionDetectorFlexContainer();
		case DeviceSmokeDetectorFlexContainer.SHORT_NAME:
			return new DeviceSmokeDetectorFlexContainer();
		case DeviceSmokeExtractorFlexContainer.SHORT_NAME:
			return new DeviceSmokeExtractorFlexContainer();
		case DeviceSwitchButtonFlexContainer.SHORT_NAME:
			return new DeviceSwitchButtonFlexContainer();
		case DeviceTemperatureDetectorFlexContainer.SHORT_NAME:
			return new DeviceTemperatureDetectorFlexContainer();
		case DeviceWarningDeviceFlexContainer.SHORT_NAME:
			return new DeviceWarningDeviceFlexContainer();
		case DeviceWaterValveFlexContainer.SHORT_NAME:
			return new DeviceWaterValveFlexContainer();
		case DeviceWeatherStationFlexContainer.SHORT_NAME:
			return new DeviceWeatherStationFlexContainer();
		case DeviceNumberDeviceFlexContainer.SHORT_NAME:
			return new DeviceNumberDeviceFlexContainer();
		}
		return new FlexContainer();
	}
	
	public static AbstractFlexContainerAnnc getSpecializationFlexContainerAnnc(String shortName) {
		switch(shortName) {
		case AlarmSpeakerFlexContainerAnnc.SHORT_NAME:
			return new AlarmSpeakerFlexContainerAnnc();
		case AudioVideoInputFlexContainerAnnc.SHORT_NAME:
			return new AudioVideoInputFlexContainerAnnc();
		case AudioVolumeFlexContainerAnnc.SHORT_NAME:
			return new AudioVolumeFlexContainerAnnc();
		case UpVolumeFlexContainerAnnc.SHORT_NAME:
			return new UpVolumeFlexContainerAnnc();
		case DownVolumeFlexContainerAnnc.SHORT_NAME:
			return new DownVolumeFlexContainerAnnc();
		case BatteryFlexContainerAnnc.SHORT_NAME:
			return new BatteryFlexContainerAnnc();
		case BinarySwitchFlexContainerAnnc.SHORT_NAME:
			return new BinarySwitchFlexContainerAnnc();
		case ToggleFlexContainerAnnc.SHORT_NAME:
			return new ToggleFlexContainerAnnc();
		case BioElectricalImpedanceAnalysisFlexContainerAnnc.SHORT_NAME:
			return new BioElectricalImpedanceAnalysisFlexContainerAnnc();
		case BoilerFlexContainerAnnc.SHORT_NAME:
			return new BoilerFlexContainerAnnc();
		case BrightnessFlexContainerAnnc.SHORT_NAME:
			return new BrightnessFlexContainerAnnc();
		case ClockFlexContainerAnnc.SHORT_NAME:
			return new ClockFlexContainerAnnc();
		case ColourFlexContainerAnnc.SHORT_NAME:
			return new ColourFlexContainerAnnc();
		case ColourSaturationFlexContainerAnnc.SHORT_NAME:
			return new ColourSaturationFlexContainerAnnc();
		case DoorStatusFlexContainerAnnc.SHORT_NAME:
			return new DoorStatusFlexContainerAnnc();
		case ElectricVehicleConnectorFlexContainerAnnc.SHORT_NAME:
			return new ElectricVehicleConnectorFlexContainerAnnc();
		case EnergyConsumptionFlexContainerAnnc.SHORT_NAME:
			return new EnergyConsumptionFlexContainerAnnc();
		case EnergyGenerationFlexContainerAnnc.SHORT_NAME:
			return new EnergyGenerationFlexContainerAnnc();
		case FaultDetectionFlexContainerAnnc.SHORT_NAME:
			return new FaultDetectionFlexContainerAnnc();
		case HeightFlexContainerAnnc.SHORT_NAME:
			return new HeightFlexContainerAnnc();
		case HotWaterSupplyFlexContainerAnnc.SHORT_NAME:
			return new HotWaterSupplyFlexContainerAnnc();
		case KeypadFlexContainerAnnc.SHORT_NAME:
			return new KeypadFlexContainerAnnc();
		case MotionSensorFlexContainerAnnc.SHORT_NAME:
			return new MotionSensorFlexContainerAnnc();
		case OximeterFlexContainerAnnc.SHORT_NAME:
			return new OximeterFlexContainerAnnc();
		case PowerSaveFlexContainerAnnc.SHORT_NAME:
			return new PowerSaveFlexContainerAnnc();
		case PushButtonFlexContainerAnnc.SHORT_NAME:
			return new PushButtonFlexContainerAnnc();
		case RecorderFlexContainerAnnc.SHORT_NAME:
			return new RecorderFlexContainerAnnc();
		case RefrigerationFlexContainerAnnc.SHORT_NAME:
			return new RefrigerationFlexContainerAnnc();
		case RelativeHumidityFlexContainerAnnc.SHORT_NAME:
			return new RelativeHumidityFlexContainerAnnc();
		case RinseLevelFlexContainerAnnc.SHORT_NAME:
			return new RinseLevelFlexContainerAnnc();
		case RunModeFlexContainerAnnc.SHORT_NAME:
			return new RunModeFlexContainerAnnc();
		case SignalStrengthFlexContainerAnnc.SHORT_NAME:
			return new SignalStrengthFlexContainerAnnc();
		case SmokeSensorFlexContainerAnnc.SHORT_NAME:
			return new SmokeSensorFlexContainerAnnc();
		case SpinLevelFlexContainerAnnc.SHORT_NAME:
			return new SpinLevelFlexContainerAnnc();
		case TelevisionChannelFlexContainerAnnc.SHORT_NAME:
			return new TelevisionChannelFlexContainerAnnc();
		case UpChannelFlexContainerAnnc.SHORT_NAME:
			return new UpChannelFlexContainerAnnc();
		case DownChannelFlexContainerAnnc.SHORT_NAME:
			return new DownChannelFlexContainerAnnc();
		case TemperatureFlexContainerAnnc.SHORT_NAME:
			return new TemperatureFlexContainerAnnc();
		case TemperatureAlarmFlexContainerAnnc.SHORT_NAME:
			return new TemperatureAlarmFlexContainerAnnc();
		case TimerFlexContainerAnnc.SHORT_NAME:
			return new TimerFlexContainerAnnc();
		case ActivateClockTimerFlexContainerAnnc.SHORT_NAME:
			return new ActivateClockTimerFlexContainerAnnc();
		case DeactivateClockTimerFlexContainerAnnc.SHORT_NAME:
			return new DeactivateClockTimerFlexContainerAnnc();
		case TurboFlexContainerAnnc.SHORT_NAME:
			return new TurboFlexContainerAnnc();
		case WaterFlowFlexContainerAnnc.SHORT_NAME:
			return new WaterFlowFlexContainerAnnc();
		case WaterLevelFlexContainerAnnc.SHORT_NAME:
			return new WaterLevelFlexContainerAnnc();
		case WaterSensorFlexContainerAnnc.SHORT_NAME:
			return new WaterSensorFlexContainerAnnc();
		case WeightFlexContainerAnnc.SHORT_NAME:
			return new WeightFlexContainerAnnc();
		case WindFlexContainerAnnc.SHORT_NAME:
			return new WindFlexContainerAnnc();
		case StreamingFlexContainerAnnc.SHORT_NAME:
			return new StreamingFlexContainerAnnc();
		case PersonSensorFlexContainerAnnc.SHORT_NAME:
			return new PersonSensorFlexContainerAnnc();
		case BrewingFlexContainerAnnc.SHORT_NAME:
			return new BrewingFlexContainerAnnc();
		case LiquidLevelFlexContainerAnnc.SHORT_NAME:
			return new LiquidLevelFlexContainerAnnc();
		case GrinderFlexContainerAnnc.SHORT_NAME:
			return new GrinderFlexContainerAnnc();
		case FoamingFlexContainerAnnc.SHORT_NAME:
			return new FoamingFlexContainerAnnc();
		case KeepWarmFlexContainerAnnc.SHORT_NAME:
			return new KeepWarmFlexContainerAnnc();
		case ContactSensorFlexContainerAnnc.SHORT_NAME:
			return new ContactSensorFlexContainerAnnc();
		case AlarmSensorFlexContainerAnnc.SHORT_NAME:
			return new AlarmSensorFlexContainerAnnc();
		case LockFlexContainerAnnc.SHORT_NAME:
			return new LockFlexContainerAnnc();
		case AtmosphericPressureSensorFlexContainerAnnc.SHORT_NAME:
			return new AtmosphericPressureSensorFlexContainerAnnc();
		case NoiseFlexContainerAnnc.SHORT_NAME:
			return new NoiseFlexContainerAnnc();
		case ExtendedCarbonDioxideSensorFlexContainerAnnc.SHORT_NAME:
			return new ExtendedCarbonDioxideSensorFlexContainerAnnc();
		case NumberValueFlexContainerAnnc.SHORT_NAME:
			return new NumberValueFlexContainerAnnc();
		case DecrementNumberValueFlexContainerAnnc.SHORT_NAME:
			return new DecrementNumberValueFlexContainerAnnc();
		case IncrementNumberValueFlexContainerAnnc.SHORT_NAME:
			return new IncrementNumberValueFlexContainerAnnc();
		case ResetNumberValueFlexContainerAnnc.SHORT_NAME:
			return new ResetNumberValueFlexContainerAnnc();
		case DeviceAirConditionerFlexContainerAnnc.SHORT_NAME:
			return new DeviceAirConditionerFlexContainerAnnc();
		case DeviceClothesWasherFlexContainerAnnc.SHORT_NAME:
			return new DeviceClothesWasherFlexContainerAnnc();
		case DeviceElectricVehicleChargerFlexContainerAnnc.SHORT_NAME:
			return new DeviceElectricVehicleChargerFlexContainerAnnc();
		case DeviceLightFlexContainerAnnc.SHORT_NAME:
			return new DeviceLightFlexContainerAnnc();
		case DeviceMicrogenerationFlexContainerAnnc.SHORT_NAME:
			return new DeviceMicrogenerationFlexContainerAnnc();
		case DeviceOvenFlexContainerAnnc.SHORT_NAME:
			return new DeviceOvenFlexContainerAnnc();
		case DeviceRefrigeratorFlexContainerAnnc.SHORT_NAME:
			return new DeviceRefrigeratorFlexContainerAnnc();
		case DeviceRobotCleanerFlexContainerAnnc.SHORT_NAME:
			return new DeviceRobotCleanerFlexContainerAnnc();
		case DeviceSmartElectricMeterFlexContainerAnnc.SHORT_NAME:
			return new DeviceSmartElectricMeterFlexContainerAnnc();
		case DeviceStorageBatteryFlexContainerAnnc.SHORT_NAME:
			return new DeviceStorageBatteryFlexContainerAnnc();
		case DeviceTelevisionFlexContainerAnnc.SHORT_NAME:
			return new DeviceTelevisionFlexContainerAnnc();
		case DeviceThermostatFlexContainerAnnc.SHORT_NAME:
			return new DeviceThermostatFlexContainerAnnc();
		case DeviceWaterHeaterFlexContainerAnnc.SHORT_NAME:
			return new DeviceWaterHeaterFlexContainerAnnc();
		case DeviceCameraFlexContainerAnnc.SHORT_NAME:
			return new DeviceCameraFlexContainerAnnc();
		case DeviceCoffeeMachineFlexContainerAnnc.SHORT_NAME:
			return new DeviceCoffeeMachineFlexContainerAnnc();
		case DeviceContactDetectorFlexContainerAnnc.SHORT_NAME:
			return new DeviceContactDetectorFlexContainerAnnc();
		case DeviceDoorFlexContainerAnnc.SHORT_NAME:
			return new DeviceDoorFlexContainerAnnc();
		case DeviceFloodDetectorFlexContainerAnnc.SHORT_NAME:
			return new DeviceFloodDetectorFlexContainerAnnc();
		case DeviceGasValveFlexContainerAnnc.SHORT_NAME:
			return new DeviceGasValveFlexContainerAnnc();
		case DeviceMotionDetectorFlexContainerAnnc.SHORT_NAME:
			return new DeviceMotionDetectorFlexContainerAnnc();
		case DeviceSmokeDetectorFlexContainerAnnc.SHORT_NAME:
			return new DeviceSmokeDetectorFlexContainerAnnc();
		case DeviceSmokeExtractorFlexContainerAnnc.SHORT_NAME:
			return new DeviceSmokeExtractorFlexContainerAnnc();
		case DeviceSwitchButtonFlexContainerAnnc.SHORT_NAME:
			return new DeviceSwitchButtonFlexContainerAnnc();
		case DeviceTemperatureDetectorFlexContainerAnnc.SHORT_NAME:
			return new DeviceTemperatureDetectorFlexContainerAnnc();
		case DeviceWarningDeviceFlexContainerAnnc.SHORT_NAME:
			return new DeviceWarningDeviceFlexContainerAnnc();
		case DeviceWaterValveFlexContainerAnnc.SHORT_NAME:
			return new DeviceWaterValveFlexContainerAnnc();
		case DeviceWeatherStationFlexContainerAnnc.SHORT_NAME:
			return new DeviceWeatherStationFlexContainerAnnc();
		case DeviceNumberDeviceFlexContainerAnnc.SHORT_NAME:
			return new DeviceNumberDeviceFlexContainerAnnc();
		}
		return new FlexContainerAnnc();
	}
}