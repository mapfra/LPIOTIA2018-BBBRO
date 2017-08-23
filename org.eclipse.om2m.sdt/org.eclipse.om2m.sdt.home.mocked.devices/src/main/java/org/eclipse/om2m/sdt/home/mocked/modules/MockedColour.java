/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.mocked.modules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.mocked.devices.Activator;
import org.eclipse.om2m.sdt.home.modules.Colour;
import org.eclipse.om2m.sdt.home.types.DatapointType;

public class MockedColour extends Colour {
	
	static private int red, green, blue;

	public MockedColour(String name, Domain domain) {
		super(name, domain,
			new IntegerDataPoint(DatapointType.red) {
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					Activator.logger.info("set single red " + value);
					red = value;
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					int v = (red == 0) ? (int)(Math.random() * 255) : red;
					Activator.logger.info("get single red " + v);
					return v;
				}
			}, 
			new IntegerDataPoint(DatapointType.green) {
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					green = value;
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					return (green == 0) ? (int)(Math.random() * 255) : green;
				}
			}, 
			new IntegerDataPoint(DatapointType.blue) {
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					blue = value;
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					return (blue == 0) ? (int)(Math.random() * 255) : blue;
				}
			}
		);
		setDatapointHandler(new DatapointHandler() {
			@Override
			public void setValues(Map<String, Object> values)
					throws DataPointException, AccessException {
				Activator.logger.info("set values " + values);
				for (Map.Entry<String, Object> entry : values.entrySet()) {
					switch (entry.getKey()) {
					case "red": red = (int)entry.getValue(); break;
					case "green": green = (int)entry.getValue(); break;
					case "blue": blue = (int)entry.getValue(); break;
					default:
						break;
					}
				}
			}
			@Override
			public Map<String, Object> getValues(List<String> names)
					throws DataPointException, AccessException {
				Map<String, Object> ret = new HashMap<String, Object>();
				for (String name : names) {
					switch (name) {
					case "red": ret.put(name, red); break;
					case "green": ret.put(name, green); break;
					case "blue": ret.put(name, blue); break;
					default:
						break;
					}
				}
				Activator.logger.info("get values " + names + " -> " + ret);
				return ret;
			}
		});
	}

}
