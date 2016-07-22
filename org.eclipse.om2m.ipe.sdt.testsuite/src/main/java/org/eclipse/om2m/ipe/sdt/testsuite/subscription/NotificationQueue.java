/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.testsuite.subscription;

import java.util.ArrayList;
import java.util.List;

public class NotificationQueue {
	
	
	private final List<ReceivedNotification> notificationsFromOM2M = new ArrayList<>();
	private final List<ReceivedNotification> notificationsFromSDT = new ArrayList<>();
	
	public void addNotificationFromOM2M(ReceivedNotification notification) {
		synchronized (notificationsFromOM2M) {
			notificationsFromOM2M.add(notification);
		}
	}
	
	public void addNotificationFromSDT(ReceivedNotification notification) {
		synchronized (notificationsFromSDT) {
			notificationsFromSDT.add(notification);
		}
	}

	public List<ReceivedNotification> getNotificationsFromOM2M() {
		return notificationsFromOM2M;
	}

	public List<ReceivedNotification> getNotificationsFromSDT() {
		return notificationsFromSDT;
	}
	
	
	

}
