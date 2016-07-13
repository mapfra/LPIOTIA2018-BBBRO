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
