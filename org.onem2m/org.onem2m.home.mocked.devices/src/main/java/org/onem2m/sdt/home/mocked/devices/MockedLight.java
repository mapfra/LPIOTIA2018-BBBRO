/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.home.mocked.devices;

import java.util.Arrays;
import java.util.List;

import org.onem2m.home.devices.Light;
import org.onem2m.home.modules.Colour;
import org.onem2m.home.modules.ColourSaturation;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.ArrayDataPoint;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.home.mocked.module.MockedBinarySwitch;
import org.onem2m.sdt.home.mocked.module.MockedFaultDetection;
import org.onem2m.sdt.home.mocked.module.MockedRunMode;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;
import org.osgi.framework.ServiceRegistration;

public class MockedLight extends Light implements MockedDevice {

	private List<ServiceRegistration> serviceRegistrations;

	public MockedLight(String id, String serial, Domain domain, String location) {
		super(id, serial, domain);

		// set property
		setLocation(location);

		// Module FaultDetection
		addModule(new MockedFaultDetection("faultDetection_" + id, domain, 
				new BooleanDataPoint("status") {

			Boolean status = Boolean.FALSE;

			@Override
			public void doSetValue(Boolean value) throws DataPointException {
				status = value;
			}

			@Override
			public Boolean doGetValue() throws DataPointException {
				return status;
			}
		}));

		// Module BinarySwitch
		addModule(new MockedBinarySwitch("binarySwitch_" + id, domain, 
				new BooleanDataPoint("powerState") {

			Boolean powerState = Boolean.FALSE;

			@Override
			public void doSetValue(Boolean value) throws DataPointException {
				powerState = value;
			}

			@Override
			public Boolean doGetValue() throws DataPointException {
				return powerState;
			}
		}));

		// Module RunMode
		addModule(new MockedRunMode("runMode_" + id, domain, 
				new ArrayDataPoint<String>("operationMode") {

			List<String> values = Arrays.asList("mode1");

			@Override
			public void doSetValue(List<String> value) throws DataPointException {
				try {
					List<String> possibleValues = getRunMode().getSupportedModes();
					for (String v : value) {
						if (! possibleValues.contains(v)) {
							throw new DataPointException("value " + v + " is not permitted");
						}
					}
					values = value;
				} catch (AccessException e) {
					throw new DataPointException(e);	
				}
			}

			@Override
			public List<String> doGetValue() throws DataPointException {
				return values;
			}
		}, 
		new ArrayDataPoint<String>("supportedModes") {

			List<String> values = Arrays.asList("mode1", "mode2", "mode3");

			@Override
			public void doSetValue(List<String> value) throws DataPointException {
				values = value;
			}

			@Override
			public List<String> doGetValue() throws DataPointException {
				return values;
			}
		}));

		// Module Colour
		addModule(new Colour("colour", domain, 
				new IntegerDataPoint("red") {

			Integer v = new Integer(0);

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

			Integer v = new Integer(0);


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

			Integer v = new Integer(0);

			@Override
			public void doSetValue(Integer value) throws DataPointException {
				v = value;
			}

			@Override
			public Integer doGetValue() throws DataPointException {
				return v;
			}
		}));

		addModule(new ColourSaturation("colourSaturation", domain, 
				new IntegerDataPoint("colourSaturation") {

			Integer v = new Integer(0);

			@Override
			public void doSetValue(Integer value) throws DataPointException {
				v = value;
			}

			@Override
			public Integer doGetValue() throws DataPointException {
				return v;
			}
		}));
	}

	public void registerDevice() {
		if (! ((serviceRegistrations == null) || serviceRegistrations.isEmpty())) {
			return;
		}
		serviceRegistrations = Activator.register(this);
	}

	public void unregisterDevice() {
		if (serviceRegistrations == null)
			return;
		for (ServiceRegistration reg : serviceRegistrations) {
			reg.unregister();
		}
		serviceRegistrations.clear();
	}

}
