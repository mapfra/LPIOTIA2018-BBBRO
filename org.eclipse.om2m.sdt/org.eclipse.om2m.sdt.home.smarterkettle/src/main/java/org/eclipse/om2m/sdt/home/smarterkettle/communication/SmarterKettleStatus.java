/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.smarterkettle.communication;

public class SmarterKettleStatus {
	
	public final SmarterKettleStatusDescriptor NO_FAULT = new SmarterKettleStatusDescriptor(0, "No faults detected");
	public final SmarterKettleStatusDescriptor UNREACHABLE = new SmarterKettleStatusDescriptor(-1, "Kettle is unreachable");
	public final SmarterKettleStatusDescriptor WATER_ERROR = new SmarterKettleStatusDescriptor(-2,  "There's no water in the kettle");
	public final SmarterKettleStatusDescriptor UNPLUGGED = new SmarterKettleStatusDescriptor(-3, "Kettle isn't plugged");
	
	public final SmarterKettleStatusDescriptor BOILING_IN_PROGRESS = new SmarterKettleStatusDescriptor(1,  "Boiling in progress");
	
	private enum WaterLevels {
		EMPTY, LOW, HALF, QUARTER, FULL;
	}

	private int currentTemperature = 0;
	private  int waterLevel = 0;

	private WaterLevels waterLevelName = WaterLevels.EMPTY;

	private boolean isPlugged = false;
	private boolean isBoiling = false;
	private boolean isEmpty = false;
	
	private int targetTemperature = 30;
	private int minTemperature = 20;;
	private int maxTemperature = 100;
	private int stepTemperature = 1;
	
	public int getCode() {
		if (!isPlugged)
			return UNPLUGGED.getCode();
		else if (isEmpty) 
			return WATER_ERROR.getCode();
		else 
			return NO_FAULT.getCode();
	}
	
	public String getDescription() {
		if (!isPlugged)
			return UNPLUGGED.getDescription();
		else if (isEmpty) 
			return WATER_ERROR.getDescription();
		else 
			return NO_FAULT.getDescription();
	}

	public WaterLevels getWaterLevelName() {
		return waterLevelName;
	}

	public void setWaterLevelName(WaterLevels waterLevelName) {
		this.waterLevelName = waterLevelName;
	}

	public int getCurrentTemperature() {
		return currentTemperature;
	}

	public void setCurrentTemperature(int currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	public int getWaterLevel() {
		return waterLevel;
	}

	public  void setWaterLevel(int waterLevel) {
		this.waterLevel = waterLevel;
	}

	public  void setWaterLevelEnum(int level) {
		if (level >= 190)
			waterLevelName = WaterLevels.FULL;
		else if (level < 190 && level >= 120)
			waterLevelName = WaterLevels.QUARTER;
		else if (level < 120 && level >= 80)
			waterLevelName = WaterLevels.HALF;
		else if (level < 80 && level >= 20)
			waterLevelName = WaterLevels.LOW;
		else
			waterLevelName = WaterLevels.EMPTY;
	}

	public  boolean isPlugged() {
		return isPlugged;
	}

	public  void setPlugged(boolean isPlugged) {
		this.isPlugged = isPlugged;
	}

	public  boolean isBoiling() {
		return isBoiling;
	}

	public  void setBoiling(boolean isBoiling) {
		this.isBoiling = isBoiling;
	}
	
	public boolean getFaultDetection() {
		return isPlugged && ! isEmpty;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public int getTargetTemperature() {
		return targetTemperature;
	}

	public void setTargetTemperature(int targetTemperature) {
		this.targetTemperature = targetTemperature;
	}

	public int getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(int minTemperature) {
		this.minTemperature = minTemperature;
	}

	public int getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(int maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public int getStepTemperature() {
		return stepTemperature;
	}

	public void setStepTemperature(int stepTemperature) {
		this.stepTemperature = stepTemperature;
	}


}