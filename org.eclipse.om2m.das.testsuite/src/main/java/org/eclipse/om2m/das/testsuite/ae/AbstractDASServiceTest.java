/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.das.testsuite.ae;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.constants.SecurityInfoType;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AccessControlRule;
import org.eclipse.om2m.commons.resource.DynAuthDasRequest;
import org.eclipse.om2m.commons.resource.DynAuthDasResponse;
import org.eclipse.om2m.commons.resource.DynAuthDasResponse.DynamicACPInfo;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.SecurityInfo;
import org.eclipse.om2m.commons.resource.SetOfAcrs;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.das.testsuite.Activator;
import org.eclipse.om2m.das.testsuite.Test;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.osgi.framework.ServiceRegistration;

public abstract class AbstractDASServiceTest extends Test implements InterworkingService {

	private String poa;
	private int expectedNumberOfCall = 1;
	private List<Call> calls = new ArrayList<>();
	private List<ServiceRegistration<InterworkingService>> registeredInterworkingServices = new ArrayList<>();
	
	private AE dasAe;

	protected AbstractDASServiceTest(String pName, CseService pCseService) {
		super(pName, pCseService);
		
		// create DAS ae
		createDasAE();
	}

	@Override
	public abstract void performTest();

	@Override
	public void cleanUp() {
		
		for (ServiceRegistration<InterworkingService> sr : registeredInterworkingServices) {
			unregisterInterworkingService(sr);
		}
		
		// delete das ae
		deleteDasAE();
		
	}
	
	@Override
	public String getAPOCPath() {
		return dasAe.getPointOfAccess().get(0);
	}

	
	
	@Override
	public ResponsePrimitive doExecute(RequestPrimitive request) {
		ResponsePrimitive response = new ResponsePrimitive(request);
		if (!Operation.NOTIFY.equals(request.getOperation())) {
			// ko
			response.setResponseStatusCode(ResponseStatusCode.OPERATION_NOT_ALLOWED);
			return response;
		}
		
		SecurityInfo securityInfo = null;
		try {
			securityInfo = (SecurityInfo) request.getContent();
			if (securityInfo == null) {
				// ko
				response.setResponseStatusCode(ResponseStatusCode.CONTENTS_UNACCEPTABLE);
				return response;
			}
		} catch (ClassCastException e) {
			// ko
			response.setResponseStatusCode(ResponseStatusCode.CONTENTS_UNACCEPTABLE);
			return response;
		}
		
		if (!SecurityInfoType.DYNAMIC_AUTHORIZATION_REQUEST.equals(securityInfo.getSecurityInfoType())) {
			// ko
			response.setResponseStatusCode(ResponseStatusCode.CONTENTS_UNACCEPTABLE);
			return response;
		}
		
		// retrieve das request
		DynAuthDasRequest dasRequest = securityInfo.getDasRequest();
		if (dasRequest == null) {
			response.setResponseStatusCode(ResponseStatusCode.ACCESS_DENIED);
			return response;
		}
		
		// nothing to do except tracking call
		int nbCall = getNumberOfCall();

		Call call = new Call(dasRequest);
		addCall(call);

		if (nbCall > expectedNumberOfCall) {
			response.setResponseStatusCode(ResponseStatusCode.ACCESS_DENIED);
			return response;
		}
		
		
		SecurityInfo responseSecurityInfo = new SecurityInfo();
		responseSecurityInfo.setSecurityInfoType(SecurityInfoType.DYNAMIC_AUTHORIZATION_RESPONSE);
		responseSecurityInfo.setDasResponse(new DynAuthDasResponse());
		responseSecurityInfo.getDasResponse().setDynamicACPInfo(new DynamicACPInfo());
		responseSecurityInfo.getDasResponse().getDynamicACPInfo().setGrantedPrivileges(new SetOfAcrs());
		AccessControlRule acr = new AccessControlRule();
		acr.setAccessControlOperations(dasRequest.getOperation());
		acr.getAccessControlOriginators().add(dasRequest.getOriginator());
		responseSecurityInfo.getDasResponse().getDynamicACPInfo().getGrantedPrivileges().getAccessControlRule().add(acr);
		
		response.setContent(responseSecurityInfo);
		response.setResponseStatusCode(ResponseStatusCode.OK);
		
		
		return response;
	}


	/**
	 * @return the numberOfCall
	 */
	protected int getNumberOfCall() {
		int nbOfCall = 0;
		synchronized (calls) {
			nbOfCall = calls.size();
		}
		return nbOfCall;
	}

