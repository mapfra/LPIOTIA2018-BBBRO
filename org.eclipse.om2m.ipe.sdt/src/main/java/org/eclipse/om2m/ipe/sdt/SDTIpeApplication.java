/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AEAnnc;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.sdt.Device;

public class SDTIpeApplication implements DeviceListListener {

	private static Log logger = LogFactory.getLog(SDTIpeApplication.class);

	private static final String SEP = "/";
	private static final String APPLICATION_NAME = "SDT_IPE";
	private static final String DEFAULT_BASE_LOCATION = SEP + Constants.CSE_ID + SEP + Constants.CSE_NAME;
	private static final String DEFAULT_APPLICATION_LOCATION = DEFAULT_BASE_LOCATION + SEP + APPLICATION_NAME;

	private static final String POA = "sdt";

	private final Map<Device, SDTDeviceAdaptor> devices = new HashMap<>();

	private final String remoteCseId;
	private final String remoteCseName;
	private final String sdtIpeApplicationLocation;
	private final String sdtIpeBaseLocation;
	private final boolean ipeUnderAnnouncedResource;
	private final boolean hasToBeAnnounced;
	
	private AccessControlPolicy adminAccessControlPolicy;
	private AccessControlPolicy remoteAdminAccessControlPolicy;
	private AE registeredAe;

	public SDTIpeApplication(final String announceCseId, final String pRemoteCseName,
			final boolean ipeUnder, final boolean hasToBeAnnounced) {
		this.remoteCseId = announceCseId;
		this.remoteCseName = pRemoteCseName;
		this.ipeUnderAnnouncedResource = ipeUnder;
		this.hasToBeAnnounced = hasToBeAnnounced;

		if (ipeUnderAnnouncedResource) {
			if ((remoteCseId != null) && (remoteCseName != null)) {
				sdtIpeBaseLocation = DEFAULT_BASE_LOCATION + SEP + remoteCseName;
				sdtIpeApplicationLocation = sdtIpeBaseLocation + SEP + APPLICATION_NAME;
			} else {
				sdtIpeApplicationLocation = DEFAULT_APPLICATION_LOCATION;
				sdtIpeBaseLocation = DEFAULT_BASE_LOCATION;
			}
		} else {
			sdtIpeApplicationLocation = DEFAULT_APPLICATION_LOCATION;
			sdtIpeBaseLocation = DEFAULT_BASE_LOCATION;
		}
	}

	// SDT Device Management
	private boolean addSDTDevice(Device device) {
		logger.info("add SDT Device (id:" + device.getId() + ", name=" + device.getName() + ") into oneM2M");

		SDTDeviceAdaptor sdtDeviceAdaptor = new SDTDeviceAdaptor(sdtIpeApplicationLocation, 
				sdtIpeBaseLocation, device, 
				adminAccessControlPolicy.getResourceID(), 
				remoteCseId, remoteCseName, hasToBeAnnounced);
		if (sdtDeviceAdaptor.publishIntoOM2MTree()) {
			synchronized (devices) {
				devices.put(device, sdtDeviceAdaptor);
			}
			return true;
		}
		return false;
	}

	private void removeSDTDevice(Device device) {
		logger.info("remove SDT Device (id:" + device.getId() + ", name=" + device.getName() + ") into oneM2M");
		SDTDeviceAdaptor sdtDeviceAdaptor = null;
		synchronized (devices) {
			sdtDeviceAdaptor = devices.remove(device);
		}

		if (sdtDeviceAdaptor != null) {
			sdtDeviceAdaptor.unpublishIntoOM2MTree();
			sdtDeviceAdaptor = null;
		}
	}

