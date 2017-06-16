package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlRegistry;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRegistry
public class ObjectFactory {
	
	public AbstractFlexContainer createDevLt() {
		return new DeviceLightFlexContainer();
	}
	
	public AbstractFlexContainer createDSEMr() {
		return new DeviceSmartElectricMeterFlexContainer();
	}
	
	public AbstractFlexContainer createDeWHr() {
		return new DeviceWaterHeaterFlexContainer();
	}
	
	public AbstractFlexContainer createDevCa() {
		return new DeviceCameraFlexContainer();
	}
	
	public AbstractFlexContainer createDCeMe() {
		return new DeviceCoffeeMachineFlexContainer();
	}
	
	public AbstractFlexContainer createDCtDr() {
		return new DeviceContactDetectorFlexContainer();
	}
	
	public AbstractFlexContainer createDevDr() {
		return new DeviceDoorFlexContainer();
	}
	
	public AbstractFlexContainer createDFdDr() {
		return new DeviceFloodDetectorFlexContainer();
	}
	
	public AbstractFlexContainer createDGsVe() {
		return new DeviceGasValveFlexContainer();
	}
	
	public AbstractFlexContainer createDMnDr() {
		return new DeviceMotionDetectorFlexContainer();
	}
	
	public AbstractFlexContainer createDSeDr() {
		return new DeviceSmokeDetectorFlexContainer();
	}
	
	public AbstractFlexContainer createDSeEr() {
		return new DeviceSmokeExtractorFlexContainer();
	}
	
	public AbstractFlexContainer createDShBn() {
		return new DeviceSwitchButtonFlexContainer();
	}
	
	public AbstractFlexContainer createDTeDr() {
		return new DeviceTemperatureDetectorFlexContainer();
	}
	
	public AbstractFlexContainer createDWgDe() {
		return new DeviceWarningDeviceFlexContainer();
	}
	
	public AbstractFlexContainer createDevWV() {
		return new DeviceWaterValveFlexContainer();
	}
	
	public AbstractFlexContainer createDWrSn() {
		return new DeviceWeatherStationFlexContainer();
	}
	
	public AbstractFlexContainer createAlaSr() {
		return new ModuleAlarmSpeakerFlexContainer();
	}
	
	public AbstractFlexContainer createAudVe() {
		return new ModuleAudioVolumeFlexContainer();
	}
	
	public AbstractFlexContainer createBinSh() {
		return new ModuleBinarySwitchFlexContainer();
	}
	
	public AbstractFlexContainer createBoilr() {
		return new ModuleBoilerFlexContainer();
	}
	
	public AbstractFlexContainer createBrigs() {
		return new ModuleBrightnessFlexContainer();
	}
	
	public AbstractFlexContainer createClock() {
		return new ModuleClockFlexContainer();
	}
	
	public AbstractFlexContainer createColor() {
		return new ModuleColourFlexContainer();
	}
	
	public AbstractFlexContainer createColSn() {
		return new ModuleColourSaturationFlexContainer();
	}
	
	public AbstractFlexContainer createDooSe() {
		return new ModuleDoorStatusFlexContainer();
	}
	
	public AbstractFlexContainer createEneCn() {
		return new ModuleEnergyConsumptionFlexContainer();
	}
	
	public AbstractFlexContainer createEneGn() {
		return new ModuleEnergyGenerationFlexContainer();
	}
	
	public AbstractFlexContainer createFauDn() {
		return new ModuleFaultDetectionFlexContainer();
	}
	
	public AbstractFlexContainer createRelHy() {
		return new ModuleRelativeHumidityFlexContainer();
	}
	
	public AbstractFlexContainer createRunMe() {
		return new ModuleRunModeFlexContainer();
	}
	
	public AbstractFlexContainer createSmoSr() {
		return new ModuleSmokeSensorFlexContainer();
	}
	
	public AbstractFlexContainer createTempe() {
		return new ModuleTemperatureFlexContainer();
	}
	
	public AbstractFlexContainer createWatLl() {
		return new ModuleWaterLevelFlexContainer();
	}
	
	public AbstractFlexContainer createWatSr() {
		return new ModuleWaterSensorFlexContainer();
	}
	
	public AbstractFlexContainer createAtPSr() {
		return new ModuleAtmosphericPressureSensorFlexContainer();
	}
	
	public AbstractFlexContainer createBrwng() {
		return new ModuleBrewingFlexContainer();
	}
	
	public AbstractFlexContainer createCbDSr() {
		return new ModuleCarbonDioxideSensorFlexContainer();
	}
	
	public AbstractFlexContainer createCbMSr() {
		return new ModuleCarbonMonoxideSensorFlexContainer();
	}
	
	public AbstractFlexContainer createConSr() {
		return new ModuleContactSensorFlexContainer();
	}
	
	public AbstractFlexContainer createDimng() {
		return new ModuleDimmingFlexContainer();
	}
	
	public AbstractFlexContainer createEOCBr() {
		return new ModuleEnergyOverloadCircuitBreakerFlexContainer();
	}
	
	public AbstractFlexContainer createECDSr() {
		return new ModuleExtendedCarbonDioxideSensorFlexContainer();
	}
	
	public AbstractFlexContainer createFomng() {
		return new ModuleFoamingFlexContainer();
	}
	
	public AbstractFlexContainer createGridr() {
		return new ModuleGrinderFlexContainer();
	}
	
	public AbstractFlexContainer createNoise() {
		return new ModuleNoiseFlexContainer();
	}
	
	public AbstractFlexContainer createPerSr() {
		return new ModulePersonSensorFlexContainer();
	}
	
	public AbstractFlexContainer createStrem() {
		return new ModuleStreamingFlexContainer();
	}
	
	public AbstractFlexContainer createLock() {
		return new ModuleLockFlexContainer();
	}
	
	public AbstractFlexContainer createTogge() {
		return new ActionToggleFlexContainer();
	}
	
}
