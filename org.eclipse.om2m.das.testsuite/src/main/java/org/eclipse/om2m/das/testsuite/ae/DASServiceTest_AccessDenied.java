/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.das.testsuite.ae;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.constants.SecurityInfoType;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.DynAuthDasRequest;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.SecurityInfo;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.osgi.framework.ServiceRegistration;

public class DASServiceTest_AccessDenied extends AbstractDASServiceTest {
	
	private DynamicAuthorizationConsultation dac; 

	public DASServiceTest_AccessDenied(CseService pCseService) {
		super("DASService AccessDenied", pCseService);
	}

	@Override
	public void performTest() {
		// create DAC
		dac = createDAS(getDasAE().getResourceID());
		if (dac == null) {
			setState(State.KO);
			setMessage("unable to create dac");
			return;
		}

		// set number of expected call
		setExpectedNumberOfCall(1);

		// register this as a InterworkingService
		ServiceRegistration<InterworkingService> interworkingServiceRegistration = registerInterworkingService(this);

		// create application (with DynamicAuthorizationConsultationIDs)
		List<String> dacis = new ArrayList<>();
		dacis.add(dac.getResourceID());
		AE ae = createAE(dacis);
		if (ae == null) {
			setState(State.KO);
			setMessage("unable to create AE");
			return;
		}

		// retrieve ae ==> DASS must be called && return AccessDenied
		ResponsePrimitive response = retrieveEntity(ae.getResourceID(), "nom:password");
		if (!ResponseStatusCode.ACCESS_DENIED.equals(response.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unexpected ResponseCode, expecting " + ResponseStatusCode.OK + ", found ="
					+ response.getResponseStatusCode());
			return;
		}

		// unregister DASS
		unregisterInterworkingService(interworkingServiceRegistration);
		
		// one call
		List<Call> receivedCalls = getCalls();
		if (receivedCalls.size() != 1) {
			setState(State.KO);
			setMessage("expecting 1 call, found " + receivedCalls.size());
			return;
		}
		
		Call receivedCall = receivedCalls.get(0);
		if (receivedCall.getDynAuthRequest() == null) {
			setMessage("request is null");
			setState(State.KO);
			return;
		}

		// targetId
		if (!receivedCall.getDynAuthRequest().getTargetedResourceID().equals(ae.getResourceID())) {
			setState(State.KO);
			setMessage("bad targetId, expecting " + ae.getResourceID() + ", found "
					+ receivedCall.getDynAuthRequest().getTargetedResourceID());
			return;
		}

		// from
		if (!receivedCall.getDynAuthRequest().getOriginator().equals("nom:password")) {
			setState(State.KO);
			setMessage("bad caller credentials, expecting:" + "nom:password" + ", found "
					+ receivedCall.getDynAuthRequest().getOriginator());
			return;
		}

		// operation
		if (!Operation.RETRIEVE.equals(receivedCall.getDynAuthRequest().getOperation())) {
			setState(State.KO);
			setMessage("bad operation, expecting:" + Operation.RETRIEVE + ", found "
					+ receivedCall.getDynAuthRequest().getOperation());
			return;
		}

		// check resource entity
		if (receivedCall.getDynAuthRequest().getTargetedResourceID() == null) {
			setState(State.KO);
			setMessage("resourceEntity is null");
			return;
		}
		if (!ae.getResourceID().equals(receivedCall.getDynAuthRequest().getTargetedResourceID())) {
			setState(State.KO);
			setMessage("bad resourceEntity id, expecting " + ae.getResourceID() + ", found:"
					+ receivedCall.getDynAuthRequest().getTargetedResourceID());
			return;
		}

		setState(State.OK);
		

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
		
		Call call = new Call(dasRequest);
		addCall(call);
		
		response.setResponseStatusCode(ResponseStatusCode.ACCESS_DENIED);
		return response;
	}


}
