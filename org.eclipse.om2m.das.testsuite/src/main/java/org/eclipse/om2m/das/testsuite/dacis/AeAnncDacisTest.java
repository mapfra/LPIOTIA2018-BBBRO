package org.eclipse.om2m.das.testsuite.dacis;

import java.util.UUID;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AEAnnc;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RemoteCSE;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.das.testsuite.Test;

public class AeAnncDacisTest extends Test {

	public AeAnncDacisTest(CseService pCseService) {
		super("Ae Annc Dacis", pCseService);
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
		
		// 2017/03/22 : An AeAnnc entity could only be located under a RemoteCse
		// it is a current limitation of oM2M
		
		// create a RemoteCse
		RemoteCSE remoteCse = createRemoteCse();
		if (remoteCse == null) {
			// ko
			setState(State.KO);
			setMessage("unable to create RemoteCse");
			return;
		}
		

		// create a AeAnnc with a dynamicAuthorizationConsultationsId
		AEAnnc toBeCreatedAeAnnc = new AEAnnc();
		toBeCreatedAeAnnc.getDynamicAuthorizationConsultationIDs().add(dac.getResourceID());
		toBeCreatedAeAnnc.setAppID("App" + UUID.randomUUID());
		toBeCreatedAeAnnc.setLink("/link" + UUID.randomUUID());

		// prepare CREATE request
		RequestPrimitive createRequest = new RequestPrimitive();
		createRequest.setOperation(Operation.CREATE);
		createRequest.setName(toBeCreatedAeAnnc.getAppID());
		createRequest.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		createRequest.setTargetId(remoteCse.getResourceID());
		createRequest.setRequestContentType(MimeMediaType.OBJ);
		createRequest.setReturnContentType(MimeMediaType.OBJ);
		createRequest.setResourceType(ResourceType.AE_ANNC);
		createRequest.setContent(toBeCreatedAeAnnc);

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
			setMessage("unable to create AeAnnc: expecting " + ResponseStatusCode.CREATED + ", found "
					+ createResponse.getResponseStatusCode());
			return;
		}

		AEAnnc createdAeAnnc = null;
		try {
			createdAeAnnc = (AEAnnc) createResponse.getContent();
		} catch (ClassCastException e) {
			setState(State.KO);
			setMessage("expected response content is not a AeAnnc");
			return;
		}

		// check dacis
		if (!checkEquals(createdAeAnnc.getDynamicAuthorizationConsultationIDs(),
				toBeCreatedAeAnnc.getDynamicAuthorizationConsultationIDs(), "dynamicAuthorizationConsultationIDs")) {
			return;
		}

		// prepare RETRIEVE request
		RequestPrimitive retrieveRequest = new RequestPrimitive();
		retrieveRequest.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		retrieveRequest.setOperation(Operation.RETRIEVE);
		retrieveRequest.setTargetId(createdAeAnnc.getResourceID());
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
			setMessage("unable to retrieve AeAnnc: expecting " + ResponseStatusCode.OK + ", found "
					+ createResponse.getResponseStatusCode());
			return;
		}

		AEAnnc retrievedAeAnnc = null;
		try {
			retrievedAeAnnc = (AEAnnc) retrieveResponse.getContent();
		} catch (ClassCastException e) {
			setState(State.KO);
			setMessage("expected response content is not a AeAnnc");
			return;
		}

		// check dacis
		if (!checkEquals(retrievedAeAnnc.getDynamicAuthorizationConsultationIDs(),
				toBeCreatedAeAnnc.getDynamicAuthorizationConsultationIDs(), "dynamicAuthorizationConsultationIDs")) {
			return;
		}

		// OK
		setState(State.OK);
	}

}
