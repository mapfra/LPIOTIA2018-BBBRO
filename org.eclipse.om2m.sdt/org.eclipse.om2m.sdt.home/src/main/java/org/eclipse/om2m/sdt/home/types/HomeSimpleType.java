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

	static public final HomeSimpleType AlertColourCode = new HomeSimpleType(HomeBasicType.ALERTCOLOURCODE);
	static public final HomeSimpleType DeviceType = new HomeSimpleType(HomeBasicType.DEVICETYPE);
	static public final HomeSimpleType SupportedInputSources = new HomeSimpleType(HomeBasicType.SUPPORTEDINPUTSOURCES);
	static public final HomeSimpleType LiquidLevel = new HomeSimpleType(HomeBasicType.LIQUIDLEVEL);
	static public final HomeSimpleType SpinLevelStrength = new HomeSimpleType(HomeBasicType.SPINLEVELSTRENGTH);
	static public final HomeSimpleType DoorState = new HomeSimpleType(HomeBasicType.DOORSTATE);
	static public final HomeSimpleType Tone = new HomeSimpleType(HomeBasicType.TONE);
	static public final HomeSimpleType JobStates = new HomeSimpleType(HomeBasicType.JOBSTATES);
	static public final HomeSimpleType WaterFlowStrength = new HomeSimpleType(HomeBasicType.WATERFLOWSTRENGTH);
	static public final HomeSimpleType WindStrength = new HomeSimpleType(HomeBasicType.WINDSTRENGTH);
	static public final HomeSimpleType GrainsLevel = new HomeSimpleType(HomeBasicType.GRAINSLEVEL);
	static public final HomeSimpleType FoamStrength = new HomeSimpleType(HomeBasicType.FOAMSTRENGTH);
	static public final HomeSimpleType TasteStrength = new HomeSimpleType(HomeBasicType.TASTESTRENGTH);
	static public final HomeSimpleType GrindCoarseness = new HomeSimpleType(HomeBasicType.GRINDCOARSENESS);
	static public final HomeSimpleType MachineState = new HomeSimpleType(HomeBasicType.MACHINESTATE);
	static public final HomeSimpleType WashingCourse = new HomeSimpleType(HomeBasicType.WASHINGCOURSE);
	static public final HomeSimpleType GeneralTemperature = new HomeSimpleType(HomeBasicType.GENERALTEMPERATURE);
	static public final HomeSimpleType GeneralLevel = new HomeSimpleType(HomeBasicType.GENERALLEVEL);
	static public final HomeSimpleType GeneralSpeed = new HomeSimpleType(HomeBasicType.GENERALSPEED);
	
	protected HomeSimpleType(final BasicType type) {
		super(type);
	}
	
	public String getOneM2MType() {
		return "hd:" + getType().getValue();
	}

}
