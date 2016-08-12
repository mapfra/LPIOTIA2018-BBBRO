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
import org.onem2m.sdt.impl.DataPointException;

public class MockedColour extends Colour {

	public MockedColour(String name, Domain domain) {
		super(name, domain,
			new IntegerDataPoint("red") {
				private Integer v = new Integer((int)(Math.random() * 255));
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					v = value;
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					return v;
				}
			}, 
			new IntegerDataPoint("green") {
				private Integer v = new Integer((int)(Math.random() * 255));
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					v = value;
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					return v;
				}
			}, 
			new IntegerDataPoint("blue") {
				private Integer v = new Integer((int)(Math.random() * 255));
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					v = value;
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					return v;
				}
			}
		);
	}

}
