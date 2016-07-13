package org.eclipse.om2m.ipe.sdt.testsuite.subscription;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.Notification;
import org.eclipse.om2m.commons.resource.Notification.NotificationEvent;
import org.eclipse.om2m.commons.resource.Notification.NotificationEvent.Representation;
import org.eclipse.om2m.datamapping.service.DataMapperService;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

public class SubscriptionHttpServlet extends HttpServlet {

	private static final String SERVLET_PATH_PREFIX = "/subscription_";

	private final String servletPath;

	private final HttpService httpService;

	private final DataMapperService dataMapperService;

	private final NotificationQueue notificationQueue;
	
	private boolean openToStoreNotification = false;

	public SubscriptionHttpServlet(final HttpService pHttpService, final DataMapperService pDataMapperService,
			final NotificationQueue pNotificationQueue, final String moduleName) {
		this.httpService = pHttpService;
		this.dataMapperService = pDataMapperService;
		this.notificationQueue = pNotificationQueue;
		this.servletPath = SERVLET_PATH_PREFIX + System.currentTimeMillis() + moduleName;
	}

	/**
	 * register http servlet
	 * 
	 * @return true if successfully registered
	 */
	protected boolean register() {
		try {
			httpService.registerServlet(servletPath, this, null, null);
		} catch (ServletException | NamespaceException e) {
			return false;
		}
		return true;
	}

	/**
	 * unregister http servlet
	 */
	protected void unregister() {
		httpService.unregister(servletPath);
	}

	protected String getServletPath() {
		return servletPath;
	}
	
	protected void storeNotification() {
		openToStoreNotification = true;
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// read input stream
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		// marshalling
		Notification notification = (Notification) dataMapperService.stringToObj(sb.toString());

		// check if the request is a verification request
		boolean verifRequest = (notification.isVerificationRequest() != null
				? notification.isVerificationRequest().booleanValue() : false);
		if (verifRequest) {
			// return OK
			resp.setStatus(HttpServletResponse.SC_OK);
		} else {
			// notification
			NotificationEvent notifEvent = notification.getNotificationEvent();
			Representation representation = notifEvent.getRepresentation();

			if (representation.getResource() instanceof FlexContainer) {
				FlexContainer notifiedFlexContainer = (FlexContainer) representation.getResource();
				ReceivedNotification receivedNotification = new ReceivedNotification(notifiedFlexContainer, new Date());
				if (openToStoreNotification) {
					this.notificationQueue.addNotificationFromOM2M(receivedNotification);
				}
			}
			resp.setStatus(HttpServletResponse.SC_OK);
		}

	}

}
