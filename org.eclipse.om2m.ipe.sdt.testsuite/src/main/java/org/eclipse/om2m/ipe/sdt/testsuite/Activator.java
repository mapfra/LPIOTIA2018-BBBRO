/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.testsuite;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.sdt.testsuite.subscription.SubscriptionTestSuite;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator {
	
	public static final String SDT_IPE_LOCATION = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/SDT_IPE";
	
	private BundleContext bundleContext;
	
	private CseService cseService;
	
	private ServiceTracker cseServiceTracker;
	
	private SubscriptionTestSuite subscriptionTestSuite;
	

	@Override
	public void start(final BundleContext context) throws Exception {
		
		bundleContext = context;

		cseServiceTracker = new ServiceTracker(context, CseService.class.getName(), new ServiceTrackerCustomizer() {
			
			@Override
			public void removedService(ServiceReference reference, Object service) {
				cseService = null;
			}
			
			@Override
			public void modifiedService(ServiceReference reference, Object service) {
			}
			
			@Override
			public Object addingService(ServiceReference reference) {
				cseService = (CseService) context.getService(reference);
				startTest();
				return cseService;
			}
		});
		cseServiceTracker.open();
		
		// start tests
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		
		// stop tests
		if (subscriptionTestSuite != null) {
			subscriptionTestSuite.stopTests();
			
			Thread.sleep(5000);
			
			subscriptionTestSuite.printTestReports();
		}
	}
	
	public void startTest() {
		try {
//			DeviceDiscoveryTestSuite deviceDiscoveryTestSuite = new DeviceDiscoveryTestSuite(bundleContext, cseService);
			
//			SDTModuleTestSuite moduleTestSuite = new SDTModuleTestSuite(bundleContext, cseService);
			
			subscriptionTestSuite = new SubscriptionTestSuite(bundleContext, cseService);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
