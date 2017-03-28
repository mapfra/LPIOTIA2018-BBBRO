package org.eclipse.om2m.das.testsuite.dasservice;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.exceptions.AccessDeniedException;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.das.service.DynamicAuthorizationServerService;
import org.eclipse.om2m.das.testsuite.Test.State;
import org.osgi.framework.ServiceRegistration;

public class DASServiceTest_TwoDAS extends DASServiceTest {

	private int nbOfCallDas1 = 0;
	private int nbOfCallDas2 = 0;
	
	public DASServiceTest_TwoDAS(CseService pCseService) {
		super("DasService Two services", pCseService);
	}

	@Override
	public void performTest() {
		// create DAC
		final DynamicAuthorizationConsultation dac = createDAS();
		if (dac == null) {
			setState(State.KO);
			setMessage("unable to create dac");
			return;
		}

		// register this as a DynamicAuthorizationServerService
		ServiceRegistration<DynamicAuthorizationServerService> dassRegistration = registerDynamicAuthorizationServerService(
				new DynamicAuthorizationServerService() {

					@Override
					public String getPoA() {
						return dac.getDynamicAuthorisationPoA().get(0);
					}

					@Override
					public void authorize(RequestPrimitive request, ResourceEntity resourceEntity)
							throws AccessDeniedException {
						nbOfCallDas1++;
						throw new AccessDeniedException();
					}
				});

		ServiceRegistration<DynamicAuthorizationServerService> dassRegistration2 = registerDynamicAuthorizationServerService(
				new DynamicAuthorizationServerService() {

					@Override
					public String getPoA() {
						return dac.getDynamicAuthorisationPoA().get(1);
					}

					@Override
					public void authorize(RequestPrimitive request, ResourceEntity resourceEntity)
							throws AccessDeniedException {
						nbOfCallDas2++;
						throw new AccessDeniedException();
					}
				});

		// create application (with DynamicAuthorizationConsultationIDs)
		List<String> dacis = new ArrayList<>();
		dacis.add(dac.getResourceID());
		AE ae = createAE(dacis);
		if (ae == null) {
			setState(State.KO);
			setMessage("unable to create AE");
			return;
		}

		// retrieve ae ==> DASS must be called
		ResponsePrimitive response = retrieveEntity(ae.getResourceID(), "nom:password");
		if (!ResponseStatusCode.ACCESS_DENIED.equals(response.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("expecting " + ResponseStatusCode.ACCESS_DENIED + ", found ="
					+ response.getResponseStatusCode());
			return;
		}

		// unregister DASS
		unregisterDynamicAuthorizationServerService(dassRegistration);
		unregisterDynamicAuthorizationServerService(dassRegistration2);
		
		// check nb of call for dac1
		if (nbOfCallDas1 != 1) {
			setState(State.KO);
			setMessage("expect 1 call on das1, received " + nbOfCallDas1 + " call");
			return;
		}
		
		// check nb of call for dac2
		if (nbOfCallDas2 != 1) {
			setState(State.KO);
			setMessage("expect 1 call on das2, received " + nbOfCallDas2 + " call");
			return;
		}
		
		setState(State.OK);

	}

}
