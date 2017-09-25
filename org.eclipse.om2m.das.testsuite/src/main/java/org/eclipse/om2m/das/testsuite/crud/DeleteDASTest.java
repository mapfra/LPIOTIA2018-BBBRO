/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.das.testsuite.crud;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.das.testsuite.Test;

public class DeleteDASTest extends Test {

	public DeleteDASTest(CseService pCseService) {
		super("Delete DAS", pCseService);
	}

	@Override
	public void performTest() {
		DynamicAuthorizationConsultation createdDas = createDAS();
		if (createdDas == null) {
			// KO
			setState(State.KO);
			setMessage("unable to create a DAS");
			return;
		}

		// delete - non hierarchical uri
		if (!deleteDas(createdDas.getResourceID())) {
			// KO
			return;
		}

		createdDas = createDAS();
		if (createdDas == null) {
			// KO
			setState(State.KO);
			setMessage("unable to create a DAS");
			return;
		}
		
		// delete - hierarchical uri
		if (!deleteDas("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + createdDas.getName())) {
			// KO
			return;
		}

		// OK
		setState(State.OK);

	}

	private boolean deleteDas(String dasUrl) {
		// prepare request
		RequestPrimitive requestPrimitive = new RequestPrimitive();
		requestPrimitive.setOperation(Operation.DELETE);
		requestPrimitive.setTargetId(dasUrl);
		requestPrimitive.setFrom(Constants.ADMIN_REQUESTING_ENTITY);

		// execute request
		ResponsePrimitive response = getCseService().doRequest(requestPrimitive);
		if (response != null) {
			if (!ResponseStatusCode.DELETED.equals(response.getResponseStatusCode())) {
				setState(State.KO);
				setMessage("expecting " + ResponseStatusCode.UPDATED + " status code, found "
						+ response.getResponseStatusCode());
				return false;
			}

			// no content expected
			if (!checkNull(response.getContent(), "content")) {
				return false;
			}

		} else {
			setState(State.KO);
			setMessage("no response");
		}

		// try to retrieve the deleted das
		ResponsePrimitive retrievedResponse = retrieveEntity(dasUrl);
		if (retrievedResponse == null) {
			setState(State.KO);
			setMessage("no response");
			return false;
		}
		if (!ResponseStatusCode.NOT_FOUND.equals(retrievedResponse.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("resource must not exist");
			return false;
		}

		return true;
	}

}
