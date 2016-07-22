/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.RunMode;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.ArrayDataPoint;

public class MockedRunMode extends RunMode {

	public MockedRunMode(String name, Domain domain,
			ArrayDataPoint<String> operationMode, 
			ArrayDataPoint<String> supportedModes) {
		super(name, domain, operationMode, supportedModes);
	}

}
