package org.eclipse.om2m.das.testsuite.crud;

import java.util.UUID;

import org.eclipse.om2m.commons.constants.CSEType;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.RemoteCSE;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;

public class CreateDAS_RemoteCSE_Test extends CreateDAS_CseBase_Test {

	public CreateDAS_RemoteCSE_Test(CseService pCseService) {
		super("Create DAS - RemoteCSE", pCseService);
	}
	
	@Override
	public void performTest() {
		RemoteCSE remoteCse = createRemoteCSE();
		if (remoteCse == null) {
			setState(State.KO);
			setMessage("unable to create RemoteCse");
			return;
		}
		
		// check non hierarchical uri
		if (!createAndCheck(remoteCse.getResourceID())) {
			return;
		}
		
		// check hierarchical uri
		if (!createAndCheck("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + remoteCse.getName())) {
			return;
		}
		
		setState(State.OK);
	}
	
	
	/**
	 * Create a fake AE.
	 * @return
	 */
	private RemoteCSE createRemoteCSE() {
		RemoteCSE remoteCse = new RemoteCSE();
		
		remoteCse.setCseType(CSEType.IN_CSE);
		remoteCse.setCSEID("cseId" + UUID.randomUUID());
		remoteCse.setCSEBase("/base" + remoteCse.getCSEID());
		remoteCse.setRequestReachability(Boolean.FALSE);
		
		
		RequestPrimitive request = new RequestPrimitive();
		request.setName(remoteCse.getCSEID());
		request.setOperation(Operation.CREATE);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setResourceType(ResourceType.REMOTE_CSE);
		request.setTargetId("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setContent(remoteCse);
		
		// execute
		ResponsePrimitive response = getCseService().doRequest(request);
		if ((response != null) && (ResponseStatusCode.CREATED.equals(response.getResponseStatusCode()))) {
			return (RemoteCSE) response.getContent();
		}
		
		
		return null;
	}

}
