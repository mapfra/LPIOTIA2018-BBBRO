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
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.das.testsuite.Test;

public class DynamicAuthorizationConsultationDacisTest extends Test {

	public DynamicAuthorizationConsultationDacisTest(CseService pCseService) {
		super("DynamicAuthorizationConsultation DACIs", pCseService);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.om2m.das.testsuite.Test#performTest()
	 */
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

		// create a DynamicAuthorizationConsultation with a dynamicAuthorizationConsultationsId
		DynamicAuthorizationConsultation toBeCreatedDac = new DynamicAuthorizationConsultation();
		toBeCreatedDac.getDynamicAuthorizationConsultationIDs().add(dac.getResourceID());
		toBeCreatedDac.setDynamicAuthorizationEnabled(Boolean.FALSE);
		toBeCreatedDac.setName("DAC_" + UUID.randomUUID());
		
		// prepare CREATE request
		RequestPrimitive createRequest = new RequestPrimitive();
		createRequest.setOperation(Operation.CREATE);
		createRequest.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		createRequest.setTo("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME);
		createRequest.setRequestContentType(MimeMediaType.OBJ);
		createRequest.setReturnContentType(MimeMediaType.OBJ);
		createRequest.setResourceType(ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION);
		createRequest.setContent(toBeCreatedDac);

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
			setMessage("unable to create DynamicAuthorizationConsultation: expecting " + ResponseStatusCode.CREATED + ", found "
					+ createResponse.getResponseStatusCode());
			return;
		}

		DynamicAuthorizationConsultation createdDac = null;
		try {
			createdDac = (DynamicAuthorizationConsultation) createResponse.getContent();
		} catch (ClassCastException e) {
			setState(State.KO);
			setMessage("expected response content is not a DynamicAuthorizationConsultation");
			return;
		}

		// check dacis
		if (!checkEquals(createdDac.getDynamicAuthorizationConsultationIDs(),
				toBeCreatedDac.getDynamicAuthorizationConsultationIDs(), "dynamicAuthorizationConsultationIDs")) {
			return;
		}


		// execute RETRIEVE request
		ResponsePrimitive retrieveResponse = retrieveEntity(createdDac.getResourceID());
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
			setMessage("unable to retrieve DynamicAuthorizationConsultation: expecting " + ResponseStatusCode.OK + ", found "
					+ createResponse.getResponseStatusCode());
			return;
		}

		DynamicAuthorizationConsultation retrievedDac = null;
		try {
			retrievedDac = (DynamicAuthorizationConsultation) retrieveResponse.getContent();
		} catch (ClassCastException e) {
			setState(State.KO);
			setMessage("expected response content is not a DynamicAuthorizationConsultation");
			return;
		}

		// check dacis
		if (!checkEquals(retrievedDac.getDynamicAuthorizationConsultationIDs(),
				toBeCreatedDac.getDynamicAuthorizationConsultationIDs(), "dynamicAuthorizationConsultationIDs")) {
			return;
		}
		
		// delete dac
		RequestPrimitive deleteRequest = new RequestPrimitive();
		deleteRequest.setOperation(Operation.DELETE);
		deleteRequest.setTo(dac.getResourceID());
		deleteRequest.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		ResponsePrimitive deleteResponse = getCseService().doRequest(deleteRequest);
		if (!ResponseStatusCode.DELETED.equals(deleteResponse.getResponseStatusCode())) {
			// ko
			setState(State.KO);
			setMessage("unable to delete DAC");
			return;
		}
		
		// execute RETRIEVE request
		retrieveResponse = retrieveEntity(createdDac.getResourceID());
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
			setMessage("unable to retrieve DynamicAuthorizationConsultation: expecting " + ResponseStatusCode.OK + ", found "
					+ createResponse.getResponseStatusCode());
			return;
		}

		retrievedDac = null;
		try {
			retrievedDac = (DynamicAuthorizationConsultation) retrieveResponse.getContent();
		} catch (ClassCastException e) {
			setState(State.KO);
			setMessage("expected response content is not a DynamicAuthorizationConsultation");
			return;
		}

		// check dacis
		if (!checkEquals(retrievedDac.getDynamicAuthorizationConsultationIDs(),
				new ArrayList<String>(), "dynamicAuthorizationConsultationIDs")) {
			return;
		}


		// OK
		setState(State.OK);
	}

}
