/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.das.testsuite.crud;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.das.testsuite.Test;

public class RetrieveDASTest extends Test {

	public RetrieveDASTest(CseService pCseService) {
		super("Retrieve DAS", pCseService);
	}

	@Override
	public void performTest() {
		// create DAS
		DynamicAuthorizationConsultation createdDas = createDAS();
		if (createdDas == null) {
			// KO
			setState(State.KO);
			setMessage("unable to create a DAS");
			return;
		}
		
		// retrieve - non hierarchical
		if (!retrieveDas(createdDas.getResourceID(), createdDas)) {
			// KO
			return;
		}
		
		// retrieve - hierarchical
		if (!retrieveDas("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + createdDas.getName(), createdDas)) {
			// KO
			return;
		}
		
		// OK
		setState(State.OK);

	}

	private boolean retrieveDas(String dasUrl, DynamicAuthorizationConsultation createdDas) {
		// prepare request
		RequestPrimitive requestPrimitive = new RequestPrimitive();
		requestPrimitive.setOperation(Operation.RETRIEVE);
		requestPrimitive.setRequestContentType(MimeMediaType.OBJ);
		requestPrimitive.setReturnContentType(MimeMediaType.OBJ);
		requestPrimitive.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		requestPrimitive.setTargetId(dasUrl);

		// perform request
		ResponsePrimitive response = getCseService().doRequest(requestPrimitive);
		if (response != null) {

			if (ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {

				DynamicAuthorizationConsultation retrievedDas = null;
				try {
					retrievedDas = (DynamicAuthorizationConsultation) response.getContent();
				} catch (ClassCastException e) {
					setState(State.KO);
					setMessage("unable to cast content as a DynamicAuthorizationConsultation");
					return false;
				}

				// resourceName
				if (!checkEquals(retrievedDas.getName(), createdDas.getName(), "name")) {
					return false;
				}

				// resourceType
				if (!checkEquals(retrievedDas.getResourceType().intValue(), createdDas.getResourceType().intValue(),
						"resourceType")) {
					return false;
				}

				// resourceID
				if (!checkEquals(retrievedDas.getResourceID(), createdDas.getResourceID(), "resourceID")) {
					return false;
				}

				// parentID
				if (!checkEquals(retrievedDas.getParentID(), createdDas.getParentID(), "parentID")) {
					return false;
				}

				// expirationTime
				if (!checkEquals(retrievedDas.getExpirationTime(), createdDas.getExpirationTime(), "expirationTime")) {
					return false;
				}

				// accessControlPolicyIDs
				if (!checkEquals(retrievedDas.getAccessControlPolicyIDs(), createdDas.getAccessControlPolicyIDs(),
						"accessControlPolicyIDs")) {
					return false;
				}

				// creationTime
				if (!checkEquals(retrievedDas.getCreationTime(), createdDas.getCreationTime(), "creationTime")) {
					return false;
				}

				// lastModifiedTime
				if (!checkEquals(retrievedDas.getLastModifiedTime(), createdDas.getLastModifiedTime(),
						"lastModifiedTime")) {
					return false;
				}

				// labels
				if (!checkEquals(retrievedDas.getLabels(), createdDas.getLabels(), "labels")) {
					return false;
				}

				// dynamicAuthorizationConsultationIDs
				// TODO

				// dynamicAuthorizationEnabled
				if (!checkEquals(retrievedDas.getDynamicAuthorizationEnabled(),
						createdDas.getDynamicAuthorizationEnabled(), "dynamicAuthorizationEnabled")) {
					return false;
				}

				// dynamicAuthorizationPoA
				if (!checkEquals(retrievedDas.getDynamicAuthorisationPoA(), createdDas.getDynamicAuthorisationPoA(),
						"dynamicAuthorizationPoA")) {
					return false;
				}

				// dynamicAuthorizationLifetime
				if (!checkEquals(retrievedDas.getDynamicAuthorizationLifetime(),
						createdDas.getDynamicAuthorizationLifetime(), "dynamicAuthorizationLifetime")) {
					return false;
				}

			} else {
				setState(State.KO);
				setMessage("expecting " + ResponseStatusCode.UPDATED + " status code, found "
						+ response.getResponseStatusCode());
				return false;
			}

		} else {
			setState(State.KO);
			setMessage("no response");
		}

		return true;
	}

}
