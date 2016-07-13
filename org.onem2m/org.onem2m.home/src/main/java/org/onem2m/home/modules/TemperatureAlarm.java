package org.onem2m.home.modules;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class TemperatureAlarm extends AbstractAlarmSensor {
	
	private IntegerDataPoint temperature;
	private IntegerDataPoint temperatureThreshhold;
	
	public TemperatureAlarm(final String name, final Domain domain, BooleanDataPoint alarm) {
		super(name, domain, alarm, ModuleType.temperatureAlarm,
				"The detection of abnormal temperature.");
	}

	public void setTemperature(IntegerDataPoint dp) {
		this.temperature = dp;
		temperature.setOptional(true);
		temperature.setWritable(false);
		temperature.setDoc("To report the value of the temperature; the common unit is ºC");
		addDataPoint(temperature);
	}
	
	public int getTemperature() throws DataPointException, AccessException {
		if (temperature == null)
			throw new DataPointException("Not implemented");
		return temperature.getValue();
	}

	public void setTemperatureThreshhold(IntegerDataPoint dp) {
		this.temperatureThreshhold = dp;
		temperatureThreshhold.setOptional(true);
		temperatureThreshhold.setDoc("The threshhold to trigger the alarm.");
		addDataPoint(temperatureThreshhold);
	}
	
	public int getTemperatureThreshhold() throws DataPointException, AccessException {
		if (temperatureThreshhold == null)
			throw new DataPointException("Not implemented");
		return temperatureThreshhold.getValue();
	}
	
	public void setTemperatureThreshhold(int v) throws DataPointException, AccessException {
		if (temperatureThreshhold == null)
			throw new DataPointException("Not implemented");
		temperatureThreshhold.setValue(v);
	}

}
