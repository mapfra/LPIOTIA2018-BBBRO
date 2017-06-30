/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.smartercoffee.communication;

import org.eclipse.om2m.sdt.home.smartercoffee.Activator;
import org.eclipse.om2m.sdt.home.types.LiquidLevel;

public class SmarterCoffeeStatus {
	
	public static final SmarterCoffeeStatusDescriptor NO_FAULT = new SmarterCoffeeStatusDescriptor(0, "No fault detected.");
	
	public static final SmarterCoffeeStatusDescriptor UNREACHEABLE = new SmarterCoffeeStatusDescriptor(-1, "The coffee machine is unreachable");
	
	public static final SmarterCoffeeStatusDescriptor CARAFE_ERROR = new SmarterCoffeeStatusDescriptor(-2, "No carafe on the plate");
	
	public static final SmarterCoffeeStatusDescriptor WATER_ERROR = new SmarterCoffeeStatusDescriptor(-3, "No water in tank");
	
	
	public static final SmarterCoffeeStatusDescriptor COFFEE_READY = new SmarterCoffeeStatusDescriptor(1, "Coffee is ready!");
		
	public static final SmarterCoffeeStatusDescriptor BREWING_IN_PROGRESS = new SmarterCoffeeStatusDescriptor(2, "Brewing in progress");
	
	public static final SmarterCoffeeStatusDescriptor UNKNOW_STATE = new SmarterCoffeeStatusDescriptor(100, "The state is unknow");

	
	private boolean isUnreachable = false;
	private boolean isCarafeUndetected = false;
	private boolean isWaterError = false;
	
	private int waterLevel = 1;
	private int cupsNumber = 0;
	private int strength = 0;
	
	private boolean carafeDetected = false;
	private boolean useGrinder = false;
	private boolean machineReady = false;
	private boolean grindInProgress = false;
	private boolean waterPumpInProgress = false;
	private boolean cycleComplete = false;
	private boolean keepWarm = false;
	
	private boolean coffeeReady = false;
	
	private boolean waterPumpWasUse = false;
	
	
	public boolean getFaultDetection(){
		return isCarafeUndetected || isUnreachable || isWaterError;
	}
	
	public void parseStatus(byte[] dataToParse){
		
		if (dataToParse !=null) {
			
			//Activator.logger.debug("Data parsing!");
			
			this.waterLevel = dataToParse[2];
			this.carafeDetected = getParameter(dataToParse[1], SmarterCoffeeCommands.STATUS_FLAGS_CARAFE_DETECTED_0);
			this.machineReady = getParameter(dataToParse[1], SmarterCoffeeCommands.STATUS_FLAGS_MASK_MACHINE_READY_2);
			this.useGrinder = getParameter(dataToParse[1], SmarterCoffeeCommands.STATUS_FLAGS_MASK_USE_GRIDNER_1);
			this.grindInProgress = getParameter(dataToParse[1], SmarterCoffeeCommands.STATUS_FLAGS_MASK_GRIND_3);
			this.waterPumpInProgress = getParameter(dataToParse[1], SmarterCoffeeCommands.STATUS_FLAGS_MASK_WATER_PUMP_4);
			this.cycleComplete = getParameter(dataToParse[1], SmarterCoffeeCommands.STATUS_FLAGS_MASK_CYCLE_COMPLETE_5);
			this.keepWarm = getParameter(dataToParse[1], SmarterCoffeeCommands.STATUS_FLAGS_MASK_KEEP_WARM_6);
			//Activator.logger.debug("machineReady: " + machineReady + " useGrinder: " + useGrinder + " grindInProgress: " + grindInProgress + " waterPumpInProgress: " + waterPumpInProgress + " cycleComplete: " + cycleComplete + " keepWarm: " + keepWarm);
			updateFaultDetectors();
			checkCoffeeReady();
		}
		else Activator.logger.debug("There is no data to parse. Check SmarterCoffeeMachine reachability");
			
	}
	
