/*
ObjectFactory : ObjectFactory





Created: 2017-07-17 15:25:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlRegistry;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRegistry
public class ObjectFactory {
	
	public AbstractFlexContainer createalaSr() {
		return new AlarmSpeakerFlexContainer();
	}
	
	public AbstractFlexContainer createAuVIt() {
		return new AudioVideoInputFlexContainer();
	}
	
	public AbstractFlexContainer createAudVe() {
		return new AudioVolumeFlexContainer();
	}
	
	public AbstractFlexContainer createUpoVe() {
		return new UpVolumeFlexContainer();
	}
	
	public AbstractFlexContainer createDowVe() {
		return new DownVolumeFlexContainer();
	}
	
	public AbstractFlexContainer createBatty() {
		return new BatteryFlexContainer();
	}
	
	public AbstractFlexContainer createBinSh() {
		return new BinarySwitchFlexContainer();
	}
	
	public AbstractFlexContainer createTogge() {
		return new ToggleFlexContainer();
	}
	
	public AbstractFlexContainer createBEIAs() {
		return new BioElectricalImpedanceAnalysisFlexContainer();
	}
	
	public AbstractFlexContainer createBoilr() {
		return new BoilerFlexContainer();
	}
	
	public AbstractFlexContainer createBrigs() {
		return new BrightnessFlexContainer();
	}
	
	public AbstractFlexContainer createClock() {
		return new ClockFlexContainer();
	}
	
	public AbstractFlexContainer createColor() {
		return new ColourFlexContainer();
	}
	
	public AbstractFlexContainer createColSn() {
		return new ColourSaturationFlexContainer();
	}
	
	public AbstractFlexContainer createDooSs() {
		return new DoorStatusFlexContainer();
	}
	
	public AbstractFlexContainer createElVCr() {
		return new ElectricVehicleConnectorFlexContainer();
	}
	
	public AbstractFlexContainer createEneCn() {
		return new EnergyConsumptionFlexContainer();
	}
	
	public AbstractFlexContainer createEneGn() {
		return new EnergyGenerationFlexContainer();
	}
	
	public AbstractFlexContainer createFauDn() {
		return new FaultDetectionFlexContainer();
	}
	
	public AbstractFlexContainer createHeigt() {
		return new HeightFlexContainer();
	}
	
	public AbstractFlexContainer createHoWSy() {
		return new HotWaterSupplyFlexContainer();
	}
	
	public AbstractFlexContainer createKeypd() {
		return new KeypadFlexContainer();
	}
	
	public AbstractFlexContainer createMotSr() {
		return new MotionSensorFlexContainer();
	}
	
	public AbstractFlexContainer createOximr() {
		return new OximeterFlexContainer();
	}
	
	public AbstractFlexContainer createPowSe() {
		return new PowerSaveFlexContainer();
	}
	
	public AbstractFlexContainer createPusBn() {
		return new PushButtonFlexContainer();
	}
	
	public AbstractFlexContainer createRecor() {
		return new RecorderFlexContainer();
	}
	
	public AbstractFlexContainer createRefrn() {
		return new RefrigerationFlexContainer();
	}
	
	public AbstractFlexContainer createRelHy() {
		return new RelativeHumidityFlexContainer();
	}
	
	public AbstractFlexContainer createRinLl() {
		return new RinseLevelFlexContainer();
	}
	
	public AbstractFlexContainer createRunMe() {
		return new RunModeFlexContainer();
	}
	
	public AbstractFlexContainer createSigSh() {
		return new SignalStrengthFlexContainer();
	}
	
	public AbstractFlexContainer createSmoSr() {
		return new SmokeSensorFlexContainer();
	}
	
	public AbstractFlexContainer createSpiLl() {
		return new SpinLevelFlexContainer();
	}
	
	public AbstractFlexContainer createTelCl() {
		return new TelevisionChannelFlexContainer();
	}
	
	public AbstractFlexContainer createUphCl() {
		return new UpChannelFlexContainer();
	}
	
	public AbstractFlexContainer createDowCl() {
		return new DownChannelFlexContainer();
	}
	
	public AbstractFlexContainer createTempe() {
		return new TemperatureFlexContainer();
	}
	
	public AbstractFlexContainer createTemAm() {
		return new TemperatureAlarmFlexContainer();
	}
	
	public AbstractFlexContainer createTimer() {
		return new TimerFlexContainer();
	}
	
	public AbstractFlexContainer createAcCTr() {
		return new ActivateClockTimerFlexContainer();
	}
	
	public AbstractFlexContainer createDeCTr() {
		return new DeactivateClockTimerFlexContainer();
	}
	
	public AbstractFlexContainer createTurbo() {
		return new TurboFlexContainer();
	}
	
	public AbstractFlexContainer createWatFw() {
		return new WaterFlowFlexContainer();
	}
	
	public AbstractFlexContainer createWatLl() {
		return new WaterLevelFlexContainer();
	}
	
	public AbstractFlexContainer createWatSr() {
		return new WaterSensorFlexContainer();
	}
	
	public AbstractFlexContainer createWeigt() {
		return new WeightFlexContainer();
	}
	
	public AbstractFlexContainer createWind() {
		return new WindFlexContainer();
	}
	
	public AbstractFlexContainer createStreg() {
		return new StreamingFlexContainer();
	}
	
	public AbstractFlexContainer createPerSr() {
		return new PersonSensorFlexContainer();
	}
	
	public AbstractFlexContainer createBrewg() {
		return new BrewingFlexContainer();
	}
	
	public AbstractFlexContainer createLiqLl() {
		return new LiquidLevelFlexContainer();
	}
	
	public AbstractFlexContainer createGrinr() {
		return new GrinderFlexContainer();
	}
	
	public AbstractFlexContainer createFoamg() {
		return new FoamingFlexContainer();
	}
	
	public AbstractFlexContainer createKeeWm() {
		return new KeepWarmFlexContainer();
	}
	
	public AbstractFlexContainer createConSr() {
		return new ContactSensorFlexContainer();
	}
	
	public AbstractFlexContainer createalSer() {
		return new AlarmSensorFlexContainer();
	}
	
	public AbstractFlexContainer createLock() {
		return new LockFlexContainer();
	}
	
	public AbstractFlexContainer createAtPSr() {
		return new AtmosphericPressureSensorFlexContainer();
	}
	
	public AbstractFlexContainer createNoise() {
		return new NoiseFlexContainer();
	}
	
	public AbstractFlexContainer createECDSr() {
		return new ExtendedCarbonDioxideSensorFlexContainer();
	}
	
	public AbstractFlexContainer createDeACr() {
		return new DeviceAirConditionerFlexContainer();
	}
	
	public AbstractFlexContainer createDeCWr() {
		return new DeviceClothesWasherFlexContainer();
	}
	
	public AbstractFlexContainer createDEVCr() {
		return new DeviceElectricVehicleChargerFlexContainer();
	}
	
	public AbstractFlexContainer createDevLt() {
		return new DeviceLightFlexContainer();
	}
	
	public AbstractFlexContainer createDevMn() {
		return new DeviceMicrogenerationFlexContainer();
	}
	
	public AbstractFlexContainer createDevOn() {
		return new DeviceOvenFlexContainer();
	}
	
	public AbstractFlexContainer createDevRr() {
		return new DeviceRefrigeratorFlexContainer();
	}
	
	public AbstractFlexContainer createDeRCr() {
		return new DeviceRobotCleanerFlexContainer();
	}
	
	public AbstractFlexContainer createDSEMr() {
		return new DeviceSmartElectricMeterFlexContainer();
	}
	
	public AbstractFlexContainer createDeSBy() {
		return new DeviceStorageBatteryFlexContainer();
	}
	
	public AbstractFlexContainer createDevTn() {
		return new DeviceTelevisionFlexContainer();
	}
	
	public AbstractFlexContainer createDevTt() {
		return new DeviceThermostatFlexContainer();
	}
	
	public AbstractFlexContainer createDeWHr() {
		return new DeviceWaterHeaterFlexContainer();
	}
	
	public AbstractFlexContainer createDevCa() {
		return new DeviceCameraFlexContainer();
	}
	
	public AbstractFlexContainer createDeCMe() {
		return new DeviceCoffeeMachineFlexContainer();
	}
	
	public AbstractFlexContainer createDeCDr() {
		return new DeviceContactDetectorFlexContainer();
	}
	
	public AbstractFlexContainer createDevDr() {
		return new DeviceDoorFlexContainer();
	}
	
	public AbstractFlexContainer createDeFDr() {
		return new DeviceFloodDetectorFlexContainer();
	}
	
	public AbstractFlexContainer createDeGVe() {
		return new DeviceGasValveFlexContainer();
	}
	
	public AbstractFlexContainer createDeMDr() {
		return new DeviceMotionDetectorFlexContainer();
	}
	
	public AbstractFlexContainer createDeSDr() {
		return new DeviceSmokeDetectorFlexContainer();
	}
	
	public AbstractFlexContainer createDeSEr() {
		return new DeviceSmokeExtractorFlexContainer();
	}
	
	public AbstractFlexContainer createDeSBn() {
		return new DeviceSwitchButtonFlexContainer();
	}
	
	public AbstractFlexContainer createDeTDr() {
		return new DeviceTemperatureDetectorFlexContainer();
	}
	
	public AbstractFlexContainer createDeWDe() {
		return new DeviceWarningDeviceFlexContainer();
	}
	
	public AbstractFlexContainer createDeWVe() {
		return new DeviceWaterValveFlexContainer();
	}
	
	public AbstractFlexContainer createDeWSn() {
		return new DeviceWeatherStationFlexContainer();
	}
	
}