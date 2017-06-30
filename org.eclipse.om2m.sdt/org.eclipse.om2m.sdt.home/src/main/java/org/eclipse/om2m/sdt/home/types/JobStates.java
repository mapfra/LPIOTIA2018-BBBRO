/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

import java.util.Arrays;
import java.util.List;

import org.eclipse.om2m.sdt.Identifiers;
import org.eclipse.om2m.sdt.datapoints.ClonedEnum;
import org.eclipse.om2m.sdt.datapoints.EnumDataPoint;

public class JobStates extends ClonedEnum {
	
//	0~99		Reserved for future use
//	100~199		Reserved for deviceAirConditioner
//	200	idle	This value is for deviceClothesWasher and indicates idle state.
//	201	preWash	This value is for deviceClothesWasher and indicates pre-washing state. pre-wash is a cold water cycle that runs prior to the main wash cycle. It is used for heavily soiled laundy.
//	202	wash	This value is for deviceClothesWasher and indicates washing state.
//	203	rinse	This value is for deviceClothesWasher and indicates rinsing state.
//	204	spin	This value is for deviceClothesWasher and indicates spinning state.
//	205	dry		This value is for deviceClothesWasher and indicates drying state.
//	206	airDry	This value is for deviceClothesWasher and indicates air-drying state. In airDry state, a rotating wheel circulates air to get fresh air used to dry laundry.  
//	207	wrinkleProof	This value is for deviceClothesWasher and indicates wrinkleProof state.
//	208	soak	This value is for deviceClothesWasher and indicates soak state.
//	209~299		Reserved for deviceClothesWasher
//	300~399		Reserved for deviceElectricVehicleCharger
//	400~499		Reserved for deviceLight
//	500~599		Reserved for deviceMicrogeneration
//	600	warmingUp 	This value is for deviceOven and indicates warmingUp state.
//	601	cooking 	This value is for deviceOven and indicates cooking state.
//	602	cooling	This value is for deviceOven and indicates cooling state.
//	603~699		Reserved for deviceOven
//	700~799		Reserved for deviceRefrigerator
//	800	charging	This value is for deviceRobotCleaner and indicates charging state.
//	801	homing	This value is for deviceRobotCleaner and indicates homing state.
//	802	docking	This value is for deviceRobotCleaner and indicates docking state.
//	803~899		Reserved for deviceRobotCleaner
//	900~999		Reserved for deviceSmartElectricMeter
//	1000~1099		Reserved for deviceStorageBattery
//	1100~1199		Reserved for deviceTelevision
//	1200	antifreeze	This mode sets the thermostat to a minimum temperature to avoid home system to freeze when the habitants are not there for a long time
//	1201	manual	This mode allows for direct change of the temperature indication for the thermostat by the user.
//	1202	eco	This is to set the thermostat to the economic mode
//	1203	program	The program mode is used to set the thermostat to a predefined mode
//	1204~1299		Reserved for deviceThermostat
//	1300~1399		Reserved for deviceWaterHeater
//	1400~1499		Reserved for deviceCoffeeMachine

	static public final int idle 			= 200;
	static public final int preWash			= 201;
	static public final int wash 			= 202;
	static public final int rinse	 		= 203;
	static public final int spin 			= 204;
	static public final int dry 			= 205;
	static public final int airDry	 		= 206;
	static public final int wrinkleProof	= 207;
	static public final int soak	 		= 208;
	
	static public final int noeffect		= 400;
	static public final int colorloop		= 401;
	static public final int noalert	 		= 402;
	static public final int lselect	 		= 403;
	static public final int select	 		= 404;

	static public final int warmingUp 		= 600;
	static public final int cooking 		= 601;
	static public final int cooling 		= 602;
	
	static public final int charging 		= 800;
	static public final int homing 			= 801;
	static public final int docking 		= 802;
	
	static public final int antifreeze 		= 1200;
	static public final int manual	 		= 1201;
	static public final int eco		 		= 1202;
	static public final int program		 	= 1203;
	
	static private List<Integer> values = Arrays.asList(
			idle, preWash, wash, rinse, spin, dry, airDry, wrinkleProof, soak,
			noeffect, colorloop, noalert, lselect, select,
			warmingUp, cooking, cooling, 
			charging, homing, docking,
			antifreeze, manual, eco, program
	);
	
	public JobStates(EnumDataPoint<Integer> dp) {
		this(DatapointType.currentJobState, dp);
	}
	
	public JobStates(Identifiers name, EnumDataPoint<Integer> dp) {
		super(name, HomeDataType.JobStates, dp);
		setValidValues(values);
	}
	
	static public List<Integer> getValues() {
		return values;
	}

}
