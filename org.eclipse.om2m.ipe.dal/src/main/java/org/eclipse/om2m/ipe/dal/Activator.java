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

package org.eclipse.om2m.ipe.dal;

import java.awt.EventQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.eclipse.om2m.persistence.service.DBService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Manages the starting and stopping of the bundle.
 */
public class Activator implements BundleActivator {
	
	/** Logger */
	private static Log LOGGER = LogFactory.getLog(Activator.class);
	/** Current bundle context */
	private static BundleContext context;
	/** Service tracker */
	private ServiceTracker<Object, Object> cseServiceTracker;
	private ServiceTracker<Object, Object> dbServiceTracker;

	/** CSE and DB service*/
	private static CseService cse;
	private static DBService dbs;

	/** IPE service object and registration*/
	private ServiceRegistration<?> ipeReg;
	private InterworkingServiceImpl ipe;

	public void start(BundleContext context) throws Exception {
		
		LOGGER.info("Start DAL interworking bundle...");
		Activator.context = context;

		// start service tracker to track CSE and DB service
		cseServiceTracker = new ServiceTracker<Object, Object>(context,
				CseService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference,
					Object service) {
				LOGGER.info("CseService removed");
				Activator.cse = null;
			}

			public Object addingService(ServiceReference<Object> reference) {
				LOGGER.info("CseService discovered");
				Activator.cse = (CseService) this.context.getService(reference);
				if (ipe == null) {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							ipe = new InterworkingServiceImpl();
							ipe.activate();

							LOGGER.info("Register DAL InterworkingService ...");
							ipeReg = context.registerService(
									InterworkingService.class.getName(), ipe, null);
							LOGGER.info("DAL InterworkingService is registered.");
						}
					});
				}
				return Activator.cse;
			}
		};
		cseServiceTracker.open();

		dbServiceTracker = new ServiceTracker<Object, Object>(context,
				DBService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference,
					Object service) {
				LOGGER.info("DBService removed");
				Activator.dbs = null;
			}

			public Object addingService(ServiceReference<Object> reference) {
				LOGGER.info("DBService discovered");
				Activator.dbs = (DBService) this.context.getService(reference);
				return Activator.dbs;
			}
		};
		dbServiceTracker.open();
	}

	public void stop(BundleContext context) throws Exception {
		LOGGER.info("Stop DAL interworking bundle...");

		// unregister the IPE service
		if (ipe != null) {
			ipe.deactivate();
		}

		if (ipeReg != null) {
			ipeReg.unregister();
		}
	}

	/**
	 * Return the bundle context of current bundle.
	 * 
	 * @param
	 * @return BundleContext
	 */
	public static BundleContext getContext() {
		return Activator.context;
	}

	/**
	 * Return the CSE service object.
	 * 
	 * @param
	 * @return CseService
	 */
	public static CseService getCseService() {
		return Activator.cse;
	}

	/**
	 * Return the DB service object.
	 * 
	 * @param
	 * @return DBService
	 */
	public static DBService getDBService() {
		return Activator.dbs;
	}

}
