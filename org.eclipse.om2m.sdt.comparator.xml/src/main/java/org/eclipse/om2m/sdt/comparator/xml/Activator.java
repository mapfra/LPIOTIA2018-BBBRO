/*******************************************************************************
 * Copyright (c) 2013-2016 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Thierry Monteil : Project manager, technical co-manager
 *     Mahdi Ben Alaya : Technical co-manager
 *     Samir Medjiah : Technical co-manager
 *     Khalil Drira : Strategy expert
 *     Guillaume Garzone : Developer
 *     François Aïssaoui : Developer
 *
 * New contributors :
 *******************************************************************************/
package org.eclipse.om2m.sdt.comparator.xml;

import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Manages the starting and stopping of the bundle.
 * 
 */
public class Activator implements BundleActivator {
	/** logger */
	private static Log LOGGER = LogFactory.getLog(Activator.class);
	public static String CONTEXT_URI = "comparator";
	public static String RESOURCES_URI = "webpage";

	public static String SEP = "/";

	/** HTTP service tracker */
	private ServiceTracker<Object, Object> httpServiceTracker;

	private WelcomeServlet welcomeServlet;

	@Override
	public void start(BundleContext context) throws Exception {

		welcomeServlet = new WelcomeServlet();

		httpServiceTracker = new ServiceTracker<Object, Object>(context, HttpService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				LOGGER.info("HttpService removed");
				try {
					LOGGER.info("Unregister " + SEP + CONTEXT_URI + SEP + RESOURCES_URI + " http context");
					((HttpService) service).unregister(SEP + CONTEXT_URI + SEP + RESOURCES_URI);
					LOGGER.info("Unregister " + SEP + CONTEXT_URI + " http context");
					((HttpService) service).unregister(SEP + CONTEXT_URI);
				} catch (IllegalArgumentException e) {
					LOGGER.error("Error unregistring webapp servlet", e);
				}
			}

			public Object addingService(ServiceReference<Object> reference) {
				LOGGER.info("HttpService discovered");
				HttpService httpService = (HttpService) context.getService(reference);
				try {
					LOGGER.info("Register " + SEP + CONTEXT_URI + " http context");
					httpService.registerServlet(SEP + CONTEXT_URI, welcomeServlet, null, null);
					httpService.registerResources(SEP + CONTEXT_URI + SEP + RESOURCES_URI, "/webapps", null);

				} catch (Exception e) {
					LOGGER.error("Error registring webapp servlet", e);
				}
				return httpService;
			}
		};
		httpServiceTracker.open();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}
}
