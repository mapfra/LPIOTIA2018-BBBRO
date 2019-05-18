/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.smartercoffee;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.HomeDomain;
import org.eclipse.om2m.sdt.home.driver.Logger;
import org.eclipse.om2m.sdt.home.types.TasteStrength;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.log.LogService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Activator implements ManagedService, BundleActivator {

	private static final String PID_VALUE = "smarter.coffee";

	static private final String PROTOCOL = "SmarterCoffeeMachine";

	static private final String ID = "SmarterCoffee";

	private static int idNum = 1;

	private Domain domain;

	private boolean activated;

	private BundleContext context;

	private ServiceRegistration serviceRegistration;

	public static Logger logger = new Logger(PROTOCOL);

	private SmarterCoffeeMachine sCoffee;

	private static final String IP = "coffee.machine.ip";

	private static final String PORT = "coffee.machine.port";

	public Activator() {
		System.out.println("Bundle coffee machine starting");
		logger.info("Activator");

		this.domain = new HomeDomain("Smarter Coffee Domain");
	}

	public void addCoffeeMachine(String ip, int port){
		sCoffee = new SmarterCoffeeMachine((Activator.ID + idNum++), domain, ip, port); 
		sCoffee.setProtocol(PROTOCOL);
		sCoffee.register(context);	
		System.out.println("test - addCoffe");
		//test();
	}

	public void test() {
		try{
			System.out.println("URUCHOMIONO TEST");

			//logger.debug("SENDING REQUEST TO SMARTER COFFEE");
			sCoffee.getBrewing().setCupsNumber(1);
			sCoffee.getBrewing().setStrength(TasteStrength.Values.zero);
			//sCoffee.getKeepWarm();
			sCoffee.getKeepWarm().setPowerState(true);
			//sCoffee.getGrinder().setUseGrinder(true);
			//sCoffee.getBrewing().setStatus(1); 

			System.out.println("settint true to powerState");
			sCoffee.getBrewingSwitch().setPowerState(true);

			if (sCoffee.getFaultDetection().getStatus()) {
				logger.debug("Fault description: " + sCoffee.getFaultDetection().getDescription() + " code: " + sCoffee.getFaultDetection().getCode());
				logger.debug("Water level in the tank: " + sCoffee.getWaterStatus().getLiquidLevel());		
			}
		} catch(DataPointException | AccessException e) {
			e.printStackTrace();
		}
	}

	public void setLog(final LogService logService) {
		logger.setLogService(logService);
	}

	public void unsetLog(final LogService logService) {
		logger.unsetLogService();
	}

	@Override
	public void updated(Dictionary properties) throws ConfigurationException {
		String ip = null;
		int port = 0;
		if (properties == null) {
			logger.info("No found properties... ");
		} else {
			try {
				ip = (String) properties.get(IP);
				port = (int) Integer.parseInt(properties.get(PORT).toString());
			} catch (Exception ignored) {
				ignored.printStackTrace();
			}
			System.out.println("zaaaaaaaaraz: addCoffeMachine w UPDATED");
			addCoffeeMachine(ip, port);
		}
		//test();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		this.context = context;
		logger.info("Activating");
		activated = true;
		logger.info("Activated " + domain.prettyPrint());
		Dictionary properties = new Hashtable();
		properties.put(Constants.SERVICE_PID, PID_VALUE);
		serviceRegistration = context.registerService(ManagedService.class.getName(), this, properties );

		//test();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		activated = false;
		idNum = 1;
		sCoffee.unregister();

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

}
