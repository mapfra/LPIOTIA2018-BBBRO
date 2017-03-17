package org.eclipse.om2m.das.testsuite.crud;

import java.util.UUID;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;

public class CreateDAS_Application_Test extends CreateDAS_CseBase_Test {

	public CreateDAS_Application_Test(CseService pCseService) {
		super("Create DAS - Application", pCseService);
	}

	@Override
	public void performTest() {

		// create AE
		AE ae = createAe();
		if (ae == null) {
			setState(State.KO);
			setMessage("unable to create application");
			return;
		}
		
		// using non hierarchical address
		if (!createAndCheck(ae.getResourceID())) {
			// KO
			return;
		}
		
		// using hierarchical address
		if (!createAndCheck("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + ae.getName())) {
			return;
		}
		
		setState(State.OK);
		
	}
	
	/**
	 * Create a fake AE.
	 * @return
	 */
	private AE createAe() {
		AE ae = new AE();
		
		ae.setAppID("1234");
		ae.setAppName("appName" + UUID.randomUUID());
		ae.setRequestReachability(Boolean.FALSE);
		
		RequestPrimitive request = new RequestPrimitive();
		request.setName(ae.getAppName());
		request.setOperation(Operation.CREATE);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setResourceType(ResourceType.AE);
		request.setTargetId("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setContent(ae);
		
		// execute
		ResponsePrimitive response = getCseService().doRequest(request);
		if ((response != null) && (ResponseStatusCode.CREATED.equals(response.getResponseStatusCode()))) {
			return (AE) response.getContent();
		}
		
		
		return null;
	}
}
