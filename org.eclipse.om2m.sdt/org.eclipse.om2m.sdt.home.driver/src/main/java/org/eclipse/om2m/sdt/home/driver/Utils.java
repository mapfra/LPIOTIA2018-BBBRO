/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.driver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.om2m.sdt.Device;
import org.eclipse.om2m.sdt.Element;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.Property;
import org.eclipse.om2m.sdt.home.devices.GenericDevice;
import org.eclipse.om2m.sdt.home.modules.GenericSensor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedService;

@SuppressWarnings({ "rawtypes", "unchecked" })
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

	static private ConfigurationAdmin cfgAdmin;

	static public List<ServiceRegistration> register(GenericDevice device, 
			BundleContext context) {
		String protocol = device.getProtocol();
		if (protocol == null) {
			protocol = "Unknown";
		}
		Logger log = new Logger(protocol);
		if (isEmpty(device.getDeviceAliasName())) {
			device.setDeviceAliasName(device.getDeviceType().getLongName().substring(6)
					+ " #" + device.getSerialNumber().substring(device.getSerialNumber().length() - 5));
		}
		List<ServiceRegistration> regs = new ArrayList<ServiceRegistration>();
		regs.add(context.registerService(getSDTNames(device),
				device, getProperties(device, protocol, log)));
		for (Module module : device.getModules()) {
			regs.add(context.registerService(getSDTNames(module),
					module, getProperties(module, protocol, log)));
		}
		if (getConfigurationAdmin(context) != null) {
			try {
				PersistedDevice pDev = new PersistedDevice(device);
				Configuration cfg = cfgAdmin.getConfiguration(device.getPid());
				Dictionary props = cfg.getProperties();
				if (props != null) {
					log.info("Already persisted device: " + props);
					pDev.updateDevice(props);
				} else {
					log.info("Unpersisted device: " + device);
					props = new Hashtable();
					for (Property prop : device.getProperties()) {
						if (prop.getValue() != null)
							props.put(prop.getName(), prop.getValue());
					}
					props.put(SERVICE_PID, "P_" + device.getPid());
					log.info("persist: " + props);
					pDev.setRegistration(context.registerService(ManagedService.class.getName(),
							pDev, props));
					cfg.update(props);
				}
			} catch(Exception e) {
				log.warning("", e);
			}
		}
		return regs;
	}
	
	static public void setProperties(ServiceReference ref, GenericDevice device) {
		String name = null;
		String manuf = null;
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
				device.setDeviceAliasName(val.toString());
			else if (prop.equals(Utils.DEVICE_MANUFACTURER))
				manuf = val.toString();
			else if (prop.equals(Utils.DEVICE_PRODUCT_CLASS))
				device.setDeviceModelName(val.toString());
			else if (prop.equals(Utils.DEVICE_FRIENDLY_NAME))
				name = val.toString();
//			else device.setProperty(prop, Utils.DEVICE_FRIENDLY_NAME, val.toString());
		}
		device.setDeviceManufacturer((manuf != null) ? manuf : "Unknown");
		device.setDeviceName((name != null) ? name : device.getSerialNumber());
	}
	
	static public final boolean equals(final String s1, final String s2) {
		return (s1 == null) ? (s2 == null) : s1.equals(s2);
	}
	
	static private final boolean isEmpty(final String s1) {
		return (s1 == null) || s1.isEmpty();
	}
	
	static private final String[] getSDTNames(final Device elt) {
		Class<?> clazz = elt.getClass();
		while (! clazz.getPackage().getName().equals(HOME_DEVS_PKG)) {
			clazz = clazz.getSuperclass();
			if (clazz == null) 
				return new String[] { Device.class.getName() };
		}
		return new String[] { clazz.getName(), Device.class.getName() };
	}
	
	static private final String[] getSDTNames(final Module elt) {
		Class<?> clazz = elt.getClass();
		while (! clazz.getPackage().getName().equals(HOME_MODS_PKG)) {
			clazz = clazz.getSuperclass();
			if (clazz == null) 
				return new String[] { Module.class.getName() };
		}
		return new String[] { clazz.getName(), Module.class.getName() };
	}

	static private Dictionary<String, Object> getProperties(Element elt, String protocol, Logger log) {
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		List<String> categories = new ArrayList<String>();
		categories.add(SDT);
		categories.add((elt instanceof Device) ? DEVICE : MODULE);
		if ((protocol != null) && ! protocol.isEmpty())
			categories.add(protocol);
		props.put(DEVICE_CATEGORY, categories.toArray(new String[] {}));
		Collection<Property> sdtProps = null;
		if (elt instanceof Device) {
			props.put(SDT_ID, elt.getName());
			props.put(SERVICE_PID, ((Device)elt).getPid());
			String desc = null;
			try {
				desc = ((GenericDevice)elt).getDeviceModelName();
				if (desc == null) desc = ((GenericDevice)elt).getDeviceAliasName();
				if (desc == null) desc = ((GenericDevice)elt).getDeviceName();
			} catch(Exception ignored) {}
			if (desc == null) desc = ((GenericDevice)elt).getSerialNumber();
			props.put(DEVICE_DESCRIPTION, desc);
			sdtProps = ((Device)elt).getProperties();
		} else if (elt instanceof Module) {
			props.put(SERVICE_PID, ((Module)elt).getPid());
			sdtProps = ((Module)elt).getProperties();
		}
		for (Property prop : sdtProps) {
			if (prop.getValue() != null)
				props.put(prop.getName(), prop.getValue());
		}
		log.info("Properties: " + props);
		return props;
	}

	static private final ConfigurationAdmin getConfigurationAdmin(final BundleContext bc) {
		if (cfgAdmin == null) {
			ServiceReference configAdminServiceReference = bc.getServiceReference(ConfigurationAdmin.class.getName());
			if (configAdminServiceReference != null) {
				cfgAdmin = (ConfigurationAdmin) bc.getService(configAdminServiceReference);
			}
		}
		return cfgAdmin;
	}
	
//	static private final Configuration getConfiguration(BundleContext bc , String pid) throws IOException {
//		Configuration config = null;
//		ConfigurationAdmin configAdmin = getConfigurationAdmin(bc);
//		if (configAdmin != null) {
//			config = configAdmin.getConfiguration(pid);
//		}
//		return config;
//	}

}
