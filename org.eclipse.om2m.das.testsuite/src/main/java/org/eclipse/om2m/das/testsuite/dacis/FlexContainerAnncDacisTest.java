package org.eclipse.om2m.das.testsuite.dacis;

import java.util.ArrayList;
import java.util.UUID;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AEAnnc;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.FlexContainerAnnc;
import org.eclipse.om2m.commons.resource.RemoteCSE;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.das.testsuite.Test;

public class FlexContainerAnncDacisTest extends Test {

	public FlexContainerAnncDacisTest(CseService pCseService) {
		super("FlexContainerAnnc Dacis", pCseService);
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
		
		// 2017/03/22 : An FlexContainerAnnc entity could only be located under an AeAnnc.
		// an AeAnnc could only be located under a RemoteCse
		// so we are going to create a RemoteCse and an AeAnnc to be able to create the FlexContainerAnnc
		// object
		// it is a current limitation of oM2M
		
		// create a RemoteCse
		RemoteCSE remoteCse = createRemoteCse();
		if (remoteCse == null) {
			// ko
			setState(State.KO);
			setMessage("unable to create RemoteCse");
			return;
		}
		
		AEAnnc aeAnnc = createAeAnnc(remoteCse.getResourceID());
		if (aeAnnc == null) {
			// ko
			setState(State.KO);
			setMessage("unable to create AeAnnc");
			return;
		}
		

		// create a FlexContainerAnnc with a dynamicAuthorizationConsultationsId
		FlexContainerAnnc toBeCreatedFlexContainerAnnc = new FlexContainerAnnc();
		toBeCreatedFlexContainerAnnc.getDynamicAuthorizationConsultationIDs().add(dac.getResourceID());
		toBeCreatedFlexContainerAnnc.setContainerDefinition("myDef");
		toBeCreatedFlexContainerAnnc.setLink("/FlexContainerAnnc" + UUID.randomUUID());
		toBeCreatedFlexContainerAnnc.setName("FlexContainerAnnc_" + UUID.randomUUID());

		// prepare CREATE request
		RequestPrimitive createRequest = new RequestPrimitive();
		createRequest.setOperation(Operation.CREATE);
		createRequest.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		createRequest.setTargetId(aeAnnc.getResourceID());
		createRequest.setRequestContentType(MimeMediaType.OBJ);
		createRequest.setReturnContentType(MimeMediaType.OBJ);
		createRequest.setResourceType(ResourceType.FLEXCONTAINER_ANNC);
		createRequest.setContent(toBeCreatedFlexContainerAnnc);

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
			setMessage("unable to create FlexContainerAnnc: expecting " + ResponseStatusCode.CREATED + ", found "
					+ createResponse.getResponseStatusCode());
			return;
		}

		FlexContainerAnnc createdFlexContainerAnnc = null;
		try {
			createdFlexContainerAnnc = (FlexContainerAnnc) createResponse.getContent();
		} catch (ClassCastException e) {
			setState(State.KO);
			setMessage("expected response content is not a FlexContainerAnnc");
			return;
		}

		// check dacis
		if (!checkEquals(createdFlexContainerAnnc.getDynamicAuthorizationConsultationIDs(),
				toBeCreatedFlexContainerAnnc.getDynamicAuthorizationConsultationIDs(), "dynamicAuthorizationConsultationIDs")) {
			return;
		}

		// execute RETRIEVE request
		ResponsePrimitive retrieveResponse = retrieveEntity(createdFlexContainerAnnc.getResourceID());
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
			setMessage("unable to retrieve FlexContainerAnnc: expecting " + ResponseStatusCode.OK + ", found "
					+ createResponse.getResponseStatusCode());
			return;
		}

		FlexContainerAnnc retrievedFlexContainerAnnc = null;
		try {
			retrievedFlexContainerAnnc = (FlexContainerAnnc) retrieveResponse.getContent();
		} catch (ClassCastException e) {
			setState(State.KO);
			setMessage("expected response content is not a FlexContainerAnnc");
			return;
		}

		// check dacis
		if (!checkEquals(retrievedFlexContainerAnnc.getDynamicAuthorizationConsultationIDs(),
				toBeCreatedFlexContainerAnnc.getDynamicAuthorizationConsultationIDs(), "dynamicAuthorizationConsultationIDs")) {
			return;
		}
		
		// delete dac
		RequestPrimitive deleteRequest = new RequestPrimitive();
		deleteRequest.setOperation(Operation.DELETE);
		deleteRequest.setTargetId(dac.getResourceID());
		deleteRequest.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		ResponsePrimitive deleteResponse = getCseService().doRequest(deleteRequest);
		if (!ResponseStatusCode.DELETED.equals(deleteResponse.getResponseStatusCode())) {
			// ko
			setState(State.KO);
			setMessage("unable to delete DAC");
			return;
		}
		
		// execute RETRIEVE request
		retrieveResponse = retrieveEntity(createdFlexContainerAnnc.getResourceID());
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
			setMessage("unable to retrieve FlexContainerAnnc: expecting " + ResponseStatusCode.OK + ", found "
					+ createResponse.getResponseStatusCode());
			return;
		}

		retrievedFlexContainerAnnc = null;
		try {
			retrievedFlexContainerAnnc = (FlexContainerAnnc) retrieveResponse.getContent();
		} catch (ClassCastException e) {
			setState(State.KO);
			setMessage("expected response content is not a FlexContainerAnnc");
			return;
		}

		// check dacis
		if (!checkEquals(retrievedFlexContainerAnnc.getDynamicAuthorizationConsultationIDs(),
				new ArrayList<String>(), "dynamicAuthorizationConsultationIDs")) {
			return;
		}

		// OK
		setState(State.OK);
	}

}
