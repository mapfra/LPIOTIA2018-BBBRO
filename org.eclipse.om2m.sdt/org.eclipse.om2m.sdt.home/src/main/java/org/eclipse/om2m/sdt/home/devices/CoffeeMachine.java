package org.eclipse.om2m.sdt.home.devices;


import java.util.Collection;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.home.modules.BinarySwitch;
import org.eclipse.om2m.sdt.home.modules.Brewing;
import org.eclipse.om2m.sdt.home.modules.Clock;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.modules.Foaming;
import org.eclipse.om2m.sdt.home.modules.Grinder;
import org.eclipse.om2m.sdt.home.modules.KeepWarm;
import org.eclipse.om2m.sdt.home.modules.Level;
import org.eclipse.om2m.sdt.home.modules.RunMode;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class CoffeeMachine extends GenericDevice{

	
	public CoffeeMachine(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceCoffeeMachine, domain);
	}
	
	private FaultDetection faultDetection;
	
	private RunMode runMode;
	
	private Clock clock;
	
	private Brewing brewing;
	
	private Level waterStatus;
	
	private Level milkStatus;
	
	private Level beansStatus;
	
	private Grinder grinder;
	
	private Foaming foamedMilk;
	
	private Level milkQuantity;
	
	private KeepWarm keepWarm;
	
	private BinarySwitch brewingSwitch;
	
	public void addModule(Module module) {
		if (module instanceof FaultDetection)
			addModule((FaultDetection)module);
		else if (module instanceof RunMode)
			addModule((RunMode)module);
		else if (module instanceof Clock)
			addModule((Clock)module);
		else if (module instanceof Brewing)
			addModule((Brewing)module);
		else if (module instanceof Level){
			Collection<String> col = module.getDataPointNames();
			if(col.contains("waterStatus")){
				addModuleWaterStatus((Level)module);
			}
			if(col.contains("milkStatus")){
				addModuleMilkStatus((Level)module);
			}
			if(col.contains("beansStatus")){
				addModuleBeansStatus((Level)module);
			}
			if(col.contains("milkQuantity")){
				addModuleMilkQuantity((Level)module);
			}
		}
		else if (module instanceof Grinder)
			addModule((Grinder)module);
		else if (module instanceof Foaming)
			addModule((Foaming)module);
		else if(module instanceof KeepWarm)
			addModule((KeepWarm)module);
		else if(module instanceof BinarySwitch)
			addModule((BinarySwitch)module);
		else
			super.addModule(module);
	}
	
	public void addModule(FaultDetection mod) {
		this.faultDetection = mod;
		super.addModule(faultDetection);
	}
	
	public void addModule(BinarySwitch mod){
		this.brewingSwitch = mod;
		super.addModule(brewingSwitch);
	}
	
	public void addModule(KeepWarm mod){
		this.keepWarm = mod;
		super.addModule(keepWarm);
	}

	
	public void addModule(RunMode mod) {
		this.runMode = mod;
		super.addModule(runMode);
	}
	
	public void addModule(Clock mod) {
		this.clock = mod;
		super.addModule(clock);
	}
	
	public void addModule(Brewing mod) {
		this.brewing = mod;
		super.addModule(brewing);
	}
	
	public void addModule(Grinder mod) {
		this.grinder = mod;
		super.addModule(grinder);
	}
	
	public void addModule(Foaming mod) {
		this.foamedMilk = mod;
		super.addModule(foamedMilk);
	}
	
	
	public void addModuleWaterStatus(Level mod) {
		this.waterStatus = mod;
		super.addModule(waterStatus);
	}
	
	public void addModuleMilkStatus(Level mod) {
		this.milkStatus = mod;
		super.addModule(milkStatus);
	}
	
	public void addModuleBeansStatus(Level mod) {
		this.beansStatus = mod;
		super.addModule(beansStatus);
	}
	
	
	public void addModuleMilkQuantity(Level mod) {
		this.milkQuantity = mod;
		super.addModule(milkQuantity);
	}
	
	public FaultDetection getFaultDetection() {
		return faultDetection;
	}

	public RunMode getRunMode() {
		return runMode;
	}

	public Clock getClock() {
		return clock;
	}

	public Brewing getBrewing() {
		return brewing;
	}

	public Level getWaterStatus() {
		return waterStatus;
	}

	public Level getMilkStatus() {
		return milkStatus;
	}

	public Level getBeansStatus() {
		return beansStatus;
	}

	public Grinder getGrinder() {
		return grinder;
	}

	public Foaming getFoamedMilk() {
		return foamedMilk;
	}

	public Level getMilkQuantity() {
		return milkQuantity;
	}

	public KeepWarm getKeepWarm() {
		return keepWarm;
	}

	public void setKeepWarm(KeepWarm keepWarm) {
		this.keepWarm = keepWarm;
	}

	public BinarySwitch getBrewingSwitch() {
		return brewingSwitch;
	}

	public void setBrewingSwitch(BinarySwitch brewingSwitch) {
		this.brewingSwitch = brewingSwitch;
	}

	
	
	
}
