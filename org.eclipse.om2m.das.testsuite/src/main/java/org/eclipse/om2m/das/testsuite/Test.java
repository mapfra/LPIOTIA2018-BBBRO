package org.eclipse.om2m.das.testsuite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.eclipse.om2m.commons.constants.AccessControl;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AEAnnc;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.AccessControlRule;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.FlexContainerAnnc;
import org.eclipse.om2m.commons.resource.RemoteCSE;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.SetOfAcrs;
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

	public void cleanUp() {
	}

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
		return createDAS((String) null);
	}
	
	
	protected DynamicAuthorizationConsultation createDAS(String poA) {
		List<String> poas = null;
		if (poA != null) {
			poas = new ArrayList<>();
			poas.add(poA);
		}
		return createDAS(poas);
	}

	protected DynamicAuthorizationConsultation createDAS(List<String> poAs) {
		// create request
		RequestPrimitive request = new RequestPrimitive();

		// das field
		Boolean enabled = Boolean.TRUE;
		List<String> poa = new ArrayList<>();
		if (poAs == null) {
			poa.add("poa1" + UUID.randomUUID());
			poa.add("poa2" + UUID.randomUUID());
		} else {
			poa.addAll(poAs);
		}

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
		return createAeAnnc(url, null);
	}

	protected AEAnnc createAeAnnc(String url, List<String> dacis) {
		
		AccessControlPolicy createdAcp = createAcp();
		if (createdAcp == null) {
			// something goes wrong
			return null;
		}
		
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
		aeAnnc.getAccessControlPolicyIDs().add(createdAcp.getResourceID());
		if (dacis != null) {
			aeAnnc.getDynamicAuthorizationConsultationIDs().addAll(dacis);
		}

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

	/**
	 * Create a fake AE.
	 * 
	 * @return
	 */
	protected AE createAe() {
		return createAE(null);
	}

	protected AccessControlPolicy createAcp() {
		// create a specific acp for this entity
		AccessControlPolicy acp = new AccessControlPolicy();
		AccessControlRule acr = new AccessControlRule();
		acr.getAccessControlOriginators().add(Constants.ADMIN_REQUESTING_ENTITY);
		acr.setAccessControlOperations(AccessControl.ALL);
		acp.setPrivileges(new SetOfAcrs());
		acp.getPrivileges().getAccessControlRule().add(acr);
		acp.setSelfPrivileges(new SetOfAcrs());
		AccessControlRule selfAcr = new AccessControlRule();
		selfAcr.setAccessControlOperations(AccessControl.ALL);
		selfAcr.getAccessControlOriginators().add(Constants.ADMIN_REQUESTING_ENTITY);
		acp.getSelfPrivileges().getAccessControlRule().add(selfAcr);

		RequestPrimitive acpCreateRequest = new RequestPrimitive();
		acpCreateRequest.setName("ACP" + UUID.randomUUID());
		acpCreateRequest.setOperation(Operation.CREATE);
		acpCreateRequest.setRequestContentType(MimeMediaType.OBJ);
		acpCreateRequest.setReturnContentType(MimeMediaType.OBJ);
		acpCreateRequest.setResourceType(ResourceType.ACCESS_CONTROL_POLICY);
		acpCreateRequest.setTargetId("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME);
		acpCreateRequest.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		acpCreateRequest.setContent(acp);

		ResponsePrimitive acpCreateResponse = getCseService().doRequest(acpCreateRequest);
		if (!ResponseStatusCode.CREATED.equals(acpCreateResponse.getResponseStatusCode())) {
			// ko
			return null;
		}
		return (AccessControlPolicy) acpCreateResponse.getContent();
	}

	/**
	 * Create a new Application entity
	 * 
	 * @param dacis
	 *            associated dynamicAuthorizationConsultation
	 * @return Ae or null
	 */
	protected AE createAE(List<String> dacis) {

		AccessControlPolicy createdAcp = createAcp();
		if (createdAcp == null) {
			// something goes wrong
			return null;
		}

		AE ae = new AE();

		ae.setAppID("1234");
		ae.setAppName("appName" + UUID.randomUUID());
		ae.setRequestReachability(Boolean.TRUE);
		ae.getAccessControlPolicyIDs().add(createdAcp.getResourceID());
		ae.getPointOfAccess().add("poa_" + UUID.randomUUID()); 
		if (dacis != null) {
			ae.getDynamicAuthorizationConsultationIDs().addAll(dacis);
		}

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

	protected FlexContainer createFlexContainer(List<String> dacis) {
		
		return createFlexContainer("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME, dacis);

	}
	
	protected FlexContainer createFlexContainer(String resourceUrl, List<String> dacis) {

		AccessControlPolicy createdAcp = createAcp();
		if (createdAcp == null) {
			// something goes wrong
			return null;
		}

		FlexContainer flexContainer = new FlexContainer();

		flexContainer.setContainerDefinition("myDef");
		flexContainer.getAccessControlPolicyIDs().add(createdAcp.getResourceID());
		if (dacis != null) {
			flexContainer.getDynamicAuthorizationConsultationIDs().addAll(dacis);
		}

		RequestPrimitive request = new RequestPrimitive();
		request.setName("FlexContainer_" + UUID.randomUUID());
		request.setOperation(Operation.CREATE);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setResourceType(ResourceType.FLEXCONTAINER);
		request.setTargetId(resourceUrl);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setContent(flexContainer);

		// execute
		ResponsePrimitive response = getCseService().doRequest(request);
		if ((response != null) && (ResponseStatusCode.CREATED.equals(response.getResponseStatusCode()))) {
			return (FlexContainer) response.getContent();
		}

		return null;
	}

	protected FlexContainerAnnc createFlexContainerAnnc(String resourceUrl, List<String> dacis) {

		AccessControlPolicy createdAcp = createAcp();
		if (createdAcp == null) {
			// something goes wrong
			return null;
		}

		FlexContainerAnnc flexContainerAnnc = new FlexContainerAnnc();

		flexContainerAnnc.setContainerDefinition("myDef");
		flexContainerAnnc.getAccessControlPolicyIDs().add(createdAcp.getResourceID());
		flexContainerAnnc.setLink("/link" + UUID.randomUUID());
		if (dacis != null) {
			flexContainerAnnc.getDynamicAuthorizationConsultationIDs().addAll(dacis);
		}

		RequestPrimitive request = new RequestPrimitive();
		request.setName("FlexContainer_" + UUID.randomUUID());
		request.setOperation(Operation.CREATE);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setResourceType(ResourceType.FLEXCONTAINER_ANNC);
		request.setTargetId(resourceUrl);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setContent(flexContainerAnnc);

		// execute
		ResponsePrimitive response = getCseService().doRequest(request);
		if ((response != null) && (ResponseStatusCode.CREATED.equals(response.getResponseStatusCode()))) {
			return (FlexContainerAnnc) response.getContent();
		}

		return null;
	}

	protected ResponsePrimitive retrieveEntity(String url) {
		return retrieveEntity(url, Constants.ADMIN_REQUESTING_ENTITY);
	}

	protected ResponsePrimitive retrieveEntity(String url, String from) {
		// create request
		RequestPrimitive request = new RequestPrimitive();

		// setup request
		request.setOperation(Operation.RETRIEVE);
		request.setTargetId(url);
		request.setFrom(from);
		request.setResourceType(ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION);
		request.setReturnContentType(MimeMediaType.OBJ);

		return getCseService().doRequest(request);
	}
	
	protected void deleteEntity(String url) {
		RequestPrimitive request = new RequestPrimitive();
		request.setOperation(Operation.DELETE);
		request.setTargetId(url);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		
		getCseService().doRequest(request);
	}
}
