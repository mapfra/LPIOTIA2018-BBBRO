package org.eclipse.om2m.testsuite.flexcontainer;

import java.util.List;

import javax.servlet.ServletException;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.NotificationContentType;
import org.eclipse.om2m.commons.constants.ResourceStatus;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.EventNotificationCriteria;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.Notification;
import org.eclipse.om2m.commons.resource.Resource;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.Subscription;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.datamapping.service.DataMapperService;
import org.eclipse.om2m.testsuite.flexcontainer.TestReport.Status;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

public class SubscriptionTest extends FlexContainerTestSuite {

	private SubscriptionServlet subscriptionServlet;

	private final HttpService httpService;
	private final DataMapperService dataMapperService;

	private boolean isServletRegistered = false;
	private Exception servletRegistrationException;

	public SubscriptionTest(CseService pCseService, HttpService pHttpService, DataMapperService pDataMapper) {
		super(pCseService);

		httpService = pHttpService;
		dataMapperService = pDataMapper;

		// register servlet
		subscriptionServlet = new SubscriptionServlet(pHttpService, pDataMapper);
		try {
			subscriptionServlet.registerServlet();
			isServletRegistered = true;
		} catch (ServletException e) {
			isServletRegistered = false;
			servletRegistrationException = e;
		} catch (NamespaceException e) {
			isServletRegistered = false;
			servletRegistrationException = e;
		}
	}

	@Override
	protected String getTestSuiteName() {
		return "SubscriptionTest";
	}

	public void testCreateSubscription() throws InterruptedException {

		if (!checkServletIsRegistered("testCreateSubscription")) {
			// servlet not registered
			// nothing to do except returning...
			return;
		}

		// create a FlexContainer
		FlexContainer flexContainer = null;
		try {
			flexContainer = createFlexContainer();
		} catch (Exception e) {
			createTestReport("testCreateSubscription", Status.KO, e.getMessage(), e);
			return;
		}
		// here flexContainer has been successfully created

		// add a subscription
		Subscription subscription = new Subscription();
		subscription.getNotificationURI().add(subscriptionServlet.getServletUrl());
		subscription.setSubscriberURI(subscriptionServlet.getServletUrl());
		subscription.setNotificationContentType(NotificationContentType.MODIFIED_ATTRIBUTES);

		String flexContainerLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/"
				+ flexContainer.getName();
		String subscriptionName = "subscription_" + System.currentTimeMillis();

		ResponsePrimitive response = sendCreateSubscriptionRequest(subscription, flexContainerLocation,
				subscriptionName);
		Subscription returnedSubscription = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testCreateSubscription", Status.KO, "unable to create the subscription", null);
			return;
		} else {
			returnedSubscription = (Subscription) response.getContent();

			if (!returnedSubscription.getNotificationURI().contains(subscriptionServlet.getServletUrl())) {
				createTestReport("testCreateSubscription", Status.KO, "invalid notification URI", null);
				return;
			}
			if (!returnedSubscription.getName().equals(subscriptionName)) {
				createTestReport("testCreateSubscription", Status.KO, "invalid subscription name(expected:"
						+ subscriptionName + ", found:" + returnedSubscription.getName() + ")", null);
				return;
			}
		}

		Thread.sleep(1000);
		// check last notification
		Notification notification = subscriptionServlet.getLastNotification();
		if (notification == null) {
			// KO
			createTestReport("testCreateSubscription", Status.KO, "expecting Notification message", null);

			return;
		} else {
			// expecting Notification object
			System.out.println("notif=" + notification);

			// reset list of notification
			subscriptionServlet.resetNotifications();
		}

		// get the subscriptions
		response = sendRetrieveRequest(flexContainerLocation);
		if (response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// OK
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();
			List<ChildResourceRef> childs = retrievedFlexContainer.getChildResource();

			if ((childs != null) && (!childs.isEmpty())) {
				boolean foundSubscription = false;
				System.out.println("child size = " + childs.size());
				for (ChildResourceRef ref : childs) {
					System.out.println("found childResourceRef=" + ref.getResourceName() + "( expected:"
							+ subscriptionName + "), type=" + ref.getType());

					if ((ref.getType().intValue() == ResourceType.SUBSCRIPTION)
							&& (ref.getResourceName().equals(subscriptionName))) {
						foundSubscription = true;
						break;
					}
				}
				if (!foundSubscription) {
					// no subscription found
					createTestReport("testCreateSubscription", Status.KO, "no subscription found", null);
					return;
				}
			} else {
				// KO
				// no child
				createTestReport("testCreateSubscription", Status.KO, "no child (ie subscription) found", null);
				return;
			}
		} else {
			// KO
			createTestReport("testCreateSubscription", Status.KO, "unable to retrieve the FlexContainer", null);
		}

