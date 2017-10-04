/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.testsuite.subscription;

import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.events.SDTEventListener;
import org.eclipse.om2m.sdt.events.SDTNotification;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class SubscriptionSDTListener implements SDTEventListener {
	
	private final NotificationQueue notificationQueue;
	private final BundleContext bundleContext;
	private final Module module;
	private boolean storeNotification = false;
	private ServiceRegistration serviceRegistration;
	
	public SubscriptionSDTListener(final NotificationQueue pNotificationQueue, final BundleContext pBundleContext, final Module pModule) {
		this.notificationQueue = pNotificationQueue;
		this.bundleContext = pBundleContext;
		this.module = pModule;
		
	}
	
	protected void register() {
		Dictionary<String, String> properties = new Hashtable<>();
		properties.put(SDTEventListener.MODULES_NAMES, module.getName());
		properties.put(SDTEventListener.DOMAINS, "*");
		properties.put(SDTEventListener.DEVICES_IDS, module.getOwner().getId());
		properties.put(SDTEventListener.DATAPOINTS, "*");
		properties.put(SDTEventListener.DEVICES_DEFS, "*");
		properties.put(SDTEventListener.MODULES_DEFS, "*");
		
		serviceRegistration = bundleContext.registerService(SDTEventListener.class.getName(), this, properties);
	}
	
	protected void unregister() {
		if (serviceRegistration != null) {
			serviceRegistration.unregister();
			serviceRegistration = null;
		}
	}
	
	protected void storeNotification() {
		this.storeNotification = true;
	}
	

	@Override
	public void handleNotification(SDTNotification notif) {
		
		Date currentDate = new Date();
		DataPoint dataPoint = notif.getDataPoint();
		Object value = notif.getValue();
		
		ReceivedNotification receivedNotification = new ReceivedNotification(dataPoint, value, currentDate);
		if (storeNotification) {
			this.notificationQueue.addNotificationFromSDT(receivedNotification);
		}
		

	}

	@Override
	public void setAuthenticationThreadGroup(ThreadGroup group) {
		// TODO Auto-generated method stub

	}

}
