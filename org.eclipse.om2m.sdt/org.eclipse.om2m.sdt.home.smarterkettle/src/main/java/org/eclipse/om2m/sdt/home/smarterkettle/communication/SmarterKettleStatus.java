/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.smarterkettle.communication;

import org.eclipse.om2m.sdt.home.types.LiquidLevel;

public class SmarterKettleStatus {
	
	class StatusDescriptor {
		
		private int code;
		private String description;
		
		public StatusDescriptor(int code, String desc) {
			this.code = code;
			this.description = desc;
		}

	}
	
	public final StatusDescriptor NO_FAULT = new StatusDescriptor(0, "No fault detected");
	public final StatusDescriptor UNREACHABLE = new StatusDescriptor(-1, "Kettle is unreachable");
	public final StatusDescriptor WATER_ERROR = new StatusDescriptor(-2,  "No water in the kettle");
	public final StatusDescriptor UNPLUGGED = new StatusDescriptor(-3, "Kettle not plugged");
	public final StatusDescriptor BOILING_IN_PROGRESS = new StatusDescriptor(1,  "Boiling in progress");

	private int currentTemperature = 0;
	private LiquidLevel.Values waterLevel = LiquidLevel.Values.zero;

	private boolean isPlugged = false;
	private boolean isBoiling = false;
	private boolean isEmpty = false;
	
	private int targetTemperature = 30;
	private int minTemperature = 20;
	private int maxTemperature = 100;
	private int stepTemperature = 1;
	
	public int getCode() {
		if (!isPlugged)
			return UNPLUGGED.code;
		else if (isEmpty) 
			return WATER_ERROR.code;
		else 
			return NO_FAULT.code;
	}
	
	public String getDescription() {
		if (!isPlugged)
			return UNPLUGGED.description;
		else if (isEmpty) 
			return WATER_ERROR.description;
		else 
			return NO_FAULT.description;
	}

	public int getCurrentTemperature() {
		return currentTemperature;
	}

	public void setCurrentTemperature(int currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	public LiquidLevel.Values getWaterLevel() {
		return waterLevel;
	}

	public  void setWaterLevel(int level) {
		if (level >= 190)
			waterLevel = LiquidLevel.Values.maximum;
		else if (level >= 120)
			waterLevel = LiquidLevel.Values.high;
		else if (level >= 80)
			waterLevel = LiquidLevel.Values.medium;
		else if (level >= 20)
			waterLevel = LiquidLevel.Values.low;
		else
			waterLevel = LiquidLevel.Values.zero;
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
