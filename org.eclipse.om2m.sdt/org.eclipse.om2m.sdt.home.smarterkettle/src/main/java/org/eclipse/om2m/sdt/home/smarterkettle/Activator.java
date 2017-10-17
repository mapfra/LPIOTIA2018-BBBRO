/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.smarterkettle;

import java.util.Dictionary;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.ActionException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.HomeDomain;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

public class Activator implements ManagedService, BundleActivator {
	
	private BundleContext context;
	private Boolean activated;
	private Domain domain;
	private static final String ID = "SmarterKettle";
	
	private SmarterKettle sKettle;
	private static int idNum = 1;
	
	
	public Activator() {
		
		this.domain = new HomeDomain("Smarter Kettle Domain");
	}
	
	private void addKettleMachine(String ip, int port){
		System.out.println("AddKettleMachine");
		System.out.println(ip + ":" + port);
		
		
		sKettle = new SmarterKettle(ID + idNum++, domain, ip, port);
		
		sKettle.register(context);
		
	}

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Bundle kettle machine starting");
		this.context = context;
		activated = true;
		
		addKettleMachine("10.0.1.27", 2081);
		//test();
	}
	
	public void test() throws ActionException, AccessException, InterruptedException, DataPointException{
		System.out.println("----Test----");
		
		if(!sKettle.getFaultDetection().getStatus()){			
			System.out.println("KOD bledu: " + sKettle.getFaultDetection().getCode() + " Opis: " + sKettle.getFaultDetection().getDescription());
		}
		else{
			System.out.println("Brak błędów");
		}
		
		
		System.out.println("----Test modułu temperatury---");
		
		sKettle.getTemperature().setTargetTemperature(50);
		sKettle.getTemperature().setTargetTemperature(50);
		
		
		System.out.println("Current: " + sKettle.getTemperature().getCurrentTemperature());
		System.out.println("Target: " + sKettle.getTemperature().getTargetTemperature());
		System.out.println("Max: " + sKettle.getTemperature().getMaxValue());
		System.out.println("Min: " + sKettle.getTemperature().getMinValue());
		System.out.println("Step: " + sKettle.getTemperature().getStepValue());
		
		
		
		
		sKettle.getBoilingSwitch().toggle();
		
		
		System.out.println("Test modułu BOILING");
		System.out.println("Spradzenie statusu za pomocą modułu BOILING");
		//System.out.println("Czy gotuje: " + sKettle.getBoiling().getStatus());
		System.out.println("Czy goutje: " + sKettle.getBoilingSwitch().getPowerState());
		
		
		
		
	}


	@Override
	public void updated(Dictionary properties) throws ConfigurationException {
		System.out.println("Uptaded");
		addKettleMachine("10.0.1.28", 2081);
				
	}
	

	@Override
	public void stop(BundleContext context) throws Exception {
		activated = false;
	}
}
	
