/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.smarterkettle;

import java.util.List;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.FloatDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.exceptions.ActionException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.actions.Toggle;
import org.eclipse.om2m.sdt.home.devices.Kettle;
import org.eclipse.om2m.sdt.home.driver.Utils;
import org.eclipse.om2m.sdt.home.modules.BinarySwitch;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.modules.Temperature;
import org.eclipse.om2m.sdt.home.smarterkettle.communication.SmarterKettleCommunication;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.DeviceType;
import org.eclipse.om2m.sdt.home.types.TasteStrength;
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
		
		

		BinarySwitch binarySwitch = new BinarySwitch("BinarySwitch" + getId(), domain, new BooleanDataPoint(DatapointType.powerState){

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
				new BooleanDataPoint(DatapointType.status) {
			@Override
			public Boolean doGetValue() throws DataPointException {
				smarterKettle.checkStatus();
				return smarterKettle.getFaultDetection(); 
			}
		});
		
		faultDetection.setCode(new IntegerDataPoint(DatapointType.code) {
			
			@Override
			protected Integer doGetValue() throws DataPointException {
				return smarterKettle.getCode();
			}	
		});
		
		faultDetection.setDescription(new StringDataPoint(DatapointType.description) {
			
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
		Temperature temperature = new Temperature("Temperature_" + getId(), domain, new FloatDataPoint(DatapointType.currentTemperature) {
			
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float) smarterKettle.kettleStatus.getCurrentTemperature();
			}
			
			
			
		});
		
		temperature.setTargetTemperature(new FloatDataPoint(DatapointType.targetTemperature) {
			
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float)smarterKettle.kettleStatus.getTargetTemperature();
			}
			
			@Override
			protected void doSetValue(Float b) throws DataPointException {
				
				smarterKettle.kettleStatus.setTargetTemperature(Math.round(b));
			}
					
			
		});
		
		temperature.setUnit(new StringDataPoint(DatapointType.unit) {
			
			@Override
			protected String doGetValue() throws DataPointException {
				return null;
			}
		});
		
		temperature.setMinValue(new FloatDataPoint(DatapointType.minValue) {
			
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float)smarterKettle.kettleStatus.getMinTemperature();
			}
			
			
		});
		
		temperature.setMaxValue(new FloatDataPoint(DatapointType.maxValue) {
			
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float)smarterKettle.kettleStatus.getMaxTemperature();
			}
		});
		
		temperature.setStepValue(new FloatDataPoint(DatapointType.stepValue) {
			
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float)smarterKettle.kettleStatus.getStepTemperature();
			}
		});
		
		
		
		addModule(temperature);
	}
	
//*****************Temperature Module*******************


}

	
	

