/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.datamapping.service.DataMapperService;
import org.eclipse.om2m.flexcontainer.service.FlexContainerService;
import org.eclipse.om2m.sdt.Device;
import org.eclipse.om2m.sdt.events.SDTEventListener;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Activator implements BundleActivator, ManagedService {

	private static final String CSE_ID_TO_BE_ANNOUNCED = "cse.id.to.be.announced";
	private static final String CSE_NAME_TO_BE_ANNOUNCED = "cse.name.to.be.announced";
	private static final String ANNOUNCEMENT_ENABLED = "announcement.enabled";
	private static final String IPE_UNDER_ANNOUNCED_RESOURCE = "ipe.under.announced.resource";
	private static final String SDT_IPE = "sdt.ipe";
	private static final String PROP_PROTOCOL = "propProtocol";
	private static final String CLOUD_PROTOCOL = "Cloud.";

	private String cseIdToBeAnnounced;
	private String cseNameToBeAnnounced;
	private boolean ipeUnderAnnouncedResource;
	private ServiceRegistration<?> serviceRegistration;
	private boolean isSDTIPEStarted = false;

	private ServiceTracker cseServiceTracker;
	private ServiceTracker deviceServiceTracker;
	private ServiceTracker dataMapperServiceTracker;

	private SDTIpeApplication sdtIPEApplication;
	private CseService cseService;

	private static DataMapperService dataMapperService;
	private static BundleContext bundleContext;
	private static Object sync = new Object();

	private static Log logger = LogFactory.getLog(Activator.class);

	@Override
	public void start(final BundleContext context) throws Exception {
		bundleContext = context;
		logger.info("start SDT IPE");

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
					// a CSE Service has been previously caught.
					// No need to use a second instance !
					return null;
				}
				// at this point, we are sure this is the firstly detected CSE Service.
				cseService = (CseService) bundleContext.getService(reference);
				if (isSDTIPEStarted)
					startSDTIpe();
				return cseService;
			}
		});
		cseServiceTracker.open();

		// register this activator as a managed service
		try {
			ServiceReference ref = bundleContext.getServiceReference(ConfigurationAdmin.class.getName());
			if ((ref == null) || (bundleContext.getService(ref) == null)) {
				// No config admin. Start with default values: no announcement
				logger.info("Manage default properties");
				cseIdToBeAnnounced = null;
				cseNameToBeAnnounced = null;
				ipeUnderAnnouncedResource = false;
				startSDTIpe();
			} else {
				logger.info("Manage configuration properties");
				Dictionary properties = new Hashtable<>();
				properties.put(org.osgi.framework.Constants.SERVICE_PID, SDT_IPE);
				serviceRegistration = bundleContext.registerService(ManagedService.class.getName(),
						this, properties);
			}
		} catch (Exception e) {
			logger.error("Error starting SDT IPE Activator", e);
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		logger.info("stop SDT IPE");
		try {
			stopSDTIPE();

			if (cseServiceTracker != null) {
				// stop CseServiceTracker
				cseServiceTracker.close();
				cseServiceTracker = null;
			}
			if (serviceRegistration != null) {
				serviceRegistration.unregister();
				serviceRegistration = null;
			}
			deviceServiceTracker = null;
			sdtIPEApplication = null;
			bundleContext = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Register Sdt Ipe Application.
	 * 
	 * This method may create SdtIpeApplication object and start the tracking of
	 * SDTDevice.
	 * 
	 * @throws Exception
	 */
	protected void registerSdtIpeApplication(String announceCseId,
			String cseName, boolean ipeUnder) throws Exception {
		sdtIPEApplication = new SDTIpeApplication(cseService, announceCseId, cseName, ipeUnder);
		sdtIPEApplication.publishSDTIPEApplication();
	}

	/**
	 * Unregister Sdt Ipe Application.
	 * 
	 * Stop SdtDevice tracking
	 */
	protected void unregisterSdtIpeApplication() {
		if (sdtIPEApplication != null) {
			sdtIPEApplication.deleteIpeApplicationEntity();
			sdtIPEApplication = null;
		}
	}

	private void startSDTIpe() {
		isSDTIPEStarted = true;
		if (cseService == null) {
			// Wait for CSEService!
			return;
		}
		// create and register SDTIpeApplication
		try {
			logger.info("Start IPE App " + cseIdToBeAnnounced + " / " + cseNameToBeAnnounced 
					+ " / " + ipeUnderAnnouncedResource);
			registerSdtIpeApplication(cseIdToBeAnnounced, cseNameToBeAnnounced,
					ipeUnderAnnouncedResource);
			startSDTDeviceTracking();
		} catch (Exception e) {
			logger.error("SdtIpeApplication oneM2M publishing failed", e);
			stopSDTIPE();
		}
	}

	private void stopSDTIPE() {
		if (isSDTIPEStarted) {
			stopSDTDeviceTracking();
			unregisterSdtIpeApplication();
		}
		isSDTIPEStarted = false;
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
			}
			@Override
			public Object addingService(ServiceReference reference) {
				String protocol = (String) reference.getProperty(PROP_PROTOCOL);
				logger.info("Found device, protocol " + protocol);
				if ((protocol != null) && protocol.startsWith(CLOUD_PROTOCOL)) {
					logger.info("Cloud device, ignore...");
				} else {
					Device device = (Device) bundleContext.getService(reference);
					if (sdtIPEApplication.addSDTDevice(device)) {
						return device;
					}
				}
				return null;
			}
		});
		deviceServiceTracker.open();
	}

	private void stopSDTDeviceTracking() {
		if (deviceServiceTracker != null) {
			deviceServiceTracker.close();
			deviceServiceTracker = null;
		}
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
		logger.info("registerFlexContainerService for path " + instance.getFlexContainerLocation());
		return bundleContext.registerService(FlexContainerService.class.getName(), instance, null);
	}

	public static ServiceRegistration registerSDTListener(SDTEventListener listener,
			Dictionary dictionary) {
		logger.info("registerSDTListener");
		return bundleContext.registerService(SDTEventListener.class.getName(),
				listener, dictionary);
	}

	@Override
	public void updated(Dictionary properties) throws ConfigurationException {
		logger.info("updated(properties=" + properties + ")");
		if (properties != null) {
			String propCseIdToBeAnnounced = (String) properties.get(CSE_ID_TO_BE_ANNOUNCED);
			String propCseNameToBeAnnounced = (String) properties.get(CSE_NAME_TO_BE_ANNOUNCED);
			Boolean propAnnouncementEnabled = Boolean.parseBoolean((String) properties.get(ANNOUNCEMENT_ENABLED));
			Boolean propIpeUnderAnnouncedResource = Boolean.parseBoolean((String) properties.get(IPE_UNDER_ANNOUNCED_RESOURCE));
			logger.info("updated(" + CSE_ID_TO_BE_ANNOUNCED + "=" + propCseIdToBeAnnounced + ")\n"
					+ "updated(" + CSE_NAME_TO_BE_ANNOUNCED + "=" + propCseNameToBeAnnounced + ")\n"
					+ "updated(" + ANNOUNCEMENT_ENABLED + "=" + propAnnouncementEnabled + ")\n"
					+ "updated(" + IPE_UNDER_ANNOUNCED_RESOURCE + "=" + propIpeUnderAnnouncedResource + ")");

			if (propAnnouncementEnabled == null) {
				logger.info("Undefined property announcement.enabled. Announcement disabled");
				cseIdToBeAnnounced = null;
				cseNameToBeAnnounced = null;
				ipeUnderAnnouncedResource = false;
				return;
			}
			boolean isValidConfiguration = false;

			if (propAnnouncementEnabled) {
				if ((propCseIdToBeAnnounced != null) && (propCseNameToBeAnnounced != null)) {
					// check if CSE is connected
					if (checkIfRemoteCSEExists(propCseIdToBeAnnounced,
							propCseNameToBeAnnounced)) {
						isValidConfiguration = true;
						cseIdToBeAnnounced = propCseIdToBeAnnounced;
						cseNameToBeAnnounced = propCseNameToBeAnnounced;
						ipeUnderAnnouncedResource = (propIpeUnderAnnouncedResource == null)
								? false : propIpeUnderAnnouncedResource;
					}
				} else {
					// no remote
					logger.info("no REMOTE CSE where to announce resource but announcement.enabled=true");
					isValidConfiguration = false;
				}
			} else {
				// announcement.enabled = false
				isValidConfiguration = true;
				cseIdToBeAnnounced = null;
				cseNameToBeAnnounced = null;
				ipeUnderAnnouncedResource = false;
			}

			if (isValidConfiguration) {
				// stop previous configuration
				stopSDTIPE();

				// start again with the new one
				startSDTIpe();
			}
		}
	}

	/**
	 * 
	 * @param remoteCseId
	 * @param remoteCseName
	 * @return
	 */
	private boolean checkIfRemoteCSEExists(final String remoteCseId, final String remoteCseName) {
		if (cseService == null) {
			return false;
		}
		ResponsePrimitive response = CseUtil.sendRetrieveRequest(cseService,
				"/" + remoteCseId + "/" + remoteCseName + "/" + Constants.CSE_NAME);
		return ResponseStatusCode.OK.equals(response.getResponseStatusCode());
	}

}