		// update the value of the custom attribute
		FlexContainer toBeUpdated = new FlexContainer();
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powerState");
		ca.setCustomAttributeValue("false");
		ca.setCustomAttributeType("xs:boolean");
		toBeUpdated.getCustomAttributes().add(ca);
		response = sendUpdateFlexContainerRequest(flexContainerLocation, toBeUpdated);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.UPDATED)) {
			// KO
			createTestReport("testCreateSubscription", Status.KO, "unable to update flexContainer", null);
			return;
		}

		// wait 1s
		Thread.sleep(1000);

		// check if we receive a Notification through the servlet
		notification = subscriptionServlet.getLastNotification();
		if (notification != null) {
			// check received notification
			// power state should be false

			FlexContainer notifiedFlexContainer = (FlexContainer) notification.getNotificationEvent()
					.getRepresentation().getResource();

			ca = notifiedFlexContainer.getCustomAttribute("powerState");
			if (ca != null) {
				if (!ca.getCustomAttributeValue().equals("false")) {
					createTestReport("testCreateSubscription", Status.KO, "CustomAttribute powerState value is wrong",
							null);
					return;
				}

			} else {
				// KO
				// custom attribute is missing
				createTestReport("testCreateSubscription", Status.KO, "Custom attribute powerState is missing", null);
				return;
			}
		} else {
			createTestReport("testCreateSubscription", Status.KO, "no notification received. Expected one !", null);
		}

		createTestReport("testCreateSubscription", Status.OK, null, null);

	}

	public void testDeleteSubscription() throws InterruptedException {
		if (!checkServletIsRegistered("testCreateSubscription")) {
			// servlet not registered
			// nothing to do except returning...
			return;
		}

		// create a FlexContainer
		FlexContainer flexContainer = null;
		try {
			flexContainer = createFlexContainer();
		} catch (Exception e) {
			createTestReport("testCreateSubscription", Status.KO, e.getMessage(), e);
			return;
		}
		// here flexContainer has been successfully created

		// add a subscription
		Subscription subscription = new Subscription();
		subscription.getNotificationURI().add(subscriptionServlet.getServletUrl());
		subscription.setSubscriberURI(subscriptionServlet.getServletUrl());
		subscription.setNotificationContentType(NotificationContentType.MODIFIED_ATTRIBUTES);

		String flexContainerLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/"
				+ flexContainer.getName();
		String subscriptionName = "subscription_" + System.currentTimeMillis();
		String subscriptionLocation = flexContainerLocation + "/" + subscriptionName;

		ResponsePrimitive response = sendCreateSubscriptionRequest(subscription, flexContainerLocation,
				subscriptionName);
		Subscription returnedSubscription = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testCreateSubscription", Status.KO, "unable to create the subscription", null);
			return;
		} else {
			returnedSubscription = (Subscription) response.getContent();

			if (!returnedSubscription.getNotificationURI().contains(subscriptionServlet.getServletUrl())) {
				createTestReport("testCreateSubscription", Status.KO, "invalid notification URI", null);
				return;
			}
			if (!returnedSubscription.getName().equals(subscriptionName)) {
				createTestReport("testCreateSubscription", Status.KO, "invalid subscription name(expected:"
						+ subscriptionName + ", found:" + returnedSubscription.getName() + ")", null);
				return;
			}
		}

		Thread.sleep(1000);
		// check last notification
		Notification notification = subscriptionServlet.getLastNotification();
		if (notification == null) {
			// KO
			createTestReport("testCreateSubscription", Status.KO, "expecting Notification message", null);

			return;
		} else {
			// expecting Notification object
			System.out.println("notif=" + notification);

			// reset list of notification
			subscriptionServlet.resetNotifications();
		}

		// delete the subscription
		response = sendDeleteRequest(subscriptionLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.DELETED)) {
			// KO
			createTestReport("testDeleteSubscription", Status.KO, "unable to delete the subscription", null);
			return;
		}

		// Thread.sleep(1000);
		// // we should a notification about the deletion of the subscription
		// notification = subscriptionServlet.getLastNotification();
		// if (notification != null) {
		// // OK
		// if
		// (!notification.getNotificationEvent().getResourceStatus().equals(ResourceStatus.DELETED))
		// {
		// createTestReport("testDeleteSubscription", Status.KO,
		// "invalid resource status (expected:" + ResourceStatus.DELETED + ",
		// received:"
		// + notification.getNotificationEvent().getResourceStatus() + ")",
		// null);
		// return;
		// } else {
		// // reset notification
		// subscriptionServlet.resetNotifications();
		// }
		// } else {
		// // KO
		// // we don't receive a notification about deletion
		// createTestReport("testDeleteSubscription", Status.KO,
		// "no notification about the deletion of the subscription", null);
		// return;
		// }

		// update the flexContainer value
		// update the value of the custom attribute
		FlexContainer toBeUpdated = new FlexContainer();
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powerState");
		ca.setCustomAttributeValue("false");
		ca.setCustomAttributeType("xs:boolean");
		toBeUpdated.getCustomAttributes().add(ca);
		response = sendUpdateFlexContainerRequest(flexContainerLocation, toBeUpdated);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.UPDATED)) {
			// KO
			createTestReport("testDeleteSubscription", Status.KO, "unable to update flexContainer", null);
			return;
		}

		// wait 1s
		Thread.sleep(1000);

		// check if we receive a notification
		notification = subscriptionServlet.getLastNotification();
		if (notification != null) {
			// KO
			// we should not receive any notification
			createTestReport("testDeleteSubscription", Status.KO, "notification received but not expected", null);
			return;
		}

		createTestReport("testDeleteSubscription", Status.OK, null, null);
	}

	public void testUpdateSubscription() throws InterruptedException {

		if (!checkServletIsRegistered("testCreateSubscription")) {
			// servlet not registered
			// nothing to do except returning...
			return;
		}

		// create a FlexContainer
		FlexContainer flexContainer = null;
		try {
			flexContainer = createFlexContainer();
		} catch (Exception e) {
			createTestReport("testUpdateSubscription", Status.KO, e.getMessage(), e);
			return;
		}
		// here flexContainer has been successfully created

		// add a subscription
		Subscription subscription = new Subscription();
		subscription.getNotificationURI().add(subscriptionServlet.getServletUrl());
		subscription.setSubscriberURI(subscriptionServlet.getServletUrl());
		subscription.setNotificationContentType(NotificationContentType.MODIFIED_ATTRIBUTES);

		String flexContainerLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/"
				+ flexContainer.getName();
		String subscriptionName = "subscription_" + System.currentTimeMillis();
		String subscriptionLocation = flexContainerLocation + "/" + subscriptionName;

		ResponsePrimitive response = sendCreateSubscriptionRequest(subscription, flexContainerLocation,
				subscriptionName);
		Subscription returnedSubscription = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testUpdateSubscription", Status.KO, "unable to create the subscription", null);
			return;
		} else {
			returnedSubscription = (Subscription) response.getContent();

			if (!returnedSubscription.getNotificationURI().contains(subscriptionServlet.getServletUrl())) {
				createTestReport("testUpdateSubscription", Status.KO, "invalid notification URI", null);
				return;
			}
			if (!returnedSubscription.getName().equals(subscriptionName)) {
				createTestReport("testCreateSubscription", Status.KO, "invalid subscription name(expected:"
						+ subscriptionName + ", found:" + returnedSubscription.getName() + ")", null);
				return;
			}
		}

		Thread.sleep(1000);
		// check last notification
		Notification notification = subscriptionServlet.getLastNotification();
		if (notification == null) {
			// KO
			createTestReport("testUpdateSubscription", Status.KO, "expecting Notification message", null);

			return;
		} else {
			// expecting Notification object
			System.out.println("notif=" + notification);

			// reset list of notification
			subscriptionServlet.resetNotifications();
		}

		// update the value of the custom attribute
		FlexContainer toBeUpdated = new FlexContainer();
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powerState");
		ca.setCustomAttributeValue("false");
		ca.setCustomAttributeType("xs:boolean");
		toBeUpdated.getCustomAttributes().add(ca);
		response = sendUpdateFlexContainerRequest(flexContainerLocation, toBeUpdated);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.UPDATED)) {
			// KO
			createTestReport("testUpdateSubscription", Status.KO, "unable to update flexContainer", null);
			return;
		}

		// wait 1s
		Thread.sleep(1000);

		// check if we receive a Notification through the servlet
		notification = subscriptionServlet.getLastNotification();
		if (notification != null) {
			// check received notification
			// power state should be false

			FlexContainer notifiedFlexContainer = (FlexContainer) notification.getNotificationEvent()
					.getRepresentation().getResource();

			ca = notifiedFlexContainer.getCustomAttribute("powerState");
			if (ca != null) {
				if (!ca.getCustomAttributeValue().equals("false")) {
					createTestReport("testUpdateSubscription", Status.KO, "CustomAttribute powerState value is wrong",
							null);
					return;
				}

			} else {
				// KO
				// custom attribute is missing
				createTestReport("testUpdateSubscription", Status.KO, "Custom attribute powerState is missing", null);
				return;
			}
		} else {
			createTestReport("testUpdateSubscription", Status.KO, "no notification received. Expected one !", null);
		}

		// send subscription update
		SubscriptionServlet servlet2 = new SubscriptionServlet(httpService, dataMapperService);
		try {
			servlet2.registerServlet();
		} catch (ServletException e) {
			createTestReport("testUpdateSubscription", Status.KO, "unable to register servlet for notification", e);
		} catch (NamespaceException e) {
			createTestReport("testUpdateSubscription", Status.KO, "unable to register servlet for notification", e);
		}
		subscription.getNotificationURI().clear();
		subscription.getNotificationURI().add(servlet2.getServletUrl());
		subscription.setSubscriberURI(null);
		response = sendUpdateSubscriptionRequest(subscriptionLocation, subscription);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.UPDATED)) {
			createTestReport(
					"testUpdateSubscription", Status.KO, "unable to update a subscription (expected:"
							+ ResponseStatusCode.UPDATED + ", received:" + response.getResponseStatusCode() + ")",
					null);
			return;
		}
		servlet2.resetNotifications();
		subscriptionServlet.resetNotifications();

		// check servlet2 notification

		// send an update of the FlexContainer
		// update the value of the custom attribut
		toBeUpdated = new FlexContainer();
		ca = new CustomAttribute();
		ca.setCustomAttributeName("powerState");
		ca.setCustomAttributeValue("false");
		ca.setCustomAttributeType("xs:boolean");
		toBeUpdated.getCustomAttributes().add(ca);
		response = sendUpdateFlexContainerRequest(flexContainerLocation, toBeUpdated);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.UPDATED)) {
			// KO
			createTestReport("testUpdateSubscription", Status.KO, "unable to update flexContainer", null);
			return;
		}

		// wait 1s
		Thread.sleep(1000);

		// check if we receive a Notification through the servlet
		notification = servlet2.getLastNotification();
		if (notification != null) {
			// check received notification
			// power state should be false

			FlexContainer notifiedFlexContainer = (FlexContainer) notification.getNotificationEvent()
					.getRepresentation().getResource();

			ca = notifiedFlexContainer.getCustomAttribute("powerState");
			if (ca != null) {
				if (!ca.getCustomAttributeValue().equals("false")) {
					createTestReport("testUpdateSubscription", Status.KO, "CustomAttribute powerState value is wrong",
							null);
					return;
				}

			} else {
				// KO
				// custom attribute is missing
				createTestReport("testUpdateSubscription", Status.KO, "Custom attribute powerState is missing", null);
				return;
			}
		} else {
			createTestReport("testUpdateSubscription", Status.KO, "no notification received. Expected one !", null);
			return;
		}
		
		// check notification on first servlet
		notification = subscriptionServlet.getLastNotification();
		if (notification != null) {
			createTestReport("testUpdateSubscription", Status.KO, "Received a notification but not expected", null);
			return;
		}
				

		createTestReport("testUpdateSubscription", Status.OK, null, null);
	}

	private FlexContainer createFlexContainer() throws Exception {
		FlexContainer flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition("org.onem2m.home.moduleclass.binaryswitch");
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powerState");
		ca.setCustomAttributeType("xs:boolean");
		ca.setCustomAttributeValue("true");
		flexContainer.getCustomAttributes().add(ca);

		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer,
				"/" + Constants.CSE_ID + "/" + Constants.CSE_NAME, "FlexContainer_" + System.currentTimeMillis());
		if (response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			return (FlexContainer) response.getContent();
		} else {
			// KO
			throw new Exception(
					"Unable to create FlexContainer (responseStatusCode:" + response.getResponseStatusCode() + ")");
		}

	}

	/**
	 * Check if the servlet is registered. Create a Test report KO if not
	 * 
	 * @param methodName
	 *            name of the method
	 * @return true if the servlet has been successfully registered
	 */
	private boolean checkServletIsRegistered(String methodName) {
		if (!isServletRegistered) {
			createTestReport(methodName, Status.KO, "NotificationServlet can be registered",
					servletRegistrationException);
			return false;
		}
		return true;
	}

}
