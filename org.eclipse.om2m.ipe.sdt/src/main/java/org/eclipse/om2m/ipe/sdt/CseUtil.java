/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt;

import java.util.List;

import org.eclipse.om2m.commons.constants.AccessControl;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AEAnnc;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.AccessControlRule;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.SetOfAcrs;
import org.eclipse.om2m.commons.resource.Subscription;
import org.eclipse.om2m.core.service.CseService;

public class CseUtil {

	/**
	 * Send a oM2M CREATE Application Entity request
	 * 
	 * @param cseService
	 *            cseService
	 * @param ae
	 *            new application entity to create
	 * @param resourceLocation
	 *            location of the to be created application
	 * @param resourceName
	 *            name of the to be created application
	 * @return ResponsePrimitive sent by the CSE
	 */
	public static ResponsePrimitive sendCreateApplicationEntityRequest(CseService cseService, AE ae,
			String resourceLocation, String resourceName) {
		RequestPrimitive request = new RequestPrimitive();

		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(resourceLocation);
		request.setOperation(Operation.CREATE);
		request.setName(resourceName);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setResourceType(ResourceType.AE);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(ae);

		return sendRequest(cseService, request);
	}
	
	/**
	 * Send a oM2M CREATE Application Entity request
	 * 
	 * @param cseService
	 *            cseService
	 * @param ae
	 *            new application entity to create
	 * @param resourceLocation
	 *            location of the to be created application
	 * @param resourceName
	 *            name of the to be created application
	 * @return ResponsePrimitive sent by the CSE
	 */
	public static ResponsePrimitive sendUpdateApplicationAnncEntityRequest(CseService cseService, AEAnnc aeAnnc,
			String resourceLocation) {
		RequestPrimitive request = new RequestPrimitive();

		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(resourceLocation);
		request.setOperation(Operation.UPDATE);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(aeAnnc);

		return sendRequest(cseService, request);
	}
	
	/**
	 * Send a oM2M CREATE Subscription request
	 * 
	 * @param cseService
	 *            cseService
	 * @param subscription
	 *            new application entity to create
	 * @param resourceLocation
	 *            location of the to be created application
	 * @param resourceName
	 *            name of the to be created application
	 * @return ResponsePrimitive sent by the CSE
	 */
	public static ResponsePrimitive sendCreateSubscriptionRequest(CseService cseService, Subscription subscription,
			String resourceLocation, String resourceName) {
		RequestPrimitive request = new RequestPrimitive();

		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(resourceLocation);
		request.setOperation(Operation.CREATE);
		request.setName(resourceName);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setResourceType(ResourceType.SUBSCRIPTION);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(subscription);

		return sendRequest(cseService, request);
	}

	/**
	 * Send a CREATE FlexContainer request
	 * 
	 * @param cseService CSE service
	 * @param flexContainer flexContainer to be created
	 * @param resourceLocation location of the to be created resource
	 * @param resourceName name of the to be created resource
	 * @return response sent by the CSE
	 */
	public static ResponsePrimitive sendCreateFlexContainerRequest(CseService cseService, FlexContainer flexContainer,
			String resourceLocation, String resourceName) {
		RequestPrimitive request = new RequestPrimitive();

		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(resourceLocation);
		request.setOperation(Operation.CREATE);
		request.setName(resourceName);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setResourceType(ResourceType.FLEXCONTAINER);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(flexContainer);

		return sendRequest(cseService, request);
	}
	
	public static ResponsePrimitive sendCreateDefaultACP(CseService cseService, String acpLocation, String acpName, List<String> labels) {
		
		AccessControlPolicy acp = new AccessControlPolicy();
		acp.getLabels().addAll(labels);
		
		// privileges
		SetOfAcrs setOfAcs = new SetOfAcrs();
		acp.setPrivileges(setOfAcs);
		AccessControlRule acr = new AccessControlRule();
		acr.setAccessControlOperations(AccessControl.ALL);
		acr.getAccessControlOriginators().add(Constants.ADMIN_REQUESTING_ENTITY);
		setOfAcs.getAccessControlRule().add(acr);
		
		// self priveleges
		acp.setSelfPrivileges(new SetOfAcrs());
		AccessControlRule acrSP = new AccessControlRule();
		acrSP.setAccessControlOperations(AccessControl.ALL);
		acrSP.getAccessControlOriginators().add(Constants.ADMIN_REQUESTING_ENTITY);
		acp.getSelfPrivileges().getAccessControlRule().add(acrSP);
		
		RequestPrimitive request = new RequestPrimitive();

		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(acpLocation);
		request.setOperation(Operation.CREATE);
		request.setName(acpName);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setResourceType(ResourceType.ACCESS_CONTROL_POLICY);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(acp);

		
		
		return sendRequest(cseService, request);
	}
	
	/**
	 * Send a INTERNAL NOTIFY FlexContainer request
	 * 
	 * @param cseService CSE service
	 * @param flexContainer flexContainer to be internally notified
	 * @param resourceLocation location of the to be internally notified resource
	 * 
	 * @return response sent by the CSE
	 */
	public static ResponsePrimitive sendInternalNotifyFlexContainerRequest(CseService cseService, FlexContainer flexContainer,
			String resourceLocation) {
		RequestPrimitive request = new RequestPrimitive();

		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(resourceLocation);
		request.setOperation(Operation.INTERNAL_NOTIFY);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setResourceType(ResourceType.FLEXCONTAINER);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(flexContainer);

		return sendRequest(cseService, request);
	}
	
	
	/**
	 * Retrieve a resource
	 * @param cseService
	 * @param uri
	 * @return response
	 */
	public static ResponsePrimitive sendRetrieveRequest(CseService cseService, String uri) {
		RequestPrimitive request = new RequestPrimitive();

		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(uri);
		request.setOperation(Operation.RETRIEVE);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);

		return sendRequest(cseService, request);
	}

	/**
	 * Delete a oM2M resource
	 * 
	 * @param cseService
	 *            CSE service
	 * @param resourceLocation
	 *            location of the to be deleted resource
	 * @return ResponsePrimitive sent by the CSE
	 */
	public static ResponsePrimitive sendDeleteRequest(CseService cseService, String resourceLocation) {
		RequestPrimitive request = new RequestPrimitive();

		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(resourceLocation);
		request.setOperation(Operation.DELETE);

		return sendRequest(cseService, request);
	}

	/**
	 * Send a request to the CSE
	 * 
	 * @param cseService
	 *            CSe service
	 * @param request
	 *            request to be sent
	 * @return ResponsePrimitive received from the CSE
	 */
	private static ResponsePrimitive sendRequest(CseService cseService, RequestPrimitive request) {
		return cseService.doRequest(request);
	}
}