	/**
	 * Add a call
	 * 
	 * @param call
	 */
	protected void addCall(Call call) {
		synchronized (calls) {
			calls.add(call);
		}
	}

	protected void clearCalls() {
		synchronized (calls) {
			calls.clear();
		}
	}

	/**
	 * Get calls
	 * 
	 * @return
	 */
	protected List<Call> getCalls() {
		List<Call> toBeReturned = new ArrayList<>();
		synchronized (calls) {
			toBeReturned.addAll(calls);
		}
		return toBeReturned;
	}

	/**
	 * Check a call
	 * 
	 * @param callIndex
	 * @param expectedResourceId
	 * @param expectedFrom
	 * @param expectedOperation
	 * @return true if the call is as expected
	 */
	protected boolean checkCall(int callIndex, String expectedResourceId, String expectedFrom,
			BigInteger expectedOperation) {
		// check authorize
		if (getNumberOfCall() != getExpectedNumberOfCall()) {
			setMessage("expect 1 call, found " + getNumberOfCall());
			setState(State.KO);
			return false;
		}

		List<Call> receivedCalls = getCalls();
		// at this point, we are sure we have one call in the list
		Call call = receivedCalls.get(callIndex);

		if (call.getDynAuthRequest() == null) {
			setMessage("request is null");
			setState(State.KO);
			return false;
		}

		// targetId
		if (!call.getDynAuthRequest().getTargetedResourceID().equals(expectedResourceId)) {
			setState(State.KO);
			setMessage("bad targetId, expecting " + expectedResourceId + ", found "
					+ call.getDynAuthRequest().getTargetedResourceID());
			return false;
		}

		// from
		if (!call.getDynAuthRequest().getOriginator().equals(expectedFrom)) {
			setState(State.KO);
			setMessage("bad caller credentials, expecting:" + expectedFrom + ", found "
					+ call.getDynAuthRequest().getOriginator());
			return false;
		}

		// operation
		if (!expectedOperation.equals(call.getDynAuthRequest().getOperation())) {
			setState(State.KO);
			setMessage("bad operation, expecting:" + Operation.RETRIEVE + ", found "
					+ call.getDynAuthRequest().getOperation());
			return false;
		}

		// check resource entity
		if (call.getDynAuthRequest().getTargetedResourceID() == null) {
			setState(State.KO);
			setMessage("resourceEntity is null");
			return false;
		}
		if (!expectedResourceId.equals(call.getDynAuthRequest().getTargetedResourceID())) {
			setState(State.KO);
			setMessage("bad resourceEntity id, expecting " + expectedResourceId + ", found:"
					+ call.getDynAuthRequest().getTargetedResourceID());
			return false;
		}

		return true;
	}

	/**
	 * @return the expectedNumberOfCall
	 */
	protected int getExpectedNumberOfCall() {
		return expectedNumberOfCall;
	}

	/**
	 * @param expectedNumberOfCall
	 *            the expectedNumberOfCall to set
	 */
	protected void setExpectedNumberOfCall(int expectedNumberOfCall) {
		this.expectedNumberOfCall = expectedNumberOfCall;
	}

	/**
	 * register a dynamicAuthorizationServerService
	 * 
	 * @param service
	 * @return
	 */
	protected ServiceRegistration<InterworkingService> registerInterworkingService(
			InterworkingService service) {
		ServiceRegistration<InterworkingService> sr = Activator.getBundleContext()
				.registerService(InterworkingService.class, service, null);
		return sr;
	}

	/**
	 * Unregister a dynamicAuthorizationServerService
	 * 
	 * @param serviceRegistration
	 */
	protected void unregisterInterworkingService(
			ServiceRegistration<InterworkingService> serviceRegistration) {
		if (serviceRegistration != null) {
			try {
				serviceRegistration.unregister();
				serviceRegistration = null;
			} catch (IllegalStateException e) {
			}

			registeredInterworkingServices.remove(serviceRegistration);
		}

	}

	protected class Call {
		private final DynAuthDasRequest requestPrimitive;

		public Call(final DynAuthDasRequest pRequest) {
			requestPrimitive = pRequest;
		}

		/**
		 * @return the requestPrimitive
		 */
		public DynAuthDasRequest getDynAuthRequest() {
			return requestPrimitive;
		}

	}
	
	private void createDasAE() {
		if (dasAe == null) {
			dasAe = createAe();
		}
	}
	
	private void deleteDasAE() {
		if (dasAe != null) {
			deleteEntity(dasAe.getResourceID());
			dasAe = null;
		}
	}
	
	protected AE getDasAE() {
		return dasAe;
	}

}
