package org.eclipse.om2m.sdt.home.monitoring.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.AccessControl;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.NotificationContentType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.AccessControlRule;
import org.eclipse.om2m.commons.resource.Notification;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.SetOfAcrs;
import org.eclipse.om2m.commons.resource.Subscription;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AeRegistration implements InterworkingService {
	
	private static Log LOGGER = LogFactory.getLog(AeRegistration.class);

	private static final String HOME_MONITORING_NAME = "SDT_Home_Monitoring_Application";
	private static final String ACP_HOME_MONITORING_NAME = HOME_MONITORING_NAME + "_ACP";
	private static final String HOME_MONITORING_RESOURCE_ID = "ResourceID/SDT_Home_Monitoring_Application";
	private static final String RESOURCE_TYPE = "ResourceType/Application";
	private static final String POA = "HomeMonitoringPOA";
	
	private static final AeRegistration INSTANCE = new AeRegistration();
	
	private CseService cseService;
	private AE registeredApplication;
	private AccessControlPolicy registeredAcp;
	
	private List<JSONObject> notifications;
	private List<String> subscriptions;

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
		notifications = new ArrayList<>();
		subscriptions = new ArrayList<>();
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
		ae.setRequestReachability(Boolean.TRUE);
		ae.getLabels().add(HOME_MONITORING_RESOURCE_ID);
		ae.getLabels().add(RESOURCE_TYPE);
		ae.getAccessControlPolicyIDs().add(registeredAcp.getResourceID());
		ae.getPointOfAccess().add(POA);
		
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
		deleteAllSubscriptions();
		
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

	@Override
	public ResponsePrimitive doExecute(RequestPrimitive request) {
		ResponsePrimitive response = new ResponsePrimitive(request);

		if (!request.getOperation().equals(Operation.NOTIFY)) {
			response.setResponseStatusCode(ResponseStatusCode.NOT_IMPLEMENTED);
			return response;
		}
		
		// store notifications
		String content = null;
		JSONParser parser = new JSONParser();
		JSONObject notification = null;
		try {
			content = (String) request.getContent();
			notification = (JSONObject) parser.parse(content);
		} catch (ClassCastException | ParseException e) {
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
			return response;
		}
		
		// add in list
		addNotification(notification);
		
		response.setResponseStatusCode(ResponseStatusCode.OK);
		return response;
	}

	@Override
	public String getAPOCPath() {
		return POA;
	}

	public List<JSONObject> getNotificationsAndClears() {
		List<JSONObject> notificationsToBeReturned = new ArrayList<>();
		synchronized (notifications) {
			notificationsToBeReturned.addAll(notifications);
			notifications.clear();
		}
		return notificationsToBeReturned;
	}
	
	public void addNotification(JSONObject notification) {
		LOGGER.debug("add notification from subscription ");
		synchronized (notifications) {
			notifications.add(notification);
		}
	}
	
	
	public boolean createSubscription(String resourceId) {
		Subscription subscription = new Subscription();
		subscription.setNotificationContentType(NotificationContentType.WHOLE_RESOURCE);
		subscription.getNotificationURI().add(registeredApplication.getResourceID());
		
		RequestPrimitive request = new RequestPrimitive();
		request.setOperation(Operation.CREATE);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTargetId(resourceId);
		request.setResourceType(ResourceType.SUBSCRIPTION);
		request.setReturnContentType(MimeMediaType.JSON);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setContent(subscription);
		
		ResponsePrimitive response = cseService.doRequest(request);
		
		// check response status code
		if (! ResponseStatusCode.CREATED.equals(response.getResponseStatusCode())) {
			// KO
			return false;
		} else {
			String content = (String) response.getContent();
			JSONParser parser = new JSONParser();
			try {
				JSONObject createdSubscription = (JSONObject) parser.parse(content);
				String subscriptionId = (String) ((JSONObject) createdSubscription.get("m2m:sub")).get("ri");
				addSubscription(subscriptionId);
			} catch (ParseException e) {
				LOGGER.error("unable to parse subscription json payload", e);
				return false;
			} catch(NullPointerException e) {
				LOGGER.error("unable to retrieve subscription object", e);
				return false;
			} catch (ClassCastException e) {
				LOGGER.error("unable to cast subscription object", e);
				return false;
			}
			return true;
		}
	}
	
	private void deleteSubscription(String subscriptionId) {
		RequestPrimitive request = new RequestPrimitive();
		request.setOperation(Operation.DELETE);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTargetId(subscriptionId);
		request.setResourceType(ResourceType.SUBSCRIPTION);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		
		ResponsePrimitive response = cseService.doRequest(request);
	}
	
	private void addSubscription(String subscriptionId) {
		synchronized (subscriptions) {
			subscriptions.add(subscriptionId);
		}
	}
	
	private void deleteAllSubscriptions() {
		synchronized (subscriptions) {
			for(String subId: subscriptions) {
				deleteSubscription(subId);
			}
			subscriptions.clear();
		}
	}
}
