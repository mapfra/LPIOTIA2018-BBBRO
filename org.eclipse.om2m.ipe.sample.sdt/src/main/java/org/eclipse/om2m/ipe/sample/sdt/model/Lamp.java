/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sample.sdt.model;

import java.util.List;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.Light;
import org.eclipse.om2m.sdt.home.driver.Utils;
import org.eclipse.om2m.sdt.home.modules.ColourSaturation;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Lamp extends Light {

	private List<ServiceRegistration> serviceRegistrations;

	public Lamp(String id, String serial, Domain domain, BundleContext bundleContext) {
		super(id, serial, domain);

		// Module BinarySwitch
		addModule(new SampleBinarySwitch("binarySwitch_" + id, domain));

		// Module Colour
		addModule(new SampleColour("colour_" + id, domain));

		addModule(new ColourSaturation("colourSaturation_" + id, domain, 
			new IntegerDataPoint(DatapointType.colourSat) {
				private Integer v = new Integer((int)(Math.random() * 100));
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					v = value;
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					return v;
				}
			})
		);
		setDeviceModelName("Fake lamp as SDT Light");
		setDeviceName("SDT Light");
		setLocation("Kitchen");
		setDeviceManufacturer("OM2M");
		if (! ((serviceRegistrations == null) || serviceRegistrations.isEmpty())) {
			return;
		}
		serviceRegistrations = Utils.register(this, bundleContext);
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
