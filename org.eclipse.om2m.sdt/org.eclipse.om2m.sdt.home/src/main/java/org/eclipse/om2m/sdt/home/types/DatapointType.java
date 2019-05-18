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

public enum DatapointType implements Identifiers.Typed {
	
	absoluteEnergyConsumption,
	absoluteStartTime,
	absoluteStopTime,
	acousticStatus,
	alarm,
	alarmStatus,
	atmosphericPressure,
	availableChannels,
	bath,
	batteryThreshold,
	blue,
	bone,
	brightness,
	capacity,
	ch2o, 
	channelId,
	charging,
	co, 
	co2, 
	coarseness,
	code,
	colourSaturation,
	cupsNumber,
	current,
	currentDate,
	currentJobState,
	currentMachineState,
	currentPlayerMode,
	currentPlayerModeName,
	currentTemperature,
	currentTime,
	currentValue,
	defaultValue,
	defrost,
	description,
	desiredHumidity,
	detectedPersons,
	detectedTime,
	diastolicPressure,
	discharging,
	doorState,
	duration,
	estimatedTimeToEnd,
	fat,
	filterLifetime,
	foamingStrength,
	frequency,
	grainsRemaining,
	green,
	height,
	jobStates,
	kcal,
	keyNumber,
	level,
	light,
	liquidLevel,
	lock,
	loginName,
	loudness,
	lowBattery,
	lqi,
	machineStates,
	maxPressureThreshhold,
	maxValue,
	mediaID,
	mediaName,
	mediaType,
	minPressureThreshhold,
	minValue,
	monitoringEnabled,
	multiplyingFactors,
	muscle,
	muteEnabled,
	numberValue,
	openAlarm,
	openDuration,
	oxygenSaturation,
	password,
	power,
	powerGenerationData,
	powerSaveEnabled,
	powerState,
	previousChannel,
	progressPercentage,
	pulseRate,
	pushed,
	rapidCool,
	rapidFreeze,
	red,
	referenceTimer,
	relativeHumidity,
	resistance,
	roundingEnergyConsumption,
	roundingEnergyGeneration,
	rssi,
	runningTime,
	sdp,
	sensitivity,
	sensorHumidity, 
	sensorOdor,
	sensorPM1, 
	sensorPM10,
	sensorPM2, 
	significantDigits,
	silentTime,
	smokeThreshhold,
	speed,
	speedFactor,
	spinLevelStrength,
	status,
	step,
	stepValue,
	strength,
	supportedMediaSources,
	supportedPlayerModes,
	systolicPressure,
	targetDuration,
	targetTemperature,
	targetTimeToStart,
	targetTimeToStop,
	temperature,
	temperatureThreshhold,
	token,
	tone,
	turboEnabled,
	unit,
	url,
	useGrinder,
	uvStatus,
	uvValue,
	visceraFat,
	voc,
	voltage,
	volumePercentage,
	water,
	weight,
	;
	
	private final String longDefinitionName;
	private final String shortDefinitionName;
	private final DataType dataType;
	
	DatapointType() {
		longDefinitionName = toString();
		String[] dp = FlexContainers.getDataPoint(longDefinitionName);
		shortDefinitionName = dp[0];
		dataType = HomeDataType.getDataType(dp[1]);
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
	
	@Override
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
