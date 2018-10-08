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
import org.eclipse.om2m.sdt.home.driver.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.log.LogService;

public class Activator implements ManagedService, BundleActivator {
	
	static public Logger LOGGER;
	
	private BundleContext context;
	private boolean activated;
	private Domain domain;
	private static final String ID = "SmarterKettle";
	
	private SmarterKettle sKettle;
	private static int idNum = 1;
	
	
	public Activator() {
		this.domain = new HomeDomain("Smarter Kettle Domain");
		LOGGER = new Logger("SMARTER");
	}
	
	private void addKettleMachine(String ip, int port){
		LOGGER.info("AddKettleMachine");
		LOGGER.info(ip + ":" + port);
		sKettle = new SmarterKettle(ID + idNum++, domain, ip, port);
		sKettle.register(context);
	}

	@Override
	public void start(BundleContext ctxt) throws Exception {
		try {
			LOGGER = new Logger("SMARTER");
			LOGGER.setLogService((LogService) ctxt.getService(
					ctxt.getServiceReference(LogService.class.getName())));
		} catch (Exception ignored) {
		}
		LOGGER.info("Bundle kettle machine starting");
		this.context = ctxt;
		activated = true;
		
		addKettleMachine("10.0.1.27", 2081);
		//test();
	}
	
	public void test() throws ActionException, AccessException, InterruptedException, DataPointException{
		LOGGER.info("----Test----");
		if (sKettle.getFaultDetection().getStatus()) {
			LOGGER.info("kettle OK");
		} else {
			LOGGER.info("Error code: " + sKettle.getFaultDetection().getCode() +
					" / " + sKettle.getFaultDetection().getDescription());
		}
		LOGGER.info("----Test module temperature---");
		
		sKettle.getTemperature().setTargetTemperature(50);
		sKettle.getTemperature().setTargetTemperature(50);
		
		LOGGER.info("Current: " + sKettle.getTemperature().getCurrentTemperature());
		LOGGER.info("Target: " + sKettle.getTemperature().getTargetTemperature());
		LOGGER.info("Max: " + sKettle.getTemperature().getMaxValue());
		LOGGER.info("Min: " + sKettle.getTemperature().getMinValue());
		LOGGER.info("Step: " + sKettle.getTemperature().getStepValue());
		
		sKettle.getBinarySwitch().toggle();
		
		LOGGER.info("Boiling: " + sKettle.getBinarySwitch().getPowerState());
	}

	@Override
	public void updated(Dictionary properties) throws ConfigurationException {
		LOGGER.info("Uptaded");
		addKettleMachine("10.0.1.28", 2081);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		activated = false;
		if (sKettle != null)
			sKettle.unregister();
	}

}
