/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.netatmo.model;

import java.util.HashMap;
import java.util.Map;

public class WeatherStation extends WeatherStationModule {

	public static final String MODULES = "modules";

	private final Map<String/* id */, WeatherStationModule> modules;

	public WeatherStation(final String pId, final String pName) {
		super(pId, pName);
		modules = new HashMap<>();
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
		StringBuffer sb = new StringBuffer();
		sb.append("WeatherStation(mainModule=").append(super.toString()).append(",modules=[");
		for(WeatherStationModule module : getModules().values()) {
			sb.append(module.toString()).append(",");
		}
		sb.append("])");
		return sb.toString();
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
