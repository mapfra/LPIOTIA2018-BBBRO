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
import org.eclipse.om2m.core.service.RemoteCseService;
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
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Activator implements BundleActivator, ManagedService, EventHandler {

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
	private boolean hasToBeAnnounced;
	private ServiceRegistration<?> serviceRegistration;
	private boolean isSDTIPEStarted = false;

	private ServiceTracker cseServiceTracker;
	private ServiceTracker deviceServiceTracker;
	private ServiceTracker dataMapperServiceTracker;

	private SDTIpeApplication sdtIPEApplication;
	private CseService cseService;

	private static BundleContext bundleContext;
	private static Object sync = new Object();

	private static Log logger = LogFactory.getLog(Activator.class);

	@Override
	public void start(final BundleContext context) throws Exception {
		bundleContext = context;
		logger.info("start SDT IPE");

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
						// at this point, we are sure this is the firstly
						// detected CSE Service.
						cseService = (CseService) bundleContext.getService(reference);
						if (!isSDTIPEStarted) {
							startSDTIpe();
						}
						return cseService;
					}
				});
		cseServiceTracker.open();

		// register this activator as a managed service
//		try {
//			ServiceReference ref = bundleContext.getServiceReference(ConfigurationAdmin.class.getName());
//			if ((ref == null) || (bundleContext.getService(ref) == null)) {
//				// No config admin. Start with default values: no announcement
//				logger.info("Manage default properties");
//				cseIdToBeAnnounced = null;
//				cseNameToBeAnnounced = null;
//				ipeUnderAnnouncedResource = false;
//				startSDTIpe();
//			} else {
//				logger.info("Manage configuration properties");
//				
//			}
//		} catch (Exception e) {
//			logger.error("Error starting SDT IPE Activator", e);
//		}
		
		// 
		Dictionary properties = new Hashtable<>();
		properties.put(org.osgi.framework.Constants.SERVICE_PID, SDT_IPE);
		properties.put(EventConstants.EVENT_TOPIC, RemoteCseService.REMOTE_CSE_TOPIC);
		serviceRegistration = bundleContext.registerService(
				new String[] { ManagedService.class.getName(), EventHandler.class.getName() }, this,
				properties);
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
	protected void registerSdtIpeApplication(String announceCseId, String cseName, boolean ipeUnder) throws Exception {
		if (sdtIPEApplication != null) {
			// unregister a previous version
			unregisterSdtIpeApplication();
		}
		sdtIPEApplication = new SDTIpeApplication(cseService, announceCseId, cseName, ipeUnder, hasToBeAnnounced);
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
		synchronized (this) {
			isSDTIPEStarted = true;
			if (cseService == null) {
				// Wait for CSEService!
				return;
			}

			if (checkIfRemoteCSEExists(cseIdToBeAnnounced, cseNameToBeAnnounced)) {

				// create and register SDTIpeApplication
				try {
					logger.info("Start IPE App " + cseIdToBeAnnounced + " / " + cseNameToBeAnnounced + " / "
							+ ipeUnderAnnouncedResource);
					registerSdtIpeApplication(cseIdToBeAnnounced, cseNameToBeAnnounced, ipeUnderAnnouncedResource);
					startSDTDeviceTracking();
				} catch (Exception e) {
					logger.error("SdtIpeApplication oneM2M publishing failed", e);
					stopSDTIPE();
				}

			} else {
				logger.error("Remote CSE not available");
			}

		}
	}

	private void stopSDTIPE() {
		synchronized (this) {

			if (isSDTIPEStarted) {
				stopSDTDeviceTracking();
				unregisterSdtIpeApplication();
			}
			isSDTIPEStarted = false;

		}
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


	public static ServiceRegistration registerFlexContainerService(FlexContainerService instance) {
		logger.info("registerFlexContainerService for path " + instance.getFlexContainerLocation());
		return bundleContext.registerService(FlexContainerService.class.getName(), instance, null);
	}

	public static ServiceRegistration registerSDTListener(SDTEventListener listener, Dictionary dictionary) {
		logger.info("registerSDTListener");
		return bundleContext.registerService(SDTEventListener.class.getName(), listener, dictionary);
	}

	@Override
	public void updated(Dictionary properties) throws ConfigurationException {
		logger.info("updated(properties=" + properties + ")");
		if (properties != null) {
			String propCseIdToBeAnnounced = (String) properties.get(CSE_ID_TO_BE_ANNOUNCED);
			String propCseNameToBeAnnounced = (String) properties.get(CSE_NAME_TO_BE_ANNOUNCED);
			Boolean propAnnouncementEnabled = Boolean.parseBoolean((String) properties.get(ANNOUNCEMENT_ENABLED));
			Boolean propIpeUnderAnnouncedResource = Boolean
					.parseBoolean((String) properties.get(IPE_UNDER_ANNOUNCED_RESOURCE));
			logger.info("updated(" + CSE_ID_TO_BE_ANNOUNCED + "=" + propCseIdToBeAnnounced + ")\n" + "updated("
					+ CSE_NAME_TO_BE_ANNOUNCED + "=" + propCseNameToBeAnnounced + ")\n" + "updated("
					+ ANNOUNCEMENT_ENABLED + "=" + propAnnouncementEnabled + ")\n" + "updated("
					+ IPE_UNDER_ANNOUNCED_RESOURCE + "=" + propIpeUnderAnnouncedResource + ")");

			
			boolean isValidConfiguration = false;

			if (propAnnouncementEnabled == null) {
				logger.info("Undefined property announcement.enabled. Announcement disabled");
				cseIdToBeAnnounced = null;
				cseNameToBeAnnounced = null;
				ipeUnderAnnouncedResource = false;
				isValidConfiguration = true;
			} else if (propAnnouncementEnabled) {
				if ((propCseIdToBeAnnounced != null) && (propCseNameToBeAnnounced != null)) {
					// check if CSE is connected
					// if (checkIfRemoteCSEExists(propCseIdToBeAnnounced,
					// propCseNameToBeAnnounced)) {
					isValidConfiguration = true;
					hasToBeAnnounced = true;
					cseIdToBeAnnounced = propCseIdToBeAnnounced;
					cseNameToBeAnnounced = propCseNameToBeAnnounced;
					ipeUnderAnnouncedResource = (propIpeUnderAnnouncedResource == null) ? false
							: propIpeUnderAnnouncedResource;
					// }
				} else {
					// no remote
					logger.info("no REMOTE CSE where to announce resource but announcement.enabled=true");
					isValidConfiguration = false;
				}
			} else {
				// announcement.enabled = false
				if (propIpeUnderAnnouncedResource.booleanValue()) {
					if ((propCseIdToBeAnnounced != null) && (propCseNameToBeAnnounced != null)) {
						isValidConfiguration = true;
						hasToBeAnnounced = false;
						cseIdToBeAnnounced = propCseIdToBeAnnounced;
						cseNameToBeAnnounced = propCseNameToBeAnnounced;
						ipeUnderAnnouncedResource = true;
					} else {
						// invalid configuration
						isValidConfiguration = false;
						hasToBeAnnounced = false;
						cseIdToBeAnnounced = null;
						cseNameToBeAnnounced = null;
						ipeUnderAnnouncedResource = false;
						logger.info("no REMOTE CSE where ipe.under.announced.resource=true");
					}
				} else {
					isValidConfiguration = true;
					cseIdToBeAnnounced = null;
					cseNameToBeAnnounced = null;
					ipeUnderAnnouncedResource = false;
					hasToBeAnnounced = false;
				}

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

		if (remoteCseId == null) {
			// no need to check remote cse !
			return true;
		}

		ResponsePrimitive response = null;
		// check 3 times
		for (int i = 0; i < 3; i++) {
			response = CseUtil.sendRetrieveRequest(cseService,
					"/" + remoteCseId + "/" + remoteCseName + "/" + Constants.CSE_NAME);
			if (ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
				break;
			}

			// wait 10s
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ResponseStatusCode.OK.equals(response.getResponseStatusCode());
	}

	@Override
	public void handleEvent(Event event) {

		logger.debug("handleEvent!!!!!!");

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// check event
		String remoteCseId = (String) event.getProperty(RemoteCseService.REMOTE_CSE_ID_PROPERTY);
		String remoteCseName = (String) event.getProperty(RemoteCseService.REMOTE_CSE_NAME_PROPERTY);
		String operationProperty = (String) event.getProperty(RemoteCseService.OPERATION_PROPERTY);

		if ((remoteCseId == null) || (remoteCseName == null)) {
			// nothing to do
			return;
		}

		if (RemoteCseService.ADD_OPERATION_VALUE.equals(operationProperty)) {
			// add a new cse
			if ((remoteCseId.equals(cseIdToBeAnnounced)) && (remoteCseName.equals(cseNameToBeAnnounced))) {
				startSDTIpe();
			}

		} else if (RemoteCseService.REMOVE_OPERATION_VALUE.equals(operationProperty)) {
			// remove a remoteCse
			if ((remoteCseId.equals(cseIdToBeAnnounced)) && (remoteCseName.equals(cseNameToBeAnnounced))) {
				stopSDTIPE();
			}
		}

	}

}