	public boolean checkCoffeeReady(){
		//Activator.logger.debug("Check coffee ready function...");
		this.coffeeReady = false;
		if(waterPumpInProgress){
			this.waterPumpWasUse = true;
		}
		if(machineReady && carafeDetected && waterPumpWasUse && !waterPumpInProgress){
			this.waterPumpWasUse = false;
			this.coffeeReady = true;
		}
		
		return this.coffeeReady;
	}
	
	public int getCoffeePreparationStatus(){
		if(checkCoffeeReady()) return COFFEE_READY.code;
		if(waterPumpWasUse) return BREWING_IN_PROGRESS.code;
		else return UNKNOW_STATE.code;
	}
	
	public boolean getParameter(byte dataToParse, byte mask){
		int shift = 0;
		switch(mask){
			case SmarterCoffeeCommands.STATUS_FLAGS_CARAFE_DETECTED_0:
				shift = 0;
				break;
			case SmarterCoffeeCommands.STATUS_FLAGS_MASK_USE_GRIDNER_1:
				shift = 1;
				break;
			case SmarterCoffeeCommands.STATUS_FLAGS_MASK_MACHINE_READY_2:
				shift = 2;
				break;
			case SmarterCoffeeCommands.STATUS_FLAGS_MASK_GRIND_3:
				shift = 3;
				break;
			case SmarterCoffeeCommands.STATUS_FLAGS_MASK_WATER_PUMP_4:
				shift = 4;
				break;
			case SmarterCoffeeCommands.STATUS_FLAGS_MASK_CYCLE_COMPLETE_5:
				shift = 5;
				break;
			case SmarterCoffeeCommands.STATUS_FLAGS_MASK_KEEP_WARM_6:
				shift = 6;
				break;
			case SmarterCoffeeCommands.STATUS_FLAGS_MASK_SCHEDULE_7:
				shift = 7;
				break;
		}
		return (((dataToParse & mask) >> shift) == 1)?true:false;
	}
	
	public void updateFaultDetectors(){
		this.isCarafeUndetected = !carafeDetected;
		if(this.waterLevel < 1) isWaterError = true;
		else isWaterError = false;
		
		Activator.logger.debug("Is carafe undetected: " + isCarafeUndetected + " isWaterError: " + isWaterError);
	}
	
	public int getCode(){
		if(isUnreachable){
			return UNREACHEABLE.code;
		}
		else if(isCarafeUndetected){
			return CARAFE_ERROR.code;
		}
		else if(isWaterError){
			return WATER_ERROR.code;
		}
		else
			return NO_FAULT.code;
	}
	
	public String getDescription(){
		if(isUnreachable){
			return UNREACHEABLE.description;
		}
		else if(isCarafeUndetected){
			return CARAFE_ERROR.description;
		}
		else if(isWaterError){
			return WATER_ERROR.description;
		}
		else
			return NO_FAULT.description;
	}

	public boolean isUnreachable() {
		return isUnreachable;
	}

	public void setUnreachable(boolean isReachable) {
		this.isUnreachable = isReachable;
	}

	public boolean isCarafeUndetected() {
		return isCarafeUndetected;
	}

	public void setCarafeUndetected(boolean isCarafeDetected) {
		this.isCarafeUndetected = isCarafeDetected;
	}

	public boolean isWaterError() {
		return isWaterError;
	}

	public void setWaterError(boolean isWaterError) {
		this.isWaterError = isWaterError;
	}

	public boolean isCoffeeReady() {
		return coffeeReady;
	}

	public void setCoffeeReady(boolean coffeeReady) {
		this.coffeeReady = coffeeReady;
	}

	public int getWaterLevel() {
		
		System.out.println("raw water level=" + waterLevel);
		if(waterLevel == 0){
			return LiquidLevel.low;
		}
		if(waterLevel == 17){		//about HALF -> from about level 7 on the right side of the tank			
			return LiquidLevel.medium;
		}
		if(waterLevel == 18){	//from about level 7 to 10					
			return LiquidLevel.high;
		}
		if(waterLevel == 19){							//almost FULL -> from about level 10
			return LiquidLevel.maximum;
		}
		else{
			return -1;
		}
		
	}

	public void setKeepWarm(boolean keepWarm) {
		this.keepWarm = keepWarm;
	}
	
	public boolean getKeepWarm(){
		return keepWarm;
	}

}
