/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.netatmo.sdt;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.onem2m.home.devices.WeatherStation;
import org.onem2m.home.driver.Utils;
import org.onem2m.home.modules.AtmosphericPressureSensor;
import org.onem2m.home.modules.ExtendedCarbonDioxideSensor;
import org.onem2m.home.modules.Noise;
import org.onem2m.home.modules.RelativeHumidity;
import org.onem2m.home.modules.Temperature;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.datapoints.FloatDataPoint;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.datapoints.StringDataPoint;
import org.onem2m.sdt.impl.DataPointException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.orange.basedriver.netatmo.impl.Activator;
import com.orange.basedriver.netatmo.model.WeatherStationModule;

public class SDTWeatherStation extends WeatherStation {

	private List<ServiceRegistration> serviceRegistrations;

	private final WeatherStationModule stationOrModule;

	public SDTWeatherStation(WeatherStationModule pStationOrModule) {
		super(computeId(pStationOrModule.getName()),
				pStationOrModule.getId(), Activator.NETATMO_DOMAIN);
		this.stationOrModule = pStationOrModule;
		
		setDeviceManufacturer("Netatmo");
		setDeviceModelName("WeatherStation");
		setDeviceName(pStationOrModule.getName());
		setProtocol(Activator.PROTOCOL);
		try {
			setPresentationURL(new URL("https://www.netatmo.com/product/weather/weatherstation"));
		} catch (MalformedURLException ignored) {
		}

		if (stationOrModule.getDataTypes().contains(WeatherStationModule.TEMPERATURE_DATA_TYPE)) {
			// temperature
			setTemperature(new Temperature("temperature_" + getId(), Activator.NETATMO_DOMAIN,
				new FloatDataPoint("currentTemperature") {
					@Override
					protected Float doGetValue() throws DataPointException {
						return new Double(stationOrModule.getCurrentTemperature()).floatValue();
					}
				}));
			getTemperature().setMinValue(new FloatDataPoint("minValue") {
				@Override
				protected Float doGetValue() throws DataPointException {
					return new Double(stationOrModule.getMinTemperature()).floatValue();
				}
			});
			getTemperature().setMaxValue(new FloatDataPoint("maxValue") {
				@Override
				protected Float doGetValue() throws DataPointException {
					return new Double(stationOrModule.getMaxTemperature()).floatValue();
				}
			});
			getTemperature().setUnits(new StringDataPoint("units") {
				@Override
				protected String doGetValue() throws DataPointException {
					return "°C";
				}
			});
		}

		if (stationOrModule.getDataTypes().contains(WeatherStationModule.HUMIDITY_DATA_TYPE)) {
			// humidity
			setRelativeHumidity(new RelativeHumidity("relativeHumidity_" + getId(), Activator.NETATMO_DOMAIN,
				new FloatDataPoint("relativeHumidity") {
					@Override
					protected Float doGetValue() throws DataPointException {
						return (float) stationOrModule.getHumidity();
					}
				}));
		}

		if (stationOrModule.getDataTypes().contains(WeatherStationModule.NOISE_DATA_TYPE)) {
			// noise
			setNoise(new Noise("noise_" + getId(), Activator.NETATMO_DOMAIN, 
				new IntegerDataPoint("noise") {
					@Override
					protected Integer doGetValue() throws DataPointException {
						return new Long(stationOrModule.getNoise()).intValue();
					}
				}));
		}

		if (stationOrModule.getDataTypes().contains(WeatherStationModule.PRESSURE_DATA_TYPE)) {
			// pressure
			setAtmosphericPressureSensor(new AtmosphericPressureSensor("atmosphericPressureSensor_" + getId(), 
					Activator.NETATMO_DOMAIN, 
				new FloatDataPoint("atmosphericPressure") {
					@Override
					protected Float doGetValue() throws DataPointException {
						return new Double(stationOrModule.getAbsolutePressure()).floatValue();
					}
				}));
		}

		if (stationOrModule.getDataTypes().contains(WeatherStationModule.CO2_DATA_TYPE)) {
			// co2
			setExtendedCarbonDioxideSensor(new ExtendedCarbonDioxideSensor("extendedCarbonDioxideSensor_" + getId(),
					Activator.NETATMO_DOMAIN, 
				new BooleanDataPoint("alarm") {
					@Override
					protected Boolean doGetValue() throws DataPointException {
						return stationOrModule.getCo2() >= 600;
					}
			}, 
			new IntegerDataPoint("carbonDioxideValue") {
				@Override
				protected Integer doGetValue() throws DataPointException {
					return new Long(stationOrModule.getCo2()).intValue();
				}
			}));
		}
	}

	public void register(BundleContext bundleContext) {
		serviceRegistrations = Utils.register(this, bundleContext);
	}

	public void unregister() {
		for (ServiceRegistration sr : serviceRegistrations) {
			sr.unregister();
		}
		
		// remove all modules & device object from domain
		for(String moduleName : getModuleNames()) {
			Activator.NETATMO_DOMAIN.removeModule(moduleName);
		}
		Activator.NETATMO_DOMAIN.removeDevice(getName());
	}

	private static String computeId(String pId) {
		return pId.replaceAll("[^a-zA-Z0-9]+", "_").trim();
	}

}
