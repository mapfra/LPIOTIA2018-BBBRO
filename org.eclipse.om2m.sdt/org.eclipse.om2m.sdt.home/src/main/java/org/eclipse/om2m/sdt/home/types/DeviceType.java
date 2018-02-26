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
	
	deviceAirConditioner(1, "deviceAirConditioner", "deviceAirConditioner", "deACr"),
	deviceClothesWasher(2, "deviceClothesWasher", "deviceClothesWasher", "deCWr"),
	deviceElectricVehicleCharger(3, "deviceElectricVehicleCharger", "deviceElectricVehicleCharger", "dEVCr"),
	deviceLight(4, "deviceLight", "deviceLight", "devLt"),
	deviceMicrogeneration (5, "deviceMicrogeneration", "deviceMicrogeneration", "devMn"),
	deviceOven(6, "deviceOven", "deviceOven", "devOn"),
	deviceRefrigerator(7, "deviceRefrigerator", "deviceRefrigerator", "devRr"),
	deviceRobotCleaner(8, "deviceRobotCleaner", "deviceRobotCleaner", "devRCr"),
	deviceSmartElectricMeter(9, "deviceSmartElectricMeter", "deviceSmartElectricMeter", "dSEMr"),
	deviceStorageBattery(10, "deviceStorageBattery", "deviceStorageBattery", "deSBy"),
	deviceTelevision(11, "deviceTelevision", "deviceTelevision", "devTn"),
	deviceThermostat(12, "deviceThermostat", "deviceThermostat", "devTt"),
	deviceWaterHeater(13, "deviceWaterHeater", "deviceWaterHeater", "devWHr"),
	deviceCoffeeMachine(14, "deviceCoffeeMachine", "deviceCoffeeMachine", "dCeMe"), 
	deviceKettle(15, "deviceKettle", "deviceKettle", "dKtle"),
	
	deviceDoor(100, "deviceDoor", "deviceDoor", "devDr"),
	deviceSmokeExtractor(101, "deviceSmokeExtractor", "deviceSmokeExtractor", "dSeEr"),
	deviceSwitchButton(102, "deviceSwitchButton", "deviceSwitchButton", "deSBn"),
	deviceWarningDevice(103, "deviceWarningDevice", "deviceWarningDevice", "deWDe"),
	
	deviceGasValve(200, "deviceGasValve", "deviceGasValve", "dGsVe"),
	deviceWaterValve(201, "deviceWaterValve", "deviceWaterValve", "deWVe"),
	
	deviceFloodDetector(300, "deviceFloodDetector", "deviceFloodDetector", "deFDr"),
	deviceMotionDetector(301, "deviceMotionDetector", "deviceMotionDetector", "deMDr"),
	deviceSmokeDetector(302, "deviceSmokeDetector", "deviceSmokeDetector", "deSDr"),
	deviceTemperatureDetector(303, "deviceTemperatureDetector", "deviceTemperatureDetector", "deTDr"),
	deviceContactDetector(304, "deviceContactDetector", "deviceContactDetector", "deCDr"),
	
	deviceCamera(400, "deviceCamera", "deviceCamera", "devCa"),
	deviceWeatherStation(500, "deviceWeatherStation", "deviceWeatherStation", "deWSn"),
	deviceNumberDevice(600, "deviceNumberDevice", "deviceNumberDevice", "deNDe"),
	
	undefinedVendorExt(0, "undefinedVendorExt", "", "");
	
	static public final String PATH = "org.onem2m.home.device.";
	
	private int value;
	private final String def;
	private final String longDefinitionName;
	private final String shortDefinitionName;
	
	DeviceType(int v, String s, String longDef, String shortDef) {
		value = v;
		def = s;
		longDefinitionName = longDef;
		shortDefinitionName = shortDef;
	}

    public int getValue() {
        return value;
    }
    
    public String getDefinition() {
    	return PATH + def;
    }
    
    

    /**
	 * @return the longDefinitionName
	 */
	@Override
	public String getLongName() {
		return longDefinitionName;
	}

	/**
	 * @return the shortDefinitionName
	 */
	@Override
	public String getShortName() {
		return shortDefinitionName;
	}

	public static DeviceType fromValue(int v) {
        for (DeviceType c: DeviceType.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException("Undefined value " + v);
    }

    public static DeviceType fromValue(String def) {
        for (DeviceType c: DeviceType.values()) {
            if (c.def.equals(def)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Undefined definition " + def);
    }

}
