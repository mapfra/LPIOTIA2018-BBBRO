/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.testsuite.subscription;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.NotificationContentType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.DiscoveryResult;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.Notification;
import org.eclipse.om2m.commons.resource.Notification.NotificationEvent;
import org.eclipse.om2m.commons.resource.Notification.NotificationEvent.Representation;
import org.eclipse.om2m.commons.resource.Resource;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.Subscription;
import org.eclipse.om2m.commons.resource.URIList;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.datamapping.service.DataMapperService;
import org.eclipse.om2m.ipe.sdt.testsuite.CSEUtil;
import org.eclipse.om2m.ipe.sdt.testsuite.TestReport;
import org.eclipse.om2m.ipe.sdt.testsuite.TestReport.State;
import org.eclipse.om2m.ipe.sdt.testsuite.module.exception.FlexContainerNotFound;
import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.Property;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTracker;

public class SubscriptionTestSuite extends HttpServlet {
	
	private static final String SERVLET_PATH = "/notification";
	
	private final BundleContext bundleContext;
	
	private final CseService cseService;
	
	private final HttpService httpService;
	
	private final DataMapperService dataMapperService;
	
	private ServiceTracker sdtModuleTracker;
	
	private List<TestReport> reports = new ArrayList();
	
	private boolean toBeStopped = false;
	
	public SubscriptionTestSuite(BundleContext pBundleContext, CseService pCseService) {
		this.bundleContext = pBundleContext;
		this.cseService = pCseService;
		
		ServiceReference httpServiceReference = this.bundleContext.getServiceReference(HttpService.class.getName());
		if (httpServiceReference != null) {
			httpService = (HttpService) this.bundleContext.getService(httpServiceReference);
		} else {
			httpService = null;
		}
		
		ServiceReference dataMapperServiceReference = this.bundleContext.getServiceReference(DataMapperService.class.getName());
		if (dataMapperServiceReference != null) {
			dataMapperService = (DataMapperService) this.bundleContext.getService(dataMapperServiceReference);
		} else {
			dataMapperService = null;
		}
		
		if ((httpService != null) && (dataMapperService != null)) {
			sdtModuleTracker = new ServiceTracker(bundleContext, Module.class.getName(), null) {
				@Override
				public Object addingService(ServiceReference reference) {
					Module module = (Module) super.addingService(reference);
					testSubscriptionForModule(module);
					return null;
				}
				
				@Override
				public void removedService(ServiceReference reference, Object service) {
					// TODO Auto-generated method stub
					super.removedService(reference, service);
				}
			};
			sdtModuleTracker.open();
			
		}
		
	}
	
	public void stopTests() {
		synchronized (this) {
			toBeStopped = true;
		}
	}
	
	private boolean testsHaveToBeStopped() {
		boolean answer;
		synchronized (this) {
			answer = toBeStopped;
		}
		return answer;
	}
	
