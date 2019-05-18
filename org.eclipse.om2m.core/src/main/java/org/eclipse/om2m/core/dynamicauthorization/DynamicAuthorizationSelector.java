/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.core.dynamicauthorization;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.constants.SecurityInfoType;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.exceptions.AccessDeniedException;
import org.eclipse.om2m.commons.resource.AccessControlRule;
import org.eclipse.om2m.commons.resource.DasInfo;
import org.eclipse.om2m.commons.resource.DynAuthDasRequest;
import org.eclipse.om2m.commons.resource.DynAuthDasResponse.DynamicACPInfo;
import org.eclipse.om2m.commons.resource.DynAuthTokenReqInfo;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.SecurityInfo;
import org.eclipse.om2m.commons.resource.SetOfAcrs;
import org.eclipse.om2m.core.redirector.Redirector;

public class DynamicAuthorizationSelector {

	protected static Log LOGGER = LogFactory.getLog(DynamicAuthorizationSelector.class);

	/**
	 * singleton
	 */
	private static final DynamicAuthorizationSelector INSTANCE = new DynamicAuthorizationSelector();

	/**
	 * 
	 * @return singleton
	 */
	public static DynamicAuthorizationSelector getInstance() {
		return INSTANCE;
	}

	/**
	 * Make private the default constructor.
	 */
	private DynamicAuthorizationSelector() {
	}

	/**
	 * Perform authorize request to the Dynamic Authorization Servers
	 * represented by the provided DynamicAuthorizationConsultation entities.
	 * 
	 * 
	 * @param dacesToBeUsed
	 *            dynamicAuthorizationConsultation entities to be used for the
	 *            authorization process
	 * @param request
	 *            request to be performed if the authorization is granted
	 * @param resourceEntity
	 *            resource involved in the request
	 * @return list of ACPs extracted from dynamicAcpInfo
	 */
	public void authorize(List<DynamicAuthorizationConsultationEntity> dacesToBeUsed, 
			RequestPrimitive request, ResourceEntity resourceEntity) throws AccessDeniedException {
		LOGGER.debug("authorize " + resourceEntity.getResourceID() 
			+ " to " + dacesToBeUsed);
		// create securityInfo describing the request to authorize
		SecurityInfo securityInfo = new SecurityInfo();
		securityInfo.setSecurityInfoType(SecurityInfoType.DYNAMIC_AUTHORIZATION_REQUEST);
		securityInfo.setDasRequest(new DynAuthDasRequest());
		securityInfo.getDasRequest().setOperation(request.getOperation());
		securityInfo.getDasRequest().setOriginator(request.getFrom());
		securityInfo.getDasRequest().getTokenIDs().addAll(request.getTokens());
		securityInfo.getDasRequest().setTargetedResourceID(resourceEntity.getResourceID());
		securityInfo.getDasRequest().setTargetedResourceType(resourceEntity.getResourceType());
		
		// iterate over daces list in order to perform authorization process
		Map<DynamicAuthorizationConsultationEntity, List<String>> tickets = new HashMap<>();
		for (DynamicAuthorizationConsultationEntity dace : dacesToBeUsed) {
			// check if the DynamicAuthorizationConsultation entity is enabled
			if (dace.getDynamicAuthorizationEnabled()) {
				List<String> ticketsPerDAC = new ArrayList<>();
				if (authorize(dace, securityInfo, resourceEntity, ticketsPerDAC, request)) {
					return;
				}
				tickets.put(dace, ticketsPerDAC);
			}
		}
		
		// prepare DynAuthTokenReqInfo to be returned
		DynAuthTokenReqInfo dynAuthTokenReqInfo = null;
		if (!dacesToBeUsed.isEmpty()) {
			dynAuthTokenReqInfo = new DynAuthTokenReqInfo();
			for (DynamicAuthorizationConsultationEntity dace : dacesToBeUsed) {
				DasInfo dasInfo = new DasInfo();
				dasInfo.setURI(dace.getDynamicAuthorizationPoA().get(0));
				dasInfo.setDasRequest(securityInfo.getDasRequest());
				
				List<String> ticketsPerDac = tickets.get(dace);
				dasInfo.getDasRequest().getTokenIDs().clear();
				dasInfo.getDasRequest().getTokenIDs().addAll(ticketsPerDac);
				
				dynAuthTokenReqInfo.getDasInfo().add(dasInfo);			
			}
		}
		throw new AccessDeniedException(dynAuthTokenReqInfo);
	}

