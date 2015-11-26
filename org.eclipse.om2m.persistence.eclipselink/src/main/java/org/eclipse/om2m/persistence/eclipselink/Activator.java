/*******************************************************************************
 * Copyright (c) 2013-2015 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Thierry Monteil (Project co-founder) - Management and initial specification,
 *         conception and documentation.
 *     Mahdi Ben Alaya (Project co-founder) - Management and initial specification,
 *         conception, implementation, test and documentation.
 *     Khalil Drira - Management and initial specification.
 *     Guillaume Garzone - Initial specification, conception, implementation, test
 *         and documentation.
 *     François Aïssaoui - Initial specification, conception, implementation, test
 *         and documentation.
 *******************************************************************************/
package org.eclipse.om2m.persistence.eclipselink;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.persistence.eclipselink.internal.DBServiceJPAImpl;
import org.eclipse.om2m.persistence.service.DBService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	/** Logger */
	private static final Log LOGGER = LogFactory.getLog(Activator.class);

	private static BundleContext context;

	protected static BundleContext getContext() {
		return context;
	}

	/**
	 * Start the plugin
	 */
	public void start(BundleContext bundleContext) throws Exception {
		try {
			Activator.context = bundleContext;
			LOGGER.info("Starting Persistence JPA plugin...");
			DBServiceJPAImpl.getInstance().init();

			LOGGER.info("Registering Database (JPA-EL) Service");
			context.registerService(DBService.class.getName(),
					DBServiceJPAImpl.getInstance(), null);
		} catch (Exception e) {
			LOGGER.error("Error in activator", e);
		}
	}


	/**
	 * Stop the plugin
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		LOGGER.info("Stopping Persistence JPA plugin");
		// DBServiceJPAImpl.getInstance().close();
		LOGGER.info("Persistence JPA plugin stopped");
	}

}
