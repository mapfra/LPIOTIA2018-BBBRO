/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.persistence.mongodb;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class DBTransactionImpl implements DBTransaction {
	
	private static final Log LOGGER = LogFactory.getLog(DBTransactionImpl.class);

	private static final Set<String> GLOBAL_LOCKED_OBJECTS = new HashSet<>();

	private final UUID uuid;
	private final Set<String> lockedObjects;

	private boolean childToBeLoaded = true;

	public DBTransactionImpl() {
		uuid = UUID.randomUUID();
		lockedObjects = new HashSet<>();
	}

	@Override
	public void open() {
		// nothing to do
	}

	@Override
	public void commit() {
		LOGGER.info("commit() for trasaction " + uuid);
		Set<String> currentLockedObjects = new HashSet<>();
		synchronized (lockedObjects) {
			currentLockedObjects.addAll(lockedObjects);
		}
		
		for(String lockedResourceId : currentLockedObjects) {
			unlock(lockedResourceId);
		}
	}

	@Override
	public void close() {
	}

	@Override
	public void clear() {
	}

	@Override
	public void lock(Object object) {
		LOGGER.info("lock()");;
		
		ResourceEntity resourceEntityToBeLocked = null;
		if (object instanceof ResourceEntity) {
			resourceEntityToBeLocked = (ResourceEntity) object;
		}

		if (resourceEntityToBeLocked != null) {
			// handle lock
			String resourceId = resourceEntityToBeLocked.getResourceID();

			LOGGER.info("request lock for " + resourceId + " by transaction " + uuid);
			
			// add global lock (blocking call for 10s max
			if (addGlobalLock(resourceId)) {
				synchronized (lockedObjects) {
					lockedObjects.add(resourceId);
				}
			} else {
				throw new RuntimeException("unable to acquire lock for resource " + resourceId + " by transaction " + uuid);
			}

		} else {
			// does nothing ?
			throw new RuntimeException();
		}

	}

	@Override
	public void unlock(Object object) {
		LOGGER.info("unlock object=" + object + " for transaction " + uuid);
		ResourceEntity resourceEntityToBeLocked = null;
		if (object instanceof ResourceEntity) {
			resourceEntityToBeLocked = (ResourceEntity) object;
		}

		if (resourceEntityToBeLocked != null) {
			String resourceId = resourceEntityToBeLocked.getResourceID();
			unlock(resourceId);
			
		}
	}
	
	private void unlock(String resourceId) {
		LOGGER.debug("try to unlock " + resourceId + " by transaction " + uuid);
		synchronized (lockedObjects) {
			 if (lockedObjects.remove(resourceId)) {
				 removeGlobalLock(resourceId);
			 }
		}
	}

	public boolean isChildToBeLoaded() {
		return childToBeLoaded;
	}

	public void setChildToBeLoaded(boolean childToBeLoaded) {
		this.childToBeLoaded = childToBeLoaded;
	}

	/**
	 * Check if a global lock exists for the provided resourceId
	 * 
	 * @param resourceId
	 * @return true if a lock exists for this resourceId
	 */
	private static boolean isExistingLock(final String resourceId) {
		synchronized (GLOBAL_LOCKED_OBJECTS) {
			return GLOBAL_LOCKED_OBJECTS.contains(resourceId);
		}
	}

	private synchronized static boolean addGlobalLock(final String resourceId) {
		synchronized (GLOBAL_LOCKED_OBJECTS) {

			long initialTime = System.currentTimeMillis();
			while (System.currentTimeMillis() < initialTime + 10000) {
				if (GLOBAL_LOCKED_OBJECTS.contains(resourceId)) {
					try {
						GLOBAL_LOCKED_OBJECTS.wait(1000);
					} catch (InterruptedException e) {
						return false;
					}
				}

				if (!GLOBAL_LOCKED_OBJECTS.contains(resourceId)) {
					GLOBAL_LOCKED_OBJECTS.add(resourceId);
					return true;
				}
			}

		}

		return false;
	}

	private synchronized static void removeGlobalLock(final String resourceId) {
		synchronized (GLOBAL_LOCKED_OBJECTS) {
			GLOBAL_LOCKED_OBJECTS.remove(resourceId);
			GLOBAL_LOCKED_OBJECTS.notifyAll();
		}
	}

}
