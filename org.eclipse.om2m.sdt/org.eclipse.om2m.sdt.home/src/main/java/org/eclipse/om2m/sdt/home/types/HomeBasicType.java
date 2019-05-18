/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

import org.eclipse.om2m.sdt.types.BasicType;

public class HomeBasicType extends BasicType {

	static private final String PREFIX = "hd:enum";
	static private final String S_ALERTCOLOURCODE = PREFIX + "AlertColourCode";
	static private final String S_DOORSTATE = PREFIX + "DoorState";
	static private final String S_FOAMSTRENGTH = PREFIX + "FoamStrength";
	static private final String S_GENERALLEVEL = PREFIX + "GeneralLevel";
	static private final String S_GENERALSPEED = PREFIX + "GeneralSpeed";
	static private final String S_GENERALTEMPERATURE = PREFIX + "GeneralTemperature";
	static private final String S_GRAINSLEVEL = PREFIX + "GrainsLevel";
	static private final String S_GRINDCOARSENESS = PREFIX + "GrindCoarseness";
	static private final String S_JOBSTATE = PREFIX + "JobState";
	static private final String S_LIQUIDLEVEL = PREFIX + "LiquidLevel";
	static private final String S_MACHINESTATE = PREFIX + "MachineState";
	static private final String S_PLAYERMODE = PREFIX + "PlayerMode";
	static private final String S_SPINLEVELSTRENGTH = PREFIX + "SpinLevelStrength";
	static private final String S_SUPPORTEDMEDIASOURCES = PREFIX + "SupportedMediaSource";
	static private final String S_TASTESTRENGTH = PREFIX + "TasteStrength";
	static private final String S_TONE = PREFIX + "Tone";
	static private final String S_WASHINGCOURSE = PREFIX + "washingCourse";
	static private final String S_WATERFLOWSTRENGTH = PREFIX + "WaterFlowStrength";
	static private final String S_WINDSTRENGTH = PREFIX + "WindStrength";
	static private final String S_UVSTATUS = PREFIX + "UvStatus";
	
	static public final HomeBasicType AlertColourCode = new HomeBasicType(S_ALERTCOLOURCODE, AlertColourCode.Values.class);
	static public final HomeBasicType DoorState = new HomeBasicType(S_DOORSTATE, DoorState.Values.class);
	static public final HomeBasicType FoamStrength = new HomeBasicType(S_FOAMSTRENGTH, FoamStrength.Values.class);
	static public final HomeBasicType GENERALLEVEL = new HomeBasicType(S_GENERALLEVEL, Integer.class);
	static public final HomeBasicType GENERALSPEED = new HomeBasicType(S_GENERALSPEED, Integer.class);
	static public final HomeBasicType GENERALTEMPERATURE = new HomeBasicType(S_GENERALTEMPERATURE, Integer.class);
	static public final HomeBasicType GrainsLevel = new HomeBasicType(S_GRAINSLEVEL, GrainsLevel.Values.class);
	static public final HomeBasicType GrindCoarseness = new HomeBasicType(S_GRINDCOARSENESS, GrindCoarseness.Values.class);
	static public final HomeBasicType JobState = new HomeBasicType(S_JOBSTATE, JobState.Values.class);
	static public final HomeBasicType LiquidLevel = new HomeBasicType(S_LIQUIDLEVEL, LiquidLevel.Values.class);
	static public final HomeBasicType MachineState = new HomeBasicType(S_MACHINESTATE, MachineState.Values.class);
	static public final HomeBasicType PlayerMode = new HomeBasicType(S_PLAYERMODE, PlayerMode.Values.class);
	static public final HomeBasicType SpinLevel = new HomeBasicType(S_SPINLEVELSTRENGTH, SpinLevel.Values.class);
	static public final HomeBasicType SupportedMediaSources = new HomeBasicType(S_SUPPORTEDMEDIASOURCES, SupportedMediaSources.Values.class);
	static public final HomeBasicType TasteStrength = new HomeBasicType(S_TASTESTRENGTH, TasteStrength.Values.class);
	static public final HomeBasicType Tone = new HomeBasicType(S_TONE, Tone.Values.class);
	static public final HomeBasicType WASHINGCOURSE = new HomeBasicType(S_WASHINGCOURSE, Integer.class);
	static public final HomeBasicType WATERFLOWSTRENGTH = new HomeBasicType(S_WATERFLOWSTRENGTH, Integer.class);
	static public final HomeBasicType WINDSTRENGTH = new HomeBasicType(S_WINDSTRENGTH, Integer.class);
	static public final HomeBasicType UVSTATUS = new HomeBasicType(S_UVSTATUS, Integer.class);
	
	
    protected HomeBasicType(String v, Class<?> c) {
    	super(v, c);
    }
	
	public Object fromString(String val) throws Exception {
		if (val == null)
			return null;
		if (getClazz().equals(Integer.class))
			return Integer.parseInt(val);
		if (getClazz().isEnum()) {
			for (Object item : getClazz().getEnumConstants()) {
				if (val.equals(item.toString()))
					return item;
			}
		}
		return null;
	}
	
	public String toString(Object val) {
		return (val == null) ? null : val.toString();
	}

}
