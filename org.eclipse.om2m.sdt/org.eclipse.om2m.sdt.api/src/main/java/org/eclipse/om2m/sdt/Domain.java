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

import org.eclipse.om2m.sdt.utils.Activator;

public class Domain extends Element {
	
	private Map<String, Domain> imports;
	
	private Map<String, ModuleClass> modules;
	
	private Map<String, Device> devices;

	public Domain(final String id) {
		super(id);
		imports = new HashMap<String, Domain>();
		modules = new HashMap<String, ModuleClass>();
		devices = new HashMap<String, Device>();
		
		Activator.addDomain(this);
	}

	public String getId() {
		return getName();
	}

	public Collection<String> getModuleNames() {
		return modules.keySet();
	}

	public Collection<ModuleClass> getModules() {
		return modules.values();
	}

	public ModuleClass getModule(final String name) {
		return modules.get(name);
	}

	public void addModule(final ModuleClass module) {
		if (modules.get(module.getName()) != null)
			throw new IllegalArgumentException();
		this.modules.put(module.getName(), module);
	}

	public void removeModule(final String name) {
		this.modules.remove(name);
	}

	public Collection<String> getDeviceNames() {
		return devices.keySet();
	}

	public Collection<Device> getDevices() {
		return devices.values();
	}

	public Device getDevice(final String name) {
		return devices.get(name);
	}

	public void addDevice(final Device device) {
		if (devices.get(device.getName()) != null)
			throw new IllegalArgumentException();
		this.devices.put(device.getName(), device);
	}

	public void removeDevice(final String name) {
		Device dev = this.devices.remove(name);
		if (dev != null) {
			for (Module mod : dev.getModules()) {
				removeModule(mod.getName());
			}
		}
	}

	public Collection<String> getImportNames() {
		return imports.keySet();
	}

	public Collection<Domain> getImports() {
		return imports.values();
	}

	public Domain getImport(final String name) {
		return imports.get(name);
	}

	public void addImport(final Domain domain) {
		if (imports.get(domain.getName()) != null)
			throw new IllegalArgumentException();
		this.imports.put(domain.getName(), domain);
	}

	public void removeImport(final String name) {
		this.imports.remove(name);
	}

	@Override
	protected String prettyPrint(String t1) {
		String t2 = t1 + "\t";
		StringBuffer ret = new StringBuffer("<Domain xmlns:xi=\"http://www.w3.org/2001/XInclude\""
				+ " xmlns=\"http://homegatewayinitiative.org/xml/dal/3.0\"" 
				+ " id=\"" + getId() + "\">");
		if (getDoc() != null) ret.append("\n").append(t2).append(getDoc());
		if (! imports.isEmpty()) {
			ret.append("<Imports>");
			for (Domain c : imports.values()) {
				ret.append("<xi:include href=\"" + c.getId() + ".xml\" parse=\"xml\"/>");
			}
			ret.append("</Imports>");
		}
		prettyPrint(ret, modules.values(), "Modules", t2);
		prettyPrint(ret, devices.values(), "Devices", t2);
		return ret.append("\n").append(t1).append("</Domain>").toString();
	}

}
