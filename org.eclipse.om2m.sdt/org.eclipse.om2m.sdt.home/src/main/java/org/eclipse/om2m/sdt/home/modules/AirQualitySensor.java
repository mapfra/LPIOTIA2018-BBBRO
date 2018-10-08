/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : AirQualitySensor

ThThis ModuleClass provides capabilities for a monitoring sensor that measures the air quality.

Created: 2018-06-21 15:18:21
*/

package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class AirQualitySensor extends Module {
	
	private BooleanDataPoint monitoringEnabled;
	private IntegerDataPoint sensorPM1;
	private IntegerDataPoint sensorPM2;
	private IntegerDataPoint sensorPM10;
	private IntegerDataPoint sensorOdor;
	private IntegerDataPoint sensorHumidity;
	private IntegerDataPoint co2;
	private IntegerDataPoint co;
	private IntegerDataPoint ch2o;
	private IntegerDataPoint voc;

	public AirQualitySensor (final String name, final Domain domain) {
		super(name, domain, ModuleType.airQualitySensor);
	}

	public AirQualitySensor(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain);
		BooleanDataPoint bdp = (BooleanDataPoint) dps.get(DatapointType.monitoringEnabled.getShortName());
		if (bdp != null)
			setMonitoringEnabled(bdp);
		IntegerDataPoint dp = (IntegerDataPoint) dps.get(DatapointType.sensorPM1.getShortName());
		if (dp != null)
			setSensorPM1(dp);
		dp = (IntegerDataPoint) dps.get(DatapointType.sensorPM2.getShortName());
		if (dp != null)
			setSensorPM2(dp);
		dp = (IntegerDataPoint) dps.get(DatapointType.sensorPM10.getShortName());
		if (dp != null)
			setSensorPM10(dp);
		dp = (IntegerDataPoint) dps.get(DatapointType.sensorOdor.getShortName());
		if (dp != null)
			setSensorOdor(dp);
		dp = (IntegerDataPoint) dps.get(DatapointType.sensorHumidity.getShortName());
		if (dp != null)
			setSensorHumidity(dp);
		dp = (IntegerDataPoint) dps.get(DatapointType.co.getShortName());
		if (dp != null)
			setCo(dp);
		dp = (IntegerDataPoint) dps.get(DatapointType.co2.getShortName());
		if (dp != null)
			setCo2(dp);
		dp = (IntegerDataPoint) dps.get(DatapointType.ch2o.getShortName());
		if (dp != null)
			setCh2o(dp);
		dp = (IntegerDataPoint) dps.get(DatapointType.voc.getShortName());
		if (dp != null)
			setVoc(dp);
	}

	public void setMonitoringEnabled(BooleanDataPoint sv) {
		monitoringEnabled = sv;
		monitoringEnabled.setWritable(true);
		monitoringEnabled.setOptional(true);
		monitoringEnabled.setDoc("The current status of monitoring. \"True\" indicates enabled, and \"False\" indicates not enabled.");
		addDataPoint(monitoringEnabled);
	}

	public boolean getMonitoringEnabled() throws DataPointException, AccessException {
		if (monitoringEnabled == null)
			throw new DataPointException("Not implemented");
		return monitoringEnabled.getValue();
	}

	public void setMonitoringEnabled(boolean b) throws DataPointException, AccessException {
		if (monitoringEnabled == null)
			throw new DataPointException("Not implemented");
		monitoringEnabled.setValue(b);
	}

	public void setSensorPM1(IntegerDataPoint sv) {
		sensorPM1 = sv;
		sensorPM1.setWritable(false);
		sensorPM1.setOptional(true);
		sensorPM1.setDoc("The concentration of particle matter under 1㎛.. The minimum value is 0, and the maximum value is 1000.");
		addDataPoint(sensorPM1);
	}

	public int getSensorPM1() throws DataPointException, AccessException {
		if (sensorPM1 == null)
			throw new DataPointException("Not implemented");
		return sensorPM1.getValue();
	}

	public void setSensorPM2(IntegerDataPoint sv) {
		sensorPM2 = sv;
		sensorPM2.setWritable(false);
		sensorPM2.setOptional(true);
		sensorPM2.setDoc("The concentration of particle matter under 2.5㎛. The minimum value is 0, and the maximum value is 1000.");
		addDataPoint(sensorPM2);
	}

	public int getSensorPM2() throws DataPointException, AccessException {
		if (sensorPM2 == null)
			throw new DataPointException("Not implemented");
		return sensorPM2.getValue();
	}

	public void setSensorPM10(IntegerDataPoint sv) {
		sensorPM10 = sv;
		sensorPM10.setWritable(false);
		sensorPM10.setOptional(true);
		sensorPM10.setDoc("The concentration of particle matter under 10㎛. The minimum value is 0, and the maximum value is 1000.");
		addDataPoint(sensorPM10);
	}

	public int getSensorPM10() throws DataPointException, AccessException {
		if (sensorPM10 == null)
			throw new DataPointException("Not implemented");
		return sensorPM10.getValue();
	}

	public void setSensorOdor(IntegerDataPoint sv) {
		sensorOdor = sv;
		sensorOdor.setWritable(false);
		sensorOdor.setOptional(true);
		sensorOdor.setDoc("The concentration of odor that reflects air pollution. The minimum value is 0, and the maximum valu is 1000.");
		addDataPoint(sensorOdor);
	}

	public int getSensorOdor() throws DataPointException, AccessException {
		if (sensorOdor == null)
			throw new DataPointException("Not implemented");
		return sensorOdor.getValue();
	}

	public void setSensorHumidity(IntegerDataPoint sv) {
		sensorHumidity = sv;
		sensorHumidity.setWritable(false);
		sensorHumidity.setOptional(true);
		sensorHumidity.setDoc("The measured humidity. The minimum value is 0, and the maximum value is 100.");
		addDataPoint(sensorHumidity);
	}

	public int getSensorHumidity() throws DataPointException, AccessException {
		if (sensorHumidity == null)
			throw new DataPointException("Not implemented");
		return sensorHumidity.getValue();
	}

	public void setCo2(IntegerDataPoint sv) {
		co2 = sv;
		co2.setWritable(false);
		co2.setOptional(true);
		co2.setDoc("This value indicates the CO2 level in ppm (parts per million).");
		addDataPoint(co2);
	}

	public int getCo2() throws DataPointException, AccessException {
		if (co2 == null)
			throw new DataPointException("Not implemented");
		return co2.getValue();
	}

	public void setCo(IntegerDataPoint sv) {
		co = sv;
		co.setWritable(false);
		co.setOptional(true);
		co.setDoc("This value indicates the CO level in ppm.");
		addDataPoint(co);
	}

	public int getCo() throws DataPointException, AccessException {
		if (co == null)
			throw new DataPointException("Not implemented");
		return co.getValue();
	}

	public void setCh2o(IntegerDataPoint sv) {
		ch2o = sv;
		ch2o.setWritable(false);
		ch2o.setOptional(true);
		ch2o.setDoc("This value indicates the CH2O level in ppm.");
		addDataPoint(ch2o);
	}

	public int getCh2o() throws DataPointException, AccessException {
		if (ch2o == null)
			throw new DataPointException("Not implemented");
		return ch2o.getValue();
	}


	public void setVoc(IntegerDataPoint sv) {
		voc = sv;
		voc.setWritable(false);
		voc.setOptional(true);
		voc.setDoc("This value indicates the VOC (Volatile Organic Compounds) value in ppm.");
		addDataPoint(voc);
	}

	public int getVoc() throws DataPointException, AccessException {
		if (voc == null)
			throw new DataPointException("Not implemented");
		return voc.getValue();
	}

}
