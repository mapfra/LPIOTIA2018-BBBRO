/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.mocked.modules;

import java.util.Arrays;
import java.util.List;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.modules.RunState;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.JobState;
import org.eclipse.om2m.sdt.home.types.MachineState;

public class MockedRunState extends RunState {
	
	static private List<JobState.Values> jobStates = 
			Arrays.asList(JobState.Values.values());

	static private List<MachineState.Values> machineStates = 
			Arrays.asList(MachineState.Values.values());
	
	static private MachineState.Values state;

	public MockedRunState(String name, Domain domain) {
		super(name, domain,
			new JobState() {
				@Override
				protected JobState.Values doGetValue() throws DataPointException {
					return jobStates.get((int) (Math.random() * jobStates.size()));
				}
			},
			new ArrayDataPoint<JobState.Values>(DatapointType.jobStates) {
				@Override
				public List<JobState.Values> doGetValue() throws DataPointException {
					return jobStates;
				}
			}, 
			new MachineState() {
				@Override
				protected MachineState.Values doGetValue() throws DataPointException {
					if (state == null) {
						state = machineStates.get((int) (Math.random() * machineStates.size()));
					}
					return state;
				}
				@Override
				public void doSetValue(MachineState.Values value) throws DataPointException {
					state = value;
				}
			},
			new ArrayDataPoint<MachineState.Values>(DatapointType.machineStates) {
				@Override
				public List<MachineState.Values> doGetValue() throws DataPointException {
					return machineStates;
				}
			});
	}

}
