/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.home.mocked.module;

import java.util.List;

import org.onem2m.home.modules.RunMode;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.ArrayDataPoint;
import org.onem2m.sdt.impl.DataPointException;

public class MockedRunMode extends RunMode {
	
	public MockedRunMode(String name, Domain domain) {
		super(name, domain,
			new ArrayDataPoint<String>("operationMode") {
				private List<String> operationModes;
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
				private List<String> supportedModes;
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
