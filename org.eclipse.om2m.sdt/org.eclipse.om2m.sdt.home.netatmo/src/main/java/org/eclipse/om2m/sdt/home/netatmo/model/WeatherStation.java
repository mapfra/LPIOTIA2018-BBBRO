/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.netatmo.model;

import java.util.HashMap;
import java.util.Map;

public class WeatherStation extends WeatherStationModule {

	public static final String MODULES = "modules";

	private final Map<String/* id */, WeatherStationModule> modules;

	public WeatherStation(final String id, final String name) {
		super(id, name);
		modules = new HashMap<String, WeatherStationModule>();
	}

	public void addOrUpdateModule(WeatherStationModule module) {
		synchronized (modules) {
			WeatherStationModule existingModule = modules.get(module.getId());
			if (existingModule != null) {
				// update
				existingModule.updateData(module);
			} else {
				modules.put(module.getId(), module);
			}
		}
	}
	
	public Map<String, WeatherStationModule> getModules() {
		Map<String, WeatherStationModule> toBeReturned = new HashMap<>();
		synchronized (modules) {
			toBeReturned.putAll(modules);
		}
		return toBeReturned;
	}

	@Override
	public String toString() {
		String ret = "WeatherStation(mainModule=" + super.toString()
			+ ", modules=[\"";
		boolean first = true;
		for (WeatherStationModule module : getModules().values()) {
			if (first) first = false;
			else ret += ", ";
			ret += module;
		}
		return ret + "])";
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof WeatherStation)) {
			return false;
		}
		WeatherStation other = (WeatherStation) obj;
		return other.getId().equals(getId());
	}

}
