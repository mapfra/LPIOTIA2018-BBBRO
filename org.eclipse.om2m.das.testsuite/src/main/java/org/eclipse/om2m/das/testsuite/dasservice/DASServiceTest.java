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
import org.eclipse.om2m.das.testsuite.Activator;
import org.eclipse.om2m.das.testsuite.Test;
import org.osgi.framework.ServiceRegistration;

public class DASServiceTest extends Test implements DynamicAuthorizationServerService {

	private DynamicAuthorizationConsultation dac;

	private int expectedNumberOfCall = 1;
	private List<Call> calls = new ArrayList<>();

	private ServiceRegistration<DynamicAuthorizationServerService> dassRegistration;

	/**
	 * To be used by activator
	 * @param pCseService
	 */
	public DASServiceTest(CseService pCseService) {
		super("DasServiceTest", pCseService);
	}

	/**
	 * To be used by extended class
	 * @param pName
	 * @param pCseService
	 */
	protected  DASServiceTest(String pName, CseService pCseService) {
		super(pName, pCseService);
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

		// set number of expected call
		setExpectedNumberOfCall(1);

		// register this as a DynamicAuthorizationServerService
		dassRegistration = registerDynamicAuthorizationServerService(this);

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
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			setState(State.KO);
			setMessage("unable to retrieve Ae, expecting " + ResponseStatusCode.OK + ", found ="
					+ response.getResponseStatusCode());
			return;
		}

		// unregister DASS
		unregisterDynamicAuthorizationServerService(dassRegistration);

		// check authorize
		if (getNumberOfCall() != 1) {
			setMessage("expect 1 call, found " + getNumberOfCall());
			setState(State.KO);
			return;
		}

		List<Call> receivedCalls = getCalls();
		// at this point, we are sure we have one call in the list
		Call call = receivedCalls.get(0);

		if (call.getRequestPrimitive() == null) {
			setMessage("request is null");
			setState(State.KO);
			return;
		}

		// targetId
		if (!call.getRequestPrimitive().getTargetId().equals(ae.getResourceID())) {
			setState(State.KO);
			setMessage("bad targetId, expecting " + ae.getResourceID() + ", found "
					+ call.getRequestPrimitive().getTargetId());
			return;
		}

		// from
		if (!call.getRequestPrimitive().getFrom().equals("nom:password")) {
			setState(State.KO);
			setMessage("bad caller credentials, expecting:" + "nom:password" + ", found "
					+ call.getRequestPrimitive().getFrom());
			return;
		}

		// operation
		if (!Operation.RETRIEVE.equals(call.getRequestPrimitive().getOperation())) {
			setState(State.KO);
			setMessage("bad operation, expecting:" + Operation.RETRIEVE + ", found "
					+ call.getRequestPrimitive().getOperation());
			return;
		}

		// check resource entity
		if (call.getResourceEntity() == null) {
			setState(State.KO);
			setMessage("resourceEntity is null");
			return;
		}
		if (!ae.getResourceID().equals(call.getResourceEntity().getResourceID())) {
			setState(State.KO);
			setMessage("bad resourceEntity id, expecting " + ae.getResourceID() + ", found:"
					+ call.getResourceEntity().getResourceID());
			return;
		}

		setState(State.OK);
	}

	@Override
	public void cleanUp() {
		unregisterDynamicAuthorizationServerService(dassRegistration);
	}

	protected static ServiceRegistration<DynamicAuthorizationServerService> registerDynamicAuthorizationServerService(
			DynamicAuthorizationServerService service) {
		return Activator.getBundleContext().registerService(DynamicAuthorizationServerService.class, service, null);
	}

	protected static void unregisterDynamicAuthorizationServerService(
			ServiceRegistration<DynamicAuthorizationServerService> serviceRegistration) {
		if (serviceRegistration != null) {
			try {
				serviceRegistration.unregister();
				serviceRegistration = null;
			} catch (IllegalStateException e) {
			}
		}

	}

	@Override
	public String getPoA() {
		if (dac != null) {
			return dac.getDynamicAuthorisationPoA().get(0);
		}
		return null;
	}

	@Override
	public void authorize(RequestPrimitive request, ResourceEntity resourceEntity) throws AccessDeniedException {
		// nothing to do except tracking call
		int nbCall = getNumberOfCall();

		Call call = new Call(request, resourceEntity);
		addCall(call);

		if (nbCall > expectedNumberOfCall) {
			throw new AccessDeniedException(
					"expect " + expectedNumberOfCall + " call -> currentNumber=" + getNumberOfCall());
		}

	}

	protected void addCall(Call call) {
		synchronized (calls) {
			calls.add(call);
		}
	}

	protected List<Call> getCalls() {
		List<Call> toBeReturned = new ArrayList<>();
		synchronized (calls) {
			toBeReturned.addAll(calls);
		}
		return toBeReturned;
	}

	protected class Call {
		private final RequestPrimitive requestPrimitive;
		private final ResourceEntity resourceEntity;

		public Call(final RequestPrimitive pRequest, final ResourceEntity pResource) {
			requestPrimitive = pRequest;
			resourceEntity = pResource;
		}

		/**
		 * @return the requestPrimitive
		 */
		public RequestPrimitive getRequestPrimitive() {
			return requestPrimitive;
		}

		/**
		 * @return the resourceEntity
		 */
		public ResourceEntity getResourceEntity() {
			return resourceEntity;
		}

	}

	/**
	 * @return the expectedNumberOfCall
	 */
	protected int getExpectedNumberOfCall() {
		return expectedNumberOfCall;
	}

	/**
	 * @param expectedNumberOfCall
	 *            the expectedNumberOfCall to set
	 */
	protected void setExpectedNumberOfCall(int expectedNumberOfCall) {
		this.expectedNumberOfCall = expectedNumberOfCall;
	}

	/**
	 * @return the numberOfCall
	 */
	protected int getNumberOfCall() {
		int nbOfCall = 0;
		synchronized (calls) {
			nbOfCall = calls.size();
		}
		return nbOfCall;
	}

}
