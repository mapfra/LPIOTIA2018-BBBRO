/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.smarterkettle;

import java.awt.KeyboardFocusManager;
import java.nio.channels.NonWritableChannelException;
import java.util.List;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.FloatDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.ActionException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.actions.Toggle;
import org.eclipse.om2m.sdt.home.devices.Kettle;
import org.eclipse.om2m.sdt.home.driver.Utils;
import org.eclipse.om2m.sdt.home.modules.BinarySwitch;
import org.eclipse.om2m.sdt.home.modules.Boiling;
import org.eclipse.om2m.sdt.home.modules.Brewing;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.modules.Grinder;
import org.eclipse.om2m.sdt.home.modules.KeepWarm;
import org.eclipse.om2m.sdt.home.modules.Level;
import org.eclipse.om2m.sdt.home.modules.Temperature;
import org.eclipse.om2m.sdt.home.smarterkettle.communication.SmarterKettleCommands;
import org.eclipse.om2m.sdt.home.smarterkettle.communication.SmarterKettleCommunication;
import org.eclipse.om2m.sdt.home.smarterkettle.communication.SmarterKettleStatus;
import org.eclipse.om2m.sdt.home.types.DeviceType;
import org.eclipse.om2m.sdt.home.types.LevelType;
import org.eclipse.om2m.sdt.home.types.TasteStrength;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class SmarterKettle extends Kettle{
	
	
	private List<ServiceRegistration> registrations;
	private Domain domain; 
	private String serial = "SmarterKettle";
	private String IP;
	private int port;
	
	
	private SmarterKettleCommunication smarterKettle;
	
	private IntegerDataPoint cupsNumber;
	private BooleanDataPoint keepWarm;
	private TasteStrength strength;
	private IntegerDataPoint status;
	
	

	public SmarterKettle(String id, Domain domain, String ip , int port) {
		
		super(id, id, domain);
		this.domain = domain;
		this.serial = id;
		this.IP = ip;
		this.port = port;
		
		smarterKettle = new SmarterKettleCommunication(ip, port);
		
		System.out.println("TUTAJ HALO: " + DeviceType.deviceKettle);
		
		
		addBinarySwitch();
		addFaultDetection();
		addTemperature();
		
		setDeviceManufacturer("Smarter");
		setDeviceName("SmarterKettle 2.0");
	
		
		
		
	}
	
	
	public void register(BundleContext context) {
		registrations = Utils.register(this, context);
	}
	
	
	
//*********************BinarySwitch*********************	
	
	private void addBinarySwitch(){
		
		

		BinarySwitch binarySwitch = new BinarySwitch("BinarySwitch" + getId(), domain, new BooleanDataPoint("powerState"){

			@Override
			protected Boolean doGetValue() throws DataPointException {
				return smarterKettle.kettleStatus.isBoiling();
			}
			
			@Override
			protected void doSetValue(Boolean v)throws DataPointException{
				if(!smarterKettle.kettleStatus.isBoiling()){
					smarterKettle.startKettle(smarterKettle.kettleStatus.getTargetTemperature());
					System.out.println("Włączanie");
				}
					
				else{
					smarterKettle.stopKettle();	
					System.out.println("Wyłączanie");
				}
									
			}			
		});
		
		binarySwitch.setToggle(new Toggle("toggle"){

			@Override
			protected void doToggle() throws ActionException {
				
				smarterKettle.checkStatus();
				
				System.out.println("Uruchomiony toggle");
				if(!smarterKettle.kettleStatus.isBoiling()){
					smarterKettle.startKettle();
					System.out.println("Włączanie");
				}
					
				else{
					smarterKettle.stopKettle();	
					System.out.println("Wyłączanie");
				}
								
			}			
		});
		
		
		addModule(binarySwitch);
	}
//*********************BinarySwitch*********************
	
//***************Fault Detection Module*****************

	
	private void addFaultDetection() {
		FaultDetection faultDetection = new FaultDetection("FaultDetection_" + getId(), domain,
				new BooleanDataPoint("status") {
			@Override
			public Boolean doGetValue() throws DataPointException {
				smarterKettle.checkStatus();
				return smarterKettle.getFaultDetection(); 
			}
		}, new IntegerDataPoint("code") {
			
			@Override
			protected Integer doGetValue() throws DataPointException {
				return smarterKettle.getCode();
			}	
		}, new StringDataPoint("description") {
			
			@Override
			protected String doGetValue() throws DataPointException {
				return smarterKettle.getDescription();
			}
		});
		
		
		
		addModule(faultDetection);
	}
	
	
//***************Fault Detection Module*****************
	
//*****************Temperature Module*******************
	
	
	private void addTemperature(){
		System.out.println("Dodawanie modułu temperatury...");
		Temperature temperature = new Temperature("Temperature_" + getId(), domain, new FloatDataPoint("currentTemperature") {
			
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float) smarterKettle.kettleStatus.getCurrentTemperature();
			}
			
			
			
		}, new FloatDataPoint("targetTemperature") {
			
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float)smarterKettle.kettleStatus.getTargetTemperature();
			}
			
			@Override
			protected void doSetValue(Float b) throws DataPointException {
				
				smarterKettle.kettleStatus.setTargetTemperature(Math.round(b));
			}
					
			
		}, new StringDataPoint("unit") {
			
			@Override
			protected String doGetValue() throws DataPointException {
				return null;
			}
		}, new FloatDataPoint("minValue") {
			
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float)smarterKettle.kettleStatus.getMinTemperature();
			}
			
			
		}, new FloatDataPoint("maxValue") {
			
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float)smarterKettle.kettleStatus.getMaxTemperature();
			}
		}, new FloatDataPoint("stepValue") {
			
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float)smarterKettle.kettleStatus.getStepTemperature();
			}
		});
		
		
		
		addModule(temperature);
	}
	
//*****************Temperature Module*******************


}

	
	

