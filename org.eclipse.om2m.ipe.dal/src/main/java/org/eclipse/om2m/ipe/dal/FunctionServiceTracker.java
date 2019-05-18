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
import java.math.BigInteger;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.dal.Function;
import org.osgi.service.dal.FunctionEvent;
import org.osgi.service.dal.PropertyMetadata;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTracker;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.obix.Contract;
import org.eclipse.om2m.commons.obix.Obj;
import org.eclipse.om2m.commons.obix.Op;
import org.eclipse.om2m.commons.obix.Str;
import org.eclipse.om2m.commons.obix.Uri;
import org.eclipse.om2m.commons.obix.io.ObixEncoder;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.persistence.service.DBTransaction;

/**
 * Device function service tracker.
 */
public class FunctionServiceTracker extends ServiceTracker<Object, Object> {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(FunctionServiceTracker.class);

	/** Resource Id of the device this function tracker belongs to */
	private String deviceResourceName = "";

	public static final String DESC = "DESCRIPTOR";
	public static final String LATEST = "la";

	/**
	 * Constructor
	 * 
	 * @param deviceUid
	 *            - UID of the device that this function belongs to
	 *            deviceResourceId - resource Id of the device
	 * @return
	 */
	public FunctionServiceTracker(String deviceUid, String deviceResourceName)
			throws InvalidSyntaxException {
		super(Activator.getContext(),
				Activator.getContext().createFilter(
						"(&(" + Constants.OBJECTCLASS + "="
								+ Function.class.getName().toString() + ")("
								+ Function.SERVICE_DEVICE_UID + "=" + deviceUid
								+ "))"), null);
		this.deviceResourceName = deviceResourceName;
	}

	/**
	 * Call back method when function service is unregistered
	 * 
	 * @param reference
	 *            - service reference
	 * @return
	 */
	@Override
	public void removedService(ServiceReference<Object> reference,
			Object service) {
		LOGGER.info("Function service removed");
	}

