package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlRegistry;

import org.eclipse.om2m.commons.resource.FlexContainer;

@XmlRegistry
public class ObjectFactory {
	
	public FlexContainer createDevLt() {
		return new DeviceLightFlexContainer();
	}
	
	public FlexContainer createDSEMr() {
		return new DeviceSmartElectricMeterFlexContainer();
	}
	
	public FlexContainer createDeWHr() {
		return new DeviceWaterHeaterFlexContainer();
	}
	
	public FlexContainer createDevCa() {
		return new DeviceCameraFlexContainer();
	}
	
	public FlexContainer createDCeMe() {
		return new DeviceCoffeeMachineFlexContainer();
	}
	
	public FlexContainer createDCtDr() {
		return new DeviceContactDetectorFlexContainer();
	}
	
	public FlexContainer createDevDr() {
		return new DeviceDoorFlexContainer();
	}
	
	public FlexContainer createDFdDr() {
		return new DeviceFloodDetectorFlexContainer();
	}
	
	public FlexContainer createDGsVe() {
		return new DeviceGasValveFlexContainer();
	}
	
	public FlexContainer createDMnDr() {
		return new DeviceMotionDetectorFlexContainer();
	}
	
	public FlexContainer createDSeDr() {
		return new DeviceSmokeDetectorFlexContainer();
	}
	
	public FlexContainer createDSeEr() {
		return new DeviceSmokeExtractorFlexContainer();
	}
	
	public FlexContainer createDShBn() {
		return new DeviceSwitchButtonFlexContainer();
	}
	
	public FlexContainer createDTeDr() {
		return new DeviceTemperatureDetectorFlexContainer();
	}
	
	public FlexContainer createDWgDe() {
		return new DeviceWarningDeviceFlexContainer();
	}
	
	public FlexContainer createDevWV() {
		return new DeviceWaterValveFlexContainer();
	}
	
	public FlexContainer createDWrSn() {
		return new DeviceWeatherStationFlexContainer();
	}
	
	public FlexContainer createAlaSr() {
		return new ModuleAlarmSpeakerFlexContainer();
	}
	
	public FlexContainer createAudVe() {
		return new ModuleAudioVolumeFlexContainer();
	}
	
	public FlexContainer createBinSh() {
		return new ModuleBinarySwitchFlexContainer();
	}
	
	public FlexContainer createBoilr() {
		return new ModuleBoilerFlexContainer();
	}
	
	public FlexContainer createBrigs() {
		return new ModuleBrightnessFlexContainer();
	}
	
	public FlexContainer createClock() {
		return new ModuleClockFlexContainer();
	}
	
	public FlexContainer createColor() {
		return new ModuleColourFlexContainer();
	}
	
	public FlexContainer createColSn() {
		return new ModuleColourSaturationFlexContainer();
	}
	
	public FlexContainer createDooSe() {
		return new ModuleDoorStatusFlexContainer();
	}
	
	public FlexContainer createEneCn() {
		return new ModuleEnergyConsumptionFlexContainer();
	}
	
	public FlexContainer createEneGn() {
		return new ModuleEnergyGenerationFlexContainer();
	}
	
	public FlexContainer createFauDn() {
		return new ModuleFaultDetectionFlexContainer();
	}
	
	public FlexContainer createRelHy() {
		return new ModuleRelativeHumidityFlexContainer();
	}
	
	public FlexContainer createRunMe() {
		return new ModuleRunModeFlexContainer();
	}
	
	public FlexContainer createSmoSr() {
		return new ModuleSmokeSensorFlexContainer();
	}
	
	public FlexContainer createTempe() {
		return new ModuleTemperatureFlexContainer();
	}
	
	public FlexContainer createWatLl() {
		return new ModuleWaterLevelFlexContainer();
	}
	
	public FlexContainer createWatSr() {
		return new ModuleWaterSensorFlexContainer();
	}
	
	public FlexContainer createAtPSr() {
		return new ModuleAtmosphericPressureSensorFlexContainer();
	}
	
	public FlexContainer createBrwng() {
		return new ModuleBrewingFlexContainer();
	}
	
	public FlexContainer createCbDSr() {
		return new ModuleCarbonDioxideSensorFlexContainer();
	}
	
	public FlexContainer createCbMSr() {
		return new ModuleCarbonMonoxideSensorFlexContainer();
	}
	
	public FlexContainer createConSr() {
		return new ModuleContactSensorFlexContainer();
	}
	
	public FlexContainer createDimng() {
		return new ModuleDimmingFlexContainer();
	}
	
	public FlexContainer createEOCBr() {
		return new ModuleEnergyOverloadCircuitBreakerFlexContainer();
	}
	
	public FlexContainer createECDSr() {
		return new ModuleExtendedCarbonDioxideSensorFlexContainer();
	}
	
	public FlexContainer createFomng() {
		return new ModuleFoamingFlexContainer();
	}
	
	public FlexContainer createGridr() {
		return new ModuleGrinderFlexContainer();
	}
	
	public FlexContainer createNoise() {
		return new ModuleNoiseFlexContainer();
	}
	
	public FlexContainer createPerSr() {
		return new ModulePersonSensorFlexContainer();
	}
	
	public FlexContainer createStrem() {
		return new ModuleStreamingFlexContainer();
	}
	
	public FlexContainer createLock() {
		return new ModuleLockFlexContainer();
	}
	
	public FlexContainer createTogge() {
		return new ActionToggleFlexContainer();
	}
	
}
