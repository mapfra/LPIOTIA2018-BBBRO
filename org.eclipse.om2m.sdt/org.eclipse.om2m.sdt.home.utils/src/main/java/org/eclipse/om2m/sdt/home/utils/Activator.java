/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.sdt.home.utils.api.ISDTDiscovery;
import org.eclipse.om2m.sdt.home.utils.api.ISDTDiscoveryFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Activator implements BundleActivator {

	static private BundleContext context;

	public static final Log LOGGER = LogFactory.getLog(Activator.class);

	private ServiceTracker cseServiceTracker;
    private ServiceRegistration registeredFactory;

	@Override
	public void start(BundleContext ctxt) throws Exception {
		try {
			context = ctxt;
			initCseServiceTracker();
		} catch (Exception e) {
			LOGGER.error("Error starting cloud connector", e);
		}
	}
	
	private void initCseServiceTracker() {
		cseServiceTracker = new ServiceTracker(context, CseService.class.getName(), null) {
			public void removedService(ServiceReference reference, Object service) {
				LOGGER.info("CSEService removed");
        		registeredFactory.unregister();
        		registeredFactory = null;
            }
            public Object addingService(ServiceReference reference) {
            	LOGGER.info("CSE Service found");
            	CseService cseService = (CseService) this.context.getService(reference); 
            	SDTDiscovery.initCseService(cseService);
            	registeredFactory = this.context.registerService(ISDTDiscoveryFactory.class.getName(),
            			new ISDTDiscoveryFactory() {
							@Override
							public ISDTDiscovery getSDTDiscovery(String mnName) throws Exception {
								return new SDTDiscovery(mnName);
							}
						}, 
						null);
                return cseService;
            }
        };
        cseServiceTracker.open();
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		cseServiceTracker.close();
		registeredFactory.unregister();
		registeredFactory = null;
		context = null;
	}

}
