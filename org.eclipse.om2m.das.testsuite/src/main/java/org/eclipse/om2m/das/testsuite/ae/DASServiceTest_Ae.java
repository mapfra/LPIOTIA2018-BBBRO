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

import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.osgi.framework.ServiceRegistration;

public class DASServiceTest_Ae extends AbstractDASServiceTest  {

	/**
	 * To be used by activator
	 * 
	 * @param pCseService
	 */
	public DASServiceTest_Ae(CseService pCseService) {
		super("DasServiceTest_Ae", pCseService);
	}

	@Override
	public void performTest() {

		// create DAC
		DynamicAuthorizationConsultation dac = createDAS(getDasAE().getResourceID());
		if (dac == null) {
			setState(State.KO);
			setMessage("unable to create dac");
			return;
		}

		// set number of expected call
		setExpectedNumberOfCall(1);

		// register this as a InterworkingService
		ServiceRegistration<InterworkingService> interworkingServiceRegistration = registerInterworkingService(
				this);

		// create application (with DynamicAuthorizationConsultationIDs)
		List<String> dacis = new ArrayList<>();
		dacis.add(dac.getResourceID());
		AE ae = createAE(dacis);
		if (ae == null) {
			setState(State.KO);
			setMessage("unable to create AE");
			return;
		}

		// retrieve ae ==> DASS must be called
		ResponsePrimitive response = retrieveEntity(ae.getResourceID(), "nom:password");
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unable to retrieve Ae, expecting " + ResponseStatusCode.OK + ", found ="
					+ response.getResponseStatusCode());
			return;
		}

		// check call
		if (!checkCall(0, ae.getResourceID(), "nom:password", Operation.RETRIEVE)) {
			return;
		}

		// clear calls
		clearCalls();

		// update ae
		AE toBeUpdated = new AE();
		toBeUpdated.setRequestReachability(Boolean.TRUE);

		// prepare update request
		RequestPrimitive updateRequest = new RequestPrimitive();
		updateRequest.setOperation(Operation.UPDATE);
		updateRequest.setFrom("nini:nono");
		updateRequest.setTargetId(ae.getResourceID());
		updateRequest.setRequestContentType(MimeMediaType.OBJ);
		updateRequest.setReturnContentType(MimeMediaType.OBJ);
		updateRequest.setContent(toBeUpdated);

		// execute updateRequest
		ResponsePrimitive updateResponse = getCseService().doRequest(updateRequest);
		if (updateResponse == null) {
			setState(State.KO);
			setMessage("updateResponse is null");
			return;
		}
		if (!ResponseStatusCode.UPDATED.equals(updateResponse.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unable to update Ae, expecting " + ResponseStatusCode.UPDATED + ", found ="
					+ updateResponse.getResponseStatusCode());
			return;
		}

		// check call
		if (!checkCall(0, ae.getResourceID(), "nini:nono", Operation.UPDATE)) {
			return;
		}

		// clear calls
		clearCalls();

		// delete ae ==> prepare request
		RequestPrimitive deleteRequest = new RequestPrimitive();
		deleteRequest.setOperation(Operation.DELETE);
		deleteRequest.setTargetId(ae.getResourceID());
		deleteRequest.setFrom("zaza:zozo");

		// execute deleteRequest
		ResponsePrimitive deleteResponse = getCseService().doRequest(deleteRequest);
		if (deleteResponse == null) {
			setState(State.KO);
			setMessage("deleteResponse is null");
			return;
		}
		if (!ResponseStatusCode.DELETED.equals(deleteResponse.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unable to delete Ae, expecting " + ResponseStatusCode.DELETED + ", found ="
					+ deleteResponse.getResponseStatusCode());
			return;
		}

		// check call
		if (!checkCall(0, ae.getResourceID(), "zaza:zozo", Operation.DELETE)) {
			return;
		}

		// unregister InterworkingService
		unregisterInterworkingService(interworkingServiceRegistration);

		setState(State.OK);
	}

}
