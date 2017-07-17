/*
FlexContainerFactory : FlexContainerFactory





Created: 2017-07-17 15:25:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

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
		}
		return new FlexContainer();
	}
}