package org.eclipse.om2m.das.testsuite.ae;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.osgi.framework.ServiceRegistration;

public class DASServiceTest_IndirectDACIs extends AbstractDASServiceTest {

	public DASServiceTest_IndirectDACIs(CseService pCseService) {
		super("DASService Indirect DACIs", pCseService);
	}

	@Override
	public void performTest() {
		// This test performs the following steps
		// 1 - creates a DAC
		// 2 - create a FlexContainer and associate it with the created DAC
		// 3 - create a flexContainer child belonging to the flexcontainer
		// created at step 2.This newly created flexContainer is not associated
		// with a DAC ==> we must use the DAC of its parent.
		// 4 - retrieve the child flexContainer ==> its parent DAC must be use
		// 5 - create a new flexContainer entity whose the parent is the
		// flexContainer created at step 3. This flexContainer is not associated
		// with a DAC ==> it should use the DAC of the flexContainer created at
		// step 2
		// 6 - retrieve the flexContainer created at step 5 ==> the DAC of the
		// flexContainer created at step 2 must be used

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

		// create a FlexContainer
		List<String> dacis = new ArrayList<>();
		dacis.add(dac.getResourceID());
		FlexContainer parentFlexContainer = createFlexContainer(dacis);
		if (parentFlexContainer == null) {
			setState(State.KO);
			setMessage("unable to create parentFlexContainer");
			return;
		}

		// create a child flexContainer
		FlexContainer childFlexContainer = new FlexContainer();
		childFlexContainer.setContainerDefinition("grege");
		childFlexContainer.setName("FlexContainer_" + UUID.randomUUID());

		// prepare childCreateRequest
		RequestPrimitive childCreateRequest = new RequestPrimitive();
		childCreateRequest.setFrom("mama:mimi");
		childCreateRequest.setTargetId(parentFlexContainer.getResourceID());
		childCreateRequest.setOperation(Operation.CREATE);
		childCreateRequest.setRequestContentType(MimeMediaType.OBJ);
		childCreateRequest.setReturnContentType(MimeMediaType.OBJ);
		childCreateRequest.setResourceType(ResourceType.FLEXCONTAINER);
		childCreateRequest.setContent(childFlexContainer);

		// execute childCreateRequest
		ResponsePrimitive childCreateResponse = getCseService().doRequest(childCreateRequest);
		if (childCreateResponse == null) {
			setState(State.KO);
			setMessage("childCreateResponse is null");
			return;
		}
		if (!ResponseStatusCode.CREATED.equals(childCreateResponse.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unable to create child FlexContainer, expecting " + ResponseStatusCode.CREATED + ", found="
					+ childCreateResponse.getResponseStatusCode());
		}
		FlexContainer createdChildFlexContainer = (FlexContainer) childCreateResponse.getContent();

		// check DASS call
		if (!checkCall(0, parentFlexContainer.getResourceID(), "mama:mimi", Operation.CREATE)) {
			return;
		}

		// clear calls
		clearCalls();

		// retrieve child FlexContainer
		RequestPrimitive retrieveChildFlexContainerRequest = new RequestPrimitive();
		retrieveChildFlexContainerRequest.setFrom("sisi:sasa");
		retrieveChildFlexContainerRequest.setTargetId(createdChildFlexContainer.getResourceID());
		retrieveChildFlexContainerRequest.setOperation(Operation.RETRIEVE);
		retrieveChildFlexContainerRequest.setReturnContentType(MimeMediaType.OBJ);

		// execute retrieveChildFlexContainerRequest
		ResponsePrimitive retrieveChildFlexContainerResponse = getCseService()
				.doRequest(retrieveChildFlexContainerRequest);
		if (retrieveChildFlexContainerResponse == null) {
			setState(State.KO);
			setMessage("retrieveChildFlexContainerResponse is null");
			return;
		}
		if (!ResponseStatusCode.OK.equals(retrieveChildFlexContainerResponse.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unable to retrieve child FlexContainer, expecting " + ResponseStatusCode.OK + ", found "
					+ retrieveChildFlexContainerResponse.getResponseStatusCode());
			return;
		}
		// check call
		if (!checkCall(0, createdChildFlexContainer.getResourceID(), "sisi:sasa", Operation.RETRIEVE)) {
			return;
		}
		
		// clear calls
		clearCalls();

		// create grandson (with no dacis)
		FlexContainer grandSonFlexContainer = new FlexContainer();
		grandSonFlexContainer.setContainerDefinition("juju");
		grandSonFlexContainer.setName("FlexContainerGrandSon_" + UUID.randomUUID());

		// prepare createGrandSonRequest
		RequestPrimitive createGrandSonRequest = new RequestPrimitive();
		createGrandSonRequest.setOperation(Operation.CREATE);
		createGrandSonRequest.setFrom("juju:jaja");
		createGrandSonRequest.setTargetId(createdChildFlexContainer.getResourceID());
		createGrandSonRequest.setRequestContentType(MimeMediaType.OBJ);
		createGrandSonRequest.setReturnContentType(MimeMediaType.OBJ);
		createGrandSonRequest.setResourceType(ResourceType.FLEXCONTAINER);
		createGrandSonRequest.setContent(grandSonFlexContainer);

		// execute createGrandSonRequest
		ResponsePrimitive createGrandSonResponse = getCseService().doRequest(createGrandSonRequest);
		if (createGrandSonResponse == null) {
			setState(State.KO);
			setMessage("createGrandSonResponse is null");
			return;
		}
		if (!ResponseStatusCode.CREATED.equals(createGrandSonResponse.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unable to create grandSon, expecting " + ResponseStatusCode.CREATED + ", found="
					+ createGrandSonResponse.getResponseStatusCode());
			return;
		}
		FlexContainer createdGrandSonFlexContainer = (FlexContainer) createGrandSonResponse.getContent();

		// check call
		if (!checkCall(0, createdChildFlexContainer.getResourceID(), "juju:jaja", Operation.CREATE)) {
			return;
		}

		// clear calls
		clearCalls();

		// prepare retrieveGrandSonFlexContainerRequest
		RequestPrimitive retrieveGrandSonFlexContainerRequest = new RequestPrimitive();
		retrieveGrandSonFlexContainerRequest.setTargetId(createdGrandSonFlexContainer.getResourceID());
		retrieveGrandSonFlexContainerRequest.setReturnContentType(MimeMediaType.OBJ);
		retrieveGrandSonFlexContainerRequest.setOperation(Operation.RETRIEVE);
		retrieveGrandSonFlexContainerRequest.setFrom("xixi:xoxo");

		// execute retrieveGrandSonFlexContainerRequest
		ResponsePrimitive retrieveGrandSonFlexContainerResponse = getCseService()
				.doRequest(retrieveGrandSonFlexContainerRequest);
		if (retrieveGrandSonFlexContainerResponse == null) {
			setState(State.KO);
			setMessage("retrieveGrandSonFlexContainerResponse is null");
			return;
		}
		if (!ResponseStatusCode.OK.equals(retrieveGrandSonFlexContainerResponse.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unable to retrieve grandSonFlexContainer, expecting " + ResponseStatusCode.OK + ", found "
					+ retrieveGrandSonFlexContainerResponse.getResponseStatusCode());
			return;
		}
		
		// check call
		if (!checkCall(0, createdGrandSonFlexContainer.getResourceID(), "xixi:xoxo", Operation.RETRIEVE)) {
			return;
		}

		// unregister InterworkingService
		unregisterInterworkingService(interworkingServiceRegistration);

		// OK
		setState(State.OK);
	}

}
