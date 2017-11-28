/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.AccessControl;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AEAnnc;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.AccessControlRule;
import org.eclipse.om2m.commons.resource.DeviceInfo;
import org.eclipse.om2m.commons.resource.Node;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.Resource;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.SetOfAcrs;
import org.eclipse.om2m.commons.resource.Subscription;
import org.eclipse.om2m.core.service.CseService;

public class CseUtil {

    private static final Log logger = LogFactory.getLog(CseUtil.class);
    
    static CseService cseService;

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
	public static ResponsePrimitive sendCreateApplicationEntityRequest(AE ae, 
			String resourceLocation) {
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(resourceLocation);
		request.setOperation(Operation.CREATE);
		request.setResourceType(ResourceType.AE);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(ae);
		return cseService.doRequest(request);
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
	 * @return ResponsePrimitive sent by the CSE
	 */
	public static ResponsePrimitive sendUpdateApplicationAnncEntityRequest(AEAnnc aeAnnc, 
			String resourceLocation) {
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(resourceLocation);
		request.setOperation(Operation.UPDATE);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(aeAnnc);
		return cseService.doRequest(request);
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
	 * @return ResponsePrimitive sent by the CSE
	 */
	public static ResponsePrimitive sendCreateSubscriptionRequest(Subscription subscription, 
			String resourceLocation) {
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(resourceLocation);
		request.setOperation(Operation.CREATE);
		request.setResourceType(ResourceType.SUBSCRIPTION);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(subscription);
		return cseService.doRequest(request);
	}

	/**
	 * Send a CREATE FlexContainer request
	 * 
	 * @param cseService CSE service
	 * @param flexContainer flexContainer to be created
	 * @param resourceLocation location of the to be created resource
	 * @return response sent by the CSE
	 */
	public static ResponsePrimitive sendCreateFlexContainerRequest(AbstractFlexContainer flexContainer, 
			String resourceLocation) {
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(resourceLocation);
		request.setOperation(Operation.CREATE);
		request.setResourceType(ResourceType.FLEXCONTAINER);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(flexContainer);
		return cseService.doRequest(request);
	}

	/**
	 * Send a UPDATE FlexContainer request
	 * 
	 * @param cseService CSE service
	 * @param flexContainer flexContainer to be updated
	 * @param resourceLocation location of the to be created resource
	 * @return response sent by the CSE
	 */
	public static ResponsePrimitive sendUpdateFlexContainerRequest(AbstractFlexContainer flexContainer) { 
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setOperation(Operation.UPDATE);
		request.setTo(flexContainer.getResourceID());
		request.setResourceType(ResourceType.FLEXCONTAINER);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(flexContainer);
		return cseService.doRequest(request);
	}
	
	public static ResponsePrimitive sendCreateDefaultACP(String acpLocation, 
			String acpName, List<String> labels) {
		AccessControlPolicy acp = new AccessControlPolicy();
		acp.setName(acpName);
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
		request.setResourceType(ResourceType.ACCESS_CONTROL_POLICY);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(acp);
		
		return cseService.doRequest(request);
	}

	/**
	 * Send a CREATE Node request
	 * 
	 * @param cseService CSE service
	 * @param node the node to be created
	 * @param devInfo the deviceInfo to be created (child of node)
 	 * @param baseLocation location of the to be created resource
	 * @return response sent by the CSE
	 */
	public static ResponsePrimitive sendCreateNodeRequest(Node node, 
			DeviceInfo devInfo, String baseLocation) {
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(baseLocation);
		request.setOperation(Operation.CREATE);
		request.setResourceType(ResourceType.NODE);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(node);

		ResponsePrimitive resp = cseService.doRequest(request);
		if (! resp.getResponseStatusCode().equals(ResponseStatusCode.CREATED))
			return resp;
		Node createdNode = (Node) resp.getContent();
		
		request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(createdNode.getResourceID());
		request.setOperation(Operation.CREATE);
		request.setResourceType(ResourceType.MGMT_OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(devInfo);
		return cseService.doRequest(request);
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
	public static ResponsePrimitive sendInternalNotifyFlexContainerRequest(AbstractFlexContainer flexContainer, 
			String resourceLocation) {
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(resourceLocation);
		request.setOperation(Operation.INTERNAL_NOTIFY);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setResourceType(ResourceType.FLEXCONTAINER);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(flexContainer);
		return cseService.doRequest(request);
	}
	
	/**
	 * Retrieve a resource
	 * @param cseService
	 * @param uri
	 * @return response
	 */
	public static ResponsePrimitive sendRetrieveRequest(String uri) {
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(uri);
		request.setOperation(Operation.RETRIEVE);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		return cseService.doRequest(request);
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
	public static ResponsePrimitive sendDeleteRequest(String resourceLocation) {
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(resourceLocation);
		request.setOperation(Operation.DELETE);
		return cseService.doRequest(request);
	}
	
}