	// SDT IPE Application entity
	/**
	 * Send a CREATE oneM2M request to create SDT IPE application entity.
	 * 
	 * @throws Exception
	 */
	public void publishSDTIPEApplication() throws Exception {
		logger.info("create ipe application");

		AE ae = new AE();
		ae.setName(APPLICATION_NAME);
		ae.setAppID(APPLICATION_NAME);
		ae.setRequestReachability(Boolean.TRUE);
		ae.getPointOfAccess().add(POA);
		if (hasToBeAnnounced) {
			ae.getAnnounceTo().add(SEP + remoteCseId);
			ae.getAnnouncedAttribute().add(ShortName.APP_ID);
			ae.getAnnouncedAttribute().add(ShortName.APP_NAME);
			ae.getAnnouncedAttribute().add(ShortName.NODE_LINK);
		}

		ResponsePrimitive resp = null;
		try {
			for (int i = 0; i < 3; i++) {
				resp = CseUtil.sendCreateApplicationEntityRequest(ae, sdtIpeBaseLocation);
	
				if (ResponseStatusCode.CREATED.equals(resp.getResponseStatusCode())) {
					 // nothing do 
					 break;
				 } else if (ResponseStatusCode.CONFLICT.equals(resp.getResponseStatusCode())) {
					 // remove previous SDT_IPE application
					 deleteIpeApplicationEntity();
					 continue;
				 } else {	
					 logger.error("Unable to create SDT IPE Application:" + resp.getContent(), null);
						throw new Exception("unable to create SDT Application Entity");
				 }
			}
		} catch (Throwable _e) {
			logger.error("Error in CreateIPEApplication", _e);
			throw _e;
		}

		if (!ResponseStatusCode.CREATED.equals(resp.getResponseStatusCode())) {
			// no need to continue
			return;
		} else {
			registeredAe = (AE) resp.getContent();
		}

		ResponsePrimitive response = CseUtil.sendCreateDefaultACP(sdtIpeBaseLocation,
				"ACP_Device_Admin_" + System.currentTimeMillis(), new ArrayList<String>());
		adminAccessControlPolicy = (AccessControlPolicy) response.getContent();

		if (/*(remoteCseId != null) && (remoteCseName != null)*/ hasToBeAnnounced) {
			// remote ACP_Device_Admin
			response = CseUtil.sendCreateDefaultACP(
					SEP + remoteCseId + SEP + remoteCseName + SEP + Constants.CSE_NAME,
					"Remote_ACP_Device_Admin" + System.currentTimeMillis(), new ArrayList<String>());
			remoteAdminAccessControlPolicy = (AccessControlPolicy) response.getContent();

			// update SDT_IPE_ANNC
			AEAnnc sdtIpeAnnc = new AEAnnc();
			sdtIpeAnnc.getAccessControlPolicyIDs().add(remoteAdminAccessControlPolicy.getResourceID());
			CseUtil.sendUpdateApplicationAnncEntityRequest(sdtIpeAnnc,
					SEP + remoteCseId + SEP + remoteCseName + SEP + Constants.CSE_NAME + "/SDT_IPE_Annc");
		}
	}

	/**
	 * Send a DELETE oneM2M request to delete SDT IPE application entity
	 */
	protected void deleteIpeApplicationEntity() {
		logger.info("delete ipe application");
		if (registeredAe != null) { 
			ResponsePrimitive response = CseUtil.sendDeleteRequest(registeredAe.getResourceID()/* sdtIpeApplicationLocation*/);
			if (!response.getResponseStatusCode().equals(ResponseStatusCode.DELETED)) {
				// log only
				// no need to throw an exception
				logger.error("unable to delete SDT IPE Application entity:" + response.getContent(), null);
			}
			registeredAe = null;
		}
		

		if (adminAccessControlPolicy != null) {
			CseUtil.sendDeleteRequest(adminAccessControlPolicy.getResourceID());
			adminAccessControlPolicy = null;
		}
		if (remoteAdminAccessControlPolicy != null) {
			CseUtil.sendDeleteRequest(remoteAdminAccessControlPolicy.getResourceID());
			remoteAdminAccessControlPolicy = null;
		}
	}

	@Override
	public void notifyNewDevice(Device newDevice) {
		addSDTDevice(newDevice);
	}

	@Override
	public void notifyDeviceRemoved(Device toBeRemovedDevice) {
		removeSDTDevice(toBeRemovedDevice);
	}

}
