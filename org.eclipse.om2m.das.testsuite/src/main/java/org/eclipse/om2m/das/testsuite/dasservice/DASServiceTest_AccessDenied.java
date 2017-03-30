package org.eclipse.om2m.das.testsuite.dasservice;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.exceptions.AccessDeniedException;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.das.service.DynamicAuthorizationServerService;
import org.osgi.framework.ServiceRegistration;

public class DASServiceTest_AccessDenied extends AbstractDASServiceTest {
	
	private DynamicAuthorizationConsultation dac; 

	public DASServiceTest_AccessDenied(CseService pCseService) {
		super("DASService AccessDenied", pCseService);
	}

	@Override
	public void performTest() {
		// create DAC
		dac = createDAS();
		if (dac == null) {
			setState(State.KO);
			setMessage("unable to create dac");
			return;
		}

		// set poa
		setPoA(dac.getDynamicAuthorisationPoA().get(0));
		
		// set number of expected call
		setExpectedNumberOfCall(1);

		// register this as a DynamicAuthorizationServerService
		ServiceRegistration<DynamicAuthorizationServerService> dassRegistration = registerDynamicAuthorizationServerService(this);

		// create application (with DynamicAuthorizationConsultationIDs)
		List<String> dacis = new ArrayList<>();
		dacis.add(dac.getResourceID());
		AE ae = createAE(dacis);
		if (ae == null) {
			setState(State.KO);
			setMessage("unable to create AE");
			return;
		}

		// retrieve ae ==> DASS must be called && return AccessDenied
		ResponsePrimitive response = retrieveEntity(ae.getResourceID(), "nom:password");
		if (!ResponseStatusCode.ACCESS_DENIED.equals(response.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unexpected ResponseCode, expecting " + ResponseStatusCode.OK + ", found ="
					+ response.getResponseStatusCode());
			return;
		}

		// unregister DASS
		unregisterDynamicAuthorizationServerService(dassRegistration);
		
		// one call
		List<Call> receivedCalls = getCalls();
		if (receivedCalls.size() != 1) {
			setState(State.KO);
			setMessage("expecting 1 call, found " + receivedCalls.size());
			return;
		}
		
		Call receivedCall = receivedCalls.get(0);
		if (receivedCall.getRequestPrimitive() == null) {
			setMessage("request is null");
			setState(State.KO);
			return;
		}

		// targetId
		if (!receivedCall.getRequestPrimitive().getTargetId().equals(ae.getResourceID())) {
			setState(State.KO);
			setMessage("bad targetId, expecting " + ae.getResourceID() + ", found "
					+ receivedCall.getRequestPrimitive().getTargetId());
			return;
		}

		// from
		if (!receivedCall.getRequestPrimitive().getFrom().equals("nom:password")) {
			setState(State.KO);
			setMessage("bad caller credentials, expecting:" + "nom:password" + ", found "
					+ receivedCall.getRequestPrimitive().getFrom());
			return;
		}

		// operation
		if (!Operation.RETRIEVE.equals(receivedCall.getRequestPrimitive().getOperation())) {
			setState(State.KO);
			setMessage("bad operation, expecting:" + Operation.RETRIEVE + ", found "
					+ receivedCall.getRequestPrimitive().getOperation());
			return;
		}

		// check resource entity
		if (receivedCall.getResourceEntity() == null) {
			setState(State.KO);
			setMessage("resourceEntity is null");
			return;
		}
		if (!ae.getResourceID().equals(receivedCall.getResourceEntity().getResourceID())) {
			setState(State.KO);
			setMessage("bad resourceEntity id, expecting " + ae.getResourceID() + ", found:"
					+ receivedCall.getResourceEntity().getResourceID());
			return;
		}

		setState(State.OK);
		

	}

	@Override
	public void authorize(RequestPrimitive request, ResourceEntity resourceEntity) throws AccessDeniedException {
		Call call = new Call(request, resourceEntity);
		addCall(call);

		// in all cases
		throw new AccessDeniedException();
	}
	
	@Override
	public String getPoA() {
		if (dac != null) {
			return dac.getDynamicAuthorisationPoA().get(0);
		}
		return null;
	}

}
