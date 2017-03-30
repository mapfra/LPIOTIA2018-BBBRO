package org.eclipse.om2m.das.testsuite.dasservice;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.exceptions.AccessDeniedException;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.das.service.DynamicAuthorizationServerService;
import org.eclipse.om2m.das.testsuite.Activator;
import org.eclipse.om2m.das.testsuite.Test;
import org.eclipse.om2m.das.testsuite.Test.State;
import org.eclipse.om2m.das.testsuite.dasservice.AbstractDASServiceTest.Call;
import org.osgi.framework.ServiceRegistration;

public abstract class AbstractDASServiceTest extends Test implements DynamicAuthorizationServerService {

	private String poa;
	private int expectedNumberOfCall = 1;
	private List<Call> calls = new ArrayList<>();
	private List<ServiceRegistration<DynamicAuthorizationServerService>> registeredDASSes = new ArrayList<>();

	protected AbstractDASServiceTest(String pName, CseService pCseService) {
		super(pName, pCseService);
	}

	@Override
	public abstract void performTest();

	@Override
	public void cleanUp() {
		for (ServiceRegistration<DynamicAuthorizationServerService> sr : registeredDASSes) {
			unregisterDynamicAuthorizationServerService(sr);
		}
	}

	@Override
	public String getPoA() {
		return poa;
	}

	/**
	 * Set poa
	 * 
	 * @param pPoa
	 */
	protected void setPoA(String pPoa) {
		this.poa = pPoa;
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

	/**
	 * Add a call
	 * 
	 * @param call
	 */
	protected void addCall(Call call) {
		synchronized (calls) {
			calls.add(call);
		}
	}

	protected void clearCalls() {
		synchronized (calls) {
			calls.clear();
		}
	}

	/**
	 * Get calls
	 * 
	 * @return
	 */
	protected List<Call> getCalls() {
		List<Call> toBeReturned = new ArrayList<>();
		synchronized (calls) {
			toBeReturned.addAll(calls);
		}
		return toBeReturned;
	}

	/**
	 * Check a call
	 * 
	 * @param callIndex
	 * @param expectedResourceId
	 * @param expectedFrom
	 * @param expectedOperation
	 * @return true if the call is as expected
	 */
	protected boolean checkCall(int callIndex, String expectedResourceId, String expectedFrom,
			BigInteger expectedOperation) {
		// check authorize
		if (getNumberOfCall() != getExpectedNumberOfCall()) {
			setMessage("expect 1 call, found " + getNumberOfCall());
			setState(State.KO);
			return false;
		}

		List<Call> receivedCalls = getCalls();
		// at this point, we are sure we have one call in the list
		Call call = receivedCalls.get(callIndex);

		if (call.getRequestPrimitive() == null) {
			setMessage("request is null");
			setState(State.KO);
			return false;
		}

		// targetId
		if (!call.getRequestPrimitive().getTargetId().equals(expectedResourceId)) {
			setState(State.KO);
			setMessage("bad targetId, expecting " + expectedResourceId + ", found "
					+ call.getRequestPrimitive().getTargetId());
			return false;
		}

		// from
		if (!call.getRequestPrimitive().getFrom().equals(expectedFrom)) {
			setState(State.KO);
			setMessage("bad caller credentials, expecting:" + expectedFrom + ", found "
					+ call.getRequestPrimitive().getFrom());
			return false;
		}

		// operation
		if (!expectedOperation.equals(call.getRequestPrimitive().getOperation())) {
			setState(State.KO);
			setMessage("bad operation, expecting:" + Operation.RETRIEVE + ", found "
					+ call.getRequestPrimitive().getOperation());
			return false;
		}

		// check resource entity
		if (call.getResourceEntity() == null) {
			setState(State.KO);
			setMessage("resourceEntity is null");
			return false;
		}
		if (!expectedResourceId.equals(call.getResourceEntity().getResourceID())) {
			setState(State.KO);
			setMessage("bad resourceEntity id, expecting " + expectedResourceId + ", found:"
					+ call.getResourceEntity().getResourceID());
			return false;
		}

		return true;
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
	 * register a dynamicAuthorizationServerService
	 * 
	 * @param service
	 * @return
	 */
	protected ServiceRegistration<DynamicAuthorizationServerService> registerDynamicAuthorizationServerService(
			DynamicAuthorizationServerService service) {
		ServiceRegistration<DynamicAuthorizationServerService> sr = Activator.getBundleContext()
				.registerService(DynamicAuthorizationServerService.class, service, null);
		return sr;
	}

	/**
	 * Unregister a dynamicAuthorizationServerService
	 * 
	 * @param serviceRegistration
	 */
	protected void unregisterDynamicAuthorizationServerService(
			ServiceRegistration<DynamicAuthorizationServerService> serviceRegistration) {
		if (serviceRegistration != null) {
			try {
				serviceRegistration.unregister();
				serviceRegistration = null;
			} catch (IllegalStateException e) {
			}

			registeredDASSes.remove(serviceRegistration);
		}

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

}
