/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

import org.eclipse.om2m.sdt.types.DataType;

public class HomeDataType extends DataType {

	static public final HomeDataType DeviceType = new HomeDataType("deviceType", HomeSimpleType.DeviceType);
	static public final HomeDataType SupportedInputSources = new HomeDataType("supportedInputSources", HomeSimpleType.SupportedInputSources);
	static public final HomeDataType LiquidLevel = new HomeDataType("liquidLevel", HomeSimpleType.LiquidLevel);
	static public final HomeDataType SpinLevelStrength = new HomeDataType("spinLevelStrength", HomeSimpleType.SpinLevelStrength);
	static public final HomeDataType DoorState = new HomeDataType("doorState", HomeSimpleType.DoorState);
	static public final HomeDataType Tone = new HomeDataType("tone", HomeSimpleType.Tone);
	static public final HomeDataType JobStates = new HomeDataType("jobStates", HomeSimpleType.JobStates);
	static public final HomeDataType AlertColourCode = new HomeDataType("alertColourCode", HomeSimpleType.AlertColourCode);
	static public final HomeDataType WaterFlowStrength = new HomeDataType("waterFlowStrength", HomeSimpleType.WaterFlowStrength);
	static public final HomeDataType WindStrength = new HomeDataType("windStrength", HomeSimpleType.WindStrength);
	static public final HomeDataType GrainsLevel = new HomeDataType("grainsLevel", HomeSimpleType.GrainsLevel);
	static public final HomeDataType FoamStrength = new HomeDataType("foamStrength", HomeSimpleType.FoamStrength);
	static public final HomeDataType TasteStrength = new HomeDataType("tasteStrength", HomeSimpleType.TasteStrength);
	static public final HomeDataType GrindCoarseness = new HomeDataType("grindCoarseness", HomeSimpleType.GrindCoarseness);
	static public final HomeDataType MachineState = new HomeDataType("machineState", HomeSimpleType.MachineState);
	static public final HomeDataType WashingCourse = new HomeDataType("washingCourse", HomeSimpleType.WashingCourse);
	static public final HomeDataType GeneralTemperature = new HomeDataType("generalTemperature", HomeSimpleType.GeneralTemperature);
	static public final HomeDataType GeneralLevel = new HomeDataType("generalLevel", HomeSimpleType.GeneralLevel);
	static public final HomeDataType GeneralSpeed = new HomeDataType("generalSpeed", HomeSimpleType.GeneralSpeed);

	public HomeDataType(final String name, final TypeChoice type) {
		super(name, type);
	}
	
}
