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
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AEAnnc;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RemoteCSE;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.osgi.framework.ServiceRegistration;

public class DASServiceTest_AeAnnc extends AbstractDASServiceTest {

	/**
	 * To be used by activator
	 * 
	 * @param pCseService
	 */
	public DASServiceTest_AeAnnc(CseService pCseService) {
		super("DasServiceTest_AeAnnc", pCseService);
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

		// register this as a InterworkingService
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
		List<String> dacis = new ArrayList<>();
		dacis.add(dac.getResourceID());
		AEAnnc createdAeAnnc = createAeAnnc(remoteCse.getResourceID(), dacis);
		if (createdAeAnnc == null) {
			setState(State.KO);
			setMessage("unable to create a AeAnnc");
			return;
		}


		// retrieve aeAnnc ==> DASS must be called
		ResponsePrimitive response = retrieveEntity(createdAeAnnc.getResourceID(), "nom:password");
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unable to retrieve AeAnnc, expecting " + ResponseStatusCode.OK + ", found ="
					+ response.getResponseStatusCode());
			return;
		}

		if (!checkCall(0, createdAeAnnc.getResourceID(), "nom:password", Operation.RETRIEVE)) {
			// KO
			return;
		}

		// clear calls
		clearCalls();

		// update AeAnnc
		AEAnnc toBeUpdated = new AEAnnc();
		toBeUpdated.setLink("/link" + UUID.randomUUID());

		// prepare update request
		RequestPrimitive updateRequest = new RequestPrimitive();
		updateRequest.setFrom("nom:prenom");
		updateRequest.setTargetId(createdAeAnnc.getResourceID());
		updateRequest.setOperation(Operation.UPDATE);
		updateRequest.setRequestContentType(MimeMediaType.OBJ);
		updateRequest.setReturnContentType(MimeMediaType.OBJ);
		updateRequest.setContent(toBeUpdated);

		// execute update request
		ResponsePrimitive updateResponse = getCseService().doRequest(updateRequest);
		if (updateResponse == null) {
			setState(State.KO);
			setMessage("updateAeAnnc response is null");
			return;
		}
		if (!ResponseStatusCode.UPDATED.equals(updateResponse.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unable to update AeAnnc, expecting " + ResponseStatusCode.UPDATED + ", found "
					+ updateResponse.getResponseStatusCode());
			return;
		}

		// check call
		if (!checkCall(0, createdAeAnnc.getResourceID(), "nom:prenom", Operation.UPDATE)) {
			// KO
			return;
		}

		// clear calls
		clearCalls();

		// delete AeAnnc
		RequestPrimitive deleteRequest = new RequestPrimitive();
		deleteRequest.setTargetId(createdAeAnnc.getResourceID());
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
		if (!checkCall(0, createdAeAnnc.getResourceID(), "toto:tata", Operation.DELETE)) {
			// KO
			return;
		}

		// unregister InterworkingService
		unregisterInterworkingService(interworkingServiceRegistration);

		setState(State.OK);
	}

	

}