	/**
	 * Call back method when function service is registered. Create container
	 * resource asynchronously
	 * 
	 * @param reference
	 *            - service reference
	 * @return Object
	 */
	@Override
	public Object addingService(ServiceReference<Object> reference) {
		LOGGER.info("Function service discovered");
		final Function func = (Function) this.context.getService(reference);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				createFunctionResource(func);
			}
		});

		return func;
	}

	/**
	 * Create container resource asynchronously
	 * 
	 * @param func
	 *            - function service object
	 * @return
	 */
	private void createFunctionResource(Function func) {

		// Set the function resource name to the function UID, replace the
		// illegal character
		String funcUid = (String) func.getServiceProperty(Function.SERVICE_UID);
		String resourceName = funcUid.replace(':', '_');
		String resourceId = "";

		// Construct the container resource
		Container container = new Container();
		container.setMaxNrOfInstances(BigInteger.valueOf(10));
		container.setName(resourceName);
		ResponsePrimitive response = InterworkingServiceImpl.createResource(
				InterworkingServiceImpl.CSE_PREFIX + "/" + deviceResourceName,
				 container, ResourceType.CONTAINER);

		if (response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			resourceId = response.getLocation();
		}
		// Query the container resource ID from database if it is already
		// created
		else if (response.getResponseStatusCode().equals(
				ResponseStatusCode.CONFLICT)) {

			DBTransaction dbTransaction = Activator.getDBService()
					.getDbTransaction();
			dbTransaction.open();
			ContainerEntity dbContainer = Activator.getDBService()
					.getDAOFactory().getContainerByResourceNameDAO()
					.find(dbTransaction, resourceName);
			dbTransaction.close();

			resourceId = dbContainer.getResourceID();
		}

		if (!resourceId.isEmpty()) {

			// Store the function to the map, so it can handle the oneM2M
			// request
			InterworkingServiceImpl.addFunction(resourceName, func);

			// Create the DESCRIPTOR container resource for the function, to
			// describe the function information
			container = new Container();
			container.setMaxNrOfInstances(BigInteger.valueOf(10));
			container.setName(DESC);

			response = InterworkingServiceImpl.createResource(resourceId,
					container, ResourceType.CONTAINER);

			String descResourceId = "";

			if (response.getResponseStatusCode().equals(
					ResponseStatusCode.CREATED)) {
				descResourceId = response.getLocation();
			}
			// Query from the database by function resource ID
			else if (response.getResponseStatusCode().equals(
					ResponseStatusCode.CONFLICT)) {
				DBTransaction dbTransaction = Activator.getDBService()
						.getDbTransaction();
				dbTransaction.open();
				ContainerEntity dbContainer = Activator.getDBService()
						.getDAOFactory().getDescContainerByParentDAO()
						.find(dbTransaction, resourceId);
				dbTransaction.close();

				descResourceId = dbContainer.getResourceID();
			}

			if (!descResourceId.isEmpty()) {
				// Create DESCRIPTOR content instance resource
				ContentInstance contentInstance = new ContentInstance();
				contentInstance.setContent(getFunctionDescContent(func,
						resourceName, resourceId));
				contentInstance.setContentInfo(MimeMediaType.OBIX);
				contentInstance.setName(DESC);
				InterworkingServiceImpl.createResource(descResourceId, 
						contentInstance, ResourceType.CONTENT_INSTANCE);
			}

			// Register this function's event handler
			Dictionary<String, Object> dict = new Hashtable<String, Object>();
			dict.put(EventConstants.EVENT_TOPIC,
					FunctionEvent.TOPIC_PROPERTY_CHANGED);
			dict.put(EventConstants.EVENT_FILTER, "(" + Function.SERVICE_UID
					+ "=" + funcUid + ")");

			FunctionEventHandler newFuncEventHandler = new FunctionEventHandler(
					funcUid, resourceId);
			ServiceRegistration<?> register = Activator.getContext()
					.registerService(EventHandler.class.getName(),
							newFuncEventHandler, dict);
			if (register == null) {
				LOGGER.error(String.format(
						"Event handler (%s) register failed!", funcUid));
			}
		}

	}

	/**
	 * Return the specific function DESCRIPTOR content string
	 * 
	 * @param func
	 *            - function service object resourceName - resource name of this
	 *            function
	 * @return
	 */
	private String getFunctionDescContent(Function func, String resourceName,
			String resourceId) {

		// Basic function parameter
		Obj obj = new Obj();
		obj.add(new Str(Function.SERVICE_UID, (String) func
				.getServiceProperty(Function.SERVICE_UID)));
		obj.add(new Str(Function.SERVICE_TYPE, (String) func
				.getServiceProperty(Function.SERVICE_TYPE)));
		obj.add(new Str(Function.SERVICE_VERSION, (String) func
				.getServiceProperty(Function.SERVICE_VERSION)));
		obj.add(new Str(Function.SERVICE_DESCRIPTION, (String) func
				.getServiceProperty(Function.SERVICE_DESCRIPTION)));
		// Get resource state from oneM2M side
		Op opLa = new Op();
		opLa.setName("getState");
		opLa.setHref(new Uri(InterworkingServiceImpl.CSE_PREFIX + "/"
				+ deviceResourceName + "/" + resourceName + "/" + LATEST));
		// opLa.setHref(new Uri(resourceId));
		opLa.setIs(new Contract("retrieve"));
		opLa.setIn(new Contract("obix:Nil"));
		opLa.setOut(new Contract("obix:Nil"));
		obj.add(opLa);

		// Function properties
		String[] propNames = (String[]) func
				.getServiceProperty(Function.SERVICE_PROPERTY_NAMES);

		if (propNames != null) {
			for (String propName : propNames) {

				// Get property access policy
				Map<?, ?> propMetadata = func.getPropertyMetadata(propName)
						.getMetadata(null);

				Integer access = (Integer) propMetadata
						.get(PropertyMetadata.ACCESS);

				// Create get operation when this property is readable
				if ((access.intValue() & PropertyMetadata.ACCESS_READABLE) != 0) {
					String getPropOpName = "get"
							+ propName.replace(propName.substring(0, 1),
									propName.substring(0, 1).toUpperCase());
					Op getPropOp = new Op();
					getPropOp.setName(getPropOpName);
					getPropOp.setHref(new Uri(
							InterworkingServiceImpl.CSE_PREFIX + "/"
									+ deviceResourceName + "?function="
									+ resourceName + "&op=" + getPropOpName));
					getPropOp.setIs(new Contract("execute"));
					getPropOp.setIn(new Contract("obix:Nil"));
					getPropOp.setOut(new Contract("obix:Nil"));
					obj.add(getPropOp);
				}

				// Create set operation when this property is writable
				if ((access.intValue() & PropertyMetadata.ACCESS_WRITABLE) != 0) {

					String setPropOpName = "set"
							+ propName.replace(propName.substring(0, 1),
									propName.substring(0, 1).toUpperCase());
					Op setPropOp = new Op();
					setPropOp.setName(setPropOpName);
					setPropOp.setHref(new Uri(
							InterworkingServiceImpl.CSE_PREFIX + "/"
									+ deviceResourceName + "?function="
									+ resourceName + "&op=" + setPropOpName));
					setPropOp.setIs(new Contract("execute"));
					setPropOp.setIn(new Contract("obix:Nil"));
					setPropOp.setOut(new Contract("obix:Nil"));
					setPropOp.setWritable(true);
					obj.add(setPropOp);
				}

			}
		}

		// Function operations
		String[] operNames = (String[]) func
				.getServiceProperty(Function.SERVICE_OPERATION_NAMES);

		if (operNames != null) {
			for (String operName : operNames) {
				Op opState = new Op();
				opState.setName(operName);
				opState.setHref(new Uri(InterworkingServiceImpl.CSE_PREFIX
						+ "/" + deviceResourceName + "?function="
						+ resourceName + "&op=" + operName));
				opState.setIs(new Contract("execute"));
				opState.setIn(new Contract("obix:Nil"));
				opState.setOut(new Contract("obix:Nil"));
				obj.add(opState);
			}
		}

		return ObixEncoder.toString(obj);
	}

}