	/**
	 * Test subscription for a module
	 * @param module
	 */
	private void testSubscriptionForModule(final Module pModule) {
		
		Runnable r = new Runnable() {
			
			
			
			@Override
			public void run() {
				SubscriptionHttpServlet servlet;
				NotificationQueue notificationQueue;
				SubscriptionSDTListener sdtListener;
				Module module = pModule;
				
				
				if (module == null) {
					TestReport report = new TestReport("Test susbcription - module null");
					report.setErrorMessage("module null");		
					report.setState(State.KO);
					addTestReport(report);
					return;
				}
				
				TestReport report = new TestReport("Test subscription for module " + module.getName());
				
				// create notification queue
				notificationQueue = new NotificationQueue();
				
				// create servlet & register
				servlet = new SubscriptionHttpServlet(httpService, dataMapperService, notificationQueue, module.getName());
				servlet.register();
				
				
				// create subscription
				String subscriptionUrl = createSubscription(module, servlet.getServletPath());
				if (subscriptionUrl == null) {
					// subscription failed
					return;
				}
				
				// create SDT listeners
				sdtListener = new SubscriptionSDTListener(notificationQueue, bundleContext, module);
				sdtListener.register();
				
				// store notifs
				servlet.storeNotification();
				sdtListener.storeNotification();
				
				// wait 20s
				while(!testsHaveToBeStopped()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				// delete subscription
				deleteSusbcription(subscriptionUrl);
				
				// unregister subscription
				sdtListener.unregister();
				
				// unregister servlet
				servlet.unregister();
				
				List<ReceivedNotification> notificationsFromOM2M = notificationQueue.getNotificationsFromOM2M();
				List<ReceivedNotification> notificationsFromSDT = notificationQueue.getNotificationsFromSDT();
//				for(ReceivedNotification rn : notificationsFromOM2M) {
//					System.out.println(rn.get);
//				}
				System.out.println("number of notifs(FlexContainer) for module " + module.getName() + ":" + notificationsFromOM2M.size());
				System.out.println("number of notifs(SDT) for module " + module.getName() + ":" + notificationsFromSDT.size());
				if (notificationsFromOM2M.size() != notificationsFromSDT.size()) {
					report.setErrorMessage("received " + notificationsFromOM2M.size() + " from OM2M and received " + notificationsFromSDT.size() + " from SDT");
					report.setState(State.KO);
					addTestReport(report);
					return;
				}
				for(int i = 0; i < notificationsFromOM2M.size(); i++) {
					ReceivedNotification receivedNotificationOM2M = notificationsFromOM2M.get(i);
					ReceivedNotification receivedNotificationSDT = notificationsFromSDT.get(i);
					
					// SDT
					DataPoint dp = receivedNotificationSDT.getDataPoint();
					Date dateSDT = receivedNotificationSDT.getDate();
					Object value = receivedNotificationSDT.getValue();
					
					// OM2M
					FlexContainer fc = receivedNotificationOM2M.getFlexContainer();
					Date dateOM2M = receivedNotificationOM2M.getDate();
					CustomAttribute ca = fc.getCustomAttribute(dp.getName());
					
					// check if custom attribute exist
					if (ca == null) {
						// no custom attribute for the datapoint
						report.setErrorMessage("No customAttribute for datapoint " + dp.getName());
						report.setState(State.KO);
						addTestReport(report);
						return;
					}
					
					// check value
					String valueFromOM2M = ca.getCustomAttributeValue();
					if (!valueFromOM2M.equals(value.toString())) {
						report.setErrorMessage("value from OM2M (" + valueFromOM2M + ") is different of the value from SDT (" + value.toString() + ")");
						report.setState(State.KO);
						addTestReport(report);
						return;
					}
					
					// check date : the two notifications should have a close date/time
					long elapsedTime = Math.abs(dateOM2M.getTime() - dateSDT.getTime());
					if (elapsedTime > 2000) {
						report.setErrorMessage("Delay between SDT notif and OM2M notification is over 2s");
						report.setState(State.KO);
						addTestReport(report);
						return;
					}
					
					
				}
				
				
				report.setState(State.OK);
				report.setErrorMessage("nb of message:" + notificationsFromOM2M.size());
				addTestReport(report);
				
//				printTestReports();
				
			}
		};
		Thread t = new Thread(r);
		t.start();
		
	}
	
	public String createSubscription(Module pModule, String servletPath) {
		String subscriptionUrl = null;
		
		Subscription subscription = new Subscription();
		subscription.getNotificationURI().add("http://127.0.0.1:" + Constants.CSE_PORT + servletPath);
		subscription.setNotificationContentType(NotificationContentType.WHOLE_RESOURCE);
		
		String moduleFlexContainerUrl = null ;
		List<String> labels = new ArrayList<>();
		
		
		labels.add("name/" + pModule.getName());
//		labels.add("device.id/" + pModule.getOwner().getId());

		ResponsePrimitive responsePrimitive = CSEUtil.discovery(cseService, labels,
				"/" + Constants.CSE_ID + "/" + Constants.CSE_NAME);
		if (responsePrimitive.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			URIList discoveryResult = (URIList) responsePrimitive.getContent();
			if (discoveryResult.getListOfUri().size() == 1) {
				moduleFlexContainerUrl = discoveryResult.getListOfUri().get(0);
			} else {
				System.out.println("too much discoveryResult = " + discoveryResult.getListOfUri().size());
			}
		}
		
		String subscriptionName = "subscription_" + System.currentTimeMillis();
		
		if (moduleFlexContainerUrl != null) {
			ResponsePrimitive response = CSEUtil.createSubscription(cseService, subscription, moduleFlexContainerUrl, subscriptionName);
			if (!ResponseStatusCode.CREATED.equals(response.getResponseStatusCode())) {
				System.out.println("unable to create subscription");
				
			} else {
				Subscription createdSubscription = (Subscription) response.getContent();
				return moduleFlexContainerUrl + "/" + subscriptionName;
			}
		}

		return null;

	}
	
	public void deleteSusbcription(String subscriptionUrl) {
		ResponsePrimitive response = CSEUtil.deleteResource(cseService, subscriptionUrl);
		if (!ResponseStatusCode.DELETED.equals(response.getResponseStatusCode())) {
			System.out.println("unable to delete subscription: " + response.getContent());
		} else {
			System.out.println("susbcription " + subscriptionUrl + " successfully deleted");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("################################################################################");
		System.out.println("receive a POST");
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
	protected void addTestReport(TestReport report) {
		synchronized (reports) {
			reports.add(report);
		}
	}
	
	protected List<TestReport> getTestReports() {
		List<TestReport> list = new ArrayList<>();
		synchronized (reports) {
			list.addAll(reports);
		}
		return list;
	}
	
	
	public void printTestReports() {
		System.out.println("################################################################################");
		List<TestReport> list = getTestReports();
		for(TestReport report : list) {
			System.out.println(report.toString());
		}
		System.out.println("################################################################################");
	}
	
	

}
