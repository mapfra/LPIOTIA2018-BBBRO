/*******************************************************************************
 * Copyright (c) 2017 Huawei Technologies Co., Ltd (http://www.huawei.com)
 * Huawei Base, Bantian,Longgang District ,Shenzhen ,Guangdong ,China
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Seven Ganlu : Developer
 *
 * New contributors :
 *******************************************************************************/

package org.eclipse.om2m.dal.driver.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventAdmin;

import org.eclipse.om2m.dal.driver.sample.fan.FanDevice;

/**
 * Manages the starting and stopping of the bundle.
 */
public class Activator implements BundleActivator {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(Activator.class);
	/** Current bundle context */
	private static BundleContext context = null;
	/** Event admin service */
	private static EventAdmin eventAdm = null;

	public void start(BundleContext context) throws Exception {
		LOGGER.info("Start DAL driver sample bundle...");
		Activator.context = context;
		
		// Get the admin service
		ServiceReference<?> serviceRef = context
				.getServiceReference(EventAdmin.class.getName());
		if (serviceRef == null) {
			throw new Exception(
					"Failed to obtain EventAdmin service reference!");
		}

		eventAdm = (EventAdmin) context.getService(serviceRef);
		if (eventAdm == null) {
			throw new Exception("Failed to obtain EventAdmin service object!");
		}

		// Create a fan device
		FanDevice.init();
	}

	public void stop(BundleContext context) throws Exception {
		LOGGER.info("Stop DAL driver sample bundle...");
		FanDevice.stop();
		context = null;
	}

	/**
	 * Return the bundle context of current bundle.
	 * @param 
	 * @return BundleContext
	 */
	public static BundleContext getContext() {
		return context;
	}

	/**
	 * Return the event admin service.
	 * @param 
	 * @return EventAdmin
	 */
	public static EventAdmin getEventAdmin() {
		return eventAdm;
	}
}
