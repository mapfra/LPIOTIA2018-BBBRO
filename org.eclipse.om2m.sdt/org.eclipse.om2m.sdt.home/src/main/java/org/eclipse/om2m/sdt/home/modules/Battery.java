/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import javax.xml.bind.PropertyException;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.Property;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;
import org.eclipse.om2m.sdt.home.types.PropertyType;
import org.eclipse.om2m.sdt.types.SimpleType;

public class Battery extends Module {
	
	private IntegerDataPoint level;
	
	private IntegerDataPoint capacity;
	private IntegerDataPoint batteryThreshold;
	
	private BooleanDataPoint charging;
	private BooleanDataPoint discharging;
	private BooleanDataPoint lowBattery;
	
	private Property electricEnergy;
	private Property voltage;
	private Property material;
	
	public Battery(final String name, final Domain domain, IntegerDataPoint level) {
		super(name, domain, ModuleType.battery);

		if ((level == null) ||
				! level.getShortDefinitionType().equals(DatapointType.level.getShortName())) {
			throw new IllegalArgumentException("Wrong level datapoint: " + level);
		}
		this.level = level;
		this.level.setWritable(false);
		this.level.setDoc("The rounded percentage of the current level of battery in the range of [0, 100]. 0 percentage shall mean no battery remained.");
		addDataPoint(this.level);
	}
	
	public Battery(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (IntegerDataPoint) dps.get(DatapointType.level.getShortName()));
		
		IntegerDataPoint capacity = 
				(IntegerDataPoint) dps.get(DatapointType.capacity.getShortName());
		if (capacity != null)
			setCapacity(capacity);
		IntegerDataPoint batteryThreshold = 
				(IntegerDataPoint) dps.get(DatapointType.batteryThreshold.getShortName());
		if (batteryThreshold != null)
			setBatteryThreshold(batteryThreshold);
		BooleanDataPoint charging = 
				(BooleanDataPoint) dps.get(DatapointType.charging.getShortName());
		if (charging != null)
			setCharging(charging);
		BooleanDataPoint discharging = (BooleanDataPoint) dps.get(DatapointType.discharging.getShortName());
		if (discharging != null)
			setDischarging(discharging);
		BooleanDataPoint lowBattery = (BooleanDataPoint) dps.get(DatapointType.lowBattery.getShortName());
		if (lowBattery != null)
			setLowBattery(lowBattery);
	}

	public float getLevel() throws DataPointException, AccessException {
		return level.getValue();
	}

	public void setCharging(BooleanDataPoint dp) {
		this.charging = dp;
		this.charging.setOptional(true);
		this.charging.setWritable(false);
		this.charging.setDoc("The status of charging. \"True\" indicates enabled, and \"False\" indicates not enabled.");
		addDataPoint(charging);
	}

	public boolean getCharging() throws DataPointException, AccessException {
		if (charging == null)
			throw new DataPointException("Not implemented");
		return charging.getValue();
	}

	public void setDischarging(BooleanDataPoint dp) {
		this.discharging = dp;
		this.discharging.setOptional(true);
		this.discharging.setWritable(false);
		this.discharging.setDoc("The status of discharging. \"True\" indicates enabled, and \"False\" indicates not enabled");
		addDataPoint(discharging);
	}

	public boolean getDischarging() throws DataPointException, AccessException {
		if (discharging == null)
			throw new DataPointException("Not implemented");
		return discharging.getValue();
	}

	public void setLowBattery(BooleanDataPoint dp) {
		this.lowBattery = dp;
		this.lowBattery.setOptional(true);
		this.lowBattery.setWritable(false);
		this.lowBattery.setDoc("To indicate that the battery is in low charge level.");
		addDataPoint(lowBattery);
	}

	public boolean getLowBattery() throws DataPointException, AccessException {
		if (lowBattery == null)
			throw new DataPointException("Not implemented");
		return lowBattery.getValue();
	}

	public void setCapacity(IntegerDataPoint dp) {
		this.capacity = dp;
		this.capacity.setOptional(true);
		this.capacity.setWritable(false);
		this.capacity.setDoc("The total capacity of battery in mAh.");
		addDataPoint(capacity);
	}

	public int getCapacity() throws DataPointException, AccessException {
		if (capacity == null)
			throw new DataPointException("Not implemented");
		return capacity.getValue();
	}

	public void setBatteryThreshold(IntegerDataPoint dp) {
		this.batteryThreshold = dp;
		this.batteryThreshold.setOptional(true);
		this.batteryThreshold.setWritable(true);
		this.batteryThreshold.setDoc("When the battery level is less than batteryThreshold then the lowBattery is true (and optionally to generate an alarm).");
		addDataPoint(batteryThreshold);
	}

	public int getBatteryThreshold() throws DataPointException, AccessException {
		if (batteryThreshold == null)
			throw new DataPointException("Not implemented");
		return batteryThreshold.getValue();
	}

	public void setBatteryThreshold(int b) throws DataPointException, AccessException {
		if (batteryThreshold == null)
			throw new DataPointException("Not implemented");
		batteryThreshold.setValue(b);
	}
	
	public void setElectricEnergy(int v) {
		if (electricEnergy == null) {
			electricEnergy = new Property(PropertyType.electricEnergy);
			electricEnergy.setType(SimpleType.Integer);
			electricEnergy.setDoc("Rated electric energy");
			addProperty(electricEnergy);
		}
		electricEnergy.setValue(Integer.toString(v));
	}
	
	public int getElectricEnergy() throws PropertyException {
		if (electricEnergy == null)
			throw new PropertyException("Not implemented");
		return Integer.parseInt(electricEnergy.getValue());
	}
	
	public void setVoltage(int v) {
		if (voltage == null) {
			voltage = new Property(PropertyType.voltage);
			voltage.setType(SimpleType.Integer);
			voltage.setDoc("Rated voltage");
			addProperty(voltage);
		}
		voltage.setValue(Integer.toString(v));
	}
	
	public int getVoltage() throws PropertyException {
		if (voltage == null)
			throw new PropertyException("Not implemented");
		return Integer.parseInt(voltage.getValue());
	}
	
	public void setMaterial(String v) {
		if (material == null) {
			material = new Property(PropertyType.material);
			material.setType(SimpleType.String);
			material.setDoc("The material (e.g. lithium ion, nickel and lead) of the cell.");
			addProperty(material);
		}
		material.setValue(v);
	}
	
	public String getMaterial() throws PropertyException {
		if (material == null)
			throw new PropertyException("Not implemented");
		return material.getValue();
	}
	
}
