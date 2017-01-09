/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.smartercoffee.communication;


public class SmarterCoffeeCommands {

	//HashMap<Integer, String> cupNum = new HashMap<Integer, String>();
	
	public static final int MAX_NUMBER_OF_CUPS = 12;
	
	public static final int MIN_NUMBER_OF_CUPS = 1;
	
	

	public static final byte HEADER_STATUS = 		50; // TODO Add to doc: The status message is periodically sending after establish a connection with the machine
	
	public static final byte HEADER_START =			51; //Starts brewing coffee as specified by parameter values
	
	public static final byte HEADER_STARTX =		55; // Starts brewing coffee using parameter values set on the machine.
	
	public static final byte HEADER_STOP =			52;
	
	public static final byte HEADER_SETSTR =		53; //Sets brew strength. Doesn't start brewing.
	public static final byte HEADER_SETCUPS =		54; //Sets number of cups. Doesn't start brewing.
	public static final byte HEADER_GRINDTGGL =		60; //Toggles grinder use on and off. The coffee maker LCD shows Beans/Filter respectively. There are no separate 'set grinder on' and 'set grinder off' commands
	public static final byte HEADER_HOTPLATEON =	62; //Turns on the hot plate under the carafe and starts a timer to count down to turnoff.The Android app uses a hardcoded duration of 5 minutes.
	public static final byte HEADER_HOTPLATEOFF = 	74; //Turns off the hot plate. There is no delay. Use SET_HOT_PLATE_ON to turn off the hot plate after a specified interval
	public static final byte HEADER_SETTIME =		2;	//Sets the coffee maker's clock to the specified date and hour.
	public static final byte HEADER_RESET = 		16; //Resets the coffee maker settings (just the brew parameters -- not a factory reset).
	//public static final byte HEADER_SCHEDUPD =	64;
	//public static final byte HEADER_SCHEDACT =	67;
	//public static final byte HEADER_SCHEDREQ =	65;
	
	public static final byte HEADER_ACK = 			3;
	
	
	public static final byte STATUS_FLAGS_MASK_SCHEDULE_7 = 		(byte) 0x80;
	public static final byte STATUS_FLAGS_MASK_KEEP_WARM_6 = 		(byte) 0x40;
	public static final byte STATUS_FLAGS_MASK_CYCLE_COMPLETE_5 = 	(byte) 0x20;
	public static final byte STATUS_FLAGS_MASK_WATER_PUMP_4 = 		(byte) 0x10;
	public static final byte STATUS_FLAGS_MASK_GRIND_3 = 			(byte) 0x08;
	public static final byte STATUS_FLAGS_MASK_MACHINE_READY_2 = 	(byte) 0x04;
	public static final byte STATUS_FLAGS_MASK_USE_GRIDNER_1 = 		(byte) 0x02;
	public static final byte STATUS_FLAGS_CARAFE_DETECTED_0 = 		(byte) 0x01;
	
	
	public static final byte WATER_LEVEL_EMPTY = 	0;
	public static final byte WATER_LEVEL_LOW = 		1;
	public static final byte WATER_LEVEL_HALF = 	2;
	public static final byte WATER_LEVEL_FULL = 	19;
	
	public static final byte BREW_STRENGTH_0 = 		0;
	public static final byte BREW_STRENGTH_1 = 		1;
	public static final byte BREW_STRENGTH_2 = 		2;
	
	public static final byte END_OF_MESSAGE = 		126;
	
	
}