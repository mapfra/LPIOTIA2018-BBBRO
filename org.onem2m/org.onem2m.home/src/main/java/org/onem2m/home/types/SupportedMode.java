/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.types;

import org.onem2m.sdt.datapoints.EnumDataPoint;

public abstract class SupportedMode extends EnumDataPoint<Integer> {
	
	static public final int antifreeze 		= 1;
	static public final int manual 			= 2;
	static public final int eco 			= 3;
	static public final int program 		= 4;
	static public final int off 			= 5;
	static public final int ready 			= 6;
	static public final int running 		= 7;
	static public final int paused 			= 8;
	static public final int aborted 		= 9;
	static public final int cancelled 		= 10;
	static public final int completed 		= 11;
	static public final int washing 		= 12;
	static public final int spinning 		= 13;
	static public final int drying 			= 14;
	static public final int rinsing 		= 15;
	static public final int warming_up 		= 16;
	static public final int cooking 		= 17;
	static public final int cooling 		= 18;
	static public final int dehumidifying 	= 19;
	static public final int energy_saving 	= 20;
	static public final int charging 		= 21;
	static public final int homing 			= 22;
	static public final int docking 		= 23;
	
	public SupportedMode(String name) {
		super(name, HomeDataType.SupportedMode);
		setValidValues(new Integer[] { antifreeze, manual, eco, program, off, ready, running, 
			paused, aborted, cancelled, completed, washing, spinning, drying, rinsing, 
			warming_up, cooking, cooling, dehumidifying, energy_saving, charging, homing, docking });
	}

}
