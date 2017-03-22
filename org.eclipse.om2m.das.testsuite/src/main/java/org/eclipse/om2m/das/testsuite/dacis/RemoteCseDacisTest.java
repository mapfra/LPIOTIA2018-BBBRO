package org.eclipse.om2m.das.testsuite.dacis;

import java.util.UUID;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RemoteCSE;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.das.testsuite.Test;

public class RemoteCseDacisTest extends Test {

	public RemoteCseDacisTest(CseService pCseService) {
		super("Remote Cse Dacis", pCseService);
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

		// create a RemoteCse with a dynamicAuthorizationConsultationsId
		RemoteCSE toBeCreatedRemoteCse = new RemoteCSE();
		toBeCreatedRemoteCse.getDynamicAuthorizationConsultationIDs().add(dac.getResourceID());
		toBeCreatedRemoteCse.setRequestReachability(Boolean.FALSE);
		toBeCreatedRemoteCse.setCSEBase("/cseBase" + UUID.randomUUID());
		toBeCreatedRemoteCse.setCSEID("cseId" + UUID.randomUUID());

		// prepare CREATE request
		RequestPrimitive createRequest = new RequestPrimitive();
		createRequest.setOperation(Operation.CREATE);
		createRequest.setName("RemoteCSE_" + UUID.randomUUID());
		createRequest.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		createRequest.setTargetId("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME);
		createRequest.setRequestContentType(MimeMediaType.OBJ);
		createRequest.setReturnContentType(MimeMediaType.OBJ);
		createRequest.setResourceType(ResourceType.REMOTE_CSE);
		createRequest.setContent(toBeCreatedRemoteCse);

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
			setMessage("unable to create RemoteCse: expecting " + ResponseStatusCode.CREATED + ", found "
					+ createResponse.getResponseStatusCode());
			return;
		}

		RemoteCSE createdRemoteCse = null;
		try {
			createdRemoteCse = (RemoteCSE) createResponse.getContent();
		} catch (ClassCastException e) {
			setState(State.KO);
			setMessage("expected response content is not a RemoteCse");
			return;
		}

		// check dacis
		if (!checkEquals(createdRemoteCse.getDynamicAuthorizationConsultationIDs(),
				toBeCreatedRemoteCse.getDynamicAuthorizationConsultationIDs(), "dynamicAuthorizationConsultationIDs")) {
			return;
		}

		// prepare RETRIEVE request
		RequestPrimitive retrieveRequest = new RequestPrimitive();
		retrieveRequest.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		retrieveRequest.setOperation(Operation.RETRIEVE);
		retrieveRequest.setTargetId(createdRemoteCse.getResourceID());
		retrieveRequest.setReturnContentType(MimeMediaType.OBJ);

		// execute RETRIEVE request
		ResponsePrimitive retrieveResponse = getCseService().doRequest(retrieveRequest);
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
			setMessage("unable to retrieve RemoteCse: expecting " + ResponseStatusCode.OK + ", found "
					+ createResponse.getResponseStatusCode());
			return;
		}

		RemoteCSE retrievedRemoteCse = null;
		try {
			retrievedRemoteCse = (RemoteCSE) retrieveResponse.getContent();
		} catch (ClassCastException e) {
			setState(State.KO);
			setMessage("expected response content is not a RemoteCse");
			return;
		}

		// check dacis
		if (!checkEquals(retrievedRemoteCse.getDynamicAuthorizationConsultationIDs(),
				toBeCreatedRemoteCse.getDynamicAuthorizationConsultationIDs(), "dynamicAuthorizationConsultationIDs")) {
			return;
		}

		// OK
		setState(State.OK);
	}

}
