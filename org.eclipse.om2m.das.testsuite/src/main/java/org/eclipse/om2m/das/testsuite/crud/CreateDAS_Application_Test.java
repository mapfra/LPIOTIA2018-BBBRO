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
	
	
}
