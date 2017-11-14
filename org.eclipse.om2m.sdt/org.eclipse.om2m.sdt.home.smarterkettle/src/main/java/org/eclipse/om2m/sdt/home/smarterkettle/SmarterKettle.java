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
import org.eclipse.om2m.sdt.home.types.ModuleType;
import org.eclipse.om2m.sdt.home.types.TasteStrength;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class SmarterKettle extends Kettle {
	
	@SuppressWarnings("rawtypes")
	private List<ServiceRegistration> registrations;
	private Domain domain; 
	
	private SmarterKettleCommunication smarterKettle;
	
	private IntegerDataPoint cupsNumber;
	private BooleanDataPoint keepWarm;
	private TasteStrength strength;
	private IntegerDataPoint status;
	

	public SmarterKettle(String id, Domain domain, String ip , int port) {
		super(id, id, domain);
		this.domain = domain;
		smarterKettle = new SmarterKettleCommunication(ip, port);
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
	
	private void addBinarySwitch() {
		BinarySwitch binarySwitch = new BinarySwitch("BinarySwitch" + getId(), 
				domain, new BooleanDataPoint(DatapointType.powerState) {
			@Override
			protected Boolean doGetValue() throws DataPointException {
				return smarterKettle.getStatus().isBoiling();
			}
			@Override
			protected void doSetValue(Boolean v)throws DataPointException {
				if (smarterKettle.getStatus().isBoiling()) {
					smarterKettle.stopKettle();	
				} else {
					smarterKettle.startKettle(smarterKettle.getStatus().getTargetTemperature());
				}
			}			
		});
		
		binarySwitch.setToggle(new Toggle("toggle") {
			@Override
			protected void doToggle() throws ActionException {
				smarterKettle.checkStatus();
				if (smarterKettle.getStatus().isBoiling()) {
					smarterKettle.stopKettle();	
				} else {
					smarterKettle.startKettle();
				}
			}			
		});
		addModule(binarySwitch);
	}
	
//***************Fault Detection Module*****************

	private void addFaultDetection() {
		FaultDetection faultDetection = new FaultDetection(ModuleType.faultDetection + getId(), domain,
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
	
//*****************Temperature Module*******************
	
	private void addTemperature(){
		Temperature temperature = new Temperature("Temperature_" + getId(), domain, 
				new FloatDataPoint(DatapointType.currentTemperature) {
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float) smarterKettle.getStatus().getCurrentTemperature();
			}
		});
		temperature.setTargetTemperature(new FloatDataPoint(DatapointType.targetTemperature) {
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float)smarterKettle.getStatus().getTargetTemperature();
			}
			@Override
			protected void doSetValue(Float b) throws DataPointException {
				smarterKettle.getStatus().setTargetTemperature(Math.round(b));
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
				return (float)smarterKettle.getStatus().getMinTemperature();
			}
		});
		temperature.setMaxValue(new FloatDataPoint(DatapointType.maxValue) {
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float)smarterKettle.getStatus().getMaxTemperature();
			}
		});
		temperature.setStepValue(new FloatDataPoint(DatapointType.stepValue) {
			@Override
			protected Float doGetValue() throws DataPointException {
				return (float)smarterKettle.getStatus().getStepTemperature();
			}
		});
		addModule(temperature);
	}

}
