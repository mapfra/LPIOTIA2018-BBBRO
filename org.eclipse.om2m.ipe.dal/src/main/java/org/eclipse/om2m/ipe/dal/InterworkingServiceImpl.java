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

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.dal.Device;
import org.osgi.service.dal.Function;
import org.osgi.util.tracker.ServiceTracker;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.exceptions.InternalServerErrorException;
import org.eclipse.om2m.commons.obix.Obj;
import org.eclipse.om2m.commons.obix.Str;
import org.eclipse.om2m.commons.obix.io.ObixEncoder;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.Resource;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.eclipse.om2m.persistence.service.DBTransaction;

/**
 * Implementation of DAL interworking service.
 */
public class InterworkingServiceImpl implements InterworkingService {

	/** Logger */
	private static Log LOGGER = LogFactory
			.getLog(InterworkingServiceImpl.class);

	/** global static function map */
	private static HashMap<String, Function> functions = new HashMap<String, Function>();

	public static String poc = "dal";
	public static String CSE_ID = "/" + Constants.CSE_ID;
	public static String CSE_PREFIX = CSE_ID + "/" + Constants.CSE_NAME;
	public static final String DATA = "DATA";
	public static final String DESC = "DESCRIPTOR";

	/**
	 * Constructor
	 */
	public InterworkingServiceImpl() {
	}

	/**
	 * Activate method, called before registering IPE service
	 * 
	 * @param
	 * @return
	 */
	public void activate() {

		// Register device service listener
		ServiceTracker<Object, Object> deviceTracker = new ServiceTracker<Object, Object>(
				Activator.getContext(), Device.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference,
					Object service) {
				LOGGER.info("Device service removed");
			}

			public Object addingService(ServiceReference<Object> reference) {

				// Create the device resource when device service detected
				LOGGER.info("Device service discovered");
				Device device = (Device) this.context.getService(reference);
				createDeviceResource(device);
				return device;
			}
		};

		deviceTracker.open();
	}

	public void deactivate() {

	}

	/**
	 * Handle the resource operation request
	 * 
	 * @param request
	 * @return ResponsePrimitive
	 */
	@Override
	public ResponsePrimitive doExecute(RequestPrimitive request) {

		ResponsePrimitive response = new ResponsePrimitive(request);
		String oprName = "";
		String funcResourseName = "";

		// Get the input parameter
		if (request.getQueryStrings().containsKey("op")) {
			oprName = request.getQueryStrings().get("op").get(0);
		} else {
			throw new BadRequestException("Parameter 'op' is not found.");
		}

		if (request.getQueryStrings().containsKey("function")) {
			funcResourseName = request.getQueryStrings().get("function").get(0);
		} else {
			throw new BadRequestException("Parameter 'function' is not found.");
		}

		// Get the function service object by resource name
		Function function = functions.get(funcResourseName);

		if (function == null) {
			throw new BadRequestException(
					"The specified function can not be found.");
		}

		// Get the function operation method object by method name
		Method method = null;

		try {

			method = function.getClass().getDeclaredMethod(oprName);
			method.setAccessible(true);

		} catch (Exception e) {
			throw new BadRequestException(
					"The specified operation is not supported.");
		}

		// Call the function's method
		try {
			Object ret = method.invoke(function);

			// If the method returns a value
			if (ret != null) {

				Obj obj = new Obj();
				obj.add(new Str("Result", ret.toString()));
				response.setContent(ObixEncoder.toString(obj));
				request.setReturnContentType(MimeMediaType.OBIX);
			}
		} catch (Exception e) {
			throw new InternalServerErrorException(e.getMessage());
		}

		response.setResponseStatusCode(ResponseStatusCode.OK);

		return response;
	}

	/**
	 * Returns the ApocPath id required for the
	 * {@link InterworkingProxyController} to dispatch a received request.
	 * 
	 * @param
	 * @return String
	 */
	@Override
	public String getAPOCPath() {
		return poc;
	}

	/**
	 * Add the function service object to the map
	 * 
	 * @param resourceName
	 *            - resource name of this function function - service object
	 * @return
	 */
	public static void addFunction(String resourceName, Function function) {
		functions.put(resourceName, function);
	}

	/**
	 * Create AE resource when device service detected
	 * 
	 * @param device
	 *            - service object
	 * @return
	 */
	private void createDeviceResource(Device device) {

		String deviceUid = (String) device
				.getServiceProperty(Device.SERVICE_UID);
		String resourceId = "";

		// Set the application ID to the device UID, replace the illegal
		// character
		String appId = deviceUid.replace(':', '_');

		// Construct the AE resource
		AE ae = new AE();
		ae.setRequestReachability(true);
		ae.getPointOfAccess().add(InterworkingServiceImpl.poc);
		ae.setAppID(appId);
		ae.setName(appId);
		ResponsePrimitive response = createResource(CSE_ID,  ae,
				ResourceType.AE);

		if (response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {

			resourceId = response.getLocation();

		}
		// If the resource is already created, query the resource id from
		// database
		else if (response.getResponseStatusCode().equals(
				ResponseStatusCode.CONFLICT)) {

			DBTransaction dbTransaction = Activator.getDBService()
					.getDbTransaction();
			dbTransaction.open();
			AeEntity dbAe = Activator.getDBService().getDAOFactory()
					.getAeByAppIdDAO().find(dbTransaction, appId);
			dbTransaction.close();

			resourceId = dbAe.getResourceID();
		}

		if (!resourceId.isEmpty()) {

			// Track the device function service by the device UID
			Dictionary<String, Object> dict = new Hashtable<String, Object>();
			dict.put(Function.SERVICE_DEVICE_UID,
					device.getServiceProperty(Device.SERVICE_UID));

			FunctionServiceTracker funcTracker;
			try {
				funcTracker = new FunctionServiceTracker(deviceUid, appId);
				funcTracker.open();
			} catch (InvalidSyntaxException e) {
				LOGGER.error(e);
			}

		}

	}

	/**
	 * Create resource
	 * 
	 * @param targetId
	 *            - target resource Id name - resource name to be created
	 *            resource - resource object resourceType - resource type
	 * @return ResponsePrimitive
	 */
	
	public static ResponsePrimitive createResource(String targetId, Resource resource, int resourceType) {
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(targetId);
		request.setResourceType(BigInteger.valueOf(resourceType));
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(resource);
		//request.setName(name);
		request.setOperation(Operation.CREATE);
		return Activator.getCseService().doRequest(request);
	}

}
