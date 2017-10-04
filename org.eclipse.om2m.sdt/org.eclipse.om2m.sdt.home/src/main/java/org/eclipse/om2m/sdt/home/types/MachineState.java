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

public class MachineState extends ClonedEnum {
	
//	1	idle	Machine is ready to operate
//	2	active	Machine is operating its functions
//	3	reserved	Reservation is made by user
//	4	paused	Operation is paused by user
//	5	cancelled	Operation is cancelled by user
//	6	stopped	Operation is stopped/aborted by some other reasons
//	7	complete	Operation is complete
//	8	error	Error has occurred 
//	9	diagnostic	Machine reports diagnostic information to the server
//	10	test	Particular functions run for test

	static public final int idle 		= 1;
	static public final int active		= 2;
	static public final int reserved 	= 3;
	static public final int paused	 	= 4;
	static public final int cancelled 	= 5;
	static public final int stopped 	= 6;
	static public final int complete	= 7;
	static public final int error		= 8;
	static public final int diagnostic	= 9;
	static public final int test 		= 10;
	
	static private List<Integer> values = Arrays.asList(
			idle, active, reserved, paused, cancelled, 
			stopped, complete, error, diagnostic, test
	);

	public MachineState(EnumDataPoint<Integer> dp) {
		this(DatapointType.currentMachineState, dp);
	}

	public MachineState(Identifiers name, EnumDataPoint<Integer> dp) {
		super(name, HomeDataType.MachineState, dp);
		setValidValues(values);
	}

}
