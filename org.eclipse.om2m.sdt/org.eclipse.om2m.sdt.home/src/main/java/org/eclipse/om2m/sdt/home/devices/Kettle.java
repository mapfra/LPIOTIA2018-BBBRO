package org.eclipse.om2m.sdt.home.devices;

import java.security.Timestamp;
import java.util.concurrent.BlockingDeque;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.home.modules.BinarySwitch;
import org.eclipse.om2m.sdt.home.modules.Boiling;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.modules.KeepWarm;
import org.eclipse.om2m.sdt.home.modules.RunMode;
import org.eclipse.om2m.sdt.home.modules.Temperature;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class Kettle extends GenericDevice {

	public Kettle(String id, String serial, DeviceType type, Domain domain) {
		super(id, serial, DeviceType.deviceKettle, domain);
		// TODO Auto-generated constructor stub
	}
	
	public Kettle(final String id, final String serial, final Domain domain){
		super(id, serial, DeviceType.deviceKettle, domain);
		
		
	}
	

	
	private FaultDetection faultDetection;
	private RunMode runMode;
	//private LiquidRemaining waterLevel;
	private BinarySwitch boilingSwitch;
	private Temperature temperature;
	//private Boiling boiling;
	private KeepWarm keepWarm;
	
	public void addModule(Module module){
		if(module instanceof FaultDetection)
			addModule((FaultDetection)module);
		else if(module instanceof RunMode)
			addModule((RunMode)module);
		/*else if(module instanceof LiquidRemaining)
			addModule((LiquidRemaining)module);*/
		else if(module instanceof BinarySwitch)
			addModule((BinarySwitch)module);
		else if(module instanceof KeepWarm)
			addModule((KeepWarm)module);
		else if(module instanceof Temperature)
			addModule((Temperature)module);
		else if(module instanceof Boiling)
			addModule((Boiling)module);
		else 
			super.addModule(module);
		
		
	}
	
	//******************ADD MODULES******************
	
	public void addModule(FaultDetection mod) {
		this.faultDetection = mod;
		super.addModule(faultDetection);
	}
	
	public void addModule(RunMode mod){
		this.runMode = mod;
		super.addModule(runMode);
	}
	
	/*(public void addModule(Boiling mod){
		this.boiling = mod;
		super.addModule(boiling);
	}
	*/
	/*public void addModule(LiquidRemaining mod){
		this.waterLevel = mod;
		super.addModule(waterLevel);
	}*/
	
	public void addModule(BinarySwitch mod){
		this.boilingSwitch = mod;
		super.addModule(boilingSwitch);
	}
	
	public void addModule(Temperature mod){
		this.temperature = mod;
		super.addModule(temperature);
	}
	
	public void addModule(KeepWarm mod){
		this.keepWarm = mod;
		super.addModule(keepWarm);
	}
	
	//******************GETTERS******************

	public FaultDetection getFaultDetection() {
		return faultDetection;
	}
	
	public Temperature getTemperature(){
		return temperature;
	}
	
	
	public KeepWarm getKeepWarm(){
		return keepWarm;
	}
	
	
	
	
	
	

	public RunMode getRunMode() {
		return runMode;
	}

	/*public LiquidRemaining getWaterLevel() {
		return waterLevel;
	}*/

	public BinarySwitch getBoilingSwitch() {
		return boilingSwitch;
	}

	/*
	  public Boiling getBoiling() {
	 
		return boiling;
	}

	public void setBoiling(Boiling boiling) {
		this.boiling = boiling;
	}
	
	*/
	
	
	

}
