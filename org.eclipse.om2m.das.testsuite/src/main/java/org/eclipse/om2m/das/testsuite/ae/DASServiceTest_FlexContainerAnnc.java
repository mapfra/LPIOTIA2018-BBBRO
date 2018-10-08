/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.das.testsuite.ae;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AEAnnc;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RemoteCSE;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.flexcontainerspec.BinarySwitchFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ToggleFlexContainerAnnc;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.osgi.framework.ServiceRegistration;

public class DASServiceTest_FlexContainerAnnc extends AbstractDASServiceTest {

	/**
	 * To be used by activator
	 * 
	 * @param pCseService
	 */
	public DASServiceTest_FlexContainerAnnc(CseService pCseService) {
		super("DasServiceTest_FlexContainerAnnc", pCseService);
	}

	@Override
	public void performTest() {

		// create DAC
		DynamicAuthorizationConsultation dac = createDAS(getDasAE().getResourceID());
		if (dac == null) {
			setState(State.KO);
			setMessage("unable to create dac");
			return;
		}

		// set number of expected call
		setExpectedNumberOfCall(1);

		// register this as a DynamicAuthorizationServerService
		ServiceRegistration<InterworkingService> interworkingServiceRegistration = registerInterworkingService(
				this);
		
		// create the following hierarchy : RemoteCse > AeAnnc > FlexContainerAnnc
		RemoteCSE remoteCse = createRemoteCse();
		if (remoteCse == null) {
			setState(State.KO);
			setMessage("unable to create a RemoteCse");
			return;
		}
		
		// create an AeAnnc
		AEAnnc aeAnnc = createAeAnnc(remoteCse.getResourceID());
		if (aeAnnc == null) {
			setState(State.KO);
			setMessage("unable to create a AeAnnc");
			return;
		}

		// create flexContainerAnnc (with DynamicAuthorizationConsultationIDs)
		List<String> dacis = new ArrayList<>();
		dacis.add(dac.getResourceID());
		BinarySwitchFlexContainerAnnc createdFlexContainerAnnc = createFlexContainerAnnc(aeAnnc.getResourceID(), dacis);
		if (createdFlexContainerAnnc == null) {
			setState(State.KO);
			setMessage("unable to create FlexContainerAnnc");
			return;
		}

		// retrieve flexContainer ==> DASS must be called
		ResponsePrimitive response = retrieveEntity(createdFlexContainerAnnc.getResourceID(), "nom:password");
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unable to retrieve FlexContainerAnnc, expecting " + ResponseStatusCode.OK + ", found ="
					+ response.getResponseStatusCode());
			return;
		}

		if (!checkCall(0, createdFlexContainerAnnc.getResourceID(), "nom:password", Operation.RETRIEVE)) {
			// KO
			return;
		}

		// clear calls
		clearCalls();

		// update FlexContainer
		BinarySwitchFlexContainerAnnc toBeUpdated = new BinarySwitchFlexContainerAnnc();
		CustomAttribute customAttribute = new CustomAttribute();
		customAttribute.setShortName("powSe");
		customAttribute.setValue("true");
		createdFlexContainerAnnc.getCustomAttributes().add(customAttribute);

		// prepare update request
		RequestPrimitive updateRequest = new RequestPrimitive();
		updateRequest.setFrom("nom:prenom");
		updateRequest.setTo(createdFlexContainerAnnc.getResourceID());
		updateRequest.setOperation(Operation.UPDATE);
		updateRequest.setRequestContentType(MimeMediaType.OBJ);
		updateRequest.setReturnContentType(MimeMediaType.OBJ);
		updateRequest.setContent(toBeUpdated);

		// execute update request
		ResponsePrimitive updateResponse = getCseService().doRequest(updateRequest);
		if (updateResponse == null) {
			setState(State.KO);
			setMessage("updateFlexContainerAnnc response is null");
			return;
		}
		if (!ResponseStatusCode.UPDATED.equals(updateResponse.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unable to update FlexContainerAnnc, expecting " + ResponseStatusCode.UPDATED + ", found "
					+ updateResponse.getResponseStatusCode());
			return;
		}

		// check call
		if (!checkCall(0, createdFlexContainerAnnc.getResourceID(), "nom:prenom", Operation.UPDATE)) {
			// KO
			return;
		}

		// clear calls
		clearCalls();

		// create a new childFlexContainer
		ToggleFlexContainerAnnc flexContainerAnncChildToBeCreated = new ToggleFlexContainerAnnc();
		flexContainerAnncChildToBeCreated.setLink("/link" + UUID.randomUUID());
		flexContainerAnncChildToBeCreated.setName("FlexContainer_" + UUID.randomUUID());
		
		// prepare child creation request
		RequestPrimitive createChildRequest = new RequestPrimitive();
		createChildRequest.setContent(flexContainerAnncChildToBeCreated);
		createChildRequest.setRequestContentType(MimeMediaType.OBJ);
		createChildRequest.setReturnContentType(MimeMediaType.OBJ);
		createChildRequest.setResourceType(ResourceType.FLEXCONTAINER_ANNC);
		createChildRequest.setFrom("nana:nono");
		createChildRequest.setTo(createdFlexContainerAnnc.getResourceID());
		createChildRequest.setOperation(Operation.CREATE);

		// execute createChildRequest
		ResponsePrimitive createChildResponse = getCseService().doRequest(createChildRequest);
		if (createChildResponse == null) {
			setState(State.KO);
			setMessage("create child response is null");
			return;
		}
		if (!ResponseStatusCode.CREATED.equals(createChildResponse.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unable to create a child FlexContainerAnnc, expecting " + ResponseStatusCode.CREATED + ", found "
					+ createChildResponse.getResponseStatusCode());
			return;
		}
		// check call
		if (!checkCall(0, createdFlexContainerAnnc.getResourceID(), "nana:nono", Operation.CREATE)) {
			// KO
			return;
		}

		// clear calls
		clearCalls();

		// delete FlexContainerAnnc
		RequestPrimitive deleteRequest = new RequestPrimitive();
		deleteRequest.setTo(createdFlexContainerAnnc.getResourceID());
		deleteRequest.setFrom("toto:tata");
		deleteRequest.setOperation(Operation.DELETE);

		// execute delete request
		ResponsePrimitive deleteResponse = getCseService().doRequest(deleteRequest);
		if (deleteResponse == null) {
			setState(State.KO);
			setMessage("deleteFlexContainerAnnc response is null");
			return;
		}

		if (!ResponseStatusCode.DELETED.equals(deleteResponse.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unable to delete FlexContainerAnnc, expecting " + ResponseStatusCode.DELETED + ", found "
					+ deleteResponse.getResponseStatusCode());
			return;
		}

		// check call
		if (!checkCall(0, createdFlexContainerAnnc.getResourceID(), "toto:tata", Operation.DELETE)) {
			// KO
			return;
		}

		// unregister InterworkingService
		unregisterInterworkingService(interworkingServiceRegistration);

		setState(State.OK);
	}

	

}
