/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.driver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.onem2m.home.devices.GenericDevice;
import org.onem2m.home.modules.GenericSensor;
import org.onem2m.sdt.Device;
import org.onem2m.sdt.Element;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.Property;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class Utils {

	static public final String SERVICE_PID = org.osgi.framework.Constants.SERVICE_PID;
	static public final String SERVICE_ID = org.osgi.framework.Constants.SERVICE_ID;
	static public final String OBJECTCLASS = org.osgi.framework.Constants.OBJECTCLASS;
	static public final String DEVICE_DESCRIPTION = org.osgi.service.device.Constants.DEVICE_DESCRIPTION;
	static public final String DEVICE_SERIAL = org.osgi.service.device.Constants.DEVICE_SERIAL;
	static public final String DEVICE_CATEGORY = org.osgi.service.device.Constants.DEVICE_CATEGORY;
	static public final String DEVICE_MANUFACTURER = "DEVICE_MANUFACTURER";
	static public final String DEVICE_PRODUCT_CLASS = "DEVICE_PRODUCT_CLASS";
	static public final String DEVICE_FRIENDLY_NAME = "DEVICE_FRIENDLY_NAME";

	static private final String HOME_DEVS_PKG = GenericDevice.class.getPackage().getName();
	static private final String HOME_MODS_PKG = GenericSensor.class.getPackage().getName();

	static private final String SDT = "SDT";
	static private final String SDT_ID = "SDT_ID";
	static private final String DEVICE = "Device";
	static private final String MODULE = "Module";

	static public List<ServiceRegistration> register(GenericDevice device, 
			BundleContext context) {
		List<ServiceRegistration> regs = new ArrayList<ServiceRegistration>();
		regs.add(context.registerService(getSDTNames(device),
				device, getProperties(device, device.getProtocol())));
		for (Module mod : device.getModules()) {
			regs.add(context.registerService(getSDTNames(mod),
					mod, getProperties(mod, device.getProtocol())));
		}
		return regs;
	}
	
	static public void setProperties(ServiceReference ref, GenericDevice gen) {
		for (String prop : ref.getPropertyKeys()) {
			if (prop.equalsIgnoreCase(Utils.SERVICE_PID)
					|| prop.equalsIgnoreCase(Utils.SERVICE_ID)
					|| prop.equalsIgnoreCase(Utils.DEVICE_CATEGORY)
					|| prop.equalsIgnoreCase(Utils.OBJECTCLASS)) {
				continue;
			}
			Object val = ref.getProperty(prop);
			if ((val == null) || val.toString().isEmpty()) {
				continue;
			}
			if (prop.equals(Utils.DEVICE_DESCRIPTION))
				gen.setDeviceAliasName(val.toString());
			else if (prop.equals(Utils.DEVICE_MANUFACTURER))
				gen.setDeviceManufacturer(val.toString());
			else if (prop.equals(Utils.DEVICE_PRODUCT_CLASS))
				gen.setDeviceModelName(val.toString());
			else if (prop.equals(Utils.DEVICE_FRIENDLY_NAME))
				gen.setDeviceName(val.toString());
			else gen.addProperty(new Property(prop, val.toString()));
		}
		if (gen.getDeviceManufacturer() == null)
			gen.setDeviceManufacturer("Unknown");
		if (gen.getDeviceName() == null)
			gen.setDeviceModelName(gen.getSerialNumber());
	}
	
	static private final String[] getSDTNames(final Device elt) {
		Class<?> clazz = elt.getClass();
		while (! clazz.getPackage().getName().equals(HOME_DEVS_PKG)) {
			clazz = clazz.getSuperclass();
			if (clazz == null) return new String[] { Device.class.getName() };
		}
		return new String[] { clazz.getName(), Device.class.getName() };
	}
	
	static private final String[] getSDTNames(final Module elt) {
		Class<?> clazz = elt.getClass();
		while (! clazz.getPackage().getName().equals(HOME_MODS_PKG)) {
			clazz = clazz.getSuperclass();
			if (clazz == null) return new String[] { Module.class.getName() };
		}
		return new String[] { clazz.getName(), Module.class.getName() };
	}

	static private Dictionary<String, Object> getProperties(Element elt, String protocol) {
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		List<String> categories = new ArrayList<String>();
		categories.add(SDT);
		categories.add((elt instanceof Device) ? DEVICE : MODULE);
		if ((protocol != null) && ! protocol.isEmpty())
			categories.add(protocol);
		String[] cats = categories.toArray(new String[] {});
		Collection<Property> sdtProps = null;
		if (elt instanceof Device) {
			props.put(DEVICE_CATEGORY, cats);
			props.put(SDT_ID, elt.getName());
			props.put(SERVICE_PID, ((Device)elt).getPid());
			sdtProps = ((Device)elt).getProperties();
		} else if (elt instanceof Module) {
			props.put(DEVICE_CATEGORY, cats);
			props.put(SERVICE_PID, ((Module)elt).getPid());
			sdtProps = ((Module)elt).getProperties();
		} else {
			return props;
		}
		for (Property prop : sdtProps) {
			if (prop.getValue() != null)
				props.put(prop.getName(), prop.getValue());
		}
		return props;
	}

}
