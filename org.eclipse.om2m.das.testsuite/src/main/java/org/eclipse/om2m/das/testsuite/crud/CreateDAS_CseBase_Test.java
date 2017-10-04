/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.das.testsuite.crud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

public class CreateDAS_CseBase_Test extends Test {

	/**
	 * to be used by activator
	 * 
	 * @param pCseService
	 */
	public CreateDAS_CseBase_Test(CseService pCseService) {
		super("CREATE DAS", pCseService);
	}

	/**
	 * to be used by class that extends this class
	 * 
	 * @param pName
	 * @param pCseService
	 */
	protected CreateDAS_CseBase_Test(String pName, CseService pCseService) {
		super(pName, pCseService);
	}

	@Override
	public void performTest() {

		// create - non hierarchical
		if (!createAndCheck("/" + Constants.CSE_ID)) {
			return;
		}

		// create - hierarchical
		if (!createAndCheck("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME)) {
			return;
		}

		setState(State.OK);

	}

	protected boolean createAndCheck(String toBeCreatedDasUrl) {
		// create request
		RequestPrimitive request = new RequestPrimitive();

		// das field
		Boolean enabled = Boolean.TRUE;
		List<String> poa = new ArrayList<>();
		poa.add("poa1");
		poa.add("poa2");

		String dasName = "DAS" + UUID.randomUUID().toString();

		// setup request
		request.setOperation(Operation.CREATE);
		request.setTargetId(toBeCreatedDasUrl);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setResourceType(ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);

		// set das
		DynamicAuthorizationConsultation das = new DynamicAuthorizationConsultation();
		das.setDynamicAuthorizationEnabled(enabled);
		das.setDynamicAuthorisationPoA(poa);
		das.setDynamicAuthorizationLifetime(new Date().toString());
		das.setName(dasName);
		
		request.setContent(das);

		ResponsePrimitive response = getCseService().doRequest(request);
		if (response != null) {
			if (ResponseStatusCode.CREATED.equals(response.getResponseStatusCode())) {
				// OK
				// retrieve created das from response
				try {
					DynamicAuthorizationConsultation createdDas = (DynamicAuthorizationConsultation) response
							.getContent();

					// resourceName
					if (!checkNotNull(createdDas.getName(), "name")) {
						return false;
					}
					if (!checkNotEmpty(createdDas.getName(), "name")) {
						return false;
					}

					// resourceType
					if (!checkNotNull(createdDas.getResourceType(), "resourceType")) {
						return false;
					}
					if (!checkEquals(createdDas.getResourceType().intValue(),
							ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION, "resourceType")) {
						return false;
					}

					// resourceID
					if (!checkNotNull(createdDas.getResourceID(), "resourceID")) {
						return false;
					}
					if (!checkNotEmpty(createdDas.getResourceID(), "resourceID")) {
						return false;
					}

					// parentID
					if (!checkNotNull(createdDas.getParentID(), "parentID")) {
						return false;
					}
					if (!checkNotEmpty(createdDas.getParentID(), "parentID")) {
						return false;
					}

					// expirationType
					if (!checkNull(createdDas.getExpirationTime(), "expirationTime")) {
						return false;
					}

					// accessControlPolicyIDs
					if (!checkNotNull(createdDas.getAccessControlPolicyIDs(), "accessControlPolicyIDs")) {
						return false;
					}
					if (createdDas.getAccessControlPolicyIDs().size() == 0) {
						setState(State.KO);
						setMessage("expected at least one default ACP");
					}

					// creationTime
					if (!checkNotNull(createdDas.getCreationTime(), "creationTime")) {
						return false;
					}
					if (!checkNotEmpty(createdDas.getCreationTime(), "creatiomTime")) {
						return false;
					}

					// lastModifiedTime
					if (!checkNotNull(createdDas.getLastModifiedTime(), "lastModifiedTime")) {
						return false;
					}
					if (!checkNotEmpty(createdDas.getLastModifiedTime(), "lastModifiedTime")) {
						return false;
					}

					// labels
					if (!checkNotNull(createdDas.getLabels(), "labels")) {
						return false;
					}
					if (createdDas.getLabels().size() != 0) {
						setState(State.KO);
						setMessage("no label expected, found " + createdDas.getLabels().toString());
						return false;
					}

					// dynamicAuthorizationEnabled
					if (!checkNotNull(createdDas.getDynamicAuthorizationEnabled(), "dynamicAuthorizationEnabled")) {
						return false;
					}
					if (!checkEquals(createdDas.getDynamicAuthorizationEnabled(), enabled,
							"dynamicAuthorizationEnabled")) {
						return false;
					}

					// dynamicAuthorizationPoA
					if (!checkEquals(createdDas.getDynamicAuthorisationPoA(), poa, "dynamicAuthorizationPoA")) {
						return false;
					}

					// dynamicAuthorizationLifetime
					if (!checkNotNull(createdDas.getDynamicAuthorizationLifetime(), "dynamicAuthorizationLifetime")) {
						return false;
					}

				} catch (ClassCastException e) {
					setState(State.KO);
					setMessage("unable to retrieve created DynamicAuthorizationConsultation as a Java object");
					return false;
				}

			} else {
				// KO
				setState(State.KO);
				setMessage("unexpected " + response.getResponseStatusCode() + " response status code. Expected "
						+ ResponseStatusCode.CREATED);
				return false;
			}
		} else {
			// KO
			setState(State.KO);
			setMessage("response is null");
			return false;
		}
		return true;

	}

}
