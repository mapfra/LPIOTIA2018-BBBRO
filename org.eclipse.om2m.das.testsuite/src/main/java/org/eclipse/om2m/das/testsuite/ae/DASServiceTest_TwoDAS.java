package org.eclipse.om2m.das.testsuite.ae;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.osgi.framework.ServiceRegistration;

public class DASServiceTest_TwoDAS extends AbstractDASServiceTest {

	private int nbOfCallDas1 = 0;
	private int nbOfCallDas2 = 0;
	
	public DASServiceTest_TwoDAS(CseService pCseService) {
		super("DasService Two services", pCseService);
	}

	@Override
	public void performTest() {
		
		// create dasAE1
		final AE dasAe1 = createAe();
		final AE dasAe2 = createAe();
		
		// create DAC
		List<String> poas = new ArrayList<>();
		poas.add(dasAe1.getResourceID());
		poas.add(dasAe2.getResourceID());
		final DynamicAuthorizationConsultation dac = createDAS(poas);
		if (dac == null) {
			setState(State.KO);
			setMessage("unable to create dac");
			return;
		}

		// register this as a InterworkingService
		ServiceRegistration<InterworkingService> interworkingServiceRegistration = registerInterworkingService(
				new InterworkingService() {
					
					@Override
					public String getAPOCPath() {
						// should ae1.getPoa
						return dasAe1.getPointOfAccess().get(0);
					}
					
					@Override
					public ResponsePrimitive doExecute(RequestPrimitive RequestPrimitive) {
						nbOfCallDas1++;
						ResponsePrimitive response = new ResponsePrimitive(RequestPrimitive);
						response.setResponseStatusCode(ResponseStatusCode.ACCESS_DENIED);
						return response;
					}
				});

		ServiceRegistration<InterworkingService> interworkingServiceRegistration2 = registerInterworkingService(
				new InterworkingService() {
					
					@Override
					public String getAPOCPath() {
						// should be ae2.getPoa
						return dasAe2.getPointOfAccess().get(0);
					}
					
					@Override
					public ResponsePrimitive doExecute(RequestPrimitive RequestPrimitive) {
						nbOfCallDas2++;
						ResponsePrimitive response = new ResponsePrimitive(RequestPrimitive);
						response.setResponseStatusCode(ResponseStatusCode.ACCESS_DENIED);
						return response;
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
		unregisterInterworkingService(interworkingServiceRegistration);
		unregisterInterworkingService(interworkingServiceRegistration2);
		
		// delete das ae
		deleteEntity(dasAe1.getResourceID());
		deleteEntity(dasAe2.getResourceID());
		
		// check nb of call for ae1
		if (nbOfCallDas1 != 1) {
			setState(State.KO);
			setMessage("expect 1 call on ae1, received " + nbOfCallDas1 + " call");
			return;
		}
		
		// check nb of call for ae2
		if (nbOfCallDas2 != 1) {
			setState(State.KO);
			setMessage("expect 1 call on ae2, received " + nbOfCallDas2 + " call");
			return;
		}
		
		setState(State.OK);

	}

}
