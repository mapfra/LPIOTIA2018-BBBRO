/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.om2m.sdt.types.SimpleType;
import org.eclipse.om2m.sdt.utils.Logger;

public class Device extends Element {
	
	private Map<String, Module> modules;
	
	private Map<String, Property> properties;
	
	private Map<String, SubDevice> devices;

	private String definition;

	public Device(final String id, final Domain domain, final String definition) {
		super(definition + "__" + id);
		if (domain.getDevice(getName()) != null)
			throw new IllegalArgumentException("Already a device with name " 
					+ getName() + " in domain " + domain);
		this.definition = definition;
		modules = new HashMap<String, Module>();
		properties = new HashMap<String, Property>();
		devices = new HashMap<String, SubDevice>();
		domain.addDevice(this);
	}

	public String getId() {
		return getName();
	}

	public String getPid() {
		return getName().replaceAll("\\.", "_");
	}
	
	public String getDefinition() {
		return definition;
	}

	public Collection<String> getModuleNames() {
		return modules.keySet();
	}

	public Collection<Module> getModules() {
		return modules.values();
	}

	public Module getModule(final String name) {
		return modules.get(name);
	}

	public void addModule(Module module) {
		if (modules.get(module.getName()) != null)
			throw new IllegalArgumentException("Already a module with name " + module.getName() 
					+ " on device " + getName());
		Logger.info("SUPER add module " + module, getClass());
		module.setOwner(this);
		this.modules.put(module.getName(), module);
	}

	public void removeModule(final String name) {
		this.modules.remove(name);
	}

	public Collection<String> getPropertyNames() {
		return properties.keySet();
	}

	public Collection<Property> getProperties() {
		return properties.values();
	}

	public Property getProperty(final String name) {
		return properties.get(name);
	}

	public void addProperty(Property property) {
		this.properties.put(property.getName(), property);
	}

	public void setProperty(String name, String shortName, String value) {
		setProperty(name, shortName, value, null);
	}

	public void setProperty(String name, String shortName, String value, String type) {
		Property prop = getProperty(name);
		if (prop == null) {
			prop = new Property(name, shortName);
			if (type != null)
				prop.setType(SimpleType.getSimpleType(type));
		}
		prop.setValue(value);
	}

	public void removeProperty(final String name) {
		this.properties.remove(name);
	}

	public Collection<String> getSubDeviceNames() {
		return devices.keySet();
	}

	public Collection<SubDevice> getSubDevices() {
		return devices.values();
	}

	public SubDevice getSubDevice(final String name) {
		return devices.get(name);
	}
	
	public void addSubDevice(SubDevice device) {
		if (devices.get(device.getName()) != null)
			throw new IllegalArgumentException("Already a subdevice with name " + device.getName() 
					+ " on device " + getName());
		this.devices.put(device.getName(), device);
	}

	public void removeSubDevice(final String name) {
		this.devices.remove(name);
	}

	@Override
	protected String prettyPrint(String t1) {
		String t2 = t1 + "\t";
		StringBuffer ret = new StringBuffer(t1).append("<Device id=\"")
				.append(getId()).append("\">");
		if (getDoc() != null) ret.append("\n").append(t2).append(getDoc());
		prettyPrint(ret, properties.values(), "Properties", t2);
		prettyPrint(ret, modules.values(), "Modules", t2);
		prettyPrint(ret, devices.values(), "SubDevices", t2);
		return ret.append("\n").append(t1).append("</Device>").toString();
	}
	
    protected void finalize() throws Throwable {
    	Logger.info("finalize " + this);
    	modules.clear();
    	devices.clear();
    	properties.clear();
    }
	
}
