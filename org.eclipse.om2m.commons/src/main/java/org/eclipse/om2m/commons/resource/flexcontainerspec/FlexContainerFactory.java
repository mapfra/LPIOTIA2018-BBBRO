package org.eclipse.om2m.commons.resource.flexcontainerspec;

import org.eclipse.om2m.commons.resource.FlexContainer;

public class FlexContainerFactory {
	
	public static FlexContainer getSpecializationFlexContainer(String shortName) {
		switch (shortName) {
		case DeviceLightFlexContainer.SHORT_NAME:
			return new DeviceLightFlexContainer();
		case DeviceSmartElectricMeterFlexContainer.SHORT_NAME:
			return new DeviceSmartElectricMeterFlexContainer();
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
		case ModuleAlarmSpeakerFlexContainer.SHORT_NAME:
			return new ModuleAlarmSpeakerFlexContainer();
		case ModuleAudioVolumeFlexContainer.SHORT_NAME:
			return new ModuleAudioVolumeFlexContainer();
		case ModuleBinarySwitchFlexContainer.SHORT_NAME:
			return new ModuleBinarySwitchFlexContainer();
		case ModuleBoilerFlexContainer.SHORT_NAME:
			return new ModuleBoilerFlexContainer();
		case ModuleBrightnessFlexContainer.SHORT_NAME:
			return new ModuleBrightnessFlexContainer();
		case ModuleClockFlexContainer.SHORT_NAME:
			return new ModuleClockFlexContainer();
		case ModuleColourFlexContainer.SHORT_NAME:
			return new ModuleColourFlexContainer();
		case ModuleColourSaturationFlexContainer.SHORT_NAME:
			return new ModuleColourSaturationFlexContainer();
		case ModuleDoorStatusFlexContainer.SHORT_NAME:
			return new ModuleDoorStatusFlexContainer();
		case ModuleEnergyConsumptionFlexContainer.SHORT_NAME:
			return new ModuleEnergyConsumptionFlexContainer();
		case ModuleEnergyGenerationFlexContainer.SHORT_NAME:
			return new ModuleEnergyGenerationFlexContainer();
		case ModuleFaultDetectionFlexContainer.SHORT_NAME:
			return new ModuleFaultDetectionFlexContainer();
		case ModuleRelativeHumidityFlexContainer.SHORT_NAME:
			return new ModuleRelativeHumidityFlexContainer();
		case ModuleRunModeFlexContainer.SHORT_NAME:
			return new ModuleRunModeFlexContainer();
		case ModuleSmokeSensorFlexContainer.SHORT_NAME:
			return new ModuleSmokeSensorFlexContainer();
		case ModuleTemperatureFlexContainer.SHORT_NAME:
			return new ModuleTemperatureFlexContainer();
		case ModuleWaterLevelFlexContainer.SHORT_NAME:
			return new ModuleWaterLevelFlexContainer();
		case ModuleWaterSensorFlexContainer.SHORT_NAME:
			return new ModuleWaterSensorFlexContainer();
		case ModuleAtmosphericPressureSensorFlexContainer.SHORT_NAME:
			return new ModuleAtmosphericPressureSensorFlexContainer();
		case ModuleBrewingFlexContainer.SHORT_NAME:
			return new ModuleBrewingFlexContainer();
		case ModuleCarbonDioxideSensorFlexContainer.SHORT_NAME:
			return new ModuleCarbonDioxideSensorFlexContainer();
		case ModuleCarbonMonoxideSensorFlexContainer.SHORT_NAME:
			return new ModuleCarbonMonoxideSensorFlexContainer();
		case ModuleContactSensorFlexContainer.SHORT_NAME:
			return new ModuleContactSensorFlexContainer();
		case ModuleDimmingFlexContainer.SHORT_NAME:
			return new ModuleDimmingFlexContainer();
		case ModuleEnergyOverloadCircuitBreakerFlexContainer.SHORT_NAME:
			return new ModuleEnergyOverloadCircuitBreakerFlexContainer();
		case ModuleExtendedCarbonDioxideSensorFlexContainer.SHORT_NAME:
			return new ModuleExtendedCarbonDioxideSensorFlexContainer();
		case ModuleFoamingFlexContainer.SHORT_NAME:
			return new ModuleFoamingFlexContainer();
		case ModuleGrinderFlexContainer.SHORT_NAME:
			return new ModuleGrinderFlexContainer();
		case ModuleNoiseFlexContainer.SHORT_NAME:
			return new ModuleNoiseFlexContainer();
		case ModulePersonSensorFlexContainer.SHORT_NAME:
			return new ModulePersonSensorFlexContainer();
		case ModuleStreamingFlexContainer.SHORT_NAME:
			return new ModuleStreamingFlexContainer();
		case ModuleLockFlexContainer.SHORT_NAME:
			return new ModuleLockFlexContainer();
		case ActionToggleFlexContainer.SHORT_NAME:
			return new ActionToggleFlexContainer();
		}
		return new FlexContainer();
		
	}

}
