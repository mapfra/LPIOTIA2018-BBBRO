/*******************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

import org.eclipse.om2m.sdt.Identifiers;
import org.eclipse.om2m.sdt.types.DataType;

public enum DatapointType implements Identifiers {
	
	absoluteEnergyConsumption("absoluteEnergyConsumption", "abECn", DataType.Float),
	absoluteStartTime("absoluteStartTime", "abSTe", DataType.Time),
	absoluteStopTime("absoluteStopTime", "abST0", DataType.Time),
	alarm("alarm", "alarm", DataType.Boolean),
	alarmStatus("alarmStatus", "alaSs", DataType.Boolean),
	availableChannels("availableChannels", "avaCs", DataType.Integer),
	bath("bath", "bath", DataType.Boolean),
	batteryThreshold("batteryThreshold", "batTd", DataType.Integer),
	blue("blue", "blue", DataType.Integer),
	bone("bone", "bone", DataType.Float),
	brightness("brightness", "brigs",  DataType.Integer),
	capacity("capacity", "capay",  DataType.Integer),
	channelId("channelId", "chaId", DataType.Integer),
	charging("charging", "charg", DataType.Boolean),
	code("code", "code", DataType.Integer),
	colourSat("colourSat", "colSn",  DataType.Integer),
	current("current", "currt", DataType.Float),
	currentDate("currentDate", "curDe", DataType.Date),
	currentTemperature("currentTemperature", "curT0", DataType.Float),
	currentTime("currentTime", "curTe", DataType.Time),
	defrost("defrost", "defrt", DataType.Boolean),
	description("description", "descn", DataType.String),
	desiredHumidity("desiredHumidity", "desHy", DataType.Float),
	detectedTime("detectedTime", "detTe", DataType.Datetime),
	diastolicPressure("diastolicPressure", "diaPe", DataType.Integer),
	directionAuto("directionAuto", "dirAo", DataType.Boolean),
	directionDown("directionDown", "dirDn", DataType.Boolean),
	directionLeft("directionLeft", "dirLt", DataType.Boolean),
	directionRight("directionRight", "dirRt", DataType.Boolean),
	directionUp("directionUp", "dirUp", DataType.Boolean),
	discharging("discharging", "discg", DataType.Boolean),
	doorState("doorState", "dooSt", DataType.Integer),
	duration("duration", "duran", DataType.Integer),
	estimatedTimeToEnd("estimatedTimeToEnd", "eTTEd", DataType.Integer),
	fat("fat", "fat", DataType.Float),
	filterLifetime("filterLifetime", "filLe", DataType.Integer),
	frequency("frequency", "freqy", DataType.Float),
	green("green", "green", DataType.Integer),
	height("height", "heigt", DataType.Float),
	inputSourceID("inputSourceID", "inSId", DataType.Integer),
	kcal("kcal", "kcal", DataType.Float),
	keyNumber("keyNumber", "keyNr", DataType.Integer),
	level("level", "level", DataType.Integer),
	light("light", "light", DataType.Integer),
	liquidLevel("liquidLevel", "liqLv", DataType.Integer),
	lowBattery("lowBattery", "lowBy", DataType.Boolean),
	lqi("lqi", "lqi", DataType.Integer),
	maxValue("maxValue", "maxVe", DataType.Float),
	minValue("minValue", "minVe", DataType.Float),
	multiplyingFactors("multiplyingFactors", "mulFs", DataType.Float),
	muscle("muscle", "musce", DataType.Float),
	muteEnabled("muteEnabled", "mutEd", DataType.Boolean),
	openAlarm("openAlarm", "opeAm", DataType.Boolean),
	openDuration("openDuration", "opeDn", DataType.Time),
	operationMode("operationMode", "opeMe", DataType.Integer),
	oxygenSaturation("oxygenSaturation", "oxySn", DataType.Integer),
	power("power", "power", DataType.Float),
	powerGenerationData("powerGenerationData", "poGDa", DataType.Float),
	powerSaveEnabled("powerSaveEnabled", "poSEd", DataType.Boolean),
	powerState("powerState", "powSe", DataType.Boolean),
	previousChannel("previousChannel", "preCl", DataType.Integer),
	pulseRate("pulseRate", "pulRe", DataType.Integer),
	pushed("pushed", "pusBn", DataType.Boolean),
	rapidCool("rapidCool", "rapCl", DataType.Boolean),
	rapidFreeze("rapidFreeze", "rapFe", DataType.Boolean),
	red("red", "red", DataType.Integer),
	referenceTimer("referenceTimer", "refTr", DataType.Integer),
	relativeHumidity("relativeHumidity", "relHy", DataType.Float),
	resistance("resistance", "resie", DataType.Float),
	rinseLevel("rinseLevel", "rinLl", DataType.Integer),
	roundingEnergyConsumption("roundingEnergyConsumption", "roECn", DataType.Integer),
	roundingEnergyGeneration("roundingEnergyGeneration", "roEGn", DataType.Integer),
	rssi("rssi", "rssi", DataType.Float),
	runningTime("runningTime", "runTe", DataType.Integer),
	sensitivity("sensitivity", "sensy", DataType.Integer),
	significantDigits("significantDigits", "sigDs", DataType.Integer),
	silentTime("silentTime", "silTe", DataType.Integer),
	status("status", "stats", DataType.Boolean),
	stepValue("stepValue", "steVe", DataType.Float),
	strength("strength", "streh", DataType.Integer),
	supportedInputSources("supportedInputSources", "suISs", DataType.Integer),
	supportedModes("supportedModes", "supMs", DataType.Integer),
	systolicPressure("systolicPressure", "sysPe", DataType.Integer),
	targetDuration("targetDuration", "tarDn", DataType.Integer),
	targetTemperature("targetTemperature", "tarTe", DataType.Float),
	targetTimeToStart("targetTimeToStart", "tTTSt", DataType.Integer),
	targetTimeToStop("targetTimeToStop", "tTTSp", DataType.Integer),
	temperature("temperature", "tempe", DataType.Float),
	temperatureThreshhold("temperatureThreshhold", "temTd", DataType.Integer),
	tone("tone", "tone", DataType.Integer),
	turboEnabled("turboEnabled", "turEd", DataType.Boolean),
	unit("unit", "unit", DataType.String),
	visceraFat("visceraFat", "visFt", DataType.Float),
	voltage("voltage", "volte", DataType.Float),
	volumePercentage("volumePercentage", "volPe", DataType.Integer),
	water("water", "water", DataType.Float),
	weight("weight", "weigt", DataType.Float),

	atmosphericPressure("atmosphericPressure", "atmPe", DataType.Float),
	carbonDioxideValue("carbonDioxideValue", "cDeVe", DataType.Float),
	coarseness("coarseness", "coass", DataType.Integer),
	cupsNumber("cupsNumber", "cupsN", DataType.Integer),
	currentJobState("currentJobState", "curJS", DataType.String),
	currentMachineState("currentMachineState", "curMS", DataType.String),
	detectedPersons("detectedPersons", "detPs", DataType.String),
	doorLock("doorLock", "dooLk", DataType.Boolean),
	foamingStrength("foamingStrength", "foaSt", DataType.Integer),
	format("format", "frmt", DataType.String),
	jobStates("jobStates", "jobSt", DataType.String),
	login("login", "login", DataType.String),
	machineStates("machineStates", "mchSt", DataType.String),
	noise("noise", "noise", DataType.Integer),
	password("password", "psWd", DataType.String),
	progressPercentage("progressPercentage", "prgPc", DataType.Integer),
	url("url", "url", DataType.String),
	useGrinder("useGrinder", "useGr", DataType.Boolean),
	
	numberValue("numberValue", "numVe", DataType.Float),		// Added by Andreas Kraft
	defaultValue("defaultValue", "defVe", DataType.Float),	// Added by Andreas Kraft
	step("step", "step", DataType.Float),					// Added by Andreas Kraft

	undefinedVendorExt("undefinedVendorExt", "undef", DataType.String);
	
	private final String longDefinitionName;
	private final String shortDefinitionName;
	private final DataType dataType;
	
	DatapointType(String longDef, String shortDef, DataType pDataType) {
		longDefinitionName = longDef;
		shortDefinitionName = shortDef;
		dataType = pDataType;
		
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

	@Override
	public String getDefinition() {
		return null;
	}
	
	public DataType getDataType() {
		return dataType;
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
