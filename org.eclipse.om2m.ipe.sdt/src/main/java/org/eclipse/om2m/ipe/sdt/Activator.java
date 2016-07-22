/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt;

import java.util.Dictionary;

import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.datamapping.service.DataMapperService;
import org.eclipse.om2m.flexcontainer.service.FlexContainerService;
import org.onem2m.sdt.Device;
import org.onem2m.sdt.events.SDTEventListener;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator {

	private ServiceTracker cseServiceTracker = null;

	private ServiceTracker deviceServiceTracker = null;

	private ServiceTracker logServiceTracker = null;

	private ServiceTracker dataMapperServiceTracker = null;

	private static DataMapperService dataMapperService;

	private SDTIpeApplication sdtIPEApplication = null;

	private CseService cseService = null;

	private static BundleContext bundleContext;

	private static Object sync = new Object();

	@Override
	public void start(final BundleContext context) throws Exception {
		bundleContext = context;
		Logger.getInstance().logInfo(Activator.class, "start SDT IPE");

		dataMapperServiceTracker = new ServiceTracker(bundleContext, DataMapperService.class.getName(),
				new ServiceTrackerCustomizer() {

					@Override
					public void removedService(ServiceReference reference, Object service) {
						setDataMapper(null);
					}

					@Override
					public void modifiedService(ServiceReference reference, Object service) {
					}

					@Override
					public Object addingService(ServiceReference reference) {
						if (getDataMapper() == null) {
							DataMapperService dms = (DataMapperService) bundleContext.getService(reference);
							if (MimeMediaType.XML.equals(dms.getServiceDataType())) {
								setDataMapper(dms);
								return dataMapperService;
							}
						}
						return null;
					}
				});
		dataMapperServiceTracker.open();

		logServiceTracker = new ServiceTracker(bundleContext, LogService.class.getName(),
				new ServiceTrackerCustomizer() {

					@Override
					public void removedService(ServiceReference reference, Object service) {
						Logger.getInstance().setLogService(null);
					}

					@Override
					public void modifiedService(ServiceReference reference, Object service) {
					}

					@Override
					public Object addingService(ServiceReference reference) {
						LogService logService = (LogService) bundleContext.getService(reference);
						Logger.getInstance().setLogService(logService);

						return logService;
					}
				});
		// logServiceTracker.open();

		cseServiceTracker = new ServiceTracker(bundleContext, CseService.class.getName(),
				new ServiceTrackerCustomizer() {

					@Override
					public void removedService(ServiceReference reference, Object service) {
						// a single CSEService

						// unregister Sdt IPE application
						unregisterSdtIpeApplication();

						cseService = null;

					}

					@Override
					public void modifiedService(ServiceReference reference, Object service) {
						// nothing to do
					}

					@Override
					public Object addingService(ServiceReference reference) {
						if (cseService != null) {
							// a CSE Service has been previously catch. No need
							// to use a
							// second instance !
							return null;
						}
						// at this point, we are sure this is the firstly
						// detected CSE
						// Service.
						cseService = (CseService) bundleContext.getService(reference);

						// create and register SDTIpeApplication
						try {
							registerSdtIpeApplication();
						} catch (Exception e) {
							Logger.getInstance().logError(Activator.class, "SdtIpeApplication oneM2M publishing failed",
									e);
							cseService = null;
							return null;
						}

						startSDTDeviceTracking();

						return cseService;
					}
				});
		cseServiceTracker.open();

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		Logger.getInstance().logInfo(Activator.class, "stop SDT IPE");

		// stop CseServiceTracker
		cseServiceTracker.close();
		cseServiceTracker = null;

		// stop LogServiceTracker
		logServiceTracker.close();
		logServiceTracker = null;

		deviceServiceTracker = null;
		sdtIPEApplication = null;
		bundleContext = null;

	}

	/**
	 * This method returns the current CSEService in a secured way.
	 * 
	 * 
	 * @return the current CSEService
	 * @throws NullPointerException
	 *             in case of no CSEService available
	 */
	protected CseService getCseService() throws NullPointerException {
		CseService toBeReturned = null;
		synchronized (this) {
			if (cseService != null) {
				toBeReturned = cseService;
			} else {
				throw new NullPointerException();
			}

		}

		return toBeReturned;
	}

	/**
	 * Register Sdt Ipe Application.
	 * 
	 * This method may create SdtIpeApplication object and start the tracking of
	 * SDTDevice.
	 * 
	 * @throws Exception
	 */
	protected void registerSdtIpeApplication() throws Exception {

		if (sdtIPEApplication == null) {
			sdtIPEApplication = new SDTIpeApplication(cseService);
			sdtIPEApplication.publishSDTIPEApplication();
		}

	}

	/**
	 * Unregister Sdt Ipe Application.
	 * 
	 * Stop SdtDevice tracking
	 */
	protected void unregisterSdtIpeApplication() {

		stopSDTDeviceTracking();

		sdtIPEApplication.deleteIpeApplicationEntity();

	}

	/**
	 * Start SDTDevice tracking.
	 * 
	 * @param pCseService
	 */
	private void startSDTDeviceTracking() {
		deviceServiceTracker = new ServiceTracker(bundleContext, Device.class.getName(),
				new ServiceTrackerCustomizer() {

					@Override
					public void removedService(ServiceReference reference, Object service) {
						sdtIPEApplication.removeSDTDevice((Device) service);

					}

					@Override
					public void modifiedService(ServiceReference reference, Object service) {
						// TODO Auto-generated method stub

					}

					@Override
					public Object addingService(ServiceReference reference) {
						Device device = (Device) bundleContext.getService(reference);

						if (sdtIPEApplication.addSDTDevice(device)) {
							return device;
						}

						return null;
					}
				});
		deviceServiceTracker.open();
	}

	private void stopSDTDeviceTracking() {
		deviceServiceTracker.close();

	}

	protected static void setDataMapper(DataMapperService dms) {
		synchronized (sync) {
			dataMapperService = dms;
		}
	}

	protected static DataMapperService getDataMapper() {
		DataMapperService dms = null;
		synchronized (sync) {
			dms = dataMapperService;
		}
		return dms;
	}

	public static ServiceRegistration registerFlexContainerService(FlexContainerService instance) {
		Logger.getInstance().logDebug(Activator.class,
				"registerFlexContainerService for path " + instance.getFlexContainerLocation());
		return bundleContext.registerService(FlexContainerService.class.getName(), instance, null);
	}

	public static ServiceRegistration registerSDTListener(SDTEventListener listener, Dictionary dictionary) {
		Logger.getInstance().logDebug(Activator.class, "registerSDTListener");
		return bundleContext.registerService(SDTEventListener.class.getName(), listener, dictionary);
	}

}
