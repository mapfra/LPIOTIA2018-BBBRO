/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.driver;

import java.util.Dictionary;
import java.util.Enumeration;

import org.eclipse.om2m.sdt.Property;
import org.eclipse.om2m.sdt.home.devices.GenericDevice;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PersistedDevice implements ManagedService {

	private GenericDevice device;
	private ServiceRegistration<?> registration;
	private Logger logger;
	
	PersistedDevice(GenericDevice device) {
		this.device = device;
		String protocol = device.getProtocol();
		logger = new Logger((protocol == null) ? "Driver" : protocol);
	}
	
	void setRegistration(ServiceRegistration<?> registration) {
		this.registration = registration;
	}

	@Override
	public void updated(Dictionary props) throws ConfigurationException {
		if ((props != null) && updateDevice(props)) {
			try {
				registration.setProperties(props);
			} catch (Exception e) {
				throw new ConfigurationException(null, null, e);
			}
		}
	}
	
	public boolean updateDevice(Dictionary<?,?> props) {
		boolean modified = false;
		for (Enumeration keys = props.keys(); keys.hasMoreElements(); ) {
			String key = (String)keys.nextElement();
			String val = (String)props.get(key);
			Property old = device.getProperty(key, false);
			if (old == null) {
				// Not a valid property: ignore (cannot add dynamically new properties)
				logger.info("Unknown property (not SDT): " + key + "/" + val);
			} else if (Utils.equals((String)old.getValue(), val)) {
				// No change, ignore
				logger.info("Unchanged property: " + key + "/" + val);
			} else {
				old.setValue(val);
				modified = true;
				logger.info("Set persisted property: " + key + "/" + val);
			}
		}
		return modified;
	}

}
