/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

import org.eclipse.om2m.sdt.Identifiers;

public enum DeviceType implements Identifiers {
	
	deviceAirConditioner,
	deviceAirQualityMonitor,
	deviceClothesWasher,
	deviceElectricVehicleCharger,
	deviceLight,
	deviceMicrogeneration,
	deviceOven,
	deviceRefrigerator,
	deviceRobotCleaner,
	deviceSmartElectricMeter,
	deviceStorageBattery,
	deviceTelevision,
	deviceThermostat,
	deviceWaterHeater,
	deviceCoffeeMachine, 
	deviceKettle,
	
	deviceDoor,
//	deviceSmokeExtractor,
	deviceSwitch,
	deviceWarning,
	
	deviceGasValve,
	deviceWaterValve,
	
	deviceContactDetector,
	deviceFloodDetector,
	deviceMotionDetector,
	deviceSmokeDetector,
	deviceTemperatureDetector,
	
//	deviceCamera2,
	deviceCamera,
	deviceWeatherStation,
	deviceHomeCCTV,
	;
	
	private final String longName;
	private final String shortName;
	private final String cnd;
	
	DeviceType() {
		longName = toString();
		shortName = FlexContainers.getFlexShortName(longName);
		cnd = FlexContainers.getContainerDefinition(longName);
	}
    
    public String getDefinition() {
    	return cnd;
    }

    /**
	 * @return the longDefinitionName
	 */
	@Override
	public String getLongName() {
		return longName;
	}

	/**
	 * @return the shortDefinitionName
	 */
	@Override
	public String getShortName() {
		return shortName;
	}

}
