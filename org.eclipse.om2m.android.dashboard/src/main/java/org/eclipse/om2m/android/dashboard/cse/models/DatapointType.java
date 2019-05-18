/*******************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.android.dashboard.cse.models;

public enum DatapointType {
	
	absoluteEnergyConsumption("absoluteEnergyConsumption", "abECn"),
	absoluteStartTime("absoluteStartTime", "abSTe"),
	absoluteStopTime("absoluteStopTime", "abST0"),
	alarm("alarm", "alarm"),
	alarmStatus("alarmStatus", "alaSs"),
	availableChannels("availableChannels", "avaCs"),
	bath("bath", "bath"),
	batteryThreshold("batteryThreshold", "batTd"),
	blue("blue", "blue"),
	bone("bone", "bone"),
	brightness("brightness", "brigs"),
	capacity("capacity", "capay"),
	channelId("channelId", "chaId"),
	charging("charging", "charg"),
	code("code", "code"),
	colourSat("colourSat", "colSn"),
	current("current", "currt"),
	currentDate("currentDate", "curDe"),
	currentTemperature("currentTemperature", "curT0"),
	currentTime("currentTime", "curTe"),
	defrost("defrost", "defrt"),
	description("description", "descn"),
	desiredHumidity("desiredHumidity", "desHy"),
	detectedTime("detectedTime", "detTe"),
	diastolicPressure("diastolicPressure", "diaPe"),
	directionAuto("directionAuto", "dirAo"),
	directionDown("directionDown", "dirDn"),
	directionLeft("directionLeft", "dirLt"),
	directionRight("directionRight", "dirRt"),
	directionUp("directionUp", "dirUp"),
	discharging("discharging", "discg"),
	doorState("doorState", "dooSt"),
	duration("duration", "duran"),
	estimatedTimeToEnd("estimatedTimeToEnd", "eTTEd"),
	fat("fat", "fat"),
	filterLifetime("filterLifetime", "filLe"),
	frequency("frequency", "freqy"),
	green("green", "green"),
	height("height", "heigt"),
	inputSourceID("inputSourceID", "inSId"),
	kcal("kcal", "kcal"),
	keyNumber("keyNumber", "keyNr"),
	level("level", "level"),
	light("light", "light"),
	liquidLevel("liquidLevel", "liqLv"),
	lowBattery("lowBattery", "lowBy"),
	lqi("lqi", "lqi"),
	maxValue("maxValue", "maxVe"),
	minValue("minValue", "minVe"),
	multiplyingFactors("multiplyingFactors", "mulFs"),
	muscle("muscle", "musce"),
	muteEnabled("muteEnabled", "mutEd"),
	openAlarm("openAlarm", "opeAm"),
	openDuration("openDuration", "opeDn"),
	operationMode("operationMode", "opeMe"),
	oxygenSaturation("oxygenSaturation", "oxySn"),
	power("power", "power"),
	powerGenerationData("powerGenerationData", "poGDa"),
	powerSaveEnabled("powerSaveEnabled", "poSEd"),
	powerState("powerState", "powSe"),
	previousChannel("previousChannel", "preCl"),
	pulseRate("pulseRate", "pulRe"),
	pushed("pushed", "pusBn"),
	rapidCool("rapidCool", "rapCl"),
	rapidFreeze("rapidFreeze", "rapFe"),
	red("red", "red"),
	referenceTimer("referenceTimer", "refTr"),
	relativeHumidity("relativeHumidity", "relHy"),
	resistance("resistance", "resie"),
	rinseLevel("rinseLevel", "rinLl"),
	roundingEnergyConsumption("roundingEnergyConsumption", "roECn"),
	roundingEnergyGeneration("roundingEnergyGeneration", "roEGn"),
	rssi("rssi", "rssi"),
	runningTime("runningTime", "runTe"),
	sensitivity("sensitivity", "sensy"),
	significantDigits("significantDigits", "sigDs"),
	silentTime("silentTime", "silTe"),
	status("status", "stats"),
	stepValue("stepValue", "steVe"),
	strength("strength", "streh"),
	supportedInputSources("supportedInputSources", "suISs"),
	supportedModes("supportedModes", "supMs"),
	systolicPressure("systolicPressure", "sysPe"),
	targetDuration("targetDuration", "tarDn"),
	targetTemperature("targetTemperature", "tarTe"),
	targetTimeToStart("targetTimeToStart", "tTTSt"),
	targetTimeToStop("targetTimeToStop", "tTTSp"),
	temperature("temperature", "tempe"),
	temperatureThreshhold("temperatureThreshhold", "temTd"),
	tone("tone", "tone"),
	turboEnabled("turboEnabled", "turEd"),
	unit("unit", "unit"),
	visceraFat("visceraFat", "visFt"),
	voltage("voltage", "volte"),
	volumePercentage("volumePercentage", "volPe"),
	water("water", "water"),
	weight("weight", "weigt"),

	atmosphericPressure("atmosphericPressure", "atmPe"),
	carbonDioxideValue("carbonDioxideValue", "cDeVe"),
	coarseness("coarseness", "coass"),
	cupsNumber("cupsNumber", "cupsN"),
	currentJobState("currentJobState", "curJS"),
	currentMachineState("currentMachineState", "curMS"),
	detectedPersons("detectedPersons", "detPs"),
	doorLock("doorLock", "dooLk"),
	foamingStrength("foamingStrength", "foaSt"),
	format("format", "frmt"),
	jobStates("jobStates", "jobSt"),
	login("login", "login"),
	machineStates("machineStates", "mchSt"),
	noise("noise", "noise"),
	password("password", "psWd"),
	progressPercentage("progressPercentage", "prgPc"),
	url("url", "url"),
	useGrinder("useGrinder", "useGr"),

	undefinedVendorExt("undefinedVendorExt", "undef");
	
	private final String longDefinitionName;
	private final String shortDefinitionName;
	
	DatapointType(String longDef, String shortDef) {
		longDefinitionName = longDef;
		shortDefinitionName = shortDef;
	}

    /**
	 * @return the longDefinitionName
	 */
	public String getLongName() {
		return longDefinitionName;
	}

	/**
	 * @return the shortDefinitionName
	 */
	public String getShortName() {
		return shortDefinitionName;
	}

    public static DatapointType fromLongName(String def) {
        for (DatapointType c: DatapointType.values()) {
            if (c.longDefinitionName.equals(def)) {
                return c;
            }
        }
		return null;
    }

    public static DatapointType fromShortName(String def) {
        for (DatapointType c: DatapointType.values()) {
            if (c.shortDefinitionName.equals(def)) {
                return c;
            }
        }
		return null;
    }

}
