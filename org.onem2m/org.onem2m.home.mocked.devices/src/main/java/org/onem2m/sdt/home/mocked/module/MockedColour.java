/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.Colour;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.IntegerDataPoint;

public class MockedColour extends Colour {

	public MockedColour(String name, Domain domain, IntegerDataPoint red, IntegerDataPoint green,
			IntegerDataPoint blue) {
		super(name, domain, red, green, blue);
	}

}
