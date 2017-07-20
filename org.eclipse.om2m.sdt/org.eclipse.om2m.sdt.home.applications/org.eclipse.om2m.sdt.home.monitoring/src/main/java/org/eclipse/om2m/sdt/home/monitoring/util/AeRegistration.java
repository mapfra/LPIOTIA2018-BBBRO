package org.eclipse.om2m.sdt.home.monitoring.util;

import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.AccessControl;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.AccessControlRule;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.SetOfAcrs;
import org.eclipse.om2m.core.service.CseService;

public class AeRegistration {
	
	private static Log LOGGER = LogFactory.getLog(AeRegistration.class);

	private static final String HOME_MONITORING_NAME = "SDT_Home_Monitoring_Application";
	private static final String ACP_HOME_MONITORING_NAME = HOME_MONITORING_NAME + "_ACP";
	private static final String HOME_MONITORING_RESOURCE_ID = "ResourceID/SDT_Home_Monitoring_Application";
	private static final String RESOURCE_TYPE = "ResourceType/Application";
	
	private static final AeRegistration INSTANCE = new AeRegistration();
	
	private CseService cseService;
	private AE registeredApplication;
	private AccessControlPolicy registeredAcp;

	/**
	 * Retrieves instance
	 * @return instance
	 */
	public static AeRegistration getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Make private the default constructor
	 */
	private AeRegistration() {
	}
	
	/**
	 * Set current cse service
	 * @param pCseService cseService instance or null
	 */
	public void setCseService(CseService pCseService) {
		cseService = pCseService;
	}
	
	/**
	 * Create an AE in the INCSE
	 * @return true if the AE has been successfully created
	 */
	public boolean createAe() {
		if (cseService == null) {
			// KO
			return false;
		}
		if (! createACP()) {
			return false;
		}
		
		RequestPrimitive request = new RequestPrimitive();

		AE ae = new AE();
		ae.setName(HOME_MONITORING_NAME);
		ae.setAppName(HOME_MONITORING_NAME);
		ae.setAppID(HOME_MONITORING_NAME);
		ae.setRequestReachability(Boolean.FALSE);
		ae.getLabels().add(HOME_MONITORING_RESOURCE_ID);
		ae.getLabels().add(RESOURCE_TYPE);
		ae.getAccessControlPolicyIDs().add(registeredAcp.getResourceID());
		
		request.setOperation(Operation.CREATE);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTargetId("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME);
		request.setResourceType(ResourceType.AE);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setContent(ae);
		
		ResponsePrimitive response = cseService.doRequest(request);
		
		// check response status code
		if (! ResponseStatusCode.CREATED.equals(response.getResponseStatusCode())) {
			// KO
			return false;
		}
		
		// retrieve created application
		try {
			registeredApplication = (AE) response.getContent();
		} catch (ClassCastException e) {
			// ko
			return false;
		}
		// ok
		return true;
	}
	
	public void deleteAe() {
		if (registeredApplication == null) {
			return;
		}
		if (cseService == null) { // KO
			return;
		}
		
		RequestPrimitive request = new RequestPrimitive();
		request.setOperation(Operation.DELETE);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTargetId(registeredApplication.getResourceID());
		
		cseService.doRequest(request);
		deleteAcp();	
	}
	
	private boolean createACP() {
		LOGGER.info("createACP");
		RequestPrimitive request = new RequestPrimitive();

		AccessControlPolicy acp = new AccessControlPolicy();
		acp.setName(ACP_HOME_MONITORING_NAME);
		acp.setPrivileges(new SetOfAcrs());
		AccessControlRule adminAccessControlRule = new AccessControlRule();
		adminAccessControlRule.setAccessControlOperations(AccessControl.ALL);
		adminAccessControlRule.getAccessControlOriginators().add(Constants.ADMIN_REQUESTING_ENTITY);
		acp.getPrivileges().getAccessControlRule().add(adminAccessControlRule);
		acp.setSelfPrivileges(new SetOfAcrs());
		acp.getSelfPrivileges().getAccessControlRule().add(adminAccessControlRule);
		
		request.setOperation(Operation.CREATE);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTargetId("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME);
		request.setResourceType(ResourceType.ACCESS_CONTROL_POLICY);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setContent(acp);
		
		ResponsePrimitive response = cseService.doRequest(request);
		// check response status code
		BigInteger code = response.getResponseStatusCode();
		if (! ResponseStatusCode.CREATED.equals(code)) {
			// KO
			LOGGER.info("createACP KO " + code);
			return false;
		}
		
		// retrieve created application
		try {
			registeredAcp = (AccessControlPolicy) response.getContent();
			// ok
			LOGGER.info("createACP OK " + registeredAcp);
			return true;
		} catch (ClassCastException e) {
			LOGGER.info("createACP KO " + e);
			return false;
		}
	}
	
	public void deleteAcp() {
		if (registeredAcp == null) {
			return;
		}
		
		LOGGER.info("deleteAcp " + registeredAcp);
		RequestPrimitive request = new RequestPrimitive();
		request.setOperation(Operation.DELETE);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTargetId(registeredAcp.getResourceID());
		cseService.doRequest(request);
	}

}
