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

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.onem2m.sdt.Device;

public class SDTIpeApplication {

	private static final String APPLICATION_NAME = "SDT_IPE";

	private static final String BASE_LOCATION = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;

	private static final String APPLICATION_LOCATION = BASE_LOCATION + "/" + APPLICATION_NAME;

	private static final String POA = "sdt";

	private String aeId;

	private final CseService cseService;

	private final Map<Device, SDTDeviceAdaptor> devices = new HashMap<>();
	
	private AccessControlPolicy adminAccessControlPolicy;

	public SDTIpeApplication(final CseService pCseService) {
		cseService = pCseService;
	}

	// SDT Device Management
	protected boolean addSDTDevice(Device device) {
		Logger.getInstance().logInfo(SDTIpeApplication.class,
				"add SDT Device (id:" + device.getId() + ", name=" + device.getName() + ") into oneM2M");

		SDTDeviceAdaptor sdtDeviceAdaptor = new SDTDeviceAdaptor(APPLICATION_LOCATION, device, cseService, adminAccessControlPolicy.getResourceID());
		if (sdtDeviceAdaptor.publishIntoOM2MTree()) {

			synchronized (devices) {
				devices.put(device, sdtDeviceAdaptor);
			}
			return true;
		}

		return false;
	}

	protected void removeSDTDevice(Device device) {
		Logger.getInstance().logInfo(SDTIpeApplication.class,
				"remove SDT Device (id:" + device.getId() + ", name=" + device.getName() + ") into oneM2M");

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
		Logger.getInstance().logInfo(SDTIpeApplication.class, "create ipe application");

		AE ae = new AE();
		ae.setAppID(APPLICATION_NAME);
		ae.setRequestReachability(Boolean.TRUE);
		ae.getPointOfAccess().add(POA);

		ResponsePrimitive responsePrimitive = CseUtil.sendCreateApplicationEntityRequest(cseService, ae, BASE_LOCATION,
				APPLICATION_NAME);

		if (!(responsePrimitive.getResponseStatusCode().equals(ResponseStatusCode.CREATED)
				|| responsePrimitive.getResponseStatusCode().equals(ResponseStatusCode.CONFLICT))) {
			Logger.getInstance().logError(SDTIpeApplication.class,
					"Unable to create SDT IPE Application:" + responsePrimitive.getContent(), null);
			throw new Exception("unable to create SDT Application Entity");
		}

		AE receivedAe = (AE) responsePrimitive.getContent();
		aeId = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + receivedAe.getResourceID();
		
		
		ResponsePrimitive response = CseUtil.sendCreateDefaultACP(cseService, APPLICATION_LOCATION, "ACP_Device_Admin", new ArrayList<String>());
		adminAccessControlPolicy = (AccessControlPolicy) response.getContent();
	}

	/**
	 * Send a DELETE oneM2M request to delete SDT IPE application entity
	 */
	protected void deleteIpeApplicationEntity() {
		Logger.getInstance().logInfo(SDTIpeApplication.class, "delete ipe application");
		ResponsePrimitive response = CseUtil.sendDeleteRequest(cseService, APPLICATION_LOCATION);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.DELETED)) {
			// log only
			// no need to throw an exception
			Logger.getInstance().logError(SDTIpeApplication.class,
					"unable to delete SDT IPE Application entity:" + response.getContent(), null);
		}
	}


}
