/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

import org.eclipse.om2m.sdt.types.Array;
import org.eclipse.om2m.sdt.types.DataType;

public class HomeDataType extends DataType {

	static public final HomeDataType AlertColourCode = new HomeDataType(HomeSimpleType.AlertColourCode);
	static public final HomeDataType DoorState = new HomeDataType(HomeSimpleType.DoorState);
	static public final HomeDataType FoamStrength = new HomeDataType(HomeSimpleType.FoamStrength);
	static public final HomeDataType GeneralLevel = new HomeDataType(HomeSimpleType.GeneralLevel);
	static public final HomeDataType GeneralSpeed = new HomeDataType(HomeSimpleType.GeneralSpeed);
	static public final HomeDataType GeneralTemperature = new HomeDataType(HomeSimpleType.GeneralTemperature);
	static public final HomeDataType GrainsLevel = new HomeDataType(HomeSimpleType.GrainsLevel);
	static public final HomeDataType GrindCoarseness = new HomeDataType(HomeSimpleType.GrindCoarseness);
	static public final HomeDataType JobState = new HomeDataType(HomeSimpleType.JobState);
	static public final HomeDataType LiquidLevel = new HomeDataType(HomeSimpleType.LiquidLevel);
	static public final HomeDataType MachineState = new HomeDataType(HomeSimpleType.MachineState);
	static public final HomeDataType PlayerMode = new HomeDataType(HomeSimpleType.PlayerMode);
	static public final HomeDataType SpinLevelStrength = new HomeDataType(HomeSimpleType.SpinLevelStrength);
	static public final HomeDataType SupportedMediaSources = new HomeDataType(HomeSimpleType.SupportedMediaSources);
	static public final HomeDataType TasteStrength = new HomeDataType(HomeSimpleType.TasteStrength);
	static public final HomeDataType Tone = new HomeDataType(HomeSimpleType.Tone);
	static public final HomeDataType WashingCourse = new HomeDataType(HomeSimpleType.WashingCourse);
	static public final HomeDataType WaterFlowStrength = new HomeDataType(HomeSimpleType.WaterFlowStrength);
	static public final HomeDataType WindStrength = new HomeDataType(HomeSimpleType.WindStrength);
	static public final HomeDataType UvStatus = new HomeDataType(HomeSimpleType.UvStatus);
	
	public HomeDataType(final TypeChoice type) {
		super(type);
	}
	
	static public DataType getDataType(final String name) {
		String s = name.trim();
		DataType ret = DataType.getDataType(s);
		if (ret != null) 
			return ret;
		if (s.startsWith("[")) {
			DataType dt = getDataType(s.substring(1, s.length()-1));
			return (dt == null) ? null : new DataType(new Array<>(dt));
		}
		HomeSimpleType st = (HomeSimpleType) HomeSimpleType.getSimpleType(s);
		if (st != null)
			return new HomeDataType(st);
		return null;
	}

}
