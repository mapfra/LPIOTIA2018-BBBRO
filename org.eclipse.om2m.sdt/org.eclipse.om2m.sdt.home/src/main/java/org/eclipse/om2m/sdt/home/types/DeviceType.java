/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

public enum DeviceType {
	
	deviceAirConditioner(1, "deviceAirConditioner"),
	deviceClothesWasher(2, "deviceClothesWasher"),
	deviceElectricVehicleCharger(3, "deviceElectricVehicleCharger"),
	deviceLight(4, "deviceLight"),
	deviceMicrogeneration (5, "deviceMicrogeneration"),
	deviceOven(6, "deviceOven"),
	deviceRefrigerator(7, "deviceRefrigerator"),
	deviceRobotCleaner(8, "deviceRobotCleaner"),
	deviceSmartElectricMeter(9, "deviceSmartElectricMeter"),
	deviceStorageBattery(10, "deviceStorageBattery"),
	deviceTelevision(11, "deviceTelevision"),
	deviceThermostat(12, "deviceThermostat"),
	deviceWaterHeater(13, "deviceWaterHeater"),
	deviceCoffeeMachine(14, "deviceCoffeeMachine"), 
	
	deviceDoor(100, "deviceDoor"),
	deviceSmokeExtractor(101, "deviceSmokeExtractor"),
	deviceSwitchButton(102, "deviceSwitchButton"),
	deviceWarningDevice(103, "deviceWarningDevice"),
	
	deviceGasValve(200, "deviceGasValve"),
	deviceWaterValve(201, "deviceWaterValve"),
	
	deviceFloodDetector(300, "deviceFloodDetector"),
	deviceMotionDetector(301, "deviceMotionDetector"),
	deviceSmokeDetector(302, "deviceSmokeDetector"),
	deviceTemperatureDetector(303, "deviceTemperatureDetector"),
	deviceContactDetector(304, "deviceContactDetector"),
	
	deviceCamera(400, "deviceCamera"),
	deviceWeatherStation(500, "deviceWeatherStation"),
	
	undefinedVendorExt(0, "undefinedVendorExt");
	
	static public final String PATH = "org.onem2m.home.device.";
	
	private int value;
	private String def;
	
	DeviceType(int v, String s) {
		value = v;
		def = s;
	}

    public int getValue() {
        return value;
    }
    
    public String getDefinition() {
    	return PATH + def;
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
