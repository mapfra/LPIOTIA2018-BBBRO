/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.das.testsuite.dacis;

import java.util.ArrayList;
import java.util.UUID;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.das.testsuite.Test;

public class AeDacisTest extends Test {

	public AeDacisTest(CseService pCseService) {
		super("Ae Dacis", pCseService);
	}

	@Override
	public void performTest() {
		// createDas
		DynamicAuthorizationConsultation dac = createDAS();
		if (dac == null) {
			// ko
			setState(State.KO);
			setMessage("unable to create DAC");
			return;
		}

		// create a Ae with a dynamicAuthorizationConsultationsId
		AE toBeCreatedAe = new AE();
		toBeCreatedAe.getDynamicAuthorizationConsultationIDs().add(dac.getResourceID());
		toBeCreatedAe.setAppID("App" + UUID.randomUUID());
		toBeCreatedAe.setRequestReachability(Boolean.FALSE);
		toBeCreatedAe.setName(toBeCreatedAe.getAppID());

		// prepare CREATE request
		RequestPrimitive createRequest = new RequestPrimitive();
		createRequest.setOperation(Operation.CREATE);
		createRequest.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		createRequest.setTo("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME);
		createRequest.setRequestContentType(MimeMediaType.OBJ);
		createRequest.setReturnContentType(MimeMediaType.OBJ);
		createRequest.setResourceType(ResourceType.AE);
		createRequest.setContent(toBeCreatedAe);

		// execute CREATE request
		ResponsePrimitive createResponse = getCseService().doRequest(createRequest);
		if (createResponse == null) {
			// ko
			setState(State.KO);
			setMessage("response is null");
			return;
		}
		if (!ResponseStatusCode.CREATED.equals(createResponse.getResponseStatusCode())) {
			// ko
			setState(State.KO);
			setMessage("unable to create Ae: expecting " + ResponseStatusCode.CREATED + ", found "
					+ createResponse.getResponseStatusCode());
			return;
		}

		AE createdAe = null;
		try {
			createdAe = (AE) createResponse.getContent();
		} catch (ClassCastException e) {
			setState(State.KO);
			setMessage("expected response content is not a Ae");
			return;
		}

		// check dacis
		if (!checkEquals(createdAe.getDynamicAuthorizationConsultationIDs(),
				toBeCreatedAe.getDynamicAuthorizationConsultationIDs(), "dynamicAuthorizationConsultationIDs")) {
			return;
		}

		// execute RETRIEVE request
		ResponsePrimitive retrieveResponse = retrieveEntity(createdAe.getResourceID());
		if (retrieveResponse == null) {
			// ko
			setState(State.KO);
			setMessage("retrieveResponse is null");
			return;
		}
		// check response status
		if (!ResponseStatusCode.OK.equals(retrieveResponse.getResponseStatusCode())) {
			// ko
			setState(State.KO);
			setMessage("unable to retrieve Ae: expecting " + ResponseStatusCode.OK + ", found "
					+ createResponse.getResponseStatusCode());
			return;
		}

		AE retrievedAe = null;
		try {
			retrievedAe = (AE) retrieveResponse.getContent();
		} catch (ClassCastException e) {
			setState(State.KO);
			setMessage("expected response content is not a Ae");
			return;
		}

		// check dacis
		if (!checkEquals(retrievedAe.getDynamicAuthorizationConsultationIDs(),
				toBeCreatedAe.getDynamicAuthorizationConsultationIDs(), "dynamicAuthorizationConsultationIDs")) {
			return;
		}

		// delete DAC
		RequestPrimitive deleteDACRequest = new RequestPrimitive();
		deleteDACRequest.setOperation(Operation.DELETE);
		deleteDACRequest.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		deleteDACRequest.setTo(dac.getResourceID());
		if (!ResponseStatusCode.DELETED.equals(getCseService().doRequest(deleteDACRequest).getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unable to delete DAC");
			return;
		}

		// retrieve again ==> dacis list should be empty

		// execute RETRIEVE request
		retrieveResponse = retrieveEntity(createdAe.getResourceID());
		if (retrieveResponse == null) {
			// ko
			setState(State.KO);
			setMessage("retrieveResponse is null");
			return;
		}
		// check response status
		if (!ResponseStatusCode.OK.equals(retrieveResponse.getResponseStatusCode())) {
			// ko
			setState(State.KO);
			setMessage("unable to retrieve Ae: expecting " + ResponseStatusCode.OK + ", found "
					+ createResponse.getResponseStatusCode());
			return;
		}
		retrievedAe = null;
		try {
			retrievedAe = (AE) retrieveResponse.getContent();
		} catch (ClassCastException e) {
			setState(State.KO);
			setMessage("expected response content is not a Ae");
			return;
		}
		// check dacis
		if (!checkEquals(retrievedAe.getDynamicAuthorizationConsultationIDs(), new ArrayList<String>(),
				"dynamicAuthorizationConsultationIDs")) {
			return;
		}

		// OK
		setState(State.OK);
	}

}
