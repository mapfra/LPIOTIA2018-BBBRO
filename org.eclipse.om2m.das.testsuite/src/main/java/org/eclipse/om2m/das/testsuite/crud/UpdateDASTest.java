package org.eclipse.om2m.das.testsuite.crud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.das.testsuite.Test;

public class UpdateDASTest extends Test {

	public UpdateDASTest(CseService pCseService) {
		super("Update DAS", pCseService);
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

		// update - non hierarchical uri
		if (!updateDas(createdDas.getResourceID(), createdDas)) {
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
		// update - hierarchical uri
		if (!updateDas("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + createdDas.getName(), createdDas)) {
			// KO
			return;
		}

		// OK
		setState(State.OK);

	}

	private boolean updateDas(String dasUrl, DynamicAuthorizationConsultation createdDas) {
		// wait 1s to be sure about the lastModifiedTime
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
		}

		Boolean enabled = Boolean.FALSE;
		List<String> poa = new ArrayList<>();
		poa.add("poa3");
		poa.add("poa4");
		poa.add("poa5");
		String lifetime = new Date().toString();

		// new DAS
		DynamicAuthorizationConsultation toBeUpdateDas = new DynamicAuthorizationConsultation();
		toBeUpdateDas.setDynamicAuthorisationPoA(poa);
		toBeUpdateDas.setDynamicAuthorizationEnabled(enabled);
		toBeUpdateDas.setDynamicAuthorizationLifetime(lifetime);

		// set up request
		RequestPrimitive request = new RequestPrimitive();
		request.setContent(toBeUpdateDas);
		request.setOperation(Operation.UPDATE);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTargetId(dasUrl);

		// perform request
		ResponsePrimitive response = getCseService().doRequest(request);
		if (response != null) {
			if (!ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
				setState(State.KO);
				setMessage("expecting " + ResponseStatusCode.UPDATED + " status code, found "
						+ response.getResponseStatusCode());
				return false;
			}

			DynamicAuthorizationConsultation updatedDac = null;
			try {
				updatedDac = (DynamicAuthorizationConsultation) response.getContent();
			} catch (ClassCastException e) {
				setState(State.KO);
				setMessage("unable to cast content as a DynamicAuthorizationConsultation");
				return false;
			}

			// at this point we are sure that updatedDac != null

			// resourceName
			if (!checkNull(updatedDac.getName(), "name")) {
				return false;
			}

			// resourceType
			if (!checkNull(updatedDac.getResourceType(), "type")) {
				return false;
			}

			// resourceId
			if (!checkNull(updatedDac.getResourceID(), "resourceID")) {
				return false;
			}

			// parentID
			if (!checkNull(updatedDac.getParentID(), "parentID")) {
				return false;
			}

			// expirationTime
			if (!checkNull(updatedDac.getExpirationTime(), "expirationTime")) {
				return false;
			}

			// accessControlPolicies
			if (!checkEmpty(updatedDac.getAccessControlPolicyIDs(), "accessControlPolicyIDs")) {
				return false;
			}

			// creationTime
			if (!checkNull(updatedDac.getCreationTime(), "creationTime")) {
				return false;
			}

			// lastModifiedTime
			if (!checkNotEmpty(updatedDac.getLastModifiedTime(), "lastModifiedTime")) {
				return false;
			}
			if (updatedDac.getLastModifiedTime().equals(createdDas.getLastModifiedTime())) {
				setState(State.KO);
				setMessage("lastModifiedTime must be different");
				return false;
			}

			// labels
			if (!checkEmpty(updatedDac.getLabels(), "labels")) {
				return false;
			}

			// dynamicAuthorizationConsultationIDs
			// TODO

			// dynamicAuthorizationEnabled
			if (!checkEquals(updatedDac.getDynamicAuthorizationEnabled(), enabled, "dynamicAuthorizationEnabled")) {
				return false;
			}

			// dynamicAuthorizationPoA
			if (!checkEquals(updatedDac.getDynamicAuthorisationPoA(), poa, "dynamicAuthorizationPoA")) {
				return false;
			}

			// dynamicAuthorizationLifetime
			if (!checkEquals(updatedDac.getDynamicAuthorizationLifetime(), lifetime, "dynamicAuthorizationLifetime")) {
				return false;
			}

		} else {
			setState(State.KO);
			setMessage("response is null");
			return false;
		}

		return true;
	}

}