	/**
	 * Perform authorize request to a specific server
	 * 
	 * @param dace
	 *            dynamicAuthorizationConsultation entity to be used for the
	 *            authorization process
	 * @param request
	 *            request
	 * @param resourceEntity
	 *            resource
	 * @return true if the server grants the authorization else false (all other
	 *         cases)
	 */
	private boolean authorize(DynamicAuthorizationConsultationEntity dace, SecurityInfo securityInfo,
			ResourceEntity resourceEntity, List<String> tokens, RequestPrimitive request) {
		List<String> pointOfAccesses = dace.getDynamicAuthorizationPoA();
		LOGGER.debug("authorize " + resourceEntity.getResourceID() + " to " + pointOfAccesses);
		for (String pointOfAccess : pointOfAccesses) {
			RequestPrimitive notifyRequest = new RequestPrimitive();
			notifyRequest.setOperation(Operation.NOTIFY);
			notifyRequest.setTo(pointOfAccess);
			notifyRequest.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
			notifyRequest.setContent(securityInfo);
			notifyRequest.setRequestContentType(MimeMediaType.OBJ);
			notifyRequest.setReturnContentType(MimeMediaType.OBJ);

			ResponsePrimitive response = Redirector.retargetNotify(notifyRequest);
			if (ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
				SecurityInfo responseSecurityInfo = (SecurityInfo) response.getContent();
				if (responseSecurityInfo == null) {
					// no dynamic acp
					LOGGER.info("No SecurityInfo in response");
					continue;
				}
				if (!SecurityInfoType.DYNAMIC_AUTHORIZATION_RESPONSE
						.equals(responseSecurityInfo.getSecurityInfoType())) {
					// invalid type
					LOGGER.info("Wrong SecurityInfo type in response");
					continue;
				}
				
				if (responseSecurityInfo.getDasResponse().getTokens() != null) {
					tokens.addAll(responseSecurityInfo.getDasResponse().getTokens());
				}
				
				DynamicACPInfo dynamicAcpInfo = responseSecurityInfo.getDasResponse().getDynamicACPInfo();
				if (dynamicAcpInfo == null) {
					// no dynamicAcpInfo
					LOGGER.info("No DynamicACPInfo in response");
					continue;
				}
				try {
					checkACPs(dynamicAcpInfo, securityInfo.getDasRequest().getOriginator(), 
							securityInfo.getDasRequest().getOperation());
					List<String> rpts = responseSecurityInfo.getDasResponse().getTokens();
					if ((rpts != null) && !(rpts.isEmpty())) {
						request.getTokens().clear();
						request.getTokens().addAll(rpts);
					}
					LOGGER.debug("check OK");
					return true;
				} catch (AccessDeniedException e) {
					// we continue to iterate over dac point of accesses
					LOGGER.warn("check KO: " + e.getMessage());
				}
			} else {
				LOGGER.info("KO response " + response.getResponseStatusCode());
			}
		}
		return false;
	}

	/**
	 * Checks the Access Right based on ACP list (Permission)
	 * @param acp - Id of the accessRight
	 * @param originator - requesting entity used by the requester
	 * @param operation - requested method
	 * @return error with a specific status code if the requesting Entity or the method does not exist otherwise null
	 */
	private void checkACPs(DynamicACPInfo dynamicAcpInfo, String originator, BigInteger operation)
			throws AccessDeniedException{
		if (originator == null) {
			throw new AccessDeniedException("No originator");
		}
		if (dynamicAcpInfo == null) {
			throw new AccessDeniedException("dynamicAcpInfo is false");
		}
		// Check Resource accessRight existence not found
		
		SetOfAcrs setOfAcrs = dynamicAcpInfo.getGrantedPrivileges();
		if (setOfAcrs == null) {
			throw new AccessDeniedException("set of acrs is null");
		}
		for (AccessControlRule rule : setOfAcrs.getAccessControlRule()) {
			LOGGER.debug("check " + originator + " in " + rule.getAccessControlOriginators()
				+ " and " + operation + " / " + rule.getAccessControlOperations());
			if (rule.getAccessControlOperations().equals(operation)) {
				for (String  originatorRule : rule.getAccessControlOriginators()) {
					if (originator.matches(originatorRule.replace("*", ".*"))) {
						return;
					}
				}
			}
		}
		throw new AccessDeniedException("originator not found or operation not allowed");
	}
	
}
