/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.mocked.devices;

import java.util.List;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Event;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.SwitchButton;
import org.eclipse.om2m.sdt.home.modules.PushButton;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.osgi.framework.ServiceRegistration;

@SuppressWarnings("rawtypes")
public class MockedSwitchButton extends SwitchButton implements MockedDevice {

	private List<ServiceRegistration> serviceRegistrations;
	private boolean running;
	private PushButton pushButton;
	private boolean pushed;

	public MockedSwitchButton(String id, String serial, Domain domain) {
		super(id, serial, domain);

		pushButton = new PushButton("pushButton_" + id, domain, 
			new BooleanDataPoint(DatapointType.pushed) {
				@Override
				public Boolean doGetValue() throws DataPointException {
					return pushed;
				}
			}
		);
		addModule(pushButton);
	}

	public void registerDevice() {
		if (! ((serviceRegistrations == null) || serviceRegistrations.isEmpty())) {
			return;
		}
		serviceRegistrations = Activator.register(this);
		running = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("start thread " + pushButton);
				while (running) {
					try {
						Thread.sleep((int) (30000 + 5000 * Math.random()));
						System.out.println("Generating Push Button event");
						Event evt = new Event("PUSH");
						pushed = ! pushed;
						evt.setValue(pushed);
						evt.addDataPoint(pushButton.getDataPointByShortName(
								DatapointType.pushed.getShortName()));
						pushButton.addEvent(evt);
					} catch (Throwable e) {
						System.out.println("Error generating event");
						e.printStackTrace();
						running = false;
					}
				}
				System.out.println("stopped thread");
			}
		}).start();
	}

	public void unregisterDevice() {
		running = false;
		if (serviceRegistrations == null)
			return;
		for (ServiceRegistration reg : serviceRegistrations) {
			reg.unregister();
		}
		serviceRegistrations.clear();
	}

}
