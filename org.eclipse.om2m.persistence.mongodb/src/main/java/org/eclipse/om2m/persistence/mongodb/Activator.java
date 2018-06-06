/*******************************************************************************
 * Copyright (c) 2014 - 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    BAREAU Cyrille <cyrille.bareau@orange.com>, 
 *    BONNARDEL Gregory <gbonnardel.ext@orange.com>, 
 *******************************************************************************/
package org.eclipse.om2m.persistence.mongodb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.persistence.service.DBService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private static final Log LOGGER = LogFactory.getLog(Activator.class);
	
	private BundleContext context;
	private ServiceRegistration serviceRegistration;
	
	@Override
	public void start(BundleContext context) throws Exception {
		LOGGER.info("start mongoDb bundle");
		this.context = context;
		DBServiceImpl.getInstance().init();
		registerPersistence();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		LOGGER.info("stop mongoDb bundle");
		unregisterPersistence();
	}
	
	private void registerPersistence() {
		serviceRegistration = context.registerService(DBService.class, DBServiceImpl.getInstance(), null);
	}
	
	private void unregisterPersistence() {
		if (serviceRegistration != null) {
			serviceRegistration.unregister();
			serviceRegistration = null;
		}
	}

}
