/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.core.remotecse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

public class RemoteCseService implements org.eclipse.om2m.core.service.RemoteCseService {

	/** Logger */
	private static final Log LOGGER = LogFactory.getLog(RemoteCseService.class);

	private static final RemoteCseService INSTANCE = new RemoteCseService();
	private final Map<String, RemoteCSEEntity> remoteCses;
	private EventAdmin eventAdmin;

	public static RemoteCseService getInstance() {
		return INSTANCE;
	}

	private RemoteCseService() {
		remoteCses = new HashMap<>(2);
	}

	@Override
	public List<RemoteCSEEntity> getRemoteCses() {
		List<RemoteCSEEntity> toBeReturned = null;
		synchronized (remoteCses) {
			toBeReturned = new ArrayList<>(remoteCses.values());
		}
		return toBeReturned;
	}

	public void addRemoteCseAndPublish(RemoteCSEEntity toBeAdded) {
		LOGGER.info("addRemoteCseAndPublish(cseId=" + toBeAdded.getResourceID() +", name=" +toBeAdded.getName() + ")");
		synchronized (remoteCses) {
			remoteCses.put(toBeAdded.getName(), toBeAdded);
		}

		if (eventAdmin != null) {

			// create a new Event
			Map<String, Object> properties = new Hashtable<>();
			String cseId = toBeAdded.getRemoteCseId();
			if (cseId.startsWith("/")) {
				cseId = cseId.substring(1);
			}
			properties.put(REMOTE_CSE_ID_PROPERTY, cseId);
			properties.put(REMOTE_CSE_NAME_PROPERTY, toBeAdded.getName());
			properties.put(OPERATION_PROPERTY, ADD_OPERATION_VALUE);
			properties.put(REMOTE_CSE_POA, toBeAdded.getPointOfAccess());
			Event event = new Event(REMOTE_CSE_TOPIC, properties);

			// send it through EventAdmin (asynchronously)
			eventAdmin.postEvent(event);
			
			LOGGER.info("post Event to inform about RemoteCSE creation (cseId=" + cseId
			+ ", cseName=" + toBeAdded.getName() + ")");

		}

	}

	public void removeRemoteCseAndPublish(String cseName) {
		LOGGER.info("removeRemoteCseAndPublish(name=" + cseName + ")");
		RemoteCSEEntity toBeRemoved = null;
		synchronized (remoteCses) {
			toBeRemoved = remoteCses.remove(cseName);
		}

		if ((toBeRemoved != null) && (eventAdmin != null)) {
			// create a new Event
			Map<String, String> properties = new Hashtable<>();
			properties.put(REMOTE_CSE_ID_PROPERTY, toBeRemoved.getResourceID());
			properties.put(REMOTE_CSE_NAME_PROPERTY, toBeRemoved.getName());
			properties.put(OPERATION_PROPERTY, REMOVE_OPERATION_VALUE);
			Event event = new Event(REMOTE_CSE_TOPIC, properties);

			// send it through EventAdmin (asynchronously)
			eventAdmin.postEvent(event);

			LOGGER.info("post Event to inform about RemoteCSE deletion (cseId=" + toBeRemoved.getRemoteCseId()
					+ ", cseName=" + toBeRemoved.getName() + ")");
		}
	}

	public void setEventAdmin(final EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

}
