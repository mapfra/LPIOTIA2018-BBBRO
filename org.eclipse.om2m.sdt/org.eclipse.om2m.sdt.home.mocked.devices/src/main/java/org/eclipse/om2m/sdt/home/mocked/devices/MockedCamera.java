/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.mocked.devices;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.Camera;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedPlayerControl;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedSessionDescription;
import org.eclipse.om2m.sdt.home.modules.Credentials;
import org.eclipse.om2m.sdt.home.modules.PersonSensor;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.osgi.framework.ServiceRegistration;

@SuppressWarnings("rawtypes")
public class MockedCamera extends Camera implements MockedDevice {

	private List<ServiceRegistration> serviceRegistrations;
	
	private static String login = "toto";
	private static String password = "mdp";

	public MockedCamera(String id, String serial, Domain domain) {
		super(id, serial, domain);

		// Module FaultDetection
		setSessionDescription(new MockedSessionDescription("sdp_" + id, domain));

		setPersonSensor(new PersonSensor("personSensor_" + id, domain, 
			new ArrayDataPoint<String>(DatapointType.detectedPersons) {
				@Override
				public List<String> doGetValue() throws DataPointException {
					return Arrays.asList("admin", "Phil");
				}
			}));
		
		setPlayerControl(new MockedPlayerControl("playerCtrl_" + id, domain));
		
		Credentials cred = new Credentials("cred_" + id, domain);
		cred.setLoginName(new StringDataPoint(DatapointType.loginName) {
			@Override
			public String getValue() throws DataPointException, AccessException {
				System.out.println("getValue loginName: " + doGetValue());
				return "Not readable";
			}
			@Override
			protected String doGetValue() throws DataPointException {
				return login;
			}
			@Override
			protected void doSetValue(String s) throws DataPointException {
				System.out.println("doSetValue loginName: " + s);
				login = s;
			}
		});
		cred.setPassword(new StringDataPoint(DatapointType.password) {
			@Override
			public String getValue() throws DataPointException, AccessException {
				System.out.println("getValue password: " + doGetValue());
				return "Not readable";
			}
			@Override
			protected String doGetValue() throws DataPointException {
				return password;
			}
			@Override
			protected void doSetValue(String s) throws DataPointException {
				System.out.println("doSetValue password: " + s);
				password = s;
			}
		});
		setCredentials(cred);
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
