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
	
	acousticSensor,
	airQualitySensor,
	alarmSpeaker,
	anemometer,
	audioVolume,
	barometer,
	battery,
	binarySwitch,
	bioElectricalImpedanceAnalysis,
	boiler,
	brewing,
	brightness,
	clock,
	colour,
	colourSaturation,
	contactSensor,
	credentials,
	doorStatus,
	electricVehicleConnector,
	energyConsumption,
	energyGeneration,
	faultDetection,
	foaming,
	grinder,
	height,
	hotWaterSupply,
	keepWarm,
	keypad,
	liquidLevel,
	lock,
	motionSensor,
	numberValue,
	oximeter,
	personSensor,
	playerControl,
	powerSave,
	pushButton,
	recorder,
	refrigeration,
	relativeHumidity,
	runState,
	sessionDescription,
	signalStrength,
	smokeSensor,
	spinLevel,
	televisionChannel,
	temperature,
	temperatureAlarm,
	timer,
	turbo,
	uvSensor,
	waterFlow,
	waterSensor,
	weight,
	;
	
	private final String longName;
	private final String shortName;
	private final String cnd;
	
	ModuleType() {
		longName = toString();
		shortName = FlexContainers.getFlexShortName(longName);
		cnd = FlexContainers.getContainerDefinition(longName);
//		System.out.println("MOD " + longName + "/" + shortName + "/" + cnd);
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
