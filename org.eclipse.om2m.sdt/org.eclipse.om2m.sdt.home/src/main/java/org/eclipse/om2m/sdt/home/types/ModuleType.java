/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

import org.eclipse.om2m.sdt.Identifiers;

public enum ModuleType implements Identifiers {
	
	alarmSpeaker(1, "alarmSpeaker", "alarmSpeaker", "alaSr"),
	audioVideoInput(2, "audioVideoInput", "audioVideoInput","auVIt"),
	audioVolume(3, "audioVolume", "audioVolume", "audVe"),
	battery(4, "battery", "battery", "batty"),
	binarySwitch(5, "binarySwitch", "binarySwitch", "binSh"),
	bioElectricalImpedanceAnalysis(6, "bioElectricalImpedanceAnalysis", "bioElectricalImpedanceAnalysis", "bEIAs"),
	boiler(7, "boiler", "boiler", "boilr"),
	brightness(8, "brightness", "brightness", "brigs"),
	clock(9, "clock", "clock", "clock"),
	colour(10, "colour", "colour", "color"),
	colourSaturation(11, "colourSaturation", "colourSaturation", "colSn"),
	doorStatus(12, "doorStatus", "doorStatus", "dooSs"),
	electricVehicleConnector(13, "electricVehicleConnector", "electricVehicleConnector", "elVCr"),
	energyConsumption(14, "energyConsumption", "energyConsumption", "eneCn"),
	energyGeneration(15, "energyGeneration", "energyGeneration", "eneGn"),
	faultDetection(16, "faultDetection", "faultDetection", "fauDn"),
	height(17, "height", "height", "heigt"),
	hotWaterSupply(18, "hotWaterSupply", "hotWaterSupply", "hoWSy"),
	keypad(19, "keypad", "keypad", "keypd"),
	motionSensor(20, "motionSensor", "motionSensor", "motSr"),
	oximeter(21, "oximeter", "oximeter", "oximr"),
	powerSave(22, "powerSave", "powerSave", "powS0"),
	pushButton(23, "pushButton", "pushButton", "pusBn"),
	recorder(24, "recorder", "recorder", "recor"),
	refrigeration(25, "refrigeration", "refrigeration", "refrn"),
	relativeHumidity(26, "relativeHumidity", "relativeHumidity", "relHy"),
	//rinseLevel(27, "rinseLevel"),
	runState(27, "runState", "runState", "runSt"),
	runMode(28, "runMode", "runMode", "runMe"),
	signalStrength(29, "signalStrength", "signalStrength", "sigSh"),
	smokeSensor(30, "smokeSensor", "smokeSensor", "smoSr"),
	spinLevel(31, "spinLevel", "spinLevel", "spiLl"),
	televisionChannel(32, "televisionChannel", "televisionChannel", "telCl"),
	temperature(33, "temperature", "temperature", "tempe"),
	temperatureAlarm(34, "temperatureAlarm", "temperatureAlarm", "temAm"),
	timer(35, "timer", "timer", "timer"),
	turbo(36, "turbo", "turbo", "turbo"),
	waterFlow(37, "waterFlow", "waterFlow", "watFw"),
	liquidLevel(38, "liquidLevel", "liquidLevel", "liqLl"),
	waterSensor(39, "waterSensor", "waterSensor", "watSr"),
	weight(40, "weight", "weight", "weigt"),
	wind(41, "wind", "wind", "wind"),
	
	/****ADDED by Maciek****/
	
	grinder(42, "grinder", "grinder", "gridr"),
	foaming(43, "foaming", "foaming", "fomng"),
	brewing(44, "brewing", "brewing", "brwng"),
	boiling(45, "boiling", "boiling", "bling"),
	keepWarm(46, "keepwarm", "keepwarm", "kWarm"),
	
	/***********************/

	atmosphericPressureSensor(100, "atmosphericPressureSensor", "atmosphericPressureSensor", "atPSr"),
	carbonDioxideSensor(101, "carbonDioxideSensor", "carbonDioxideSensor", "cbDSr"),
	carbonMonoxideSensor(102, "carbonMonoxideSensor", "carbonMonoxideSensor", "cbMSr"),
	contactSensor(103, "contactSensor", "contactSensor", "conSr"),
	dimming(104, "dimming", "dimming", "dimng"),
	energyOverloadCircuitBreaker(105, "energyOverloadCircuitBreaker", "energyOverloadCircuitBreaker", "eOCBr"),
	genericSensor(106, "genericSensor", "genericSensor", "genSr"),
	glassBreakSensor(107, "glassBreakSensor", "glassBreakSensor", "gBkSr"),
	presenceSensor(108, "presenceSensor", "presenceSensor", "preSr"),
	touchSensor(109, "touchSensor", "touchSensor", "touSr"),
	lock(110, "lock", "lock", "lock"),

	personSensor(150, "personSensor", "personSensor", "perSr"),
	streaming(151, "streaming", "streaming", "streg"),
	noise(152, "noise", "noise", "noise"),
	extendedCarbonDioxideSensor(153, "extendedCarbonDioxideSensor", "extendedCarbonDioxideSensor", "eCDSr"),
	
	numberValue(154, "numberValue", "numberValue", "numVe"), // Added by Andreas Kraft
	
	
	abstractAlarmSensor(200, "abstractAlarmSensor", "abstractAlarmSensor", "aAlSr");
	
	static private final String PATH = "org.onem2m.home.moduleclass.";
	
	private int value;
	private final String def;
	private final String longDefinitionName;
	private final String shortDefinitionName;
	
	ModuleType(final int v, final String s, final String longDef, final String shortDef) {
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

	public static ModuleType fromValue(int v) {
        for (ModuleType c: ModuleType.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException("Undefined value " + v);
    }

}
