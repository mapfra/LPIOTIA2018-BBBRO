/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.netatmo.model;

import java.util.ArrayList;
import java.util.List;

public class WeatherStationModule {
	
	public static final String ID = "_id";
	public static final String NAME = "module_name";
	public static final String DASHBOARD_DATA = "dashboard_data";
	
	public static final String ABSOLUTE_PRESSURE = "AbsolutePressure";
	public static final String NOISE = "Noise";
	public static final String TEMPERATURE = "Temperature";
	public static final String HUMIDITY = "Humidity";
	public static final String PRESSURE = "Pressure";
	public static final String CO2 = "CO2";
	public static final String MIN_TEMPERATURE = "min_temp";
	public static final String MAX_TEMPERATURE = "max_temp";
	public static final Object TEMPERATURE_TREND = "temp_trend";
	public static final String DATE = "time_utc";
	
	public static final String DATA_TYPE = "data_type";
	public static final String TEMPERATURE_DATA_TYPE = "Temperature";
	public static final String HUMIDITY_DATA_TYPE = "Humidity";
	public static final String CO2_DATA_TYPE = "CO2";
	public static final String NOISE_DATA_TYPE = "Noise";
	public static final String PRESSURE_DATA_TYPE = "Pressure";
	
	private final String id;
	private final String name;
	private double currentTemperature;
	private String temperatureTrend;
	private double minTemperature;
	private double maxTemperature;
	private long humidity;
	private long co2;
	private long noise;
	private double pressure;
	private double absolutePressure;
	
	private List<String> dataTypes;
	
	private long date;
	
	public WeatherStationModule(final String pId, final String pName) {
		this.id = pId;
		this.name = pName;
		this.dataTypes = new ArrayList<>();
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public double getCurrentTemperature() {
		return currentTemperature;
	}

	public void setCurrentTemperature(double currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	public String getTemperatureTrend() {
		return temperatureTrend;
	}

	public void setTemperatureTrend(String temperatureTrend) {
		this.temperatureTrend = temperatureTrend;
	}

	public double getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(double minTemperature) {
		this.minTemperature = minTemperature;
	}

	public double getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}
	
	public long getHumidity() {
		return humidity;
	}

	public void setHumidity(long humidity) {
		this.humidity = humidity;
	}
	
	public long getCo2() {
		return co2;
	}

	public void setCo2(long co2) {
		this.co2 = co2;
	}

	public long getNoise() {
		return noise;
	}

	public void setNoise(long noise) {
		this.noise = noise;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public double getAbsolutePressure() {
		return absolutePressure;
	}

	public void setAbsolutePressure(double absolutePressure) {
		this.absolutePressure = absolutePressure;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}
	
	public List<String> getDataTypes() {
		return dataTypes;
	}
	
	public void addDataType(String newDataType) {
		dataTypes.add(newDataType);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("WeatherStationModule(id=").append(getId())
			.append(", name=").append(getName())
			.append(", dataType=[");
		for(String dataType : dataTypes) {
			sb.append(dataType).append(",");
		}
		sb.append("], absolutePressure=").append(absolutePressure)
			.append(", pressure=").append(pressure)
			.append(", noise=").append(noise)
			.append(", currentTemperature=").append(getCurrentTemperature())
			.append(", temperatureTrend=").append(getTemperatureTrend())
			.append(", minTemperature=").append(getMinTemperature())
			.append(", maxTemperature=").append(getMaxTemperature())
			.append(", humidity=").append(getHumidity())
			.append(", co2=").append(co2).append(", date=").append(date).append(")");
		return sb.toString();
	}
	
	public void updateData(WeatherStationModule module) {
		this.currentTemperature = module.getCurrentTemperature();
		this.temperatureTrend = module.getTemperatureTrend();
		this.minTemperature = module.getMinTemperature();
		this.maxTemperature = module.getMaxTemperature();
		this.absolutePressure = module.getAbsolutePressure();
		this.pressure = module.getPressure();
		this.noise = module.getNoise();
		this.co2 = module.getCo2();
		this.humidity = module.getHumidity();
		this.date = module.getDate();
	}

}
