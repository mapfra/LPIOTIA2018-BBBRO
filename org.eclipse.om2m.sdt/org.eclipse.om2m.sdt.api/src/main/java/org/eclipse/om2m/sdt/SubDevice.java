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

public class SubDevice extends Element {
	
	private Map<String, Module> modules;
	
	private Map<String, Property> properties;

	public SubDevice(final String id) {
		super(id);
		modules = new HashMap<String, Module>();
		properties = new HashMap<String, Property>();
	}

	public String getId() {
		return getName();
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

	public void addModule(final Module module) {
		if (modules.get(module.getName()) != null)
			throw new IllegalArgumentException();
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

	public void addProperty(final Property property) {
		this.properties.put(property.getName(), property);
	}

	public void removeProperty(final String name) {
		this.properties.remove(name);
	}

	@Override
	protected String prettyPrint(String t1) {
		String t2 = t1 + "\t";
		StringBuffer ret = new StringBuffer(t1).append("<SubDevice id=\"")
				.append(getId()).append("\">");
		if (getDoc() != null) ret.append("\n").append(t2).append(getDoc());
		prettyPrint(ret, properties.values(), "Properties", t2);
		prettyPrint(ret, modules.values(), "Modules", t2);
		return ret.append("\n").append(t1).append("</SubDevice>").toString();
	}

}
