/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt;

import java.util.Dictionary;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.core.service.RemoteCseService;
import org.eclipse.om2m.flexcontainer.service.FlexContainerService;
import org.eclipse.om2m.sdt.Device;
import org.eclipse.om2m.sdt.Property;
import org.eclipse.om2m.sdt.events.SDTEventListener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Activator implements EventHandler {

	private static final String CSE_ID_TO_BE_ANNOUNCED = "cse.id.to.be.announced";
	private static final String CSE_NAME_TO_BE_ANNOUNCED = "cse.name.to.be.announced";
	private static final String ANNOUNCEMENT_ENABLED = "announcement.enabled";
	private static final String IPE_UNDER_ANNOUNCED_RESOURCE = "ipe.under.announced.resource";
	private static final String PROP_PROTOCOL = "proPl";//"propProtocol";
	private static final String CLOUD_PROTOCOL = "Cloud.";

	private String cseIdToBeAnnounced;
	private String cseNameToBeAnnounced;
	private boolean ipeUnderAnnouncedResource;
	private boolean hasToBeAnnounced;
	private boolean isSDTIPEStarted = false;

	private SDTIpeApplication sdtIPEApplication;
	private CseService cseService;

	private static BundleContext bundleContext;

	private static Log logger = LogFactory.getLog(Activator.class);

	public Activator() {
	}

	/**
	 * Activate method.
	 * 
	 * @param pBundleContext
	 * @param properties
	 *            contains the first configuration
	 */
	protected void activate(BundleContext pBundleContext, Map<String, Object> properties) {
		logger.info("activate SDT IPE");
		bundleContext = pBundleContext;

		if (checkConfigurations(properties)) {
			startSdtIpe();
		}

	}

	protected void deactivate(ComponentContext cc) {
		logger.info("deactivate SDT IPE");

		stopSdtIpe(); 
		bundleContext = null;
	}

	protected void modified(Map<String, Object> properties) {
		logger.info("Modified SDT IPE");
		checkConfigurations(properties);

	}

	protected void setCseService(CseService cseService) {
		logger.info("setCseService");
		this.cseService = cseService;
	}

	protected void unsetCseService(CseService pCseService) {
		logger.info("unsetCseService");
		this.cseService = null;
	}

	protected void setDevice(Device device) {
		logger.info("setDevice(" + device.getName() + ") " + device.getProperties());
		Property protocol = device.getProperty(PROP_PROTOCOL, true);
		logger.info("Found device, protocol " + protocol);
		if ((protocol != null) && protocol.getValue().startsWith(CLOUD_PROTOCOL)) {
			logger.info("Cloud device, ignore...");
		} else {
			DeviceList.getInstance().addDevice(device);
		}
	}

	protected void unsetDevice(Device pDevice) {
		logger.info("unsetDevice(" + pDevice.getName() + ")");
		DeviceList.getInstance().removeDevice(pDevice);
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
		DeviceList.getInstance().addListenerAndSend(sdtIPEApplication);
	}

	/**
	 * Unregister Sdt Ipe Application.
	 * 
	 * Stop SdtDevice tracking
	 */
	protected void unregisterSdtIpeApplication() {
		if (sdtIPEApplication != null) {
			DeviceList.getInstance().removeListener(sdtIPEApplication);
			sdtIPEApplication.deleteIpeApplicationEntity();
			sdtIPEApplication = null;
		}
	}

	private void startSdtIpe() {
		synchronized (this) {
			isSDTIPEStarted = true;

			if (checkIfRemoteCSEExists(cseIdToBeAnnounced, cseNameToBeAnnounced)) {

				// create and register SDTIpeApplication
				try {
					logger.info("Start IPE App " + cseIdToBeAnnounced + " / " + cseNameToBeAnnounced + " / "
							+ ipeUnderAnnouncedResource);
					registerSdtIpeApplication(cseIdToBeAnnounced, cseNameToBeAnnounced, ipeUnderAnnouncedResource);
				} catch (Exception e) {
					logger.error("SdtIpeApplication oneM2M publishing failed", e);
					stopSdtIpe();
				}

			} else {
				logger.error("Remote CSE not available");
			}

		}
	}

	private void stopSdtIpe() {
		synchronized (this) {

			if (isSDTIPEStarted) {
				unregisterSdtIpeApplication();
			}
			isSDTIPEStarted = false;

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

	private boolean checkConfigurations(Map<String, Object> properties) {
		boolean isValidConfiguration = false;

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

			if (propAnnouncementEnabled) {
				if ((propCseIdToBeAnnounced != null) && (propCseNameToBeAnnounced != null)) {
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
		} else {
			logger.info("No properties. Deactivate announcement");
			cseIdToBeAnnounced = null;
			cseNameToBeAnnounced = null;
			ipeUnderAnnouncedResource = false;
			hasToBeAnnounced = false;
			isValidConfiguration = true;
		}

		return isValidConfiguration;
	}

	/**
	 * 
	 * @param remoteCseId
	 * @param remoteCseName
	 * @return
	 */
	private boolean checkIfRemoteCSEExists(final String remoteCseId, final String remoteCseName) {

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

		logger.info("handleEvent!!!!!!");

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
				startSdtIpe();
			}

		} else if (RemoteCseService.REMOVE_OPERATION_VALUE.equals(operationProperty)) {
			// remove a remoteCse
			if ((remoteCseId.equals(cseIdToBeAnnounced)) && (remoteCseName.equals(cseNameToBeAnnounced))) {
				stopSdtIpe();
			}
		}

	}

}
