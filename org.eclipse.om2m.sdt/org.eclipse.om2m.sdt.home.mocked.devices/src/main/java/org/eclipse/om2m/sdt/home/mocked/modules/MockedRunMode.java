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
import org.eclipse.om2m.sdt.home.modules.RunMode;

public class MockedRunMode extends RunMode {
	
	public MockedRunMode(String name, Domain domain) {
		super(name, domain,
			new ArrayDataPoint<String>("operationMode") {
				private List<String> operationModes = Arrays.asList("mode1", "mode2", "mode3");
				@Override
				public List<String> doGetValue() throws DataPointException {
					return operationModes;
				}
				@Override
				public void doSetValue(List<String> vals) throws DataPointException {
					operationModes = vals;
				}
			}, 
			new ArrayDataPoint<String>("supportedModes") {
				private List<String> supportedModes = Arrays.asList("mode1", "mode3");
				@Override
				public List<String> doGetValue() throws DataPointException {
					return supportedModes;
				}
				@Override
				public void doSetValue(List<String> value) throws DataPointException {
					supportedModes = value;
				}
			});
	}

}
