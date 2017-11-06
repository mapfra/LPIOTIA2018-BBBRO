/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.monitoring.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.SetOfAcrs;
import org.eclipse.om2m.commons.resource.Subscription;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.eclipse.om2m.sdt.home.monitoring.servlet.SessionManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AeRegistration implements InterworkingService, org.eclipse.om2m.sdt.home.monitoring.util.Constants {

	private static Log LOGGER = LogFactory.getLog(AeRegistration.class);

	private static final AeRegistration INSTANCE = new AeRegistration();

	private CseService cseService;
	private AE registeredApplication;
	private AccessControlPolicy registeredAcp;

	private Map<String /* sessionId */, Set<String> /* list of subscriptions */> subscriptionsPerSessions;
	private Map<String /* sessionId */, List<JSONObject>> notifications;

	private Map<String /* subscription's ri */, String /* resourceId */> subscriptions;
	private Map<String /* resource id */, String /* subscription ri */> subscribedToResourcesSet;

	/**
	 * Retrieves instance
	 * 
	 * @return instance
	 */
	public static AeRegistration getInstance() {
		return INSTANCE;
	}

	/**
	 * Make private the default constructor
	 */
	private AeRegistration() {
		notifications = new HashMap<String, List<JSONObject>>();
		subscriptions = new HashMap<String,String>();
		subscribedToResourcesSet = new HashMap<String,String>();
		subscriptionsPerSessions = new HashMap<String, Set<String>>();
	}

	/**
	 * Set current cse service
	 * 
	 * @param pCseService
	 *            cseService instance or null
	 */
	public void setCseService(CseService pCseService) {
		cseService = pCseService;
	}

	/**
	 * Create an AE in the INCSE
	 * 
	 * @return true if the AE has been successfully created
	 */
	public boolean createAe() {
		if ((cseService == null) || ! createACP()) {
			return false;
		}

		AE ae = new AE();
		ae.setName(RESOURCE_ID);
		ae.setAppName(FRIENDLY_HOME_MONITORING_NAME);
		ae.setAppID(RESOURCE_ID);
		ae.setRequestReachability(Boolean.TRUE);
		ae.getLabels().add(HOME_MONITORING_RESOURCE_ID);
		ae.getLabels().add(RESOURCE_TYPE);
		ae.getAccessControlPolicyIDs().add(registeredAcp.getResourceID());
		ae.getPointOfAccess().add(POA);

		RequestPrimitive request = new RequestPrimitive();
		request.setOperation(Operation.CREATE);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME);
		request.setResourceType(ResourceType.AE);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setContent(ae);

		ResponsePrimitive response = cseService.doRequest(request);
		// check response status code
		if (! ResponseStatusCode.CREATED.equals(response.getResponseStatusCode())) {
			return false;
		}

		// retrieve created application
		try {
			registeredApplication = (AE) response.getContent();
		} catch (ClassCastException e) {
			return false;
		}
		
		// create a Container to store the icon
		Container iconContainer = createContainer(registeredApplication.getResourceID(), "ICON");
		if (iconContainer != null) {
			createContentInstance(iconContainer, "/" + IMAGES + "logo.png");
		}
		
		Container presentationUrlContainer = createContainer(registeredApplication.getResourceID(), "PRESENTATION_URL");
		if (presentationUrlContainer != null) {
			createContentInstance(presentationUrlContainer, "/" + WEBAPPS + "login.html");
		}
		
		// ok
		return true;
	}

	public void deleteAe() {
		deleteAllSubscriptions();
		if ((registeredApplication == null) || (cseService == null)) { // KO
			return;
		}

		RequestPrimitive request = new RequestPrimitive();
		request.setOperation(Operation.DELETE);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(registeredApplication.getResourceID());

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
		request.setTo("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME);
		request.setResourceType(ResourceType.ACCESS_CONTROL_POLICY);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setContent(acp);

		ResponsePrimitive response = cseService.doRequest(request);
		// check response status code
		if (! ResponseStatusCode.CREATED.equals(response.getResponseStatusCode())) {
			LOGGER.info("createACP KO " + response);
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
		request.setTo(registeredAcp.getResourceID());
		cseService.doRequest(request);
	}
	
	private Container createContainer(String resourceID, String name) {
		Container container = new Container();
		container.setName(name);

		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(resourceID);
		request.setOperation(Operation.CREATE);
		request.setResourceType(ResourceType.CONTAINER);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setContent(container);
		
		ResponsePrimitive response = cseService.doRequest(request);
		return response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)
				? (Container) response.getContent() : null;
	}
	
	private void createContentInstance(Container iconContainer, String value) {
		ContentInstance contentInstance = new ContentInstance();
		contentInstance.setContentInfo("paint");
		contentInstance.setContent(value);

		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(iconContainer.getResourceID());
		request.setOperation(Operation.CREATE);
		request.setResourceType(ResourceType.CONTENT_INSTANCE);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setContent(contentInstance);
		
		cseService.doRequest(request );
	}

	@Override
	public ResponsePrimitive doExecute(RequestPrimitive request) {
		ResponsePrimitive response = new ResponsePrimitive(request);
		if (! request.getOperation().equals(Operation.NOTIFY)) {
			response.setResponseStatusCode(ResponseStatusCode.NOT_IMPLEMENTED);
			return response;
		}

		// store notifications
		try {
			String content = (String) request.getContent();
			JSONObject notification = (JSONObject) new JSONParser().parse(content);
			LOGGER.info("Got notif: " + notification);
			// add in list
			addNotification(notification);
			response.setResponseStatusCode(ResponseStatusCode.OK);
		} catch (Exception e) {
			LOGGER.error("Error notif", e);
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
		}
		return response;
	}

	@Override
	public String getAPOCPath() {
		return POA;
	}

	public List<JSONObject> getNotificationsAndClears(String sessionId) {
		List<JSONObject> notificationsToBeReturned = new ArrayList<JSONObject>();
		List<JSONObject> notifsPerSession;
		// retrieve list of notifs based on sessionId
		synchronized (notifications) {
			notifsPerSession = notifications.get(sessionId);
		}
		if (notifsPerSession != null) {
			synchronized (notifsPerSession) {
				notificationsToBeReturned.addAll(notifsPerSession);
				notifsPerSession.clear();
			}
		}
		return notificationsToBeReturned;
	}

	private void addNotification(JSONObject notification) {
		LOGGER.debug("add notification from subscription ");
		String subscriptionId = (String) ((JSONObject) notification.get("m2m:sgn")).get("m2m:sur");
		for (Entry<String, Set<String>> entry : subscriptionsPerSessions.entrySet()) {
			if (entry.getValue().contains(subscriptionId)) {
				addNotification(entry.getKey(), notification);
			}
		}
	}

	private void addNotification(String sessionId, JSONObject notification) {
		List<JSONObject> notifsPerSession = null;
		synchronized (notifications) {
			notifsPerSession = notifications.get(sessionId);
			if (notifsPerSession == null) {
				notifsPerSession = new ArrayList<>();
				notifications.put(sessionId, notifsPerSession);
			}
		}
		synchronized (notifsPerSession) {
			notifsPerSession.add(notification);
		}
	}

	public boolean createSubscription(String resourceId, String sessionId) {
		if ((resourceId == null) || ! SessionManager.getInstance().checkTokenExists(sessionId)) {
			return false;
		}

		// check if a subscription exists for this device
		String subscriptionId = null;
		if ((subscriptionId = checkIfSubscriptionExists(resourceId)) != null) {
			// associate this session with this subscription
			associateSubscriptionAndSession(subscriptionId, sessionId);
			return true;
		}

		Subscription subscription = new Subscription();
		subscription.setNotificationContentType(NotificationContentType.WHOLE_RESOURCE);
		subscription.getNotificationURI().add(registeredApplication.getResourceID());

		RequestPrimitive request = new RequestPrimitive();
		request.setOperation(Operation.CREATE);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(resourceId);
		request.setResourceType(ResourceType.SUBSCRIPTION);
		request.setReturnContentType(MimeMediaType.JSON);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setContent(subscription);

		ResponsePrimitive response = cseService.doRequest(request);
		// check response status code
		if (!ResponseStatusCode.CREATED.equals(response.getResponseStatusCode())) {
			return false;
		}
		
		try {
			String content = (String) response.getContent();
			JSONObject createdSubscription = (JSONObject) new JSONParser().parse(content);
			subscriptionId = (String) ((JSONObject) createdSubscription.get("m2m:sub")).get("ri");
			addSubscription(subscriptionId, resourceId);
			// associate this session with this subscription
			associateSubscriptionAndSession(subscriptionId, sessionId);
			return true;
		} catch (ParseException e) {
			LOGGER.error("unable to parse subscription json payload", e);
		} catch (NullPointerException e) {
			LOGGER.error("unable to retrieve subscription object", e);
		} catch (ClassCastException e) {
			LOGGER.error("unable to cast subscription object", e);
		}
		return false;
	}

	private void deleteSubscription(String subscriptionId) {
		RequestPrimitive request = new RequestPrimitive();
		request.setOperation(Operation.DELETE);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(subscriptionId);
		request.setResourceType(ResourceType.SUBSCRIPTION);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);

		cseService.doRequest(request);
	}

	private void addSubscription(String subscriptionId, String resourceId) {
		synchronized (subscriptions) {
			subscriptions.put(subscriptionId, resourceId);
		}
		synchronized (subscribedToResourcesSet) {
			subscribedToResourcesSet.put(resourceId, subscriptionId);
		}
	}

	private void deleteAllSubscriptions() {
		synchronized (subscriptions) {
			for (String subId : subscriptions.keySet()) {
				deleteSubscription(subId);
			}
			subscriptions.clear();
		}
		synchronized (subscribedToResourcesSet) {
			subscribedToResourcesSet.clear();
		}
	}

	private String checkIfSubscriptionExists(String subscribedToResourceId) {
		synchronized (subscribedToResourcesSet) {
			return subscribedToResourcesSet.get(subscribedToResourceId);
		}
	}

	private void associateSubscriptionAndSession(String subscriptionId, String sessionId) {
		Set<String> subscriptionIds = null;
		synchronized (subscriptionsPerSessions) {
			subscriptionIds = subscriptionsPerSessions.get(sessionId);
			if (subscriptionIds == null) {
				subscriptionIds = new HashSet<String>();
				subscriptionsPerSessions.put(sessionId, subscriptionIds);
			}
		}
		synchronized (subscriptionIds) {
			subscriptionIds.add(subscriptionId);
		}
	}

	public void deassociateSubscriptionAndSessions(String sessionId) {
		synchronized (subscriptionsPerSessions) {
			subscriptionsPerSessions.remove(sessionId);
		}
		List<JSONObject> notifsPerSession = null;
		synchronized (notifications) {
			notifsPerSession = notifications.remove(sessionId);
		}
		if (notifsPerSession != null) {
			synchronized (notifsPerSession) {
				notifsPerSession.clear();
			}
		}
	}
	
}
