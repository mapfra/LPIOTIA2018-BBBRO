/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.devices;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.home.modules.AtmosphericPressureSensor;
import org.eclipse.om2m.sdt.home.modules.ExtendedCarbonDioxideSensor;
import org.eclipse.om2m.sdt.home.modules.Noise;
import org.eclipse.om2m.sdt.home.modules.RelativeHumidity;
import org.eclipse.om2m.sdt.home.modules.Temperature;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class WeatherStation extends GenericDevice {

	private Temperature temperature;
	private RelativeHumidity relativeHumidity;
	private AtmosphericPressureSensor atmosphericPressureSensor;
	private Noise noise;
	private ExtendedCarbonDioxideSensor extendedCarbonDioxideSensor;

	public WeatherStation(String id, String serial, Domain domain) {
		super(id, serial, DeviceType.deviceWeatherStation, domain);
	}
	
	public void addModule(Module module) {
		if (module instanceof Temperature)
			setTemperature((Temperature)module);
		else if (module instanceof RelativeHumidity)
			setRelativeHumidity((RelativeHumidity)module);
		else if (module instanceof AtmosphericPressureSensor)
			setAtmosphericPressureSensor((AtmosphericPressureSensor)module);
		else if (module instanceof Noise)
			setNoise((Noise)module);
		else if (module instanceof ExtendedCarbonDioxideSensor)
			setExtendedCarbonDioxideSensor((ExtendedCarbonDioxideSensor)module);
		else 
			super.addModule(module);
	}

	// temperature - optional
	public void setTemperature(Temperature pTemperature) {
		this.temperature = pTemperature;
		super.addModule(temperature);
	}

	public void unsetTemperature(Temperature pTemperature) {
		if (temperature != null) {
			removeModule(temperature.getName());
			this.temperature = null;
		}
	}

	public Temperature getTemperature() {
		return temperature;
	}

	// relative humidity - optional
	public void setRelativeHumidity(RelativeHumidity pRelativeHumidity) {
		this.relativeHumidity = pRelativeHumidity;
		super.addModule(relativeHumidity);
	}

	public void unsetRelativeHumidity(RelativeHumidity pRelativeHumidity) {
		if (relativeHumidity != null) {
			removeModule(relativeHumidity.getName());
			this.relativeHumidity = null;
		}
	}

	public RelativeHumidity getRelativeHumidity() {
		return relativeHumidity;
	}

	// atmosphericPressureSensor - optional
	public void setAtmosphericPressureSensor(AtmosphericPressureSensor pAtmosphericPressureSensor) {
		this.atmosphericPressureSensor = pAtmosphericPressureSensor;
		super.addModule(atmosphericPressureSensor);
	}

	public void unsetAtmosphericPressureSensor(AtmosphericPressureSensor pAtmosphericPressureSensor) {
		if (atmosphericPressureSensor != null) {
			removeModule(atmosphericPressureSensor.getName());
			this.atmosphericPressureSensor = null;
		}
	}

	public AtmosphericPressureSensor getAtmosphericPressureSensor() {
		return atmosphericPressureSensor;
	}

	// noise - optional
	public void setNoise(Noise pNoise) {
		this.noise = pNoise;
		super.addModule(noise);
	}

	public void unsetNoise(Noise noise) {
		if (noise != null) {
			removeModule(noise.getName());
			this.noise = null;
		}
	}
	
	public Noise getNoise() {
		return noise;
	}
	
	// extendedCarbonDioxide - optional
	public void setExtendedCarbonDioxideSensor(ExtendedCarbonDioxideSensor pExtendedCarbonDioxideSensor) {
		this.extendedCarbonDioxideSensor = pExtendedCarbonDioxideSensor;
		super.addModule(extendedCarbonDioxideSensor);
	}
	
	public void unsetExtendCarbonDioxideSensor(ExtendedCarbonDioxideSensor pExtendedCarbonDioxideSensor) {
		if (extendedCarbonDioxideSensor != null) {
			removeModule(extendedCarbonDioxideSensor.getName());
			extendedCarbonDioxideSensor = null;
		}
	}
	
	public ExtendedCarbonDioxideSensor getExtendedCarbonDioxideSensor() {
		return extendedCarbonDioxideSensor;
	}
	
}
