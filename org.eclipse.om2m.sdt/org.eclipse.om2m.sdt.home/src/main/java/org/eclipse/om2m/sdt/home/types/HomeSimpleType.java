/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

import org.eclipse.om2m.sdt.types.BasicType;
import org.eclipse.om2m.sdt.types.SimpleType;

public class HomeSimpleType extends SimpleType {

	static public final HomeSimpleType AlertColourCode = new HomeSimpleType(HomeBasicType.AlertColourCode);
	static public final HomeSimpleType DoorState = new HomeSimpleType(HomeBasicType.DoorState);
	static public final HomeSimpleType FoamStrength = new HomeSimpleType(HomeBasicType.FoamStrength);
	static public final HomeSimpleType GeneralLevel = new HomeSimpleType(HomeBasicType.GENERALLEVEL);
	static public final HomeSimpleType GeneralSpeed = new HomeSimpleType(HomeBasicType.GENERALSPEED);
	static public final HomeSimpleType GeneralTemperature = new HomeSimpleType(HomeBasicType.GENERALTEMPERATURE);
	static public final HomeSimpleType GrainsLevel = new HomeSimpleType(HomeBasicType.GrainsLevel);
	static public final HomeSimpleType GrindCoarseness = new HomeSimpleType(HomeBasicType.GrindCoarseness);
	static public final HomeSimpleType JobState = new HomeSimpleType(HomeBasicType.JobState);
	static public final HomeSimpleType LiquidLevel = new HomeSimpleType(HomeBasicType.LiquidLevel);
	static public final HomeSimpleType MachineState = new HomeSimpleType(HomeBasicType.MachineState);
	static public final HomeSimpleType PlayerMode = new HomeSimpleType(HomeBasicType.PlayerMode);
	static public final HomeSimpleType SpinLevelStrength = new HomeSimpleType(HomeBasicType.SpinLevel);
	static public final HomeSimpleType SupportedMediaSources = new HomeSimpleType(HomeBasicType.SupportedMediaSources);
	static public final HomeSimpleType TasteStrength = new HomeSimpleType(HomeBasicType.TasteStrength);
	static public final HomeSimpleType Tone = new HomeSimpleType(HomeBasicType.Tone);
	static public final HomeSimpleType WashingCourse = new HomeSimpleType(HomeBasicType.WASHINGCOURSE);
	static public final HomeSimpleType WaterFlowStrength = new HomeSimpleType(HomeBasicType.WATERFLOWSTRENGTH);
	static public final HomeSimpleType WindStrength = new HomeSimpleType(HomeBasicType.WINDSTRENGTH);
	static public final HomeSimpleType UvStatus = new HomeSimpleType(HomeBasicType.UVSTATUS);
	
	protected HomeSimpleType(final BasicType type) {
		super(type);
	}

}
