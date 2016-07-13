package org.eclipse.om2m.testsuite.flexcontainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.resource.Notification;
import org.eclipse.om2m.datamapping.service.DataMapperService;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

public class SubscriptionServlet extends HttpServlet {

	private final DataMapperService dataMapperService;

	private final HttpService httpService;

	private List<Notification> receivedNotifications = new ArrayList<Notification>();

	private String servletPath = "/NotificationServlet_" + System.currentTimeMillis();

	public SubscriptionServlet(HttpService pHttpService, DataMapperService pDataMapperService) {
		this.httpService = pHttpService;
		this.dataMapperService = pDataMapperService;
	}

	/**
	 * Register the Subscription Servlet
	 * 
	 * @throws ServletException
	 * @throws NamespaceException
	 */
	public void registerServlet() throws ServletException, NamespaceException {
		this.httpService.registerServlet(servletPath, this, null, null);
	}

	/**
	 * Unregister the servlet
	 */
	public void unregisterServlet() {
		this.httpService.unregister(servletPath);
	}

	public String getServletUrl() {
		return "http://" + Constants.CSE_IP + ":" + Constants.CSE_PORT + servletPath;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		Notification notification = (Notification) dataMapperService.stringToObj(sb.toString());
		synchronized (receivedNotifications) {
			// add a new notification
			System.out.println("receive a new notification: " + notification.toString());
			receivedNotifications.add(notification);
		}

	}

	public Notification getLastNotification() {
		synchronized (receivedNotifications) {
			if (!receivedNotifications.isEmpty()) {
				return receivedNotifications.get(receivedNotifications.size() - 1);
			} else { 
				return null;
			}
		}
	}
	
	public List<Notification> getNotifications() {
		List<Notification> toBeReturned = new ArrayList<Notification>();
		
		synchronized (receivedNotifications) {
			toBeReturned.addAll(receivedNotifications);
		}
		
		return  toBeReturned;
	}
	
	public void resetNotifications() {
		synchronized (receivedNotifications) {
			receivedNotifications.clear();
		}
	}

//	public class Notification {
//
//		private final Object notification;
//
//		public Notification(Object pNotification) {
//			this.notification = pNotification;
//		}
//
//		public Object getNotification() {
//			return notification;
//		}
//
//		@Override
//		public String toString() {
//			if (notification.getClass().equals(org.eclipse.om2m.commons.resource.Notification.class)) {
//				org.eclipse.om2m.commons.resource.Notification n = (org.eclipse.om2m.commons.resource.Notification) notification;
//				return "Notification(creator=" + n.getCreator() + ", notificationForwardingUri="
//						+ n.getNotificationForwardingURI() + ", notificationEvent=" + n.getNotificationEvent()
//						+ ", subscriptionReference=" + n.getSubscriptionReference() + ")";
//			}
//			return "obj=" + notification;
//		}
//
//	}

}
