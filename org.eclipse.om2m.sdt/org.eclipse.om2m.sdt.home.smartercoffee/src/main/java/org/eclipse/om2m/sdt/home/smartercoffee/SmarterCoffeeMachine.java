/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.smartercoffee;

import java.util.List;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.CoffeeMachine;
import org.eclipse.om2m.sdt.home.driver.Utils;
import org.eclipse.om2m.sdt.home.modules.Brewing;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.modules.Grinder;
import org.eclipse.om2m.sdt.home.modules.Level;
import org.eclipse.om2m.sdt.home.smartercoffee.communication.SmarterCoffeeCommands;
import org.eclipse.om2m.sdt.home.smartercoffee.communication.SmarterCoffeeCommunication;
import org.eclipse.om2m.sdt.home.types.LevelType;
import org.eclipse.om2m.sdt.home.types.TasteStrength;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class SmarterCoffeeMachine extends CoffeeMachine{
	
	private List<ServiceRegistration> registrations;
	private Domain domain; 
	private String serial = "SmarterCoffee";
	private String IP;
	private int port;
	private SmarterCoffeeCommunication smarterCoffee;

	public SmarterCoffeeMachine(String id, Domain domain, String ip , int port) {
		super(id, id, domain);
		this.domain = domain;
		this.serial = id;
		this.IP = ip;
		this.port = port;
		
		this.smarterCoffee = new SmarterCoffeeCommunication(ip, port);
		
		setProperties();
		
		try {
			addFaultDetection();
		} catch (Exception e) {
			Activator.logger.warning("Error addFaultDetection", e);
		}
		try {
			addBrewing();
		} catch (Exception e) {
			Activator.logger.warning("Error addBrewing", e);
		}
		try {
			addGrinder();
		} catch (Exception e) {
			Activator.logger.warning("Error addGrinder", e);
		}
		try {
			addWaterStatus();
		} catch (Exception e) {
			Activator.logger.warning("Error addWaterStatus", e);
		}
		
		setDeviceManufacturer("Smarter");
		setDeviceModelName("IKTSMC10EUFR");
		setDeviceName("Smarter coffee machine");
		
	}
	
	public void register(BundleContext context) {
		registrations = Utils.register(this, context);
	}

	void unregister() {
		for (ServiceRegistration reg : registrations) {
			reg.unregister();
		}
		for (String moduleName : getModuleNames()) {
			// remove module from domain
			domain.removeModule(moduleName);
		}
		domain.removeDevice(getName());
	}
	
	private void addFaultDetection() {
		FaultDetection faultDetection = new FaultDetection("FaultDetection_" + getId(), domain,
				new BooleanDataPoint("status") {
			@Override
			public Boolean doGetValue() throws DataPointException {
				smarterCoffee.getStatus();
				return smarterCoffee.getFaultDetection(); 
			}
		}, new IntegerDataPoint("code") {
			
			@Override
			protected Integer doGetValue() throws DataPointException {
				return smarterCoffee.getCode();
			}
		}, new StringDataPoint("description") {
			
			@Override
			protected String doGetValue() throws DataPointException {
				return smarterCoffee.getDescription();
			}
		});
		
		addModule(faultDetection);
	}
	
	private void addBrewing(){
		
		Activator.logger.info("add Brewing starting");
		
		Brewing brewing = new Brewing("Brewing_" + getId(), domain, new IntegerDataPoint("cupsNumber") {
			
			int cupNumber = 0;
			
			@Override
			protected Integer doGetValue() throws DataPointException {
				return cupNumber;
			}
			
			@Override
			protected void doSetValue(Integer value) throws DataPointException {
				cupNumber = value;
				smarterCoffee.setNumberOfCups(value);
			}
		}, new BooleanDataPoint("keepWarm") {
			
			boolean keepWarm = false;
			
			@Override
			protected Boolean doGetValue() throws DataPointException {
				return keepWarm;
			}
			
			@Override
			protected void doSetValue(Boolean value) throws DataPointException {
				keepWarm = value;
				if(value)
					smarterCoffee.setHotPlateOn(5); // argument is a mintues user want to use hot plate
				else 
					smarterCoffee.setHotPlateOff();
			}
		}, new TasteStrength("strength") {
			
			int strength = TasteStrength.zero;

			@Override
			protected Integer doGetValue() throws DataPointException {
				return strength;
			}
			
			@Override
			protected void doSetValue(Integer value) throws DataPointException {
				strength = value;
				if(value >= TasteStrength.zero && value < TasteStrength.medium){
					smarterCoffee.setBrewStrength(SmarterCoffeeCommands.BREW_STRENGTH_0);
				}
				else if(value == TasteStrength.medium){
					smarterCoffee.setBrewStrength(SmarterCoffeeCommands.BREW_STRENGTH_1);
				}
				else if (value > TasteStrength.medium && value <= TasteStrength.maximum){
					smarterCoffee.setBrewStrength(SmarterCoffeeCommands.BREW_STRENGTH_2);
				}
			}
			
			
		}, new IntegerDataPoint("status") {
		
			@Override
			protected Integer doGetValue() throws DataPointException {
				smarterCoffee.getStatus();
				return smarterCoffee.getCoffeeReadyStatus();
			}
			@Override
			protected void doSetValue(Integer value) throws DataPointException {
				if(value.intValue() == 1)
					try {
						smarterCoffee.start(getGrinder().getUseGrinder(), getBrewing().getCupsNumber(), getBrewing().getStrength(), getBrewing().getKeepWarm());
												
					} catch (AccessException e) {
						throw new DataPointException(e.getMessage());
					}
				else 
					smarterCoffee.stop();
			}
		});
		
		addModule(brewing);
		
	}
	
	private void addGrinder(){
	
		Activator.logger.info("add Grinder starting");
		
		Grinder grinder = new Grinder("Grinder_" + getId(), domain, new BooleanDataPoint("useGrinder") {
			
			boolean useGrinder = false;
			
			@Override
			protected Boolean doGetValue() throws DataPointException {
				return useGrinder;
			}
			
			@Override
			protected void doSetValue(Boolean value) throws DataPointException {
				useGrinder = value.booleanValue();
			}
		}, new IntegerDataPoint("grindCoarsenes") {
			
			@Override
			protected Integer doGetValue() throws DataPointException {
				// set and get coarsenes of beans is NOT POSSIBLE through API in SmarterCoffee (is's setted via physical potentiometer on the device)
				return null;
			}
		});
	
		addModule(grinder);
	}
	
	private void addWaterStatus(){
		
		Activator.logger.info("add WaterStatus starting");
		
		Level waterStatus = new Level("waterStatus", domain, null, new LevelType("waterStatus") {
			
			@Override
			protected Integer doGetValue() throws DataPointException {
				smarterCoffee.getStatus();
				return smarterCoffee.getWaterStatus();
			}
		});
		
		addModule(waterStatus);
		
	}
	
	
	
	public void setProperties(){
		
	}
	
	
	
}

