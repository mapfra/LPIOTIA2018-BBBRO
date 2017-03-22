package org.eclipse.om2m.das.testsuite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AEAnnc;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RemoteCSE;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;

public abstract class Test {

	private final String name;

	public enum State {
		OK, KO
	};

	private State state = State.KO;
	private String message;
	private final CseService cseService;

	protected Test(String pName, CseService pCseService) {
		name = pName;
		cseService = pCseService;
	}

	public String getName() {
		return name;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public CseService getCseService() {
		return cseService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Test to be performed
	 */
	public abstract void performTest();

	public boolean checkNotNull(Object object, String objectName) {
		if (object == null) {
			setState(State.KO);
			setMessage("object " + objectName + " must not be null ");
			return false;
		}

		return true;
	}

	public boolean checkNull(Object object, String objectName) {
		if (object != null) {
			setState(State.KO);
			setMessage("object " + objectName + " must be null ");
			return false;
		}

		return true;
	}

	public boolean checkEmpty(List object, String objectName) {
		if (object == null) {
			setState(State.KO);
			setMessage("object " + objectName + " must not be null");
			return false;
		}

		if (!object.isEmpty()) {
			setState(State.KO);
			setMessage("object " + objectName + " must be empty ");
			return false;
		}

		return true;
	}

	public boolean checkNotEmpty(String object, String objectName) {
		if (!checkNotNull(object, objectName)) {
			return false;
		}
		if (object.isEmpty()) {
			setState(State.KO);
			setMessage("object " + objectName + " must not be empty ");
			return false;
		}

		return true;
	}

	public boolean checkEquals(int currentValue, int expectedValue, String objectName) {
		if (currentValue != expectedValue) {
			setState(State.KO);
			setMessage("object " + objectName + ": expected=" + expectedValue + ", found=" + currentValue);
			return false;
		}
		return true;
	}

	public boolean checkEquals(Object currentValue, Object expectedValue, String objectName) {
		if (currentValue == null) {
			if (expectedValue != null) {
				setState(State.KO);
				setMessage("object " + objectName + ": expected=" + expectedValue + ", found=" + currentValue);
				return false;
			} else {
				return true;
			}
		}
		if (!currentValue.equals(expectedValue)) {
			setState(State.KO);
			setMessage("object " + objectName + ": expected=" + expectedValue + ", found=" + currentValue);
			return false;
		}
		return true;
	}

	public boolean checkEquals(List<?> currentList, List<?> expectedList, String objectName) {
		if (currentList == null) {
			if (expectedList != null) {
				setState(State.KO);
				setMessage("object " + objectName + ": expected=" + expectedList.toString() + ", found=" + currentList);
				return false;
			} else {
				return true;
			}
		}
		if (!currentList.equals(expectedList)) {
			setState(State.KO);
			setMessage("object " + objectName + ": expected=" + expectedList + ", found=" + currentList.toString());
			return false;
		}
		return true;
	}

	public void printTestReport() {
		System.out.println("\t - " + getName() + ", state=" + getState() + ", message=" + getMessage());
	}

	protected DynamicAuthorizationConsultation createDAS() {
		// create request
		RequestPrimitive request = new RequestPrimitive();

		// das field
		Boolean enabled = Boolean.TRUE;
		List<String> poa = new ArrayList<>();
		poa.add("poa1");
		poa.add("poa2");

		String dasName = "DAS" + UUID.randomUUID().toString();

		// setup request
		request.setOperation(Operation.CREATE);
		request.setTargetId("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME);
		request.setName(dasName);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setResourceType(ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);

		// set das
		DynamicAuthorizationConsultation das = new DynamicAuthorizationConsultation();
		das.setDynamicAuthorizationEnabled(enabled);
		das.setDynamicAuthorisationPoA(poa);
		das.setDynamicAuthorizationLifetime(new Date().toString());

		request.setContent(das);

		ResponsePrimitive response = getCseService().doRequest(request);
		if (response != null) {
			if (ResponseStatusCode.CREATED.equals(response.getResponseStatusCode())) {
				// OK
				return (DynamicAuthorizationConsultation) response.getContent();
			}
		}

		// KO
		return null;
	}

	protected RemoteCSE createRemoteCse() {
		// create request
		RequestPrimitive request = new RequestPrimitive();

		String remoteCseName = "RemoteCse_" + UUID.randomUUID().toString();

		// setup request
		request.setOperation(Operation.CREATE);
		request.setTargetId("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME);
		request.setName(remoteCseName);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setResourceType(ResourceType.REMOTE_CSE);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);

		// set RemoteCse
		RemoteCSE remoteCse = new RemoteCSE();
		remoteCse.setCSEBase("/base/" + remoteCseName);
		remoteCse.setCSEID(remoteCseName);
		remoteCse.setRequestReachability(Boolean.FALSE);
		
		
		request.setContent(remoteCse);

		ResponsePrimitive response = getCseService().doRequest(request);
		if (response != null) {
			if (ResponseStatusCode.CREATED.equals(response.getResponseStatusCode())) {
				// OK
				return (RemoteCSE) response.getContent();
			}
		}

		// KO
		return null;
	}
	
	protected AEAnnc createAeAnnc(String url) {
		// create request
		RequestPrimitive request = new RequestPrimitive();

		String aeAnncCseName = "AeAnnc_" + UUID.randomUUID().toString();

		// setup request
		request.setOperation(Operation.CREATE);
		request.setTargetId(url);
		request.setName(aeAnncCseName);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setResourceType(ResourceType.AE_ANNC);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);

		// set RemoteCse
		AEAnnc aeAnnc = new AEAnnc();
		aeAnnc.setAppID("AeAnncAppID_" + UUID.randomUUID());
		aeAnnc.setLink("/" + aeAnnc.getAppID());
		
		request.setContent(aeAnnc);

		ResponsePrimitive response = getCseService().doRequest(request);
		if (response != null) {
			if (ResponseStatusCode.CREATED.equals(response.getResponseStatusCode())) {
				// OK
				return (AEAnnc) response.getContent();
			}
		}

		// KO
		return null;
	}

	protected ResponsePrimitive retrieveDynamicAuthorizationConsultation(String url) {
		// create request
		RequestPrimitive request = new RequestPrimitive();

		// setup request
		request.setOperation(Operation.CREATE);
		request.setTargetId(url);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setResourceType(ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION);
		request.setReturnContentType(MimeMediaType.OBJ);

		return getCseService().doRequest(request);
	}
}
