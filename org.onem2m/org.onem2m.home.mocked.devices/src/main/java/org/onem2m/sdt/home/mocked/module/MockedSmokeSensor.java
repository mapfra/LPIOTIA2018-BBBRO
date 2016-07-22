/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.SmokeSensor;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.BooleanDataPoint;

public class MockedSmokeSensor extends SmokeSensor {

	public MockedSmokeSensor(String name, Domain domain, BooleanDataPoint alarm) {
		super(name, domain, alarm);
	}

}
