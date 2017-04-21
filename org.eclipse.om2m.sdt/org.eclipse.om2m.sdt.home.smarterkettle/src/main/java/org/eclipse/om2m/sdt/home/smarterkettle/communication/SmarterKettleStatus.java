package org.eclipse.om2m.sdt.home.smarterkettle.communication;

public class SmarterKettleStatus {
	
	public final SmarterKettleStatusDescriptor NO_FAULT = new SmarterKettleStatusDescriptor(0, "No faults detected");
	public final SmarterKettleStatusDescriptor UNREACHABLE = new SmarterKettleStatusDescriptor(-1, "Kettle is unreachable");
	public final SmarterKettleStatusDescriptor WATER_ERROR = new SmarterKettleStatusDescriptor(-2,  "There's no water in the kettle");
	public final SmarterKettleStatusDescriptor UNPLUGGED = new SmarterKettleStatusDescriptor(-3, "Kettle isn't plugged");
	
	public final SmarterKettleStatusDescriptor BOILING_IN_PROGRESS = new SmarterKettleStatusDescriptor(1,  "Boiling in progress");
	

	private int currentTemperature = 0;
	private  int waterLevel = 0;

	private boolean isPlugged = false;
	private boolean isBoiling = false;
	private boolean isEmpty = false;
	
	private int targetTemperature = 30;
	private int minTemperature = 20;;
	private int maxTemperature = 100;
	private int stepTemperature = 1;
	
	public int getCode(){
		if(!isPlugged)
			return UNPLUGGED.getCode();
		else if (isEmpty) 
			return WATER_ERROR.getCode();
		else 
			return NO_FAULT.getCode();
	}
	
	public String getDescription(){
		if(!isPlugged)
			return UNPLUGGED.getDescription();
		else if (isEmpty) 
			return WATER_ERROR.getDescription();
		else 
			return NO_FAULT.getDescription();
	}
	
	

	public enum waterLevels {
		EMPTY, LOW, HALF, QUARTER, FULL;
	}

	private waterLevels waterLevelName = waterLevels.EMPTY;

	public waterLevels getWaterLevelName() {
		return waterLevelName;
	}

	public void setWaterLevelName(waterLevels waterLevelName) {
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

	public  void setWaterLevelEnum(int waterLevel) {
		if (waterLevel >= 190)
			waterLevelName = waterLevels.FULL;
		else if (waterLevel < 190 && waterLevel >= 120)
			waterLevelName = waterLevels.QUARTER;
		else if (waterLevel < 120 && waterLevel >= 80)
			waterLevelName = waterLevels.HALF;
		else if (waterLevel < 80 && waterLevel >= 20)
			waterLevelName = waterLevels.LOW;
		else
			waterLevelName = waterLevels.EMPTY;
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
	
	
	
	public boolean getFaultDetection(){
		if(!isPlugged)
			return false;
		else if(isEmpty)
			return false;
		else
			return true;
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