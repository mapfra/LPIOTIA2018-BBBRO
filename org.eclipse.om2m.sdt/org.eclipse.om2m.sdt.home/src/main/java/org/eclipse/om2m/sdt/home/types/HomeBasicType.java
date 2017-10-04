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

	static public final HomeBasicType DEVICETYPE = new HomeBasicType("deviceType", Integer.class);
	static public final HomeBasicType SUPPORTEDINPUTSOURCES = new HomeBasicType("supportedInputSources", Integer.class);
	static public final HomeBasicType LIQUIDLEVEL = new HomeBasicType("liquidLevel", Integer.class);
	static public final HomeBasicType SPINLEVELSTRENGTH = new HomeBasicType("spinLevelStrength", Integer.class);
	static public final HomeBasicType DOORSTATE = new HomeBasicType("doorState", Integer.class);
	static public final HomeBasicType TONE = new HomeBasicType("tone", Integer.class);
	static public final HomeBasicType JOBSTATES = new HomeBasicType("jobStates", Integer.class);
	static public final HomeBasicType ALERTCOLOURCODE = new HomeBasicType("alertColourCode", Integer.class);
	static public final HomeBasicType WATERFLOWSTRENGTH = new HomeBasicType("waterFlowStrength", Integer.class);
	static public final HomeBasicType WINDSTRENGTH = new HomeBasicType("windStrength", Integer.class);
	static public final HomeBasicType GRAINSLEVEL = new HomeBasicType("grainsLevel", Integer.class);
	static public final HomeBasicType FOAMSTRENGTH = new HomeBasicType("foamStrength", Integer.class);
	static public final HomeBasicType TASTESTRENGTH = new HomeBasicType("tasteStrength", Integer.class);
	static public final HomeBasicType GRINDCOARSENESS = new HomeBasicType("grindCoarseness", Integer.class);
	static public final HomeBasicType MACHINESTATE = new HomeBasicType("machineState", Integer.class);
	static public final HomeBasicType WASHINGCOURSE = new HomeBasicType("washingCourse", Integer.class);
	static public final HomeBasicType GENERALTEMPERATURE = new HomeBasicType("generalTemperature", Integer.class);
	static public final HomeBasicType GENERALLEVEL = new HomeBasicType("generalLevel", Integer.class);
	static public final HomeBasicType GENERALSPEED = new HomeBasicType("generalSpeed", Integer.class);

    protected HomeBasicType(String v, Class<?> c) {
    	super(v, c);
    }
    
}
